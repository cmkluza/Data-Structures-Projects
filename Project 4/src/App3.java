class App3{


    public static void main(String args[]){

	// declare instance of EasyIn class
	EasyIn easy = new EasyIn();
        // other variables
	DictionaryBST dict;             //  tree dictionary
	String name;
	long startTime,endTime;

	System.out.println();
	System.out.println("Welcome to the Tree Dictionary App 3");
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

	///////////////////////////////////////////////////////////////////////
	/////////////// extract an array of word by visiting the nodes in-order
	// the array will be sorted

	ArrayWord list=dict.extractArrayInOrder();

        ////////////////////////////////////////////////////////////////////////////////
	/////////////////// Create a collection of BST dictionaries of same word lengths
	///////////////////////////////////////////////////////////////////////////////
	CollectionBST cold=new CollectionBST(dict);
	cold.info();


	//////////////////// Define a list of words to search from the array

	System.out.println("\nHow many random words would you like to search?:");
	int nrnd=easy.readInt();

	/////////////////// Search the BST Dictionary

	startTime = System.currentTimeMillis(); // capture time

	int totalStep=0;

	for (int i=0;i<nrnd;i++)
	    if (cold.search(list.getRandomWord().getWord()))// randomly select a word into array and search it with the BST
		totalStep=totalStep+cold.getStep();


	endTime = System.currentTimeMillis(); // capture time



	/////////////////////// return info

	System.out.println("Ok search done in "+(endTime-startTime)+"ms");
	System.out.println("Average number of step is "+totalStep/nrnd);

	System.out.println("Goodbye!");



    }
}
