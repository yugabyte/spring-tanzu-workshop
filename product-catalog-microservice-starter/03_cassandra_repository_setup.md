# Create Spring Data Cassandra Repositories for YCQL access

#### Step 1: Create a repository for `ProductMetadata` domain

Create a new package `com.yugabyte.app.yugastore.repo` and create the interface `ProductMetadataRepo.java`.

```
@RepositoryRestResource(path = "product")
public interface ProductMetadataRepo extends CassandraRepository<ProductMetadata, String> {
	
	@Query("SELECT * FROM cronos.products limit ?0 offset ?1")
	@RestResource(path = "products", rel = "products")
	public List<ProductMetadata> getProducts(@Param("limit") int limit, @Param("offset") int offset);

	Optional<ProductMetadata> findById(String id);
}
```
`@RepositoryRestResource` annotation exposes repository methods as REST APIs.

#### Step 2: Create a repository for `ProductRanking` domain

In the package `com.yugabyte.app.yugastore.repo`, create a new interface `ProductRankingRepository.java`.

```
public interface ProductRankingRepository extends CassandraRepository<ProductRanking, String> {
	
	@Query("select * from cronos.product_rankings where asin=?0")
	@RestResource(path = "product", rel = "product")
	Optional<ProductRanking> findProductRankingById(String asin);
	
	@Query("SELECT * FROM cronos.product_rankings where category =?0 limit ?1 offset ?2")
	@RestResource(path = "category", rel = "category")
	public List<ProductRanking> getProductsByCategory(@Param("name") String category, @Param("limit") int limit, @Param("offset") int offset);

}
```