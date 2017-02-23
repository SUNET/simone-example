# simone-example

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

### Base URI
If you change the port or SimOne is installed behind a firewall you must change the base URI used in the published feed. You can do that by adding the following to the run command.
```bash
-e "SIMONE_BASE_URI=http://my.uri.se"
```
## Try it out	

### Add Order 

### Read Feed

### Batch load Orders

## Tips

## Debug

remote debug
