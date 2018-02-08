class App3{
  
  
    public static void main(String args[]){
    
	// declare instance of EasyIn class
	EasyIn easy = new EasyIn();
        // other variables
	MovieArray dvdlist;
	String filename;
	int maxsize;
	long startTime,endTime;
	
	System.out.println();
	System.out.println("Welcome to Movie App 3");
	System.out.println("======================\n");
	System.out.println();

 
	//////////////////// Load movie database from command line
        System.out.println("Enter the name of the movie database:");
        filename = easy.readString();
	System.out.println("Enter the max size of the array to store the database:");
        maxsize = easy.readInt();
	
        dvdlist = new MovieArray(filename,maxsize); // initialize database          
        System.out.println("\nSize of database "+dvdlist.getSize());

	////////////// create the hash table
        HashTable htab=new  HashTable(dvdlist);        
        System.out.println("\nOK HashTable created, maxSize is "+htab.getMaxSize());
	System.out.println("it  found "+htab.getCol()+" collisions"+" with "+htab.getMaxProbe()+" maximum probe length");
	
	if (htab.getMaxSize()<=100) { // save the hashtable if small enough
	    htab.saveHashTable(filename+"-hash");
	    System.out.println("HashTable save in file '"+filename+"-hash'");
        }
	

	////////////// Search the database

        /// Reload the array database (may have been erased while creating the hashtable)   
        dvdlist = new MovieArray(filename,maxsize); // initialize database   	
        System.out.println("\nHow many random titles you would like to search?:");
	int nrnd=easy.readInt();

	
	startTime = System.currentTimeMillis(); // capture time
	int totalStep=0;

        for (int i=0;i<nrnd;i++)  if (htab.find(dvdlist.getRandomTitle())) // randomly select a title and search it
				      totalStep=totalStep+htab.getStep();
	endTime = System.currentTimeMillis(); // capture time
	
	System.out.println("\nOk search done in "+(endTime-startTime)+"ms");
	System.out.println("Total number of search step is "+totalStep);
	System.out.println("Average number of search step is "+totalStep/nrnd);		     
	   
	System.out.println("\nGoodbye!");
			 
    }
}
