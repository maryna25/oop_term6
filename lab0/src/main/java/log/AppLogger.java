package log;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class AppLogger {

    private static Logger logger;

    public static Logger getLogger() {
        if (logger == null) {
            BasicConfigurator.configure();
            logger = Logger.getLogger("FACULTY");
        }
        return logger;
    }
}
