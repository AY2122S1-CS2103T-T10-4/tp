---
layout: page
title: User Guide
---

# AniList V1.3

AniList is a desktop application made to be a single centralized location for anime lovers to keep track of all the animes they are interested in. In AniList, users will be able to keep track of animes that they are watching, wanting to watch or even animes that they have finished. Users will also be able to get statistics on their watching habits such as number of episodes watched and the genre distribution of their current list of animes.

AniList is optimized for use via a Command Line Interface (CLI) while still having the benefits of a customizable Graphical User Interface (GUI). If you are a fast typist, AniList can get your anime management tasks done FASTER than traditional GUI applications!

This User Guide is to be used by any AniList user. No technical background is required.

## Table of Content

- [Quick Start](#quick-start)
- [GUI Breakdown](#gui-breakdown)
- [Hot-Keys](#hot-keys)
- [Commands](#commands)
  * [Adding an anime: `add`](#adding-an-anime-add)
  * [Deleting an anime: `delete`](#deleting-an-anime-delete)
  * [Updating anime episode: `update`](#updating-anime-episode-update)
  * [Updating anime watch status: `status`](#updating-anime-watch-status-status)
  * [Updating anime title: `rename`](#updating-anime-title-rename)
  * [Adding/ Deleting genre from anime: `genre`](#adding-deleting-genre-from-anime-genre)
  * [List all supported genres: `genrelist`](#list-all-supported-genres-genrelist)
  * [Listing anime based on watch status: `list`](#listing-anime-based-on-watch-status-list)
  * [Clearing all animes currently displayed: `clear`](#clearing-all-animes-currently-displayed-clear)
  * [Finding an anime: `find`](#finding-an-anime-find)
  * [View user statistics: `stats`](#view-user-statistics-stats)
  * [Viewing all supported commands: `help`](#viewing-all-supported-commands-help)
- [Commands Table](#commands-table)
- [Glossary](#glossary)




## Quick Start
1. Ensure you have `Java 11` or later installed on your computer. ([install here](https://java.com/en/download/help/download_options.html))

2. Download the latest `anilist.jar` release [here](https://github.com/AY2122S1-CS2103T-T10-4/tp/releases).

3. Place the `anilist.jar` file into your desired *home folder* for AniList.

4. Double-click to launch the application. A window similar to the one shown below should appear shortly.

5. On first launch, AniList will contain sample anime data which you could play around with. Once you are done and
   wish to clear the sample data, enter `list` to [list](#listing-anime-based-on-watch-status-list) all sample animes,
   and then use the [clear](#clearing-all-animes-currently-displayed-clear) command to delete them.



---

![](images/UG-images/base.png)

___

## GUI Breakdown

![](images/UG-images/gui.PNG)

1. **Menu Bar:** Here the user have the option to Exit the application or to change the application theme.

2. **Tabs Bar:** Here the user can change the tabs to view their anime list filtered based on their watch status. (watching, towatch, finished)

3. **Anime List Panel:** The panel where all of the user's animes are listed. The order of listing is based on the order in which each anime was added —
the latest to be added appears last in the list.

4. **Save Location:** States the location in which the anime list file is saved, with respect to the location of the `anilist.jar file`.

5. **Command Result Panel:** This panel shows the result of the command based on user input in the Command Input Box.

6. **Command Input Box:** User inputs the desired command in this box and runs the command by pressing `enter`.

## Hot-Keys

AniList currently supports some hotkeys to allow for ease of use for CLI users.

* **Theme Switching:** `CTRL-T`. This will cycle through all the available themes.

Charlotte Theme            |  Dark Theme
:-------------------------:|:-------------------------:
![](images/UG-images/charlottetheme.png)  |  ![](images/UG-images/darktheme.png)

Squid Girl Theme           |  Wonder Egg Priority Theme
:-------------------------:|:-------------------------:
![](images/UG-images/squidtheme.png)  |  ![](images/UG-images/eggtheme.png)

___

* **Tab Switching:** `CTRL-S` and `CTRL-D`. To cycle through the tabs to the right, use `CTRL-D`. To cycle through the tabs to the left, use `CTRL-S`.


All Tab  |  Watching Tab
:-------------------------:|:-------------------------:
![](images/UG-images/all.png)  |  ![](images/UG-images/watching.png)

To Watch Tab           |  Finished Tab
:-------------------------:|:-------------------------:
![](images/UG-images/towatch.png)  |  ![](images/UG-images/finished.png)


___
## Commands

<div markdown="block" class="alert alert-info">

**Notes about command format:**
* Words in UPPER_CASE are the specifications given by the user.
  e.g. in `add n/NAME`, NAME is the specification which can be used in in the form `add n/Doraemon`

* Items in square brackets are optional
  e.g. `add n/NAME [e/EPISODE]`. Both `add n/Naruto` and `add n/Naruto e/27` are considered valid commands.

* Commands are case-sensitive (TODO refine later)
  e.g. `list` and `LIST` are registered differently. Entering `list` successfully lists all animes but
  entering `LIST` registers as an unknown command.

* Leading and trailing whitespace is ignored
  TODO add example

</div>

### Adding an anime: `add`

Adds a user defined anime into the anime list.
Format: `add n/NAME [e/EPISODE] [s/STATUS] [g/GENRE] [g/GENRE] ...`

Note: If multiple `n/NAME` is given in the command, the title of the added anime will be the last provided `NAME`

Parameters:
* [`NAME`](#name)
* [`EPISODE`](#episode)
* [`STATUS`](#status)
* [`GENRE`](#genre)


<div markdown="block" class="alert alert-info">

Example usages:
* `add n/Shingeki no Kyojin`
* `add n/Shingeki no Kyojin e/2`
* `add n/Shingeki no Kyojin s/w`
* `add n/Shingeki no Kyojin e/2 s/w g/fantasy`
* `add n/Shingeki no Kyojin e/2 s/w g/fantasy g/action g/comedy`

</div>

![](images/UG-images/addcommand.png)

1. Details of the added anime is shown in the Command Result Panel.
2. The new added anime is added to the list in the Anime List Panel.
___

### Deleting an anime: `delete`

Deletes the user specified anime from the anime list if it exists.
Format: `delete INDEX`

Parameters:
* [`INDEX`](#index)

<div markdown="block" class="alert alert-info">

Example usages:
* `delete 1` deletes the first anime shown in the list.
* `delete 5` deletes the fifth anime shown on the list.

</div>

![](images/UG-images/delete.png)
1. Details of the deleted anime is shown in the Command Results Panel.
___

### Updating anime episode: `update`

Updates the episode that you are currently on for the specified anime.
Format: `update INDEX e/EPISODE`

Parameters:
* [`INDEX`](#index)
* [`EPISODE`](#episode)

<div markdown="block" class="alert alert-info">

Example usages:
* `update 1 e/3` updates the first anime to episode 3.
* `update 5 e/27` updates the fifth anime to episode 27.

</div>

![](images/UG-images/updatepisode.png)
1. Details of the updated anime is shown in the Command Results Panel.
2. The episode number of the chosen anime is updated to the specified episode number.
___

### Updating anime watch status: `status`

Updates the watch status of a specified anime.
Format: `status INDEX s/STATUS`


Parameters:
* [`INDEX`](#index)
* [`STATUS`](#status)

<div markdown="block" class="alert alert-info">

Example usages:
* `status 1 s/finished` updates the first anime to watch status `finished`.
* `status 5 s/watching` updates the fifth anime to watch status `watching`.
* `status 3 s/t` updates the third anime to watch status `towatch`

</div>

![](images/UG-images/updatestatus.png)
1. Details of the updated anime is shown in the Command Results Panel.
2. The watch status of the chosen anime is updated to the specified watch status.
___

### Updating anime title: `rename`
Updates the anime title for the user specified anime.
Format: `rename INDEX n/NAME`

Parameters:
* [`INDEX`](#index)
* [`NAME`](#name)

<div markdown="block" class="alert alert-info">

Example usages:
* `rename 1 n/Narutoo` updates the first anime title to Narutoo.
* `rename 1 n/Naruto: Shippuden` updates the first anime title to Naruto: Shippuden.

</div>

![](images/UG-images/rename.png)
1. Details of the updated anime is shown in the Command Results Panel.
2. The name of the chosen anime is updated to the specified name.
___


### Adding/ Deleting genre from anime: `genre`

Adds or deletes genre(s) from a specified anime<br>
Format: `genre INDEX c/ACTION g/GENRE [g/GENRE]`

* Duplicate genres are not allowed
* You need to provide at least 1 `GENRE`

Parameters:
* [`INDEX`](#index)
* [`ACTION`](#action)
* [`GENRE`](#genre)

<div markdown="block" class="alert alert-info">

Example usages:
*  `genre 1 c/add g/shounen g/medieval fantasy g/isekai`
*  `genre 1 c/delete g/shounen g/medieval fantasy g/isekai`

</div>

![](images/UG-images/genreadd.png)
1. Details of the updated anime is shown in the Command Results Panel.
2. The genres tagged to the chosen anime is updated based on the addition or deletion of genre.
___


### List all supported genres: `genrelist`

Lists all currently supported genres on the Command Results panel.
Format: `genrelist`

<div markdown="block" class="alert alert-info">

Example usages:
* `genrelist` displays all currently supported genres of AniList

</div>

![](images/UG-images/genrelist.png)
1. The list of all available genres is shown in the Command Results Panel.
___


### Listing anime based on watch status: `list`
Displays a list of animes that has been previously entered by the user based on their watch status.<br>
Format: `list [s/STATUS]`

* If no `STATUS` is given, all animes will be listed.

Parameters:
* [`STATUS`](#status)


<div markdown="block" class="alert alert-info">

Example usages:
* `list` displays all the anime in the list panel.
* `list s/watching` displays all the anime with the watch status `watching`
* `list s/finished` displays all the anime with the watch status `finished`
* `list s/t` displays all the anime with the watch status `towatch`

</div>

![](images/UG-images/list.png)
1. The number of anime(s) listed in the current tab is shown in the Command Results Panel.
2. The tab bar changes to show the current tab the user is on.
___

### Clearing all animes currently displayed: `clear`

Removes all anime(s) currently displayed on screen, upon user confirmation <br>
Format: `clear`

* Only animes currently listed will be removed from the application
* User will be prompted with a confirmation message, and can proceed to clear all displayed animes by entering 'clear' again
* Entering anything else will cancel the command

<div markdown="block" class="alert alert-info">

Example usages:
* `clear` on the initial clear command prompts the user to confirm the command.
* `clear` on follow-up clear command deletes all anime data currently displayed.

</div>

![](images/UG-images/clear.png)
1. A success message showing all anime(s) has been cleared is shown on the Command Results Panel.
2. The Anime List Panel is empty as all anime(s) have been cleared.
___

### Finding an anime: `find`

Finds all anime(s) that match specified keyword(s) in their names or genres
(case-insensitive) and displays them as a list with index numbers<br>
Format: `find [n/NAME KEYWORD]... [g/GENRE KEYWORD]...`

* `NAME KEYWORD` refers to the name keyword(s) you are searching for
* `GENRE KEYWORD` refers to the genre keyword(s) you are searching for
* `find` has to be followed by at least one search term

Parameters:
* `NAME KEYWORD`
* `GENRE KEYWORD`

<div markdown="block" class="alert alert-info">

Example Usages:
* `find n/boku no n/hero g/comedy` Finds all anime with "boku no" or "hero" in their name and
all anime with comedy genre.
</div>

**ADD IMAGE HERE**

___

### View user statistics: `stats`

Displays a pop-up window that shows the statistical breakdown of anime(s) in AniList
Format: `stats`

**ADD IMAGE HERE**
___

### Viewing all supported commands: `help`
Lists all currently supported commands on the Command Results panel.
Format: `help`

<div markdown="block" class="alert alert-info">

Example usages:
* `help` displays all currently supported commands of AniList

</div>

![](images/UG-images/help.png)
1. A list of all supported commands is shown in the Command Results Panel.
2. A link to the User Guide is provided.
___

## Commands Table

| Command | Usage |
| :--------: | -------- |
| [add](#adding-an-anime-add)     | `add n/NAME [e/EPISODE] [s/STATUS] [g/GENRE1] [g/GENRE2] ...` |
| [delete](#deleting-an-anime-delete)     | `delete INDEX` |
| [update](#updating-anime-episode-update)     | `update INDEX e/NEWEPISODE` |
| [status](#updating-anime-watch-status-status)     | `status INDEX s/UPDATEDSTATUS` |
| [rename](#updating-anime-title-rename)     | `rename INDEX n/NEWTITLE` |
| [genre](#adding-deleting-genre-from-anime-genre)     | `genre INDEX c/ACTION g/GENRE [g/GENRE]` |
| [genrelist](#list-all-supported-genres-genrelist) | `genrelist` |
| [list](#listing-anime-based-on-watch-status-list)     | `list [s/STATUS]` |
| [clear](#clearing-all-animes-currently-displayed-clear)    | `clear` |
| [find](#finding-an-anime-find)  | `find [n/NAME KEYWORD]... [g/GENRE KEYWORD]...` |
| [stats](#view-user-statistics-stats)  | `stats` |
| [help](#viewing-all-supported-commands-help)     | `help` |

## Glossary

### NAME
*  Refers to the anime title

### EPISODE
*  Refers to the latest episode watched for the anime
*  Must be a non-negative integer `0, 1, 2 ...`

### STATUS
*  Refers to the watch status for the anime.
*  In the form of `towatch`, `watching`, `finished`, with `t`, `w`, `f`, as their short forms.

### GENRE
*  Refers to the genre to which the animes belong to
*  Must be from the list of available genres found in [genrelist](#list-of-avaible-genres)
*  Any number of genres can be provided for a single anime
*  Can only contain alphabets and spaces. Two or more consecutive spaces are not allowed

### INDEX
*  Refers to the position of the Anime as shown in the displayed list
*  The `INDEX` provided must be a positive integer `1, 2, 3...`

### ACTION
*  Specifies additional information for the command
*  Current list of available actions: `add`, `delete`

## List of Avaible Genres
These are the current list of available Genres in the genrelist:
*  Action
*  Adventure
*  Comedy
*  Drama
*  Fantasy
*  Horror
*  Magic
*  Mystery
*  Psychological
*  Romance
*  Sci Fi
*  Slice of Life");
*  Sports
*  Supernatural
