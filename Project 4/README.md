# Project 4 - Fun with Trees

### Description
This project familiarized me with operations on binary search trees. Utilizes a similar approach to [Project 1](../Project 1) of loading several words from text files and sorting/searching them.

### Given Files
* Dictionaries
	* [tiny.txt](src/tiny.txt)
	* [short.txt](src/short.txt)
	* [english.txt](src/english.txt)
	* [french.txt](src/french.txt)
	* [spanish.txt](src/spanish.txt)
	* [sample.txt](src/sample.txt)
* [App1.java](src/App1.java)
* [App2.java](src/App2.java)
* [App3.java](src/App3.java)
* [App4.java](src/App4.java)
* [App5.java](src/App5.java)
* [App6.java](src/App6.java)
* [NodeWord.java](src/NodeWord.java)
* [EasyIn.java](src/EasyIn.java)
* [StdDraw.java](src/StdDraw.java)

### Modified Files
* [DictionaryBST.java](src/DictionaryBST.java)
* [CollectionBST.java](src/CollectionBST.java)
* [Word.java](src/Word.java)
* [ArrayWord.java](src/ArrayWord.java)

### Application Descriptions
#### [Dictionary App 1](src/App1.java)
Loads a given dictionary into a BST and, if it's less than 100 words, traverses and displays it in-order and displays it graphically. 

#### [Dictionary App 2](src/App2.java)
Uses in-order traversal to extract an array of sorted words from a BST.

#### [Dictionary App 3](src/App3.java)
Creates a collection of dictionaries of words of the same size and searches for random words in these.

#### [Dictionary App 4](src/App4.java)
Finds anagrams of an input word and creates a new BST from them

#### [Dictionary App 5](src/App5.java)
Creates a "spellchecker" that loads the english, french, and spanish dictionaries and goes through a text file to check if the words match a word from a loaded dictionary.

#### [Dictionary App 6](src/App6.java)
Similar to App 1, loads a dictionary into a BST, then converts the BST to an array representation of a BST, and finally displays the BST graphically where unused/null nodes are in gray.
