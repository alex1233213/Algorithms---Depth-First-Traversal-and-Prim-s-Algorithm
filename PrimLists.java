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
    private int[] hPos;	   // hPos[a[k]] == k
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


    public boolean isEmpty() 
    {
        return N == 0;
    }


    public void siftUp( int k) 
    {
        int v = a[k];

        // code yourself
        // must use hPos[] and dist[] arrays
    }


    public void siftDown( int k) 
    {
        int v, j;
       
        v = a[k];  
        
        // code yourself 
        // must use hPos[] and dist[] arrays
    }


    public void insert( int x) 
    {
        a[++N] = x;
        siftUp( N);
    }


    public int remove() 
    {   
        int v = a[1];
        hPos[v] = 0; // v is no longer in heap
                
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


    
	public void MST_Prim(int s)
	{
        int v, u;
        int wgt, wgt_sum = 0;
        int[]  dist, parent, hPos;
        Node t;

        //code here
        
        // dist[s] = 0;
        
        // Heap h =  new Heap(V, dist, hPos);
        // h.insert(s);
        
        // while ( ...)  
        // {
        //     // most of alg here
            
        // }
        System.out.print("\n\nWeight of MST = " + wgt_sum + "\n");
        
        // mst = parent;                      		
	}
    
    // public void showMST()
    // {
    //         System.out.print("\n\nMinimum Spanning tree parent array is:\n");
    //         for(int v = 1; v <= V; ++v)
    //             System.out.println(toChar(v) + " -> " + toChar(mst[v]));
    //         System.out.println("");
    // }




    /*this method is used to initiate a call for the 
    dfVisit method. It also marks all vertexes as unvisited
    before calling the dfVisit method to search through the graph */
    public void DF(int s) {
        int id = 0;

        //marka all nodes as unvisited
        for(int v = 1; v < V; v++) {
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
        System.out.println("Visited vertex " + toChar(v) + " along edge " + toChar(prev) + "-->" + toChar(v));

        for(u = adj[v]; u != z; u = u.next) {
            if(visited[u.vert] == 0) {
                dfVisit(v, u.vert);
            }
        }     
    }




    /*This method uses a stack for depth first 
    traversal of the graph. Takes the starting 
    vertex as the parameter*/
    public void DF_iteration(int start) {
        int id = 0;

        //mark all nodes as unvisited
        for(int v = 1; v < V; v++) {
            visited[v] = 0;
        }

        //create a stack which will keep track of the 
        //vertexes being visited
        Stack <Integer> s = new Stack <Integer>();
        
        //used to access the adjacency list
        Node n = new Node();

        //push start vertex to the stack
        s.push(start);

        //mark start vertex visited
        visited[start] = ++id;
        System.out.print("\n\nVisited vertexes in order " +
                toChar(start) + "-->");

        /*Stack will continually have nodes added to it 
        which will be traversed untill all nodes have been traversed 
        leaving an empty stack*/
        while(s.isEmpty() == false) {
            //pop the top of the stack and store its value
            int v = s.pop();
            
            //check if node v has been visited
            if(visited[v] == 0) {
                //mark as visited
                visited[v] = ++id;
                System.out.print(toChar(v) + "-->");
            }  

            //node n points to adjacency list of 
            //current node being processed
            n = adj[v];
            

            //push all adjacent nodes 
            //which are unvisited onto the stack
            while (n != z)  
            { 
                //vertex of the node n
                v = n.vert;
                if(visited[v] == 0) {
                    //push vertex to process onto the stack 
                    s.push(v);
                }
                //iterate through the adjacency list
                n = n.next;
            }
        }
    }





    // public void df_iteration_visit(int start) {
        
    // }  
}

public class PrimLists {
    public static void main(String[] args) throws IOException
    {
        int s;
        String fname;
        

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
         
        // Asking user which graph file is to be opened
        System.out.println("Enter the name of the graph file");
        fname = reader.readLine(); 

        //create the graph
        Graph g = new Graph(fname);
        

        System.out.println("Enter the start vetex of the graph");
        try {
            s = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            throw e;
        }

        //display the graph
        g.display();

        //DF performs depth first search using recursion
        // g.DF(s);

        //DF_iteration performs depth first search using iteration
        // g.DF_iteration(s);


       //g.MST_Prim(s);                  
    }
}



