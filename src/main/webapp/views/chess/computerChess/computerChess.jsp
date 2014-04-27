<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="breadcrumb">
	<a href="${pageContext.request.contextPath}">Home</a> >>
	<a href="${pageContext.request.contextPath}/chess/index.htm">Chess</a> >>
	<a href="${pageContext.request.contextPath}/chess/playChess.htm">Play Chess</a>
	<hr />
</div>

<table class="normal">

<tr>
	<td height="50">
<spring:message code="title_playChess"/>
	</td>
</tr>

</table>

<p>

<div align="center">

<%@ include file="chessBoard.jsp" %>

<div>
	<input type='button' onclick='javascript:newGame();' value='New Game'>
	<input type='button' onclick='javascript:switchSides();' value='Switch Sides'>
	<input type='button' onclick='javascript:takebackMove();' value='Takeback Move'>
</div>

</div>