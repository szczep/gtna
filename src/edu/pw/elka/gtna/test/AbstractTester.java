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
package edu.pw.elka.gtna.test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
abstract public class AbstractTester  {
	
	
	static int NUMBER_OF_THREADS;
	static ExecutorService executorService ;
	static DecimalFormat sixDForm;
	static NumberFormat nf;
	static Random rand = new Random();
	
	static {
		AbstractTester.NUMBER_OF_THREADS = 4;
		executorService = Executors.newFixedThreadPool(AbstractTester.NUMBER_OF_THREADS);
		
		nf = NumberFormat.getNumberInstance(Locale.US);
		nf.setGroupingUsed(false);
		sixDForm = (DecimalFormat)nf;
		sixDForm.applyPattern("0.000000");
	}
	
	AbstractTester(){
	}
	
	AbstractTester(int threads){
		AbstractTester.NUMBER_OF_THREADS = threads;
		executorService = Executors.newFixedThreadPool(AbstractTester.NUMBER_OF_THREADS);
	}
	

    
    
    
}
