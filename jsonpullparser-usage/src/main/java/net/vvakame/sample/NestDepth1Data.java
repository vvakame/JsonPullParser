package net.vvakame.sample;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * 深くネストするパターンのためのクラス.
 * @author vvakame
 */
@JsonModel(decamelize = true)
public class NestDepth1Data {

	@JsonKey
	NestDepth2Data data;


	/**
	 * @return the data
	 * @category accessor
	 */
	public NestDepth2Data getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 * @category accessor
	 */
	public void setData(NestDepth2Data data) {
		this.data = data;
	}
}
