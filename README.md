# SimOne-example

Simple example of how to build a simulator based on [SimOne](https://github.com/SUNET/simone). The example generates its API from [xsd](src/main/resources/order.xsd), creates a war that depends on the SimOne core jar. The war is built in a Jboss Wildfly Swarm server that runs in a Docker container.

[OrderResource.java](src/main/java/se/uhr/simone/restbucks/boundary/OrderResource.java): Implements A JAX-RS REST API to simulate. In the example it is possible to create and view a Coffee order.

[OrderController.java](src/main/java/se/uhr/simone/restbucks/control/OrderController.java): Create the example order and submits a event that is published on the FEED.

[OrderRepository.java](src/main/java/se/uhr/simone/restbucks/entity/OrderRepository.java): Stores the order for later retrieval.

## Clone

```bash
git clone https://github.com/SUNET/simone-example.git
cd simone-example
```

## Build
Builds the Wildfly Swarm uber jar and Docker container.

```bash
mvn package
```
## Run

## Without Docker

```bash
java -jar target/simeone-example-swarm.jar
```

## With Docker

Run the example on port 8080

```bash
docker run -it --rm -p 8080:8080 simone-example
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

See <http://localhost:8080/doc/#/database_admin> for more information about manipulating the database.

### Send custom Feed event

Sends a custom Feed event (possible erroneous) for test purposes

```bash
curl -X POST --header 'Content-Type: application/json' -d '{"contentType": "application/xml","content": "<message>hello</message>"}' 'http://localhost:8080/admin/feed/event'
```

See <http://localhost:8080/doc/#/feed_admin> for more information about manipulating the feed.

### Make SimOne answer with a different HTTP status code

For every REST request respond with status code 201.

```bash
curl -X PUT --header 'Content-Type: application/json' -d '201' 'http://localhost:8080/admin/rs/response/code/global'
```

See <http://localhost:8080/doc/#/rest_admin> for more information about manipulating the REST-API.

## Batch load

There are two options to load SimOne with a batch of orders.

### Use the admin REST API

Load SimOne with information from `etc/orders.txt`

```bash
curl -X POST --header 'Content-Type: multipart/form-data' -F name=orders.txt -F 'content=@etc/orders.txt' 'http://localhost:8080/admin/database'
```

### Dropin directory

The dropin directory must be mounted when when the cointainer is started, first create a directory on you host `/tmp/mydropindir` then add `-v /tmp/mydropindir:/var/simone/dropin` to the docker run command.

```bash
cp etc/orders.txt /tmp/mydropindir/
```

## Inspect the database

Add `-p 1527:1527` to the docker run command. Use a SQL CLient with the `org.apache.derby.jdbc.ClientDriver` driver.

### Feed database

URL: `jdbc:derby://localhost:1527/feed`

### Restbucks database

URL: `jdbc:derby://localhost:1527/restbucks`

## Swagger

The Swagger definition is available at <http://localhost:8080/swagger.json>

See [Docker Compose](#docker-compose) for how to view the Swagger documentation.

## Tips

### Re-use loaded database

You may mount the database directory `/var/simone/db` if you save the simulator data between re-starts. First create a directory on you host `/tmp/mydbdir` then add `-v /tmp/mydbdir:/var/simone/db` to the docker run command.

### Base URI

If you change the port or SimOne is installed behind a firewall you must change the base URI used in the published feed. You can do that by adding the following to the run command.
```bash
-e "SIMONE_BASE_URI=http://my.uri.se"
```

## Files

### Logfiles

Logfiles written to stdout.

### Database

All database files are stored under `/var/simone/db`

### Dropin

`dropin` is a special directory that is monitored by SimOne. When a new file is discovered extensions are notified and may handle the file in any way they want. The dropin directory location is `/var/simone/dropin`

## Debug

### Remote debug the application

You can remote debug the application in the running container by hooking up jdb to port 8787. Note that you also must expose the port in the docker run command.

```bash
jdb -attach 8787
```

# Docker Compose

The example includes a Docker Compose file that starts the example app along with a Swagger server.

```bash
docker-compose up
```

Checkout the Swagger documentation at <http://localhost:8090>

# Known problems

* It is not possible to load SimOne by selecting a file in Swagger.

## Prerequisites

* Git
* JDK 1.8
* Maven 3
* Docker

## Todo

* Create a Maven archetype that creates a new project from this example
