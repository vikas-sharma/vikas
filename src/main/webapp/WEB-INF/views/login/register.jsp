<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="breadcrumb">
	<a href="${pageContext.request.contextPath}">Home</a> >>
	<a href="${pageContext.request.contextPath}/register.htm">Join Now</a>
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
			<td><spring:message code="label.firstname" /> :</td>
			<td><form:input path="firstName" maxlength="32" /></td>
			<td><form:errors path="firstName" cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="label.lastname" /> :</td>
			<td><form:input path="lastName" maxlength="32" /></td>
			<td><form:errors path="lastName" cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="label.email" /> :</td>
			<td><form:input path="emailAddress" /></td>
			<td><form:errors path="emailAddress" cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="label.reenter_email" /> :</td>
			<td><form:input path="reenterEmailAddress" /></td>
			<td><form:errors path="reenterEmailAddress" cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="label.username" /> :</td>
			<td><form:input path="name" maxlength="32" /></td>
			<td><form:errors path="name" cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="label.password" /> :</td>
			<td><form:password path="password" /></td>
			<td><form:errors path="password" cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="label.gender" /> :</td>
			<td>
				<table>
					<tr>
						<td><form:radiobutton path="gender" value="M" label="M" /></td>
						<td><form:radiobutton path="gender" value="F" label="F" /></td>
					</tr>
				</table>
			</td>
			<td><form:errors path="gender" cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="label.country" /> :</td>
			<td><form:select path="country">
					<form:options items="${countryList}" itemValue="name"
						itemLabel="name" />
				</form:select></td>
			<td><form:errors path="country" cssClass="error" /></td>
		</tr>

<!-- 		<tr> -->
<%-- 			<td><spring:message code="label.word_verification" /> :</td> --%>
<%-- 			<td>${reCaptchaHtml}</td> --%>
<%-- 			<td><form:errors path="captcha" cssClass="error" /></td> --%>
<!-- 		</tr> -->

		<tr>
			<td colspan="3"><input type="submit" value="Create My Account"></td>
		</tr>
	</table>
</form:form>
