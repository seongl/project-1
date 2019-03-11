# Pre-work - *ToDoApp*

test
**ToDoApp** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Seong Lee**

Time spent: **5** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items (and display within listview item)
* [X] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] Add status of todo items
* [X] Add delete all functionality
* [X] Swithc to icons instead of a button with description to provide more intuitive guidance
* [X] Add tollbar and grouped all the icons into toolbar
* [X] Gray out invalid action to improve usability e.g. when todo list is empty, the functionalities like detail view, delete, delete all are grayed out
* [X] Add delete all functionality
* [X] Add title/splash page
* [ ] Add functionality to mark the todo item done
* [ ] Show history page for past dates
* [ ] Show graph of done and not done
* [ ] If too many todos are missed, provide an inspiring quote at the startup. Web crawl inspiring quotes on server side and restful call on the startup page
* [ ] Switch to scrollable pane
* [ ] Optimize list view with pagination
* [ ] Add sorting function by due date / by priority using framework
* [ ] Add notification 1 day, 15 min, 5 min and notify at the time
* [ ] Add functionality of copy/duplicate original task for easier modification
* [ ] Add photo in description. Instead of writing long description of what to do, take a picture that will remind you what to do.
* [ ] Integrate with Google voice to create a todo item
* [ ] Add location into description and app calls google map for map and captures driving instruction
* [ ] Automatically kick off google hangout, if it is a meeting schedueld
* [ ] Automatically open up youtube app, if todo item is watching a video at later time
* [ ] Add recurrence field for todo item e.g. weekly, monthly repeated todos
* [ ] Add recommendation service e.g. If you completed 20 sit-ups last time, suggest 30 sit-ups and suggest recommendation video for setup


## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/uBdCrBB.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## License

    Copyright [2017] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
