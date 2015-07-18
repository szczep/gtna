package edu.pw.elka.gtna.graph.interfaces;

import java.util.Set;


public interface CommunityStructure<N extends Node,E extends Edge<N>> {
	
	
	Graph<N,E> getGraph();
	void setGraph(Graph<N,E> graph);
	
	Set<Community<N>> getCommunities();
	void addCommunity(Community<N> community);
	void addNode(Node node, Community<N> community);
	Community<N> getNodeCommunity(N node);
	int size();

}
