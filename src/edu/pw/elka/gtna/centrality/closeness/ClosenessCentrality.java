package edu.pw.elka.gtna.centrality.closeness;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import edu.pw.elka.gtna.centrality.NodeCentrality;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
abstract public class ClosenessCentrality<N extends Node, E extends Edge<N>> extends NodeCentrality<N,E>{

	
    Queue<N> queue; 
    Map<N, Double> dist;
	
	/**
	 * @param graph
	 */
	public ClosenessCentrality(Graph<N,E> graph) {
		super(graph);
        dist = new HashMap<N,Double>();
        queue = new LinkedList<N>();
	}

	abstract protected double fDistance(double d);
	
    protected double getDistance(N node){
    	return dist.get(node);
    }
    

    protected void singleSourceBFS(N source){
	    for (N vert: graph.getNodes()) {
            dist.put(vert, Double.MAX_VALUE);
            queue.clear();
	    }
	    dist.put(source,0.0);
	    queue.add(source);
	    
        while (!queue.isEmpty()) {
        	N v = queue.poll();
            for(N w: graph.getNeighbours(v)){
                if (dist.get(w) == Double.MAX_VALUE ) {
                    dist.put(w, dist.get(v) + 1);
                    queue.offer(w);
                }
            }
        }
    }

	/* (non-Javadoc)
	 * @see edu.pw.elka.gtsna.centrality.AbstractCentrality#computeCentrality()
	 */
	@Override
    public void computeCentrality(){
		double sumDistance = 0.0;
		
		for (N node: graph.getNodes()){
			sumDistance = 0.0;
			singleSourceBFS(node);
			
			for (N u: graph.getNodes()){
				if (node !=u)
					sumDistance+=fDistance(getDistance(u));
			}
			
    		centralities.put(node, sumDistance) ;
		}
    }

}
