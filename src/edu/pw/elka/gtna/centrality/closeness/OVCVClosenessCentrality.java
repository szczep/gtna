package edu.pw.elka.gtna.centrality.closeness;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.pw.elka.gtna.centrality.NodeCentrality;
import edu.pw.elka.gtna.graph.algorithms.Dijkstra;
import edu.pw.elka.gtna.graph.interfaces.Community;
import edu.pw.elka.gtna.graph.interfaces.CommunityStructure;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Node;

/**
 * 
 * @author Piotr Szczepañski
 *
 * @param <N>
 * @param <E>
 */
public class OVCVClosenessCentrality<N extends Node, E extends Edge<N>> extends NodeCentrality<N, E> {

	Map<N,Map<N,Integer>> dist;
	
	Map<N,Map<Community<N>,Integer>> commDistances;

	Map<N,Map<Integer,Integer>> ComGrD;
	Map<N,Map<Integer,Integer>> ComGrEqD;
	Map<N,Map<Community<N>,Map<Integer,Integer>>> NodGrD;	
	Map<N,Map<Community<N>,Map<Integer,Integer>>> NodGrEqD;
		
	Set<Integer> distances;
	
	
	public OVCVClosenessCentrality(CommunityStructure<N, E> communityStructure) {
		super(communityStructure);
		dist = new HashMap<N,Map<N,Integer>>();
		distances = new HashSet<Integer>();
		for (N n: graph.getNodes()){
			dist.put(n, new HashMap<N,Integer>());
		}	
		commDistances  = new HashMap<N,Map<Community<N>,Integer>>();
		
		ComGrD  = new HashMap<N,Map<Integer,Integer>>();
		ComGrEqD  = new HashMap<N,Map<Integer,Integer>>();
		NodGrD  = new HashMap<N,Map<Community<N>,Map<Integer,Integer>>> ();
		NodGrEqD  = new HashMap<N,Map<Community<N>,Map<Integer,Integer>>> ();
		
		for (N n : graph.getNodes()){
			commDistances.put(n, new HashMap<Community<N>,Integer>());
			
			ComGrD.put(n, new HashMap<Integer,Integer>());
			ComGrEqD.put(n, new HashMap<Integer,Integer>());
			NodGrD.put(n, new HashMap<Community<N>,Map<Integer,Integer>>());
			NodGrEqD.put(n, new HashMap<Community<N>,Map<Integer,Integer>>());			
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

	@Override
	public void computeCentrality() {
		
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
					distances.add(dist(u,v));
				}
			}
		}
		
		

		for (N u: graph.getNodes()){
			for (int d : distances){

				ComGrD.get(u).put(d, 0);
				ComGrEqD.get(u).put(d, 0);
				
				for (Community<N> Q : communityStructure.getCommunities()){
					if (!NodGrD.get(u).containsKey(Q))
						NodGrD.get(u).put(Q, new HashMap<Integer,Integer>());
					NodGrD.get(u).get(Q).put(d, 0);
					if (!NodGrEqD.get(u).containsKey(Q))
						NodGrEqD.get(u).put(Q, new HashMap<Integer,Integer>());
					NodGrEqD.get(u).get(Q).put(d, 0);
					if (dist(u,Q) > d){
						incr(ComGrD,u,d);
						incr(ComGrEqD,u,d);
					} else if (dist(u,Q) == d) { 	
						incr(ComGrEqD,u,d);
					}
					for (N s: Q){
						if (dist(u,s) > d){
							incr(NodGrD,u,Q,d);
							incr(NodGrEqD,u,Q,d);
						} else if (dist(u,s) == d) {
							incr(NodGrEqD,u,Q,d);
						}
					}
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
		

		for (N v: graph.getNodes()){			
			centralities.put(v, 0.0);
			for (N u: graph.getNodes()){
				for(Community<N> Q : communityStructure.getNodeCommunities(v)){
					
					int s = communityStructure.size();
					int q = Q.size();
					int d = dist(v,u);		
					
					int nodjs = q-NodGrEqD.get(u).get(Q).get(d);
					int nodjseq = q-NodGrD.get(u).get(Q).get(d);
					
					int comjs = s-ComGrEqD.get(u).get(d);
					int comjjseq = s-ComGrD.get(u).get(d);

					double MCplus = f(d)/( (nodjseq)*(comjjseq) ) ;
					double MCminus = 0.0;
							
					for (int d1: distances)	{
						if (d1 > d){

							
							nodjs = q-NodGrEqD.get(u).get(Q).get(d1);
							nodjseq = q-NodGrD.get(u).get(Q).get(d1);
							
							comjs = s-ComGrEqD.get(u).get(d1);
							comjjseq = s-ComGrD.get(u).get(d1);
							
							
							MCminus +=  f(d1)/( (nodjs)*(comjs) ) ;
							MCminus -=   f(d1)/( (nodjseq)*(comjjseq) ) ;
						}
					}
					centralities.put(v, centralities.get(v) + MCplus-MCminus);	
	
					
					
					
				}
			}
		}
		
	}

}
