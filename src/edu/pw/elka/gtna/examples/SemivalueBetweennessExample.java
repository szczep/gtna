package edu.pw.elka.gtna.examples;

import edu.pw.elka.gtna.centrality.betweenness.SVBetweennessCentrality;
import edu.pw.elka.gtna.centrality.betweenness.SemivalueNodeBetweenness;
import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.graph.GraphFactory;
import edu.pw.elka.gtna.graph.creator.ScaleFreeGraphGenerator;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class SemivalueBetweennessExample {
	
	
	public static void main(String[] args) {
			
		final Graph<Node,Edge<Node>> g = 
				GraphFactory.<Node,Edge<Node>>newSimpleInstance(new ScaleFreeGraphGenerator(500, 4), GraphType.SIMPLE);

		// creating new centrality metric (Semivalue-based betweenness centrality)
		Centrality<Node> sb = new SemivalueNodeBetweenness<Node>(g){
			@Override
			protected double distribution(int k){
				return 1.0/g.getNodesNumber();
			}
		};

		sb.computeCentrality();
		sb.printCentralities();
		
		
		
		Centrality<Node> svb = new SVBetweennessCentrality<Node>(g);
		svb.computeCentrality();
		svb.printCentralities();

		
		
		
	}

}
