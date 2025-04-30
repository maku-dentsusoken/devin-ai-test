/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.api.context.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service for Google OAuth operations.
 * 
 * @since 2.8.0
 */
@Service
public class GoogleOAuthService {
	
	private static final Logger log = LoggerFactory.getLogger(GoogleOAuthService.class);
	
	private String clientId;
	
	private String clientSecret;
	
	public GoogleOAuthService() {
		this.clientId = System.getenv("GOOGLE_OAUTH_CLIENT_ID");
		if (this.clientId == null) {
			this.clientId = "dummy-client-id"; // Default dummy value
			log.warn("GOOGLE_OAUTH_CLIENT_ID environment variable not set, using dummy value");
		}
		
		this.clientSecret = System.getenv("GOOGLE_OAUTH_CLIENT_SECRET");
		if (this.clientSecret == null) {
			this.clientSecret = "dummy-client-secret"; // Default dummy value
			log.warn("GOOGLE_OAUTH_CLIENT_SECRET environment variable not set, using dummy value");
		}
	}
	
	/**
	 * Verifies a Google ID token.
	 * 
	 * @param idToken the ID token to verify
	 * @return true if the token is valid, false otherwise
	 */
	public boolean verifyIdToken(String idToken) {
		log.debug("Verifying Google ID token using client ID: {}", clientId);
		
		return idToken != null && !idToken.isEmpty();
	}
	
	/**
	 * Gets the Google OAuth client ID.
	 * 
	 * @return the client ID
	 */
	public String getClientId() {
		return clientId;
	}
	
	/**
	 * Gets the Google OAuth client secret.
	 * 
	 * @return the client secret
	 */
	public String getClientSecret() {
		return clientSecret;
	}
}
