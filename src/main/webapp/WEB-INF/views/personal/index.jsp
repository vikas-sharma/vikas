<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="breadcrumb">
	<a href="${pageContext.request.contextPath}">Home</a> >> <a
		href="${pageContext.request.contextPath}/personal/index.htm">Personal</a>
	<hr />
</div>

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