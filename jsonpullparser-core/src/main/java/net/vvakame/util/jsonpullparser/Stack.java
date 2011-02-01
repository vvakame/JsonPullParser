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
 * {@link java.util.Stack} とほぼ同じ機能を持ちます.<br> {@link java.util.Stack}
 * を使わないのは、同期をとる必要がないからです.<br> {@link java.util.Deque}
 * を使わないのは、AndroidではLevel9以上でないと使えないからです.
 * 
 * @param <T>
 * @author vvakame
 * 
 */
final public class Stack<T> {

	final List<T> stack = new ArrayList<T>();


	/**
	 * 値を積みます.
	 * @param arg 積む値
	 * @author vvakame
	 */
	public void push(T arg) {
		stack.add(arg);
	}

	/**
	 * スタックの先頭にある要素を取り除き、返す.
	 * @return スタックの先頭の要素.
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
	 * スタックの先頭にある要素返す.取り除かない.
	 * @return スタックの先頭の要素.
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
