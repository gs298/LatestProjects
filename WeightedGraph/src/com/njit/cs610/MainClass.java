package com.njit.cs610;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MainClass {
	
	  private static List<VertexStructure> vertice= new ArrayList();
	  private static List<EdgeStructure> edges=new ArrayList();
	  public static ArrayList<String> aa = new ArrayList<String>();
	  
	  public static int computeDijk(int src,int dest) { 
	   aa.clear();

/*************** Vertex Creation **********************/
	   
	   VertexStructure location;
	   location=new VertexStructure("LAX","0" );
	    vertice.add(location);
	    
	    location=new VertexStructure("SFO","1" );
	    vertice.add(location);
	    
	     location=new VertexStructure("ORD","2" );
	    vertice.add(location);
	    
	     location=new VertexStructure("DFW","3" );
	    vertice.add(location);
	     location=new VertexStructure("ATL","4" );
	    vertice.add(location);
	    
	     location=new VertexStructure("EWR","5" );
	    vertice.add(location);
	    
	     location=new VertexStructure("BOS","6" );
	    vertice.add(location);
	 
/********* joining vertices with weights ********/   
	    joinVertices( 0, 1, 100);
	    joinVertices( 0, 2, 600);
	    joinVertices( 0, 3, 600);
	    
	    joinVertices( 1, 2, 200);
	    joinVertices( 1, 3, 400);
	    joinVertices( 1, 4, 700);
	    joinVertices( 1, 0, 100);
	    
	    joinVertices( 2, 3, 100);
	    joinVertices( 2, 4, 500);
	    joinVertices( 2, 5, 600);
	    joinVertices( 2, 6, 500);
	    joinVertices( 2, 0, 600);
	    joinVertices( 2, 1, 200);
	    
	    joinVertices( 3, 4, 100);
	    joinVertices( 3, 0, 600);
	    joinVertices( 3, 1, 400);
	    joinVertices( 3, 2, 100);
	    
	    joinVertices( 4, 6, 200);
	    joinVertices( 4, 5, 400);
	    joinVertices( 4, 1, 700);
	    joinVertices( 4, 2, 500);
	    joinVertices( 4, 3, 100);
	    
	    joinVertices( 5, 6, 100);
	    joinVertices( 5, 2, 600);
	    joinVertices( 5, 4, 400);
	    
	    joinVertices( 6, 4, 200);
	    joinVertices( 6, 5, 100);
	    joinVertices( 6, 2, 500);
	 
	    GraphStructure graph = new GraphStructure(vertice, edges);
	    ShortPath_Dijkstra shrtPathObj = new ShortPath_Dijkstra(graph);
	    shrtPathObj.execute((VertexStructure)vertice.get(src));
	    
	    LinkedList<VertexStructure> path = shrtPathObj.getPath((VertexStructure)vertice.get(dest));// destination
	    
	    int distance=shrtPathObj.getShortestDistance((VertexStructure)vertice.get(dest));// gets the shortest distance between two 
	    
	    
	    for (VertexStructure vertex : path) {
	    if(!path.isEmpty()){	
	      if (vertex.toString().equals("0"))
	      {
	        aa.add("LAX");
	        aa.add("->");
	      }
	      else if (vertex.toString().equals("1"))
	      {
	        aa.add("SFO");
	        aa.add("->");
	      }
	      else if (vertex.toString().equals("2"))
	      {
	        aa.add("ORD");
	        aa.add("->");
	      }
	      else if (vertex.toString().equals("3"))
	      {
	        aa.add("DFW");
	        aa.add("->");
	      }
	      else if (vertex.toString().equals("4"))
	      {
	         aa.add("ATL");
	        aa.add("->");
	      }
	      else if (vertex.toString().equals("5"))
	      {
	        aa.add("EWR");
	        aa.add("->");
	      }
	      else if (vertex.toString().equals("6"))
	      {
	        aa.add("BOS");
	        aa.add("->");
	      }
	    }
	 
	    }
	    return distance;
	  }
	  

	  private static void joinVertices(int sourceLocNo, int destLocNo, int cost)
	  {
	    EdgeStructure lane = new EdgeStructure(/*laneId, */ (VertexStructure)vertice.get(sourceLocNo), (VertexStructure)vertice.get(destLocNo), cost);
	    edges.add(lane);
	  }
	
/**************  MAIN ***********************/
	  
public static void main(String args[]){
	System.out.println("\t\t Welcome \t\t\n");
	System.out.println("\tAirport\t\tCode\t\t");
	System.out.println("\t========\t=====\t\t");
	System.out.println("\tLAX\t\t0\t\t");
	System.out.println("\tSFO\t\t1\t\t");
	System.out.println("\tORD\t\t2\t\t");
	System.out.println("\tDFW\t\t3\t\t");
	System.out.println("\tATL\t\t4\t\t");
	System.out.println("\tEWR\t\t5\t\t");
	System.out.println("\tBOS\t\t6\t\t\n");
	
	
		Scanner scan;
		scan=new Scanner(System.in);
		System.out.println("Enter The Departing Airport Code");
		int temp1=scan.nextInt();
		System.out.println("Enter The Destination Airport Code");
		int temp2=scan.nextInt();
		
		
		
		int shortestDistance= computeDijk(temp1,temp2);
		
		ArrayList<String> temprd= new ArrayList<String>();
	       temprd = aa;
	       
	       StringBuilder shortestPath= new StringBuilder();
	       for (int i =0;i< temprd.size();++i)
	       {
	    	   shortestPath.append(temprd.get(i));
	       }
	       System.out.print("\nThe Shortest Distance:");
	       System.out.println(shortestDistance);
	       System.out.print("\nThe Path Taken:");
	       System.out.println(shortestPath);
	}
	
}
