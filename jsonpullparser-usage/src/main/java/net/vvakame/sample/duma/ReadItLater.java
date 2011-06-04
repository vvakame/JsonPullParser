package net.vvakame.sample.duma;

import java.util.Map;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * ReadItLaterの何かのAPIのMapper
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = true, decamelize = true)
public class ReadItLater {

	@JsonKey
	String status;

	@JsonKey
	String since;

	@JsonKey(converter = ItemMapConverter.class)
	Map<String, Item> list;


	/**
	 * @return the status
	 * @category accessor
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 * @category accessor
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the since
	 * @category accessor
	 */
	public String getSince() {
		return since;
	}

	/**
	 * @param since the since to set
	 * @category accessor
	 */
	public void setSince(String since) {
		this.since = since;
	}

	/**
	 * @return the list
	 * @category accessor
	 */
	public Map<String, Item> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 * @category accessor
	 */
	public void setList(Map<String, Item> list) {
		this.list = list;
	}
}
