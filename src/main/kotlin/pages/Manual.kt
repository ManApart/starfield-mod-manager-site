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
        val tag = getLatestTag()
        toc(allCommands)
        section {
            h1 { +"Commands Manual" }
            p {
                +"This command reference can also be viewed in app by using the "
                code { +"help" }
                +" command. This reference is is for version "
                code { +tag }
                +". Download the latest from "
                a(
                    href = "https://github.com/ManApart/manapart-mod-manager/releases",
                    target = "_blank"
                ) { +"github" }
                +" or possibly "
                a(href = "https://www.nexusmods.com/starfield/mods/6576", target = "_blank") { +"the Nexus" }
                +". (Nexus may not have bleeding edge versions)."
            }
            p { +"Expand or collapse sections by clicking their header." }
            allCommands.groupBy { it.category }.entries.sortedBy { it.key }.forEach { (category, commands) ->
                details {
                    id = "$category-details"
                    open = true
                    script {
                        unsafe {
                            raw(
                                """
                document.addEventListener('DOMContentLoaded', function() {
                    console.log(document.getElementById("$category-details"))
                    document.getElementById("$category-details").open = !isMobile()
                });
            """.trimIndent()
                            )
                        }
                    }
                    summary { accentLine(category.name.lowercase().capitalize()) }
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
                                    p {
                                        id = "command-${command.name}"
                                        +command.name
                                    }
                                    command.aliases.forEach { alias -> p { +alias } }
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
}

private fun BODY.toc(allCommands: List<CommandJson>) {
    div {
        id = "toc-wrapper"
        details {
            summary("a-button") { +"TOC" }
            div {
                id = "toc"
                allCommands.groupBy { it.category }.entries.sortedBy { it.key }.forEach { (category, commands) ->
                    div {
                        a(href = "#${category.name.lowercase().capitalize()}") { +category.name }
                        ul {
                            commands.forEach { command ->
                                li { a(href = "#command-${command.name}") { +command.name.capitalize() } }
                            }
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
