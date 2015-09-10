package edu.pw.elka.gtna.test;


import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import edu.pw.elka.gtna.centrality.betweenness.BetweennessCentrality;
import edu.pw.elka.gtna.centrality.betweenness.SemivalueNodeBetweenness;
import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.centrality.utils.FeatureScalingNormalization;
import edu.pw.elka.gtna.graph.GraphFactory;
import edu.pw.elka.gtna.graph.creator.ScaleFreeGraphGenerator;
import edu.pw.elka.gtna.graph.evaluator.ClusteringCoefficientEvaluator;
import edu.pw.elka.gtna.graph.evaluator.FragmentationRatioEvaluator;
import edu.pw.elka.gtna.graph.evaluator.IGMEvaluator;
import edu.pw.elka.gtna.graph.evaluator.LargestComponentEvaluator;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphEvaluator;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;
import edu.pw.elka.gtna.utils.Agregator;
import edu.pw.elka.gtna.utils.RandomSubset;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public class RandomFailurePerformerCard extends AbstractTester {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		
		if (args.length < 7) {
			System.out.println("Missing arguments: "+"GRAPH_SIZE, GRAPH_AVG_DEGREE, a, bStep, resources, iterations, threads, graphs");
			return;
		}
		for (int i=0; i<args.length;i++ ){
			System.out.println(i+": "+args[i]);
		}

	    final int graphSize = Integer.parseInt(args[0]) ;
	    final int graphDegree = Integer.parseInt(args[1]) ;
	    int a = Integer.parseInt(args[2]) ;
	    final int bStep = Integer.parseInt(args[3]) ;
	    
	    final double protectPer = Double.parseDouble(args[4]);
	    
	    final int iterations = Integer.parseInt(args[5]) ;
	    final int graphs = Integer.parseInt(args[6]) ;
	    final int threads = Integer.parseInt(args[7]) ;
	    

	    final double bonus = protectPer*graphSize;
	    
	    
	    
	    final Agregator[][] distancesB = new Agregator[4][graphSize+1];
	    final Agregator[][] distancesSV = new Agregator[4][graphSize+1];
	    
	    final Agregator[][] distancesD = new Agregator[4][graphSize+1];
	    final Agregator[][] distancesC = new Agregator[4][graphSize+1];


	    for (int i=0; i<=graphSize; i++){
		    for (int j=0; j<4; j++){
		    	distancesB[j][i]= new Agregator();
		    	distancesSV[j][i]= new Agregator();
		    	distancesD[j][i]= new Agregator();
		    	distancesC[j][i]= new Agregator();
		    }
		  
	    }
		  for (int ii=1; ii<=graphs; ii++) {
			// System.out.println("graph: "+ii+"/"+graphs);
		  
			//final int graphSize = 150;
			//final int a = 1;
			//final int iterations = 5000;
		
			final Graph<Node,Edge<Node>> gr  = 
					GraphFactory.<Node,Edge<Node>>newSimpleInstance(new ScaleFreeGraphGenerator(graphSize, graphDegree), GraphType.SIMPLE);
			
			GraphEvaluator clusteringCoefficient = new ClusteringCoefficientEvaluator<Node,Edge<Node>>(gr);
			GraphEvaluator fragmentationRatio = new FragmentationRatioEvaluator<Node,Edge<Node>>(gr);
			GraphEvaluator IGMEvaluator = new IGMEvaluator<Node,Edge<Node>>(gr);
			GraphEvaluator largestComponent = new LargestComponentEvaluator<Node,Edge<Node>>(gr);

			final double[] main = new double[4];
			main[0] = clusteringCoefficient.evaluate();
			main[1] = fragmentationRatio.evaluate();
			main[2] = IGMEvaluator.evaluate();
			main[3] = largestComponent.evaluate();
											
			
			Centrality<Node> nB = new BetweennessCentrality<Node>(gr);
			nB.computeCentrality();
			final FeatureScalingNormalization<Node> NB = new FeatureScalingNormalization<Node>(nB);
			
			executorService = Executors.newFixedThreadPool(threads);
		    final Semaphore available = new Semaphore(threads);
			
			for (int b=2;b<=graphSize; b+=bStep){
			
				System.out.println("graph: "+ii+"/"+graphs+" b: "+b+"/"+graphSize);
				
				available.acquire();
				final int b2 = b;
				final int a2 = 1;
				final int iif = ii;
				executorService.execute(new Runnable() {
				    public void run() {
		    	
				    	Centrality<Node> PSV = new SemivalueNodeBetweenness<Node>(gr){
							@Override
							protected double distribution(int k){
								if (k>=a2 && k<b2)
									return 1.0/(b2-a2);
								else return 0.0;
							}
						};
						
						PSV.computeCentrality();
						final FeatureScalingNormalization<Node> NPSV = new FeatureScalingNormalization<Node>(PSV);
						
						double[] B	= new double[6];			
						double[] SV	= new double[6];
				
						
						RandomSubset<Node> rs = new RandomSubset<Node>(gr.getNodes());
						rs.setIterationsLimitation(iterations);
						//rs.setSize(rand.nextInt((b2-a2))+a2);
						rs.setSize(b2);
						int i = 0;
						for(Set<Node> failers: rs){ i++;
							//System.out.println(i+"/"+1000+" "+nf.format(Bdist/(i-1))+" vs "+nf.format(SVdist/(i-1))+"      "+nf.format(Bpaths/(i-1))+" vs "+nf.format(SVpaths/(i-1))); i++;

							final Graph<Node,Edge<Node>> grB  = 
									GraphFactory.<Node,Edge<Node>>newSimpleInstance(GraphType.SIMPLE);
							
							final Graph<Node,Edge<Node>> grSV  = 
									GraphFactory.<Node,Edge<Node>>newSimpleInstance(GraphType.SIMPLE);

							
							
							for (Edge<Node> e : gr.getEdges()){
								grB.addEdge(e);
								grSV.addEdge(e);
							}
							
							for(Node node: failers){
								
								double randD =  rand.nextDouble();
								if (NB.getNormCentrality(node)*bonus < randD ) {
									grB.removeEdges(node);
								}
								
								if (NPSV.getNormCentrality(node)*bonus < randD  ) {
									grSV.removeEdges(node);
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

						}
						//System.out.println("("+a+","+b2+") "+nf.format(Bdist/iterations)+" vs "+nf.format(SVdist/iterations)+"      "+nf.format(Bpaths/iterations)+" vs "+nf.format(SVpaths/iterations));
						
						for (int j=0; j<4; j++){
							distancesB[j][b2].observe(B[j]/iterations);
							distancesSV[j][b2].observe(SV[j]/iterations);
						}
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

		  
		for (int i1=2; i1<=graphSize; i1+=bStep){
//			System.out.println(i+" distancesB: "+sixDForm.format(distancesB[i].getMean())+" "+sixDForm.format(distancesB[i].getLowerBound(75))+" "+sixDForm.format(distancesB[i].getUpperBound(75)));
//			System.out.println(i+" distancesSV: "+sixDForm.format(distancesSV[i].getMean())+" "+sixDForm.format(distancesSV[i].getLowerBound(75))+" "+sixDForm.format(distancesSV[i].getUpperBound(75)));
			System.out.print(i1+" ");
			for (int j=0; j<4; j++){
				System.out.print(sixDForm.format(distancesB[j][i1].getMean())+" "+sixDForm.format(distancesB[j][i1].getLowerBound(75))+" "+sixDForm.format(distancesB[j][i1].getUpperBound(75)) +
						" "+sixDForm.format(distancesSV[j][i1].getMean())+" "+sixDForm.format(distancesSV[j][i1].getLowerBound(75))+" "+sixDForm.format(distancesSV[j][i1].getUpperBound(75))+" ");

			}				System.out.println();

		}
		  
		}
}
