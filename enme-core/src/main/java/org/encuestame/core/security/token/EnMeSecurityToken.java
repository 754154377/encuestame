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
package org.encuestame.core.security.token;

import org.encuestame.core.security.SecurityUtils;
import org.encuestame.core.util.ConvertDomainsToSecurityContext;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * EnMe Security Token.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 17, 2011
 */
public class EnMeSecurityToken extends AbstractAuthenticationToken {

    /**
     * Serial.
     */
    private static final long serialVersionUID = 7648924296863041359L;

    /**
     *
     */
    private final Object principal;

    /**
     * {@link UserAccount}.
     */
    private UserAccount userAccount;

    /**
     *
     * @param principal
     * @param arg0
     */
    public EnMeSecurityToken(
            final UserAccount userAccount) {
        super(ConvertDomainsToSecurityContext
                .convertEnMePermission(userAccount.getSecUserPermissions()));
        this.principal = SecurityUtils.convertUserAccountToUserDetails(userAccount, true);
        setAuthenticated(false);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.Authentication#getCredentials()
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.Authentication#getPrincipal()
     */
    @Override
    public Object getPrincipal() {
        return principal;
    }

    /**
     * @return the userAccount
     */
    public UserAccount getUserAccount() {
        return userAccount;
    }

    /**
     * @param userAccount the userAccount to set
     */
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}