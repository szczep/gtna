package edu.pw.elka.gtna.graph.creator;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
abstract class FileParser<T> implements Iterable<T>, Iterator<T> {
	protected BufferedReader reader;
	protected String newLine;
	
	public FileParser(String fileName){
		try {			
			File file = new File(fileName);
			if (!file.exists()){
					throw new IOException("File not found.");
			}
			reader = new BufferedReader(new FileReader(file));			
		} catch (IOException e) {
			e.printStackTrace();
			try {
				reader.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
	}
	
	protected void readLine() { 		
		try {
			newLine = reader.readLine(); 
			if (newLine!=null)
				newLine = newLine.trim();
			
		} catch (IOException e) {
			e.printStackTrace();
			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
	}
	
	@Override
	public Iterator<T> iterator() {
		return this;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();			
	}
	
	@Override
	public boolean hasNext() {
		return newLine!=null;
	}
	
}
