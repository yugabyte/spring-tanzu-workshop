# Configure YugabyteDB ClusterAware Datasource

#### Step 1: Configure JPA connectivity with YugabyteDB Cluster using `application.properties`

Spring Boot automagically creates the Postgres Datasource and connection objects for us and we will only need to specify the application properties to configure our Data source. We'll add the following properties in our Spring Boot app's `application.properties` file.

```
# Application port.
server.port = 8080

# ---------------------
# JPA/Hibernate config.
spring.jpa.database=POSTGRESQL

# The SQL dialect makes Hibernate generate better SQL for the chosen database.
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect

# Hibernate ddl auto (create, create-drop, validate, update).
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create

# -------------------
# Data-source config.
spring.datasource.platform=postgres
#spring.datasource.url=jdbc:postgresql://localhost:5433/yugabyte
yugabyte.sql.datasource.url=jdbc:postgresql://127.0.0.1:5433/postgres
spring.datasource.username=yugabyte
spring.datasource.password=
```

#### Step 2: Create a new package `com.yugabyte.app.yugastore.cart.config` and create configuration class `YugabyteDataSourceConfig`

```
@Configuration
public class YugabyteDataSourceConfig {
	
	@Value("${yugabyte.sql.datasource.url}")
	String jdbcUrl;

	@Bean
	public DataSource getDataSource() {
		return new YBClusterAwareDataSource(jdbcUrl);
	}
}

```
