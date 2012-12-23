<div id="chess-container">

	<c:forEach var="sq" begin="0" end="63">

		<c:if test="${(sq % 8) == 0}">
			<div class="row">
		</c:if>

		<div id="square_${sq}" class="square">

		</div>

		<c:if test="${(sq + 1) % 8 == 0}">
			</div>
		</c:if>
	</c:forEach>
</div>

<div id="white_promotion_window">
	<img id="WQ" src='images/WQ.png'>
	<img id="WR" src='images/WR.png'>
	<img id="WB" src='images/WB.png'>
	<img id="WN" src='images/WN.png'>
</div>

<div id="black_promotion_window">
	<img id="BQ" src='images/BQ.png'>
	<img id="BR" src='images/BR.png'>
	<img id="BB" src='images/BB.png'>
	<img id="BN" src='images/BN.png'>
</div>