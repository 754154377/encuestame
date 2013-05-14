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
package org.encuestame.persistence.dao;

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.domain.notifications.Notification;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;

/**
 * Implementation for Notification Domain.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 18, 2010 7:10:54 PM
 * @version $Id:$
 */
public interface INotification extends IBaseDao{

    /**
     * Load Notifications By {@link Account} and Limit. This method add all notifications without User (global)
     * @param secUser {@link Account}
     * @param limit limit
     * @return
     */
    List<Notification> loadNotificationByUserAndLimit(
            final Account user,
            final Integer limit,
            final Integer start,
            final Boolean onlyUnread);


    /**
     * Load notifications between two dates
     * @param user
     * @param limit
     * @param start
     * @param startDate
     * @param endDate
     * @param onlyUnread
     * @return
     */
    List<Notification> loadNotificationByDate(
            final Account user,
            final Integer limit,
            final Integer start,
            final Date startDate,
            final Date endDate,
            final Boolean onlyUnread);

    /**
     * Get Notification.
     * @param notificationId
     * @return
     */
    Notification retrieveNotificationById(final Long notificationId);

    /**
     * Retrieve Notification Status
     * @param accountUser {@link UserAccount}.
     * @return
     */
    Long retrieveTotalNotificationStatus(final Account accountUser);

   /**
    * Retrieve Notification Status
    * @param accountUser
    * @return
    */

   Long retrieveTotalNotReadedNotificationStatus(final Account accountUser);
}
