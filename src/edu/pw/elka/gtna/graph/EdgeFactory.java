package edu.pw.elka.gtna.graph;

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
            e = new EdgeImpl<N>(EdgeType.SIMPLE);
            break;
            case DIRECTED:
            e = new EdgeImpl<N>(EdgeType.DIRECTED);
            break;
            default:
            ;
        }
    return e;
    }
    
}
