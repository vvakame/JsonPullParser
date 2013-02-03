package net.vvakame.sample.issue30;

import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Class for issue30, primitive list and POJO list(need coder).
 * @author vvakame
 */
@JsonModel(builder = true)
public class PrimitiveList {

	@JsonKey
	List<Integer> intList;

	@JsonKey
	List<RecursiveStructure> recursiveList;


	/**
	 * @return the intList
	 * @category accessor
	 */
	public List<Integer> getIntList() {
		return intList;
	}

	/**
	 * @param intList the intList to set
	 * @category accessor
	 */
	public void setIntList(List<Integer> intList) {
		this.intList = intList;
	}

	/**
	 * @return the recursiveList
	 * @category accessor
	 */
	public List<RecursiveStructure> getRecursiveList() {
		return recursiveList;
	}

	/**
	 * @param recursiveList the recursiveList to set
	 * @category accessor
	 */
	public void setRecursiveList(List<RecursiveStructure> recursiveList) {
		this.recursiveList = recursiveList;
	}
}
