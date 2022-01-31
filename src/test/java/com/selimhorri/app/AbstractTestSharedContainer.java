package com.selimhorri.app;

import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractTestSharedContainer {
	
	protected static MySQLContainer<?> mysqlContainer;
	
	static {
		mysqlContainer = getInstance();
		mysqlContainer.start();
	}
	
	private synchronized static final MySQLContainer<?> getInstance() {
		if (mysqlContainer == null)
			mysqlContainer = new MySQLContainer<>("mysql:5.7.33");
		return mysqlContainer;
	}
	
	
	
}







