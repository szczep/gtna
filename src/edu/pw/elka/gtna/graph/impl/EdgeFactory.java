package edu.pw.elka.gtna.graph.impl;

import edu.pw.elka.gtna.graph.Edge;
import edu.pw.elka.gtna.graph.EdgeType;
import edu.pw.elka.gtna.graph.Node;

public class EdgeFactory {
	
	
    public static<N extends Node> Edge<N> newInstance(N n1, N n2, EdgeType type)
    {
    	Edge<N> e = EdgeFactory.newInstance(type);
    	e.setN1(n1);
    	e.setN2(n2);
    	return e;
    }

	public static<N extends Node> Edge<N> newInstance(EdgeType type)
    {
    	Edge<N> e = null;
        switch(type)
        {
            case SIMPLE:
            e = new EdgeImpl<N>();
            break;
            case DIRECTED:
            e = new DirectedEdgeImpl<N>();
            break;
            default:
            ;
        }
    return e;
    }
    
}
