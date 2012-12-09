package net.vvakame.twitter;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.util.JsonHash;

@JsonModel(decamelize = true, treatUnknownKeyAsError = true)
public class ResultTweet {

	@JsonKey
	String createdAt;

	@JsonKey
	String fromUser;

	@JsonKey
	long fromUserId;

	@JsonKey
	String fromUserIdStr;

	@JsonKey
	String fromUserName;

	@JsonKey
	JsonHash geo;

	@JsonKey
	long id;

	@JsonKey
	String idStr;

	@JsonKey
	String isoLanguageCode;

	@JsonKey
	JsonHash metadata;

	@JsonKey
	String profileImageUrl;

	@JsonKey
	String profileImageUrlHttps;

	@JsonKey
	String source;

	@JsonKey
	String text;

	@JsonKey
	String toUser;

	@JsonKey
	long toUserId;

	@JsonKey
	String toUserIdStr;

	@JsonKey
	String toUserName;

	@JsonKey
	long inReplyToStatusId;

	@JsonKey
	String inReplyToStatusIdStr;

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
	 * @return the fromUser
	 */
	public String getFromUser() {
		return fromUser;
	}

	/**
	 * @param fromUser
	 *            the fromUser to set
	 */
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	/**
	 * @return the fromUserId
	 */
	public long getFromUserId() {
		return fromUserId;
	}

	/**
	 * @param fromUserId
	 *            the fromUserId to set
	 */
	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}

	/**
	 * @return the fromUserIdStr
	 */
	public String getFromUserIdStr() {
		return fromUserIdStr;
	}

	/**
	 * @param fromUserIdStr
	 *            the fromUserIdStr to set
	 */
	public void setFromUserIdStr(String fromUserIdStr) {
		this.fromUserIdStr = fromUserIdStr;
	}

	/**
	 * @return the fromUserName
	 */
	public String getFromUserName() {
		return fromUserName;
	}

	/**
	 * @param fromUserName
	 *            the fromUserName to set
	 */
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	/**
	 * @return the geo
	 */
	public JsonHash getGeo() {
		return geo;
	}

	/**
	 * @param geo
	 *            the geo to set
	 */
	public void setGeo(JsonHash geo) {
		this.geo = geo;
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
	 * @return the isoLanguageCode
	 */
	public String getIsoLanguageCode() {
		return isoLanguageCode;
	}

	/**
	 * @param isoLanguageCode
	 *            the isoLanguageCode to set
	 */
	public void setIsoLanguageCode(String isoLanguageCode) {
		this.isoLanguageCode = isoLanguageCode;
	}

	/**
	 * @return the metadata
	 */
	public JsonHash getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata
	 *            the metadata to set
	 */
	public void setMetadata(JsonHash metadata) {
		this.metadata = metadata;
	}

	/**
	 * @return the profileImageUrl
	 */
	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	/**
	 * @param profileImageUrl
	 *            the profileImageUrl to set
	 */
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	/**
	 * @return the profileImageUrlHttps
	 */
	public String getProfileImageUrlHttps() {
		return profileImageUrlHttps;
	}

	/**
	 * @param profileImageUrlHttps
	 *            the profileImageUrlHttps to set
	 */
	public void setProfileImageUrlHttps(String profileImageUrlHttps) {
		this.profileImageUrlHttps = profileImageUrlHttps;
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
	 * @return the toUser
	 */
	public String getToUser() {
		return toUser;
	}

	/**
	 * @param toUser
	 *            the toUser to set
	 */
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	/**
	 * @return the toUserId
	 */
	public long getToUserId() {
		return toUserId;
	}

	/**
	 * @param toUserId
	 *            the toUserId to set
	 */
	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}

	/**
	 * @return the toUserIdStr
	 */
	public String getToUserIdStr() {
		return toUserIdStr;
	}

	/**
	 * @param toUserIdStr
	 *            the toUserIdStr to set
	 */
	public void setToUserIdStr(String toUserIdStr) {
		this.toUserIdStr = toUserIdStr;
	}

	/**
	 * @return the toUserName
	 */
	public String getToUserName() {
		return toUserName;
	}

	/**
	 * @param toUserName
	 *            the toUserName to set
	 */
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
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
}
