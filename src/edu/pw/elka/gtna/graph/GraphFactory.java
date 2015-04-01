package edu.pw.elka.gtna.graph;

import edu.pw.elka.gtna.graph.interfaces.DirectedEdge;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class GraphFactory {


	public static<N extends Node,E extends DirectedEdge<N>> Graph<N,E> newInstance(GraphType type)
    {
		Graph<N,E> g = null;
        switch(type)
        {
            case SIMPLE:
            g = new GraphLinkedListImpl<N,E>();
            break;
            case DIRECTED:
            g = new DirectedGraphLinkedListImpl<N,E>();
            break;
            default:
            ;
        }
    return g;
    }
}
