package pages

import asciCast
import kotlinx.html.*


fun BODY.home() {
    section {
        script { src = "assets/asciinema-player.min.js" }
        h1 { +"Starfield Mod Manager" }
        div("section") {
            div("accent-line") { }
            p { +"A CLI based Starfield Mod Manager for Linux. Requires some technical know-how and familiarity with the CLI, but also provides a thin slice of Vortex capabilities." }
            p { +"Currently only fully supports premium members. Non Premium members should be able to add files by zip folder or nexus \"download with mod manager links\", but likely won't be able to download mods by pasting in a url or id." }
            p {
                +"Please report any issues with the "
                a(
                    href = "https://github.com/ManApart/starfield-mod-manager/issues",
                    target = "_blank"
                ) { +"manager" }
                +" or "
                a(
                    href = "https://github.com/ManApart/starfield-mod-manager-site/issues",
                    target = "_blank"
                ) { +"this site" }
                +" to their respective github issues page."
            }

            //TODO - do demo of adding a mod here
            asciCast("test")


        }

    }
}