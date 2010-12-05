package net.vvakame.util.jsonpullparser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class DebugUtil {

	private static PrintWriter pw;

	public static Writer getWriter(File file) throws IOException {
		if (pw != null) {
			return pw;
		}
		FileWriter filewriter = new FileWriter(file, true);
		BufferedWriter bw = new BufferedWriter(filewriter);
		pw = new PrintWriter(bw);

		return pw;
	}

	public static void write(String str) {
		write(str, 0);
	}

	public static void write(String str, int depth) {
		for (int i = 0; i < depth; i++) {
			pw.write("\t");
		}
		pw.write(str);
		pw.write("\n");
		pw.flush();
	}
}
