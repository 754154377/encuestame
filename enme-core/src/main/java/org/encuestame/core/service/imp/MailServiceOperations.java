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
package org.encuestame.core.service.imp;

import org.encuestame.core.service.ServiceOperations;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.mail.InvitationBean;
import org.encuestame.utils.mail.NotificationBean;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.mail.MailSendException;

/**
 * Interface to implement a mail service.
 * @author Picado, Juan juanATencuestame.org
 * @since May 05, 2009
 */
public interface MailServiceOperations extends ServiceOperations {

    /**
     * Send a email.
     * @param to email to send
     * @param subject subject
     * @param text text of body
     *  @throws MailSendException exception
     */
    void send(final String to, final String subject, final String text)
    throws Exception;

    /**
     * Send a welcome notification after validate the account
     * @param user
     * @param message
     */
    void welcomeNotificationAccount(final UserAccount user) throws Exception;

    /**
     * Send Password Confirmation Email.
     * @param user
     */
    void sendPasswordConfirmationEmail(final SignUpBean user) throws Exception;

    /**
     * Send Renew Password Email.
     * @param unitUserBean {@link UserAccountBean}.
     */
    void sendRenewPasswordEmail(final UserAccountBean unitUserBean) throws Exception;

    /**
     *
     * @param invitation
     */
    void sendEmailInvitation(final InvitationBean invitation) throws Exception;

    /**
     *
     * @param notification
     */
    void sendEmailNotification(final NotificationBean notification) throws Exception;

    /**
     * Send start up email notification.
     * @param startupMessage message
     */
    void sendStartUpNotification(
            final String startupMessage);

    /**
     * Send invitation.
     * @param to email to send
     * @param code code of password
     * @throws MailSendException mail exception.
     */
    void sendInvitation(
            final String to,
            final String code) throws Exception;


    /**
     * Sent email to confirm user account by email.
     * @param user
     */
    void sendConfirmYourAccountEmail(final SignUpBean user, final String inviteCode) throws Exception;

    /**
     * Delete notification.
     * @param to mail to send
     * @param body body of message
     * @throws MailSendException exception
     */
    void sendDeleteNotification(
            final String to,
            final String body)throws MailSendException;

    /**
     * Send notification status account.
     * @param user
     * @param message
     */
    void sendNotificationStatusAccount(final SignUpBean user, final String message) throws Exception;

    /**
     * Send Email Invitation.
     * @param invitation {@link InvitationBean}
     */
    void sendEmailJoinInvitation(final String email, final String username) throws Exception;
}
