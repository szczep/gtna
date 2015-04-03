package edu.pw.elka.gtna.examples;

import edu.pw.elka.gtna.graph.EdgeFactory;
import edu.pw.elka.gtna.graph.GraphFactory;
import edu.pw.elka.gtna.graph.NodeFactory;
import edu.pw.elka.gtna.graph.NodeType;
import edu.pw.elka.gtna.graph.interfaces.DirectedEdge;
import edu.pw.elka.gtna.graph.interfaces.DirectedGraph;
import edu.pw.elka.gtna.graph.interfaces.EdgeType;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class DirectedGraphExample {
	
	public static void main(String[] args) {
		DirectedGraph<Node,DirectedEdge<Node>> dg = 
				GraphFactory.<Node,DirectedEdge<Node>>newDirectedInstance(GraphType.DIRECTED);
		
		
		Node n1 = NodeFactory.newInstance("1", NodeType.SIMPLE);
		Node n2 = NodeFactory.newInstance("2", NodeType.SIMPLE);
		dg.addEdge((DirectedEdge<Node>)EdgeFactory.<Node>newInstance(n1, n2, EdgeType.DIRECTED));
		
	}
	
}
