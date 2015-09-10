package edu.pw.elka.gtna.examples;

import java.util.Set;

import edu.pw.elka.gtna.centrality.betweenness.BetweennessCentrality;
import edu.pw.elka.gtna.centrality.betweenness.SemivalueNodeBetweenness;
import edu.pw.elka.gtna.centrality.closeness.OVClosenessCentrality;
import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.centrality.utils.FeatureScalingNormalization;
import edu.pw.elka.gtna.graph.GraphFactory;
import edu.pw.elka.gtna.graph.creator.ScaleFreeGraphGenerator;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;
import edu.pw.elka.gtna.utils.MathFactors;
import edu.pw.elka.gtna.utils.RandomSubset;

public class MainTest {

	public static void main(String[] args) {
		final Graph<Node,Edge<Node>> gr = 
				GraphFactory.<Node,Edge<Node>>newSimpleInstance(new ScaleFreeGraphGenerator(20, 3), GraphType.SIMPLE);
		
		Centrality<Node> nB = new BetweennessCentrality<Node>(gr);
		nB.computeCentrality();
		final FeatureScalingNormalization<Node> NB = new FeatureScalingNormalization<Node>(nB);

		
    	Centrality<Node> PSV = new SemivalueNodeBetweenness<Node>(gr){
			@Override
			protected double distribution(int k){
				if (k>=0 && k<gr.getNodesNumber())
					return 1.0/(gr.getNodesNumber()-0);
				else return 0.0;
			}
		};
		
		PSV.computeCentrality();
		final FeatureScalingNormalization<Node> NPSV = new FeatureScalingNormalization<Node>(PSV);

		
		final double pow2 = Math.pow(2.0, gr.getNodesNumber());
    	Centrality<Node> PB = new SemivalueNodeBetweenness<Node>(gr){
			@Override
			protected double distribution(int k){
				return MathFactors.biNom(gr.getNodesNumber(),k)/pow2;
			}
		};

		PB.computeCentrality();
		final FeatureScalingNormalization<Node> NPB = new FeatureScalingNormalization<Node>(PB);

		
		double sum1 = 0.0;
		double sum2 = 0.0;
		double sum3 = 0.0;
		for (Node n : gr.getNodes()){
			System.out.println(n+" "+NB.getNormCentrality(n)+" "+NPSV.getNormCentrality(n)+" "+NPB.getNormCentrality(n));
			sum1+=NB.getNormCentrality(n);
			sum2+=NPSV.getNormCentrality(n);
			sum3+=NPB.getNormCentrality(n);
		}
		
		System.out.println("SUM "+sum1+" "+sum2+" "+sum3);
		RandomSubset<Node> rs = new RandomSubset<Node>(gr.getNodes());
		rs.setIterationsLimitation(10);
		//rs.setSize(rand.nextInt((b2-a2))+a2);
		rs.setSize(3);
		int i = 0;
		for(Set<Node> failers: rs){
			System.out.println(failers);
		}
	}

}
