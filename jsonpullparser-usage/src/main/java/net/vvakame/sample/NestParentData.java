package net.vvakame.sample;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * 深くネストするパターンのためのクラス.
 * @author vvakame
 */
@JsonModel(decamelize = true)
public class NestParentData {

	@JsonKey
	NestDepth1Data data;


	/**
	 * @return the data
	 * @category accessor
	 */
	public NestDepth1Data getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 * @category accessor
	 */
	public void setData(NestDepth1Data data) {
		this.data = data;
	}
}
