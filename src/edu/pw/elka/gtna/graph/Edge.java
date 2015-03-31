package edu.pw.elka.gtna.graph;

public interface Edge<N extends Node> {

	N getN1();
	N getN2();
	
	void setN1(N n1);
	void setN2(N n2);
	
	N getN(N n);
}
