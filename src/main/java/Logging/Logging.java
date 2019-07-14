package Logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
	
	static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	static FileHandler fileHandler = null;
	
	public static Logger getLogger() {
		
		
		try {
			fileHandler = new FileHandler("application.log",true);
			logger.addHandler(fileHandler);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fileHandler.setFormatter(formatter);  
			return logger;
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
			return null;
		}  
        
	}
}
