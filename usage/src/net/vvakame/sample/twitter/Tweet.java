package net.vvakame.sample.twitter;

import net.vvakame.util.jsonpullparser.annotation.JsonHash;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;

@JsonHash
public class Tweet {
	@JsonKey
	String geo;

	@JsonKey
	boolean truncated;

	@JsonKey
	String coordinates;

	@JsonKey
	boolean favorited;

	@JsonKey
	String source;

	@JsonKey("id_str")
	String idStr;

	@JsonKey("in_reply_to_screen_name")
	String inReplyToScreenName;

	@JsonKey("in_reply_to_status_id_str")
	String inReplyToStatusIdStr;

	@JsonKey
	String contributors;

	@JsonKey("retweet_count")
	long retweetCount;

	@JsonKey("in_reply_to_user_id_str")
	String inReplyToUserIdStr;

	@JsonKey("created_at")
	String createdAt;

	@JsonKey
	String place;

	@JsonKey
	boolean retweeted;

	@JsonKey("in_reply_to_status_id")
	long inReplyToStatusId;

	@JsonKey
	long id;

	@JsonKey("in_reply_to_user_id")
	long inReplyToUserId;

	@JsonKey
	String text;

	@JsonKey
	User user;

	/**
	 * @return the geo
	 */
	public String getGeo() {
		return geo;
	}

	/**
	 * @param geo
	 *            the geo to set
	 */
	public void setGeo(String geo) {
		this.geo = geo;
	}

	/**
	 * @return the truncated
	 */
	public boolean isTruncated() {
		return truncated;
	}

	/**
	 * @param truncated
	 *            the truncated to set
	 */
	public void setTruncated(boolean truncated) {
		this.truncated = truncated;
	}

	/**
	 * @return the coordinates
	 */
	public String getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates
	 *            the coordinates to set
	 */
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * @return the favorited
	 */
	public boolean isFavorited() {
		return favorited;
	}

	/**
	 * @param favorited
	 *            the favorited to set
	 */
	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the idStr
	 */
	public String getIdStr() {
		return idStr;
	}

	/**
	 * @param idStr
	 *            the idStr to set
	 */
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	/**
	 * @return the inReplyToScreenName
	 */
	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}

	/**
	 * @param inReplyToScreenName
	 *            the inReplyToScreenName to set
	 */
	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}

	/**
	 * @return the inReplyToStatusIdStr
	 */
	public String getInReplyToStatusIdStr() {
		return inReplyToStatusIdStr;
	}

	/**
	 * @param inReplyToStatusIdStr
	 *            the inReplyToStatusIdStr to set
	 */
	public void setInReplyToStatusIdStr(String inReplyToStatusIdStr) {
		this.inReplyToStatusIdStr = inReplyToStatusIdStr;
	}

	/**
	 * @return the contributors
	 */
	public String getContributors() {
		return contributors;
	}

	/**
	 * @param contributors
	 *            the contributors to set
	 */
	public void setContributors(String contributors) {
		this.contributors = contributors;
	}

	/**
	 * @return the retweetCount
	 */
	public long getRetweetCount() {
		return retweetCount;
	}

	/**
	 * @param retweetCount
	 *            the retweetCount to set
	 */
	public void setRetweetCount(long retweetCount) {
		this.retweetCount = retweetCount;
	}

	/**
	 * @return the inReplyToUserIdStr
	 */
	public String getInReplyToUserIdStr() {
		return inReplyToUserIdStr;
	}

	/**
	 * @param inReplyToUserIdStr
	 *            the inReplyToUserIdStr to set
	 */
	public void setInReplyToUserIdStr(String inReplyToUserIdStr) {
		this.inReplyToUserIdStr = inReplyToUserIdStr;
	}

	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place
	 *            the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @return the retweeted
	 */
	public boolean isRetweeted() {
		return retweeted;
	}

	/**
	 * @param retweeted
	 *            the retweeted to set
	 */
	public void setRetweeted(boolean retweeted) {
		this.retweeted = retweeted;
	}

	/**
	 * @return the inReplyToStatusId
	 */
	public long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	/**
	 * @param inReplyToStatusId
	 *            the inReplyToStatusId to set
	 */
	public void setInReplyToStatusId(long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the inReplyToUserId
	 */
	public long getInReplyToUserId() {
		return inReplyToUserId;
	}

	/**
	 * @param inReplyToUserId
	 *            the inReplyToUserId to set
	 */
	public void setInReplyToUserId(long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
