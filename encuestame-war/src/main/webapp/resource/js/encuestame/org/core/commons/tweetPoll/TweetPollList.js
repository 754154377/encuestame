dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPollList");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.layout.AccordionContainer");
dojo.require("dijit.layout.AccordionPane");
dojo.require("dojox.widget.Dialog");
dojo.require("dojox.form.Rating");
dojo.require("dojo.fx");
dojo.require("dojo.hash");
dojo.require("dojo.dnd.Source");

dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.commons.tweetPoll.TweetPoll");
dojo.require("encuestame.org.core.commons.dashboard.chart.DashboardPie");
dojo.require("encuestame.org.core.commons.tweetPoll.TweetPollListDetail");
dojo.require("encuestame.org.core.commons.support.Wipe");
dojo.require("encuestame.org.core.shared.utils.FoldersActions");
dojo.require("encuestame.org.core.commons.stream.HashTagInfo");
dojo.require("encuestame.org.core.commons.support.ItemsFilterSupport");

/**
 * Tweet Poll Administration Interface.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollList",
        [encuestame.org.main.EnmeMainLayoutWidget],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollList.html"),
        url : encuestame.service.list.listTweetPoll,
        /*
         * store list of items.
         */
        listItems : null,
        /*
         * default filter.
         */
        defaultSearch : "ALL",
        /*
         * store the current search.
         */
        currentSearch : "",
        /*
         * enable folder support.
         */
        folder_support : true,

        /*
         * max of items to show.
         */
        max : 20,

        /*
         * start from 0.
         */
        start : 0,

        /*
         * enable drag support.
         */
        dragSupport : true,

        /*
         *
         */
        _tweetpollListSourceWidget : null,
        
        /*
         * i18n message for this widget.
         */
        i18nMessage : {
          detail_manage_by_account : ENME.getMessage("detail_manage_by_account"),
          detail_manage_today : ENME.getMessage("detail_manage_today"),
          detail_manage_last_week : ENME.getMessage("detail_manage_last_week"),
          detail_manage_favorites : ENME.getMessage("detail_manage_favorites"),
          detail_manage_scheduled : ENME.getMessage("detail_manage_scheduled"),
          detail_manage_all : ENME.getMessage("detail_manage_all"),
          detail_manage_published : ENME.getMessage("detail_manage_published"),
          detail_manage_unpublished : ENME.getMessage("detail_manage_unpublished"),
          detail_manage_only_completed : ENME.getMessage("detail_manage_only_completed")          
        },
        

        /*
         * post create.
         */
        postCreate : function() {
            var hash = dojo.queryToObject(dojo.hash());
            if(this.listItems == null){
                this.loadTweetPolls({typeSearch : (hash.f == null ? this.defaultSearch: hash.f) });
            }
            dojo.subscribe("/encuestame/tweetpoll/list/updateOptions", this, "_checkOptionItem");
            if (hash.f) {
            dojo.query(".optionItem").forEach(function(node, index, arr) {
               if (node.getAttribute("type") == hash.f) {
                   dojo.addClass(node, "optionItemSelected");
                }
              });
            }
            if (this.folder_support && this._folder) {
                var folder = new encuestame.org.core.shared.utils.FoldersActions({folderContext: "tweetpoll"});
                this._folder.appendChild(folder.domNode);
            }
            //enable drag support.
            if (this.dragSupport) {
                this._tweetpollListSourceWidget  = new dojo.dnd.Source(this._items, {
                    accept: [],
                    copyOnly: true,
                    selfCopy : false,
                    selfAccept: false,
                    withHandles : true,
                    autoSync : true,
                    isSource : true,
                    creator: this.dndNodeCreator
                    });
            }
        },

        /*
         * init folder support.
         */
        _initFolderSupport : function() {},

        /*
         * dnd node creator.
         */
        dndNodeCreator : function (item, hint) {
            //console.debug("hint", hint);
            var tr = document.createElement("div");
            tr.innerHTML = "Item Dropped...";
            return {node: tr, data: item, type: "tweetpoll"};
        },

        /*
         *
         */
        _checkOptionItem : function(node) {
            dojo.query(".optionItem").forEach(function(node, index, arr){
                dojo.removeClass(node, "optionItemSelected");
              });
             dojo.addClass(node, "optionItemSelected");
        },

        /*
         * reset the pagination.
         */
        resetPagination : function() {
            this.start = 0;
        },

        /*
         * update the url hash.
         */
        _changeHash : function(id) {
            var hash = dojo.queryToObject(dojo.hash());
            params = {
               f : id
            };
            dojo.hash(dojo.objectToQuery(params));
        },

        /*
         * next search.
         */
        _nextSearch : function(event) {
            dojo.stopEvent(event);
            this.start = this.start + this.max;
            this.loadTweetPolls({typeSearch : this.currentSearch});
        },

        /*
         * search by all.
         */
        _searchByAll : function(event) {
            dojo.stopEvent(event);
            this.currentSearch = "ALL";
            this._changeHash(this.currentSearch);
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "ALL"});
            //console.debug(event);
            dojo.publish("/encuestame/tweetpoll/list/updateOptions", [event.currentTarget]);
        },

        /*
         * search by account.
         */
        _searchByAccount : function(event) {
            dojo.stopEvent(event);
            this.currentSearch = "ALL";
            this._changeHash(this.currentSearch);
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "ALL"});
            dojo.publish("/encuestame/tweetpoll/list/updateOptions", [event.currentTarget]);
        },

        /*
         * search by favourites.
         */
        _searchByFavourites : function(event) {
            dojo.stopEvent(event);
            this.currentSearch = "FAVOURITES";
            this._changeHash(this.currentSearch);
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "FAVOURITES"});
            dojo.publish("/encuestame/tweetpoll/list/updateOptions", [event.currentTarget]);
        },

        /*
         * search by secheduled.
         */
        _searchByScheduled : function(event) {
            dojo.stopEvent(event);
            this.currentSearch = "SCHEDULED";
            this._changeHash(this.currentSearch);
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "SCHEDULED"});
            dojo.publish("/encuestame/tweetpoll/list/updateOptions", [event.currentTarget]);
        },

        /*
         * search by last day.
         */
        _searchByLastDay : function(event) {
            dojo.stopEvent(event);
            this.currentSearch = "LASTDAY";
            this._changeHash(this.currentSearch);
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "LASTDAY"});
            dojo.publish("/encuestame/tweetpoll/list/updateOptions", [event.currentTarget]);
        },

        /*
         * search by last week.
         */
        _searchByLastWeek : function(event) {
            dojo.stopEvent(event);
            this.currentSearch = "LASTWEEK";
            this._changeHash(this.currentSearch);
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "LASTWEEK"});
            dojo.publish("/encuestame/tweetpoll/list/updateOptions", [event.currentTarget]);
        },

        /**
         * Load Tweet Polls.
         */
        loadTweetPolls : function(params) {
            dojo.publish("/encuestame/wipe/close/group", ["tp-options"]);
            dojo.publish("/encuestame/filters/selected/remove");
            var i = false;
            var load = dojo.hitch(this, function(data){
                dojo.empty(this._items);
                var itemArray = [];
                dojo.forEach(
                        data.success.tweetPolls,
                        dojo.hitch(this, function(data, index) {
                            var widget = this.createTweetPollItem(data, i);
                            if(!i) {
                                i = true;
                            }
                            if (this.dropSupport) {
                                itemArray.push(widget.domNode);
                            } else {
                                this._items.appendChild(widget.domNode);
                            }
                }));
                if (this.dropSupport) {
                    this._tweetpollListSourceWidget.insertNodes(false, itemArray);
                }
            });
            dojo.mixin(params,
                {
                max : this.max,
                start : this.start
                }
            );
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(this.url, params, load, error);
        },

        /*
         * create item.
         */
        createTweetPollItem : function(data, i) {
            //dojo.addOnLoad(dojo.hitch(this, function() {
                var widget = new encuestame.org.core.commons.tweetPoll.TweetPollListItem({data : data });
                if (!i) {
                    widget._changeBackGroundSelected();
                }
                return widget;
            //}));
        }
 });

/**
 * Represents a row for each item of the tweetpoll list.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollListItem",
        [encuestame.org.main.EnmeMainLayoutWidget],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollListItem.html"),
        //enable widgets on template.
        widgetsInTemplate: true,
        //data
        data: null,
        //seleted
        selected : false,

        panelWidget : null,
        // wipe parameters.
        _wipe : { height : 255, duration : 200},
        
        /*
         * i18n message for this widget.
         */
        i18nMessage : {
          related_terms : ENME.getMessage("related_terms")       
        },

        //post create
        postCreate : function() {

            dojo.subscribe("/encuestame/tweetpoll/item/unselect", this, "unselect");
            dojo.connect(this._title, "onclick", dojo.hitch(this, this._onClickItem));

            if (!this.data.favourites) {
                dojo.addClass(this._favourite, "emptyFavourite");
                dojo.removeClass(this._favourite, "selectedFavourite");
            } else {
                dojo.addClass(this._favourite, "selectedFavourite");
                dojo.removeClass(this._favourite, "emptyFavourite");
            }

            this.panelWidget = new encuestame.org.core.commons.support.Wipe(this._panel, this._wipe.duration, this._wipe.height);
            if (this.data.hashtags) {
                if (this.data.hashtags.length > 0) {
                    dojo.forEach(this.data.hashtags,
                        dojo.hitch(this,function(item) {
                            if (item.hashTagName !== "") {
                                var hashtag = new encuestame.org.core.commons.stream.HashTagInfo(
                                        {
                                         hashTagName: item.hashTagName,
                                         url: encuestame.utilities.url.hashtag(item.hashTagName)
                                        }
                                        );
                                this._hashtags.appendChild(hashtag.domNode);
                            }
                    }));
                } else {
                    dojo.empty(this._hashtags);
                }
            }
        },

        /*
         * Call Service.
         */
        _callService : function(/* function after response */ load, url) {
            var error = function(error) {
                this.publishMessage(error.message, ENME.CONST.MSG.ERROR);
            };
            var params = {
                    tweetPollId : this.data.id
            };
            encuestame.service.xhrGet(url, params, load, error);
        },

        /*
         * set favourite this item.
         */
        _setFavourite : function(event) {
            dojo.stopEvent(event);
            var load = dojo.hitch(this, function(data) {
                this.data.favourites = !this.data.favourites;
                if (this.data.favourites) {
                    dojo.addClass(this._favourite, "selectedFavourite");
                    dojo.removeClass(this._favourite, "emptyFavourite");
                    this.publishMessage(ENME.getMessage('commons_favourite'), ENME.CONST.MSG.SUCCESS);
                } else {
                    dojo.addClass(this._favourite, "emptyFavourite");
                    dojo.removeClass(this._favourite, "selectedFavourite");
                    this.publishMessage(ENME.getMessage('commons_unfavourite'), ENME.CONST.MSG.SUCCESS);
                }
                
            });
            this._callService(load, encuestame.service.list.favouriteTweetPoll);
        },

        /*
         *
         */
        _setCloseTweetPoll : function(event) {
            dojo.stopEvent(event);
            var load = dojo.hitch(this, function(data) {
                //console.debug(data);
            });
            this._callService(load, encuestame.service.list.changeTweetPollStatus);
        },

        /*
         * change background color on if selected.
         */
        _changeBackGroundSelected : function() {
            if (this.selected) {
               this._setUnselected();
               this.panelWidget.wipeOutOne();
               dojo.empty(this._panel);
               dojo.addClass(this._panel, "tweet-poll-item-panel");
            } else {
               dojo.addClass(this.domNode, "listItemTweetSeleted");
               dojo.removeClass(this._hashtags, "defaultDisplayHide");
               dojo.removeClass(this._panel, "tweet-poll-item-panel");
               this._createDetail(this.data);
               this.panelWidget.wipeInOne();
            }
            this.selected =!this.selected;
        },

        /*
         * un selected.
         */
        _setUnselected : function() {
            dojo.removeClass(this.domNode, "listItemTweetSeleted");
            dojo.addClass(this._hashtags, "defaultDisplayHide");
        },

        /*
         * unselect this item.
         */
        unselect : function(id) {
            if (this.selected && this != id) {
                this._changeBackGroundSelected();
            }
        },

        /*
         * create detail.
         */
        _createDetail : function(data) {
            var detail = new encuestame.org.core.commons.tweetPoll.TweetPollListDetail({data: data});
            this._panel.appendChild(detail.domNode);
        },

        /*
         * on click on the widget dom node.
         */
        _onClickItem : function(event) {
             dojo.stopEvent(event);
             this._changeBackGroundSelected();
             dojo.publish("/encuestame/tweetpoll/item/unselect", [this]);
        }
});
