package com.ft.api.jaxrs.errors;

import org.slf4j.Logger;

/**
 * LogLevel
 *
 * @author Simon.Gibbs
 */
public enum LogLevel {
    DEBUG {
        @Override
        public void logTo(Logger logger, String message, Throwable cause) {
            logger.debug(message, cause);
        }
    },
    INFO {
        @Override
        public void logTo(Logger logger, String message, Throwable cause) {
            logger.info(message, cause);
        }
    },
    WARN {
        @Override
        public void logTo(Logger logger, String message, Throwable cause) {
            logger.warn(message, cause);
        }
    },
    ERROR {
        @Override
        public void logTo(Logger logger, String message, Throwable cause) {
            logger.error(message, cause);
        }
    };

    public abstract void logTo(Logger logger, String message, Throwable cause);

    public void logTo(Logger logger, String message) {
        this.logTo(logger, message, null);
    }

}
