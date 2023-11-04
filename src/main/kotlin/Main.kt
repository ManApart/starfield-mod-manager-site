import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import pages.home
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.io.path.Path

fun main(args: Array<String>) {
    File("out/assets").mkdirs()

    createPage("index", "home", "mainPage", BODY::home)

    combineCss()
    copyFiles(File("src/main/resources/assets"), File("out/assets"))
}

private fun createPage(pageName: String, dataPage: String, bodyClass: String, customizer: BODY.() -> Unit) {
    createPage(pageName) {
        htmlWrapper(dataPage, bodyClass, customizer)
    }
}

private fun HTML.htmlWrapper(dataPage: String, bodyClass: String, customizer: BODY.() -> Unit) {
    lang = "en"
    attributes["data-page"] = dataPage
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
    body(bodyClass) {
        customizer()
        footer()
    }
}

private fun createPage(pageName: String, customizer: HTML.() -> Unit) {
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