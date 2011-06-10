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

package net.vvakame.util.jsonpullparser.factory;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;

/**
 * {@link JsonKey} に対応する生成ソースの表現.
 * @author vvakame
 */
public class SaveOriginalElement {

	boolean saveOriginal = false;

	boolean treatLogDisabledAsError = false;

	String setter;


	/**
	 * @return the saveOriginal
	 * @category accessor
	 */
	public boolean isSaveOriginal() {
		return saveOriginal;
	}

	/**
	 * @param saveOriginal the saveOriginal to set
	 * @category accessor
	 */
	public void setSaveOriginal(boolean saveOriginal) {
		this.saveOriginal = saveOriginal;
	}

	/**
	 * @return the treatLogDisabledAsError
	 * @category accessor
	 */
	public boolean isTreatLogDisabledAsError() {
		return treatLogDisabledAsError;
	}

	/**
	 * @param treatLogDisabledAsError the treatLogDisabledAsError to set
	 * @category accessor
	 */
	public void setTreatLogDisabledAsError(boolean treatLogDisabledAsError) {
		this.treatLogDisabledAsError = treatLogDisabledAsError;
	}

	/**
	 * @return the setter
	 * @category accessor
	 */
	public String getSetter() {
		return setter;
	}

	/**
	 * @param setter the setter to set
	 * @category accessor
	 */
	public void setSetter(String setter) {
		this.setter = setter;
	}
}
