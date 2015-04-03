package edu.pw.elka.gtna.graph;

import edu.pw.elka.gtna.graph.interfaces.Node;

abstract public class AbstractNode implements Node {
	
	protected String label;
	
	
	AbstractNode(){
	}
	
	AbstractNode(String label){
		this.label = label;
	}
	
	@Override
	public abstract boolean equals(Object other);
	@Override
	public abstract int hashCode();
	
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override 
	public String toString() {
		return label;
	}
	
}
