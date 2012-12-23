<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="breadcrumb">
	<a href="${pageContext.request.contextPath}">Home</a> >>
	<a href="${pageContext.request.contextPath}/personal/index.htm">Personal</a>
	<hr />
</div>

<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER, ROLE_FAMILY">

	<fieldset>
		<legend>
			<a href="myPhotos.htm"><spring:message code="home_link_myPhotos" /></a>
		</legend>
		<spring:message code="title_myPhotos" />
	</fieldset>

	<p></p>

	<fieldset>
		<legend>
			<a href="familyPhotos.htm"><spring:message
					code="home_link_familyPhotos" /></a>
		</legend>
		<spring:message code="title_familyPhotos" />
	</fieldset>

</sec:authorize>

<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_USER, ROLE_FAMILY">
	<div id="access_denied">
		Please <a href="${pageContext.request.contextPath}/login.htm">Login</a>
		or <a href="${pageContext.request.contextPath}/register.htm">Register</a>
		to access my personal pages!
	</div>
</sec:authorize>