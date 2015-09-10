package edu.pw.elka.gtna.graph;

import java.util.HashMap;
import java.util.Map;

import edu.pw.elka.gtna.graph.creator.interfaces.GraphCreator;
import edu.pw.elka.gtna.graph.interfaces.EdgeType;
import edu.pw.elka.gtna.graph.interfaces.Node;
import edu.pw.elka.gtna.graph.interfaces.WeightedEdge;
import edu.pw.elka.gtna.graph.interfaces.WeightedGraph;

public class WeightedGraphLinkedListImpl<N extends Node,E extends WeightedEdge<N>> extends GraphLinkedListImpl<N, E> 
implements WeightedGraph<N,E> {

	
	Map<E,Double> edgeWeights;
	
	WeightedGraphLinkedListImpl(){
		super();
	}

	public WeightedGraphLinkedListImpl(GraphCreator<E> gc) {
		super(gc);
	}
	
	@Override
	public boolean addEdge(E e) {
		addNode(e.getN1());
		addNode(e.getN2());
		
		if (!graphLinkedListData.get(e.getN1()).contains(e)) {
			graphLinkedListData.get(e.getN1()).add(e);			
			graphLinkedListData.get(e.getN2()).add(e);				
			
			if (edgeWeights == null){
				edgeWeights = new HashMap<E,Double>();
			}
			edgeWeights.put(e, e.getWeight());
			
			
			return true;
		}
		return false;
	}
	
	@Override
	public int getWeight(N n1, N n2) {
		WeightedEdge<Node> e = (WeightedEdge<Node>) EdgeFactory.<Node>newInstance(n1,n2,EdgeType.WEIGHTED);
		return edgeWeights.get(e).intValue();
	}
	
	
}
