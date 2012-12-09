package net.vvakame.twitter;

import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(decamelize = true, treatUnknownKeyAsError = true)
public class SearchResult {
	@JsonKey
	double completedIn;

	@JsonKey
	long maxId;

	@JsonKey
	String maxIdStr;

	@JsonKey
	String nextPage;

	@JsonKey
	int page;

	@JsonKey
	String query;

	@JsonKey
	String refreshUrl;

	@JsonKey
	List<ResultTweet> results;

	@JsonKey
	int resultsPerPage;

	@JsonKey
	long sinceId;

	@JsonKey
	String sinceIdStr;

	/**
	 * @return the completedIn
	 */
	public double getCompletedIn() {
		return completedIn;
	}

	/**
	 * @param completedIn
	 *            the completedIn to set
	 */
	public void setCompletedIn(double completedIn) {
		this.completedIn = completedIn;
	}

	/**
	 * @return the maxId
	 */
	public long getMaxId() {
		return maxId;
	}

	/**
	 * @param maxId
	 *            the maxId to set
	 */
	public void setMaxId(long maxId) {
		this.maxId = maxId;
	}

	/**
	 * @return the maxIdStr
	 */
	public String getMaxIdStr() {
		return maxIdStr;
	}

	/**
	 * @param maxIdStr
	 *            the maxIdStr to set
	 */
	public void setMaxIdStr(String maxIdStr) {
		this.maxIdStr = maxIdStr;
	}

	/**
	 * @return the nextPage
	 */
	public String getNextPage() {
		return nextPage;
	}

	/**
	 * @param nextPage
	 *            the nextPage to set
	 */
	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the refreshUrl
	 */
	public String getRefreshUrl() {
		return refreshUrl;
	}

	/**
	 * @param refreshUrl
	 *            the refreshUrl to set
	 */
	public void setRefreshUrl(String refreshUrl) {
		this.refreshUrl = refreshUrl;
	}

	/**
	 * @return the results
	 */
	public List<ResultTweet> getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(List<ResultTweet> results) {
		this.results = results;
	}

	/**
	 * @return the resultsPerPage
	 */
	public int getResultsPerPage() {
		return resultsPerPage;
	}

	/**
	 * @param resultsPerPage
	 *            the resultsPerPage to set
	 */
	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}

	/**
	 * @return the sinceId
	 */
	public long getSinceId() {
		return sinceId;
	}

	/**
	 * @param sinceId
	 *            the sinceId to set
	 */
	public void setSinceId(long sinceId) {
		this.sinceId = sinceId;
	}

	/**
	 * @return the sinceIdStr
	 */
	public String getSinceIdStr() {
		return sinceIdStr;
	}

	/**
	 * @param sinceIdStr
	 *            the sinceIdStr to set
	 */
	public void setSinceIdStr(String sinceIdStr) {
		this.sinceIdStr = sinceIdStr;
	}
}
