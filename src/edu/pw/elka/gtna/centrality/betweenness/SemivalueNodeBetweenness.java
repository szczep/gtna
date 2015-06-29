/**
 *  Copyright (C) 2013  Piotr Szczepañski
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package edu.pw.elka.gtna.centrality.betweenness;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;

/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public abstract class SemivalueNodeBetweenness<N extends Node> extends ParametrisedBetweenness<N> {

	private int k;
	protected  Map<Node, Double> tymCentralities = new LinkedHashMap<Node, Double>();
	
	abstract protected double distribution(int k);
	

	/**
	 * @param graph
	 */
	public SemivalueNodeBetweenness(Graph<N, Edge<N>> graph) {
		super(graph);
	}

	/* (non-Javadoc)
	 * @see edu.pw.elka.gtsna.centrality.betweenness.ParametrisedBetweenness#f(int)
	 */
	@Override
	protected double f(int d) {
		if (graph.getNodesNumber() - d < k-1) {
			return 0.0;
		} else {
			
			double prev = 1.0;
			for (int j=0;j<k-1;j++){
				prev = prev* (graph.getNodesNumber()-d-j)/(double)(graph.getNodesNumber()-1-j);
			}
			return prev;
//			return Factorial.factorial(graph.getNodesNumber()-d).divide(Factorial.factorial(graph.getNodesNumber()-d-k+1), 100, RoundingMode.HALF_UP)
//			.multiply(Factorial.factorial(graph.getNodesNumber()-k).divide(Factorial.factorial(graph.getNodesNumber()-1), 100, RoundingMode.HALF_UP))
//			.doubleValue();

		}
	}

	/* (non-Javadoc)
	 * @see edu.pw.elka.gtsna.centrality.betweenness.ParametrisedBetweenness#g(int)
	 */
	@Override
	protected double g(int d) {
		return f(d) + (double)(k-graph.getNodesNumber())/(double)(graph.getNodesNumber()-1);
	}
	
	
	@Override
    public void computeCentrality(){
		
       for (N vertex: graph.getNodes()){
            tymCentralities.put(vertex, 0.0);
        }   
		
		for (int i=1; i<= graph.getNodesNumber(); i++) {
			this.k = i; 
	        for (N vertex: graph.getNodes()){
	            centralities.put(vertex, 0.0);
	        }   
	        for (N source: graph.getNodes()) {
	        	computeSourceInfluecnes(source);    
	        }
	        for (N vertex: graph.getNodes()){
	        	tymCentralities.put(vertex,tymCentralities.get(vertex)+distribution(k)*centralities.get(vertex)/2.0);
	        }
		}
        
		for (N vertex: graph.getNodes()){
            centralities.put(vertex, tymCentralities.get(vertex));
        }   
		
    }

}
