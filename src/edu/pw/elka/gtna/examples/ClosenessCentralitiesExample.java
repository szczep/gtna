package edu.pw.elka.gtna.examples;

import edu.pw.elka.gtna.centrality.closeness.CSClosenessCentrality;
import edu.pw.elka.gtna.centrality.closeness.CVClosenessCentrality;
import edu.pw.elka.gtna.centrality.closeness.SVClosenessCentrality;
import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.graph.CommunityStructureImp;
import edu.pw.elka.gtna.graph.GraphFactory;
import edu.pw.elka.gtna.graph.OverlappingCommunityStructureImpl;
import edu.pw.elka.gtna.graph.creator.ScaleFreeGraphGenerator;
import edu.pw.elka.gtna.graph.interfaces.CommunityStructure;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;
import edu.pw.elka.gtna.ranking.Ranking;

public class ClosenessCentralitiesExample {

	public static void main(String[] args) {
		Graph<Node,Edge<Node>> gr = 
				GraphFactory.<Node,Edge<Node>>newSimpleInstance(new ScaleFreeGraphGenerator(13, 4), GraphType.SIMPLE);
		
		CommunityStructure<Node,Edge<Node>> cs = new CommunityStructureImp<Node,Edge<Node>>(gr);
		CommunityStructure<Node,Edge<Node>> overLappingCS = new OverlappingCommunityStructureImpl<Node,Edge<Node>>(gr);
		
		Centrality<Node> SVC = new SVClosenessCentrality<Node,Edge<Node>>(gr);
		SVC.computeCentrality();
		Ranking<Node> rSV = new Ranking<Node>(SVC);
		
		Centrality<Node> OVC = new CSClosenessCentrality<Node,Edge<Node>>(cs);
		OVC.computeCentrality();
		Ranking<Node> rOV = new Ranking<Node>(OVC);

		Centrality<Node>  CVC = new CVClosenessCentrality<Node,Edge<Node>>(overLappingCS);
		CVC.computeCentrality();
		Ranking<Node> rCV = new Ranking<Node>(CVC);
		
		Ranking.<Node>displayRankings(rOV,rSV,rCV);

//		double sum = 0.0;
//		for (Node n : gr.getNodes()){
//			sum+=c.getCentrality(n);
//		}
//
//		System.out.println("sum: "+sum);
	}

}
