package edu.pw.elka.gtna.test;


import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import edu.pw.elka.gtna.centrality.betweenness.BetweennessCentrality;
import edu.pw.elka.gtna.centrality.betweenness.SemivalueNodeBetweenness;
import edu.pw.elka.gtna.centrality.closeness.NodeClosenessCentrality;
import edu.pw.elka.gtna.centrality.degree.DegreeCentrality;
import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.graph.GraphFactory;
import edu.pw.elka.gtna.graph.creator.ScaleFreeGraphGenerator;
import edu.pw.elka.gtna.graph.evaluator.IGMEvaluator;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;
import edu.pw.elka.gtna.ranking.Ranking;
import edu.pw.elka.gtna.utils.Agregator;
import edu.pw.elka.gtna.utils.RandomSubset;


/**
 * @author Piotr Lech Szczepañski
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
	    
	    final Agregator[] distancesB = new Agregator[graphSize+1];
	    final Agregator[] distancesSV = new Agregator[graphSize+1];
	    
	    final Agregator[] distancesD = new Agregator[graphSize+1];
	    final Agregator[] distancesC = new Agregator[graphSize+1];

	    for (int i=0; i<=graphSize; i++){
	    	distancesB[i]= new Agregator();
	    	distancesSV[i]= new Agregator();
	    	distancesD[i]= new Agregator();
	    	distancesC[i]= new Agregator();
	    }
		  

		  for (int ii=1; ii<=graphs; ii++) {
			// System.out.println("graph: "+ii+"/"+graphs);
		  
			//final int graphSize = 150;
			//final int a = 1;
			//final int iterations = 5000;
		
			final Graph<Node,Edge<Node>> gr  = 
					GraphFactory.<Node,Edge<Node>>newSimpleInstance(new ScaleFreeGraphGenerator(graphSize, (int)Math.sqrt(graphSize)), GraphType.SIMPLE);
			
			IGMEvaluator<Node,Edge<Node>> aD = new IGMEvaluator<Node,Edge<Node>>(gr);
			final double mainD = aD.evaluate();	
			
			Centrality<Node> nB = new BetweennessCentrality<Node>(gr);
			nB.computeCentrality();
			final Ranking<Node> rB = new Ranking<Node>(nB);
			
			Centrality<Node> nD = new DegreeCentrality<Node>(gr);
			nD.computeCentrality();
			final Ranking<Node> rD = new Ranking<Node>(nD);
			
			Centrality<Node> nC = new NodeClosenessCentrality<Node>(gr){

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
						
						double Bdist  = 0.0;					
						double SVdist  = 0.0;
						double Dist  = 0.0;					
						double Cdist  = 0.0;
				
						
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

							
							final Graph<Node,Edge<Node>> grD  = 
									GraphFactory.<Node,Edge<Node>>newSimpleInstance(GraphType.SIMPLE);

							
							final Graph<Node,Edge<Node>> grC  = 
									GraphFactory.<Node,Edge<Node>>newSimpleInstance(GraphType.SIMPLE);
							
							for (Edge<Node> e : gr.getEdges()){
								grB.addEdge(e);
								grSV.addEdge(e);
								grD.addEdge(e);
								grC.addEdge(e);
							}
							
							for(Node node: failers){
								if ( ( (((double)rB.getPosition(node)*(double)rB.getPosition(node))/(gr.getNodesNumber()*gr.getNodesNumber())) > rand.nextDouble()) ){
									grB.removeEdges(node);
								}
								
								if ( ( (((double)rPSV.getPosition(node)*(double)rPSV.getPosition(node))/(gr.getNodesNumber()*gr.getNodesNumber())) > rand.nextDouble()) ){
									grSV.removeEdges(node);
								}
								
								if ( ( (((double)rD.getPosition(node)*(double)rD.getPosition(node))/(gr.getNodesNumber()*gr.getNodesNumber())) > rand.nextDouble()) ){
									grD.removeEdges(node);
								}
								
								if ( ( (((double)rC.getPosition(node)*(double)rC.getPosition(node))/(gr.getNodesNumber()*gr.getNodesNumber())) > rand.nextDouble()) ){
									grC.removeEdges(node);
								}
								
								
								
//								if (rB.getPosition(node) > graphSize*0.2){
//									grB.removeEdges(node);
//								}
//								if ( rPSV.getPosition(node) > graphSize*0.2){
//									grSV.removeEdges(node);
//								}
				
							}
							IGMEvaluator<Node,Edge<Node>> dB = new IGMEvaluator<Node,Edge<Node>>(grB);
							IGMEvaluator<Node,Edge<Node>> dSV = new IGMEvaluator<Node,Edge<Node>>(grSV);
							IGMEvaluator<Node,Edge<Node>> dD = new IGMEvaluator<Node,Edge<Node>>(grC);
							IGMEvaluator<Node,Edge<Node>> dC = new IGMEvaluator<Node,Edge<Node>>(grD);

//							Bdist += (mainD-dB.evaluate());
//							SVdist  += (mainD-dSV.evaluate());							
//							Dist  += (mainD-dD.evaluate());					
//							Cdist  += (mainD-dC.evaluate());
							
							Bdist += (dB.evaluate()/mainD)*100;
							SVdist  += (dSV.evaluate()/mainD)*100;							
							Dist  += (dD.evaluate()/mainD)*100;					
							Cdist  += (dC.evaluate()/mainD)*100;
							
							//rs.setSize(rand.nextInt((b2-a2))+a2);
							
							
						}
						//System.out.println("("+a+","+b2+") "+nf.format(Bdist/iterations)+" vs "+nf.format(SVdist/iterations)+"      "+nf.format(Bpaths/iterations)+" vs "+nf.format(SVpaths/iterations));
						distancesB[b2].observe(Bdist/iterations);
						distancesSV[b2].observe(SVdist/iterations);
						distancesD[b2].observe(Dist/iterations);
						distancesC[b2].observe(Cdist/iterations);
						
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
		  
		  
		for (int i=2; i<=graphSize; i+=bStep){
//			System.out.println(i+" distancesB: "+sixDForm.format(distancesB[i].getMean())+" "+sixDForm.format(distancesB[i].getLowerBound(75))+" "+sixDForm.format(distancesB[i].getUpperBound(75)));
//			System.out.println(i+" distancesSV: "+sixDForm.format(distancesSV[i].getMean())+" "+sixDForm.format(distancesSV[i].getLowerBound(75))+" "+sixDForm.format(distancesSV[i].getUpperBound(75)));
			System.out.println(i+" "+sixDForm.format(distancesB[i].getMean())+" "+sixDForm.format(distancesB[i].getLowerBound(75))+" "+sixDForm.format(distancesB[i].getUpperBound(75)) +
					" "+sixDForm.format(distancesSV[i].getMean())+" "+sixDForm.format(distancesSV[i].getLowerBound(75))+" "+sixDForm.format(distancesSV[i].getUpperBound(75)) + 
					" "+sixDForm.format(distancesD[i].getMean())+" "+sixDForm.format(distancesD[i].getLowerBound(75))+" "+sixDForm.format(distancesD[i].getUpperBound(75)) + 
					" "+sixDForm.format(distancesC[i].getMean())+" "+sixDForm.format(distancesC[i].getLowerBound(75))+" "+sixDForm.format(distancesC[i].getUpperBound(75)));
		}
		  
		}
}
