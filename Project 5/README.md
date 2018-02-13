# Project 5 - Fun with Movies

### Description
This project familiarized me with hash tables and advanced sorting techniques, utilizing a database of movie information to be loaded from a file and sorted/searched.

### Given Files
* DVD Lists
	* [dvdlist.csv](src/dvdlist.csv)
	* [dvdlist-sorted.csv](src/dvdlist-sorted.csv)
* [App1.java](src/App1.java)
* [App2.java](src/App2.java)
* [App3.java](src/App3.java)
* [EasyIn.java](src/EasyIn.java)

### Modified Files
* [Movie.java](src/Movie.java)
* [MovieArray.java](src/MovieArray.java)
* [HashTable.java](src/HashTable.java)

### Application Descrptions
#### [App 1](src/App1.java)
Loads an unsorted dvd list into an array and sorts them by name. Sorts using either insertion sort, shellsort, mergesort, quicksort, or heapsort, and saves the sorted list as a new file with a "-sorted" extension.

#### [App 2](src/App2.java)
Loads a sorted dvd list and uses binary search to search for a given number of randomly selected movies. 

#### [App 3](src/App3.java)
Loads a sorted or unsorted dvd list into an array, then converts it into a hashtable and handles collisions with open addressing and double hashing. Saves the hashtable in a new file with the extension "-hash", representing null items with "*****". Then searches for a given number of randomly generated items in the list.
