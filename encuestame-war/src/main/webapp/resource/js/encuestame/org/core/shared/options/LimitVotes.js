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
dojo.provide("encuestame.org.core.shared.options.LimitVotes");

dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.options.ConstrainNumberPicker');

/**
 * Represents a option to limit votes.
 */
dojo.declare(
    "encuestame.org.core.shared.options.LimitVotes",
    [encuestame.org.core.shared.options.ConstrainNumberPicker],{

        /*
         * to enable publish support, replace null value for publish valid url.
         * eg: /encuestame/tweetpoll/autosave
         */
        publish_url : null,

        /*
        *
        */
        constraints : "{min:0,max:4000}",

        /*
         *
         */
        label : ENME.getMessage('widget_limit_votes')
});