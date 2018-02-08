class App1{
  
  
    public static void main(String args[]){
    
	// declare instance of EasyIn class
	EasyIn easy = new EasyIn();
        // other variables
	MovieArray dvdlist;
	String filename;
	int maxsize;
	long startTime,endTime;
	

	System.out.println();
	System.out.println("Welcome to Movie App 1");
	System.out.println("======================\n");
	System.out.println();

 
	//////////////////// Load movie database from command line
        System.out.println("Enter the name of the movie database:");
        filename = easy.readString();
	System.out.println("Enter the max size of the array to store the database:");
        maxsize = easy.readInt();
	
        dvdlist = new MovieArray(filename,maxsize); // initialize database          
        System.out.println("Size of database "+dvdlist.getSize());


	////////////// Sorting the database
	System.out.println("\nWhich advanced sorting algo? (0: Insertionsort; 1:Shellsort, 2:Mergesort, 3:Quicksort, 4:Heapsort)");
	int sort=easy.readInt(); // ask before starting counting time

	startTime = System.currentTimeMillis(); // capture time
        ///////////// Select the right option
        switch(sort)
            {
	    case 0: // implement insertionsort
                dvdlist.insertionSort();
                break;	
	    case 1: // implement shellsort
                dvdlist.shellSort();
                break;	
            case 2: // implement mergesort
                dvdlist.mergeSort();
                break;
            case 3: // implement quicksort
                dvdlist.quickSort();
                break;
            case 4: // implement heapsort
                dvdlist.heapSort();
                break;
            default:
                System.out.println("Selection Invalid!");
                System.exit(0);
            }
	
	endTime = System.currentTimeMillis(); // capture time
	
	///////////// Save sorted Database
	dvdlist.save("dvdlist.csv-sorted");
	
	System.out.println("\nOK database sorted in "+(endTime-startTime)+"ms and save in file '"+filename+"-sorted'");

	   
	System.out.println("\nGoodbye!");
			 
    }
}
