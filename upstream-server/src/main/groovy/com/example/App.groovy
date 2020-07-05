package com.example

import groovy.util.logging.Slf4j
import io.undertow.Undertow
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import io.undertow.server.handlers.HttpContinueAcceptingHandler
import io.undertow.server.handlers.proxy.ProxyHandler
import io.undertow.util.Headers

@Slf4j
class App {

    static void main(final String[] args) {
        HttpHandler customHandler = new HttpHandler() {
            @Override
            void handleRequest(final HttpServerExchange exchange) throws Exception {
                log.trace('handleRequest()')
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain")
                exchange.getResponseSender().send("Hello World")
            }
        }
        HttpHandler continueHandler = new HttpContinueAcceptingHandler(customHandler)

        Undertow server = Undertow.builder()
                .addHttpListener(8000, "0.0.0.0")
                .setHandler(continueHandler).build()
        server.start();
    }
}
