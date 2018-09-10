package com.ideas2it.application.logger;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

import com.ideas2it.application.common.Constants;
/**
 * <p>
 * ApplicationLogger class is the logger class 
 * that collects all log messages from various 
 * classes in this application and logs them.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class ApplicationLogger {

    private static Logger logger;
    static {
        logger = Logger.getLogger(ApplicationLogger.class);
        PropertyConfigurator.configure(Constants.LOG4J_PROPERTIES_URL);
    }

    public static void debug(String message,Exception e){
        logger.debug(message,e);
    }

    public static void error(String message,Exception e){
        logger.error(message +"\n" + e);
    }

    public static void fatal(String message){
        logger.fatal(message);
    }

    public static void info(String message){
        logger.info(message);
    }

    public static void warn(String message){
        logger.warn(message);
    }

    public static void trace(String message){
        logger.trace(message);
    }
}
