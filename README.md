Steps to reproduce:

1. Run project from `upstream-server` dir (`./gradlew run`)
2. Run project from `reverse-proxy` dir (`./gradlew run`)
3. Execute `curl -v -H "Expect: 100-continue" http://localhost:9000`

Expected behaviour:
```
$ curl -v --noproxy localhost -H "Expect: 100-continue" http://localhost:8000
* Expire in 0 ms for 6 (transfer 0x4d2cd0)
* Expire in 1 ms for 1 (transfer 0x4d2cd0)
* Expire in 0 ms for 1 (transfer 0x4d2cd0)
* Expire in 2 ms for 1 (transfer 0x4d2cd0)
* Expire in 1 ms for 1 (transfer 0x4d2cd0)
* Expire in 1 ms for 1 (transfer 0x4d2cd0)
* Expire in 1 ms for 1 (transfer 0x4d2cd0)
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Expire in 149997 ms for 3 (transfer 0x4d2cd0)
* Expire in 200 ms for 4 (transfer 0x4d2cd0)
* Connected to localhost (127.0.0.1) port 8000 (#0)
> GET / HTTP/1.1
> Host: localhost:8000
> User-Agent: curl/7.64.0
> Accept: */*
> Expect: 100-continue
>
< HTTP/1.1 100 Continue
< Content-Length: 0
< HTTP/1.1 200 OK
< Connection: keep-alive
< Content-Type: text/plain
< Content-Length: 11
< Date: Mon, 22 Jun 2020 09:05:18 GMT
<
Hello World* Connection #0 to host localhost left intact
```

Actual behaviour:

```
$ curl -v --noproxy localhost -H "Expect: 100-continue" http://localhost:9000
* Expire in 0 ms for 6 (transfer 0x6a2cd0)
* Expire in 1 ms for 1 (transfer 0x6a2cd0)
* Expire in 0 ms for 1 (transfer 0x6a2cd0)
* Expire in 2 ms for 1 (transfer 0x6a2cd0)
* Expire in 1 ms for 1 (transfer 0x6a2cd0)
* Expire in 1 ms for 1 (transfer 0x6a2cd0)
* Expire in 2 ms for 1 (transfer 0x6a2cd0)
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Expire in 149996 ms for 3 (transfer 0x6a2cd0)
* Expire in 200 ms for 4 (transfer 0x6a2cd0)
* Connected to localhost (127.0.0.1) port 9000 (#0)
> GET / HTTP/1.1
> Host: localhost:9000
> User-Agent: curl/7.64.0
> Accept: */*
> Expect: 100-continue
>
< HTTP/1.1 100 Continue
* Recv failure: Connection was reset
* Closing connection 0
curl: (56) Recv failure: Connection was reset
```