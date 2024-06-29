import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import pages.features
import pages.home
import pages.manual
import pages.setup
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.io.path.Path

val jsonMapper = kotlinx.serialization.json.Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}

fun main() {
    File("out/assets").mkdirs()
    File("out/fonts").mkdirs()

    createPage("index", BODY::home)
    createPage("manual", BODY::manual)
    createPage("features", BODY::features)
    createPage("setup", BODY::setup)

    combineCss()
    copyFiles(File("src/main/resources/assets"), File("out/assets"))
    copyFiles(File("src/main/resources/fonts"), File("out/fonts"))
}

private fun createPage(pageName: String, customizer: BODY.() -> Unit) {
    createHtmlPage(pageName) {
        htmlWrapper(customizer)
    }
}

private fun HTML.htmlWrapper(customizer: BODY.() -> Unit) {
    lang = "en"
    head {
        title("Starfield Mod Manager")

        meta(content = "IE=edge") { httpEquiv = "X-UA-Compatible" }
        meta("viewport", "width=device-width, initial-scale=1")
        meta("view-transition", "same-origin")

        link("assets/favicon.png", "shortcut icon", "image/png")
        link("assets/styles.css", "stylesheet")
        link("assets/styles.css", "stylesheet")
        link("assets/asciinema-player.css", "stylesheet", type = "text/css")
    }
    body {
        customizer()
        footer()
    }
}

private fun createHtmlPage(pageName: String, customizer: HTML.() -> Unit) {
    val text = StringBuffer().appendHTML().html {
        customizer()
    }.toString()
    File("out/$pageName.html").writeText(text)
}

private fun combineCss() {
    val css = File("src/main/resources/css").listFiles()!!.joinToString("\n") { it.readText() }
    File("out/assets/styles.css").writeText(css)
}

private fun copyFiles(source: File, dest: File) {
    source.listFiles()!!.filter { !it.name.startsWith(".") }.forEach { file ->
        if (file.isDirectory) {
            val newDest =
                File(dest.absolutePath + "/" + file.nameWithoutExtension).also { if (!it.exists()) it.mkdirs() }
            copyFiles(file, newDest)
        } else {
            val destFile = Path(dest.absolutePath + "/" + file.name)
            Files.copy(file.toPath(), destFile, StandardCopyOption.REPLACE_EXISTING)
        }
    }
}

fun SECTION.asciCastSetup() {
    p {
        id = "js-warning"
        +"Enable javascript to watch demos"
    }
    script { src = "assets/asciinema-player.min.js" }
    script { unsafe { +"document.getElementById('js-warning').remove();" } }
}

fun DIV.asciCast(
    castName: String,
    startAt: Float = 0f,
    idleTimeLimit: Float = 1f,
    rows: Int? = null,
    posterTime: String? = null
) {
    div("asci-cast") { id = castName }
    script {
        val rowText = rows?.let { ", rows: $it" } ?: ""
        val posterText = posterTime?.let { ", poster: 'npt:$it'" } ?: ""
        val options = """
            {preload: true, startAt: $startAt, idleTimeLimit: $idleTimeLimit$rowText$posterText}
        """.trimIndent()

        unsafe { +"AsciinemaPlayer.create('assets/casts/$castName.cast', document.getElementById('$castName'), $options);" }
    }
}

fun HtmlBlockTag.accentLine(message: String) {
    div("accent-line") {
        id = message
        a(href = "#$message") { +message }
    }
}
