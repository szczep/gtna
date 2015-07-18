package edu.pw.elka.gtna.test;

import static edu.pw.elka.gtna.graph.interfaces.EdgeType.SIMPLE;
import edu.pw.elka.gtna.graph.EdgeFactory;
import edu.pw.elka.gtna.graph.NodeFactory;
import edu.pw.elka.gtna.graph.NodeType;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class TEST {

	public static void main(String[] args) {
		
		Node n1 = NodeFactory.newInstance("1", NodeType.SIMPLE);
		Node n2 = NodeFactory.newInstance("2", NodeType.SIMPLE);
		 Edge<Node> e1 = EdgeFactory.<Node>newInstance(n1, n2 ,SIMPLE);
		 
			Node n3 = NodeFactory.newInstance("1", NodeType.SIMPLE);
			Node n4= NodeFactory.newInstance("1", NodeType.SIMPLE);
		 
			 Edge<Node> e2 = EdgeFactory.<Node>newInstance(n3, n4 ,SIMPLE);	
			 
			 System.out.println(e1.equals(e2));
			
	}

}
