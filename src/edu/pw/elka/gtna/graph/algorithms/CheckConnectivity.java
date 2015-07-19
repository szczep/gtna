package edu.pw.elka.gtna.graph.algorithms;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;



public class CheckConnectivity<N extends Node,E extends Edge<N>> {

	Graph<N,E> graph;
	
	/* The connected component of the graph to which we are restricted */
	Set<N> subgraph;
	
	Boolean isConnected;
	
	/* if node was visited */
	Map <N, Boolean> visited;
	int visitedNumber;
	
	Stack<N> stack;
	Set<N> component = new LinkedHashSet<N>();
	
	public Set<N>  getComponent(){
		return new LinkedHashSet<N>(component);
	}
	
	public CheckConnectivity(Graph<N,E> graph){
		this.graph = graph;
		visited = new HashMap<N, Boolean>();
		stack = new Stack<N>();
	}
	
	
	public void check(Set<N> subgraph){
		this.subgraph = subgraph;
		isConnected = false;
		visitedNumber=0;
		stack.clear();
		visited.clear();
		component.clear();
		for(N v: subgraph){
			visited.put(v, false);
		}
		
		N root = subgraph.iterator().next();
		
		stack.push(root);
		visited.put(root, true);
		while (!stack.isEmpty()) {
			N u = stack.pop();
			component.add(u);
			visitedNumber++;
			for (N i: graph.getNeighbours(u)) {
				if (subgraph.contains(i) && !visited.get(i)){
					visited.put(i, true);
					stack.push(i);
				}
			}
		}
		isConnected = (visitedNumber==subgraph.size());
		
	}

	
	public Boolean isConencted(){
		return isConnected;
	}
	
	
}
