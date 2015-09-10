/**
 *  Copyright (C) 2013  Piotr Szczepañski
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package edu.pw.elka.gtna.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl
 *
 *
 *	This class enumerate through all subsets of a given set, O(n*2^n)
 */

public class RandomSubset<T> implements Iterable<Set<T>>, Iterator<Set<T>> {

	List<T> permutation = new ArrayList<T>();

	int iterationsLimitation = 0;
	int iterations = 0;
	
	int size = 0;
	
	Random rand = new Random();

	public RandomSubset(Collection<T> elements){
		permutation.addAll(elements);
		java.util.Collections.shuffle(permutation);
		this.size = elements.size();
	}

	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public boolean hasNext() {
		return (iterationsLimitation == 0 | iterations<iterationsLimitation);
	}

	@Override
	public Set<T> next() {		
		iterations++;
		java.util.Collections.shuffle(permutation);
		Set<T> subset = new LinkedHashSet<T>();
		int setSize = rand.nextInt(size)+1;
		for (int i=0; i<setSize;i++){
			subset.add(permutation.get(i));
		}
		return subset;
	}


	@Override
	public void remove() {
		throw new UnsupportedOperationException();	
	}


	@Override
	public Iterator<Set<T>> iterator() {
		return this;
	}


	public void setIterationsLimitation(int iterationsLimitation) {
		this.iterationsLimitation = iterationsLimitation;
	}
	
}
