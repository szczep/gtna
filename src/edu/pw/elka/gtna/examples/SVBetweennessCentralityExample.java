package edu.pw.elka.gtna.examples;

import java.text.DecimalFormat;

import edu.pw.elka.gtna.centrality.betweenness.SVBetweennessCentrality;
import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.graph.GraphFactory;
import edu.pw.elka.gtna.graph.creator.TGFReader;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;


public class SVBetweennessCentralityExample {
	
	public static void main(String[] args) {
		
		// the file in trivial graph format
		String tgfFile = "example.tgf";
		
		// creating new undirected graph
		Graph<Node,Edge<Node>> g = 
				GraphFactory.<Node,Edge<Node>>newSimpleInstance(new TGFReader(tgfFile),GraphType.SIMPLE);
		
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
