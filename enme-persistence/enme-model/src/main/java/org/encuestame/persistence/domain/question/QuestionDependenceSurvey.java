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
package org.encuestame.persistence.domain.question;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.survey.Survey;

/**
 * Questions Dependence Surveys.
 * @author Morales Urbina, Diana paolaATencuestame.org
 * @since August 10, 2010
 * @version $Id: $
 */

@Entity
@Table(name = "question_dependence_survey")
public class QuestionDependenceSurvey {

    /****/
    private Long questionDependenceId;

    /****/
    private Survey survey;

    /**
     * @return the questionDependenceId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_dependence_survey", unique = true, nullable = false)
    public Long getQuestionDependenceId() {
        return questionDependenceId;
    }

    /**
     * @param questionDependenceId the questionDependenceId to set
     */
    public void setQuestionDependenceId(Long questionDependenceId) {
        this.questionDependenceId = questionDependenceId;
    }

    /**
     * @return the survey
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sid", nullable = false)
    public Survey getSurvey() {
        return survey;
    }

    /**
     * @param survey the survey to set
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }


}
