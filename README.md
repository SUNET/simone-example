# SimOne-example

## Description

Simple example of how to build a simulator based on [SimOne](https://github.com/SUNET/simone). The example builds a war including the SimOne core jar. The war is deployed in a Jboss Wildfly server that runs in a Docker container. The Docker image is based on the SimOne image.


[OrderResource.java](src/main/java/se/uhr/simone/restbucks/boundary/OrderResource.java): Implements the REST API to simulate. In the example it is possible to create a Coffee order.

[OrderController.java](src/main/java/se/uhr/simone/restbucks/control/OrderController.java): Create the example order and submits a event that is published on the FEED.

[OrderRepository.java](src/main/java/se/uhr/simone/restbucks/entity/OrderRepository.java): Stores the order for later retrieval

## Build
Builds the war and deploys it in a Jboss Wildfly Docker container.

```bash
mvn package docker:build 
```
## Run

Run the example on port 8080

```bash
docker run -it --rm -p 8080:8080 simone-example
```

## Try it out	

### Add Order 

```bash
curl -X POST -d "Coffee" 'http://localhost:8080/sim/api/order' 
```

### Read Orders

```bash
curl 'http://localhost:8080/sim/api/order' 
```

### Read Feed 

```bash
curl 'http://localhost:8080/sim/api/feed/recent'
```

### Send custom Feed event

Sends a custom Feed event for test purposes

```bash
curl -X POST --header 'Content-Type: application/json' -d '{"contentType": "application/xml","content": "<message>hello</message>"}' 'http://localhost:8080/sim/api/admin/feed/event'
```

See <http://localhost:8080/sim/doc/#/feed_admin> for more information about manipulating the feed.

### Make SimOne answer with a different HTTP status code 

For every REST request respond with status code 201.

```bash
curl -X PUT --header 'Content-Type: application/json' -d '201' 'http://localhost:8080/sim/api/admin/rs/response/code/global'
```

See <http://localhost:8080/sim/doc/#/rest_admin> for more information about manipulating the feed.

## Batch load

There are to options to load SimOne with a batch of orders.

### REST

Load SimOne with information from `etc/orders.txt`

```bash
curl -X POST --header 'Content-Type: multipart/form-data' -F name=orders.txt -F 'content=@etc/orders.txt' 'http://localhost:8080/sim/api/admin/database'
```

### Dropin directory

The dropin directory must be mounted when when the cointainer is started, first create a directory on you host `/tmp/mydropindir` then add `-v /tmp/mydropindir:/var/simone/dropin` to the docker run command.

```bash
cp cp etc/orders.txt /tmp/mydropindir/
```
## Tips

### Re-use loaded database

You may mount the database directory `/var/simone/db` if you save the simulator data between re-starts. First create a directory on you host `/tmp/mydbdir` then add `-v /tmp/mydbdir:/var/simone/db` to the docker run command.

### Base URI

If you change the port or SimOne is installed behind a firewall you must change the base URI used in the published feed. You can do that by adding the following to the run command.
```bash
-e "SIMONE_BASE_URI=http://my.uri.se"
```

## Todo

* Create Maven archetype that creates a project from this example 
