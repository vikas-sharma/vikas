<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="breadcrumb">
	<a href="${pageContext.request.contextPath}">Home</a> >>
	<a href="${pageContext.request.contextPath}/chess/index.htm">Chess</a> >>
	<a href="${pageContext.request.contextPath}/chess/iccVideos.htm">Watch Videos</a>
	<hr />
</div>

<table class="normal">

<tr>
	<td height="50">
<spring:message code="home_link_iccVideos"/>
	</td>
</tr>

</table>

<p></p>

<div class="container">

<div class="left-element">

	<H3>Select a Game:</H3>

	<a href="iccVideos.htm?gameId=1">GM Banzai vs vikas</a> <BR/>
	<a href="iccVideos.htm?gameId=2">vikas vs nyvlad</a> <BR/>
	<a href="iccVideos.htm?gameId=3">vikas vs dereito</a> <BR/>
	<a href="iccVideos.htm?gameId=4">WGM riverbeauty vs vikas</a> <BR/>
	<a href="iccVideos.htm?gameId=5">FM thecat vs vikas</a> <BR/>
	<a href="iccVideos.htm?gameId=6">GM berta vs vikas</a> <BR/>
	<a href="iccVideos.htm?gameId=7">greatowl vs vikas</a> <BR/>
	<a href="iccVideos.htm?gameId=8">IM juraj vs vikas</a> <BR/>
	<a href="iccVideos.htm?gameId=9">vikas vs IM makunap</a> <BR/>
	<a href="iccVideos.htm?gameId=10">vikas vs IM merick</a> <BR/>
	<a href="iccVideos.htm?gameId=11">pavet vs vikas</a> <BR/>
	<a href="iccVideos.htm?gameId=12">stesan vs vikas</a>

</div>

<div class="right-element">

<c:choose>
	<c:when test="${empty gameId}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/pB0d7HpBP7c?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/pB0d7HpBP7c?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
	<c:when test="${gameId == '1'}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/pB0d7HpBP7c?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/pB0d7HpBP7c?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
	<c:when test="${gameId == '2'}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/VA2_nGwibag?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/VA2_nGwibag?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
	<c:when test="${gameId == '3'}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/5VXrbQAtHbY?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/5VXrbQAtHbY?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
	<c:when test="${gameId == '4'}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/0VZMwaw84aQ?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/0VZMwaw84aQ?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
	<c:when test="${gameId == '5'}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/FfUzZnHf9Yc?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/FfUzZnHf9Yc?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
	<c:when test="${gameId == '6'}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/OmY6Dnpt1ow?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/OmY6Dnpt1ow?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
	<c:when test="${gameId == '7'}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/n5fldsZJ1sg?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/n5fldsZJ1sg?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
	<c:when test="${gameId == '8'}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/9htDU-_RKZw?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/9htDU-_RKZw?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
	<c:when test="${gameId == '9'}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/VpKvh1sgDK8?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/VpKvh1sgDK8?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
	<c:when test="${gameId == '10'}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/bgpZHJ1OTO8?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/bgpZHJ1OTO8?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
	<c:when test="${gameId == '11'}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/LmyatVKJf6k?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/LmyatVKJf6k?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
	<c:when test="${gameId == '12'}">
		<object width="640" height="385"><param name="movie" value="http://www.youtube.com/v/37SRbMbqDpI?fs=1&amp;hl=en_GB"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/37SRbMbqDpI?fs=1&amp;hl=en_GB" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed></object>
	</c:when>
</c:choose>
</div>

</div>