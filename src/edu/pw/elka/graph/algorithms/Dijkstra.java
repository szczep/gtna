package edu.pw.elka.graph.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;



/**
 * 
 * Dijkstra Algorithm to compute shortest paths in a given graph
 *
 */
public class Dijkstra<N extends Node,E extends Edge<N>> {
	
	/* Graph */
	Graph<N,E> graph;
	
	
    protected Map<N, Integer> distances;  
    protected Map<N, N> predecessors;
       
    
    public Dijkstra(Graph<N,E> graph){
    	this.graph = graph;
    	distances = new HashMap<N, Integer>();
    	predecessors =  new HashMap<N, N>();
    }
    
    public int getDistance(N n){
    	return distances.get(n);
    }
    
	 public void computePDistances(N source) {
		distances.clear();
		predecessors.clear();
		    
		for(N v: graph.getNodes()){
			distances.put(v, Integer.MAX_VALUE);
		}
		
		distances.put(source, 0);
		//predecessors[source] = null;

        PriorityQueue<N> vertexQueue = new PriorityQueue<N>(graph.getNodesNumber(), new VerticesComparator());
        for(N v: graph.getNodes())
        	vertexQueue.add(v);
	
        
		while (!vertexQueue.isEmpty()) {
		   N u = vertexQueue.poll();
	       // Visit each edge exiting u
           for(N v: graph.getNeighbours(u))
            {   
				if (distances.get(u) < distances.get(v) - graph.getWeight(u,v)) {
				    vertexQueue.remove(v);
				    distances.put(v, distances.get(u) + graph.getWeight(u,v));
				    predecessors.put(v, u);
				    vertexQueue.add(v);
				}
            }
	    }
		
		
	 }
    
    
    /**
     * Compute distance from source to sink
     * @param source
     * @param sink
     */
	 public void computePaths(N source, N sink) {
		// System.out.println("Dijkstra: "+source+"->"+sink);
		distances.clear();
	    predecessors.clear();
		 
		for(N v: graph.getNodes()){
			distances.put(v, Integer.MAX_VALUE);
		}
		
		distances.put(source, 0);
		//predecessors[source] = null;

        PriorityQueue<N> vertexQueue = new PriorityQueue<N>(graph.getNodesNumber(), new VerticesComparator());
        for(N v: graph.getNodes())
        	vertexQueue.add(v);
	
        
		while (!vertexQueue.isEmpty()) {
		   N u = vertexQueue.poll();
		   if (u == sink)
			   break;
	            // Visit each edge exiting u
           for(N v: graph.getNeighbours(u))
            {   
				if (distances.get(u) < distances.get(v) - graph.getWeight(u,v)) {
				    vertexQueue.remove(v);
				    distances.put(v, distances.get(u) + graph.getWeight(u,v));
				    predecessors.put(v, u);
				    vertexQueue.add(v);
				}
            }
	    }
	 }
	  
	  public List<N> getShortestPathTo(N sink) {
	        List<N> path = new ArrayList<N>();
	        for (N vertex = sink; vertex != null; vertex = predecessors.get(vertex)){
	            path.add(vertex);
	        }
	        Collections.reverse(path);
	        return path;
	   }
	   
	 
	 public class VerticesComparator implements Comparator<N>
	 {
	     @Override
	     public int compare(N x, N y)
	     {
	         if (distances.get(x) < distances.get(y))
	         {
	             return -1;
	         }
	         if (distances.get(x) > distances.get(y))
	         {
	             return 1;
	         }
	         return 0;
	     }
	 }
	 
}
