package edu.pw.elka.gtna.graph.creator;


import edu.pw.elka.gtna.graph.CommunityImpl;
import edu.pw.elka.gtna.graph.NodeFactory;
import edu.pw.elka.gtna.graph.NodeType;
import edu.pw.elka.gtna.graph.interfaces.Community;
import edu.pw.elka.gtna.graph.interfaces.Node;

/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 *
 *	Trivial Graph Format reader 
 *
 */
public class TCSReader extends CommunityStructureFileReader<Node> {



	/**
	 * @param fileName
	 */
	public TCSReader(String fileName) {
		super(fileName);
		readLine(); 
	}


	@Override
	public Community<Node> next() {
		String[] edge = newLine.split("\\s");
		if (hasNext()){
			readLine();
		}
		Community<Node> C = new CommunityImpl<Node>();
	
		return C;
	}

}
