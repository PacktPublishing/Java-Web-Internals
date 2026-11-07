# Java-Best-Practices
Java Best Practices Title

## Chapter 11 - Running the sample application

The Chapter 11 sample (`Chapter11/MyWebTestApplication`) uses the Simple Web Framework
built in Chapters 08-09, so the framework must be installed in your local Maven repository first.

**Requirements:** Java 17+, Maven 3.9+ and [HTTPie](https://httpie.io/cli) for testing.

### 1. Install the framework

```bash
cd Chapters08-09/simpleWebFramework
mvn install
```

### 2. Run the application

```bash
cd ../../Chapter11/MyWebTestApplication
mvn compile exec:exec
```

The server starts on port **8080** (stop it with `Ctrl+C`).

### 3. Test it with HTTPie

In another terminal:

```bash
# GET endpoints
http :8080/hello
http :8080/test
http :8080/product
http :8080/user
http :8080/injected          # uses the service injected with @SimpleInjected
http :8080/jchampionsconf

# POST endpoints (JSON body mapped with @SimpleBody)
http POST :8080/product id:=1 name=Mouse price:=50.0 linkPhoto=mouse.jpg
http POST :8080/user username=john password=secret level:=1
```

Expected responses:

| Request | Response |
|---|---|
| `GET /hello` | `"Hello World"` |
| `GET /test` | `"it Works!"` |
| `GET /product` | `{"id":123456,"name":"Computer","price":2500.0,"linkPhoto":"computer.jpg"}` |
| `GET /user` | `{"username":"admin","password":"1234","level":0}` |
| `GET /injected` | `"ISI:Hello World"` |
| `GET /jchampionsconf` | `"Hello World from JChampions Conference"` |
| `POST /product`, `POST /user` | the same JSON object that was sent |
