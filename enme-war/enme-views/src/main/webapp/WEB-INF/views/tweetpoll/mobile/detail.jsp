<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="mobile-detail">
    <section class="mobile-question-detail category_color">
        ${tweetpoll.questionBean.questionName}
    </section>
    <article>
         <div id="chart" data-dojo-type="me/web/widget/tweetpoll/detail/TweetPollChartDetail"
                     tweetPollid="${tweetpoll.id}"
                     completed="${tweetpoll.completed}"
                     username="${tweetpoll.ownerUsername}"></div>
    </article>
    <article class="web-tweetpoll-answer-wrapper emne-box">
         <div class="web-tweetpoll-answer-answer">
                <header>
                     <div class="answer-label">
                           <spring:message code="commons_detail_answer" />
                     </div>
                     <div class="answer-votes">
                            Votes
                     </div>
                     <div class="answer-percent">
                           %
                     </div>
                </header>
                <c:forEach items="${answers}" var="a">
                    <div class="answer"
                         data-dojo-type="me/web/widget/tweetpoll/detail/TweetPollAnswer"
                         aId="${a.answers.questionAnswerId}"
                         color="${a.answers.color}"
                         label="${a.answers.answer}"
                         owner="${tweetpoll.ownerUsername}"
                         completed="${tweetpoll.completed}"
                         url="<%=request.getContextPath()%>${a.relativeUrl}">
                     </div>
               </c:forEach>
            </div>
    </article>
    <c:if test="${!empty hashtags}">
       <article class="emne-box">
           <header class="category_color">
                <spring:message code="options.hashtag" />
           </header>
           <div class="web-tweetpoll-hashtags ">
               <c:forEach items="${hashtags}" var="h">
                       <span data-dojo-type="me/web/widget/stream/HashTagInfo"
                        url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                        hashTagName="${h.hashTagName}"></span>
               </c:forEach>
           </div>
       </article>
   </c:if>

   <article class="emne-box">
       <header class="category_color">
          <spring:message code="options.links" />
       </header>
       <section>
            <div data-dojo-type="me/web/widget/social/LinksPublished"
                 more="false"
                 itemId="${tweetpoll.id}"
                 type="TWEETPOLL"
                 class="web-social-links"
                 ></div>
       </section>
   </article>

    <section class="web-tweetpoll-comments emne-box">
       <header class="category_color">
            <spring:message code="options.comments" />
      </header>
      <div name="comments"
           data-dojo-type="me/web/widget/comments/Comments"
           type="tweetpoll"
           item_id="${tweetpoll.id}">
      </div>
   </section>

</article>
