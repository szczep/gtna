package edu.pw.elka.gtna.graph;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import edu.pw.elka.gtna.graph.interfaces.Community;
import edu.pw.elka.gtna.graph.interfaces.CommunityStructure;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class CommunityStructureImp<N extends Node,E extends Edge<N>> implements CommunityStructure<N,E>{

	protected Graph<N,E> graph;
	Map<N,Community<N>> communitiesLinkedListData;
	
	@Override
	public Graph<N,E> getGraph() {
		return graph;
	}

	@Override
	public void setGraph(Graph<N,E> graph) {
		this.graph=graph;
	}

	@Override
	public Set<Community<N>> getCommunities() {
		return  new LinkedHashSet<Community<N>>(communitiesLinkedListData.values());
	}

	@Override
	public void addCommunity(Community<N> community) {
		for (N node: community){
			communitiesLinkedListData.put(node, community);
		}
	}

	@Override
	public void addNode(Node node, Set community) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Community getNodesCommunity(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

}
