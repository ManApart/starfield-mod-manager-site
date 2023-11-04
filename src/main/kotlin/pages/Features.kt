package pages

import kotlinx.html.*

fun BODY.features() {
    section {
        style = "text-align: center;"
        div {
            id = "home"

            h1 {
                id = "home-title"
                +"Features"
            }
            div("accent-line") { +"Flexible Commands" }
            ul { +"Stuff"
                li { +"To one mod" }
            }
            div("container") {
                div {
                    id = "asci"
                }
                script { src = "/assets/asciinema-player.min.js" }
                script {
                    +"AsciinemaPlayer.create('assets/test.cast', document.getElementById('asci'));"
                }
            }
        }
    }
}