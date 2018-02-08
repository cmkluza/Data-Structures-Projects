import java.util.Random;

public class Test {
    public static void main(String[] args) {
//        int nx = 4, ny = 4;
//        Heap<Edge> heap = new Heap<>();
//        Random rand = new Random();
//        Vertex start = new Vertex(0, 0,0);
//        for (int i = 0; i < 16; i++) {
//            Vertex v1 = new Vertex(i % nx, i / nx, i);
//            heap.insert(new Edge(start, v1, rand.nextInt(100), false));
//            start = v1;
//        }
//
////        heap.forEach(System.out::println);
//
//        while (!heap.isEmpty()) System.out.println(heap.remove());


//        y = 20;
        StdDraw.setXscale(-1, 9);          //  x scale
        StdDraw.setYscale(-1, 9);          //  y scale
        StdDraw.clear();
        Graph graph = new Graph("grid2");
        Graph mst = graph.mstw();
        mst.plot("BLUE");
        mst.displayInfoGraph();


//        Graph graph = new Graph(4, 5).form2DGrid();


//        Random rand = new Random();
//        Heap<Element> heap = new Heap<>(10);
//        for (int i = 0; i < 10; i++) heap.insert(new Element(rand.nextInt(100)));
//        while (!heap.isEmpty()) {
//            System.out.println(heap.remove());
//        }
    }
}

class Element implements Comparable<Element> {
    private int value;

    public Element(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(Element o) {
        return Integer.compare(this.value, o.value);
    }

    @Override
    public String toString() {
        return "Element{" +
                "value=" + value +
                '}';
    }
}
