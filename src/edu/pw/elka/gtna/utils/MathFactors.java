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

import static edu.pw.elka.gtna.utils.MathFactors.fac;

/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public class MathFactors {
	// 15 powers
	public static final long[] POWER = {1L,
			1L,2L,6L,24L,120L,
			720L,5040L,40320L,362880L,3628800L,
			39916800L,479001600L,6227020800L,87178291200L,1307674368000L};
	
	
	public static double[] POWERS;
	
	public static void precompute(int size) {
		POWERS = new double[size];
		POWERS[0] = 1d;
		for (int i=1; i<size;i++){
			POWERS[i] = i*POWERS[i-1];
		}
	}
	
	public synchronized static double fac(int i){
		if (POWERS == null || POWERS.length < i)
			precompute(i+1);
		return POWERS[i];
	}
	
	static public  double biNom(int a, int b){
		if (b>a) return 0;
		return fac(a)/(fac(a-b)*fac(b));
	}
}
