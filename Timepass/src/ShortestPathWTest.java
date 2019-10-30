import static java.lang.System.out;

//Implements shortest path with weighted, directed graphs
//Dijkstra's algorithm

class Vertex {
	//Vertex name/label
	private String vName;
	private boolean isInTree;
	
	//Constructor
	public Vertex(String name) {
		vName = name;
		isInTree = false;
	}
	
	//Get vertex name
	public String getVName() { return this.vName; }
	
	//Get and set for isInTree
	public boolean getIsInTree() { return isInTree; }
	public void setIsInTree(boolean isInTree) { this.isInTree = isInTree; }
}

class Edge {
	//Distance from start to this vertex
	private int distance; 
	//Current parent of this vertex
	private int parentVertex;
	
	public Edge(int parentVertex, int distance) {
		this.distance = distance;
		this.parentVertex = parentVertex;
	}
	
	//Get and set distance
	public int getDistance() { return this.distance; }
	public void setDistance(int distance) { this.distance = distance; }
	
	//Get and set for parentVertex
	public int getParentVertex() { return parentVertex; }
	public void setParentVertex(int parentVertex) { this.parentVertex = parentVertex; }
}

class Graph {
	private final int INFINITY = Integer.MAX_VALUE -100;
	//Contains all vertices
	private Vertex vertexList[];
	//List of edges
	private int adjacencyMatrix[][];
	//Keeps track of current number of vertices
	private int nVerts;
	//Vertices in the tree
	private int nTree;
	//Describe the shortest path
	private Edge shortestPath[];
	//Pointer at the current vertex
	private int currentVert;
	//Weight/cost to current vertex
	private int startToCurrent;

	public Graph(int maxVerts) {
		vertexList = new Vertex[maxVerts];
		//Adjacency matrix
		adjacencyMatrix = new int[maxVerts][maxVerts];
		nVerts = 0;
		nTree = 0;
		//Initialize the adjacency matrix using infinity
		for(int x = 0; x < maxVerts; x++)
			for(int k = 0; k < maxVerts; k++) adjacencyMatrix[x][k] = INFINITY;
		
		//Shortest path
		shortestPath = new Edge[maxVerts];
	}

	//Add a vertex
	public void addVertex(String vName) { vertexList[nVerts++] = new Vertex(vName); }
	
	//Add a monodirectional edge with cost
	//To have a bidirectional edge use this method twice
	public void addEdge(int verA, int verB, int weight) {
		adjacencyMatrix[verA][verB] = weight;
		adjacencyMatrix[verB][verA] = weight;
	}
	
	//Pass the starting vertex
	public void dijkstra(int startTree) {
		vertexList[startTree].setIsInTree(true);
		//Put in the tree
		nTree = 1;

		//Copy data from adjacency matrix to shortestPath
		for(int k = 0; k < nVerts; k++) {
			shortestPath[k] = new Edge(startTree, adjacencyMatrix[startTree][k]);
		}

		//Until all vertices are processed
		while(nTree < nVerts) {
			//Vertex associated with the best edge/weight
			int indexMin = getMin();
			//Extract smallest available weight from the edge
			int minDist = shortestPath[indexMin].getDistance();
		
			//Check for unreachable vertices
			if(minDist == INFINITY) {
				out.println("\nUnreachable vertex was found");
				break;
			}
			else {
				//Make currentVert be the best available
				currentVert = indexMin;
				//Get the distance from the start
				startToCurrent = shortestPath[indexMin].getDistance();
			}
			//Put current vertex in tree
			vertexList[currentVert].setIsInTree(true);
			//Take one more slot in the tree
			nTree++;
			//Find the best path
			updateShortestPath();
		}
		//Print the shortest path(s)
		displayPaths();
		//Clean up
		nTree = 0;
		for(int k = 0; k < nVerts; k++) vertexList[k].setIsInTree(false);
	}
		
	//Get smallest weight
	public int getMin() {
		//Assume minimum
		int minWeight = INFINITY;
		int indexMin = 0;

		//Get best edge among the ones that have not been used yet
      		for(int k = 1; k < nVerts; k++) {
      			if(!vertexList[k].getIsInTree() && shortestPath[k].getDistance() < minWeight) {
      				minWeight = shortestPath[k].getDistance();
      				//Update minimum
      				indexMin = k;
      			}
      		}
      		//Return the vertex index associated with the best available edge
      		return indexMin;
	}

	//Compare paths and choose the best one
	public void updateShortestPath() {
		//Start from second element
		int column = 1;
		//Until all vertices are processed
		while(column < nVerts) {
			//Column already in tree
			if(vertexList[column].getIsInTree()) {
				column++;
				continue;
			}
			//Get weight from currentVert to column
			//The vertex currentVert was the one with the best weight
			int currentToColumn = adjacencyMatrix[currentVert][column];
			//Add weight from start
			int startToColumn = startToCurrent + currentToColumn;
			//Compare.If new value is shorter update path
			if(startToColumn < shortestPath[column].getDistance()) {                            
				//Choose the best edge
				shortestPath[column].setParentVertex(currentVert);
				//Update the weight
				shortestPath[column].setDistance(startToColumn);
			}
			//Next column
			column++;
		}
	}

	public void displayPaths() {
		for(int k = 0; k < nVerts; k++) {
			//Skip if distance of vertex on itself
			if(vertexList[k].getVName() == vertexList[shortestPath[k].getParentVertex()].getVName())
				continue;
			
			out.print("\nTo " + vertexList[k].getVName() + " cost ");
         		out.print(shortestPath[k].getDistance());
         		out.print(" via " + vertexList[shortestPath[k].getParentVertex()].getVName());
		}
	}
}

class ShortestPathWTest {
	public static void main(String[] args) {
		Graph graph = new Graph(50);
		//BMT Line
		
		graph.addVertex("Whitehall Street–South Ferry");		//index 0
		graph.addVertex("Canal Street");						//index 1
		graph.addEdge(0,1,5);
		
		graph.addVertex("14th Street–Union Square");			//index 2
		graph.addEdge(1,2,6);
		
		graph.addVertex("34th Street–Herald Square");			//index 3
		graph.addEdge(2,3,5);
		
		graph.addVertex("Times Square–42nd Street");			//index 4
		graph.addEdge(3,4,5);
		
		graph.addVertex("57th Street–Seventh Avenue");			//index 5
		graph.addEdge(4,5,5);
		
		
		//IRT_line
		graph.addVertex("Bowling Green");						//index 6
		graph.addVertex("Wall Street");							//index 7
		graph.addEdge(6,7,3);
		
		graph.addVertex("Fulton Street");						//index 8
		graph.addEdge(7, 8,1);
		
		graph.addVertex("Brooklyn Bridge–City Hall");			//index 9
		graph.addEdge(8,9,5);
		
		graph.addEdge(9, 2, 6); //connects IRT and BMT lines
		
		graph.addVertex("Grand Central–42nd Street");			//index 10
		graph.addEdge(2, 10, 5);
		
		graph.addVertex("59th Street");							//index 11
		graph.addEdge(10, 11,15);
		
		graph.addVertex("86th Street");							//index 12
		graph.addEdge(11, 12,6);
		
		graph.addVertex("125th Street");						//index 13
		graph.addEdge(12, 13, 3);
		out.println("\n***SHORTEST PATHS***");
		//Start from USA
		graph.dijkstra(12);
		out.println();
	}
}