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

package net.vvakame.twitter;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(decamelize = true)
public class User {
	@JsonKey
	boolean followRequestSent;

	@JsonKey
	boolean contributorsEnabled;

	@JsonKey
	String profileSidebarFillColor;

	@JsonKey
	String description;

	@JsonKey
	boolean notifications;

	@JsonKey
	boolean profileBackgroundTile;

	@JsonKey
	String timeZone;

	@JsonKey
	String profileImageUrl;

	@JsonKey
	String location;

	@JsonKey
	long statusesCount;

	@JsonKey
	String profileLinkColor;

	@JsonKey
	long listedCount;

	@JsonKey
	String lang;

	@JsonKey
	long favouritesCount;

	@JsonKey
	String profileSidebarBorderColor;

	@JsonKey
	String url;

	@JsonKey
	String screenName;

	@JsonKey
	String idStr;

	@JsonKey
	boolean profileUseBackgroundImage;

	@JsonKey
	long followersCount;

	@JsonKey
	boolean following;

	@JsonKey
	boolean verified;

	@JsonKey
	long friendsCount;

	@JsonKey
	String profileBackgroundColor;

	@JsonKey("protected")
	boolean protecte;

	@JsonKey
	boolean isTranslator;

	@JsonKey
	String profileBackgroundImageUrl;

	@JsonKey
	String createdAt;

	@JsonKey
	String name;

	@JsonKey
	boolean showAllInlineMedia;

	@JsonKey
	boolean geoEnabled;

	@JsonKey
	long id;

	@JsonKey
	long utcOffset;

	@JsonKey
	String profileTextColor;

	/**
	 * @return the followRequestSent
	 */
	public boolean isFollowRequestSent() {
		return followRequestSent;
	}

	/**
	 * @param followRequestSent
	 *            the followRequestSent to set
	 */
	public void setFollowRequestSent(boolean followRequestSent) {
		this.followRequestSent = followRequestSent;
	}

	/**
	 * @return the contributorsEnabled
	 */
	public boolean isContributorsEnabled() {
		return contributorsEnabled;
	}

	/**
	 * @param contributorsEnabled
	 *            the contributorsEnabled to set
	 */
	public void setContributorsEnabled(boolean contributorsEnabled) {
		this.contributorsEnabled = contributorsEnabled;
	}

	/**
	 * @return the profileSidebarFillColor
	 */
	public String getProfileSidebarFillColor() {
		return profileSidebarFillColor;
	}

	/**
	 * @param profileSidebarFillColor
	 *            the profileSidebarFillColor to set
	 */
	public void setProfileSidebarFillColor(String profileSidebarFillColor) {
		this.profileSidebarFillColor = profileSidebarFillColor;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the notifications
	 */
	public boolean isNotifications() {
		return notifications;
	}

	/**
	 * @param notifications
	 *            the notifications to set
	 */
	public void setNotifications(boolean notifications) {
		this.notifications = notifications;
	}

	/**
	 * @return the profileBackgroundTile
	 */
	public boolean isProfileBackgroundTile() {
		return profileBackgroundTile;
	}

	/**
	 * @param profileBackgroundTile
	 *            the profileBackgroundTile to set
	 */
	public void setProfileBackgroundTile(boolean profileBackgroundTile) {
		this.profileBackgroundTile = profileBackgroundTile;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone
	 *            the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the statusesCount
	 */
	public long getStatusesCount() {
		return statusesCount;
	}

	/**
	 * @param statusesCount
	 *            the statusesCount to set
	 */
	public void setStatusesCount(long statusesCount) {
		this.statusesCount = statusesCount;
	}

	/**
	 * @return the profileLinkColor
	 */
	public String getProfileLinkColor() {
		return profileLinkColor;
	}

	/**
	 * @param profileLinkColor
	 *            the profileLinkColor to set
	 */
	public void setProfileLinkColor(String profileLinkColor) {
		this.profileLinkColor = profileLinkColor;
	}

	/**
	 * @return the listedCount
	 */
	public long getListedCount() {
		return listedCount;
	}

	/**
	 * @param listedCount
	 *            the listedCount to set
	 */
	public void setListedCount(long listedCount) {
		this.listedCount = listedCount;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang
	 *            the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the favouritesCount
	 */
	public long getFavouritesCount() {
		return favouritesCount;
	}

	/**
	 * @param favouritesCount
	 *            the favouritesCount to set
	 */
	public void setFavouritesCount(long favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	/**
	 * @return the profileSidebarBorderColor
	 */
	public String getProfileSidebarBorderColor() {
		return profileSidebarBorderColor;
	}

	/**
	 * @param profileSidebarBorderColor
	 *            the profileSidebarBorderColor to set
	 */
	public void setProfileSidebarBorderColor(String profileSidebarBorderColor) {
		this.profileSidebarBorderColor = profileSidebarBorderColor;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName
	 *            the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
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
	 * @return the profileUseBackgroundImage
	 */
	public boolean isProfileUseBackgroundImage() {
		return profileUseBackgroundImage;
	}

	/**
	 * @param profileUseBackgroundImage
	 *            the profileUseBackgroundImage to set
	 */
	public void setProfileUseBackgroundImage(boolean profileUseBackgroundImage) {
		this.profileUseBackgroundImage = profileUseBackgroundImage;
	}

	/**
	 * @return the followersCount
	 */
	public long getFollowersCount() {
		return followersCount;
	}

	/**
	 * @param followersCount
	 *            the followersCount to set
	 */
	public void setFollowersCount(long followersCount) {
		this.followersCount = followersCount;
	}

	/**
	 * @return the following
	 */
	public boolean isFollowing() {
		return following;
	}

	/**
	 * @param following
	 *            the following to set
	 */
	public void setFollowing(boolean following) {
		this.following = following;
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @param verified
	 *            the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	/**
	 * @return the friendsCount
	 */
	public long getFriendsCount() {
		return friendsCount;
	}

	/**
	 * @param friendsCount
	 *            the friendsCount to set
	 */
	public void setFriendsCount(long friendsCount) {
		this.friendsCount = friendsCount;
	}

	/**
	 * @return the profileBackgroundColor
	 */
	public String getProfileBackgroundColor() {
		return profileBackgroundColor;
	}

	/**
	 * @param profileBackgroundColor
	 *            the profileBackgroundColor to set
	 */
	public void setProfileBackgroundColor(String profileBackgroundColor) {
		this.profileBackgroundColor = profileBackgroundColor;
	}

	/**
	 * @return the protecte
	 */
	public boolean isProtecte() {
		return protecte;
	}

	/**
	 * @param protecte
	 *            the protecte to set
	 */
	public void setProtecte(boolean protecte) {
		this.protecte = protecte;
	}

	/**
	 * @return the isTranslator
	 */
	public boolean isTranslator() {
		return isTranslator;
	}

	/**
	 * @param isTranslator
	 *            the isTranslator to set
	 */
	public void setTranslator(boolean isTranslator) {
		this.isTranslator = isTranslator;
	}

	/**
	 * @return the profileBackgroundImageUrl
	 */
	public String getProfileBackgroundImageUrl() {
		return profileBackgroundImageUrl;
	}

	/**
	 * @param profileBackgroundImageUrl
	 *            the profileBackgroundImageUrl to set
	 */
	public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
		this.profileBackgroundImageUrl = profileBackgroundImageUrl;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the showAllInlineMedia
	 */
	public boolean isShowAllInlineMedia() {
		return showAllInlineMedia;
	}

	/**
	 * @param showAllInlineMedia
	 *            the showAllInlineMedia to set
	 */
	public void setShowAllInlineMedia(boolean showAllInlineMedia) {
		this.showAllInlineMedia = showAllInlineMedia;
	}

	/**
	 * @return the geoEnabled
	 */
	public boolean isGeoEnabled() {
		return geoEnabled;
	}

	/**
	 * @param geoEnabled
	 *            the geoEnabled to set
	 */
	public void setGeoEnabled(boolean geoEnabled) {
		this.geoEnabled = geoEnabled;
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
	 * @return the utcOffset
	 */
	public long getUtcOffset() {
		return utcOffset;
	}

	/**
	 * @param utcOffset
	 *            the utcOffset to set
	 */
	public void setUtcOffset(long utcOffset) {
		this.utcOffset = utcOffset;
	}

	/**
	 * @return the profileTextColor
	 */
	public String getProfileTextColor() {
		return profileTextColor;
	}

	/**
	 * @param profileTextColor
	 *            the profileTextColor to set
	 */
	public void setProfileTextColor(String profileTextColor) {
		this.profileTextColor = profileTextColor;
	}
}
