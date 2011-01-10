package net.vvakame.util.jsonpullparser.util;

import java.io.IOException;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;

/**
 * デシリアライザをカスタマイズしたい場合実装するクラスです.<br> {@link JsonKey#converter()} にclassを指定してください.<br>
 * 実装クラスでは {@code public static TokenConverter getInstance()} を実装してください.<br>
 * getInstance() で取得できるインスタンスはSingletonでも都度生成でもいいですが、
 * {@link #parse(JsonPullParser)}が再利用可能であるように配慮してください.
 * 
 * @author vvakame
 */
public abstract class TokenConverter<T> {
	public abstract T parse(JsonPullParser parser) throws IOException,
			JsonFormatException;
}
