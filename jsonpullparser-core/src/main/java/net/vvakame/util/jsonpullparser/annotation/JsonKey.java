package net.vvakame.util.jsonpullparser.annotation;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.TokenConverter;

@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.FIELD })
public @interface JsonKey {

	static class DiscardAllConverter extends TokenConverter<Void> {

		public static DiscardAllConverter getInstance() {
			return new DiscardAllConverter();
		}

		@Override
		public Void parse(JsonPullParser parser) throws IOException,
				JsonFormatException {
			parser.discardToken();
			return null;
		}
	}

	public String value() default "";

	public Class<? extends TokenConverter<?>> converter() default DiscardAllConverter.class;
}