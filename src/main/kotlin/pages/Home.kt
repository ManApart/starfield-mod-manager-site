package pages

import accentLine
import asciCast
import asciCastSetup
import kotlinx.html.*


fun BODY.home() {
    section {
        h1 { +"Starfield Mod Manager" }

        div("links") {
            a("features.html", classes = "a-button") { +"Features" }
            a("setup.html", classes = "a-button") { +"Setup" }
            a("manual.html", classes = "a-button") { +"Manual" }
            a(
                "https://github.com/ManApart/starfield-mod-manager",
                classes = "a-button",
                target = "_blank"
            ) { +"Source" }
        }
        asciCastSetup()

        div("section") {
            id = "home-summary"
            accentLine("Linux CLI Mod Manager")
            p { +"Flexible CLI commands support everything from Nexus download links to bulk updating mods, to maintaining and deploying local mod collections." }

            p { +"Check out the demo below and click Features above to discover more functionality." }
            asciCast("enable", posterTime = "0:02")
        }

    }
}