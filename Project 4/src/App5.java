class App5{
  
  
    public static void main(String args[]){
    
	// declare instance of EasyIn class
	EasyIn easy = new EasyIn();
        // other variables
	DictionaryBST dict;             //  tree dictionary
	String name;
	long startTime,endTime;

	System.out.println();
	System.out.println("Welcome to the Tree Dictionary App 5");
	System.out.println("====================================");
	System.out.println();
	
	
	//////////////////// Create the dictionary

	dict=new DictionaryBST(); // create BST dictionary
	dict.loadDictionary("english"); // add to the BST
	dict.loadDictionary("french"); // add to the BST
	dict.loadDictionary("spanish"); // add to the BST


	dict.info(); // return info

	
		
	//////////////////// Spell Checker
	System.out.println("\nEnter Text filename to spellcheck:");
	dict.spellCheckFile(easy.readString());
	       


		
    }
}
