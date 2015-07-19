package edu.pw.elka.gtna.graph.creator;

import edu.pw.elka.gtna.graph.creator.interfaces.CommunityStructureCreator;
import edu.pw.elka.gtna.graph.interfaces.Community;
import edu.pw.elka.gtna.graph.interfaces.Node;


abstract public class CommunityStructureFileReader<N extends Node>  extends FileParser<Community<N>> 
implements CommunityStructureCreator<N> {
	
	protected Community<? extends Node> edge;

	public CommunityStructureFileReader(String fileName){
		super(fileName);
	}

}
