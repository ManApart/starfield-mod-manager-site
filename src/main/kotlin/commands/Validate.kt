package commands

import Mod
import blue
import cyan
import detectStagingChanges
import toolData
import yellow
import java.io.File

fun validateHelp() = """
    validate
    validate <mod index>
    validate 1 2 4
    validate 1-3
    validate staged
    validate enabled
    validate disabled
""".trimIndent()

fun validateMods(args: List<String>) {
    when {
        args.isEmpty() -> validate()
        args.first() == "staged" -> validate { File(it.filePath).exists() }
        args.first() == "enabled" -> validate { it.enabled }
        args.first() == "disabled" -> validate { it.enabled }
        else -> {
            args.getIndicesOrRange(toolData.mods.size)
                .mapNotNull { toolData.byIndex(it) }
                .validate()
        }
    }
}

private fun validate(filter: (Mod) -> Boolean = {true}) {
    toolData.mods.filter(filter).validate()
}

private fun List<Mod>.validate() {
    val errorMap = mutableMapOf<Int, Pair<Mod, MutableList<String>>>()
    val indexed = mapIndexed { i, mod -> mod to i }.toMap()

    addDupeIds(indexed, errorMap)
    addDupeFilenames(indexed, errorMap)
    detectStagingIssues(errorMap)
    detectDupePlugins(indexed)
    detectIncorrectCasing(indexed, errorMap)

    printErrors(errorMap)
    println(cyan("Validated $size mods"))
}

private fun List<Mod>.addDupeIds(
    indexed: Map<Mod, Int>,
    errorMap: MutableMap<Int, Pair<Mod, MutableList<String>>>
) {
    groupBy { it.id }.filter { it.key != null && it.value.size > 1 }.map { it.value }.forEach { dupes ->
        val indexes = dupes.map { indexed[it]!! }
        dupes.forEach { dupe ->
            val i = indexed[dupe]!!
            errorMap.putIfAbsent(i, dupe to mutableListOf())
            errorMap[i]?.second?.add("Duplicate Id ($indexes)")
        }
    }
}

private fun List<Mod>.addDupeFilenames(
    indexed: Map<Mod, Int>,
    errorMap: MutableMap<Int, Pair<Mod, MutableList<String>>>
) {
    groupBy { it.filePath }.filter { it.value.size > 1 }.map { it.value }.forEach { dupes ->
        val indexes = dupes.map { indexed[it]!! }
        dupes.forEach { dupe ->
            val i = indexed[dupe]!!
            errorMap.putIfAbsent(i, dupe to mutableListOf())
            errorMap[i]?.second?.add("Duplicate Filepath ($indexes)")
        }
    }
}


private fun List<Mod>.detectStagingIssues(errorMap: MutableMap<Int, Pair<Mod, MutableList<String>>>) {
    forEachIndexed { i, mod ->
        val stageFolder = File(mod.filePath)
        if (stageFolder.exists()) {
            when (detectStagingChanges(stageFolder)) {
                StageChange.UNKNOWN -> {
                    errorMap.putIfAbsent(i, mod to mutableListOf())
                    errorMap[i]?.second?.add("Unable to guess folder path. You should open the staging folder and make sure it was installed correctly.")
                }

                StageChange.FOMOD -> {
                    errorMap.putIfAbsent(i, mod to mutableListOf())
                    errorMap[i]?.second?.add("FOMOD detected. You should open the staging folder and pick options yourself.")
                }

                StageChange.NO_FILES -> {
                    errorMap.putIfAbsent(i, mod to mutableListOf())
                    errorMap[i]?.second?.add("No files found in stage folder.")
                }

                else -> {}
            }
        }
    }
}

private fun detectDupePlugins(indexed: Map<Mod, Int>) {
    val dupes = indexed.keys.flatMap { mod ->
        mod.getModFiles().filter { it.extension.lowercase() in listOf("esp", "esm", "esl") }
    }.groupBy { it.name }.filter { it.value.size > 1 }
    if (dupes.isNotEmpty()) {
        println("The following plugins are duplicated: ${dupes.keys.joinToString()}\n")
    }
}

private fun detectIncorrectCasing(
    indexed: Map<Mod, Int>,
    errorMap: MutableMap<Int, Pair<Mod, MutableList<String>>>
) {
    indexed.forEach { (mod, i) ->
        val modsPaths = mod.getModFiles()
            .map { it.parent }
            .mapNotNull { file ->
                val start = file.indexOf("Data") + 4
                val end = file.lastIndexOf("/")
                if (start < end) file.substring(start, end) else null
            }.toSet()
            .filter { it != it.lowercase() }
        if (modsPaths.isNotEmpty()) {
            errorMap.putIfAbsent(i, mod to mutableListOf())
            errorMap[i]?.second?.add("Filepaths should be lowercase between data and filename:")
            modsPaths.forEach {
                errorMap[i]?.second?.add("\t${it}")
            }
        }
    }
}

private fun printErrors(errorMap: MutableMap<Int, Pair<Mod, MutableList<String>>>) {
    errorMap.entries.forEach { (i, errorList) ->
        val (mod, errors) = errorList
        println("$i ${yellow(mod.name)} has issues:")
        errors.forEach { error ->
            println("\t$error")
        }
    }
}