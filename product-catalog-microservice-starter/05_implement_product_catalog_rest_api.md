# Implement Product Catalog REST APIs

In this section we'll create the Product Catalog APIs for querying the product information. 

For Product Catalog Microservice, we'll be creating two APIs, one for retrieving product information based on the product id 
and second service for retrieving the products by category.


#### Step 1: Create Service Interface

Create a new package `com.yugabyte.app.yugastore.service` and create the following interfaces -

```
public interface ProductRankingService {

	Optional<ProductRanking> findProductRankingById(String asin);
	
	List<ProductRanking> getProductsByCategory(String category, int limit, int offset);
	
}
```

```
public interface ProductService {

    Optional<ProductMetadata> findById(String id);

    List<ProductMetadata> findAllProductsPageable(int limit, int offset);

}
```

#### Step 2: Create Implementation for the services we created in the previous step

Create a new package `com.yugabyte.app.yugastore.service.impl` and create the following implementations

```
@Service
public class ProductRankingServiceImpl implements ProductRankingService {
	
	private final ProductRankingRepository productRankingRepository;
	
	@Autowired
	public ProductRankingServiceImpl(ProductRankingRepository productRankingRepository) {
		this.productRankingRepository = productRankingRepository;
	}

	@Override
	public Optional<ProductRanking> findProductRankingById(String asin) {
		
		return productRankingRepository.findProductRankingById(asin);
	}

	@Override
	public List<ProductRanking> getProductsByCategory(String category, int limit, int offset) {
		
		return productRankingRepository.getProductsByCategory(category, limit, offset);
	}

} 

```

```
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMetadataRepo productRepository;

    @Autowired
    public ProductServiceImpl(ProductMetadataRepo productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductMetadata> findById(String id) {
        return productRepository.findById(id);
    }

	@Override
	public List<ProductMetadata> findAllProductsPageable(int limit, int offset) {
		
		return productRepository.getProducts(limit, offset);
	}
}
```


#### Step 3: Lets create REST controllers for Product Catalog APIs

Create a new package `com.yugabyte.app.yugastore.controller`. Create a new class `ProductCatalogController.java` and 
add the following HTTP mappings for displaying the Product Catalog Information.

```
@RestController
@RequestMapping(value = "/products-microservice")
public class ProductCatalogController {
  
  // This service is used to lookup metadata of products by their id.
  @Autowired
  ProductService productService;

  // This service is used to lookup the top products by sales rank in a category.
  @Autowired
  ProductRankingService productRankingService;
  
  @RequestMapping(method = RequestMethod.GET, value = "/product/{asin}", produces = "application/json")
  public ProductMetadata getProductDetails(@PathVariable String asin) {
    ProductMetadata productMetadata = productService.findById(asin).get();
    return productMetadata;
  }  

  @RequestMapping(method = RequestMethod.GET, value = "/products", produces = "application/json")
  public List<ProductMetadata> getProducts(@Param("limit") int limit, @Param("offset") int offset) {
    return productService.findAllProductsPageable(limit, offset);
  }  

  @RequestMapping(method = RequestMethod.GET, value = "/products/category/{category}", produces = "application/json")
  public List<ProductRanking> getProductsByCategory(@PathVariable String category,
                                                    @Param("limit") int limit,
                                                    @Param("offset") int offset) {
    return productRankingService.getProductsByCategory(category, limit, offset);
  }  
}
```
