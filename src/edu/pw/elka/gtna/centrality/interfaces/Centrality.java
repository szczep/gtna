package edu.pw.elka.gtna.centrality.interfaces;

import java.util.Set;

public interface Centrality<T> {
	
	/**
	 * This function computes centralities for all nodes
	 */
	public  void computeCentrality();
	
	public double getCentrality (T element);

    public void printCentralities();
    
    public Set<T> getElements();
    
}
