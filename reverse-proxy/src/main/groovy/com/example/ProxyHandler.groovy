package com.example

import groovy.util.logging.Slf4j
import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler
import ratpack.http.client.HttpClient
import ratpack.http.client.RequestSpec
import ratpack.http.client.StreamedResponse

@Slf4j
class ProxyHandler extends GroovyHandler {

    @Override
    protected void handle(GroovyContext context) {
        context.with {
            log.debug("rawURI: ${request.rawUri}")
            log.debug("request ${request.method.name} ${request.path} ${request.protocol}")
            HttpClient httpClient = context.get(HttpClient)
            URI proxyUri = URI.create("http://localhost:8000${request.rawUri}")

            httpClient.requestStream(proxyUri) { RequestSpec spec ->
                spec.headers.copy(request.headers)
                spec.headers.set('Host', 'localhost')
            }.then { StreamedResponse responseStream ->
                log.debug("upstream response: ${responseStream.status.code}")
                responseStream.forwardTo(response)
            }
        }
    }
}
