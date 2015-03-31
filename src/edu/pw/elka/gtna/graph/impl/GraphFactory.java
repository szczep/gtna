package edu.pw.elka.gtna.graph.impl;

import edu.pw.elka.gtna.graph.DirectedEdge;
import edu.pw.elka.gtna.graph.Edge;
import edu.pw.elka.gtna.graph.Graph;
import edu.pw.elka.gtna.graph.GraphType;
import edu.pw.elka.gtna.graph.Node;

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
