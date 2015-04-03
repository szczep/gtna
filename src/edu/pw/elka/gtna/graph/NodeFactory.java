package edu.pw.elka.gtna.graph;


import edu.pw.elka.gtna.graph.interfaces.Node;

public class NodeFactory {
	
    public static Node newInstance(String label, NodeType type)
    {
    	Node n = NodeFactory.newInstance(type);
    	n.setLabel(label);
    	return n;
    }

	public static <N> Node newInstance(NodeType type)
    {
		Node n = null;
        switch(type)
        {
            case SIMPLE:
            n = new NodeImpl();
            break;
            default:
            ;
        }
    return n;
    }
}
