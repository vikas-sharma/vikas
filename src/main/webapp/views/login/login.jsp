<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="breadcrumb">
	<a href="${pageContext.request.contextPath}">Home</a> >>
	<a href="${pageContext.request.contextPath}/login.htm">Login</a>
	<hr />
</div>

<table class="normal">

	<tr>
		<td height="50">Login page.</td>
	</tr>

</table>

<div align='center'>

	<%-- this form-login-page form is also used as the
	 form-error-page to ask for a login again.
	 --%>
	<c:if test="${not empty param.login_error}">
		<font color="red">Your login attempt was not successful, please
			try again.<br /> Reason: <c:out
				value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
		</font>
	</c:if>
	<form name="f" action="<c:url value='/j_spring_security_check'/>"
		method="POST">
		<div>
			Username: <input id="j_username" type='text' name='j_username'
				style="width: 150px" />
		</div>
		<br />
		<div>
			Password: <input id="j_password" type='password' name='j_password'
				style="width: 150px" />
		</div>
		<br />
		<div>
			Remember me: <input id="_spring_security_remember_me" type='checkbox'
				name='_spring_security_remember_me' value='on' checked='checked' />
		</div>
		<br />
		<div>
			<a href="forgot.htm">forgot password</a>
		</div>
		<br />
		<div>
			<input id="proceed" type="submit" value="Login" />
			<input id="reset" type="reset" value="Reset" />
		</div>
	</form>

</div>