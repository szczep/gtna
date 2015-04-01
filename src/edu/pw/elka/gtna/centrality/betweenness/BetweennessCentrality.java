/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pw.elka.gtna.centrality.betweenness;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import edu.pw.elka.gtna.centrality.NodeCentrality;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;


/**
 *
 * @author Piotr Szczepanski
 * The framework for Brandes algorithm
 * All algorithms works in O(nm) time, where n is number of vertices and m number of edges.
 */

public class BetweennessCentrality<N extends Node> extends BetweennessCentralityBrandesImpl<N> {

    
    public BetweennessCentrality(Graph<N,Edge<N>> graph) {
    	super(graph);
    }
   
    @Override
    protected void accumulation(N source) {
        while (!stack.isEmpty()){
        	N w = stack.pop();
            for(N v: pred.get(w)){
                delta.put(v, delta.get(v) + ((sigma.get(v).doubleValue()*(1.0 + delta.get(w)))/sigma.get(w).doubleValue()));
            }
            if (!w.equals(source)) {
            	centralities.put(w, centralities.get(w)+(delta.get(w)/2.0));
            }
        }
    }
   
       
}


