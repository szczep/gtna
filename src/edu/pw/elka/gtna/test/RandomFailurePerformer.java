package edu.pw.elka.gtna.test;


import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import edu.pw.elka.gtna.centrality.betweenness.BetweennessCentrality;
import edu.pw.elka.gtna.centrality.betweenness.SemivalueNodeBetweenness;
import edu.pw.elka.gtna.centrality.closeness.ClosenessCentrality;
import edu.pw.elka.gtna.centrality.degree.DegreeCentrality;
import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.centrality.utils.Ranking;
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
 * @author Piotr Lech Szczepański
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public class RandomFailurePerformer extends AbstractTester {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		
		if (args.length < 7) {
			System.out.println("Missing arguments: "+"GRAPH_SIZE, a, bStep, bLimit, iterations, threads, graphs");
			return;
		}

	    final int graphSize = Integer.parseInt(args[0]) ;
	    int a = Integer.parseInt(args[1]) ;
	    final int bStep = Integer.parseInt(args[2]) ;
	    final int bLimit = Integer.parseInt(args[3]) ;
	    final int iterations = Integer.parseInt(args[4]) ;
	    final int threads = Integer.parseInt(args[5]) ;
	    final int graphs = Integer.parseInt(args[6]) ;
	    
	    final Agregator[][] distancesB = new Agregator[6][graphSize+1];
	    final Agregator[][] distancesSV = new Agregator[6][graphSize+1];
	    
	    final Agregator[][] distancesD = new Agregator[6][graphSize+1];
	    final Agregator[][] distancesC = new Agregator[6][graphSize+1];
	    


	    for (int i=0; i<=graphSize; i++){
		    for (int j=0; j<6; j++){
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
					GraphFactory.<Node,Edge<Node>>newSimpleInstance(new ScaleFreeGraphGenerator(graphSize, (int)Math.sqrt(graphSize)), GraphType.SIMPLE);
			

			
			Centrality<Node> nB = new BetweennessCentrality<Node>(gr);
			nB.computeCentrality();
			final Ranking<Node> rB = new Ranking<Node>(nB);
			
			Centrality<Node> nD = new DegreeCentrality<Node>(gr);
			nD.computeCentrality();
			final Ranking<Node> rD = new Ranking<Node>(nD);
			
			Centrality<Node> nC = new ClosenessCentrality<Node,Edge<Node>>(gr){
				@Override
				protected double fDistance(double d) {
					if (d==0) return 0;
					return 1/d;
				}				
			};
			nC.computeCentrality();
			final Ranking<Node> rC = new Ranking<Node>(nC);

			
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
						Ranking<Node> rPSV = new Ranking<Node>(PSV); 
						
						//Ranking.displayRankings(r1, r2);
						
						double[] B	= new double[6];			
						double[] SV	= new double[6];
						double[] D	= new double[6];
						double[] C	= new double[6];
				
						
						RandomSubset<Node> rs = new RandomSubset<Node>(gr.getNodes());
						rs.setIterationsLimitation(iterations);
						rs.setSize(rand.nextInt((b2-a2))+a2);
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
								if ( (((double)rB.getPosition(node))/(gr.getNodesNumber())) > rand.nextDouble() ) {
									grB.removeEdges(node);
								}
								
								if ( ( (((double)rPSV.getPosition(node))/(gr.getNodesNumber())) > rand.nextDouble() ) ){
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
						
						for (int j=0; j<6; j++){
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
