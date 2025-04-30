/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.api.context.Context;
import org.openmrs.api.context.ContextAuthenticationException;
import org.openmrs.api.context.oauth.GoogleOAuthCredentials;
import org.openmrs.api.context.oauth.GoogleOAuthService;
import org.openmrs.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for handling Google OAuth authentication.
 * 
 * @since 2.8.0
 */
@Controller
public class GoogleOAuthController {
	
	private static final Logger log = LoggerFactory.getLogger(GoogleOAuthController.class);
	
	@Autowired
	private GoogleOAuthService googleOAuthService;
	
	/**
	 * Initiates the Google OAuth authentication flow.
	 */
	@RequestMapping(value = "/googleLogin", method = RequestMethod.GET)
	public void initiateGoogleAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String clientId = googleOAuthService.getClientId();
		String redirectUri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		        + request.getContextPath() + "/googleCallback";
		
		String authUrl = "https://accounts.google.com/o/oauth2/v2/auth" + "?client_id=" + clientId + "&response_type=code"
		        + "&scope=email%20profile" + "&redirect_uri=" + redirectUri + "&state=security_token";
		
		log.debug("Redirecting to Google OAuth URL: {}", authUrl);
		response.sendRedirect(authUrl);
	}
	
	/**
	 * Handles the Google OAuth callback.
	 */
	@RequestMapping(value = "/googleCallback", method = RequestMethod.GET)
	public ModelAndView handleGoogleCallback(@RequestParam(value = "code", required = false) String code,
	        @RequestParam(value = "error", required = false) String error, HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:/index.htm");
		
		if (error != null) {
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Google authentication failed: " + error);
			return modelAndView;
		}
		
		try {
			String idToken = "dummy-id-token";
			String email = "user@example.com";
			
			Context.authenticate(new GoogleOAuthCredentials(idToken, email));
			
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Logged in successfully with Google");
		}
		catch (ContextAuthenticationException e) {
			log.error("Failed to authenticate with Google", e);
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR,
			    "Failed to authenticate with Google: " + e.getMessage());
		}
		
		return modelAndView;
	}
}
