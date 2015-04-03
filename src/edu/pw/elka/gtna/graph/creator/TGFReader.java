package edu.pw.elka.gtna.graph.creator;


import edu.pw.elka.gtna.graph.EdgeFactory;
import edu.pw.elka.gtna.graph.NodeFactory;
import edu.pw.elka.gtna.graph.NodeType;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.EdgeType;
import edu.pw.elka.gtna.graph.interfaces.Node;

/**
 * @author Piotr Lech Szczepański
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 *
 *	Trivial Graph Format reader 
 *
 */
public class TGFReader extends GraphFileReader<Edge<Node>> {


	/**
	 * @param fileName
	 */
	public TGFReader(String fileName) {
		super(fileName);
		readLine(); 
	}


	@Override
	public Edge<Node> next() {
		String[] edge = newLine.split("\\s");
		if (hasNext()){
			readLine();
		}
		Node n1 = NodeFactory.newInstance(edge[0],NodeType.SIMPLE);
		Node n2 = NodeFactory.newInstance(edge[1],NodeType.SIMPLE);

		return EdgeFactory.<Node>newInstance(n1,n2,EdgeType.SIMPLE);
	}


}
