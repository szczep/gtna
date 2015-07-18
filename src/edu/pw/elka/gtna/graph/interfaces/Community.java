package edu.pw.elka.gtna.graph.interfaces;

import java.util.Set;

public interface Community<N extends Node> extends  Iterable<N> {
	String getLabel();	
	void setLabel(String label);
	
	Set<N> getNodes();
	void addNode(N node);
	boolean contains(N node);
	int size();
}
