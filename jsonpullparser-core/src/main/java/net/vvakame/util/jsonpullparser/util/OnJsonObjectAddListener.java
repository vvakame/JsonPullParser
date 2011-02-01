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

package net.vvakame.util.jsonpullparser.util;

/**
 * 本ライブラリ生成クラスの引数として、 {@link OnJsonObjectAddListener} の実装クラスを渡すことにより、逐次処理されてくるインスタンスを受け取ることができる.
 * @author vvakame
 */
public interface OnJsonObjectAddListener {

	/**
	 * 新しく読み取ったインスタンスが逐次渡される.
	 * @param obj 新しく読み取ったインスタンス
	 * @author vvakame
	 */
	public void onAdd(Object obj);
}
