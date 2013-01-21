package net.vvakame.sample.issue28;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

/**
 * Class for JsonHash property issue. 
 * @author vvakame
 */
@JsonModel(builder = true)
public class Issue28 {

	@JsonKey
	JsonHash hash;

	@JsonKey
	JsonArray array;


	/**
	 * @return the hash
	 * @category accessor
	 */
	public JsonHash getHash() {
		return hash;
	}

	/**
	 * @param hash the hash to set
	 * @category accessor
	 */
	public void setHash(JsonHash hash) {
		this.hash = hash;
	}

	/**
	 * @return the array
	 * @category accessor
	 */
	public JsonArray getArray() {
		return array;
	}

	/**
	 * @param array the array to set
	 * @category accessor
	 */
	public void setArray(JsonArray array) {
		this.array = array;
	}
}
