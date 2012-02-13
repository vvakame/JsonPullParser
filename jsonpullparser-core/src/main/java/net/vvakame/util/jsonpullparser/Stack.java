/*
 * Copyright 2011 vvakame <vvakame@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.vvakame.util.jsonpullparser;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * {@link java.util.Stack} equivalent.<br> We won't use {@link java.util.Stack}
 * because we don't have to synchronize.<br> And we cannot use {@link java.util.Deque}
 * because it requires an API level of 9 or better on the Android platform.
 * 
 * @param <T>
 * @author vvakame
 * 
 */
final public class Stack<T> {

	final List<T> stack = new ArrayList<T>();


	/**
	 * Pushes the given value to the stack.
	 * @param arg The value to be pushed
	 * @author vvakame
	 */
	public void push(T arg) {
		stack.add(arg);
	}

	/**
	 * Pops the value from the top of stack and returns it.
	 * @return The value has been popped.
	 * @author vvakame
	 */
	public T pop() {
		final int max = stack.size() - 1;
		if (max < 0) {
			throw new NoSuchElementException();
		}
		return stack.remove(max);
	}

	/**
	 * Returns the value currently on the top of the stack.
	 * @return The value on the top.
	 * @author vvakame
	 */
	public T peek() {
		final int top = stack.size() - 1;
		if (top < 0) {
			throw new NoSuchElementException();
		}
		return stack.get(top);
	}
}
