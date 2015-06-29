package edu.pw.elka.gtna.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pw.elka.gtna.centrality.interfaces.Centrality;


/**
 * 
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 *
 * @param <E>
 */

public class Ranking<E> {

	List<E> rank;
	Map<E, Integer> possitions = new HashMap<E, Integer>();;		
	Centrality<E> centrality;
	
	
	public Ranking(Centrality<E> centrality) {
		this.centrality = centrality;
		rank = new ArrayList<E>();

		for(E key: centrality.getElements()){
			rank.add(key);
		}		

		Collections.sort(rank, new Compare());
		
		int position = 1;
		Double previous = -1.0;
		int i=0;
		for(E elem: rank){
			if (roundDouble(centrality.getCentrality(elem)) != roundDouble(previous)) {
				previous = roundDouble(centrality.getCentrality(elem));
				position+=i;
				i=0;
			}
			i++;
			possitions.put(elem, position);
		}	
	}
	
	public static <E> void  displayRankings(Ranking<E> ... ranks){
		displayRankings(ranks[0].size(), ranks);
	}
	
	public static <E> void  displayRankings(int limit, Ranking<E> ... ranks){
		for (int pos=0; pos<limit; pos++){
			System.out.println("ELEMENT****"+pos+"****");
			for (int i = 0; i < ranks.length; i++) {
				E ele = ranks[i].getElement(pos);
				System.out.println(ranks[i].getPosition(ele)+". "+ele+" "+ranks[i].getValue(ele)+"   ");
			}
		}
	}
	
//	public static <E> double  relativeError(Centrality<E> approxC, Centrality<E> exactC){
//		double error = 0.0;
//		int etries = 0;
//		for (Map.Entry<E, Double> entry : approxValues.entrySet()) {
//			if (exactValues.get(entry.getKey())!=0) {
//				error+= Math.abs(1.0 - Math.abs(entry.getValue()/exactValues.get(entry.getKey())));
//				etries++;
//			}
//		}
//
//		return error/(double)etries;
//	}
	
//	public double relativeError(Ranking<E> rank){
//		return Ranking.relativeError(Centrality<E> approxC, Centrality<E> exactC);
//	}
//	

	
	protected class Compare implements Comparator<E>{

		public int compare(E ob1, E ob2) {		   
		   Double v1 = centrality.getCentrality(ob1);
		   Double v2 = centrality.getCentrality(ob2);
		   		   		   			   
		   return Double.compare(v2, v1);
		   
		  }
	}
	
	public E getElement(int index){
		return rank.get(index);
	}
	
	private double roundDouble(double d) {
	    return d;
	}
	
	public int size(){
		return rank.size();
	}
	
	public int getPosition(E ele){
		return possitions.get(ele);
	}
	
	public double getValue(E ele){
		return centrality.getCentrality(ele);
	}
	
	

	
	public void printRanking(){
		System.out.println("**********");
		for (E ele: rank){
			System.out.println(possitions.get(ele)+". "+ele+" "+centrality.getCentrality(ele));
		}
		System.out.println("**********");
	}
	
	
}
