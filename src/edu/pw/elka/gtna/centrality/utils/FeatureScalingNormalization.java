package edu.pw.elka.gtna.centrality.utils;

import java.util.HashMap;
import java.util.Map;

import edu.pw.elka.gtna.centrality.interfaces.Centrality;

public class FeatureScalingNormalization<E> {
	
	Map<E, Double> normalizedCentrality = new HashMap<E, Double>();		
	Centrality<E> centrality;
	
	private double max;
	private double min;
	
	public FeatureScalingNormalization(Centrality<E> centrality) {
		this.centrality = centrality;

		
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		
		for(E el: centrality.getElements()){
			max = Math.max(centrality.getCentrality(el), max);
			min = Math.min(centrality.getCentrality(el), min);
		}		
		for(E el: centrality.getElements()){
			normalizedCentrality.put(el, (centrality.getCentrality(el)-min)/(max-min));
		}
		
		double sum = 0.0;
		for(E el: centrality.getElements()){
			sum+=getNormCentrality(el);
		}
		
		for(E el: centrality.getElements()){
			normalizedCentrality.put(el, (getNormCentrality(el)/sum));
		}
		
	}
	
	public void normalizeSum(double sumsTo){

	}
	
	public double getNormCentrality(E el){
		return normalizedCentrality.get(el);
	}

	public double getMax() {
		return max;
	}

	public double getMin() {
		return min;
	}
	
	
}
