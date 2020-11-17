# Cart Microservice

## Setup YSQL Table

a. Set the following environment variables to YSQL HOST and YSQL PORT.  example: 

```
$ export YSQLSH_HOST=192.168.64.3
$ export YSQLSH_PORT=32517
```

b. Now create the necessary tables as shown below. 

```
$ ysqlsh -h $YSQLSH_HOST -p $YSQLSH_PORT -d postgres -f database-setup/schema.sql
```

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
http://localhost:8081/swagger-ui/index.html#/
```

### APIs

Add Product to Cart

```
http://localhost:8081/cart-microservice/shoppingCart/addProduct?userid=u1001&asin=a102
```

Show Cart items

```
http://localhost:8081/cart-microservice/shoppingCart/productsInCart?userid=u1001
```

Remove item from a Cart

```
http://localhost:8081/cart-microservice/shoppingCart/removeProduct?userid=u1001&asin=a102
```

## Kubernetes Deployment

### Build a Docker Image with Maven

To get started quickly, you can run Jib without even changing your pom.xml:

```
$ ./mvnw com.google.cloud.tools:jib-maven-plugin:dockerBuild -Dimage=nchandrappa/cart-microservice
```

To push to a Docker registry you use the build goal, instead of dockerBuild, i.e.

```
$ ./mvnw com.google.cloud.tools:jib-maven-plugin:build -Dimage=nchandrappa/cart-microservice
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
