import nexus.*
import java.io.File

fun addModById(id: Int, fileId: Int? = null) {
    val mod = fetchModInfo(id, fileId) ?: return
    val cleanName = mod.name.replace(" ", "-")
    println("Downloading $id: ${mod.name}")
    val downloadUrl = getDownloadUrl(toolConfig.apiKey!!, mod.id!!, mod.fileId!!)
    val destination = "$HOME/Downloads/$cleanName${parseFileExtension(downloadUrl)}"
    val downloaded = downloadMod(downloadUrl, destination)
    addModFile(mod, downloaded, mod.name)
}

fun fetchModInfo(id: Int, fileId: Int? = null): Mod? {
    val modInfo = getModDetails(toolConfig.apiKey!!, id)
    val modFileId = fileId ?: getModFiles(toolConfig.apiKey!!, id).files.let { files ->
        if (files.size == 1) files.first().file_id else {
            files.firstOrNull { it.is_primary }?.file_id
        }
    }

    if (modFileId == null) {
        println("Could not find primary file for $id")
        return null
    }

    val modName = modInfo.name.lowercase()
    val cleanName = modName.replace(" ", "-")
    val filePath = modFolder.path + "/" + cleanName
    toolData.createOrUpdate(modInfo.mod_id, modName, filePath)
    toolData.update(modInfo, modFileId)
    return toolData.byId(modInfo.mod_id)!!.also { save() }
}


fun addModByNexusProtocol(url: String) {
    val request = parseDownloadRequest(url)
    val modInfo = getModDetails(toolConfig.apiKey!!, request.modId)
    val modName = modInfo.name.lowercase()
    val cleanName = modName.replace(" ", "-")
    val filePath = modFolder.path + "/" + cleanName
    toolData.createOrUpdate(modInfo.mod_id, modName, filePath)
    toolData.update(modInfo, request.fileId)
    val mod = toolData.byId(modInfo.mod_id)!!
    save()
    println("Downloading $modName")
    val downloadUrl = getDownloadUrl(toolConfig.apiKey!!, request)
    val destination = "$HOME/Downloads/$cleanName${parseFileExtension(downloadUrl)}"
    val downloaded = downloadMod(downloadUrl, destination)
    addModFile(mod, downloaded, modName)
}

fun addModByFile(filePath: String, nameOverride: String?) {
    val name = nameOverride ?: File(filePath).nameWithoutExtension
    val sourceFile = File(filePath)

    val existing = toolData.byName(name)
    val mod = if (existing != null) existing else {
        val loadOrder = toolData.nextLoadOrder()
        val stagePath = modFolder.path + "/" + name.replace(" ", "-")
        Mod(name, stagePath, loadOrder + 1).also {
            toolData.mods.add(it)
            save()
        }
    }

    addModFile(mod, sourceFile, name)
}

fun addModFile(mod: Mod, sourceFile: File, modName: String) {
    if (!sourceFile.exists()) {
        println("Could not find ${sourceFile.absolutePath}")
        return
    }
    val stageFile = File(mod.filePath)
    val stageExists = stageFile.exists()
    if (stageMod(sourceFile, stageFile, modName)) {
        if (stageExists) {
            println("Updated ${mod.name}")
        } else {
            println("Added ${mod.name}")
        }
    } else {
        println("Failed to add mod ${mod.name}")
    }
}