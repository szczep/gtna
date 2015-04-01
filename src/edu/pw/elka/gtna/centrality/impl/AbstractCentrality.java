package edu.pw.elka.gtna.centrality.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.pw.elka.gtna.centrality.Centrality;
import edu.pw.elka.gtna.graph.Edge;
import edu.pw.elka.gtna.graph.Graph;
import edu.pw.elka.gtna.graph.Node;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
abstract public class AbstractCentrality<T,N extends Node,E extends Edge<N>> implements Centrality<T>{
	
	protected Graph<N,E> graph;
	protected  Map<T, Double> centralities = new LinkedHashMap<T, Double>();
	
	public AbstractCentrality (Graph<N,E> graph){
		this.graph = graph;
	}
	

	public double getCentrality (T element){
		return centralities.get(element);
	}	
	
	
	/**
	 * This function computes centralities for all nodes
	 */
	abstract public  void computeCentrality();

    public void printCentralities(){
        for(T element: centralities.keySet()){
            System.out.println(element+" "+centralities.get(element));
        }
    }

}
