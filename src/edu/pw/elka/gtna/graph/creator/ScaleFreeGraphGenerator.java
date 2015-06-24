package edu.pw.elka.gtna.graph.creator;


import edu.pw.elka.gtna.graph.EdgeFactory;
import edu.pw.elka.gtna.graph.NodeFactory;
import edu.pw.elka.gtna.graph.NodeType;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.EdgeType;
import edu.pw.elka.gtna.graph.interfaces.Node;


/**
 * 
 * @author Talal Rahwan and Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *

*/

/**
 * generate a random scale-free graph, which is generated according to the power law. We use
 * the standard Barabasi-Albert preferential attachment generation model (for more details,
 * see [Statistical mechanics of complex networks, 2002].
 * 
 * Basically, the algorithm starts with an initial graph containing m0 nodes, where m0 >= 2
 * (in our code, m0 is called "BarabasiAlbertParameter"). Each of the initial m0 nodes must
 * have a degree at least 1.
 * 
 * Then, the algorithm proceeds with a main parameter called "m" (Voice et al. call it "k"),
 * where m >= m0 (in our code, we set m to be equal to m0). At every round, the algorithm adds
 * a new node, and connects it to m existing nodes. these m nodes are chosen randomly. This
 * randomisation is not done uniformly. Instead, it is done such that node n_i is chosen with
 * probability k_i/(sum k_j), where k_i is the degree of node n_i, and (sum k_j) is the sum of
 * degrees of all existing nodes.
 * 
 * If we add some number of nodes, say "t", to the initial "m0" nodes, the total number of
 * nodes will be "t + m0", and the total number of edges will be "m" times "t", i.e., "mt". 
 */

public class ScaleFreeGraphGenerator extends RandomGraphGenerator<Edge<Node>> {

	protected void addEdge(Edge<Node> edge){
		edges.add(edge);
	}
	
	
	/**
	 * A method that generates a random graph
	 * 
	 * @param numOfNodes		the number of nodes in the graph
	 * @param averageNodeDegree	the average degree of every node (only relevant if the graph type is "scale free")
	 */
	public ScaleFreeGraphGenerator( int numOfNodes, int averageNodeDegree)	{
		super(numOfNodes);
		int m0 = 2;
		int m  = averageNodeDegree;
		int [] degrees = new int[numOfNodes+1];
		
		//Check for invalid input
		if( m0 < 2 ) return;
		
		//initialise the sum of degrees of existing nodes so far
		int sumOfDegreesOfExistingNodesSoFar = 0;

		

		//For the initial m0-1 values, we have to make sure that their degree is at least 1.
		//To do this, we will connect every node i with node i+1.
		for( int i=1; i<=m0-1; i++ ){
			int j = i+1;
			addEdge(EdgeFactory.<Node>newInstance(
					NodeFactory.newInstance(String.valueOf(i), NodeType.SIMPLE),
					NodeFactory.newInstance(String.valueOf(j), NodeType.SIMPLE),
					EdgeType.SIMPLE));
			sumOfDegreesOfExistingNodesSoFar += 2;
			degrees[i]++;
			degrees[j]++;
		}	
		
		//for every new node
		for( int i=m0+1; i<=numOfNodes; i++)
		{
			for( int k=1; k<=m; k++) //we have to connect node i to "m" randomly selected nodes
			{
				//select a random number between 1 and "sumOfDegreesOfExistingNodesSoFar"
				int randomNumber = random.nextInt(sumOfDegreesOfExistingNodesSoFar) + 1;

				//Find the randomly selected node based on the randomly selected number
				int j = 1;
				int sum = degrees[j];
				while( sum < randomNumber ){
					j++;
					sum += degrees[j];
				}
				//Connect node i with node j
				addEdge(EdgeFactory.<Node>newInstance(
						NodeFactory.newInstance(String.valueOf(i), NodeType.SIMPLE),
						NodeFactory.newInstance(String.valueOf(j), NodeType.SIMPLE),
						EdgeType.SIMPLE));
				degrees[i]++;
				degrees[j]++;
				sumOfDegreesOfExistingNodesSoFar += 1;
			}
			//Update the sum of degrees of existing nodes (including the newly added node).
			sumOfDegreesOfExistingNodesSoFar += m;
		}
		
	}
	

}