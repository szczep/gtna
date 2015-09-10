package edu.pw.elka.gtna.graph.evaluator;


import static edu.pw.elka.gtna.graph.interfaces.EdgeType.SIMPLE;
import edu.pw.elka.gtna.graph.EdgeFactory;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class ClusteringCoefficientEvaluator<N extends Node,E extends Edge<N>> extends AbstractGraphEvaluator<N,E> {

	public ClusteringCoefficientEvaluator(Graph<N, E> graph) {
		super(graph);
	}

	@Override
	public double evaluate() {
		
		double clustering = 0;
		
		for (N v: graph.getNodes()){
			double connectedPairs = 0;
			for (N n1: graph.getNeighbours(v)){
				for (N n2: graph.getNeighbours(v)){
					if (graph.hasEdge((E) EdgeFactory.<N>newInstance(n1,n2,SIMPLE))){
						connectedPairs++;
					}
				}
				if (graph.getDegree(v)>1){
					clustering += connectedPairs/((graph.getDegree(v))*(graph.getDegree(v)-1));
				}
			}
		}
		return clustering/graph.getNodesNumber();
	}

}
