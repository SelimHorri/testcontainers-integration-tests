package com.selimhorri.app;

import org.testcontainers.containers.OracleContainer;
import org.testcontainers.utility.DockerImageName;

public class AbstractTestSharedOracleContainer {
	
	protected static final OracleContainer ORACLE_CONTAINER;
	private static final DockerImageName DOCKER_IMAGE_NAME;
	
	static {
		DOCKER_IMAGE_NAME = DockerImageName.parse("gvenzl/oracle-xe:slim");
		ORACLE_CONTAINER = new OracleContainer(DOCKER_IMAGE_NAME);
		ORACLE_CONTAINER.start();
	}
	
	
	
}
