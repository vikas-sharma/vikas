<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<c:import url="/personal/data/photo.xml" var="inputvalue" />
<c:import url="/personal/styles/familyPhotos.xsl" var="stylesheet" />

<div id="breadcrumb">
	<a href="${pageContext.request.contextPath}">Home</a> >>
	<a href="${pageContext.request.contextPath}/personal/index.htm">Personal</a> >>
	<a href="${pageContext.request.contextPath}/personal/familyPhotos.htm">Family Photos</a>
	<hr />
</div>

<table class="normal">

	<tr>
		<td height="50"><spring:message code="title_familyPhotos" /></td>
	</tr>

</table>

<p></p>

<x:transform xml="${inputvalue}" xslt="${stylesheet}" />

<script>
	loadFamilyAlbum(1);
</script>