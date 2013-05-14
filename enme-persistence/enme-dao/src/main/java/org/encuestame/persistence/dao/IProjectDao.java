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

import java.util.List;

import org.encuestame.persistence.domain.Attachment;
import org.encuestame.persistence.domain.Project;
import org.encuestame.persistence.domain.security.Account;
import org.hibernate.HibernateException;

 /**
  * Interface to implement Project  Dao.
  * @author Picado, Juan juanATencuestame.org
  * @since  26/04/2009
  * @version $Id$
  */
public interface IProjectDao extends IBaseDao {

    /**
     * Retrieve project by id.
     * @param projectId project id
     * @return {@link Project}
     * @throws HibernateException hibernate expcetion
     */
    Project getProjectbyId(Long projectId);


    /**
     * Find all projects.
     * @return List of Project
     * @throws HibernateException hibernate expcetion
     */
    List<Project> findAll();

    /**
     * Find Projects by {@link Account} id.
     * @param userId user id.
     * @return list of projects.
     * @throws HibernateException exception
     */
    List<Project> findProjectsByUserID(final Long userId);

    /**
     * Get Attachment Info by Attachment Id.
     * @param attachmentId
     * @return
     */
    Attachment getAttachmentbyId(final Long attachmentId);

    /**
     * Get all Attachments by Project.
     * @param projectId
     * @return
     */
    List<Attachment> getAttachmentsListbyProject(final Long projectId);

    /**
     * Get Attachment filename
     * @param filename
     * @return
     */
    Attachment getAttachmentbyName(final String filename);
}
