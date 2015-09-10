package edu.pw.elka.gtna.test;


import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import edu.pw.elka.gtna.centrality.betweenness.BetweennessCentrality;
import edu.pw.elka.gtna.centrality.betweenness.SemivalueNodeBetweenness;
import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.centrality.utils.FeatureScalingNormalization;
import edu.pw.elka.gtna.graph.GraphFactory;
import edu.pw.elka.gtna.graph.creator.GMLReader;
import edu.pw.elka.gtna.graph.creator.TGFReader;
import edu.pw.elka.gtna.graph.creator.interfaces.GraphCreator;
import edu.pw.elka.gtna.graph.evaluator.ClusteringCoefficientEvaluator;
import edu.pw.elka.gtna.graph.evaluator.FragmentationRatioEvaluator;
import edu.pw.elka.gtna.graph.evaluator.IGMEvaluator;
import edu.pw.elka.gtna.graph.evaluator.LargestComponentEvaluator;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphEvaluator;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;
import edu.pw.elka.gtna.utils.MathFactors;
import edu.pw.elka.gtna.utils.RandomSubset;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public class FailurePerformerCardSVB extends AbstractTester {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		
		if (args.length < 6) {
			System.out.println("Missing arguments: GRAPH, a, bStep, resources, iterations, threads");
			return;
		}
		for (int i=0; i<args.length;i++ ){
			System.out.println(i+": "+args[i]);
		}
		
		String graph = args[0];
		
		GraphCreator<Edge<Node>> gc = null;	
		if ("tgf".equals(graph.split("\\.")[1])){
			gc = new TGFReader(graph);
		} else {
			gc = new GMLReader(graph);
		}
		
		final Graph<Node,Edge<Node>> gr  = 
				GraphFactory.<Node,Edge<Node>>newSimpleInstance(gc, GraphType.SIMPLE);
		
	    int a = Integer.parseInt(args[1]) ;
	    final int bStep = Integer.parseInt(args[2]) ;
	    
	    final double protectPer = Double.parseDouble(args[3]);
	    
	    final int iterations = Integer.parseInt(args[4]) ;
	    final int threads = Integer.parseInt(args[5]) ;
	    
	    double resultB = 0.0;
	    double resultSV = 0.0;

	    final double bonus = protectPer*gr.getNodesNumber();
									
			
		Centrality<Node> nB = new BetweennessCentrality<Node>(gr);
		nB.computeCentrality();
		final FeatureScalingNormalization<Node> NB = new FeatureScalingNormalization<Node>(nB);
		
		
    	Centrality<Node> PSV = new SemivalueNodeBetweenness<Node>(gr){
			@Override
			protected double distribution(int k){
				return 1.0/gr.getNodesNumber();
			}
		};

		PSV.computeCentrality();
		final FeatureScalingNormalization<Node> NPSV = new FeatureScalingNormalization<Node>(PSV);
		
		final double pow2 = Math.pow(2.0, gr.getNodesNumber()-1);
    	Centrality<Node> PB = new SemivalueNodeBetweenness<Node>(gr){
			@Override
			protected double distribution(int k){
				return MathFactors.biNom(gr.getNodesNumber()-1,k)/pow2;
			}
		};

		PB.computeCentrality();
		final FeatureScalingNormalization<Node> NPB = new FeatureScalingNormalization<Node>(PB);
		
		
		
		executorService = Executors.newFixedThreadPool(threads);
	    final Semaphore available = new Semaphore(threads);
			
			for (int b=2;b<=gr.getNodesNumber(); b+=bStep){
			
			//System.out.println(" b: "+b+"/"+gr.getNodesNumber());
			
			available.acquire();
			final int b2 = b;
			final int a2 = 1;
			executorService.execute(new Runnable() {
			    public void run() {

					
					double[] B	= new double[6];			
					double[] SV	= new double[6];
					double[] BA	= new double[6];			
					double[] PSV	= new double[6];	
					
			    	Centrality<Node> PSV2 = new SemivalueNodeBetweenness<Node>(gr){
						@Override
						protected double distribution(int k){
							if (k<=b2)
								return 1.0/(b2);
							else return 0.0;
						}
					};
					
					PSV2.computeCentrality();
					final FeatureScalingNormalization<Node> NPSV2 = new FeatureScalingNormalization<Node>(PSV2);
					
					RandomSubset<Node> rs = new RandomSubset<Node>(gr.getNodes());
					rs.setIterationsLimitation(iterations);
					rs.setSize(b2);
					int i = 0;
					for(Set<Node> failers: rs){ i++;
						//System.out.println(i+"/"+1000+" "+nf.format(Bdist/(i-1))+" vs "+nf.format(SVdist/(i-1))+"      "+nf.format(Bpaths/(i-1))+" vs "+nf.format(SVpaths/(i-1))); i++;

						final Graph<Node,Edge<Node>> grB  = 
								GraphFactory.<Node,Edge<Node>>newSimpleInstance(GraphType.SIMPLE);
						
						final Graph<Node,Edge<Node>> grSV  = 
								GraphFactory.<Node,Edge<Node>>newSimpleInstance(GraphType.SIMPLE);

						final Graph<Node,Edge<Node>> grBa  = 
								GraphFactory.<Node,Edge<Node>>newSimpleInstance(GraphType.SIMPLE);
						
						final Graph<Node,Edge<Node>> grPSV  = 
								GraphFactory.<Node,Edge<Node>>newSimpleInstance(GraphType.SIMPLE);
						
						
						for (Edge<Node> e : gr.getEdges()){
							grB.addEdge(e);
							grSV.addEdge(e);
							grBa.addEdge(e);
							grPSV.addEdge(e);
						}
						
						for(Node node: failers){
							
							double randD =  rand.nextDouble();
							if (NB.getNormCentrality(node)*bonus < randD ) {
								grB.removeEdges(node);
							}
							
							if (NPSV.getNormCentrality(node)*bonus < randD  ) {
								grSV.removeEdges(node);
							}

							if (NPB.getNormCentrality(node)*bonus < randD  ) {
								grBa.removeEdges(node);
							}
							
							if (NPSV2.getNormCentrality(node)*bonus < randD  ) {
								grPSV.removeEdges(node);
							}
							
//								if (rB.getPosition(node) > graphSize*0.2){
//									grB.removeEdges(node);
//								}
//								if ( rPSV.getPosition(node) > graphSize*0.2){
//									grSV.removeEdges(node);
//								}
			
						}
						
						GraphEvaluator clusteringCoefficientB = new ClusteringCoefficientEvaluator<Node,Edge<Node>>(grB);
						GraphEvaluator fragmentationRatioB = new FragmentationRatioEvaluator<Node,Edge<Node>>(grB);
						GraphEvaluator IGMEvaluatorB = new IGMEvaluator<Node,Edge<Node>>(grB);
						GraphEvaluator largestComponentB = new LargestComponentEvaluator<Node,Edge<Node>>(grB);
						
						B[0] += clusteringCoefficientB.evaluate();
						B[1] += fragmentationRatioB.evaluate();
						B[2] += IGMEvaluatorB.evaluate();
						B[3] += largestComponentB.evaluate();
																					
						
						GraphEvaluator clusteringCoefficientSV = new ClusteringCoefficientEvaluator<Node,Edge<Node>>(grSV);
						GraphEvaluator fragmentationRatioSV = new FragmentationRatioEvaluator<Node,Edge<Node>>(grSV);
						GraphEvaluator IGMEvaluatorSV = new IGMEvaluator<Node,Edge<Node>>(grSV);
						GraphEvaluator largestComponentSV = new LargestComponentEvaluator<Node,Edge<Node>>(grSV);
						
						
						SV[0] += clusteringCoefficientSV.evaluate();
						SV[1] += fragmentationRatioSV.evaluate();
						SV[2] += IGMEvaluatorSV.evaluate();
						SV[3] += largestComponentSV.evaluate();
						
						GraphEvaluator clusteringCoefficientBa = new ClusteringCoefficientEvaluator<Node,Edge<Node>>(grBa);
						GraphEvaluator fragmentationRatioBa = new FragmentationRatioEvaluator<Node,Edge<Node>>(grBa);
						GraphEvaluator IGMEvaluatorBa = new IGMEvaluator<Node,Edge<Node>>(grBa);
						GraphEvaluator largestComponentBa = new LargestComponentEvaluator<Node,Edge<Node>>(grBa);
						
						
						BA[0] += clusteringCoefficientBa.evaluate();
						BA[1] += fragmentationRatioBa.evaluate();
						BA[2] += IGMEvaluatorBa.evaluate();
						BA[3] += largestComponentBa.evaluate();
						
						GraphEvaluator clusteringCoefficientPSV = new ClusteringCoefficientEvaluator<Node,Edge<Node>>(grPSV);
						GraphEvaluator fragmentationRatioPSV = new FragmentationRatioEvaluator<Node,Edge<Node>>(grPSV);
						GraphEvaluator IGMEvaluatorPSV = new IGMEvaluator<Node,Edge<Node>>(grPSV);
						GraphEvaluator largestComponentPSV = new LargestComponentEvaluator<Node,Edge<Node>>(grPSV);
						
						
						PSV[0] += clusteringCoefficientPSV.evaluate();
						PSV[1] += fragmentationRatioPSV.evaluate();
						PSV[2] += IGMEvaluatorPSV.evaluate();
						PSV[3] += largestComponentPSV.evaluate();

					}
					
				//	System.out.println(b2+" "+nf.format(B[j]/iterations)+" vs "+nf.format(SV[j]/iterations)+"      "+nf.format(Bpaths/iterations)+" vs "+nf.format(SVpaths/iterations));
					System.out.print(b2+" ");
					for (int j=0; j<4; j++){
						System.out.print(sixDForm.format(B[j]/iterations)+" "+
								" "+sixDForm.format(SV[j]/iterations)+" "+
								" "+sixDForm.format(BA[j]/iterations)+" "+
								" "+sixDForm.format(PSV[j]/iterations)+" ");

					}				System.out.println();
					
					available.release();
			    }
			});
			
		}
			
		executorService.shutdown();

		try {
				executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
				System.out.println("ERROR: PROCESS INTERUPTED");
			}
		
	}
}
