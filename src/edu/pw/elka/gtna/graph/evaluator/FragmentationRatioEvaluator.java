package edu.pw.elka.gtna.graph.evaluator;

import edu.pw.elka.gtna.graph.algorithms.FindComponents;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class FragmentationRatioEvaluator<N extends Node,E extends Edge<N>> extends AbstractGraphEvaluator<N,E> {

	FindComponents<N,E> fc;
    
	
	public FragmentationRatioEvaluator(Graph<N, E> graph) {
		super(graph);
		fc = new FindComponents<N,E>(graph);
	}

	@Override
	public double evaluate() {
		
		fc.compute();
				
		return 1/fc.getComponents().size();
		
	}
}
