package edu.pw.elka.gtna.centrality.betweenness;

import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public abstract class ParametrisedBetweenness<N extends Node> extends BetweennessCentralityBrandesImpl<N> {


    public ParametrisedBetweenness(Graph<N, Edge<N>> graph) {
		super(graph);
	}

	@Override
	protected void accumulation(Node source) {
        while (!stack.isEmpty()){
        	N w = stack.pop();
            for(N v: pred.get(w)){
                delta.put(v, delta.get(v) +  (sigma.get(v).doubleValue()/sigma.get(w).doubleValue())*( f((int)getDistance(w)) + delta.get(w)) );
            }
            if (!w.equals(source)) {
            	centralities.put(w, centralities.get(w)+(delta.get(w) + 2*g((int)getDistance(w))));
            }
        }
    }
	
    protected abstract double f(int d);
    protected abstract double g(int d);
    
}
