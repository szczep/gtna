package edu.pw.elka.gtna.centrality.closeness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pw.elka.gtna.centrality.NodeCentrality;
import edu.pw.elka.gtna.graph.algorithms.Dijkstra;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class SVClosenessCentrality<N extends Node, E extends Edge<N>> extends NodeCentrality<N, E> {

	private Map<N,Map<N,Integer>> distances;
	
	public SVClosenessCentrality(Graph<N,E> graph) {
		super(graph);
		distances = new HashMap<N,Map<N,Integer>>();
		for (N n: graph.getNodes()){
			distances.put(n, new HashMap<N,Integer>());
			centralities.put(n,0.0);
		}	
	}

	
	protected  double f(int d){
		if (d == 0)
			return 0.0;
		return 1.0/d;
	}
	
	@Override
	public void computeCentrality() {
		
		Dijkstra<N,E> dikstra = new Dijkstra<N,E>(this.graph);
		for (N source: graph.getNodes()){
			dikstra.computePDistances(source);
			for (N n: graph.getNodes()){
				distances.get(source).put(n, dikstra.getDistance(n));
			}			
		}
		
		for (N v : graph.getNodes()){
			double sum = 0;
			double prevDist = -1;
			double prevSV = -1;
			double currSV = -1;
			
			
			List<N> D = new ArrayList<N>(distances.get(v).keySet());
			Collections.sort(D, new VerticesComparator(v));
			int index = D.size()-1;
			while (index>0){
				if (distances.get(v).get(D.get(index)) == prevDist){
					currSV = prevSV;
				} else {
					currSV = f(distances.get(v).get(D.get(index)))/(index+1.0) - sum;
				}
				centralities.put(D.get(index), centralities.get(D.get(index))+currSV);
				sum+=f(distances.get(v).get(D.get(index)))/(index*(index+1.0));
				prevDist=distances.get(v).get(D.get(index));
				prevSV=currSV;	
				index--;
			}
			centralities.put(v, centralities.get(v) + f(0) - sum);
			
		}
		
		
	}
	
	 public class VerticesComparator implements Comparator<N>{	 
		 N source;
		 public VerticesComparator(N source){
			 this.source = source;
		 }
		 
	     @Override
	     public int compare(N x, N y)
	     {
	         if (distances.get(source).get(x) < distances.get(source).get(y))
	         {
	             return -1;
	         }
	         if (distances.get(source).get(x) > distances.get(source).get(y))
	         {
	             return 1;
	         }
	         return 0;
	     }
	     
	 }

}
