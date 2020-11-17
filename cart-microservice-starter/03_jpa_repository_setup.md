# Create Spring Data JPA repositories

#### Step 1: Create a new package `com.yugabyte.app.yugastore.cart.repositories` and Implement JPA repositories for Domain model `ShoppingCart` we created in the previous section.

```

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, String> {

}

```

#### Step 2: Add the SQL queries for Adding, Updating and Deleting products from Cart

```
@Modifying
@Transactional
@Query("UPDATE shopping_cart SET quantity = quantity + 1 WHERE user_id = ?1 AND asin =?2")
int updateQuantityForShoppingCart(String userId, String asin);

@Query("SELECT quantity FROM shopping_cart WHERE user_id = ?1 AND asin = ?2")
Optional<Integer> findByUserIdAndAsin(String userId, String asin);

@Query("SELECT sc FROM shopping_cart sc WHERE sc.userId = ?1")
Optional<List<ShoppingCart>> findProductsInCartByUserId(String userId);

@Modifying
@Transactional
@Query("UPDATE shopping_cart SET quantity = quantity - 1 WHERE user_id = ?1 AND asin =?2")
int decrementQuantityForShoppingCart(String userId, String asin);

@Modifying
@Transactional
@Query("DELETE FROM shopping_cart WHERE user_id = ?1")
int deleteProductsInCartByUserId(String userId);
```


#### Step 3: Expose the Repository methods as REST End Points

Annotate `ShoppingCartRepository` interface with `@RepositoryRestResource` annotation.

```
@RepositoryRestResource
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, String> {
}
```