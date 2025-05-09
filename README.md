# SimOne-example

Simple example of how to build a simulator based on [SimOne](https://github.com/swedish-council-for-higher-education/simone). The example creates a Quarkus server that depends on the SimOne core jar.

[OrderResource.java](src/main/java/se/uhr/simone/restbucks/boundary/OrderResource.java): Implements A JAX-RS REST API to simulate. In the example it is possible to create and view a Coffee order.

[OrderController.java](src/main/java/se/uhr/simone/restbucks/control/OrderController.java): Create the example order and submits a event that is published on the FEED.

[OrderRepository.java](src/main/java/se/uhr/simone/restbucks/entity/OrderRepository.java): Stores the order for later retrieval.


## Run

### Development mode

```
./mvnw quarkus:dev
```

### Java application

```
./mvnw clean package
```

```bash
java -jar target/simeone-example-runner.jar
```

### Docker container

```bash
./mvnw clean package -Dquarkus.container-image.build=true
```

```bash
docker run -it --rm -p 8080:8080 -p 1527:1527 test/simone-example
```

## Try it out

### Add Order

```bash
curl -X POST -d "Coffee" 'http://localhost:8080/order'
```

### Read Orders

```bash
curl 'http://localhost:8080/order'
```

### Read the Feed

```bash
curl 'http://localhost:8080/feed/recent'
```

### Empty the database

```bash
curl -X DELETE 'http://localhost:8080/admin/database'
```

### Send custom Feed event

Sends a custom Feed event (possible erroneous) for test purposes

```bash
curl -X POST --header 'Content-Type: application/json' -d '{"contentType": "application/xml","content": "<message>hello</message>"}' 'http://localhost:8080/admin/feed/event'
```

### Make SimOne answer with a different HTTP status code

For every REST request respond with status code 201.

```bash
curl -X PUT --header 'Content-Type: application/json' -d '201' 'http://localhost:8080/admin/rs/response/code/global'
```

See [OpenAPI](#OpenAPI) for more information about the admin API.

## Batch load

There are two options to load SimOne with a batch of orders.

### Use the admin REST API

Load SimOne with information from `etc/orders.txt`

```bash
curl -X POST --header 'Content-Type: multipart/form-data' -F 'file=@etc/orders.txt' 'http://localhost:8080/order/file'
```

See [simone-example.http](etc/simone-example.http) for more examples.

## Inspect the feed database

Expose port 1527 from the docker container. Use a SQL CLient with the `org.apache.derby.jdbc.ClientDriver` driver.

URL: `jdbc:derby://localhost:1527/memory:feed;create=false`

## OpenAPI

When you run the application the OpenAPI documentation is available at: <http://localhost:8080/q/openapi>


## Tips

## Debug

### Remote debug the application

You can remote debug the application in the running container by hooking up jdb to port 8787. Note that you also must expose the port in the docker run command.

```bash
jdb -attach 8787
```

# Microprofile Health

```bash
curl http://localhost:8080/q/health
```

# Microprofile Metrics

Get number of orders

```bash
curl http://localhost:8080/q/metrics
```

# Known problems

* It is not possible to load SimOne by selecting a file in Swagger.

## Prerequisites

* JDK 17

