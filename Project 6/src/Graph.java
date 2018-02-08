import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

////////////////////////////////////////////////////////////////
class Edge implements Comparable<Edge> {
    Vertex start;
    Vertex end;
    int weight;
    boolean directed;

    public Edge(Vertex start, Vertex end, int weight, boolean directed) {
        this.start = start;
        this.end = end;
        this.weight = weight;
        this.directed = directed;
    }

    // whether two edges are equals - requires same direction and weight
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Edge edge = (Edge) other;

        // if directed, requires same weight and edge start and end edges
        if (directed) {
            return weight == edge.weight && start.equals(edge.start) && end.equals(edge.end);
        } else {
            // undirected; requires same weight, and any two matching edges
            return weight == edge.weight && (start.equals(edge.start) || start.equals(edge.end))
                    && (end.equals(edge.start) || end.equals(edge.end));
        }
    }

    @Override
    public String toString() {
        if (!directed) {
            return start.toString() + " <--> " + end.toString() + "\t" + weight;
        } else {
            return start.toString() + " --> " + end.toString() + "\t" + weight;
        }
    }

    // used for sorting
    @Override
    public int compareTo(Edge other) {
        if (this.equals(other)) return 0;
        return Integer.compare(this.weight, other.weight);
    }

    public int compareStarts(Edge other) {
        if (this.equals(other)) return 0;
        return this.start.compareTo(other.start);
    }


}  // end class Edge


////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////


class Vertex implements Comparable<Vertex> {
    int i; // x coordinate
    int j; // y coordinate
    int index; // index
    boolean visited = false; // whether this node has been visited; starts as false
    List<Edge> edges = new ArrayList<>(); // connecting edges

    // default constructor
    public Vertex(int i, int j, int index) {
        this.i = i;
        this.j = j;
        this.index = index;
    }

    // adds an edge between two vertices
    public List<Edge> addEdge(Vertex other, int weight, boolean directed) {
        List<Edge> edges = new ArrayList<>(2); // the edge(s) to be returned
        if (directed) {
            // directed - add edges from this to other vertex unless the edge is already contained
            Edge edge = new Edge(this, other, weight, true);
            if (!this.edges.contains(edge)) {
                this.edges.add(edge);
                edges.add(edge);
            }
            edge = new Edge(other, this, weight, true);
            if (!other.edges.contains(edge)) {
                other.edges.add(edge);
                edges.add(edge);
            }
        } else {
            // undirected - add one edge to both vertices unless edge is already contained
            Edge edge = new Edge(this, other, weight, false);
            int i = 0;
            if (!this.edges.contains(edge)) {
                this.edges.add(edge);
                i++;
            }
            if (!other.edges.contains(edge)) {
                other.edges.add(edge);
                i++;
            }
            if (i == 2) {
                edges.add(edge);
            }
        }
        return edges;
    }

    // adds an edge to this vertex
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    // whether two vertices are equal
    public boolean equals(Vertex other) {
        // equality is based only on position, not edges
        return (this.i == other.i) && (this.j == other.j);
    }

    @Override
    public String toString() {
        return "(" + i + "," + j + ")";
    }

    // used for sorting
    @Override
    public int compareTo(Vertex o) {
        return Integer.compare(this.index, o.index);
    }
}  // end class Vertex


////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////


class Graph {
    /* =============== VARIABLES =============== */
    public Vertex nodes[]; // list of vertices
    private List<Edge> edges; // using a set ensures edges are unique (i.e. no repeats)
    private int adjMat[][];      // adjacency matrix
    private int nVertex;          // Number of vertices/nodes
    private int nx, ny; // height and length of graph

    /* =============== CONSTRUCTOR =============== */
    // default constructor
    public Graph(int nx, int ny) {
        this.nx = nx;
        this.ny = ny;
        // create nVertex nodes and give them appropriate indices
        nodes = new Vertex[nx * ny];
        for (int i = 0; i < nx * ny; i++) {
            // TODO: changed i / ny to y / nx; see if this causes problems later
            nodes[i] = new Vertex(i % nx, i / nx, i);
        }
        // set all values in the adjMat to 0
        adjMat = new int[nodes.length][nodes.length];
        for (int[] arr : adjMat) Arrays.fill(arr, 0);
        // set the number of vertices
        nVertex = nx * ny;
        // initiate edges
        edges = new ArrayList<>(nodes.length);
    }

    // constructor that reads input from a file
    public Graph(String fileName) {
        // send an error if the file doesn't exist
        if (!Files.exists(Paths.get(fileName))) {
            System.err.println("No file called " + fileName + " was found! Graph not created.");
            return;
        }

        // use a buffered reader to get input
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            // first line is nx nx
            String[] nxNy = reader.readLine().split(" ");
            nx = Integer.parseInt(nxNy[0]);
            ny = Integer.parseInt(nxNy[1]);
            // setup variables not dependent on edges/vertices
            nVertex = nx * ny;
            nodes = new Vertex[nx * ny];
            edges = new ArrayList<>(nodes.length);
            adjMat = new int[nodes.length][nodes.length];
            for (int[] arr : adjMat) Arrays.fill(arr, 0);

            // read each line, skipping the first
            reader.lines().skip(0).forEach(line -> {
                // split the line
                String[] args = line.split(" ");
                // args must have 3 elements
                if (args.length >= 3) {
                    // get the data
                    int vertIndex1 = Integer.parseInt(args[0]);
                    int vertIndex2 = Integer.parseInt(args[1]);
                    int weight = Integer.parseInt(args[2]);
                    // create the vertices and edges
                    Vertex vertex1 = new Vertex(vertIndex1 % nx, vertIndex1 / nx, vertIndex1);
                    Vertex vertex2 = new Vertex(vertIndex2 % nx, vertIndex2 / nx, vertIndex2);
                    Edge edge = new Edge(vertex1, vertex2, weight, false);
                    // add vertices to graph if they're not already there
                    if (nodes[vertIndex1] == null) {
                        nodes[vertIndex1] = vertex1;
                    }
                    if (nodes[vertIndex2] == null) {
                        nodes[vertIndex2] = vertex2;
                    }
                    // add edge to vertices and edge list
                    nodes[vertIndex1].addEdge(edge);
                    nodes[vertIndex2].addEdge(edge);
                    addEdge(edge);
                    // add to adjMat
                    adjMat[vertIndex1][vertIndex2] = weight;
                    adjMat[vertIndex2][vertIndex1] = weight;
                }
            });

            // if there are null nodes, fill them in with no edges
            for (int i = 0; i < nVertex; i++) {
                if (nodes[i] == null) {
                    nodes[i] = new Vertex(i % nx, i / nx, i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* =============== PUBLIC METHODS =============== */

    // connects each vertex to its neighboring vertices (top, bottom, left, right)
    public Graph form2DGrid() {
        // go through each vertex
        for (int i = 0; i < nodes.length; i++) {
            Vertex current = nodes[i]; // current vertex
            int top, bottom, left, right; // indices of each of the neighboring vertices
            // get each of the neighboring vertex indices
            top = getIndexFromCoords(current.i, current.j - 1);
            bottom = getIndexFromCoords(current.i, current.j + 1);
            left = getIndexFromCoords(current.i - 1, current.j);
            right = getIndexFromCoords(current.i + 1, current.j);
            // add edge only if index is valid (index is set to -1 if out of bounds)
            // also add resulting edges to edge list
            if (top != -1) {
                this.addEdges(current.addEdge(nodes[top], 1, false));
            }
            if (bottom != -1) {
                this.addEdges(current.addEdge(nodes[bottom], 1, false));
            }
            if (left != -1) {
                this.addEdges(current.addEdge(nodes[left], 1, false));
            }
            if (right != -1) {
                this.addEdges(current.addEdge(nodes[right], 1, false));
            }
            // add edges to adjacency matrix
            addEdgesToAdjMat(i);
        }
        // returns a copy of this graph; useful for chaining with creation of new graph
        return this;
    }

    // provides information about the graph
    public void displayInfoGraph() {
        // string builder to create data that will be printed out
        StringBuilder sb = new StringBuilder("List of edges + weight:\n");
        int totalWeight = 0;
        // add relevant data for each edge and add total weight
        for (Edge edge : edges) {
            sb.append(edge).append("\n");
            totalWeight += edge.weight;
        }
        sb.append("Total weight: ").append(totalWeight);
        System.out.println(sb.toString());
    }

    // displays the adjacency matrix
    public void displayAdjMat() {
        StringBuilder sb = new StringBuilder("Matrix:\n");
        // loop through all values in the adjacency matrix
        for (int i = 0; i < adjMat.length; i++) {
            sb.append("\t");
            for (int j = 0; j < adjMat[i].length; j++) {
                sb.append(adjMat[i][j]).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    // creates a minimum spanning tree with the depth-first search method
    public Graph dfs(int startIndex) {
        // make sure index is within acceptable bounds
        if (startIndex < 0 || startIndex >= nVertex) {
            System.err.println("Error: \"" + startIndex + "\" is outside of the acceptable range (0," + (nVertex - 1) + ")." +
                    " Returning a 2D grid.");
            return new Graph(nx, ny).form2DGrid();
        }

        Graph mst = new Graph(nx, ny); // the graph that will contain the minimum spanning tree
        Stack stack = new Stack(nVertex); // stack to hold vertices in while performing DFS
        Vertex current = mst.nodes[startIndex]; // start at given node
        Vertex previous; // the previous node; used for creating edges

        // visit the first node
        current.visited = true;
        // push current onto the stack
        stack.push(current);

        // continue until stack is empty
        while (!stack.isEmpty()) {
            // note current node
            previous = current;
            // get next adjacent, unvisited node
            current = mst.getAdjUnvisitedNode(current);
            // if null, that means no adjacent, unvisited nodes found; pop one from the stack, update previous
            if (current == null) {
                current = (Vertex) stack.pop();
            } else {
                // if previous is non-null, add edges to the edge list in the appropriate location,
                // and set adjacency matrix
                addMstEdge(mst, current, previous);
                // set current as visited and add to the stack
                current.visited = true;
                stack.push(current);
            }
        }

        // reset visited status
        mst.resetVisitedStatus();

        return mst;
    }

    // creates a minimum spanning tree with the breadth-first search method
    public Graph bfs(int startIndex) {
        // make sure index is within acceptable bounds
        if (startIndex < 0 || startIndex >= nVertex) {
            System.err.println("Error: \"" + startIndex + "\" is outside of the acceptable range (0," + (nVertex - 1) + ")." +
                    " Returning a 2D grid.");
            return new Graph(nx, ny).form2DGrid();
        }

        Graph mst = new Graph(nx, ny); // graph containing the minimum spanning tree
        Queue queue = new Queue(nVertex); // queue used for the search
        Vertex current = mst.nodes[startIndex]; // current node
        Vertex previous; // previous node

        // visit the first node
        current.visited = true;
        // insert current into the queue
        queue.enqueue(current);

        // continue until queue is empty
        while (!queue.isEmpty()) {
            // note current node
            previous = (Vertex) queue.dequeue();
            // go through each adjacent vertex
            while (true) {
                current = mst.getAdjUnvisitedNode(previous); // get next vertex
                if (current == null) break; // stop if there's no more unvisited adjacent vertices
                // visit the current node
                current.visited = true;
                // add the edge to the graph
                addMstEdge(mst, current, previous);
                // enqueue the edge
                queue.enqueue(current);
            }
        }

        // reset visited status
        mst.resetVisitedStatus();

        return mst;
    }

    // creates a minimum spanning tree with Prim's algorithm
    public Graph mstw() {
        Graph mstw = new Graph(nx, ny); // the graph that will hold the result
        Heap<Edge> edges = new Heap<>(); // heap to hold edges
        int current = 0, previous; // index of current element; start at root
        int vertices = 0; // number of vertices in the MST

        this.resetVisitedStatus(); // reset at "visited" values

        // loop through all vertices
        while (vertices < nVertex - 1) {
            // visit the current node
            nodes[current].visited = true;

            // go through each vertex, looking for adjacent, unvisited vertices
            for (int i = 0; i < nVertex; i++) {
                if (i == current) continue; // ignore repeats
                if (nodes[i].visited) continue; // ignore values already visited
                if (adjMat[current][i] == 0) continue; // ignore distances of zero

                // make the edge to be inserted to the heap
                Edge edge = new Edge(this.nodes[current], this.nodes[i], adjMat[current][i], false);

                // loop through the edges in the heap and if there are any with the same destination and a larger
                // weight, replace is
                for (int j = 0; j < edges.size(); j++) {
                    if (edges.nodes.get(j).end.equals(edge.end)) {
                        if (edge.weight < edges.nodes.get(j).weight) {
                            edges.nodes.set(j, edge);
                        }
                    }
                }

                // put the edge in the heap
                edges.insert(edge);
            }

            // if the heap empties before we're done, the graph isn't connected, simply return "this"
            if (edges.isEmpty()) {
                System.err.println("Error - the graph is not connected! Returning unmodified tree.");
                return this;
            }

            // get shortest edge
            Edge edge = edges.remove();
            previous = edge.start.index; // record index of previous element
            current = edge.end.index; // record index of next element

            // only increment and set values if one of the nodes was unvisited before
            if (!(this.nodes[previous].visited && this.nodes[current].visited)) {
                // otherwise, increment the number of vertices in the tree and set appropriate data
                mstw.addEdge(edge); // add edge to the MST
                mstw.adjMat[previous][current] = edge.weight; // set values in adjacency matrix
                mstw.adjMat[current][previous] = edge.weight;
                vertices++; // increment
            }
        }

        this.resetVisitedStatus(); // reset visited status for these nodes

        return mstw;
    }

    // getter for nVertex
    public int getnVertex() {
        return nVertex;
    }

    // getter for nx
    public int getNx() {
        return nx;
    }

    // getter for ny
    public int getNy() {
        return ny;
    }

    //// Plot the Graph using the StdDraw.java library
    public void plot(String color) {

        if (color.equals("BLUE"))
            StdDraw.setPenColor(StdDraw.BLUE);  // change pen color
        else if (color.equals("GRAY"))
            StdDraw.setPenColor(StdDraw.GRAY);  // change pen color
        else if (color.equals("RED"))
            StdDraw.setPenColor(StdDraw.RED);  // change pen color

        for (int i = 0; i < nVertex; i++) {
            for (int j = 0; j <= i; j++) {
                if (adjMat[i][j] != 0) {
                    StdDraw.setPenRadius(adjMat[i][j] * adjMat[i][j] * 0.0025);
                    StdDraw.filledCircle(nodes[i].i, nodes[i].j, 0.25);  // plot node
                    StdDraw.filledCircle(nodes[j].i, nodes[j].j, 0.25);  // plot node
                    StdDraw.line(nodes[i].i, nodes[i].j, nodes[j].i, nodes[j].j); //plot edges
                }
            }
        }
    }

    /* =============== PRIVATE METHODS =============== */

    // adds all adjacent edges for given vertex index
    private void addEdgesToAdjMat(int index) {
        // get all adjacent edges
        int top, bottom, left, right; // indices of each of the neighboring vertices
        // get each of the neighboring vertex indices
        top = getAdjacentIndex(nodes[index], Direction.ABOVE);
        bottom = getAdjacentIndex(nodes[index], Direction.BELOW);
        left = getAdjacentIndex(nodes[index], Direction.LEFT);
        right = getAdjacentIndex(nodes[index], Direction.RIGHT);
        // if valid, set the two values in the adjacency matrix to 1
        if (top != -1) {
            adjMat[index][top] = 1;
            adjMat[top][index] = 1;
        }
        if (bottom != -1) {
            adjMat[index][bottom] = 1;
            adjMat[bottom][index] = 1;
        }
        if (left != -1) {
            adjMat[index][left] = 1;
            adjMat[left][index] = 1;
        }
        if (right != -1) {
            adjMat[index][right] = 1;
            adjMat[right][index] = 1;
        }
    }

    // gets the index for a specific adjacent matrix
    private int getAdjacentIndex(Vertex vertex, Direction dir) {
        int x, y;
        switch (dir) {
            case ABOVE:
                x = vertex.i;
                y = vertex.j - 1;
                break;
            case BELOW:
                x = vertex.i;
                y = vertex.j + 1;
                break;
            case LEFT:
                x = vertex.i - 1;
                y = vertex.j;
                break;
            case RIGHT:
                x = vertex.i + 1;
                y = vertex.j;
                break;
            default:
                // should never happen
                x = -1;
                y = -1;
        }
        return getIndexFromCoords(x, y);
    }

    // gets the index for given x, y coords
    private int getIndexFromCoords(int x, int y) {
        // check out of bounds
        if (x < 0 || x >= nx) return -1;
        if (y < 0 || y >= ny) return -1;
        return y * nx + x;
    }

    // finds the next unvisited adjacent node
    private Vertex getAdjUnvisitedNode(Vertex current) {
        int index; // the index of the first unvisited adjacent node
        // try above
        index = getAdjacentIndex(current, Direction.ABOVE);
        // if valid AND unvisited, return it
        if (index != -1 && !nodes[index].visited) return nodes[index];
        // try below
        index = getAdjacentIndex(current, Direction.BELOW);
        // if valid AND unvisited, return it
        if (index != -1 && !nodes[index].visited) return nodes[index];
        // try left
        index = getAdjacentIndex(current, Direction.LEFT);
        // if valid AND unvisited, return it
        if (index != -1 && !nodes[index].visited) return nodes[index];
        // try right
        index = getAdjacentIndex(current, Direction.RIGHT);
        // if valid AND unvisited, return it
        if (index != -1 && !nodes[index].visited) return nodes[index];

        // if here, no adjacent, unvisited nodes found, return null
        return null;
    }

    // resets all nodes to unvisited
    private void resetVisitedStatus() {
        for (Vertex vertex : nodes) {
            vertex.visited = false;
        }
    }

    // adds edges to the edge list unless they're already contained
    private void addEdges(Collection<Edge> edges) {
        for (Edge edge : edges) {
            addEdge(edge);
        }
    }

    // adds a single edge to the edge list
    private boolean addEdge(Edge edge) {
        if (!this.edges.contains(edge)) {
            // insert the edge at the appropriate location
            int i = 0;
            for (; i < this.edges.size(); i++) {
                if (this.edges.get(i).compareStarts(edge) >= 0) break;
            }
            this.edges.add(i, edge);
            return true;
        } else {
            return false;
        }
    }

    // adds an edge to the mst
    private void addMstEdge(Graph mst, Vertex current, Vertex previous) {
        if (previous != null) {
            Edge edge = new Edge(previous, current, 1, false);
            int i = 0;
            for (; i < mst.edges.size(); i++) {
                if (mst.edges.get(i).compareStarts(edge) >= 0) break;
            }
            mst.edges.add(i, edge);
            mst.adjMat[current.index][previous.index] = 1;
            mst.adjMat[previous.index][current.index] = 1;
        }
    }

    // gets the minimum weighted edge that connects to an unvisited node
    private Edge getMinUnvisitedEdge(Collection<Edge> edges) {
        Edge minUnvisited = null; // null if no other edges found
        for (Edge edge : edges) {
            // if both vertices are visited, continue
            if (edge.start.visited && edge.end.visited) continue;
            // if less than current min, replace current min
            if (minUnvisited == null || minUnvisited.weight > edge.weight) {
                minUnvisited = edge;
            }
        }
        // return minimum unvisited edge (can be null)
        return minUnvisited;
    }

    // directions that adjacent vertices can be in; used to simplify certain methods that find adjacent vertices
    private enum Direction {
        ABOVE,
        BELOW,
        LEFT,
        RIGHT
    }

}  // end class Graph

