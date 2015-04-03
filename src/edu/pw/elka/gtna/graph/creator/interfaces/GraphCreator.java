
package edu.pw.elka.gtna.graph.creator.interfaces;



import java.util.Iterator;

import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Node;


/**
 * @author Piotr Lech Szczepa�ski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public interface GraphCreator<E extends Edge<? extends Node>> extends Iterable<E>, Iterator<E>{

	
}
