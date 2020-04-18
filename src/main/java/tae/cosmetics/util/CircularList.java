package tae.cosmetics.util;

import java.util.ArrayList;
import java.util.Collection;

public class CircularList<T> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;

	public CircularList(Collection<T> e) {
		for(T item : e) {
			add(item);
		}
	}

	@Override
	public T get(int index) {
		return super.get(index % size());
	}
	
}
