package edu.pw.elka.gtna.centrality.closeness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pw.elka.gtna.centrality.NodeCentrality;
import edu.pw.elka.gtna.graph.algorithms.Dijkstra;
import edu.pw.elka.gtna.graph.interfaces.Community;
import edu.pw.elka.gtna.graph.interfaces.CommunityStructure;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Node;
import edu.pw.elka.gtna.utils.MathFactors;

/**
 * 
 * @author Piotr Szczepañski
 *
 * @param <N>
 * @param <E>
 */
public class NEWCVClosenessCentrality<N extends Node, E extends Edge<N>> extends NodeCentrality<N, E> {

	Map<N,Map<N,Integer>> dist;
	
	Map<N,Map<Integer,Integer>> ComGrD;
	Map<N,Map<Integer,Integer>> ComGrEqD;
	Map<N,Map<Community<N>,Map<Integer,Integer>>> NodGrD;	
	Map<N,Map<Community<N>,Map<Integer,Integer>>> NodGrEqD;
		
	Map<N,Map<Community<N>,Integer>> commDistances;
	
	Map<N,List<N>> orderedNodes;
	Map<N,List<Community<N>>> orderedCommunities;
	
	public NEWCVClosenessCentrality(CommunityStructure<N, E> communityStructure) {
		super(communityStructure);
		dist = new HashMap<N,Map<N,Integer>>();
		for (N n: graph.getNodes()){
			dist.put(n, new HashMap<N,Integer>());
		}	
		commDistances  = new HashMap<N,Map<Community<N>,Integer>>();
		
		ComGrD  = new HashMap<N,Map<Integer,Integer>>();
		ComGrEqD  = new HashMap<N,Map<Integer,Integer>>();
		NodGrD  = new HashMap<N,Map<Community<N>,Map<Integer,Integer>>> ();
		NodGrEqD  = new HashMap<N,Map<Community<N>,Map<Integer,Integer>>> ();
		orderedCommunities =  new HashMap<N,List<Community<N>>> ();
		orderedNodes =  new HashMap<N,List<N>> ();
		
		
		
		for (N n : graph.getNodes()){
			centralities.put(n, 0.0);
			commDistances.put(n, new HashMap<Community<N>,Integer>());
			
			ComGrD.put(n, new HashMap<Integer,Integer>());
			ComGrEqD.put(n, new HashMap<Integer,Integer>());
			NodGrD.put(n, new HashMap<Community<N>,Map<Integer,Integer>>());
			NodGrEqD.put(n, new HashMap<Community<N>,Map<Integer,Integer>>());		
			
			orderedNodes.put(n, new ArrayList<N>(graph.getNodes()));
			orderedCommunities.put(n, new ArrayList<Community<N>>(communityStructure.getCommunities()));
			
		}
		

	}


	
	@Override
	public void computeCentrality() {
		MathFactors.precompute(graph.getNodesNumber()*communityStructure.size());
		
		Dijkstra<N,E> dikstra = new Dijkstra<N,E>(this.graph);
		for (N source: graph.getNodes()){
			dikstra.computePDistances(source);
			for (N n: graph.getNodes()){
				dist.get(source).put(n, dikstra.getDistance(n));
			}			
		}
		
		
		for (N v: graph.getNodes()){
			for (Community<N> Q : communityStructure.getCommunities()){
				commDistances.get(v).put(Q, Integer.MAX_VALUE);
				for (N u : Q){
					commDistances.get(v).put(Q, Math.min(dist(u,v),dist(v,Q)));
				}
			}
			
			Collections.sort(orderedNodes.get(v), new NodeComparator(v));
			Collections.sort(orderedCommunities.get(v), new CommunityComparator(v));
		}
		
		

		for (N u: graph.getNodes()){
			int prev = dist(u,orderedCommunities.get(u).get(0));
			
			ComGrD.get(u).put(prev, 0);
			ComGrEqD.get(u).put(prev, 0);
			
			
			for (Community<N> Q: orderedCommunities.get(u)){
				int d = dist(u,Q);
				if (d != prev){				
					ComGrD.get(u).put(d, ComGrEqD.get(u).get(prev));
					ComGrEqD.get(u).put(d, ComGrEqD.get(u).get(prev));		
					
					prev = d;
				}
				incr(ComGrEqD,u,d);
				int prevnod = dist(u,orderedNodes.get(u).get(0));
				
				if (!NodGrD.get(u).containsKey(Q))
					NodGrD.get(u).put(Q, new HashMap<Integer,Integer>());
				NodGrD.get(u).get(Q).put(prevnod, 0);
				if (!NodGrEqD.get(u).containsKey(Q))
					NodGrEqD.get(u).put(Q, new HashMap<Integer,Integer>());
				NodGrEqD.get(u).get(Q).put(prevnod, 0);
				
				for (N v: orderedNodes.get(u)){
					int dnod = dist(u,v);
					if (dnod != prevnod){							
						NodGrD.get(u).get(Q).put(dnod, NodGrEqD.get(u).get(Q).get(prevnod));
						NodGrEqD.get(u).get(Q).put(dnod, NodGrEqD.get(u).get(Q).get(prevnod));		
						
						prevnod = dnod;
					}
					if (Q.contains(v))
						incr(NodGrEqD,u,Q,dnod);
				}
				
			}
		}
		
		
//		System.out.println("ComGrD");
//		print1(ComGrD);
//		System.out.println("ComGrEqD");
//		print1(ComGrEqD);
//		System.out.println("NodGrD");
//		print2(NodGrD);
//		System.out.println("NodGrEqD");
//		print2(NodGrEqD);
		
		
		for (N u: graph.getNodes()){

			Map <Community<N>,Double> prev_val = new HashMap<Community<N>,Double>();
			Map <Community<N>,Integer> prev_d  = new HashMap<Community<N>,Integer>();
			Map <Community<N>,Double> MCminus  = new HashMap<Community<N>,Double>();
			for (Community<N> Q  : communityStructure.getCommunities()){
				prev_val.put(Q, 0.0);
				prev_d .put(Q, Integer.MAX_VALUE);
				MCminus.put(Q, 0.0);
			}
			
			
			for (N v: orderedNodes.get(u)){
				int d = dist(u,v);

				for (Community<N> Q: communityStructure.getCommunities()){
		
					int s = communityStructure.size();
					int q = Q.size();	
					
					int nodjs = q-NodGrEqD.get(u).get(Q).get(d);
					int nodjseq = q-NodGrD.get(u).get(Q).get(d);
					
					int comjs = s-ComGrEqD.get(u).get(d);
					int comjjseq = s-ComGrD.get(u).get(d);

					double MCplus = f(d)/( (nodjseq)*(comjjseq) ) ;

							
					if (prev_d.get(Q) != d){
						MCminus.put(Q, prev_val.get(Q));
						prev_d.put(Q,d);
						prev_val.put(Q, prev_val.get(Q) +  (f(d)/((nodjs)*(comjs)))  
								-  (f(d)/((nodjseq)*(comjjseq)))   );
					}
							
					if (Q.contains(v)) {
						centralities.put(v, centralities.get(v) + MCplus-MCminus.get(Q));	
					}
				
				}	
			}	
			
		}

		
	}

	private int dist(N n1, N n2){
		return dist.get(n1).get(n2);
	}
	
	private int dist(N n, Community<N> Q) {
		return commDistances.get(n).get(Q);
	}
	
	private void incr(Map<N,Map<Integer,Integer>> map, N node, Integer d){
		map.get(node).put(d, map.get(node).get(d)+1);
	}
	private void incr(Map<N,Map<Community<N>,Map<Integer,Integer>>>  map, N node, Community<N> Q, Integer d){
		map.get(node).get(Q).put(d, map.get(node).get(Q).get(d)+1);
	}
	
	private void print1(Map<N,Map<Integer,Integer>> map){
		for (Map.Entry<N,Map<Integer,Integer>> entry : map.entrySet()) {
		  	N n = entry.getKey();
		    for (Map.Entry<Integer,Integer> entry2 : entry.getValue().entrySet()) {
		    	Integer d = entry2.getKey();
		    	Integer v = entry2.getValue();
			    System.out.println(n+" "+d+" "+v);
		    }
		}
	}
	
	private void print2(Map<N,Map<Community<N>,Map<Integer,Integer>>>  map){
		for (Map.Entry<N,Map<Community<N>,Map<Integer,Integer>>> entry : map.entrySet()) {
		  	N n = entry.getKey();
		    for (Map.Entry<Community<N>,Map<Integer,Integer>> entry2 : entry.getValue().entrySet()) {
		    	Community<N> Q = entry2.getKey();
			    for (Map.Entry<Integer,Integer> entry3 : entry2.getValue().entrySet()) {
			    	Integer d = entry3.getKey();
			    	Integer v = entry3.getValue();
				    System.out.println(n+" "+Q.getLabel()+" "+d+" "+v);
			    }
		    }
		}
	}
	
	protected  double beta(int k){
		return 1.0/this.communityStructure.size();
	}
	
	protected  double alpha(int l, int coalitionSize){
		return 1.0/coalitionSize;
	}
	
	protected  double f(int d){
		if (d == 0)
			return 0.0;
		return 1.0/d;
	}
	

	
	
	 public class NodeComparator implements Comparator<N>{	 
		 N source;
		 public NodeComparator(N source){
			 this.source = source;
		 }
		 
	     @Override
	     public int compare(N x, N y)
	     {
	         if (dist(source,x) < dist(source,y))
	         {
	             return 1;
	         }
	         if (dist(source,x) > dist(source,y))
	         {
	             return -1;
	         }
	         return 0;
	     }
	     
	 }
	
	 public class CommunityComparator implements Comparator<Community<N>>{	 
		 N source;
		 public CommunityComparator(N source){
			 this.source = source;
		 }
		 
	     @Override
	     public int compare(Community<N> x, Community<N> y)
	     {
	         if (dist(source,x) < dist(source,y))
	         {
	             return 1;
	         }
	         if (dist(source,x) > dist(source,y))
	         {
	             return -1;
	         }
	         return 0;
	     }
	     
	 }
	
	
}
