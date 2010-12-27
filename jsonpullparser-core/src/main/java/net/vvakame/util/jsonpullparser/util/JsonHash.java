package net.vvakame.util.jsonpullparser.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;

public class JsonHash extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = -3685725206266732067L;

	LinkedHashMap<String, State> stateMap = new LinkedHashMap<String, State>();

	@Override
	public void clear() {
		stateMap.clear();
		super.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedHashMap#get(java.lang.Object)
	 */
	@Override
	public Object get(Object arg0) {
		// TODO Auto-generated method stub
		return super.get(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
	 */
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<String, Object> arg0) {
		// TODO Auto-generated method stub
		return super.removeEldestEntry(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#clone()
	 */
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object arg0) {
		// TODO Auto-generated method stub
		return super.containsKey(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		// TODO Auto-generated method stub
		return super.entrySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return super.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#keySet()
	 */
	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return super.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object put(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return super.put(arg0, arg1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends String, ? extends Object> arg0) {
		// TODO Auto-generated method stub
		super.putAll(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#remove(java.lang.Object)
	 */
	@Override
	public Object remove(Object arg0) {
		// TODO Auto-generated method stub
		return super.remove(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#size()
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return super.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#values()
	 */
	@Override
	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return super.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
}
