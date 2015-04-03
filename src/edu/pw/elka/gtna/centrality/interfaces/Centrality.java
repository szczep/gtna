package edu.pw.elka.gtna.centrality.interfaces;

public interface Centrality<T> {
	
	/**
	 * This function computes centralities for all nodes
	 */
	public  void computeCentrality();
	
	public double getCentrality (T element);

    public void printCentralities();
    
}
