<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<c:import url="/chess/data/favourite.xml" var="inputvalue" />
<c:import url="/chess/styles/favourite.xsl" var="stylesheet" />

<div id="breadcrumb">
	<a href="${pageContext.request.contextPath}">Home</a> >>
	<a href="${pageContext.request.contextPath}/chess/index.htm">Chess</a> >>
	<a href="${pageContext.request.contextPath}/chess/favourites.htm">Favourite Links</a>
	<hr />
</div>

<table class="normal">

<tr>
	<td height="50">
<spring:message code="title.chesslinks"/>
	</td>
</tr>

</table>

<p></p>

<x:transform xml="${inputvalue}" xslt="${stylesheet}"/>