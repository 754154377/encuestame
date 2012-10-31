/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
dojo.provide("encuestame.org.core.commons.questions.patterns.SingleOptionResponse");

dojo.require("encuestame.org.core.commons.questions.patterns.AbstractPattern");
dojo.require("encuestame.org.core.commons.questions.patterns.AbstractSoundResponse");
dojo.require("encuestame.org.core.commons.questions.patterns.AbstractVideoResponse");
dojo.require("encuestame.org.core.commons.questions.patterns.AbstractImageResponse");
dojo.require("encuestame.org.core.commons.questions.patterns.AbstractAddNewAnswer");
dojo.require("encuestame.org.core.commons.questions.patterns.AbstractMultipleSelection");

/**
 *
 */
dojo.declare("encuestame.org.core.commons.questions.patterns.SingleOptionResponse",
        [encuestame.org.core.commons.questions.patterns.AbstractPattern,
         encuestame.org.core.commons.questions.patterns.AbstractSoundResponse,
         encuestame.org.core.commons.questions.patterns.AbstractVideoResponse,
         encuestame.org.core.commons.questions.patterns.AbstractImageResponse,
         encuestame.org.core.commons.questions.patterns.AbstractAddNewAnswer,
         encuestame.org.core.commons.questions.patterns.AbstractMultipleSelection],{

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.questions.patterns", "templates/singleOptionResponse.html"),

        /**
         *  Name of the option node.
         */
        name : "",
        
        /**
         * 
         */
        vote : "",

        postCreate : function() {
        	//initialize the parents widgets.
            this.inherited(arguments);
        }

});