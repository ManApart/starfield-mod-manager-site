package pages

import accentLine
import asciCast
import asciCastSetup
import kotlinx.html.*

fun BODY.setup() {
    nav()
    section {
        h1 { +"Setup" }
        p { +"Setup is less intimidating than it seems, especially if you're familiar with BGS modding and linux. Half of these instructions exist simply because there are multiple ways you can set up your system." }
        p { +"Note: This guide assumes a vanilla instance of Starfield is running. This is not a guide on how to run Starfield on linux." }

        p { +"Currently only fully supports premium members. Non Premium members should be able to add files by zip folder or nexus \"download with mod manager links\", but likely won't be able to download mods by pasting in a url or id." }

        asciCastSetup()

        div("section") {
            accentLine("Prerequisites")
            p { +"Make sure you have dependencies installed and a copy of the manager." }
            ul {
                li { a(href = "https://www.nexusmods.com/users/myaccount?tab=api%20access") { +"Grab a personal API key from Nexus" } }

                li {
                    +"Install dependencies, if you don't have them already. You'll need to "
                    code { +"sudo apt install" }
                    +" them"
                    ul {
                        li {
                            code { +"7z" }
                            +" for installing zip and 7zip files"
                        }
                        li {
                            code { +"libarchive-tools" }
                            +" for installing rar files"
                        }
                    }
                }
                li {
                    +"Download "
                    code { +"smm.desktop" }
                    +" and "
                    code { +"starfield-mod-manger.jar" }
                    +" from "
                    a(
                        href = "https://github.com/ManApart/starfield-mod-manager/releases",
                        target = "_blank"
                    ) { +"github" }
                    +" or "
                    a(href = "https://www.nexusmods.com/starfield/mods/6576", target = "_blank") { +"the nexus." }
                    +" Place the jar in a folder where you want to run it from."
                }
            }
        }

        div("section") {
            accentLine("Shortcuts and Vortex Links")

            p { +"This section lets you more easily run the manager, and use the \"Download with Vortex\" buttons on Nexus." }

            ul {
                li {
                    +"Place "
                    code { +"smm.desktop" }
                    +" in "
                    code { +"~/.local/share/applications/" }
                }
                li {
                    +"Edit the Exec line so it points to your jar install location."
                    ul {
                        li {
                            +"Should look something like: "
                            code { +"Exec=sh -c \"java -jar /home/<user>/games/programs/starfield-mod-manager/starfield-mod-manager.jar %U\"" }
                        }
                    }
                }
                li {
                    +"Edit the Path line so it points at the folder of your jar"
                    ul {
                        li {
                            +"Should look something like: "
                            code { +"Path=/home/<user>/games/programs/starfield-mod-manager/" }
                        }
                    }
                }
                li {
                    +"Update "
                    code { +"~/.local/share/applications/mimeapps.list" }
                    +" by adding a line "
                    code { +"x-scheme-handler/nxm=smm.desktop" }
                }
                li {
                    +"Make the desktop shortcut the default handler by running: "
                    code { +"xdg-mime default smm.desktop x-scheme-handler/nxm" }
                }
                li {
                    +"Optional: If you want to run from an existing terminal, add something like "
                    code { +"alias smm='pushd /home/<user>/games/programs/starfield-mod-manager && java -jar starfield-mod-manager.jar; popd'" }
                    +" to your bash aliases. This is how I run the manager in the demos on the site"
                }
            }
        }

        div("section") {
            accentLine("Configure the App")
            p {
                +"Before you can download and deploy mods, you need to configure the app. This config will be saved in "
                code { +"config.json" }
                +" in the folder the jar is in. (Mod data will be stored in another file in that folder)."
            }
            ul {
                li { +"Start the app using one of the methods from the Shortcuts section above" }
                li {
                    code { +"config api-key <key>" }
                    +" using the api key you got from the prerequisites step"
                }
                li {
                    code { +"config game-path <path>" }
                    +" to point to your game folder"
                    ul {
                        li {
                            +"Should look something like: "
                            code { +"<...>/SteamLibrary/steamapps/common/Starfield/" }
                        }
                        li { +"This is used as the target to deploy mods to and for launching the game" }
                    }
                }
                li {
                    code { +"config appdata-path <path>" }
                    +" to point to your local appdata folder"
                    ul {
                        li {
                            +"Should look something like: "
                            code { +"<...>/SteamLibrary/steamapps/compatdata/1716740/pfx/drive_c/users/steamuser/AppData/Local/Starfield/" }
                        }
                        li { +"This is used to deploy the Plugins.txt in order to enable ESMs etc" }
                    }
                }
            }
            asciCast("config", rows = 10, posterTime = "0:02")
        }

        div("section") {
            accentLine("Deploy to Game Folder")
            ul {
                li { +"Uninstall any manually added mod files, or consider doing a clean install of the game" }
                li {
                    +"Using the app, install and enable "
                    a(href = "https://www.nexusmods.com/starfield/mods/106", target = "_blank") { +"SFSE" }
                }
                li {
                    +"For convenient launching of SFSE, both from steam and from the mod manager add: "
                    code { +"bash -c 'exec \"\${@/Starfield.exe/sfse_loader.exe}\"' -- %command%" }
                    +" to your launch options (in steam > game properties > general)"
                }
                li {
                    +"Using the app, install and enable "
                    a(
                        href = "https://www.nexusmods.com/starfield/mods/1599",
                        target = "_blank"
                    ) { +"BAKA Disable My Games Folder" }
                }
                li {
                    +"For mods with plugin files, you'll also want to install and enable "
                    a(
                        href = "https://www.nexusmods.com/starfield/mods/4157",
                        target = "_blank"
                    ) { +"Plugins.txt Enabler" }
                }
                li {
                    +"Make sure to enable mods in your StarfieldCustom.ini (that lives at your ini path). There are many tutorials you can google, but the important part is to create the file if it doesn't exist and add these three lines:"
                    p {
                        code {
                            style = "white-space: pre-line"
                            +"""
                    [Archive]
                    bInvalidateOlderFiles=1
                    sResourceDataDirsFinal=
                    """.trimIndent()
                        }
                    }
                }
                li { +"All features should now work, and mods will be deployed to the game folder" }
            }
        }

        div("section") {
            accentLine("Alternative Deploy to Docs Folder")
            p { +"If you're unable to install SFSE, you can deploy to the docs folder directly, but please note that this is not recommended as it's more error prone and can cause issues" }
            ul {
                li {
                    code { +"config ini-path <path>" }
                    +" to point to your ini (my docs) folder"
                    ul {
                        li {
                            +"Should look something like: "
                            code { +"<...>/SteamLibrary/steamapps/compatdata/1716740/pfx/drive_c/users/steamuser/Documents/My Games/Starfield" }
                        }
                    }
                }
                li {
                    +"In the app run "
                    code { +"config use-my-docs true" }
                }
                li { +"You can always revert the setting later (set it to false) once you have SFSE installed" }
            }
        }

        div("section") {
            accentLine("Installing and Using FOMODs")
            p { +"FOMODs are made to be installed with a gui. Because they rely on images and require the parsing of a non-trivial XML structure, it's not worth the time to support through the terminal." }
            p { +"Instead, FOMODS are called out when you try to enable them, or run the validate command. Installing them, even without a gui, is still simple to do." }
            ul {
                li { +"When trying to enable a FOMOD, the manager will warn you and give you the index of the mod" }
                li {
                    +"Open the mod locally by running  "
                    code { +"local x" }
                    +", with x being the index of the mod."
                }
                li { +"In the file explorer, look at any pictures under the fomod folder, and pick the files you want from the various folders. Consolidate your choices and delete the other folders and files" }
                li { +"Once you have properly structured files (starting with the Data folder), and you've deleted the fomod folder, the manager will be able to enable and deploy the mod" }
            }
        }
        div("section") {
            accentLine("Usage")
            p{+"SMM Works like most mod managers. Each mod is installed to its own private folder that mirrors the game's folder hierarchy. A load order is used to decide plugin precedence and resolve file conflicts. Once mods are enabled, a deployment creates symlinks in the game directory to the individual mod files."}
            p {
                +"Run the app and then use "
                code { +"help" }
                +" to see commands. Alternatively you can look at the "
                a(href = "manual.html") { +"generated man page" }
                +". You can also look through "
                a(href = "features.html") { +"examples." }
            }
            p { +"To reduce typing, most commands take the index of the mod, instead of mod id or name. This means the index of a mod can change as mods are added, deleted or sorted. Listing mods will always show their indices, and filtering will retain the index." }

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
        }
        div("section") {
            accentLine("Creations")
            p {
                +"Starfield Mod Manager now supports Creations and External Mods. (See the "
                a(href = "manual.html#Add") { +"manual" }
                +" for creations)."
            }
            p { +"Creations are downloaded directly into your data folder by Starfield, and managed by the game through a ContentCatalog.txt. SMM can identify and manage Creations. This allows you to enable/disable and set the load order for creations." }
            p { +"External mods are simply plugin files that are in your data folder that you don't want to manage like other mods (get their own data folder), but that should still be managed in plugins.txt. You generally shouldn't need this feature." }
            p {
                +"If you've been using SMM prior to the release of creations, you'll need to upgrade your setup. You should be able to run "
                code { +"validate" }
                +" to see what commands you'll need to run to get things working with the latest version of SMM."
            }
        }
    }
}
