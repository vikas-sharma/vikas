<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="breadcrumb">
	<a href="${pageContext.request.contextPath}">Home</a> >>
	<a href="${pageContext.request.contextPath}/forgot.htm">Forgot Password</a>
	<hr />
</div>

<table class="normal">

	<tr>
		<td height="50">Forgot Password? Don't worry, I'll mail you.</td>
	</tr>

</table>

<div align='center'>

	<form:form modelAttribute="person">

		<c:choose>

			<c:when test="${param.msg == 'success'}">
				<font color="red">Your username and password details are sent
					to your mail id. Please check.</font>
			</c:when>

			<c:otherwise>

				<c:if test="${param.msg == 'fail'}">
					<font color="red">Please provide correct mail id or
						username.</font>
				</c:if>

				<table>
					<tr>
						<td colspan="3">Please provide either your mail id or
							username</td>
					</tr>
					<tr>
						<td>email id :</td>
						<td><form:input path="emailAddress" /></td>
						<td><form:errors path="emailAddress" cssClass="error" /></td>
					</tr>
					<tr>
						<td colspan="3">--OR--</td>
					</tr>
					<tr>
						<td>User Name :</td>
						<td><form:input path="name" maxlength="32" /></td>
						<td><form:errors path="name" cssClass="error" /></td>
					</tr>

					<tr>
						<td colspan="3"><input type="submit"
							value="Email My Password"></td>
					</tr>

				</table>
			</c:otherwise>
		</c:choose>

	</form:form>

</div>