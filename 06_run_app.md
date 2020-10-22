# Run the app on your workstation

#### Step 1: Create a local cluster on Docker

To create a 1-node cluster with a replication factor (RF) of 1, run the command below.

```
$ docker run -d --name yugabyte  -p7000:7000 -p9000:9000 -p5433:5433 -p9042:9042\
 yugabytedb/yugabyte:latest bin/yugabyted start\
 --daemon=false
```

Please checkout our [docs](https://docs.yugabyte.com/latest/deploy/) for other deployment methods

#### Step 2: Build the Spring Boot application 

```
$ ./mvnw clean package
```

#### Step 3: Start the Spring Boot application

```
$ ./mvnw spring-boot:run
```

# APIs

## Create a user

You can create a user named `John Smith` and email `jsmith@yb.com` as follows:

```
$ curl --data '{ "firstName" : "John", "lastName" : "Smith", "email" : "jsmith@yb.com" }' \
       -v -X POST -H 'Content-Type:application/json' http://localhost:8080/users
```

This will return the inserted record as a JSON document:
```
{
  "userId": "1",
  "firstName": "John",
  "lastName": "Smith",
  "email": "jsmith@yb.com"
}
```

You can connect to YugaByte DB using `psql` and select these records:
```
postgres=# select * from users;
 user_id | first_name | last_name |  user_email
---------+------------+-----------+---------------
       1 | John       | Smith     | jsmith@yb.com(1 row)
```

## List all users

You can list the current set of users by running the following:
```
$ curl http://localhost:8080/users
```

You should see the following output:
```
{
  "content": [
    {
      "userId":"1",
      "email":"jsmith@yb.com",
      "firstName":"John",
      "lastName":"Smith"
    }
  ],
  ...
}
```

## Create a product

You can create a product listing as follows:
```
$ curl \
  --data '{ "productName": "Notebook", "description": "200 page notebook", "price": 7.50 }' \
  -v -X POST -H 'Content-Type:application/json' http://localhost:8080/products
```

You should see the following return value:
```
{
  "productId": "1",
  "productName": "Notebook",
  "description": "200 page, hardbound, blank notebook",
  "price": 7.5}
```

## List all products

You can do this as follows:
```
$ curl http://localhost:8080/products
```

You should see an output as follows:
```
{
  "content":[
    {
      "productId": "1",
      "productName": "Notebook","description":"200 page, hardbound, blank notebook",
      "price": 7.5
    }
  ],
  ...
}
```

## Create an order

Creating an order involves a user id ordering a particular product, this can be achieved as follows:
```
$ curl \
  --data '{ "userId": "1", "products": [ { "productId": 1, "units": 2 } ] }' \
  -v -X POST -H 'Content-Type:application/json' http://localhost:8080/orders
```

Note that you can check out multiple products in one order. As an example, the following POST payload makes one user (id=1) checkout two products (id=1 and id=2) by creating the following payload:

```
{ "userId": "1", "products": [ { "productId": 1, "units": 2 }, { "productId": 2, "units": 4 } ] }
```
