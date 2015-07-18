package edu.pw.elka.graph.algorithms;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class FindComponents<N extends Node,E extends Edge<N>> {
	
	Graph<N,E> graph;
	
	CheckConnectivity<N,E> connectivity;
	Set<Set<N>> components;
	
	Map <N, Boolean> checked;
	
	public FindComponents(Graph<N,E> graph) {
		super();
		this.graph = graph;
		connectivity = new CheckConnectivity<N,E>(graph);
		components = new LinkedHashSet<Set<N>>();
		checked = new HashMap<N, Boolean>();
	}
	
	
	
	public Set<Set<N>> getComponents() {
		return components;
	}

	public int size(){
		return components.size();
	}
	
	public void compute(){
		components.clear();
		
		Set<N> subgraph = new LinkedHashSet<N>(graph.getNodes());
		
		connectivity.check(subgraph);
		components.add(connectivity.getComponent());
		
		while(!connectivity.isConencted()){
			subgraph.removeAll(connectivity.getComponent());
			connectivity.check(subgraph);
			components.add(connectivity.getComponent());
		}			
	}
	
}
