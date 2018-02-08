class App4{
  
  
    public static void main(String args[]){
    
	// declare instance of EasyIn class
	EasyIn easy = new EasyIn();
        // other variables
	DictionaryBST dict;             //  tree dictionary
	String name;
	long startTime,endTime;

	System.out.println();
	System.out.println("Welcome to the Tree Dictionary App 4");
	System.out.println("====================================");
	System.out.println();
	
	
	//////////////////// Create the dictionary

	dict=new DictionaryBST(); // create BST dictionary
	while (true){
	    System.out.println("Enter dictionary name (english,french,short,tiny,etc.) or return to end:");
	    name = easy.readString();
	    if (name.length()!=0)
		dict.loadDictionary(name); // add to the BST
	    else
		break;
     	}

	dict.info(); // return info
	
		
	//////////////////// Search for Anagrams
	System.out.println("\nEnter word to analyze:");    
	DictionaryBST anagram=dict.createBSTAnagram(easy.readString());
					        	
	if (anagram.getSize()>0)
	    {System.out.println("Great! "+anagram.getSize()+" anagram(s) found");
		anagram.info();
		anagram.displayInOrder();
		anagram.show("word");
	    }
			
	else  System.out.println("Sorry! no anagram(s) found");  


		
    }
}
