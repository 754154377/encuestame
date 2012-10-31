/*
 ************************************************************************************
 * Copyright (C) 2001-2012 encuestame: system online surveys Copyright (C) 2012
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

import org.encuestame.persistence.domain.AbstractResult;

/**
 * Survey temporal result.
 * @author Morales Diana Paola, paolaATencuestame.org
 * @since August 07, 2012
 */
@Entity 
@Table(name = "survey_temporal_result") 
public class SurveyTemporalResult extends AbstractResult{
	
	/** **/
	private Long idTempResult;

	/** **/
	private String hash; 
    
	/**
	 * @return the tempResultId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdTempResult", unique = true, nullable = false)
	public Long getIdTempResult() {
		return idTempResult;
	}

	/**
	 * @param tempResultId the tempResultId to set
	 */
	public void setIdTempResult(Long idTempResult) {
		this.idTempResult = idTempResult;
	}

	/**
	 * @return the hash
	 */
	@Column(name = "hash", unique = true, nullable = false)
	public String getHash() {
		return hash;
	}

	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}
	
}
