package edu.pw.elka.gtna.graph;

public class NodeImpl extends AbstractNode {


	NodeImpl(){
	}
	
	NodeImpl(String label){
		super(label);
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
		@SuppressWarnings("unchecked")
		NodeImpl other = (NodeImpl) obj;
		
		if (label == null || other.label == null)
			return false;

		
		return label.equals(other.label);
	}

}
