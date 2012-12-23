<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="breadcrumb">
	<a href="${pageContext.request.contextPath}">Home</a> >>
	<a href="${pageContext.request.contextPath}/chess/index.htm">Chess</a>
	<hr />
</div>

<fieldset>
	<legend>
		<a href="playChess.htm"><spring:message code="home_link_playChess" /></a>
	</legend>
	<spring:message code="title_playChess" />
</fieldset>

<p></p>

<fieldset>
	<legend>
		<a href="myGames.htm"><spring:message code="home_link_myGames" /></a>
	</legend>
	<spring:message code="title_myGames" />
</fieldset>

<p></p>

<fieldset>
	<legend>
		<a href="iccVideos.htm"><spring:message code="home_link_iccVideos" /></a>
	</legend>
	<spring:message code="title_iccVideos" />
</fieldset>

<p></p>

<fieldset>
	<legend>
		<a href="favourites.htm">Favourites</a>
	</legend>
	Favourite Chess Links
</fieldset>