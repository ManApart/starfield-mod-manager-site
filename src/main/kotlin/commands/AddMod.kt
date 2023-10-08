package commands

import Mod
import save
import toolState

fun addModHelp(args: List<String> = listOf()) = """
   add-mod file <name-of-mod> <path-to-mod-zip>
""".trimIndent()

fun addMod(args: List<String>) {
    val subCommand = args.firstOrNull()
    when {
        args.size < 3 -> println(addModHelp())
//        subCommand == "id" -> addModById(args[1], args[2])
//        subCommand == "url" -> addModByUrl(args[1], args[2])
        subCommand == "file" -> addModByFile(args[1], args[2])

        else -> println("Unknown args: ${args.joinToString(" ")}")
    }
}

//private fun addModById(id: String, name: String){
//    println("Add by id $id $name")
//}
//private fun addModByUrl(url: String, name: String){
//    println("Add by url")
//}
private fun addModByFile(name: String, filePath: String) {
    if (stageMod(name, filePath)) {
        val loadOrder = toolState.mods.maxOfOrNull { it.loadOrder } ?: 0
        toolState.mods.add(Mod(name, filePath, loadOrder + 1))
        save()
        println("Added $name")
    } else {
        println("Failed to add mod $name")
    }
}

private fun stageMod(name: String, filePath: String): Boolean{
    return true
}