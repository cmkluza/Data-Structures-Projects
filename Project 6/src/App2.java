import java.io.*;
import java.util.*;


////////////////////////////////////////////////////////////////
class App2
{


    public static void main(String[] args)
    {
        EasyIn easy = new EasyIn();
	
	System.out.println("\nWelcome to Graph App 2");
	System.out.println("========================\n");

        
	//Generate a Graph using a 2D Grid
        System.out.println("Enter Total Grid Size x: ");
        int nx=easy.readInt();
        System.out.println("Enter Total Grid Size y: ");
        int ny=easy.readInt();
        Graph theGraph=new Graph(nx,ny); // basic constructor all weights to zero by default
	
        theGraph.form2DGrid(); // connect edges to form an (unweighted) 2D grid
	//Display the graph info
	theGraph.displayInfoGraph();
        // if Nvertex<=9, display the matrix
	if (theGraph.getnVertex()<=9) theGraph.displayAdjMat();

	

	/// Prepare plot
	int n=Math.max(nx,ny);
	StdDraw.setXscale(-1, n);          //  x scale
	StdDraw.setYscale(-1, n);          //  y scale
	
	/// Plot the main graph
	theGraph.plot("GRAY");
	    
        // Perform breadth first searches and create a new graph 
	String node;
	while (true){
	    System.out.println("\nBFS: Enter the starting node number to start the search (press return to stop): ");
	    node=easy.readString();
	    if (node.length()==0) System.exit(0); // Leave the code
	    Graph bfsPath=theGraph.bfs(Integer.parseInt(node));
	    bfsPath.displayInfoGraph(); //Display the graph info
	    StdDraw.clear();  // clear the canvas
	    bfsPath.plot("BLUE");
	}
	
	
    }
}


