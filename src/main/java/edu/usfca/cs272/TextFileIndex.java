package edu.usfca.cs272;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
	/**
	 * Constructor Method for TextFileIndex Class, creates a new HashMap
	 * for Path and List storage.
	 * */
	public TextFileIndex() {
		this.map = new HashMap<Path, List<String>>();
	}

	@Override
	public void add(Path location, String word) {
		List<String> newList = new ArrayList<>();
		newList.add(word);
		if(this.map.containsKey(location)) {
			for(String s : this.map.get(location)) {
				if(!newList.contains(s)) {
					newList.add(s);
				}
			}
		}
		this.map.put(location, newList);
	}
	
	/**
	 * Returns the number of locations stored in the index.
	 *
	 * @return 0 if the index is empty, otherwise the number of locations in the
	 *   index
	 */
	@Override
	public int size() {
		if(this.map.isEmpty()) {
			return 0;
		}
		return this.map.size();
	}

	/**
	 * Returns the number of words stored for the given path.
	 *
	 * @param location the location to lookup
	 * @return 0 if the location is not in the index or has no words, otherwise
	 *   the number of words stored for that element
	 */
	@Override
	public int size(Path location) {
		if(this.map.containsKey(location)) {
			return this.map.get(location).size();
		}
		return 0;
	}

	/**
	 * Determines whether the location is stored in the index.
	 *
	 * @param location the location to lookup
	 * @return {@true} if the location is stored in the index
	 */
	@Override
	public boolean contains(Path location) {
		return this.map.containsKey(location);
	}

	/**
	 * Determines whether the location is stored in the index and the word is
	 * stored for that location.
	 *
	 * @param location the location to lookup
	 * @param word the word in that location to lookup
	 * @return {@true} if the location and word is stored in the index
	 */
	@Override
	public boolean contains(Path location, String word) {
		return this.map.containsKey(location) && this.map.containsValue(List.of(word));
	}

	/**
	 * Returns an unmodifiable view of the locations stored in the index.
	 *
	 * @return an unmodifiable view of the locations stored in the index
	 * @see Collections#unmodifiableCollection(Collection)
	 */
	@Override
	public Collection<Path> get() {
		return Collections.unmodifiableCollection(this.map.keySet());
	}

	/**
	 * Returns an unmodifiable view of the words stored in the index for the
	 * provided location, or an empty collection if the location is not in the
	 * index.
	 *
	 * @param location the location to lookup
	 * @return an unmodifiable view of the words stored for the location
	 * @see Collections#unmodifiableCollection(Collection)
	 */
	@Override
	public Collection<String> get(Path location) {
		if(this.map.containsKey(location)) {
			return Collections.unmodifiableCollection(this.map.get(location));			
		}
		return Collections.emptyList();
	}
	
	/**
	 * Creates a String form of index
	 * @return HashMap in String form
	 */
	@Override
	public String toString() {
		return this.map.toString();
	}
}