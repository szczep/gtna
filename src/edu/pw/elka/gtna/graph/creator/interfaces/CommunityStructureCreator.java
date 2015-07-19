package edu.pw.elka.gtna.graph.creator.interfaces;

import java.util.Iterator;

import edu.pw.elka.gtna.graph.interfaces.Community;
import edu.pw.elka.gtna.graph.interfaces.Node;


public interface CommunityStructureCreator<N extends Node> extends Iterable<Community<N>>, Iterator<Community<N>>{

}
