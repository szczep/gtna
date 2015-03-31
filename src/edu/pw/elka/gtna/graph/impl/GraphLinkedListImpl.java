package edu.pw.elka.gtna.graph.impl;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.pw.elka.gtna.graph.Edge;
import edu.pw.elka.gtna.graph.Graph;
import edu.pw.elka.gtna.graph.Node;

public class GraphLinkedListImpl<N extends Node,E extends Edge<N>> implements Graph<N,E> {

	Map<N,Set<E>> graphLinkedListData;
	
	
	@Override
	public Set<N> getNeighbours(N node) {
		Set<N> neigbours = new LinkedHashSet<N>();
		for (E edge: graphLinkedListData.get(node)){
			neigbours.add(edge.getAdjacent(node));
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

	
	@Override
	public Set<N> getNodes() {
		return new LinkedHashSet<N>(graphLinkedListData.keySet());
	}

	@Override
	public Set<E> getEdges() {
		Set<E> edges = new LinkedHashSet<E>();
		for (Set<E> edgeList : graphLinkedListData.values()) {
			edges.addAll(edgeList);
		}
		return edges;
	}

	@Override
	public int removeEdge(Edge e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeNode(Node n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addEdge(E e) {
		addNode(e.getN1());
		addNode(e.getN2());
		
		if (!graphLinkedListData.get(e.getN1()).contains(e)) {
			graphLinkedListData.get(e.getN1()).add(e);
			
			if (!e.isDirected()){
				graphLinkedListData.get(e.getN2()).add(e);				
			}
			
			return true;
		}
		return false;
	}

	/**
	 * O(1)
	 */
	@Override
	public boolean addNode(N n) {
		if (!graphLinkedListData.containsKey(n)) {
			graphLinkedListData.put((N) n, new LinkedHashSet<E>());
			return true;
		}
		return false;
	}


	@Override
	public boolean isDirected() {
		// TODO Auto-generated method stub
		return false;
	}




}
