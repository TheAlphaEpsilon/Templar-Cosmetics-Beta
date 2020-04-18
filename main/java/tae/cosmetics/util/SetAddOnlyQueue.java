package tae.cosmetics.util;

import java.util.Iterator;

public class SetAddOnlyQueue<T> implements Iterable<T> {

	private static final int DEFAULT_MAX = 5;
	
	private T[] array;
	private int lastIndex = 0;
	
	public SetAddOnlyQueue() {
		this(DEFAULT_MAX);
	}
	
	@SuppressWarnings("unchecked")
	public SetAddOnlyQueue(int maxIndex) {
		array = (T[]) new Object[maxIndex];
	}

	@SuppressWarnings("unchecked")
	public void add(T element) {
		if(lastIndex < array.length) {
			array[lastIndex] = element;
			lastIndex++;
		} else {
			Object[] temp = new Object[array.length];
			for(int i = 1; i < array.length - 1; i++) {
				temp[i] = array[i - 1];
			}
			temp[0] = element;
			array = (T[]) temp;
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < array.length && array[index] != null;
			}

			@Override
			public T next() {
				return array[index++];
			}
			
		};
	}
	
}
