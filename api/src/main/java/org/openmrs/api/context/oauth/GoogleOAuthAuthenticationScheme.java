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

import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Authenticated;
import org.openmrs.api.context.BasicAuthenticated;
import org.openmrs.api.context.ContextAuthenticationException;
import org.openmrs.api.context.Credentials;
import org.openmrs.api.context.DaoAuthenticationScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Authentication scheme for Google OAuth.
 * 
 * @since 2.8.0
 */
@Component
public class GoogleOAuthAuthenticationScheme extends DaoAuthenticationScheme {
	
	private static final Logger log = LoggerFactory.getLogger(GoogleOAuthAuthenticationScheme.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GoogleOAuthService googleOAuthService;
	
	@Override
	public Authenticated authenticate(Credentials credentials) throws ContextAuthenticationException {
		log.debug("Authenticating client with Google OAuth: {}", credentials.getClientName());
		
		if (!(credentials instanceof GoogleOAuthCredentials)) {
			throw new ContextAuthenticationException(
			        "The provided credentials could not be used to authenticate with the Google OAuth authentication scheme.");
		}
		
		GoogleOAuthCredentials googleCredentials = (GoogleOAuthCredentials) credentials;
		
		try {
			boolean isValidToken = googleOAuthService.verifyIdToken(googleCredentials.getIdToken());
			
			if (!isValidToken) {
				throw new ContextAuthenticationException("Invalid Google OAuth token");
			}
			
			String email = googleCredentials.getEmail();
			User user = userService.getUserByUsername(email);
			
			if (user == null) {
				throw new ContextAuthenticationException("No OpenMRS user account exists for Google user: " + email);
			}
			
			return new BasicAuthenticated(user, GoogleOAuthCredentials.SCHEME);
		}
		catch (Exception e) {
			log.error("Error during Google OAuth authentication", e);
			throw new ContextAuthenticationException("Google OAuth authentication failed", e);
		}
	}
}
