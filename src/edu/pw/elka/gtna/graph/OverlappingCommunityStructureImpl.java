package edu.pw.elka.gtna.graph;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import edu.pw.elka.gtna.graph.interfaces.Community;
import edu.pw.elka.gtna.graph.interfaces.CommunityStructure;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class OverlappingCommunityStructureImpl<N extends Node,E extends Edge<N>> implements CommunityStructure<N,E>{

	
	protected Graph<N,E> graph;
	Map<N,Set<Community<N>>> communitiesLinkedListData;
	Set<Community<N>> communities;
	
	/**
	 * @param graph
	 */
	public OverlappingCommunityStructureImpl(Graph<N, E> graph) {
		super();
		this.graph = graph;
		this.communitiesLinkedListData = new LinkedHashMap<N,Set<Community<N>>>();	
		this.communities = new LinkedHashSet<Community<N>>();
		for (N n : graph.getNodes()){
			Community<N> C = new CommunityImpl<N>(n);
			C.setLabel(n.getLabel());
			Set<Community<N>> Cn = new LinkedHashSet<Community<N>>();
			Cn.add(C);
			communitiesLinkedListData.put(n, Cn);
			communities.add(C);
		}
	}

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
		return  new LinkedHashSet<Community<N>>(communities);
	}

	@Override
	public void addCommunity(Community<N> community) {
		for (N node: community){
			communitiesLinkedListData.get(node).add(community);
		}
		communities.add(community);
	}

	@Override
	public void addNode(Node node, Community<N> community) {
		// TODO Auto-generated method stub		
	}

	@Override
	public Community<N> getNodeCommunity(Node node) {
		throw new UnsupportedOperationException("Invalid operation for Overlapping Community Structure Implementation.");
	}

	@Override
	public int size() {
		return communities.size();
	}

	@Override
	public Set<Community<N>> getNodeCommunities(N node) {
		return new LinkedHashSet<Community<N>>(communitiesLinkedListData.get(node));
	}

}
