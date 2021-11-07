---
layout: page
title: Ryan Chung's Project Portfolio Page
---

### Project: AniList

AniList is a desktop app for managing anime that the user have watched. The user interacts with it using a CLI, and it has a GUI created with JavaFX.
This project is morphed from AddressBook Level 3, designed for teaching Software Engineering principles.

Given below are my contributions to the project.

* **New Feature**: Added the functionality to update the Episode of an Anime.
  * What it does: Allows the user to update the current Episode of an anime the user is currently watching.
  * Justification: This feature is a basic feature of our application as it helps user to keep track of which Episode they are on.
  * Highlights: This feature was the first of the new features added with some edit capabilities hence it required some work.
  The Episode count is also limited to some number after as to not only combat integer overflow input, but also prevent
  overflowing(shortened with ellipses) in the GUI.

* **New Feature**: Added the functionality to rename an Anime.
  * What it does: Allows the user to change the current name of an anime.
  * Justification: This feature is a basic feature of our application as it is part of the basic edit functionality.
  * Highlights: This required less work as it is similar to UpdateEpisode. However, to combat erroneous inputs that might
  overflow(partially cut) in the GUI, the name is limited to 140 characters maximum.

* **New Feature**: Completely revamped the find feature.
  * What it does: Allows the user to find Anime using search terms.
  * Justification: This feature allows the user to have a more granular find functionality, by supporting searching by Name and Genre
  and search input with spaces.
  * Highlights: This is the most difficult feature I implemented as the behaviour required was different from what AB3 had.
  Furthermore, it is difficult to integrate with other features such as Tab Panels and Tab Switching Functionality.
  * Credits: All team members as they provided some input and suggestions to nudge this feature into its current direction.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=NekrozQliphort&tabRepo=AY2122S1-CS2103T-T10-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Managed releases `v1.1` - `v1.4` (4 releases) on GitHub

* **Enhancements to existing features**:
  * Restrict input format for Name, Episode and Index to avoid erroneous inputs
  (examples: [\#174](https://github.com/AY2122S1-CS2103T-T10-4/tp/issues/174), [\#175](https://github.com/AY2122S1-CS2103T-T10-4/tp/issues/175), PR [\#203](https://github.com/AY2122S1-CS2103T-T10-4/tp/pull/203))

* **Documentation**:
  * User Guide:
    * Added table of contents and commands table (PR [\#114](https://github.com/AY2122S1-CS2103T-T10-4/tp/pull/114))
    * Updated the find functionality in UG (PR [\#213](https://github.com/AY2122S1-CS2103T-T10-4/tp/pull/213))
  * Developer Guide:
    * Added the find functionality to DG (which includes feature explanation, design choices, possible alternatives) (PR [\#213](https://github.com/AY2122S1-CS2103T-T10-4/tp/pull/213))

* **Team tasks contributions**:
  * Fixed various functionality bugs (examples: [\#116](https://github.com/AY2122S1-CS2103T-T10-4/tp/issues/116), PR [\#113](https://github.com/AY2122S1-CS2103T-T10-4/tp/pull/113))

* **Community**:
  * PRs reviewed (with non-trivial review comments): PR [\#57](https://github.com/AY2122S1-CS2103T-T10-4/tp/pull/57), PR [\#77](https://github.com/AY2122S1-CS2103T-T10-4/tp/pull/77), PR [\#196](https://github.com/AY2122S1-CS2103T-T10-4/tp/pull/196)
  * Contributed to forum discussions (examples: [1](https://github.com/nus-cs2103-AY2122S1/forum/issues/31))
