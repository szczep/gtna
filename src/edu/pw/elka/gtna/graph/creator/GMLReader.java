package edu.pw.elka.gtna.graph.creator;



import edu.pw.elka.gtna.graph.EdgeFactory;
import edu.pw.elka.gtna.graph.NodeFactory;
import edu.pw.elka.gtna.graph.NodeType;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.EdgeType;
import edu.pw.elka.gtna.graph.interfaces.Node;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public class GMLReader extends GraphFileReader<Edge<Node>> {

	/**
	 * @param fileName
	 */
	public GMLReader(String fileName) {
		super(fileName);
		readLine();	
		skipLines();
	}

	private void skipLines(){
		while(!newLine.equals("edge")){
			readLine();	
		}
	}

	@Override
	public Edge<Node> next() {
		readLine();		
		readLine();		
		String[] edge = newLine.split("\\s");
		int u = Integer.valueOf(edge[1])+1;
		readLine();		
		edge = newLine.split("\\s");
		int v = Integer.valueOf(edge[1])+1;
		readLine();	
		readLine();		

		Node n1 = NodeFactory.newInstance(String.valueOf(u),NodeType.SIMPLE);
		Node n2 = NodeFactory.newInstance(String.valueOf(v),NodeType.SIMPLE);

		return EdgeFactory.<Node>newInstance(n1,n2,EdgeType.SIMPLE);

	}
	
	@Override
	public boolean hasNext() {
		return newLine!=null && !newLine.equals("]");
	}

	
}
