package edu.pw.elka.gtna.centrality;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.graph.interfaces.CommunityStructure;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
abstract public class AbstractCentrality<T,N extends Node,E extends Edge<N>> implements Centrality<T>{
	
	protected Graph<N,E> graph;
	protected CommunityStructure<N,E> communityStructure;
	
	protected  Map<T, Double> centralities = new LinkedHashMap<T, Double>();
	
	
	public AbstractCentrality (Graph<N,E> graph){
		this.graph = graph;
	}
	
	public AbstractCentrality (CommunityStructure<N,E> communityStructure){
		this.communityStructure = communityStructure;
		this.graph = communityStructure.getGraph();
	}
	

	public double getCentrality (T element){
		return centralities.get(element);
	}	


    public void printCentralities(){
        for(T element: centralities.keySet()){
            System.out.println(element+"\t"+centralities.get(element));
        }
    }

}
