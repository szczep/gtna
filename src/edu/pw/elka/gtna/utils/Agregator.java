package edu.pw.elka.gtna.utils;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;







/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */

public class Agregator {
		
	private TDistribution tDistribution;
	private NormalDistribution zDistribution = new NormalDistribution();
	private SummaryStatistics statistics;
	
	public Agregator() {
		statistics = new SummaryStatistics();
	}
	
	synchronized public void observe(double d) {
		statistics.addValue(d);
	}
	
	public double getMax() {
		return statistics.getMax();
	}
	
	public double getMin() {
		return statistics.getMin();
	}
	
	public double getMean() {
		return statistics.getMean();
	}
	
	public double getN() {
		return statistics.getN();
	}
	
	public double getVariance(){
		return statistics.getVariance();
	}
	
	public double getStandardDeviation(){
		return statistics.getStandardDeviation();
	}
	
	public void clear(){
		statistics.clear();
	}

	public double getLowerBound(int confidenceLevel){
		return getMean()-getConfidenceInterval(confidenceLevel);
	}
	
	public double getUpperBound(int confidenceLevel){
		return getMean()+getConfidenceInterval(confidenceLevel);
	}
	
	/**
	 * 
	 * @param confidenceLevel e.g. 95 means 95%
	 * @return
	 */
	public double getConfidenceInterval(int confidenceLevel) {
		
		if (getN()<30) {
			tDistribution = new TDistribution(getN());
			return  Math.abs(tDistribution.inverseCumulativeProbability((double)(100-confidenceLevel)/100.0)) *(getStandardDeviation()/Math.sqrt(getN()));
		} else {
			return  Math.abs(zDistribution.inverseCumulativeProbability((double)(100-confidenceLevel)/100.0)) *(getStandardDeviation()/Math.sqrt(getN()));
		}
	}
	
}
