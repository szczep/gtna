package edu.pw.elka.gtna.graph.interfaces;


public interface WeightedEdge<N extends Node> extends Edge<N> {
	double getWeight();
	void setWeight(double weight);
}
