package org.haw.praktikum2.shared.io;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public class LoggingPrintWriter extends PrintWriter {
	private static final Logger LOGGER = Logger.getLogger(LoggingPrintWriter.class.getName());
	
	public LoggingPrintWriter(OutputStream out, String charset) throws UnsupportedEncodingException {
		this(out, charset, true);
	}
	
	private LoggingPrintWriter(OutputStream out, String charset, boolean autoFlush) throws UnsupportedEncodingException {
		super(new OutputStreamWriter(out, charset), autoFlush);
	}
	
	@Override
	public void println(String message) {
		LOGGER.info("[SEND] " + message);
		super.println(message);
	}
}
