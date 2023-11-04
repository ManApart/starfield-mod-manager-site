package pages

import jsonMapper
import kotlinx.html.*
import kotlinx.serialization.Serializable
import java.io.File

fun BODY.manual() {
    val manualData = File("../mod-manager/manual-data.json")
    if (manualData.exists()) {
        val commands = jsonMapper.decodeFromString<List<CommandJson>>(manualData.readText())
        section {
            table {
                tr {
                    td { +"Command" }
                    td { +"Summary" }
                    td { +"Aliases" }
                    td { +"Usage" }
                }
                commands.forEach { command ->
                    tr {
                        td { +command.name }
                        td { +command.summary }
                        td { +command.aliases.joinToString(",") }
                        td { +command.usage }
                    }
                }
            }
        }
    }
}

@Serializable
private data class CommandJson(
    val name: String,
    val summary: String,
    val aliases: List<String> = listOf(),
    val usage: String
)