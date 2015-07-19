package edu.pw.elka.gtna.graph;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import edu.pw.elka.gtna.graph.interfaces.Community;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class CommunityImpl<N extends Node> implements Community<N> {

	Set<N> nodes;
	String label;
	
	public CommunityImpl(){
		this.nodes = new LinkedHashSet<N>();
	}
	
	public CommunityImpl(Set<N> nodes){
		this();
		nodes.addAll(nodes);
	}
	
	public CommunityImpl(N node){
		this();
		nodes.add(node);
	}
	
	@Override
	public Iterator<N> iterator() {
		return nodes.iterator();
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public Set<N> getNodes() {
		return new LinkedHashSet<N>(nodes);
	}

	@Override
	public void addNode(N node) {
		nodes.add(node);
	}
	
	@Override
	public int hashCode() {
		final int prime = 79;
		int result = 17;
		result = prime * result * ((label == null) ? 0 : label.hashCode());
		return result; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommunityImpl<N> other = (CommunityImpl<N>) obj;
		
		if (label == null || other.label == null)
			return false;

		
		return label.equals(other.label);
	}


	@Override
	public boolean contains(N node) {
		return nodes.contains(node);
	}

	@Override
	public int size() {
		return nodes.size();
	}
	
	@Override
	public String toString() {
		return nodes.toString();
	}

}
