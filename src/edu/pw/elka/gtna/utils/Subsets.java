package edu.pw.elka.gtna.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Piotr Lech Szczepa�ski
 * @author P.Szczepanski@ii.pw.edu.pl
 *
 *
 *	This class enumerate through all subsets of a given set, O(n*2^n)
 */

public class Subsets<T> implements Iterable<Set<T>>, Iterator<Set<T>> {

	private BigInteger enumerating;
	List<T> elements = new ArrayList<T>();
	private int size;
	
	
	
	public Subsets (Collection<T> ele) {
		enumerating = BigInteger.ZERO;
		elements.addAll(ele);
		size = ele.size();
	}
	
	public Subsets<T> skipEmptySet(){
		enumerating = BigInteger.ONE;
		return this;
	}
	
	public Set<T> next(){
		
		/** LinkedHashSet: O(1) adding to set and O(n) iterating through set **/
		Set<T> set= new LinkedHashSet<T>();
		for(int i=0; i<size; i++) {
			if (enumerating.testBit(i))
				set.add(elements.get(i));
		}
		enumerating = enumerating.add(BigInteger.ONE);
		return set;
	}

	@Override
	public Iterator<Set<T>> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return (enumerating.getLowestSetBit()<size);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();	
	}
	
}
