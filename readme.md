# Starfield Mod Manager


## Configuration

Get a personal API key at https://www.nexusmods.com/users/myaccount?tab=api%20access

### Dependencies

You'll need to `sudo apt install` them

- `7z` for installing from 7zip files
- `libarchive-tools` for installing rar files


### Setup Vortex Links
- Place `smm.desktop` in `~/.local/share/applications/`.
- Edit the Exec line so it points to your jar install location.
- Edit the Path line so it points at the folder of your jar
- Update `~/.local/share/applications/mimeapps.list` by adding a line `x-scheme-handler/nxm=smm.desktop`
- Make the desktop the default handler `xdg-mime default smm.desktop x-scheme-handler/nxm`

### Configure App
Run `help config` to see how to add your api key, set your game folder, set a folder for plugins.txt and optionally fetch a list of categories
