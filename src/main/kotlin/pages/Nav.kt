package pages

import kotlinx.html.BODY
import kotlinx.html.a

fun BODY.nav() {
    a("index.html", classes = "a-button home-button") { +"Home" }
}