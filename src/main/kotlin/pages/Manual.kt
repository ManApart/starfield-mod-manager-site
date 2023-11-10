package pages

import accentLine
import jsonMapper
import kotlinx.html.*
import kotlinx.serialization.Serializable
import java.io.File

fun BODY.manual() {
    nav()
    val manualData = File("../mod-manager/manual-data.json")
    if (manualData.exists()) {
        val allCommands = jsonMapper.decodeFromString<List<CommandJson>>(manualData.readText())
        section {
            h1 { +"Commands Manual" }
            allCommands.groupBy { it.category }.entries.sortedBy { it.key }.forEach { (category, commands) ->
                accentLine(category.name.lowercase().capitalize())
                table("manual-table") {
                    tr {
                        td("header-command") {
                            +"Command"
                        }
                        td("header-usage") {
                            +"Usage"
                        }
                        td("header-description") {
                            +"Description"
                        }
                    }
                    commands.forEach { command ->
                        tr {
                            td {
                                p { +command.name }
                                p { +command.aliases.joinToString(", ") }
                            }
                            td { command.usage.split("\n").forEach { p { +it } } }
                            td { command.description.split("\n").forEach { p { +it } } }
                        }
                    }
                }
            }
        }
    }
}

enum class Category { ADD, DEPLOY, VIEW, EDIT, OPEN, UPDATE, CONFIG }

@Serializable
private data class CommandJson(
    val name: String,
    val category: Category,
    val summary: String,
    val description: String,
    val usage: String,
    val aliases: List<String> = listOf()
)