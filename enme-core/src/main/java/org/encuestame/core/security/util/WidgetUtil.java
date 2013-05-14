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
package org.encuestame.core.security.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.files.PathUtil;
import org.encuestame.core.util.SocialUtils;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.MD5Utils;
import org.encuestame.utils.PictureUtils;
import org.encuestame.utils.ShortUrlProvider;
import org.springframework.core.io.ClassPathResource;

/**
 * Dojo Widget Utils.
 * @author Picado, Juan juanATencuestame.org
 * @since Jan 23, 2011 8:23:58 AM
 * @version $Id:$
 */
public class WidgetUtil {

    private static final String URL = "http://";

    private static final Integer REQUEST_SERVER_PORT = 80;

    private static Log log = LogFactory.getLog(EnMePlaceHolderConfigurer.class);

    /**
     *
     * @param request
     * @return
     */
    public static String getUserProfileImagePath(final HttpServletRequest request) {
        final StringBuilder domain = new StringBuilder(WidgetUtil.getDomain(request));
        domain.append(PathUtil.profileUserImage);
        return domain.toString();
    }

    /**
     *
     * @param menu
     * @param request
     * @return
     */
    public static String menuSelected(final String menu, final String realPath, final String contextPath) {
            final StringBuffer stringBuffer = new StringBuffer(contextPath);
            stringBuffer.append(menu);
            log.debug("menuSelected realPath " +stringBuffer.toString());
            log.debug("menuSelected contextPath " + contextPath);
            if (stringBuffer.toString() == menu) {
                return "current";
            } else {
                return "";
            }
    }

    /**
     * Create a short Url.
     * @param provider
     * @param url
     * @return
     */
    public static String createShortUrl(final ShortUrlProvider provider, final String url) {
        String urlShort = url;
        if (!EnMePlaceHolderConfigurer.getBooleanProperty("application.offline.mode")) {
            if (provider.equals(ShortUrlProvider.GOOGL)) {
                urlShort = SocialUtils.getGoGl(url,
                        EnMePlaceHolderConfigurer.getProperty("short.google.key"));
            } else if (provider.equals(ShortUrlProvider.NONE)) {
                urlShort = url;
            } else if (provider.equals(ShortUrlProvider.TINYURL)) {
                urlShort = SocialUtils.getTinyUrl(url);
            } else if (provider.equals(ShortUrlProvider.BITLY)) {
                 urlShort = SocialUtils.getBitLy(url,
                         EnMePlaceHolderConfigurer.getProperty("short.bitLy.key"),
                         EnMePlaceHolderConfigurer.getProperty("short.bitLy.login"));
            } else {
                urlShort = null;
            }
        }
        return urlShort;
    }

    /**
     * Get Domain.
     * @param request
     * @return
     */
    public static final String getDomain(final HttpServletRequest request) {
        final StringBuffer domain = new StringBuffer(WidgetUtil.URL);
        domain.append(request.getServerName());
        if (request.getServerPort() != WidgetUtil.REQUEST_SERVER_PORT) {
            domain.append(":");
            domain.append(request.getServerPort());
        }
        // buffer.append("//");
        domain.append(request.getContextPath());
        return domain.toString();
    }

    /**
     * Build correctly period filter url.
     * @param request
     * @param period
     * @return
     */
    public static final String getHomeFilterPeriodParameter(final HttpServletRequest request, final String period){
        StringBuilder url = new StringBuilder();
        url.append(request.getContextPath());
        url.append("/home?");
        if (request.getParameter("view") == null) {
            url.append("period=");
            url.append(period);
        } else {
            url.append("view=");
            url.append(request.getParameter("view"));
            url.append("&period=");
            url.append(period);
        }
        log.debug("getHomeFilterPeriodParameter "+url.toString());
        return url.toString();
    }

    /**
     * Get Gravatar.
     * @param email
     * @param size
     * @return
     * @deprecated moved to {@link PictureUtils}
     */
    @Deprecated
    public final String getGravatar(final String email, Integer size) {
        final String hash = MD5Utils.md5Hex(email);
        StringBuilder gravatarUl = new StringBuilder();
        gravatarUl.append(PictureUtils.GRAVATAR_URL);
        gravatarUl.append(hash);
        gravatarUl.append("?s=");
        gravatarUl.append(size);
        return gravatarUl.toString();
    }

    /**
     * Get Analytics google code.
     * @param path
     * @return
     */
    public static final String getAnalytics(final String path){
        final String analyticCode = EnMePlaceHolderConfigurer.getProperty("google.analytic.code");
        final String scriptFilePath = path;
        final StringBuffer stb = new StringBuffer("");
        BufferedReader reader;
        String analyticBlock;
        try {
            if (analyticCode.isEmpty()) {
                throw new EnMeExpcetion("analytics code is emtpy");
            }
            reader = new BufferedReader(
                     new InputStreamReader(new ClassPathResource(scriptFilePath).getInputStream()));
            String aux;
            while(true) { aux = reader.readLine();
            if (aux == null) break;
            stb.append(aux);
            }
            reader.close();
            analyticBlock = stb.toString();
            analyticBlock = StringUtils.replace(analyticBlock, "$analyticCode", analyticCode);
        } catch (IOException e) {
            analyticBlock = "";
        } catch (EnMeExpcetion e) {
            analyticBlock = "";
        }
        return analyticBlock;
    }


    /**
     *
     * @param path
     * @return
     */
    public static final String getPasswordBlackList(final String path){
        final String scriptFilePath = path;
        final StringBuffer stb = new StringBuffer("[");
        BufferedReader reader;
        String passwordArray;
        try {
            reader = new BufferedReader(
                     new InputStreamReader(new ClassPathResource(scriptFilePath).getInputStream()));
            String aux;
            while(true) {
                aux = reader.readLine();
                if (aux == null) {
                    stb.append("\"\"]");
                    break;
                }
                stb.append("\"");
                stb.append(aux);
                stb.append("\",");
            }
            reader.close();
            passwordArray = stb.toString();
        } catch (IOException e) {
            passwordArray = "";
        }
        return passwordArray;
    }

    /**
     * Get black list ip.
     * @return
     */
    public static final List<String> getBlackListIP(final String path) throws EnMeExpcetion{
        final String blackListPath =  path;
        BufferedReader reader;
        final List<String> blackList = new ArrayList<String>();
        try {
            reader = new BufferedReader(
                     new InputStreamReader(new ClassPathResource(blackListPath).getInputStream()));
            String ipLine;
            while(true) {
                ipLine = reader.readLine();
                log.debug("IP readed ---> "+ ipLine);
            if (ipLine == null) break;
            blackList.add(ipLine);
            }
            reader.close();
        } catch (IOException e) {
            log.debug(e);
        }
        return blackList;
    }
}