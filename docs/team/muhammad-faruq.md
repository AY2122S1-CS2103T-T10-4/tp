---
layout: page
title: Muhammad Faruq's Project Portfolio Page
---

### Project: AniList
AniList is a desktop Command Line Interface app for managing animes that the user have watched. AniList is written in Java and the GUI is created with JavaFX.

AniList was created based of AddressBook - Level 3, an address book application used for teaching Software Engineering Principles.

Given below are my contributions to the project.

* **New Feature**: Creation of Themes and Theme switching functionality.
  * **What it does**: allows the user to switch between 4 built-in themes. Theme switching can be done by clicking on theme of choice in menu bar or by using hotkeys.
  * **Justification**: This feature improves the visual aspect and appeal of the application. Given that the application is for tracking of animes, adding anime based themes makes the application more cohesive and overall improve the user experience.
  * **Highlights**: CSS files were all refactored to make the creation of themes easier with minimal changes made to the css files. Adding a background image to a ui panel forced better understanding on css rules.


* **New Feature**: Creation of Tab Panels and Tab switching functionality.
  * **What it does**: allows user to switch between 4 tab options `all`, `watching`, `towatch`, `finished`. Allows the user to have a visual reminder of which tab they are on.
  * **Justification**: This feature significantly improves the usability of the product. The main selling point of this feature is that the user gets a visual reminder on what tab they are on. As find will be compounded on the different tabs, it can get hard to keep track of the watch status that is currently being filtered. By adding a UI component to tab switching also allows for a smoother learning curve for new users.
  * **Highlights**: This enhancement had a number of design considerations. We decided to have one single list for every single tab panel. This meant that there was a need to connect the `onClick` method for the tabs to perform a similar function to the commands. Besides it was unclear what would invoke a re-render for JavaFX which resulted in creative methods to invoke a re-render. This feature also affected the implementation of other commands such as `find` as there was considerations to be made if `find` be compounded on the tab or switch to `all tab`.


* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=faruq&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=muhammad-faruq&tabRepo=AY2122S1-CS2103T-T10-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)


* **Project management**:
  * Managed releases `v1.2` - `v1.4` (3 releases) on GitHub
<div style="page-break-after: always;"></div>

* **Enhancements to existing features**:
  * General refactoring of code to fit it to AniList specification.
  * UI reordering and redesign.


* **Documentation**:
  * User Guide:
    * Revamped the UG overall. Created a base format for the UG for others to work on. [#114](https://github.com/AY2122S1-CS2103T-T10-4/tp/pull/114/commits/ee00bafdbc7a241258c851d885bfe12eb5b85b86)
    * Redo all images in the UG due to UI changes to the application. [#199](https://github.com/AY2122S1-CS2103T-T10-4/tp/pull/199)
    * Rewrote all commands in UG due to the revamp and to ensure a cohesive UG.
    * Cosmetic updates and bug fixes regarding UG.
  * Developer Guide:
    * Theme component: wrote feature explanation, design choices, possible alternatives
    * Tabs component: wrote feature explanation, design choices, possible alternatives
    * Add Themes Feature: Proposed Feature.


* **Community**:
  * PRs reviewed [#193](https://github.com/AY2122S1-CS2103T-T10-4/tp/pull/193), [#50](https://github.com/AY2122S1-CS2103T-T10-4/tp/pull/50) and many more.
  * Reported 11 bugs for other teams during Mock PE.
  * Discussed with team members on implementation details for different features. Provide ideas and insights on the optimal design for the feature.
  * Helped team members with UI matters for the application. Helped with bug fixes for UI related bugs.
