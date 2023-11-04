package pages

import kotlinx.html.*

fun BODY.features() {
    nav()
    section("features-section") {
        script { src = "/assets/asciinema-player.min.js" }

        h1 {
            +"Features"
        }
        div("section") {
            div("accent-line") { +"Flexible Commands can target" }
            ul {
                li { +"A single mod" }
                li { +"A list of mods" }
                li { +"A range of mods" }
                li { +"All mods" }
            }
            asciCast("test")
        }
        div("section") {
            div("accent-line") { +"Add Mods" }
            ul {
                li { +"By clicking the 'Vortex' download button" }
                li { +"By id" }
                li { +"By url" }
                li { +"By local zip file" }
            }
        }
        div("section") {
            div("accent-line") { +"Open Mods" }
            ul {
                li { +"Locally" }
                li { +"On Nexus" }
            }
        }
        div("section") {
            div("accent-line") { +"Fetch Metadata and Updates" }
            ul {
                li { +"Add a mod without downloading it" }
                li { +"Refresh existing mods" }
                li { +"Check for updates" }
                li { +"Download all updated files" }
                li { +"Redownload or restage files" }
                li { +"Endorse or Abstain from mods" }
            }
        }
        div("section") {
            div("accent-line") { +"Enable and Disable Mods" }
            ul {
                li { +"Change load order" }
                li { +"Deploy Dry run to see which files will be applied" }
                li { +"Save local mod lists (profiles)" }
                li { +"Purge all symlinks" }
            }
        }
        div("section") {
            div("accent-line") { +"Search Mods" }
            ul {
                li { +"Use filter to apply search every time" }
                li { +"By name " }
                li { +"By category" }
                li { +"By enabled" }
                li { +"By Staged" }
                li { +"etc" }
            }
        }
        div("section") {
            div("accent-line") { +"Sort Mods" }
            ul {
                li { +"By id" }
                li { +"By name" }
                li { +"By enabled" }
                li { +"By Category" }
                li { +"And more" }
            }
        }
    }
}

private fun DIV.asciCast(castName: String) {
    div("asci-cast") { id = castName }
    script {
        +"AsciinemaPlayer.create('assets/casts/$castName.cast', document.getElementById('$castName'));"
    }
}