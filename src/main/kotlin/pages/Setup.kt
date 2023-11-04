package pages

import kotlinx.html.*

fun BODY.setup() {
    nav()
    section {
        script { src = "/assets/asciinema-player.min.js" }
        h1 { +"Setup" }

        div("section") {
            div("accent-line") { +"Prerequisites" }

        }

        div("section") {
            div("accent-line") { +"Shortcuts and Vortex Links" }

        }

        div("section") {
            div("accent-line") { +"Configure the App" }

        }

        div("section") {
            div("accent-line") { +"Deploy to Game Folder" }

        }

        div("section") {
            div("accent-line") { +"Alternative Deploy to Docs Folder" }

        }
    }
}