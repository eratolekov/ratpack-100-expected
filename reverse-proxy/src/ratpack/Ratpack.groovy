import com.example.ProxyHandler
import ratpack.server.ServerConfig

import static ratpack.groovy.Groovy.ratpack

ratpack {
    serverConfig { //config ->
        port(9000)
        address(InetAddress.getByName('0.0.0.0'))
    }
    handlers {
        all new ProxyHandler()
    }
}
