package commands

import Mod
import addModById
import refreshMod
import toolData
import java.io.File

fun refreshHelp() = """
    refresh <mod index>
    refresh 1 2 4
    refresh 1-3
    refresh all - For all mods with ids, attempt to redownload (or grab the file from the downloads folder if it exists) and restage.
    refresh empty - Refresh any files without staged data
    refresh staged - Refresh only files that are staged
    If you're looking to upgrade to a new version, see update and upgrade
""".trimIndent()

fun refresh(args: List<String>) {
    when {
        args.isEmpty() -> println(refreshHelp())
        args.first() == "empty" -> {
            toolData.mods
                .filter { !File(it.filePath).exists() }
                .refreshMods()
        }
        args.first() == "staged" -> {
            toolData.mods
                .filter { File(it.filePath).exists() }
                .refreshMods()
        }
        else -> {
            args.getIndicesOrRange(toolData.mods.size)
                .mapNotNull { toolData.byIndex(it) }
                .refreshMods()
        }
    }
}

private fun List<Mod>.refreshMods() {
        also { println("Refreshing ${it.size} mods") }
        .forEach { refreshMod(it) }
    println("Done Refreshing")
}