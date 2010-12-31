package net.vvakame.util.jsonpullparser.factory;

import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;

public class MessengerLog implements LogChute {

	@Override
	public void init(RuntimeServices rs) throws Exception {
	}

	@Override
	public boolean isLevelEnabled(int level) {
		return true;
	}

	@Override
	public void log(int level, String message) {
		switch (level) {
		case TRACE_ID:
		case DEBUG_ID:
		case INFO_ID:
			Log.d(message);
			break;
		case WARN_ID:
			Log.w(message);
			break;
		case ERROR_ID:
			Log.e(message);
			break;
		}
	}

	@Override
	public void log(int level, String message, Throwable t) {
		switch (level) {
		case TRACE_ID:
		case DEBUG_ID:
		case INFO_ID:
			Log.d(message);
			break;
		case WARN_ID:
			Log.w(message);
			break;
		case ERROR_ID:
			Log.e(message);
			break;
		}
	}
}
