/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.oauth2.support;

import org.encuestame.utils.oauth.AccessGrant;

/**
 * A service interface for the OAuth2 flow.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 22, 2011
 */
public interface OAuth2RestOperations {

    /**
     * Construct the URL to redirect the user to for connection authorization.
     * @param redirectUri the authorization callback url; this value must match the redirectUri registered with the provider
     */
    String buildAuthorizeUrl(String redirectUri, String scope);

    /**
     * Exchange the authorization grant for an access grant.
     * @param authorizationGrant the authorization grant returned by the provider upon user authorization
     * @param redirectUri the authorization callback url; this value must match the redirectUri registered with the provider
     */
    AccessGrant exchangeForAccess(String authorizationGrant, String redirectUri);

    /**
     * Refresh the token authorization for access grant.
     * @param refreshToken
     * @return
     */
    AccessGrant refreshToken(final String refreshToken);

}
