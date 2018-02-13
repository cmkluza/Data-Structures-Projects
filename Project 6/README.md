# Project 6 - Fun with Graphs

### Description
This project familiarized me with graphs utilizing a rectangular grid of vertices. I implemented various graph algorithms to search and operate on these grids, including depth-first search (DFS), breadth-first search (BFS), and Prim's algorithm.

### Given Files
* Grid Files
	* [grid1](src/grid1)
	* [grid2](src/grid2)
	* [grid3](src/grid3)
* [App1.java](src/App1.java)
* [App2.java](src/App2.java)
* [App3.java](src/App3.java)
* [Queue.java](src/Queue.java)
* [Stack.java](src/Stack.java)
* [StdDraw.java](src/StdDraw.java)

### Modified Files
* [Graph.java](src/Graph.java)
* [Heap.java](src/Heap.java)

### Application Descriptions
#### [App 1](src/App1.java)
Creates a rectangular grid where all neighbors are vertically and horizontally connected with uniformly weighted edges. It then performs DFS starting from a given node and shows the resulting minimum spanning tree (MST).

#### [App 2](src/App2.java)
Similar to App 1, but uses the BFS algorithm to generate an MST.

#### [App 3](src/App3.java)
Loads a weighted graph from one of the three `grid` files and displays it. Then generates an MST from either DFS or BFS, or generates a weighted MST with Prim's algorithm.
