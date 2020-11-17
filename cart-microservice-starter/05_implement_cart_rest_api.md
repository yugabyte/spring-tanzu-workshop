# Implement the REST APIs

In this section we'll create the Shopping Cart APIs for adding and removing products from the cart

#### Step 1: Create a Shopping Cart Service

Create a new package `com.yugabyte.app.yugastore.cart.service` and create a class `ShoppingCartImpl.java`.
This service will use `ShoppingCartRepository` for accessing Shopping cart data.

```
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartImpl {
	
	private final ShoppingCartRepository shoppingCartRepository;

	@Autowired
	public ShoppingCartImpl(ShoppingCartRepository shoppingCartRepository) {
		this.shoppingCartRepository = shoppingCartRepository;
	}
	
}

```


#### Step 2: Add implementation for Adding and Deleting Products from Cart Service

```
/**
	 * If product is in the map just increment quantity by 1. If product is not in
	 * the map with, add it with quantity 1
	 *
	 * @param product
	 */
	public void addProductToShoppingCart(String userId, String asin) {

		String shoppingCartKeyStr = userId + "-" + asin;
		if (shoppingCartRepository.findById(shoppingCartKeyStr).isPresent()) {
			shoppingCartRepository.updateQuantityForShoppingCart(userId, asin);
			System.out.println("Adding product: " + asin);
		} else {
			ShoppingCart currentShoppingCart = createCartObject(userId, asin);
			shoppingCartRepository.save(currentShoppingCart);
			System.out.println("Adding product: " + asin);
		}
	}

	public Map<String, Integer> getProductsInCart(String userId) {

		Map<String, Integer> productsInCartAsin = new HashMap<String, Integer>();

		if (shoppingCartRepository.findProductsInCartByUserId(userId).isPresent()) {

			List<ShoppingCart> productsInCart = shoppingCartRepository.findProductsInCartByUserId(userId).get();
			for (ShoppingCart item : productsInCart) {
				productsInCartAsin.put(item.getAsin(), item.getQuantity());
			}

		}
		return productsInCartAsin;
	}

	public void removeProductFromCart(String userId, String asin) {
		String shoppingCartKeyStr = userId + "-" + asin;
		if (shoppingCartRepository.findById(shoppingCartKeyStr).isPresent()) {
			if (shoppingCartRepository.findById(shoppingCartKeyStr).get().getQuantity() > 1) {
				shoppingCartRepository.decrementQuantityForShoppingCart(userId, asin);
				System.out.println("Decrementing product: " + asin + " quantity");
			} else if (shoppingCartRepository.findById(shoppingCartKeyStr).get().getQuantity() == 1) {
				shoppingCartRepository.deleteById(shoppingCartKeyStr);
				System.out.println("Removing product: " + asin + " since it was qty 1");
			}
		}
	}

	private ShoppingCart createCartObject(String userId, String asin) {
		ShoppingCart currentShoppingCart = new ShoppingCart();
		currentShoppingCart.setCartKey(userId + "-" + asin);
		currentShoppingCart.setUserId(userId);
		currentShoppingCart.setAsin(asin);
		LocalDateTime currentTime = LocalDateTime.now();
		currentShoppingCart.setTime_added(currentTime.toString());
		currentShoppingCart.setQuantity(1);

		return currentShoppingCart;
	}

	public void clearCart(String userId) {

		if (shoppingCartRepository.findProductsInCartByUserId(userId).isPresent()) {
			shoppingCartRepository.deleteProductsInCartByUserId(userId);
			System.out.println("Deleteing all products for user: " + userId + " since checkout was successful");
		}
	}

```

#### Step 3: Lets create REST controllers for adding and deleting carts from Shopping Cart

Create a new package `com.yugabyte.app.yugastore.cart.controller`. Create a new class `ShoppingCartController.java` and 
add the following HTTP mappings for adding and deleting products from Shopping Cart.

```
@RestController
@RequestMapping(value = "/cart-microservice")
public class ShoppingCartController {
	
	@Autowired
	ShoppingCartImpl shoppingCart;
	
	@RequestMapping(method = RequestMethod.GET, value = "/shoppingCart/addProduct", produces = "application/json")
	public String addProductToCart(@RequestParam("userid") String userId, 
			@RequestParam("asin") String asin) {
		shoppingCart.addProductToShoppingCart(userId, asin);
		return String.format("Added to Cart");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/shoppingCart/productsInCart", produces = "application/json")
	public Map<String, Integer> getProductsInCart(@RequestParam("userid") String userId) {
		return shoppingCart.getProductsInCart(userId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/shoppingCart/removeProduct", produces = "application/json")
	public String removeProductFromCart(@RequestParam("userid") String userId, 
			@RequestParam("asin") String asin) {
		shoppingCart.removeProductFromCart(userId, asin);
		return String.format("Removing from Cart");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/shoppingCart/clearCart", produces = "application/json")
	public String clearCart(@RequestParam("userid") String userId) {
		 shoppingCart.clearCart(userId);
		 return String.format("Clearing Cart, Checkout successful");
	}

}
```

