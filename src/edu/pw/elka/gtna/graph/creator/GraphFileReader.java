package edu.pw.elka.gtna.graph.creator;


import edu.pw.elka.gtna.graph.creator.interfaces.GraphCreator;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Node;

/**
 * @author Piotr Lech Szczepański
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public abstract class GraphFileReader<E extends Edge<? extends Node>> extends FileParser<E> 
implements GraphCreator<E> {
	
	protected E edge;

	public GraphFileReader(String fileName){
		super(fileName);
	}


	
}
