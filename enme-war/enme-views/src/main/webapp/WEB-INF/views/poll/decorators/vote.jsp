<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-wrapper-detail web-poll-wrapper-vote">
    <form action="<%=request.getContextPath()%>/poll/vote/post" method="post">
       <header>
           <h5>${poll.questionBean.questionName}</h5>
       </header>

       <div class="web-poll-vote">
        <c:if test="${votePollError}">
               <div class="error">
                 <spring:message code="poll.error"/>
               </div>
             </c:if>
               <article class="emne-box">
                    <section class="web-vote">
                        <article data-dojo-type="me/web/widget/poll/vote/PollVote"
                                 pollId="${poll.id}">
                            <c:forEach items="${answers}" var="a">
                                <section data-dojo-type="${poll.questionBean.widget}"
                                         itemId="${a.answerId}"
                                         name="poll"
                                         label="${a.answers}">
                                </section>
                            </c:forEach>
                        </article>
                   </section>
               </article>
               <input name="itemId" type="hidden" value="${poll.id}">
               <input name="type" type="hidden" value="${poll.questionBean.pattern}">
               <input name="slugName" type="hidden" value="${poll.questionBean.slugName}">
       </div>
       <footer>
            <button class="enme-ui-button vote" type="submit">Vote</button>
       </footer>
   </form>
</article>