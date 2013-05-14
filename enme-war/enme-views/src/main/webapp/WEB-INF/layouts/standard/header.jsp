<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="web-header">
    <div class="web-header-wrapper enme-auto-center">
        <%@ include file="/WEB-INF/layouts/logo.jsp"%>
        <div class="web-header-options">
            <c:if test="${!logged}">
                <span class="link"> <a
                    href="<%=request.getContextPath()%>/user/signin"> <spring:message
                            code="header.signin" /> </a>
                </span>
            </c:if>
            <c:if test="${logged}">
                <span class="link"> <a
                    href="<%=request.getContextPath()%>/home"> <spring:message
                            code="header.public.line" /> </a>
                </span>
            </c:if>
            <c:if test="${logged}">
                <span class="link">
                    <div data-dojo-type="me/web/widget/menu/DashBoardMenu"
                         contextPath="<%=request.getContextPath()%>"></div>
                </span> -
            </c:if>
             <span class="link web-search-wrapper">
                    <div data-dojo-type="me/web/widget/menu/SearchMenu"></div>
            </span>
        </div>
    </div>
    <c:if test="${logged}">
    <div data-dojo-type="dojox/widget/UpgradeBar" id="upgradeBar2" data-dojo-props="noRemindButton:''" style="display:none;">
        <div validate="${!isActivated}">
            <span>
                <spring:message code="singup.account.not.validated" />
            </span>
            <a href="<%=request.getContextPath()%>/user/confirm/email/refresh/code">
                   <spring:message code="singup.account.send.code" />
            </a>
        </div>
    </div>
    </c:if>
</div>
