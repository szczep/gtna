package edu.pw.elka.gtna.graph.interfaces;

import java.util.Set;

public interface Graph<N extends Node,E extends Edge<N>> {

	
	Set<N> getNeighbours(N node);
	int getDegree(N node);
	
	Set<N> getNodes();
	Set<E> getEdges();
	
	
	boolean removeEdge(E e);
	boolean removeNode(N n);
	
	
	boolean addEdge(E e);
	boolean addNode(N n);

}
