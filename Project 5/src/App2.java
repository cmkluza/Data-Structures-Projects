class App2{
  
  
    public static void main(String args[]){
    
	// declare instance of EasyIn class
	EasyIn easy = new EasyIn();
        // other variables
	MovieArray dvdlist;
	String filename;
	int maxsize;
	long startTime,endTime;
	

	System.out.println();
	System.out.println("Welcome to Movie App 2");
	System.out.println("======================\n");
	System.out.println();

 
	//////////////////// Load movie database from command line
        System.out.println("Enter the name of the movie *sorted* database:");
        filename = easy.readString();
	System.out.println("Enter the max size of the array to store the database:");
        maxsize = easy.readInt();
	
        dvdlist = new MovieArray(filename,maxsize); // initialize database          
        System.out.println("Size of database "+dvdlist.getSize());


	////////////// Searching the database
        System.out.println("How many random titles you would like to search?:");
	int nrnd=easy.readInt();

	
	startTime = System.currentTimeMillis(); // capture time
	int totalStep=0;

        for (int i=0;i<nrnd;i++)  if (dvdlist.binarySearch(dvdlist.getRandomTitle())) // randomly select a title and search it
				      totalStep=totalStep+dvdlist.getStep();
	
	endTime = System.currentTimeMillis(); // capture time
	
	System.out.println("\nOk search done in "+(endTime-startTime)+"ms");	     
	System.out.println("Total number of search step is "+totalStep);
	System.out.println("Average number of search step is "+totalStep/nrnd);		     
	 
	System.out.println("\nGoodbye!");
			 
    }
}
