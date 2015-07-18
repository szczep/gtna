package edu.pw.elka.gtna.graph.evaluator;


import java.util.Set;

import edu.pw.elka.graph.algorithms.FindComponents;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphEvaluator;
import edu.pw.elka.gtna.graph.interfaces.Node;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public class LargestComponentEvaluator<N extends Node,E extends Edge<N>> extends AbstractGraphEvaluator<N,E> {

	
	FindComponents<N,E> fc;
     
	/**
	 * @param graph
	 */
	public LargestComponentEvaluator(Graph<N,E> graph) {
		super(graph);
		fc = new FindComponents<N,E>(graph);
	}


	@Override
	public double evaluate() {
		
		double largestComponent = 0.0;
		fc.compute();
		
		for (Set<N> component: fc.getComponents()){
			if (component.size() > largestComponent)
				largestComponent=component.size();
		}		
		return largestComponent/graph.getNodesNumber();
		
	}
	

}
