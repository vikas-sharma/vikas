<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="normal">

<form:form modelAttribute="game">

	<tr>
		<td height="50">
			This is admin page, only accesible to admin user.
		</td>
	</tr>

<H3>Vote Chess</H3>
	<table>
	<tr>
		<td>Name:</td>
		<td><form:input path="name" /></td>
	</tr>
	<tr>
		<td>Title Player:</td>
		<td><form:input path="titlePlayerId" /></td>
	</tr>
	<tr>
		<td>Title Player rating:</td>
		<td><form:input path="rating" /></td>
	</tr>
	<tr>
		<td>Title (GM/IM):</td>
		<td><form:input path="title" /></td>
	</tr>
	<tr>
		<td>Title Player side:</td>
		<td><form:input path="side" /></td>
	</tr>
	<tr>
		<td>Start Time (yyyy-mm-dd hh:mm:ss):</td>
		<td><form:input path="startTime" /></td>
	</tr>
	<tr>
		<td>Description:</td>
		<td><form:input path="description" /></td>
	</tr>

	<tr><td><input type="submit" name="VoteChess" value="Vote Chess"></td></tr>
	</table>

</form:form>

</table>