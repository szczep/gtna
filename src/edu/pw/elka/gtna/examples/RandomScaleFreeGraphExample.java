package edu.pw.elka.gtna.examples;

import java.text.DecimalFormat;

import edu.pw.elka.gtna.centrality.betweenness.SVBetweennessCentrality;
import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.graph.GraphFactory;
import edu.pw.elka.gtna.graph.creator.ScaleFreeGraphGenerator;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class RandomScaleFreeGraphExample {
	public static void main(String[] args) {
	
		Graph<Node,Edge<Node>> g = 
				GraphFactory.<Node,Edge<Node>>newSimpleInstance(new ScaleFreeGraphGenerator(20, 3), GraphType.SIMPLE);
		
		// creating new centrality metric (Shapley value based betweenness centrality)
		Centrality<Node> bc = new SVBetweennessCentrality<Node>(g);
		bc.computeCentrality();
		bc.printCentralities();
		
		
		double sum = 0.0;
		for (Node n: g.getNodes()){
			sum+=bc.getCentrality(n);
		}
		
		DecimalFormat df=new DecimalFormat("0.0000");
		String formate = df.format(sum); 
		System.out.println("\n\nSum: "+formate);
		
	}
}
