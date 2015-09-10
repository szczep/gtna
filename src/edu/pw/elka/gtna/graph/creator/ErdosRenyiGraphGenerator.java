package edu.pw.elka.gtna.graph.creator;

import java.util.Random;

import edu.pw.elka.gtna.graph.EdgeFactory;
import edu.pw.elka.gtna.graph.NodeFactory;
import edu.pw.elka.gtna.graph.NodeType;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.EdgeType;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class ErdosRenyiGraphGenerator extends RandomGraphGenerator<Edge<Node>> {

	protected void addEdge(Edge<Node> edge){
		edges.add(edge);
	}
	
	public ErdosRenyiGraphGenerator(int nodesNumber, double edgeProbability) {
		super(nodesNumber);

		Random rand = new Random();
		
		for (int i = 1; i <= nodesNumber; i++){
			for (int j = i+1; j <= nodesNumber; j++){
				if ( edgeProbability > rand.nextDouble() ) {
					addEdge(EdgeFactory.<Node>newInstance(
							NodeFactory.newInstance(String.valueOf(i), NodeType.SIMPLE),
							NodeFactory.newInstance(String.valueOf(j), NodeType.SIMPLE),
							EdgeType.SIMPLE));
				}
			}
		}
		
	}
	

}
