package edu.pw.elka.gtna.examples;

import edu.pw.elka.gtna.graph.CommunityImpl;
import edu.pw.elka.gtna.graph.NodeFactory;
import edu.pw.elka.gtna.graph.NodeType;
import edu.pw.elka.gtna.graph.interfaces.Community;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class CommunityStructureExample {
	
	public static void main(String[] args) {
		Community<Node> c = new CommunityImpl<Node>();
		c.addNode(NodeFactory.newInstance("1", NodeType.SIMPLE));
		c.addNode(NodeFactory.newInstance("2", NodeType.SIMPLE));
		c.addNode(NodeFactory.newInstance("3", NodeType.SIMPLE));
	
		for(Node n: c){
			System.out.println(n);
		}
		
	}
}
