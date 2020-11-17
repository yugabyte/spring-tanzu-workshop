# Create Spring Data JPA repositories

#### Step 1: Create a new package `com.yugabyte.ybspringworkshop.repo` and Implement JPA repositories for Domain models we created in the previous section.

```

public interface UserRepository extends JpaRepository<User, Long> {

}

```


```

public interface ProductRepository extends JpaRepository<Product, Long> {

}
```


```
public interface OrderRepository extends JpaRepository<Order, UUID> {

}

```


```
public interface OrderLineRepository extends JpaRepository<OrderLine, UUID> {

	public List<OrderLine> findByOrderId(UUID orderId);
}
```

#### Step 2: Configure Spring Boot app to enable JPA Repositories

Add the following annotation to Spring Boot Application class `YbSpringWorkshopApplication.java`

```
@EnableJpaRepositories(basePackageClasses = UserRepository.class)

```