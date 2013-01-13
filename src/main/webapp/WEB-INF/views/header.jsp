<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="header">

	<div id="title">Welcome to Vikas World!</div>

	<table>
		<tr>
			<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_USER, ROLE_FAMILY">
				<td><a id="register"
					href="${pageContext.request.contextPath}/register.htm">Join Now</a></td>
				<td><a id="login"
					href="${pageContext.request.contextPath}/login.htm">Login</a></td>
			</sec:authorize>

			<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER, ROLE_FAMILY">
				<td><a href="<c:url value='/j_spring_security_logout' />">
						Logout</a></td>
			</sec:authorize>

			<td><a id="icc"
				href="http://www.chessclub.com/activities/finger.php?handle=vikas"
				target="_blank">ICC</a></td>
			<td><a href="http://www.twitter.com/vikasworld2010"
				target="_blank"><img
					src="http://twitter-badges.s3.amazonaws.com/follow_bird-a.png"
					alt="Follow me on Twitter"></a></td>
			<td><a id="orkut"
				href="http://www.orkut.com/Main#Profile?uid=12179233015669932329"
				target="_blank">Orkut</a></td>
		</tr>
	</table>

	<P>
		<span> Language : <a href="?lang=en">English</a> | <a
			href="?lang=de">Dutch</a>
		</span> <span style="float: left"> <a href="?theme=default">default</a>
			| <a href="?theme=black">black</a> | <a href="?theme=blue">blue</a>
		</span>
</div>

<script>
	$("#icc, #orkut, #login, #logout, #register", ".button").button();
</script>