<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="breadcrumb">
	<a href="${pageContext.request.contextPath}">Home</a> >>
	<a href="${pageContext.request.contextPath}/chess/index.htm">Chess</a> >>
	<a href="${pageContext.request.contextPath}/chess/myGames.htm">My Games</a>
	<hr />
</div>

<table class="normal">

	<tr>
		<td height="50"><spring:message code="title_myGames" /></td>
	</tr>

</table>

<div style="height: 100%;">

	<div class="container">

		<div class="left-element">

			<H3>
				Filter: <SELECT
					ONCHANGE="filter(this.options[this.selectedIndex].value);">
					<OPTION VALUE="all" SELECTED>All games
					<OPTION VALUE="imgm">Games against IM/GM
					<OPTION VALUE="fav">My Favourite
				</SELECT>
			</H3>

			<c:forEach var="game" items='${games}'>

				<div id="game_${game.key}">
					<a href="#"
						onclick="loadGame(<c:out value='${game.key}'/>);return false;"><c:out
							value='${game.key}. ${game.value}' /></a><BR />
				</div>

			</c:forEach>

		</div>

		<div class="right-element">

			<div id="chess-container">

				<c:forEach var="i" begin="0" end="63">

					<c:if test="${(i % 8) == 0}">
						<div class="row">
					</c:if>

					<div id="square_${i}" class="square"></div>

					<c:if test="${(i + 1) % 8 == 0}">
			</div>
			</c:if>
			</c:forEach>
		</div>
		<div id="pgn_string" class="pgn-window"></div>

		<input id="beginBtn" type='image' src="/vikas/images/rewind.png"
			alt='|<' onclick='javascript:begin()'> <input
			id="backwardBtn" type='image' src="/vikas/images/prev.png"
			alt='<' onclick='javascript:goback()'> <input id="playBtn"
			type='image' src="/vikas/images/play.png" alt='>>'
			onclick='javascript:play()'> <input id="pauseBtn"
			type='image' src="/vikas/images/pause.png" alt='||'
			onclick='javascript:pause()'> <input id="forwardBtn"
			type='image' src="/vikas/images/next.png" alt='>'
			onclick='javascript:goforward()'> <input id="endBtn"
			type='image' src="/vikas/images/fastf.png" alt='>|'
			onclick='javascript:end()'>

	</div>

</div>

</div>

<script>
	loadGame(1);

	$(document).keydown(function(e){
		if (e.keyCode == 37) { // left key pressed
			goback();
			return false;
		} else if (e.keyCode == 39) { // right key pressed
			goforward();
			return false;
		}
	}); 
</script>