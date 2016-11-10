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

        @Override
        public void logTo(Logger logger, String message, Object... args) {
            logger.debug(message, args);
        }
    },
    INFO {
        @Override
        public void logTo(Logger logger, String message, Throwable cause) {
            logger.info(message, cause);
        }
        @Override
        public void logTo(Logger logger, String message, Object... args) {
            logger.info(message, args);
        }
    },
    WARN {
        @Override
        public void logTo(Logger logger, String message, Throwable cause) {
            logger.warn(message, cause);
        }
        @Override
        public void logTo(Logger logger, String message, Object... args) {
            logger.warn(message, args);
        }
    },
    ERROR {
        @Override
        public void logTo(Logger logger, String message, Throwable cause) {
            logger.error(message, cause);
        }
        @Override
        public void logTo(Logger logger, String message, Object... args) {
            logger.error(message, args);
        }
    };

    public abstract void logTo(Logger logger, String message, Throwable cause);
    public abstract void logTo(Logger logger, String message, Object... args);

    public void logTo(Logger logger, String message) {
        this.logTo(logger, message, (Throwable) null);
    }

}
