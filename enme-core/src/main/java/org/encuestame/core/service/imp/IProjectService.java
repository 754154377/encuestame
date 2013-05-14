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

import java.util.Collection;
import java.util.List;

import org.encuestame.persistence.domain.Project;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.utils.web.UserAccountBean;

/**
 * Project Service Interface.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since April 13, 2009
 * @version $Id: $
 */
public interface IProjectService {

    /**
     * Load List of Project.
     * @param userId user id.
     * @return {@link Collection} of {@link UnitProjectBean}
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion exception
     */
    List<UnitProjectBean> loadListProjects(final String username) throws EnMeNoResultsFoundException;

    /**
     * Load project info.
     * @param project {@link Project}
     * @return {@link UnitProjectBean}
     * @throws EnMeExpcetion excepcion
     */
    UnitProjectBean loadProjectInfo(UnitProjectBean project) throws EnMeExpcetion;

    /**
     * create project.
     *
     * @param projectBean {@link UnitProjectBean}
     * @return {@link UnitProjectBean}
     * @throws EnMeExpcetion exception
     */
    UnitProjectBean createProject(UnitProjectBean projectBean, final String username) throws EnMeExpcetion;

    /**
     * Update Project Bean.
     * @param projectBean
     * @param username
     * @throws EnMeExpcetion
     */
    void updateProject(final UnitProjectBean projectBean, final String username) throws EnMeExpcetion;

    /**
     * Load list of users.
     * @return list of users with groups and permission
     * @throws Exception
     * @throws EnMeExpcetion excepcion
     */
    List<UserAccountBean> loadListUsers(final String currentUsername, final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException;

}
