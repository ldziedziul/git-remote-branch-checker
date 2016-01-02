appender("console", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%date{HH:mm:ss.SSS} [%t] %-5p %c{40} - %m%n"
    }
}
root(INFO, ["console"])

logger("pl.eonsoft", DEBUG)
logger("org.apache", WARN)
