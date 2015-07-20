package edu.pw.elka.gtna.graph.creator;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pw.elka.gtna.graph.CommunityImpl;
import edu.pw.elka.gtna.graph.EdgeFactory;
import edu.pw.elka.gtna.graph.NodeFactory;
import edu.pw.elka.gtna.graph.NodeType;
import edu.pw.elka.gtna.graph.interfaces.Community;
import edu.pw.elka.gtna.graph.interfaces.EdgeType;
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
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(newLine);
		m.find();
		Community<Node> C = new CommunityImpl<Node>();
		C.setLabel(m.group(1).replace("\"", ""));
		
		while (m.find())
			C.addNode(NodeFactory.newInstance(m.group(1).replace("\"", ""),NodeType.SIMPLE)); 
		
		if (hasNext()){
			readLine();
		}

		return C;
	}

}
