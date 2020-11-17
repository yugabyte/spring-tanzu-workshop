## Setup YCQL Tables

a. Set the following environment variables to YCQL HOST and YCQL PORT.  example: 

```
$ export CQLSH_HOST=192.168.64.3
$ export CQLSH_PORT=31620
```

b. Now create the necessary tables as shown below. 

```
$ ycqlsh -f database-setup/schema.cql
```

C. Load sample dataset for Product catalog. 

```
$ cd database-setup
$ ./dataload.sh
```

## Build and Run on Local Workstation

## Build Jars with Maven 
To build, simply run the following from the base directory:

```
$ mvn -DskipTests clean package
```

## Run the app on local workstation

```
$ mvn spring-boot:run
```

navigate to swagger-UI

```
http://localhost:8082/swagger-ui/index.html#/
```


## Kubernetes Deployment


### Build a Docker Image with Maven

To get started quickly, you can run Jib without even changing your pom.xml:

```
$ ./mvnw com.google.cloud.tools:jib-maven-plugin:dockerBuild -Dimage=nchandrappa/product-catalog-microservice
```

To push to a Docker registry you use the build goal, instead of dockerBuild, i.e.

```
$ ./mvnw com.google.cloud.tools:jib-maven-plugin:build -Dimage=nchandrappa/product-catalog-microservice
```

Note: Update docker image id to reflect the docker repository of your choice.


### Deploy Spring Boot App onto Kubernetes


```
$ kubectl apply -f config-map.yaml
$ kubectl apply -f deployment.yaml
```

navigate to swagger-UI

```
http://<loadbalancer-ip>/swagger-ui/index.html#/
```