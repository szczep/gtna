package edu.pw.elka.gtna.graph.evaluator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public class AverageShortestPathsEvaluator<N extends Node,E extends Edge<N>> extends AbstractGraphEvaluator<N,E> {

    Queue<N> queue; 
    Map<N, Double> dist;
    Map<N,Integer> sigma;
    
	/**
	 * @param graph
	 */
	public AverageShortestPathsEvaluator(Graph<N,E> graph) {
		super(graph);
        dist = new HashMap<N,Double>();
        queue = new LinkedList<N>();
        sigma = new HashMap<N,Integer>();
	}

   
    protected double getDistance(int node){
    	return dist.get(node);
    }
    

    protected void singleSourceBFS(N source){
	    for (N vert: graph.getNodes()) {
            dist.put(vert, Double.MAX_VALUE);
            sigma.put(vert, 0);
            queue.clear();
	    }
	    dist.put(source,0.0);
	    sigma.put(source, 1);
	    queue.add(source);
	    
        while (!queue.isEmpty()) {
        	N v = queue.poll();
            for(N w: graph.getNeighbours(v)){
                if (dist.get(w) == Double.MAX_VALUE ) {
                    dist.put(w, dist.get(v) + 1);
                    queue.offer(w);
                }
                if (dist.get(w) == dist.get(v) + 1) {
                    sigma.put(w, sigma.get(w) + sigma.get(v));
                }
            }
        }
    }


	@Override
	public double evaluate() {
		
		double averageShortestPaths = 0.0;

		for (N v: graph.getNodes()){
			singleSourceBFS(v);
			
			// We sum up the number of shortess paths between v and u
			for (N u: graph.getNodes()){
				if (!u.equals(v))
					averageShortestPaths+=sigma.get(u);
			}
			
		}

		return (averageShortestPaths)/(graph.getNodesNumber()*(graph.getNodesNumber()-1));

	}

}
