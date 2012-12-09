package net.vvakame.sample.issue25;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Class for Reproduce issue. 
 * @author vvakame
 */
@JsonModel
public class Issue25 {

	@JsonKey
	String issue;

	boolean isFoo;

	String isBar;

	Boolean isFizz;


	/**
	 * @return the issue
	 * @category accessor
	 */
	public String getIssue() {
		return issue;
	}

	/**
	 * @param issue the issue to set
	 * @category accessor
	 */
	public void setIssue(String issue) {
		this.issue = issue;
	}

	/**
	 * @return the isFoo
	 * @category accessor
	 */
	public boolean isFoo() {
		return isFoo;
	}

	/**
	 * @param isFoo the isFoo to set
	 * @category accessor
	 */
	public void setFoo(boolean isFoo) {
		this.isFoo = isFoo;
	}

	/**
	 * @return the isBar
	 * @category accessor
	 */
	public String getIsBar() {
		return isBar;
	}

	/**
	 * @param isBar the isBar to set
	 * @category accessor
	 */
	public void setIsBar(String isBar) {
		this.isBar = isBar;
	}

	/**
	 * @return the isFizz
	 * @category accessor
	 */
	public Boolean getIsFizz() {
		return isFizz;
	}

	/**
	 * @param isFizz the isFizz to set
	 * @category accessor
	 */
	public void setIsFizz(Boolean isFizz) {
		this.isFizz = isFizz;
	}
}
