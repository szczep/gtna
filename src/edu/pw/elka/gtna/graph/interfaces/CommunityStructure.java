package edu.pw.elka.gtna.graph.interfaces;

import java.util.Set;


public interface CommunityStructure<N extends Node,E extends Edge<N>> {
	
	
	Graph<N,E> getGraph();
	void setGraph(Graph<N,E> graph);
	
	Set<Community<N>> getCommunities();
	void addCommunity(Community<N> community);
	void addNode(N node, Set<N> community);	
	public Community<N> getNodesCommunity(N node);
	

}
