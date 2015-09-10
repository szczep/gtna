package edu.pw.elka.gtna.examples;

import edu.pw.elka.gtna.centrality.closeness.CSClosenessCentrality;
import edu.pw.elka.gtna.centrality.closeness.CVClosenessCentrality;
import edu.pw.elka.gtna.centrality.closeness.NEWCVClosenessCentrality;
import edu.pw.elka.gtna.centrality.closeness.OVCVClosenessCentrality;
import edu.pw.elka.gtna.centrality.closeness.OVClosenessCentrality;
import edu.pw.elka.gtna.centrality.closeness.SVClosenessCentrality;
import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.centrality.utils.Ranking;
import edu.pw.elka.gtna.graph.CommunityStructureImp;
import edu.pw.elka.gtna.graph.GraphFactory;
import edu.pw.elka.gtna.graph.OverlappingCommunityStructureImpl;
import edu.pw.elka.gtna.graph.creator.ScaleFreeGraphGenerator;
import edu.pw.elka.gtna.graph.interfaces.CommunityStructure;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class ClosenessCentralitiesExample {

	public static void main(String[] args) {
		Graph<Node,Edge<Node>> gr = 
				GraphFactory.<Node,Edge<Node>>newSimpleInstance(new ScaleFreeGraphGenerator(100, 10), GraphType.SIMPLE);
		
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
		
		
		
		Centrality<Node> OVCen = new OVClosenessCentrality<Node,Edge<Node>>(cs);
		OVCen.computeCentrality();
		Ranking<Node> rOVCen = new Ranking<Node>(OVCen);
		
		Centrality<Node> OVCVCen = new OVCVClosenessCentrality<Node,Edge<Node>>(cs);
		OVCVCen.computeCentrality();
		Ranking<Node> rOVCVCen = new Ranking<Node>(OVCVCen);
		
		
		Centrality<Node> NEWSVOV = new NEWCVClosenessCentrality<Node,Edge<Node>>(cs);
		NEWSVOV.computeCentrality();
		Ranking<Node> rNEWSVOV = new Ranking<Node>(NEWSVOV);
		
		
		Ranking.<Node>displayRankings(rOV,rSV,rCV,rOVCen,rOVCVCen,rNEWSVOV);

//		double sum = 0.0;
//		for (Node n : gr.getNodes()){
//			sum+=c.getCentrality(n);
//		}
//
//		System.out.println("sum: "+sum);
	}

}
