package com.yugabyte.app.yugastore.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableAutoConfiguration
@EnableCassandraRepositories(basePackages = { "com.yugabyte.app.yugastore.repo" })
class YugabyteYCQLConfig {
}
