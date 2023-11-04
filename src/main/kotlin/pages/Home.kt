package pages

import kotlinx.html.*


fun BODY.home() {
    section {
        style = "text-align: center;"
        div {
            id = "home"

            h1 {
                id = "home-title"
                +"Starfield Mod Manager"
            }
            div("accent-line") {  }
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