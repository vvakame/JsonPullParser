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

package net.vvakame.util.jsonpullparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.vvakame.util.jsonpullparser.JsonPullParser;

/**
 * Jsonとしてマッピングしたいクラスを装飾するアノテーション.
 * 
 * @see JsonKey
 * @author vvakame
 */
@Retention(RetentionPolicy.CLASS)
@Target({
	ElementType.TYPE
})
public @interface JsonModel {

	/**
	 * 未知のKeyがJSONに含まれている場合の挙動.<br>
	 * 値が {@code true} なら {@link IllegalStateException}.<br>
	 * 値が {@code false} なら {@link JsonPullParser#discardValue()} で値を読み捨てます.
	 * @return 未知のKeyをエラーにするか
	 * @author vvakame
	 */
	public boolean treatUnknownKeyAsError() default false;

	/**
	 * 配下の要素の_記法をキャメル記法に変換した上でマッピングを行うかの制御.<br>
	 * {@code true} なら行う. {@code false} なら行わない.<br>
	 * JSONで"source_id"というKeyにsourceIDというフィールドをマッピングさせます.
	 * @return _記法→キャメル記法変換
	 * @see JsonKey#decamelize()
	 * @author vvakame
	 */
	public boolean decamelize() default false;
}
