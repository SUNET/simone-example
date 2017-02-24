# SimOne-example

## Description

## Build
Builds a Wildfly Docker container.

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

## Batch load Orders

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

### Base URI

If you change the port or SimOne is installed behind a firewall you must change the base URI used in the published feed. You can do that by adding the following to the run command.
```bash
-e "SIMONE_BASE_URI=http://my.uri.se"
```
