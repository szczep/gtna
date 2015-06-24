package edu.pw.elka.gtna.utils;

import java.math.BigDecimal;

public class Factorial {
	
	protected static BigDecimal FACTORIALS[];
	
	public static BigDecimal factorial(int i) {
		
		if (FACTORIALS != null && i < FACTORIALS.length)
			return FACTORIALS[i];
		
		BigDecimal result = BigDecimal.ONE;

	    while (i!=0) {
	        result = result.multiply(BigDecimal.valueOf(i));
	        i--;
	    }

	    return result;
	}
	
	public static void precompute(int size) {
		FACTORIALS = new BigDecimal[size];
		FACTORIALS[0] = BigDecimal.ONE;
		for (int i=1; i<size;i++){
			FACTORIALS[i]= FACTORIALS[i-1].multiply(BigDecimal.valueOf(i)) ;
		}
	}
	
	
}
