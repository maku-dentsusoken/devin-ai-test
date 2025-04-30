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

import org.openmrs.api.context.Credentials;

/**
 * Credentials for Google OAuth authentication.
 * 
 * @since 2.8.0
 */
public class GoogleOAuthCredentials implements Credentials {
	
	public static final String SCHEME = "GOOGLE_OAUTH_AUTH_SCHEME";
	
	private String idToken;
	private String email;
	
	public GoogleOAuthCredentials(String idToken, String email) {
		this.idToken = idToken;
		this.email = email;
	}
	
	@Override
	public String getAuthenticationScheme() {
		return SCHEME;
	}
	
	@Override
	public String getClientName() {
		return getEmail();
	}
	
	public String getIdToken() {
		return idToken;
	}
	
	public String getEmail() {
		return email;
	}
}
