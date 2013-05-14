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

package org.encuestame.persistence.domain.survey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.encuestame.persistence.domain.AbstractFolder;
import org.encuestame.persistence.interfaces.IFolder;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;


/**
 * Survey Folders.
 * @author Morales Urbina, Diana paolaATencuestame.org
 * @since August 10, 2010
 * @version $Id: $
 */
@Entity
@Table(name = "survey_folder")
@Indexed(index="SurveyFolder")
public class SurveyFolder extends AbstractFolder implements IFolder {

    /****/
    private Long id;

    /**
     * @return the surveyFolderId
     */
    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "survey_folderId", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    /**
     * @param surveyFolderId the surveyFolderId to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}
