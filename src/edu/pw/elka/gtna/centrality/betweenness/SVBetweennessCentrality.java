/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pw.elka.gtna.centrality.betweenness;

import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;

/**
 *
 * @author szczepan
 */
public class SVBetweennessCentrality<N extends Node> extends BetweennessCentralityBrandesImpl<N> {
    
    

    public SVBetweennessCentrality(Graph<N, Edge<N>> graph) {
		super(graph);
	}

	@Override
	protected void accumulation(N source) {
        while (!stack.isEmpty()){
        	N w = stack.pop();
            for(N v: pred.get(w)){
                delta.put(v, delta.get(v) +  (sigma.get(v).doubleValue()/sigma.get(w).doubleValue())*( (1.0/dist.get(w)) + delta.get(w)) );
            }
            if (!w.equals(source)) {
            	centralities.put(w, centralities.get(w)+(delta.get(w) + ((double)(2-dist.get(w)))/dist.get(w))/2.0);
            }
        }
    }
    

}
