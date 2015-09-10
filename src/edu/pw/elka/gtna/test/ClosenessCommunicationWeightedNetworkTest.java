package edu.pw.elka.gtna.test;

import java.util.LinkedHashSet;
import java.util.Set;

import edu.pw.elka.gtna.centrality.closeness.ClosenessCentrality;
import edu.pw.elka.gtna.centrality.closeness.OVCVClosenessCentrality;
import edu.pw.elka.gtna.centrality.closeness.SVClosenessCentrality;
import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.centrality.utils.Ranking;
import edu.pw.elka.gtna.graph.CommunityStructureImp;
import edu.pw.elka.gtna.graph.GraphFactory;
import edu.pw.elka.gtna.graph.OverlappingCommunityStructureImpl;
import edu.pw.elka.gtna.graph.creator.TCSReader;
import edu.pw.elka.gtna.graph.creator.TGFWReader;
import edu.pw.elka.gtna.graph.interfaces.Community;
import edu.pw.elka.gtna.graph.interfaces.CommunityStructure;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;
import edu.pw.elka.gtna.graph.interfaces.WeightedEdge;

public class ClosenessCommunicationWeightedNetworkTest  extends AbstractTester {

	
	
	public static void main(String[] args) throws InterruptedException {
		
		// graph
		String tgfFile = "warsaw_graph.tgf";
		// community structure
		String tgfCommFile = "warsaw_communities.in";
		

		Graph<Node,WeightedEdge<Node>> gr = 
				GraphFactory.<Node,WeightedEdge<Node>>newWeightedInstance(new TGFWReader(tgfFile));
	
		GraphFactory.newWeightedInstance(new TGFWReader(tgfFile));
		
		CommunityStructure<Node,WeightedEdge<Node>> cs = new CommunityStructureImp<Node,WeightedEdge<Node>>(gr);
		
		
		CommunityStructure<Node,WeightedEdge<Node>> OCS = 
				new OverlappingCommunityStructureImpl<Node,WeightedEdge<Node>>(gr, new TCSReader(tgfCommFile));
		
		System.out.println(OCS.size());
		System.out.println(gr.getNodesNumber()+" "+gr.getEdgesNumber());
		
		
//		for (Community<Node> C: OCS.getCommunities()){
//			System.out.println(C);
//		}
		Set<Node> nodes = new LinkedHashSet();
		for (Community<Node> C: OCS.getCommunities()){
			nodes.addAll(C.getNodes());
		}
		//System.out.println(nodes);
		Set<Node> nodesG = gr.getNodes();
		
	//	System.out.println(gr.getNodesNumber()+" "+gr.getEdgesNumber());
		System.out.println(nodesG.containsAll(nodes));
		System.out.println(nodes.containsAll(nodesG));
		nodes.removeAll(nodesG);
		System.out.println(nodes);
		System.out.println("");
		System.out.println(nodesG);
		
		
		
//		Centrality<Node> SC = new ClosenessCentrality<Node,WeightedEdge<Node>>(gr){
//
//			@Override
//			protected double fDistance(double d) {
//				if (d == 0)
//					return 0.0;
//				return 1.0/d;
//			}
//			
//		};
//		SC.computeCentrality();
//		Ranking<Node> rSC = new Ranking<Node>(SC);
//		
//		Centrality<Node> SVC = new SVClosenessCentrality<Node,WeightedEdge<Node>>(gr);
//		SVC.computeCentrality();
//		Ranking<Node> rSV = new Ranking<Node>(SVC);
//
//
//		Centrality<Node>  CVC = new OVCVClosenessCentrality<Node,WeightedEdge<Node>>(OCS);
//		CVC.computeCentrality();
//		Ranking<Node> rCV = new Ranking<Node>(CVC);
//		
//		Ranking.<Node>displayRankings(rSC,rSV,rCV);
		
	}
	
	
	
	
	

}
