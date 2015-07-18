package edu.pw.elka.gtna.examples;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import edu.pw.elka.gtna.centrality.betweenness.SVBetweennessCentrality;
import edu.pw.elka.gtna.centrality.interfaces.Centrality;
import edu.pw.elka.gtna.graph.EdgeFactory;
import edu.pw.elka.gtna.graph.GraphFactory;
import edu.pw.elka.gtna.graph.NodeFactory;
import edu.pw.elka.gtna.graph.NodeType;
import edu.pw.elka.gtna.graph.creator.ScaleFreeGraphGenerator;
import edu.pw.elka.gtna.graph.evaluator.AverageShortestPathsEvaluator;
import edu.pw.elka.gtna.graph.evaluator.GlobalClusteringCoefficientEvaluator;
import edu.pw.elka.gtna.graph.evaluator.FragmentationRatioEvaluator;
import edu.pw.elka.gtna.graph.evaluator.IGMEvaluator;
import edu.pw.elka.gtna.graph.evaluator.LargestComponentEvaluator;
import edu.pw.elka.gtna.graph.evaluator.NumberOfLinksEvaluator;
import edu.pw.elka.gtna.graph.interfaces.DirectedEdge;
import edu.pw.elka.gtna.graph.interfaces.DirectedGraph;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.EdgeType;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.GraphEvaluator;
import edu.pw.elka.gtna.graph.interfaces.GraphType;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class GraphEvaluatorsExample {
	
	public static void main(String[] args) {
		Graph<Node,Edge<Node>> gr = 
				GraphFactory.<Node,Edge<Node>>newSimpleInstance(new ScaleFreeGraphGenerator(20, 3), GraphType.SIMPLE);
		

	    List<GraphEvaluator> graphEvaluators = new ArrayList<GraphEvaluator>();
		graphEvaluators.clear();
	    graphEvaluators.add(new AverageShortestPathsEvaluator<Node,Edge<Node>>(gr));
	    graphEvaluators.add(new GlobalClusteringCoefficientEvaluator<Node,Edge<Node>>(gr));
	    graphEvaluators.add(new FragmentationRatioEvaluator<Node,Edge<Node>>(gr));
	    graphEvaluators.add(new IGMEvaluator<Node,Edge<Node>>(gr));
	    graphEvaluators.add(new LargestComponentEvaluator<Node,Edge<Node>>(gr));			
	    graphEvaluators.add(new NumberOfLinksEvaluator<Node,Edge<Node>>(gr));
	    
	    for(GraphEvaluator ge : graphEvaluators){
	    	System.out.println(ge.getClass()+" "+ge.evaluate());
	    }
	    
	}
}
