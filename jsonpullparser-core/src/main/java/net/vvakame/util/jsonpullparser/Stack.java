package net.vvakame.util.jsonpullparser;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * {@link java.util.Stack} とほぼ同じ機能を持ちます.<br> {@link java.util.Stack}
 * を使わないのは、同期をとる必要がないからです.<br> {@link java.util.Deque}
 * を使わないのは、AndroidではLevel9以上でないと使えないからです.
 * 
 * @author vvakame
 * 
 */
final public class Stack<T> {
	final List<T> stack = new ArrayList<T>();

	public void push(T arg) {
		stack.add(arg);
	}

	public T pop() {
		final int max = stack.size() - 1;
		if (max < 0) {
			throw new NoSuchElementException();
		}
		return stack.remove(max);
	}

	public T peek() {
		final int top = stack.size() - 1;
		if (top < 0) {
			throw new NoSuchElementException();
		}
		return stack.get(top);
	}
}
