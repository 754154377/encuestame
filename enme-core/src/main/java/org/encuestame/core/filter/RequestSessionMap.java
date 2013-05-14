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
package org.encuestame.core.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.encuestame.utils.web.frontEnd.WebMessage;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Request session map.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public final class RequestSessionMap {

    static final String FLASH_MAP_SESSION_ATTRIBUTE = RequestSessionMap.class.getName();
    private static final String MESSAGE_ATTRIBUTE = "message";

    /**
     * Get the Flash Map for the current user session.
     * Creates one if necessary.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getCurrent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        synchronized (session) {
            Map<String, Object> flash = (Map<String, Object>) session.getAttribute(FLASH_MAP_SESSION_ATTRIBUTE);
            if (flash == null) {
                flash = new HashMap<String, Object>();
                session.setAttribute(FLASH_MAP_SESSION_ATTRIBUTE, flash);
            }
            return flash;
        }
    }

    /**
     * Put an attribute in the current flash map.
     * @param name the attribute name
     * @param value the attribute value
     */
    public static void put(String name, Object value) {
        getCurrent(getRequest(RequestContextHolder.currentRequestAttributes())).put(name, value);
    }

    /**
     * Set the 'message' attribute to a info {@link WebMessage} that renders the info text.
     */
    public static void setInfoMessage(String info) {
        put(MESSAGE_ATTRIBUTE, new WebMessage(WebMessage.WebInfoType.INFO, info));
    }

    /**
     * Set the 'message' attribute to a warning {@link WebMessage} that renders the warning text.
     */
    public static void setWarningMessage(String warning) {
        put(MESSAGE_ATTRIBUTE, new WebMessage(WebMessage.WebInfoType.WARNING, warning));
    }

    /**
     * Set the 'message' attribute to a error {@link WebMessage} that renders the error text.
     */
    public static void setErrorMessage(String error) {
        put(MESSAGE_ATTRIBUTE, new WebMessage(WebMessage.WebInfoType.ERROR, error));
    }

    public static void setErrorMessage(String error, final String description) {
        put(MESSAGE_ATTRIBUTE, new WebMessage(WebMessage.WebInfoType.ERROR, error, description));
    }

    /**
     * Set the 'message' attribute to a success {@link WebMessage} that renders the success text.
     */
    public static void setSuccessMessage(String success) {
        put(MESSAGE_ATTRIBUTE, new WebMessage(WebMessage.WebInfoType.SUCCESS, success));
    }

    /**
     *
     * @param requestAttributes
     * @return
     */
    private static HttpServletRequest getRequest(RequestAttributes requestAttributes) {
        return ((ServletRequestAttributes)requestAttributes).getRequest();
    }

    /**
     *
     */
    private RequestSessionMap() {}
}