# Create JPA Domain models

#### Step 1: Create a new package `com.yugabyte.app.yugastore.cart.domain` and let's create `ShoppingCart` domain model


```
@Entity(name = "shopping_cart")
@Table(name = "shopping_cart")
public class ShoppingCart {
	
	@Id
	@Column(name = "cart_key")
	private String cartKey;

	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "asin")
	private String asin;

	@Column(name = "time_added")
	private String time_added;
	
	@Column(name = "quantity")
	private int quantity;
	
	public String getCartKey() {
		return cartKey;
	}

	public void setCartKey(String cartKey) {
		this.cartKey = cartKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}
	
	public String getTime_added() {
		return time_added;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setTime_added(String time_added) {
		this.time_added = time_added;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
```