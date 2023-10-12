import kotlinx.serialization.Serializable
import nexus.ModInfo

@Serializable
data class State(
    val mods: MutableList<Mod> = mutableListOf()
) {
    fun byId(id: Int) = mods.firstOrNull { it.id == id }
    fun byName(name: String) = mods.firstOrNull { it.name == name }
    fun nextLoadOrder() = (mods.maxOfOrNull { it.loadOrder } ?: -1) + 1

    fun createOrUpdate(id: Int, name: String, filePath: String) {
        byId(id)?.also {
            it.name = name
            it.filePath = filePath
        } ?: Mod(name, filePath, nextLoadOrder(), id).also { mods.add(it) }
    }

    fun update(info: ModInfo, updatedFileId: Int? = null) {
        byId(info.mod_id)?.apply {
            version = info.version
            updatedFileId?.let { fileId = it }
        }
    }
}


