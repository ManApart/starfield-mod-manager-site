package commands

import Mod
import toolState
import java.io.File

fun refreshHelp(args: List<String> = listOf()) = """
    refresh <mod index>
    refresh 1 2 4
    refresh 1-3
    refresh all - For all mods with ids, attempt to redownload (or grab the file from the downloads folder if it exists) and restage.
    refresh empty - Refresh any files without staged data
""".trimIndent()

fun refresh(args: List<String>) {
    if (args.isEmpty()) {
        println(refreshHelp())
    } else if (args.first() == "empty"){
        toolState.mods
            .filter { !File(it.filePath).exists() }
            .refreshMods()
    } else {
        args.getIndicesOrRange(toolState.mods.size)
            .mapNotNull { toolState.byIndex(it) }
            .refreshMods()
    }
}

private fun List<Mod>.refreshMods(){
    filter { it.id != null }
        .also { println("Refreshing ${it.size} mods") }
        .forEach { addModById(it.id!!, it.fileId) }
        println("Done Refreshing")
}