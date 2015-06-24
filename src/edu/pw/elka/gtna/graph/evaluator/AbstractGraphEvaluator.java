package edu.pw.elka.gtna.graph.evaluator;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 * @param <N>
 *
 */
abstract public class AbstractGraphEvaluator<N extends Node,E extends Edge<N>> {

	protected Graph<N,E> graph;
	

	public AbstractGraphEvaluator(Graph<N,E> graph) {
		this.graph = graph;
	}	
	
	abstract public double evaluate();

	
}
