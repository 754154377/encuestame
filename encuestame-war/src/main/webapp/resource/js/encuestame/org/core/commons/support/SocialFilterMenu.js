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
dojo.provide("encuestame.org.core.commons.support.SocialFilterMenu");

dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.MultiSelect");
dojo.require("dijit.form.Slider");
dojo.require("dijit.form.HorizontalSlider");
dojo.require("dijit.form.HorizontalRule");
dojo.require("dijit.form.HorizontalRuleLabels");
dojo.require("encuestame.org.core.commons.social.SocialAccountPicker");
dojo.require("encuestame.org.core.commons.support.AbstractFilterSupport");

dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

/**
 * Social Filter Menu.
 * @author Picado, Juan juanATencuestame.org
 * @since 14/01/12
 */
dojo.declare("encuestame.org.core.commons.support.SocialFilterMenu",
        [encuestame.org.main.EnmeMainLayoutWidget,
         encuestame.org.core.shared.utils.SocialAccountsSupport,
         encuestame.org.core.commons.support.AbstractFilterSupport], {

    /*
     *
     */
     templatePath: dojo.moduleUrl("encuestame.org.core.commons.support", "templates/social-filters.html"),
     
     /*
      * i18n message for this widget.
      */
     i18nMessage : {
    	 social_picker_filter_selected : ENME.getMessage("social_picker_filter_selected"),
    	 commons_filter : ENME.getMessage("commons_filter")
     },     

     /*
      *
      */
     postCreate : function() {
         dojo.subscribe("/encuestame/social/picker/counter/reload", this, "_reloadCounter");
         this._loadSocialConfirmedAccounts();
         this._button.onClick = dojo.hitch(this, function() {

         });
     },

     /*
      *
      */
     cleanSocialAccounts : function(){
         dojo.empty(this._items);
     },


     /*
      * reload counter
      */
     _reloadCounter : function() {
         var counter = this._countSelected();
         this._counter.innerHTML =  counter + " " + this.i18nMessage.social_picker_filter_selected;
     },

     /*
      *
      */
     createPickSocialAccount : function(data){
         var widget = new encuestame.org.core.commons.support.SocialFilterMenuItem({data : data});
         this._items.appendChild(widget.domNode);
         this.arrayWidgetAccounts.push(widget);
     }
});

/**
 * Social Filter Menu Item.
 */
dojo.declare("encuestame.org.core.commons.support.SocialFilterMenuItem",
        [encuestame.org.main.EnmeMainLayoutWidget,
         encuestame.org.core.shared.utils.SocialAccountsSupport], {
	
	/*
	 * item data.
	 */
    data : null,

    /*
     * selected flag (false by default)
     */
    selected : false,

    /*
     * template.
     */
     templatePath: dojo.moduleUrl("encuestame.org.core.commons.support", "templates/social-item-filters.html"),
     
     /*
      * Is triggered before render
      */
    postMixInProperties: function() {
    	// if the social link is not valid, reset a fake social image.
    	this.data.picture_url = ENME.fakeImage("24", this.data.picture_url || "");
    },     

     /*
      *
      */
     postCreate : function() {
         dojo.connect(this.domNode, "onclick", this, dojo.hitch(this, function() {
             this.selected = !this.selected;
             if (this.selected) {
                 this.markAsSelected();
             } else {
                 this.unSelected();
             }
             dojo.publish("/encuestame/social/picker/counter/reload");
         }));
     },
     
     /*
      * Set as selected.
      */
     markAsSelected : function() {
         dojo.addClass(this.domNode, "selected");
         this.selected = true;
     },

     /*
      * Set at un selected.
      */
     unSelected : function() {
         dojo.removeClass(this.domNode, "selected");
         this.selected = false;
     },

});