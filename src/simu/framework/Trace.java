package simu.framework;

public class Trace {

	public enum Level {
		INFO, WAR, ERR
	}

	private static Level traceLevel;

	public static void setTraceLevel(Level lvl) {
		traceLevel = lvl;
	}

	public static void out(Level lvl, String txt) {
		if (lvl.ordinal() >= traceLevel.ordinal()) {
			System.out.println(txt);
		}
	}

	public static void out(Level lvl, Object object) {
		if (lvl.ordinal() >= traceLevel.ordinal()) {
			System.out.println(object);
		}
	}
}