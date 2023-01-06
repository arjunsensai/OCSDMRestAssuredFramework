/**
* @author Nagarjun Kepulu
 */

/***************************************************/

package com.oracle.ocsdm.utils;

import java.util.Properties;

import com.oracle.ocsdm.api.enums.EnvType;

/*Singleton Design pattern*/
public class ConfigLoader {

	private static final String CLIENT_ID = "client_id";
	private static final String CLIENT_SECRET = "client_secret";
	private static final String GRANT_TYPE = "device_group_name";
	private static final String PASSWORD = "password";
	private static final String USERNAME = "userName";

	private static final String BASE_URL_HTTP = "base_url_http";
	private static final String BASE_URL_HTTPS = "base_uri_https";

	private static final String OVERRIDE_REPORTS = "override_reports";
	private static final String REQUEST_RESPONSE_DETAILS_IN_REPORTS = "request_response_details_in_reports";
	private static final String SEND_EMAIL_TO_USERS = "send_email_to_users";
	private static final String RETRY_FAILED_TESTS = "retry_failed_tests";

	private static final String ENV = "env";
	private static final String CONFIG_PROPERTIES = "_config.properties";

	/* Default config file is stg_config.properties */
	private static final String STG_CONFIG_PROPERTIES = "stg" + CONFIG_PROPERTIES;
	private static final String PROD_CONFIG_PROPERTIES = "prod" + CONFIG_PROPERTIES;
	private static final String QA_CONFIG_PROPERTIES = "qa" + CONFIG_PROPERTIES;
	private static final String INT_CONFIG_PROPERTIES = "int" + CONFIG_PROPERTIES;


	private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources/";
	private final Properties properties;
	// private final Properties properties;
	private static ConfigLoader configLoader;

	private ConfigLoader() {

		/* Setting EnvType.BANGALORELAB as default environment */
		/*
		 * This will check for the env value from Jenkins/Maven first. If it does not
		 * get any input from Jenkins/mvn cmd line, then, will take
		 * stg_config.properties file as default
		 */
		String env = System.getProperty(ENV, EnvType.BANGALORELAB.toString());

		switch (EnvType.valueOf(env)) {

		/* Only BANGALORELAB is working, Rest are taken for example */
		case BANGALORELAB: {
			properties = getConfigPropertyFile(STG_CONFIG_PROPERTIES);
			break;
		}
		case BERLINGTONLAB: {
			properties = getConfigPropertyFile(INT_CONFIG_PROPERTIES);
			break;
		}
		case QA: {
			properties = getConfigPropertyFile(QA_CONFIG_PROPERTIES);
			break;
		}
		case DEVELOPMENT: {
			properties = getConfigPropertyFile(PROD_CONFIG_PROPERTIES);
			break;
		}
		default: {
			throw new IllegalStateException("Invalid EnvType: " + env);
		}

		}
	}

	private Properties getConfigPropertyFile(String configFile) {
		return PropertyUtils.propertyLoader(RESOURCES_PATH + configFile);
	}

	private String getPropertyValue(String propertyKey) {
		String prop = properties.getProperty(propertyKey);
		if (prop != null) {
			return prop.trim();
		} else {
			throw new RuntimeException("Property " + propertyKey + " is not specified in the config.properties file");
		}
	}

	public static ConfigLoader getInstance() {
		if (configLoader == null) {
			configLoader = new ConfigLoader();
		}
		return configLoader;
	}

	public String getClientID() {
		return getPropertyValue(CLIENT_ID);
	}

	public String getClientSecret() {
		return getPropertyValue(CLIENT_SECRET);
	}

	public String getGrantType() {
		return getPropertyValue(GRANT_TYPE);
	}

	public String getRefreshToken() {
		return getPropertyValue(PASSWORD);
	}

	public String getUserID() {
		return getPropertyValue(USERNAME);
	}

	public String getBaseUriAPI() {
		return getPropertyValue(BASE_URL_HTTP);
	}

	public String getBaseUriAccounts() {
		return getPropertyValue(BASE_URL_HTTPS);
	}

	public String getOverrideReports() {
		return getPropertyValue(OVERRIDE_REPORTS);
	}

	public String getRequestDetailsInReports() {
		return getPropertyValue(REQUEST_RESPONSE_DETAILS_IN_REPORTS);
	}

	public String getSendEmailToUsers() {
		return getPropertyValue(SEND_EMAIL_TO_USERS);
	}

	public String getRetryFailedTests() {
		System.out.println("==============================================================");
		System.out.println("RETRY_FAILED_TESTS"+RETRY_FAILED_TESTS);
		System.out.println("==============================================================");
		return getPropertyValue(RETRY_FAILED_TESTS);
	}

}
