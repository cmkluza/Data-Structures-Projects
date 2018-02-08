import java.util.Stack;

class App1
{
    public static void main(String args[])
    {
	EasyIn easy = new EasyIn();
        System.out.println("Size of the vector to generate?");
	int n = easy.readInt(); // size
	Vector v1,v2,v3; // vectors

	
	// Define vectors
        v1=new VectorArray(n);
	v2=new VectorArray(n);
	// fill up the vectors 
	v1.fill(); // random
	for (int i=0;i<n;i++){v2.set(i,v1.get(n-1-i));} // fill by hand
	
	// Add vectors v1+v2
        v3=new VectorArray(n);
	for (int i=0;i<n;i++){
	    v3.set(i,v1.get(i)+v2.get(i));
	} 
      
	System.out.println("Vector 1");
        v1.display();
	System.out.println("Vector 2");
	v2.display();
	System.out.println("Vector 1 + Vector 2");
	v3.display();
	System.out.println("Vector normLoo= "+v3.normLoo()+" normL2= "+v3.normL2());
    }
}
