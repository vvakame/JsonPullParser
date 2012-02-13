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

package net.vvakame.sample.twitter;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.util.JsonHash;

/**
 * Sample data model for tweet (as in Twitter.)
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = true, decamelize = true)
public class Tweet {

	@JsonKey
	JsonHash geo;

	@JsonKey
	boolean truncated;

	@JsonKey
	JsonHash coordinates;

	@JsonKey
	boolean favorited;

	@JsonKey
	String source;

	@JsonKey
	String idStr;

	@JsonKey
	String inReplyToScreenName;

	@JsonKey
	String inReplyToStatusIdStr;

	@JsonKey
	JsonHash contributors;

	@JsonKey
	long retweetCount;

	@JsonKey
	String inReplyToUserIdStr;

	@JsonKey
	String createdAt;

	@JsonKey
	Place place;

	@JsonKey
	boolean retweeted;

	@JsonKey
	long inReplyToStatusId;

	@JsonKey
	long id;

	@JsonKey
	long inReplyToUserId;

	@JsonKey
	String text;

	@JsonKey
	User user;


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
	public JsonHash getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates
	 *            the coordinates to set
	 */
	public void setCoordinates(JsonHash coordinates) {
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
	public JsonHash getContributors() {
		return contributors;
	}

	/**
	 * @param contributors
	 *            the contributors to set
	 */
	public void setContributors(JsonHash contributors) {
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
	public Place getPlace() {
		return place;
	}

	/**
	 * @param place
	 *            the place to set
	 */
	public void setPlace(Place place) {
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
