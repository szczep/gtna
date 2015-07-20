package edu.pw.elka.gtna.centrality.closeness;

import static edu.pw.elka.gtna.utils.MathFactors.biNom;

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
import edu.pw.elka.gtna.utils.MathFactors;

/**
 * 
 * @author Piotr Szczepañski
 *
 * @param <N>
 * @param <E>
 */
public class CVClosenessCentrality<N extends Node, E extends Edge<N>> extends NodeCentrality<N, E> {

	Map<N,Map<N,Integer>> dist;
	
	Map<N,Map<Community<N>,Integer>> commDistances;

	Map<N,Map<Integer,Integer>> ComGrD;
	Map<N,Map<Integer,Integer>> ComGrEqD;
	Map<N,Map<Community<N>,Map<Integer,Integer>>> NodGrD;	
	Map<N,Map<Community<N>,Map<Integer,Integer>>> NodGrEqD;
		
	Set<Integer> distances;
	
	
	public CVClosenessCentrality(CommunityStructure<N, E> communityStructure) {
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
		

		for (N v: graph.getNodes()){			
			centralities.put(v, 0.0);
			for (N u: graph.getNodes()){
				for(Community<N> Q : communityStructure.getNodeCommunities(v))
					for (int k=0; k< communityStructure.size(); k++){
						for (int l=0; l < Q.size(); l++){
							int d = dist(v,u);
	
							double MCplus = f(d)*( 	biNom(ComGrD.get(u).get(d),k)*biNom(NodGrD.get(u).get(Q).get(d),l) );
							double MCminus = 0.0;
							
							for (int d1: distances)	{
								if (d1 > d){
									MCminus += f(d1)*( biNom(ComGrEqD.get(u).get(d1),k)*biNom(NodGrEqD.get(u).get(Q).get(d1),l) -  
											biNom(ComGrD.get(u).get(d1),k)*biNom(NodGrD.get(u).get(Q).get(d1),l) );
								}
							}
							centralities.put(v, centralities.get(v) + beta(k)*alpha(l,Q.size())*( (MCplus-MCminus)/
									( (biNom(communityStructure.size()-1,k)*biNom(Q.size()-1,l)) ) ) );	
						}
					}		
			}
		}
		
	}

}
