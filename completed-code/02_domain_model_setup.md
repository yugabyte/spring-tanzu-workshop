# Create JPA Domain models

#### Step 1: Create a new package `com.yugabyte.ybspringworkshop.model` and let's create `User` domain model


```
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long userId;
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    private String userEmail;
    
    public void setUserId(Long userId) {
    	this.userId = userId;
    }
    
    public Long getUserId() {
    	return this.userId;
    }
    
    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }
    
    public String getFirstName() {
    	return this.firstName;
    }
    
    public void setLastName(String lastName) {
    	this.lastName = lastName;
    }
    
    public String getLastName() {
    	return this.lastName;
    }
    
    public void setEmail(String email) {
    	this.userEmail = email;
    }
    
    public String getEmail() {
    	return this.userEmail;
    }
}
```

#### Step 2: Similarly create java classes for `Product`, `Order` and `OrderLine` domain models.

* [Review the code for Product.java](./completed-code/src/main/java/com/yugabyte/springdemo/model/Product.java)
* [Review the code for Order.java](./completed-code/src/main/java/com/yugabyte/springdemo/model/Order.java)
* [Review the code for OrderLine.java](./completed-code/src/main/java/com/yugabyte/springdemo/model/OrderLine.java)
