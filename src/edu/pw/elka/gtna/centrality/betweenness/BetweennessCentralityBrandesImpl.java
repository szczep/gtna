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

abstract public class BetweennessCentralityBrandesImpl<N extends Node> extends NodeCentrality<N, Edge<N>> {

    Queue<N> queue; 
    Stack<N> stack;
    Map<N, Double> dist;
    Map<N,List<N>> pred;
    Map<N,Integer> sigma;
    Map<N,Double> delta;
    
    public BetweennessCentralityBrandesImpl(Graph<N,Edge<N>> graph) {
    	super(graph);
        stack = new Stack<N>();
        dist = new HashMap<N, Double>();
        pred = new HashMap<N,List<N>>();
        sigma = new HashMap<N,Integer>();
        delta = new HashMap<N,Double>();
        queue = new LinkedList<N>();
    }
   
    protected double getDistance(N node){
    	return dist.get(node);
    }
    
    protected int getSigma(N node){
    	return sigma.get(node);
    }
    
    protected double getDelta(N node){
    	return delta.get(node);
    }

    void singleSourceShortestPath(N source){
        dist.put(source, 1.0);
        sigma.put(source, 1);
        queue.offer(source);
    	
        while (!queue.isEmpty()) {
        	N v = queue.poll(); 

            stack.push(v);
            for(N w: graph.getNeighbours(v)){
                if (dist.get(w) == Double.MAX_VALUE ) {
                    dist.put(w, dist.get(v) + 1);
                    queue.offer(w);
                }
                if (dist.get(w) == dist.get(v) + 1) {
                    sigma.put(w, sigma.get(w) + sigma.get(v));
                    pred.get(w).add(v);
                }
            }
        }
    }
    

    protected abstract void accumulation(N source);

    void initialization(){ 
        for (N vert: graph.getNodes()) {
            pred.put(vert, new LinkedList<N>());
            dist.put(vert, Double.MAX_VALUE);
            sigma.put(vert, 0);
            delta.put(vert, 0.0);            
        }
    }
    
    
    public void computeCentrality(){
        for (N vertex: graph.getNodes()){
            centralities.put(vertex, 0.0);
        }   
        for (N source: graph.getNodes()) {
        	computeSourceInfluecnes(source);    
        }

    }

    public void computeSourceInfluecnes(N source){
    	initialization();
        singleSourceShortestPath(source);           
        accumulation(source);
    }
    
    public Graph<N,Edge<N>> getGraph() {
        return  graph;
    }

    public void setGraph(Graph<N,Edge<N>> graph) {
        this.graph = graph;
    }
    

     
       
}


