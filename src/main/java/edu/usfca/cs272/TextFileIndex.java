package edu.usfca.cs272;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * A special type of {@link ForwardIndex} that indexes the UNIQUE words that
 * were found in a text file (represented by {@link Path} objects).
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2022
 */
public class TextFileIndex implements ForwardIndex<Path>{	
	private HashMap<Path, List<String>> map;
	/**
	 * Demonstrates this class. If this method does not compile, then the
	 * {@link TextFileIndex} class is not properly implementing the
	 * {@link ForwardIndex} interface.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		TextFileIndex index = new TextFileIndex();
		
		index.add(Path.of("hello.txt"), List.of("hello", "hola"));
		index.add(Path.of("letters.txt"), List.of("a", "b", "c", "c"));
		index.add(Path.of("letters.txt"), List.of("b", "e"));
		index.add(Path.of("planets.txt"), List.of("earth", "mars"));

		System.out.println(index);
	}
	
	public TextFileIndex() {
		this.map = new HashMap<Path, List<String>>();
	}

	@Override
	public void add(Path location, String word) {
		if(this.map.containsKey(location)) {
			this.map.remove(location, this.map.get(location));
			this.map.put(location, List.of(word));
		} else {
			this.map.put(location, List.of(word));			
		}
	}
	

	@Override
	public int size() {
		if(this.map.isEmpty()) {
			return 0;
		}
		return this.map.size();
	}

	@Override
	public int size(Path location) {
		if(this.map.containsKey(location)) {
			return this.map.get(location).size();
		}
		return 0;
	}

	@Override
	public boolean contains(Path location) {
		return this.map.containsKey(location);
	}

	@Override
	public boolean contains(Path location, String word) {
		return this.map.containsKey(location) && this.map.containsValue(List.of(word));
	}

	@Override
	public Collection<Path> get() {
		return Collections.unmodifiableCollection(this.map.keySet());
	}

	@Override
	public Collection<String> get(Path location) {
		if(this.map.containsKey(location)) {
			return Collections.unmodifiableCollection(this.map.get(location));			
		}
		return Collections.emptyList();
	}
	
	@Override
	public String toString() {
		return this.map.toString();
	}
}