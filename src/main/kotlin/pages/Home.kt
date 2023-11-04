package pages

import kotlinx.html.*


fun BODY.home() {
    section {
        h1 {
            +"Starfield Mod Manager"
        }
        div("section") {
            div("accent-line") { }
            p { +"A CLI based Starfield Mod Manager for Linux. Requires some technical know-how and familiarity with the CLI, but also provides a thin slice of Vortex capabilities." }
            p { +"Currently only fully supports premium members. Non Premium members should be able to add files by zip folder or nexus \"download with mod manager links\", but likely won't be able to download mods by pasting in a url or id." }
            div("links") {
                a("/features.html", classes = "a-button") { +"Features" }
                a("/setup.html", classes = "a-button") { +"Setup" }
                a("/manual.html", classes = "a-button") { +"Manual" }
                a(
                    "https://github.com/ManApart/starfield-mod-manager",
                    classes = "a-button",
                    target = "_blank"
                ) { +"Source" }
            }
        }
        div("section") {
            div("accent-line") { +"Usage" }
            p {
                +"Run the app and then use "
                code { +"help" }
                +" to see commands. Alternatively you can look at the "
                a(href = "/manual") { +"generated man page" }
                +". You can also look through "
                a(href = "/featuress") { +"examples." }
            }
            p { +"To reduce typing, most commands take the index of the mod, instead of mod id or name. This means the index of a mod can change as mods are added, deleted or sorted. Listing mods will always show their indices, and filtering will retain the index." }
        }
        div("section") {
            div("accent-line") { +"Alternatives" }
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