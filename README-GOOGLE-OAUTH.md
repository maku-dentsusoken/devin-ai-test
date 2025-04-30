# Google OAuth Authentication for OpenMRS

This document describes how to use the Google OAuth authentication provider for OpenMRS.

## Overview

The Google OAuth authentication provider allows users to log in to OpenMRS using their Google accounts. This implementation integrates with the existing OpenMRS authentication system by implementing the `AuthenticationScheme` interface.

## Implementation Details

The implementation consists of the following components:

1. **GoogleOAuthCredentials**: Implements the `Credentials` interface for Google OAuth authentication.
2. **GoogleOAuthAuthenticationScheme**: Implements the authentication scheme for Google OAuth.
3. **GoogleOAuthService**: Service for Google OAuth operations with environment variable configuration.
4. **GoogleOAuthController**: Controller to handle Google OAuth authentication flow.
5. **Spring Configuration**: Configuration to register the Google OAuth authentication scheme.

## Configuration

### Environment Variables

The Google OAuth authentication provider uses the following environment variables:

- `GOOGLE_OAUTH_CLIENT_ID`: The Google OAuth client ID.
- `GOOGLE_OAUTH_CLIENT_SECRET`: The Google OAuth client secret.

If these environment variables are not set, dummy values will be used.

### How to Set Environment Variables

#### Linux/Mac

```bash
export GOOGLE_OAUTH_CLIENT_ID=your-client-id
export GOOGLE_OAUTH_CLIENT_SECRET=your-client-secret
```

#### Windows

```cmd
set GOOGLE_OAUTH_CLIENT_ID=your-client-id
set GOOGLE_OAUTH_CLIENT_SECRET=your-client-secret
```

### Google API Console Setup

To use Google OAuth authentication, you need to create a project in the Google API Console and configure the OAuth consent screen and credentials:

1. Go to the [Google API Console](https://console.developers.google.com/).
2. Create a new project or select an existing one.
3. Configure the OAuth consent screen.
4. Create OAuth 2.0 credentials (Web application type).
5. Add authorized redirect URIs (e.g., `http://localhost:8080/openmrs/googleCallback`).
6. Copy the client ID and client secret to use in the environment variables.

## Usage

After configuring the environment variables and deploying the application, users can log in using Google OAuth by:

1. Navigating to the login page.
2. Clicking the "Login with Google" button.
3. Authenticating with Google.
4. Being redirected back to OpenMRS.

## Integration with OpenMRS

The Google OAuth authentication provider is integrated with OpenMRS through the following mechanisms:

1. The `GoogleOAuthAuthenticationScheme` is registered as a Spring bean in `applicationContext-googleOAuth.xml`.
2. The `GoogleOAuthController` handles the OAuth flow and redirects.
3. The `GoogleOAuthCredentials` and `GoogleOAuthService` handle the authentication logic.

## Security Considerations

- The Google OAuth authentication provider uses HTTPS for all communication with Google.
- User credentials are never stored or logged.
- The implementation follows OAuth 2.0 best practices.
- Environment variables are used to configure sensitive information.

## Troubleshooting

If you encounter issues with Google OAuth authentication:

1. Check that the environment variables are set correctly.
2. Verify that the redirect URIs in the Google API Console match your application's URLs.
3. Check the server logs for error messages.
4. Ensure that the user's Google account has access to the application.
