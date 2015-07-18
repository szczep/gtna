package edu.pw.elka.gtna.graph.interfaces;

import java.util.Set;

public interface Graph<N extends Node,E extends Edge<N>> {

	
	Set<N> getNeighbours(N node);
	Set<N> getNeighbours(Set<N> subgraph);
	
	int getDegree(N node);
	
	Set<N> getNodes();
	Set<E> getEdges();
	
	int getNodesNumber();
	int getEdgesNumber();
	
	boolean hasEdge(E edge);
	
	
	boolean removeEdge(E e);
	boolean removeEdges(N n);
	boolean removeNode(N n);
		
	boolean addEdge(E e);
	boolean addNode(N n);
	
	int getWeight(N n1, N n2);
	double getWeight(N n1);
	
}
