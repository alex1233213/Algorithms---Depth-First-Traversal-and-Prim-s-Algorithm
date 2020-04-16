/*
Implementation of part 2 of assignment -
modification of DF to count the number of connected components in a disconnected graph.

The program opens myDiscGraph.txt file to find its connected components

Alexandru Bulgari
C18342126
*/



import java.io.*;

public class DisconnectedGraph {
    class Node {
        public int vert;
        public Node next;
    }
    
    // V = number of vertices
    // E = number of edges
    // adj[] is the adjacency lists array
    //connected_components - number of connected components
    private int V, E;
    private Node[] adj;
    private Node z;
    private int connected_components;
    
    
    // used for traversing graph
    private int[] visited;
    private int id;
    
    // default constructor
    public DisconnectedGraph(String graphFile) throws IOException
    {
        int u, v;
        int e, wgt;
        Node t;

        FileReader fr = new FileReader(graphFile);
		BufferedReader reader = new BufferedReader(fr);
	           
        String splits = " +";  // multiple whitespace as delimiter
		String line = reader.readLine();        
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);
        
        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);
        
        //initialise the size of the array visited[] to the number of edges
        //this array will be used to traverse the graph later on
        visited = new int[V + 1];

        // create sentinel node
        z = new Node(); 
        z.next = z;
        
        // create adjacency lists, initialised to sentinel node z
        adj = new Node[V+1];        
        for(v = 1; v <= V; ++v)
            adj[v] = z;               
        
       // read the edges
        System.out.println("Reading edges from text file");
        for(e = 1; e <= E; ++e)
        {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]); 
            
            System.out.println("Edge " + toChar(u) +
                                 "----"
                                 + toChar(v));    
            
            //put edge into adjacency matrix     
            //create new node for adjacent vertex v for vertex u 
            t = new Node();

            //assign the vertex v to the node created and its weight 
            t.vert = v;

            //vertex is now added to start of the list like in a stack
            t.next = adj[u];
            adj[u] = t;

            /*after edge v has been linked to edge u the same is done 
             to connect edge u to edge v
            */

            //create node which will store vertex u
            t = new Node();
            t.vert = u;
            
            //put node to start of the list
            t.next = adj[v]; // point to the node at the start of the list
            adj[v] = t; // point to the new node, new node is now at the start
        }	   
    }





   
    // convert vertex into char for pretty printing
    private char toChar(int u)
    {  
        return (char)(u + 64);
    }



    

    
    // method to display the graph representation
    public void display() {
        int v;
        Node n;
        
        for(v=1; v<=V; ++v){
            System.out.print("\nadj[" + toChar(v) + "] ->" );
            for(n = adj[v]; n != z; n = n.next) 
                System.out.print(" |" + toChar(n.vert) + "| ->");    
        }
        System.out.println("");
    }




    /*this method is used to initiate a call for the 
    dfVisit method. It also marks all vertexes as unvisited
    before calling the dfVisit method to search through the graph.
    Also uses a for loop to call dfVisit for all unvisited 
    vertices and increments connected_components each time function is called
    */
    public void DF() {
        int id = 0;

        //marka all nodes as unvisited
        for(int v = 1; v <= V; v++) {
            visited[v] = 0;
        }

        
        //call dfVisit from each vertex that 
        //has not been visited
        
        for(int v = 1; v <= V; v++) {
            if(visited[v] == 0) {
                System.out.println("-----------------------------------");
                System.out.println("Connected component starting at "
                 + toChar(v));
                 System.out.println("-----------------------------------");

                dfVisit(0, v);
                System.out.println();
                //each time dfVisit is called, 
                connected_components++;
            }
            
        }
    }



    /*This method calls itself recursively as it 
    searches through the nodes of the graph. The method
    calls itself initially with the parameters prev and 
    v. prev is the ancestor node and v is the current node
    . It is then called recursively with v being the 
    previous node and its adjacent node being the current node
    The visited[] array ensures graph stops being traversed 
    when all nodes have been visited */
    public void dfVisit(int prev, int v) {
        Node u = new Node();

        visited[v] = ++id;
        System.out.println("Visited vertex " 
                            + toChar(v) + 
                            " along edge "
                             + toChar(prev) + 
                             "-->" + toChar(v));

        for(u = adj[v]; u != z; u = u.next) {
            if(visited[u.vert] == 0) {
                dfVisit(v, u.vert);
            }
        }     
    }



    public static void main(String[] args) throws Exception {
        
        //create the disconnected graph, pass in the name of the file
        DisconnectedGraph g = new DisconnectedGraph("myDiscGraph.txt"); 

        //display the graph
        g.display();
        System.out.println();

        //DF performs depth first search using recursion
        g.DF();

        //after calling DF on the starting vertex, the number of 
        //connected components in the disconnected graph have been 
        //calculated
        System.out.println("The disconnected graph g is composed of "
                         + g.connected_components + " connected components");
    }
}