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
package org.encuestame.utils.security;

import java.io.Serializable;

/**
 * Sing Up Bean.
 * @author Picado, Juan juanATencuestame.org
 * @since 08/05/2009 14:40:44
 */
public class SignUpBean implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = 6150533795415299837L;

    private String fullName;

    private String username;

    private String password;

    private String email;

    private String captcha;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the captcha
     */
    public String getCaptcha() {
        return captcha;
    }

    /**
     * @param captcha
     *            the captcha to set
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SignUpBean [fullName=").append(fullName)
                .append(", username=").append(username)
                .append(", email=").append(email)
                .append(", captcha=").append(captcha).append("]");
        return builder.toString();
    }
}
