import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import pages.examples
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

fun main(args: Array<String>) {
    File("out/assets").mkdirs()
    File("out/fonts").mkdirs()

    createPage("index", BODY::home)
    createPage("manual", BODY::manual)
    createPage("examples", BODY::examples)
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
        base { href = "/" }

        meta(content = "IE=edge") { httpEquiv = "X-UA-Compatible" }
        meta("viewport", "width=device-width, initial-scale=1")
        meta("view-transition", "same-origin")

        link("/assets/images/favicon.png", "shortcut icon", "image/png")
        link("/assets/styles.css", "stylesheet")
        link("/assets/styles.css", "stylesheet")
        link("/assets/asciinema-player.css", "stylesheet", type = "text/css")
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
            val newDest = File(dest.absolutePath + "/" + file.nameWithoutExtension).also { if (!it.exists()) it.mkdirs() }
            copyFiles(file, newDest)
        } else {
            val destFile = Path(dest.absolutePath + "/" + file.name)
            Files.copy(file.toPath(), destFile, StandardCopyOption.REPLACE_EXISTING)
        }
    }
}