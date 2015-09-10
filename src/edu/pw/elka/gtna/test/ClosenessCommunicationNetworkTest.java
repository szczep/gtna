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
import edu.pw.elka.gtna.graph.creator.TGFReader;
import edu.pw.elka.gtna.graph.interfaces.Community;
import edu.pw.elka.gtna.graph.interfaces.CommunityStructure;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class ClosenessCommunicationNetworkTest  extends AbstractTester {

	
	
	public static void main(String[] args) throws InterruptedException {
		
		// graph
		String tgfFile = "warsaw_graph.tgf";
		// community structure
		String tgfCommFile = "warsaw_communities.in";
		

		Graph<Node,Edge<Node>> gr = 
				GraphFactory.<Node,Edge<Node>>newSimpleInstance(new TGFReader(tgfFile),GraphType.SIMPLE);
		
		
		CommunityStructure<Node,Edge<Node>> cs = new CommunityStructureImp<Node,Edge<Node>>(gr);
		
		
		CommunityStructure<Node,Edge<Node>> OCS = new OverlappingCommunityStructureImpl<Node,Edge<Node>>(gr, new TCSReader(tgfCommFile));
		
		for (Community<Node> C: OCS.getCommunities()){
//			System.out.println(C);
		}
		Set<Node> nodes = new LinkedHashSet();
		for (Community<Node> C: OCS.getCommunities()){
			nodes.addAll(C.getNodes());
		}
		//System.out.println(nodes);
		Set<Node> nodesG = gr.getNodes();
		
		System.out.println(gr.getNodesNumber()+" "+gr.getEdgesNumber());
//		System.out.println(nodesG.containsAll(nodes));
//		System.out.println(nodes.containsAll(nodesG));
//		nodes.removeAll(nodesG);
//		System.out.println(nodes);
		
		
		
		Centrality<Node> SC = new ClosenessCentrality<Node,Edge<Node>>(gr){

			@Override
			protected double fDistance(double d) {
				if (d == 0)
					return 0.0;
				return 1.0/d;
			}
			
		};
//		SC.computeCentrality();
//		Ranking<Node> rSC = new Ranking<Node>(SC);
//		
//		Centrality<Node> SVC = new SVClosenessCentrality<Node,Edge<Node>>(gr);
//		SVC.computeCentrality();
//		Ranking<Node> rSV = new Ranking<Node>(SVC);
//
//
//		Centrality<Node>  CVC = new OVCVClosenessCentrality<Node,Edge<Node>>(OCS);
//		CVC.computeCentrality();
//		Ranking<Node> rCV = new Ranking<Node>(CVC);
//		
//		Ranking.<Node>displayRankings(rSC,rSV,rCV);
		
	}
	
	
	
	
	

}
