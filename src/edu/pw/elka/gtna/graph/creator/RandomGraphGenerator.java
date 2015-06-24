package edu.pw.elka.gtna.graph.creator;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import edu.pw.elka.gtna.graph.creator.interfaces.GraphCreator;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Node;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
abstract public class RandomGraphGenerator<E extends Edge<? extends Node>> implements GraphCreator<E> {



	List<E> edges = new ArrayList<E>();
	private int counter = 0;
	protected int nodesNumber;
	
	Random random = new Random();
	
	public RandomGraphGenerator (int nodesNumber){
		this.nodesNumber = nodesNumber;
	}
	
	@Override
	public boolean hasNext() {
		return counter<edges.size();
	}

	@Override
	public E next() {
		counter++;
		return edges.get(counter-1);
	}
	

	public void setNodesNumber(int nodesNumber) {
		this.nodesNumber = nodesNumber;		
	}	
	

	@Override
	public void remove() {
		throw new UnsupportedOperationException();			
	}
	

	@Override
	public Iterator<E> iterator() {
		return this;
	}
	
}
