/*
Algorithms assignment
Implementation of part 1 and part 3 of the assignment.
Part 1 - implement depth first search on a graph using 
iterations and recursion.
Part 3 - implement Prim's algorithm for finding the minimum 
spanning tree for a weighted connected graph.

When running the program , user is asked to enter the name 
of the graph file to be used and the starting vertex.


Alexandru Bulgari
C18342126
*/

import java.io.*;
import java.util.Stack;     



class Heap
{
    private int[] a;	   // heap array
    private int[] hPos;	   // keeps track of position of heap elements
    private int[] dist;    // dist[v] = priority of v

    private int N;         // heap size
   
    // The heap constructor gets passed from the Graph:
    //    1. maximum heap size
    //    2. reference to the dist[] array
    //    3. reference to the hPos[] array
    public Heap(int maxSize, int[] _dist, int[] _hPos) 
    {
        N = 0;
        a = new int[maxSize + 1];
        dist = _dist;
        hPos = _hPos;
    }




    //returns true if heap is empty
    public boolean isEmpty() 
    {
        return N == 0;
    }




    //finds vertex position on the graph after added to heap 
    //in a upward direction, based on distance priority,
    //i.e shortest distance on top of heap. 
    //Based on pseudocode from notes
    public void siftUp( int k) 
    {
        int u = a[k];
        
        //a[0] is not used as a node for heap
        a[0] = 0;
        

        //distance of node is smaller than its parents
        while(dist[u] < dist[a[k / 2]]) {
            //child node assigned parent node 
            a[k] = a[k / 2];

            //position of node on heap stored
            hPos[a[k]] = k;
            k = k / 2;
            
        }

        a[k] = u;

        //record position of vertex
        hPos[a[k]] = k;
    }




    /*after the top of the heap is removed, it 
    is replaced by the last node on heap, which is 
    then moved downwards according to the distance 
    priority. Based on pseudocode from notes
    */
    public void siftDown( int k)
    {
        int v, j;
       
        //a[1]
        v = a[k];  
        
        //while heap has a left child
        while(k <= N / 2) {
            //left child
            j = 2 * k;

            if(j < N && dist[a[j]] > dist[a[j + 1]]) {
                j++;
            }

            //distance smaller than its child exits the loop
            //as no need to change position of nodes
            if(dist[v] <= dist[a[j]]) {
                break;
            }

            //top node becomes child node
            a[k] = a[j];
            k = j;
        }
        

        a[k] = v;
        //store positon of node a[k]
        hPos[a[k]] = k;
    }

    //insert node into heap
    public void insert( int x) 
    {
        a[++N] = x;
        siftUp( N);
    }

    //remove node from heap
    public int remove() 
    {   
        int v = a[1];
        hPos[v] = 0; // v is no longer in heap
        
        //last node on heap becomes first
        a[1] = a[N--];
        siftDown(1);
        
        a[N+1] = 0;  // put null node into empty spot
        
        return v;
    }

}

class Graph {
    class Node {
        public int vert;
        public int wgt;
        public Node next;
    }
    
    // V = number of vertices
    // E = number of edges
    // adj[] is the adjacency lists array
    private int V, E;
    private Node[] adj;
    private Node z;
    private int[] mst;
    
    
    // used for traversing graph
    private int[] visited;
    private int id;
    
    // default constructor
    public Graph(String graphFile)  throws IOException
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
        visited = new int[E];

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
            wgt = Integer.parseInt(parts[2]);
            
            System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));    
            
            //put edge into adjacency matrix     
            //create new node for adjacent vertex v for vertex u 
            t = new Node();

            //assign the vertex v to the node created and its weight 
            t.vert = v;
            t.wgt = wgt;

            //vertex is now added to start of the list like in a stack
            t.next = adj[u];
            adj[u] = t;

            /*after edge v has been linked to edge u the same is done 
             to connect edge u to edge v
            */

            //create node which will store vertex u
            t = new Node();
            t.vert = u;
            t.wgt = wgt;
            
            //put node to start of the list
            t.next = adj[v]; // point to the node at the start of the list
            adj[v] = t; // point to the new node, new node is now at the start
        }
        
        //close the reader
        reader.close();
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
                System.out.print(" |" + toChar(n.vert) + " | " + n.wgt + "| ->");    
        }
        System.out.println("");
    }


    

    /*This method finds the minimum spanning 
    tree of the input graph , with starting vertex, s. 
    Based on pseudocode from notes*/
	public void MST_Prim(int s)
	{
        int v, u;
        int wgt, wgt_sum = 0;
        int[]  dist, parent, hPos;
        Node t = new Node();

        // initialise arrays
        dist = new int[V + 1];
        parent = new int[V + 1];
        hPos = new int[V + 1];
    

        //mark distance of all vertexes as infinity
        for(v = 1; v <= V; ++v) {
            //mark distance to all nodes infinity
            dist[v] = Integer.MAX_VALUE;

            //all nodes have no parents
            parent[v] = 0;
            hPos[v] = 0;//v not in heap
        }


        
        dist[s] = 0;
        
        Heap h =  new Heap(V, dist, hPos);
        h.insert(s);
        
        while(h.isEmpty() == false)  {
            v = h.remove();//remove from top of heap, store its value 
            
            //calculate the sum of the weights
            wgt_sum += dist[v];
            
            dist[v] = -dist[v];// v now in mst


            //for all vertices adjacent to v
            for(t = adj[v]; t != z; t = t.next) {
                //u = adjacent vertex
                //wgt = weight of the adjacent vertex from v - u
                u = t.vert;
                wgt = t.wgt;

                //check if weight is smaller than current weight
                if(wgt < dist[u]) {
                    dist[u] = t.wgt;
                    parent[u] = v;

                    //check if vertex is in heap
                    if(hPos[u] == 0) {
                        h.insert(u);
                    } else  {
                        //if its already in the heap
                        //position its priority according 
                        //to distance from vertex v
                        h.siftUp(hPos[u]);
                    }
                }
            }
        }



        System.out.print("\n\nWeight of MST = " + wgt_sum + "\n");
        
        //parent stores vertices in mst
        mst = parent;                      		
	}
    



    //display mst
    public void showMST()
    {
        System.out.print("Minimum Spanning tree parent array is:\n");
        for(int v = 1; v <= V; ++v)
            System.out.println(toChar(v) + " -> " + toChar(mst[v]));
            System.out.println("");
    }




    /*this method is used to initiate a call for the 
    dfVisit method. It also marks all vertexes as unvisited
    before calling the dfVisit method to search through the graph */
    public void DF(int s) {
        int id = 0;

        //mark all nodes as unvisited
        for(int v = 1; v <= V; v++) {
            visited[v] = 0;
        }

        dfVisit(0, s);
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
        System.out.println("Visited vertex " + toChar(v) +
                             " along edge " + toChar(prev) +
                              "-->" + toChar(v));

        for(u = adj[v]; u != z; u = u.next) {
            if(visited[u.vert] == 0) {
                dfVisit(v, u.vert);
            }
        }
    }


    
    /*This method marks all nodes as unvisited before
    initiating a call to DF_iteration to traverse the 
    graph */
    public void df_iteration_visit(int start) {
        int id = 0;

        //mark all nodes as unvisited
        for(int v = 1; v <= V; v++) {
            visited[v] = 0;
        }

        DF_iteration(start);
    }



    /*This method uses a stack for depth first 
    traversal of the graph. Takes the starting 
    vertex as the parameter*/
    public void DF_iteration(int start) {
        

        //create a stack which will keep track of the 
        //vertexes being visited
        Stack <Integer> s = new Stack <Integer>();


        //used to access the adjacency list
        Node n = new Node();

        //push start vertex to the stack
        s.push(start);

        //mark start vertex visited
        visited[start] = ++id;
        System.out.println("\nStarting vertex " + toChar(start));

        /*Stack will continually have nodes added to it 
        which will be traversed untill all nodes have been traversed 
        leaving an empty stack*/
        while(s.isEmpty() == false) {
            //value stored on top of stack
            int v = s.peek();

            /*v is the current vertex being processed on top of stack
            prev is the next value in the stack from which v originates */
            int prev = s.size() - 2;


            //check if node v has been visited
            if(visited[v] == 0) {
                //mark as visited
                visited[v] = ++id;
                System.out.println("Visited vertex " + toChar(v) + 
                                    " along edge " + toChar(s.get(prev))
                                     + "-->" + toChar(v));
            }  

            //node n points to adjacency list of 
            //current node being processed
            n = adj[v];

            //push adjacent node of v  
            //which is unvisited onto the stack
            while (n != z)  
            { 
                //vertex of the node n
                int u = n.vert;

                if(visited[u] == 0) {
                    //push vertex to process onto the stack 
                    s.push(u);
                    break;
                } else {
                    //iterate through the adjacency list
                    n = n.next;

                    //if no adjacent unvisited nodes found, pop 
                    //vertex from the stack
                    if(n == z) {
                        s.pop();
                    }
                }
            }
        }
    }
}

public class PrimLists {
    public static void main(String[] args) throws IOException
    {
        int s;
        String fname;
        

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
         
        // Asking user which graph file is to be opened
        System.out.print("Enter the name of the graph file: ");
        fname = reader.readLine(); 

        //create the graph
        Graph g = new Graph(fname);
        
        //ask user for the starting vertex of the graph
        System.out.print("Enter the start vetex of the graph: ");
        try {
            s = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            throw e;
        }

        //display the graph
        g.display();

        //DF performs depth first search using recursion
        System.out.println("\n-----------------------------------");
        System.out.println("Depth first search using recursion");
        System.out.println("-----------------------------------");
        g.DF(s);

        //df_iteration_visit() performs depth first search using iteration
        System.out.println("\n\n\n-----------------------------------");
        System.out.println("Depth first search using iteration");
        System.out.println("-----------------------------------");
        g.df_iteration_visit(s);
    
            

        //Minimum spanning tree
        g.MST_Prim(s);  

        //display MST
        g.showMST();                
    }
}



