<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="breadcrumb">
	<a href="${pageContext.request.contextPath}">Home</a> >>
	<hr />
</div>

<table class="normal">

	<tr>
		<td height="50">Register yourself.</td>
	</tr>

</table>

<form:form modelAttribute="person">

	<table border=0 align="center">
		<tr>
			<td><spring:message code="label.password" /> :</td>
			<td><form:password path="password" /></td>
			<td><form:errors path="password" cssClass="error" /></td>
		</tr>
		<tr>
			<td colspan="3"><input type="submit" value="Reset Password"></td>
		</tr>
	</table>
</form:form>
