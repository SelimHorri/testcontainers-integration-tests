package com.selimhorri.app;

import org.testcontainers.containers.OracleContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractTestSharedOracleContainer {
	
	protected static OracleContainer oracleContainer;
	
	static {
		oracleContainer = getInstance();
		oracleContainer.start();
	}
	
	private synchronized static final OracleContainer getInstance() {
		if (oracleContainer == null)
			oracleContainer = new OracleContainer(DockerImageName.parse("gvenzl/oracle-xe:slim"));
		return oracleContainer;
	}
	
	
	
}
