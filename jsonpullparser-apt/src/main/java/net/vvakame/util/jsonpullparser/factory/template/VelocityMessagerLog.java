package net.vvakame.util.jsonpullparser.factory.template;

import net.vvakame.util.jsonpullparser.factory.Log;

import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;

public class VelocityMessagerLog implements LogChute {

	@Override
	public void init(RuntimeServices rs) throws Exception {
	}

	@Override
	public boolean isLevelEnabled(int level) {
		return true;
	}

	@Override
	public void log(int level, String message) {
		Log.d(message);
	}

	@Override
	public void log(int level, String message, Throwable t) {
		Log.e(t);
	}
}