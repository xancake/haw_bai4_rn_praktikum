package org.haw.praktikum2.shared.connection;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class LoggingPrintWriter extends PrintWriter {
	private static final Logger LOGGER = Logger.getLogger(LoggingPrintWriter.class.getName());
	
	public LoggingPrintWriter(OutputStream out) {
		this(out, true);
	}
	
	public LoggingPrintWriter(OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
	}
	
	@Override
	public void println(String message) {
		LOGGER.info("[SEND] " + message);
		super.println(message);
	}
}
