class App6{
  
  
    public static void main(String args[]){
    
	// declare instance of EasyIn class
	EasyIn easy = new EasyIn();
        // other variables
	DictionaryBST dict;             //  dictionary
	String name;

	System.out.println();
	System.out.println("Welcome to the Tree Dictionary App 6");
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

	if (dict.getSize()<=100) {//small dictionary only
	// Convert the BST to an array using in-Order traversal
        ArrayWord array=dict.convertToArrayInOrder();
	
	// Display the non-null components of the array
	System.out.println("\n Array:"); 
	array.displayAll();

	// Plot the user tree using the array
        int Lx=1200;
	int Ly=400;
	array.plotBST(Lx,Ly);
       		
	}

    }
}
