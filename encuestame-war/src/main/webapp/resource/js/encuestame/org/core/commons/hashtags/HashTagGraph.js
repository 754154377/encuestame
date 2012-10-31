dojo.provide("encuestame.org.core.commons.hashtags.HashTagGraph");

dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.commons.chart.RaphaelSupport");
dojo.require('encuestame.org.core.commons.stream.HashTagInfo');
dojo.require("dojo.number");

dojo.declare(
    "encuestame.org.core.commons.hashtags.HashTagGraph",
    [encuestame.org.main.EnmeMainLayoutWidget, encuestame.org.core.commons.chart.RaphaelSupport],{
    
    /**
     * Template path.
     */
    templatePath: dojo.moduleUrl("encuestame.org.core.commons.hashtags", "template/hashTagGraph.html"),
    
    /**
     * Hashtag name.
     */
    hashtagName : "",
    
    /**
     * Default filter.
     */
    default_filter : "HASHTAG",
    
    /**
     * Default filter.
     */
    default_range : ENME.CONST.YEAR,
    
    /**
     * Define the channel to refresh the graph.
     */
    channel : "",
    
    /**
     * Create all buttons.
     * @param period {String} Define the period to filter the data.
     */
    _createButtons : function(period) {
    	 var params = {
    	     tagName : this.hashtagName,
    	     filter  : ENME.CONST.HASHTAGRATED 
    	 };
    	 if ( period) {
    		 params.period = period;
    	 }
    	 var load = dojo.hitch(this, function(data) {
    		 if (ENME.CONST.SUCCESS in data) {
    			 var hashTagButtonStats = data.success.hashTagButtonStats;
    			 usage_by_item = hashTagButtonStats['usage_by_item'],
    			 total_usage_by_social_network = hashTagButtonStats['total_usage_by_social_network'],
    			 total_hits = hashTagButtonStats['total_hits'],
    			 usage_by_votes = hashTagButtonStats['usage_by_votes'];
    			 if (this._stat) { 
	    			 dojo.empty(this._stat);
	    			 this._stat.appendChild(this._createAButton({title : usage_by_item.label, value : usage_by_item.value , sub_label : usage_by_item.sub_label, filter : usage_by_item.filter }, true, period));
	    			 this._stat.appendChild(this._createAButton({title : total_usage_by_social_network.label, value : total_usage_by_social_network.value , sub_label : total_usage_by_social_network.sub_label,  filter : total_usage_by_social_network.filter}, false, period));
	    			 this._stat.appendChild(this._createAButton({title : total_hits.label, value : total_hits.value , sub_label : total_hits.sub_label, filter : total_hits.filter}, false, period));
	    			 this._stat.appendChild(this._createAButton({title : usage_by_votes.label, value : usage_by_votes.value , sub_label : usage_by_votes.sub_label, filter : usage_by_votes.filter}, false, period));
    			 }
    		 }
    	 });  	 
    	 this.callGET(params, encuestame.service.list.rate.buttons, load, null, null);
    },
    
    /**
     * Create a stats button.
     * @param {Object}  button_data
     * @param {Boolean} selectedButton
     */
    _createAButton : function (button_data, selectedButton, period) {
    	var handler_params = {
		        data : {
		            "title" : button_data.title,
		            "value" : button_data.value,
		            "label" : button_data.sub_label,
		            "filter" : button_data.filter
		        },     		        
		};
    	//if period exist, override the default period.
    	if (period) {
    		handler_params.period = period;
    	}
    	var params = {
    	        selectedButton : selectedButton || false,
    		    _handler : new encuestame.org.core.commons.hashtags.HashTagGraphStatsUsageHandler(handler_params)
    	};
		var button = new encuestame.org.core.commons.hashtags.HashTagGraphStatsButton(params);
		return button.domNode;
    },

    /**
     * Create a new chart
     */
    _createNewChart : function(filter, range) {
    	this._callRateService(filter, range);
    },
    
    /**
     * Reload the chart.
     */
    _reload : function () {
    	
    },
    
    /**
     * Create a line graph.
     * @param valuesx
     * @param valuesy
     */
    _createGraph : function(valuesx, valuesy) {
    	dojo.empty(this._graph);
    	var graph = this._graph.id;
    	if (graph) {
	    	var option = {
	    			id : graph, 
	    			valuesx : valuesx,
	    			valuesy : valuesy,
	    			width : 630,
	    			radius : 1,
	    			height : 280,
	    	};
	    	//create a line graph
	    	ENME.graph("line", option);
    	}
    },
    
    /**
     * Call a hashtag rate service.
     * @param filter
     */
    _callRateService : function (filter, range) {
    	var params = {
       	     tagName : this.hashtagName,
       	     period : range || this.default_range,
       	     filter  : filter || this.default_filter,  
       	 };
       	 var load = dojo.hitch(this, function(data) {
       		 /*
       		  * Example of range service.
       		  * {
				    "error": { },
				    "success": {
				        "statsByRange": [
				            {
				                "label": "3",
				                "value": 3,
				                "sub_label": "Marzo"
				            },
				            {
				                "label": "2",
				                "value": 2,
				                "sub_label": "Febrero"
				            }
				        ]
				    }
				
				}
       		  */
       		 var parent = this;
       		 var buildGrahpArray = function (data, totalItems) {
      			    var array_stats = [[],[]];
    				for (var i = 1; i <= totalItems ; i++) {
						array_stats[0].push(i);
						array_stats[1].push(0);
       				}
					//ENME.log(array_stats);
	       			dojo.forEach(data,
	                        dojo.hitch(this,function(item) {
                        	/*
                        	 * Object { label="8", value=14, sub_label="Agosto"}
                        	 */
	                      array_stats[1][item.label] = item.value;		                        	
	               }));
	       		   parent._createGraph(array_stats[0], array_stats[1]);
       		 };
       		 
       		 if ("success" in data) { //TODOOOOO: EL PROBLEMA ESTA AKI NECESITO CREAR MAS DATOS PARA PERIODOS
       			  //ENME.log(data.success);
       			  var stats = data.success.statsByRange;
       			  if (params.period === ENME.CONST.YEAR) {
       				  buildGrahpArray(stats, 12);
       			  } else if (params.period === ENME.CONST.ALL) {
       				  //TODO: pending
       			  } else if (params.period === ENME.CONST.DAY) {
       				  buildGrahpArray(stats, 24);
       			  } else if (params.period === ENME.CONST.WEEK) {
       				  buildGrahpArray(stats, 7);
       			  } else if (params.period === ENME.CONST.MONTH) {
       				buildGrahpArray(stats, 31);
       			  }
       		 }
       	 });  	 
       	 this.callGET(params, encuestame.service.list.range.hashtag, load, null, null);
    },

    /**
     * Start the post create cycle.
     */
    postCreate : function() {
    	if (this.hashtagName) {
	        dojo.subscribe("/encuestame/hashtag/chart/new", this, this._createNewChart);
	        dojo.subscribe("/encuestame/hashtag/chart/reload", this, this._reload);	        
	        //create the button
	        this._createButtons(this.default_range);
	        dojo.addOnLoad(dojo.hitch(this, function() {
		        if (typeof(this.default_filter) === 'string') {
		        	this._callRateService(this.default_filter, this.default_range);
		        }
	        }));        	        
	        dojo.subscribe(this.channel, this, this._refreshPublish);
    	}
    },
    
    /**
     * 
     * @param period
     */
    _refreshPublish : function (period) {
    	//ENME.log(period);
    	this._callRateService(this.default_filter, period);
    	this._createButtons(period);
    }

});

/**
 * Widget to display a hashtag stats button.
 */
dojo.declare(
    "encuestame.org.core.commons.hashtags.HashTagGraphStatsButton",
    [encuestame.org.main.EnmeMainLayoutWidget],{
    
    /**
     * Template path.
     */
    templatePath: dojo.moduleUrl("encuestame.org.core.commons.hashtags", "template/hashTagGraphStatsButton.html"),

    /**
     * Button handler.
     */
    _handler : null,

    /**
     * Define if the button is selected by default.
     */
    selectedButton : false,


    /**
     * Post create cycle.
     */
    postCreate : function(){
        if (this._handler != null) {
            dojo.subscribe("/encuestame/hashtag/buttons", this, this._switchButton);
            dojo.connect(this._button, "onclick", dojo.hitch(this, function(event) {
                this._handler.onClick();
            }));
            this._handler.init(this);
            if (this.selectedButton) {
                dojo.addClass(this.domNode, "selected");
            }
            this._button.appendChild(this._handler.domNode);
        }
    },

    /**
     * review the state of the button.
     */
    _switchButton : function(ref) {
        if ( this.domNode == ref.domNode) {
            dojo.addClass(ref.domNode, "selected");
            ref.selectedButton = true;
        } else {
            dojo.removeClass(this.domNode, "selected");
            this.selectedButton = false;
        }
    }
});

/**
 * Button handler to customize the button behaviour.
 */
dojo.declare(
        "encuestame.org.core.commons.hashtags.HashTagGraphStatsUsageHandler",
        [encuestame.org.main.EnmeMainLayoutWidget],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.hashtags", "template/hashTagGraphStatsUsageHandler.html"),

        /**
         * 
         */
        data :{},
        
        /**
         * Default period if not set on created process.
         */
        period : ENME.CONST.YEAR,

        _buttonRef : null,

        /**
         * Initialize the button handler.
         * @param ref {Object} button reference. 
         */
        init : function(ref) {
            this._buttonRef = ref;
            this._dt1.innerHTML = this.data.title;
            this._dt2.innerHTML = dojo.number.format(this.data.value, {places: 0});
            this._dt3.innerHTML = this.data.label;
        },

        /**
         *
         */
        onClick : function(event) {
        	//zthis.stopEvent(event);
        	//unselect the others buttons
            dojo.publish("/encuestame/hashtag/buttons", [this._buttonRef]);
            //display a new chart on hashtag wrapper graphs.
            dojo.publish("/encuestame/hashtag/chart/new", [this.data.filter, this.period]);
        }

});