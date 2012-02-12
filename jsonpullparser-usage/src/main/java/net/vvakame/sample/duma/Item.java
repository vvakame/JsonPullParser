package net.vvakame.sample.duma;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Item or something in the ReadItLater API.
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = true, decamelize = true)
public class Item {

	@JsonKey
	String itemId;

	@JsonKey
	String url;

	@JsonKey
	String title;

	@JsonKey
	String timeUpdated;

	@JsonKey
	String timeAdded;

	@JsonKey
	String tags;

	@JsonKey
	String state;


	/**
	 * @return the itemId
	 * @category accessor
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 * @category accessor
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the url
	 * @category accessor
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 * @category accessor
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the title
	 * @category accessor
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 * @category accessor
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the timeUpdated
	 * @category accessor
	 */
	public String getTimeUpdated() {
		return timeUpdated;
	}

	/**
	 * @param timeUpdated the timeUpdated to set
	 * @category accessor
	 */
	public void setTimeUpdated(String timeUpdated) {
		this.timeUpdated = timeUpdated;
	}

	/**
	 * @return the timeAdded
	 * @category accessor
	 */
	public String getTimeAdded() {
		return timeAdded;
	}

	/**
	 * @param timeAdded the timeAdded to set
	 * @category accessor
	 */
	public void setTimeAdded(String timeAdded) {
		this.timeAdded = timeAdded;
	}

	/**
	 * @return the tags
	 * @category accessor
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 * @category accessor
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the state
	 * @category accessor
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 * @category accessor
	 */
	public void setState(String state) {
		this.state = state;
	}
}
