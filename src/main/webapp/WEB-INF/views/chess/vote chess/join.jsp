<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="normal">

<tr>
	<td height="50">
<spring:message code="title_playChess"/>
	</td>
</tr>

</table>

<p>

<div align="center">

<div id="chess-container">

<%@ include file="chessBoard.jsp" %>

</div>

</div>