package edu.pw.elka.gtna.graph;

import edu.pw.elka.gtna.graph.creator.interfaces.GraphCreator;
import edu.pw.elka.gtna.graph.interfaces.DirectedEdge;
import edu.pw.elka.gtna.graph.interfaces.DirectedGraph;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;
import edu.pw.elka.gtna.graph.interfaces.WeightedEdge;
import edu.pw.elka.gtna.graph.interfaces.WeightedGraph;

public class GraphFactory {

	public static<N extends Node,E extends DirectedEdge<N>> DirectedGraph<N,E> newDirectedInstance(GraphType type)
    {
		DirectedGraph<N,E> g = null;
        switch(type)
        {
            case DIRECTED:
            g = new DirectedGraphLinkedListImpl<N,E>();
            break;
            default:
            ;
        }
    return g;
    }
	
	public static<N extends Node,E extends WeightedEdge<N>> WeightedGraph<N,E> newWeightedInstance(){
		return new WeightedGraphLinkedListImpl<N,E>();
    }
	
	public static<N extends Node,E extends WeightedEdge<N>> WeightedGraph<N,E> newWeightedInstance(GraphCreator<E> gc){
		return new WeightedGraphLinkedListImpl<N,E>(gc);
    }
	
	
	public static<N extends Node,E extends Edge<N>> Graph<N,E> newSimpleInstance(GraphCreator<E> gc, GraphType type)
    {
		Graph<N,E> g = null;
        switch(type)
        {
            case SIMPLE:
            g = new GraphLinkedListImpl<N,E>(gc);
            break;
            default:
            ;
        }
    return g;
    }
	
	public static<N extends Node,E extends Edge<N>> Graph<N,E> newSimpleInstance(GraphType type)
    {
		Graph<N,E> g = null;
        switch(type)
        {
            case SIMPLE:
            g = new GraphLinkedListImpl<N,E>();
            break;
            default:
            ;
        }
    return g;
    }
}
