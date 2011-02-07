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

/**
 * ついったの人の表現
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = true)
public class User {

	@JsonKey(decamelize = true)
	boolean followRequestSent;

	@JsonKey(decamelize = true)
	boolean contributorsEnabled;

	@JsonKey(decamelize = true)
	String profileSidebarFillColor;

	@JsonKey
	String description;

	@JsonKey
	boolean notifications;

	@JsonKey(decamelize = true)
	boolean profileBackgroundTile;

	@JsonKey(decamelize = true)
	String timeZone;

	@JsonKey(decamelize = true)
	String profileImageUrl;

	@JsonKey
	String location;

	@JsonKey(decamelize = true)
	long statusesCount;

	@JsonKey(decamelize = true)
	String profileLinkColor;

	@JsonKey(decamelize = true)
	long listedCount;

	@JsonKey
	String lang;

	@JsonKey(decamelize = true)
	long favouritesCount;

	@JsonKey(decamelize = true)
	String profileSidebarBorderColor;

	@JsonKey
	String url;

	@JsonKey(decamelize = true)
	String screenName;

	@JsonKey(decamelize = true)
	String idStr;

	@JsonKey(decamelize = true)
	boolean profileUseBackgroundImage;

	@JsonKey(decamelize = true)
	long followersCount;

	@JsonKey
	boolean following;

	@JsonKey
	boolean verified;

	@JsonKey(decamelize = true)
	long friendsCount;

	@JsonKey(decamelize = true)
	String profileBackgroundColor;

	@JsonKey("protected")
	boolean protect;

	@JsonKey(decamelize = true)
	boolean isTranslator;

	@JsonKey(decamelize = true)
	String profileBackgroundImageUrl;

	@JsonKey(decamelize = true)
	String createdAt;

	@JsonKey
	String name;

	@JsonKey(decamelize = true)
	boolean showAllInlineMedia;

	@JsonKey(decamelize = true)
	boolean geoEnabled;

	@JsonKey
	long id;

	@JsonKey(decamelize = true)
	long utcOffset;

	@JsonKey(decamelize = true)
	String profileTextColor;


	/**
	 * @return the followRequestSent
	 * @category accessor
	 */
	public boolean isFollowRequestSent() {
		return followRequestSent;
	}

	/**
	 * @param followRequestSent the followRequestSent to set
	 * @category accessor
	 */
	public void setFollowRequestSent(boolean followRequestSent) {
		this.followRequestSent = followRequestSent;
	}

	/**
	 * @return the contributorsEnabled
	 * @category accessor
	 */
	public boolean isContributorsEnabled() {
		return contributorsEnabled;
	}

	/**
	 * @param contributorsEnabled the contributorsEnabled to set
	 * @category accessor
	 */
	public void setContributorsEnabled(boolean contributorsEnabled) {
		this.contributorsEnabled = contributorsEnabled;
	}

	/**
	 * @return the profileSidebarFillColor
	 * @category accessor
	 */
	public String getProfileSidebarFillColor() {
		return profileSidebarFillColor;
	}

	/**
	 * @param profileSidebarFillColor the profileSidebarFillColor to set
	 * @category accessor
	 */
	public void setProfileSidebarFillColor(String profileSidebarFillColor) {
		this.profileSidebarFillColor = profileSidebarFillColor;
	}

	/**
	 * @return the description
	 * @category accessor
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 * @category accessor
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the notifications
	 * @category accessor
	 */
	public boolean isNotifications() {
		return notifications;
	}

	/**
	 * @param notifications the notifications to set
	 * @category accessor
	 */
	public void setNotifications(boolean notifications) {
		this.notifications = notifications;
	}

	/**
	 * @return the profileBackgroundTile
	 * @category accessor
	 */
	public boolean isProfileBackgroundTile() {
		return profileBackgroundTile;
	}

	/**
	 * @param profileBackgroundTile the profileBackgroundTile to set
	 * @category accessor
	 */
	public void setProfileBackgroundTile(boolean profileBackgroundTile) {
		this.profileBackgroundTile = profileBackgroundTile;
	}

	/**
	 * @return the timeZone
	 * @category accessor
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone the timeZone to set
	 * @category accessor
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @return the profileImageUrl
	 * @category accessor
	 */
	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	/**
	 * @param profileImageUrl the profileImageUrl to set
	 * @category accessor
	 */
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	/**
	 * @return the location
	 * @category accessor
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 * @category accessor
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the statusesCount
	 * @category accessor
	 */
	public long getStatusesCount() {
		return statusesCount;
	}

	/**
	 * @param statusesCount the statusesCount to set
	 * @category accessor
	 */
	public void setStatusesCount(long statusesCount) {
		this.statusesCount = statusesCount;
	}

	/**
	 * @return the profileLinkColor
	 * @category accessor
	 */
	public String getProfileLinkColor() {
		return profileLinkColor;
	}

	/**
	 * @param profileLinkColor the profileLinkColor to set
	 * @category accessor
	 */
	public void setProfileLinkColor(String profileLinkColor) {
		this.profileLinkColor = profileLinkColor;
	}

	/**
	 * @return the listedCount
	 * @category accessor
	 */
	public long getListedCount() {
		return listedCount;
	}

	/**
	 * @param listedCount the listedCount to set
	 * @category accessor
	 */
	public void setListedCount(long listedCount) {
		this.listedCount = listedCount;
	}

	/**
	 * @return the lang
	 * @category accessor
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 * @category accessor
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the favouritesCount
	 * @category accessor
	 */
	public long getFavouritesCount() {
		return favouritesCount;
	}

	/**
	 * @param favouritesCount the favouritesCount to set
	 * @category accessor
	 */
	public void setFavouritesCount(long favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	/**
	 * @return the profileSidebarBorderColor
	 * @category accessor
	 */
	public String getProfileSidebarBorderColor() {
		return profileSidebarBorderColor;
	}

	/**
	 * @param profileSidebarBorderColor the profileSidebarBorderColor to set
	 * @category accessor
	 */
	public void setProfileSidebarBorderColor(String profileSidebarBorderColor) {
		this.profileSidebarBorderColor = profileSidebarBorderColor;
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
	 * @return the screenName
	 * @category accessor
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName the screenName to set
	 * @category accessor
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * @return the idStr
	 * @category accessor
	 */
	public String getIdStr() {
		return idStr;
	}

	/**
	 * @param idStr the idStr to set
	 * @category accessor
	 */
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	/**
	 * @return the profileUseBackgroundImage
	 * @category accessor
	 */
	public boolean isProfileUseBackgroundImage() {
		return profileUseBackgroundImage;
	}

	/**
	 * @param profileUseBackgroundImage the profileUseBackgroundImage to set
	 * @category accessor
	 */
	public void setProfileUseBackgroundImage(boolean profileUseBackgroundImage) {
		this.profileUseBackgroundImage = profileUseBackgroundImage;
	}

	/**
	 * @return the followersCount
	 * @category accessor
	 */
	public long getFollowersCount() {
		return followersCount;
	}

	/**
	 * @param followersCount the followersCount to set
	 * @category accessor
	 */
	public void setFollowersCount(long followersCount) {
		this.followersCount = followersCount;
	}

	/**
	 * @return the following
	 * @category accessor
	 */
	public boolean isFollowing() {
		return following;
	}

	/**
	 * @param following the following to set
	 * @category accessor
	 */
	public void setFollowing(boolean following) {
		this.following = following;
	}

	/**
	 * @return the verified
	 * @category accessor
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @param verified the verified to set
	 * @category accessor
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	/**
	 * @return the friendsCount
	 * @category accessor
	 */
	public long getFriendsCount() {
		return friendsCount;
	}

	/**
	 * @param friendsCount the friendsCount to set
	 * @category accessor
	 */
	public void setFriendsCount(long friendsCount) {
		this.friendsCount = friendsCount;
	}

	/**
	 * @return the profileBackgroundColor
	 * @category accessor
	 */
	public String getProfileBackgroundColor() {
		return profileBackgroundColor;
	}

	/**
	 * @param profileBackgroundColor the profileBackgroundColor to set
	 * @category accessor
	 */
	public void setProfileBackgroundColor(String profileBackgroundColor) {
		this.profileBackgroundColor = profileBackgroundColor;
	}

	/**
	 * @return the protect
	 * @category accessor
	 */
	public boolean isProtect() {
		return protect;
	}

	/**
	 * @param protect the protect to set
	 * @category accessor
	 */
	public void setProtect(boolean protect) {
		this.protect = protect;
	}

	/**
	 * @return the isTranslator
	 * @category accessor
	 */
	public boolean isTranslator() {
		return isTranslator;
	}

	/**
	 * @param isTranslator the isTranslator to set
	 * @category accessor
	 */
	public void setTranslator(boolean isTranslator) {
		this.isTranslator = isTranslator;
	}

	/**
	 * @return the profileBackgroundImageUrl
	 * @category accessor
	 */
	public String getProfileBackgroundImageUrl() {
		return profileBackgroundImageUrl;
	}

	/**
	 * @param profileBackgroundImageUrl the profileBackgroundImageUrl to set
	 * @category accessor
	 */
	public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
		this.profileBackgroundImageUrl = profileBackgroundImageUrl;
	}

	/**
	 * @return the createdAt
	 * @category accessor
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 * @category accessor
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the name
	 * @category accessor
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 * @category accessor
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the showAllInlineMedia
	 * @category accessor
	 */
	public boolean isShowAllInlineMedia() {
		return showAllInlineMedia;
	}

	/**
	 * @param showAllInlineMedia the showAllInlineMedia to set
	 * @category accessor
	 */
	public void setShowAllInlineMedia(boolean showAllInlineMedia) {
		this.showAllInlineMedia = showAllInlineMedia;
	}

	/**
	 * @return the geoEnabled
	 * @category accessor
	 */
	public boolean isGeoEnabled() {
		return geoEnabled;
	}

	/**
	 * @param geoEnabled the geoEnabled to set
	 * @category accessor
	 */
	public void setGeoEnabled(boolean geoEnabled) {
		this.geoEnabled = geoEnabled;
	}

	/**
	 * @return the id
	 * @category accessor
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 * @category accessor
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the utcOffset
	 * @category accessor
	 */
	public long getUtcOffset() {
		return utcOffset;
	}

	/**
	 * @param utcOffset the utcOffset to set
	 * @category accessor
	 */
	public void setUtcOffset(long utcOffset) {
		this.utcOffset = utcOffset;
	}

	/**
	 * @return the profileTextColor
	 * @category accessor
	 */
	public String getProfileTextColor() {
		return profileTextColor;
	}

	/**
	 * @param profileTextColor the profileTextColor to set
	 * @category accessor
	 */
	public void setProfileTextColor(String profileTextColor) {
		this.profileTextColor = profileTextColor;
	}
}
