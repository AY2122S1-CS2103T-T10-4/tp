---
layout: page
title: Muhammad Faruq's Project Portfolio Page
---

### Project: AniList
AniList is a desktop Command Line Interface application for managing animes that users have watched. AniList is written in Java and the GUI is created with JavaFX.

AniList was created based of AddressBook - Level 3, an address book application used for teaching Software Engineering Principles.

Given below are my contributions to the project.

* **New Feature**: Created new themes and added the functioniality to switch themes.
  * Refactored CSS files to allow creation of themes to be easier for developers.
  * Created 3 new themes beyond the original style provided by AddressBook3.
  * Added hotkeys for theme switching
  * Added `Themes` option in menu bar for theme switching
  * **Justification**: This feature allows the user to really enjoy the usage of AniList as it is aesthetically pleasing and has many themes to fit different preferences.

* **New Feature**: Created tab panels and added functionality for tab switching.
  * Added the UI components for the anime watch status tabs.
  * Added command functionality for tab switching and on click functionality for tab switching.
  * **Justification**: When the list gets large, it gets rather hard to manage the animes in the list. It is a must to have this feature as what is the point of a management application that is not filtered and hard to manage. This allows for the user to easily see which tab they are on and see list they are seeing filtered based on their watch status.
  * **Highlights**: This feature caused quite a fair bit of issues as all the tabs was still showing the same anime list. We decided against having separate lists as that will complicate things further and settled on a single master list. However this meant that the list had to be filtered with every change of tab. This posed an issue as there was a need to connect an `onClick` method in the UI to perform a similar function to a command. This feature affected the implementation of other commands such as `find` as we had to decide if find will find from the current tab or will find from the list of all animes.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=faruq&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=muhammad-faruq&tabRepo=AY2122S1-CS2103T-T10-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)
* **Contributions to UG**:
  * Revamped the UG and created a base format for others to add on to. This includes adding all current available commands and images.
  * Redid the images due to the updated UI.
  * Other small bug fixes.

* **Contributions to DG**:
  * Theme component: wrote the feature explanation, design choices and possible alternatives.
  * Tabs component: wrote the feature explanation, design choices and possible alternatives.

* **Other Contributions**:
  * AniList was very much a team effort. While we all did have our individual components to make, the team was constantly checking on one another and asking for implementations ideas and correctness. While I did the tabs component, other members joined in to give ideas for implementation and we decided as a team the way to go. When faced with issues we are always helping one another when we are free. As I was appointed to be the JavaFX guy, I helped out on most matters pertaining to UI. For example I helped to work on the stats window and the stats command and fix some bugs in that feature. All in all, while some were busy with assignments, others will come and help, when it is then their turn to be busy, those that cleared their assignments will come and help.
  * The team focused heavily on code quality, always trying to ensure best practices are not broken as much as possible. Pull requests are often rejected with changes to be made so as to ensure code quality. The team overall is always carefully checking one another's code to ensure readability, understandability and maintainability. [PR reviews](https://github.com/AY2122S1-CS2103T-T10-4/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3A%40me)
