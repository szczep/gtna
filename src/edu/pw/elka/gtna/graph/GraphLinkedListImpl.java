package edu.pw.elka.gtna.graph;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import edu.pw.elka.gtna.graph.creator.interfaces.GraphCreator;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class GraphLinkedListImpl<N extends Node,E extends Edge<N>> implements Graph<N,E> {

	Map<N,Set<E>> graphLinkedListData;
	
	
    GraphLinkedListImpl(){
		graphLinkedListData = new LinkedHashMap<N,Set<E>>();
	}
	
    
    GraphLinkedListImpl(GraphCreator<E> gc){
    	this();
		for (E e: gc){
			addEdge(e);
		}
	}
    
	/**
	 * O(N)
	 */
	@Override
	public Set<N> getNeighbours(N node) {
		Set<N> neigbours = new LinkedHashSet<N>();
		for (E edge: graphLinkedListData.get(node)){
			neigbours.add(edge.getN(node));
		}
		return neigbours;
	}
	

	/**
	 * O(1)
	 */
	@Override
	public int getDegree(N node) {
		return graphLinkedListData.get(node).size();
	}

	/**
	 * O(N)
	 */
	@Override
	public Set<N> getNodes() {
		return new LinkedHashSet<N>(graphLinkedListData.keySet());
	}

	/**
	 * O(E)
	 * 
	 */
	@Override
	public Set<E> getEdges() {
		Set<E> edges = new LinkedHashSet<E>();
		for (Set<E> edgeList : graphLinkedListData.values()) {
			edges.addAll(edgeList);
		}
		return edges;
	}
	
	/**
	 * O(1)
	 * 
	 */
	@Override
	public boolean removeEdge(E e) {
		graphLinkedListData.get(e.getN1()).remove(e);
		return graphLinkedListData.get(e.getN2()).remove(e);
	}
	
	/**
	 * O(E)
	 * 
	 */
	@Override
	public boolean removeNode(N n) {
		
		if (graphLinkedListData.containsKey(n)){
			for(E edge: graphLinkedListData.get(n)) {
				graphLinkedListData.get(edge.getN(n)).remove(edge);
			}
			graphLinkedListData.remove(n);
			return true;
		}
		return false;
	}
	
	/**
	 * O(1)
	 * 
	 */
	@Override
	public boolean addEdge(E e) {
		addNode(e.getN1());
		addNode(e.getN2());
		
		if (!graphLinkedListData.get(e.getN1()).contains(e)) {
			graphLinkedListData.get(e.getN1()).add(e);			
			graphLinkedListData.get(e.getN2()).add(e);				
			
			return true;
		}
		return false;
	}

	/**
	 * O(1)
	 * 
	 */
	@Override
	public boolean addNode(N n) {
		if (!graphLinkedListData.containsKey(n)) {
			graphLinkedListData.put(n, new LinkedHashSet<E>());
			return true;
		}
		return false;
	}


	@Override
	public int getNodesNumber() {
		return graphLinkedListData.keySet().size();
	}


	@Override
	public int getEdgesNumber() {
		return getEdges().size();
	}


	@Override
	public boolean removeEdges(N n) {
		if (graphLinkedListData.containsKey(n)){
			graphLinkedListData.get(n).clear();
			return true;
		}
		return false;
	}

	@Override
	public Set<N> getNeighbours(Set<N> subgraph){
		Set<N> N = new LinkedHashSet<N>();
		for(N i : subgraph) {
			for (N u: getNeighbours(i)){
				if (!subgraph.contains(u)) {
					N.add(u);
				}
			}
		}
		return N;
	}


	@Override
	public boolean hasEdge(E e) {
		return graphLinkedListData.get(e.getN1()).contains(e);
	}


	@Override
	public int getWeight(N n1, N n2) {
		return 1;
	}


	@Override
	public double getWeight(N n1) {
		return 0;
	}


}
