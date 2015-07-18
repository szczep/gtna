package edu.pw.elka.gtna.graph.evaluator;

import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class AverageDegreeEvaluator<N extends Node,E extends Edge<N>> extends AbstractGraphEvaluator<N,E> {
	
	public AverageDegreeEvaluator(Graph<N, E> graph) {
		super(graph);
	}

	@Override
	public double evaluate() {

		return this.graph.getEdgesNumber()*2.0/(graph.getNodesNumber());
	}

}
