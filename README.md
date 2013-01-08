NoTime
======

My task management application for Android
This application is ment to help me understand the basics of Android programming. 
Description:
-NoTime should display a list of tasks for the current user, as well as the deadline, in order of their priority.

-Priority is set by user and will be used to determine a task's order in the display list

-The application should display the statistics of the current, completed and failed tasks


-The user can add a link to a folder with relevant files for every task

-The tasks are stored in a SQLite database, accesible by a Content Provider

-The list is loaded asynchrounously to prevent the blocking of the UI



To Do List:

!!!!!CLEAN THE CODE....TRIAL AND ERROR TESTING HAVE MADE IT MESSY


-Clear the row layout of all the useless data displayed just for testing purposes.

-Add a picture to the row layout, so each task looks better in the list.
(probably going to need a custom cursor adapter?)

-Display the remaining time in each row, instead of the deadline.

-Create an activity that displays statistics based on the completed/failed/active tasks

-Make the app compatible with lower versions of Android (<3)..................DONE
(probably going to use the support library and work with fragments).

-Maybe add support for different users.

-Maybe sync it with Google Tasks.


------------------------------------------------------------------------------------------------------
Contact: vladut.alex@gmail.com
