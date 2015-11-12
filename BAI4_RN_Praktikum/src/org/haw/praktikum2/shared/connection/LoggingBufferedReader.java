package org.haw.praktikum2.shared.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;

public class LoggingBufferedReader extends BufferedReader {
	private static final Logger LOGGER = Logger.getLogger(LoggingBufferedReader.class.getName());
	
	public LoggingBufferedReader(InputStream in) {
		this(new InputStreamReader(in));
	}
	
	public LoggingBufferedReader(Reader in) {
		super(in);
	}
	
	@Override
	public String readLine() throws IOException {
		String line = super.readLine();
		LOGGER.info("[RECV] " + line);
		return line;
	}
}
