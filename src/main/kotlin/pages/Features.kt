package pages

import accentLine
import asciCast
import asciCastSetup
import kotlinx.html.*

fun BODY.features() {
    nav()
    section {
        h1 { +"Features" }
        asciCastSetup()
        div("section") {
            accentLine("Flexible Commands can target")
            ul {
                li { +"A single mod" }
                li { +"A list of mods" }
                li { +"A range of mods" }
                li { +"All mods" }
            }
            asciCast("bulk", rows = 10, posterTime = "0:02")
        }
        div("section") {
            accentLine("Add Mods")
            ul {
                li { +"By clicking the 'Vortex' download button" }
                li { +"By id" }
                li { +"By url" }
                li { +"By local zip file" }
            }
            asciCast("add", rows = 10, posterTime = "0:18")
        }
        div("section") {
            accentLine("Open Mods")
            ul {
                li { +"Locally" }
                li { +"On Nexus" }
            }
            asciCast("open", rows = 10, posterTime = "0:02")
            p { +"(Browser and folder opens outside the terminal, so are not seen in the demo)" }
        }
        div("section") {
            accentLine("Fetch Metadata and Updates")
            ul {
                li { +"Add a mod without downloading it" }
                li { +"Refresh existing mods" }
                li { +"Check for updates" }
                li { +"Download all updated files" }
                li { +"Redownload or restage files" }
                li { +"Endorse or Abstain from mods" }
            }
            asciCast("fetch", rows = 10, posterTime = "0:02")
        }
        div("section") {
            accentLine("Enable and Disable Mods")
            ul {
                li { +"Manage Plugins.txt" }
                li { +"Change load order" }
                li { +"Deploy Dry run to see which files will be applied" }
                li { +"Save local mod lists (profiles)" }
                li { +"Purge all symlinks" }
            }
            asciCast("enable", posterTime = "0:02")
        }
        div("section") {
            accentLine("Search Mods")
            ul {
                li { +"Use filter to apply search every time" }
                li { +"By name " }
                li { +"By category" }
                li { +"By enabled" }
                li { +"By Staged" }
                li { +"etc" }
            }
            asciCast("search", posterTime = "0:05")
        }
        div("section") {
            accentLine("Sort Mods")
            ul {
                li { +"By id" }
                li { +"By name" }
                li { +"By enabled" }
                li { +"By Category" }
                li { +"And more" }
            }
            asciCast("sort", rows = 20, posterTime = "0:02")
        }
        div("section") {
            accentLine("Alternatives")
            ul {
                +"Don't like this mod manager? Try another one:"
                li { a(href = "https://github.com/cyberrumor/ammo", target = "_blank") { +"Ammo" } }
                li { a(href = "https://github.com/CHollingworth/Lamp", target = "_blank") { +"Lamp" } }
                li {
                    a(
                        href = "https://github.com/lVlyke/starfield-mod-loader",
                        target = "_blank"
                    ) { +"Starfield Mod Loader" }
                }
            }
        }
    }
}


