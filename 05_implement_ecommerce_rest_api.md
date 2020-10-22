# Implement the REST APIs

#### Step 1: Lets create a REST controllers for adding and listing Users

Create a new package `com.yugabyte.ybspringworkshop.controller`. Create a new class `UserController.java` and the following HTTP mappings for adding and listing Users.

```
@RestController
public class UserController {
	@Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }
}

```

#### Step 2: Lets now create a REST controllers for adding and listing Products

Create a new class `ProductController.java` and the following HTTP mappings for adding and listing products.

```
@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }


    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }
}
```

#### Step 3: Finally, Lets create a REST controller for placing orders

Create a new class `OrderController.java` and add the below POST mapping to handle order creation. 


```
@RestController
public class OrderController {
	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	OrderLineRepository orderLineRepository;

    @GetMapping("/orders")
    public Page<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @PostMapping("/orders")
    public Order createOrder(@Valid @RequestBody Order order) {

    	double orderTotal = 0.0;
    	for (Order.Product orderProduct : order.getProducts()) {
    		orderTotal += productRepository.findById(orderProduct.getProductId())
    				.map(product -> { return product.getPrice() * orderProduct.getUnits(); 
    				}).get();
    	}

    	order.setOrderTotal(orderTotal);
    	order.setUser(userRepository.findById(order.getUserId()).map(user -> {
            return user;
        }).get());

    	Order newOrder = orderRepository.save(order);
    	
    	for (Order.Product orderProduct : order.getProducts()) {
    		orderLineRepository.save(new OrderLine(newOrder.getOrderId(), orderProduct.getProductId(), orderProduct.getUnits()));
    	}
        
    	return newOrder;
    }
}

```