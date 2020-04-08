/*


*/

import java.io.*;



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



    private int toInt(String s) {
        return (int)(s - 64);
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
        s = Integer.parseInt(reader.readLine());

        System.out.println(s);
        g.display();

       //g.DF(s);
       //g.DF_iteration(s);
       //g.MST_Prim(s);                  
    }
}
