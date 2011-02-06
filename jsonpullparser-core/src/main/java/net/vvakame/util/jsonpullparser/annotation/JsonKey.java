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

import net.vvakame.util.jsonpullparser.util.TokenConverter;

/**
 * Jsonとしてマッピングしたいクラスのフィールドを装飾するアノテーション.
 * 
 * @see JsonModel
 * @author vvakame
 */
@Retention(RetentionPolicy.SOURCE)
@Target({
	ElementType.FIELD
})
public @interface JsonKey {

	/**
	 * もっくこんばーた！(ΦωΦ)ｶｯ
	 * @author vvakame
	 */
	static class MockConverter extends TokenConverter<Void> {

		public static MockConverter getInstance() {
			throw new UnsupportedOperationException("if you use this method. override it.");
		}
	}


	/**
	 * JSONのKeyに当たる部分の名前.<br>
	 * 省略時はフィールド名と同じものとして処理される.
	 * @return JSONのKey部分のマッチングに利用する値
	 * @author vvakame
	 */
	public String value() default "";

	/**
	 * fieldの型を本ライブラリでサポートしておらず、さらに {@link JsonModel} でも修飾されていない場合に {@link TokenConverter} の拡張クラスを渡すことにより型変換を行うことが可能です.<br>
	 * 例えば、 {@link java.util.Date} と {@code long} の変換を定義することができます.
	 * @return 型変換に利用する {@link TokenConverter} のクラス
	 * @author vvakame
	 */
	public Class<? extends TokenConverter<?>> converter() default MockConverter.class;

	/**
	 * JSONからPOJOへのマッピング処理を行うかの制御.<br>
	 * {@code true} なら行う. {@code false} なら行わない.
	 * @return JSONデシリアライズ対象
	 * @author vvakame
	 */
	public boolean in() default true;

	/**
	 * POJOからJSONへの変換処理を行うかの制御.<br>
	 * {@code true} なら行う. {@code false} なら行わない.
	 * @return JSONシリアライズ対象
	 * @author vvakame
	 */
	public boolean out() default true;

	/**
	 * _記法をキャメル記法に変換した上でマッピングを行うかの制御.<br>
	 * {@code true} なら行う. {@code false} なら行わない.<br>
	 * JSONで"source_id"というKeyにsourceIDというフィールドをマッピングさせます.
	 * @return _記法→キャメル記法変換
	 * @author vvakame
	 */
	public boolean decamelize() default false;
}
