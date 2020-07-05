import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy

import static ch.qos.logback.classic.Level.OFF
import static ch.qos.logback.classic.Level.TRACE
import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.WARN
import static ch.qos.logback.classic.Level.ERROR
import static ch.qos.logback.classic.Level.ALL
import static groovy.json.JsonOutput.toJson

appender("console", ConsoleAppender) {
    withJansi = false
    encoder(PatternLayoutEncoder) {
        pattern = "%cyan(%d{HH:mm:ss.SSS}) %gray([%-15.15thread]) %highlight(%-5level) %magenta(%-40.40logger{40}) - %msg%n"
    }
}

appender("file", RollingFileAppender) {
    file = "./logs/app.log"
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = "%-30(%date [%-15.15thread]) %-5level %-40.40logger{40} - %msg%n"
    }
    rollingPolicy(FixedWindowRollingPolicy) {
        fileNamePattern = "./logs/app.%i.log"
        minIndex = 1
        maxIndex = 9
    }
    triggeringPolicy(SizeBasedTriggeringPolicy) {
        maxFileSize = "2MB"
    }
}

logger('com.example', TRACE)
logger('ratpack', TRACE)

root(INFO, ["file", "console"])