<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/<spring:theme code='css'/>" type="text/css" />

<script
	src="http://www.google.com/jsapi?key=ABQIAAAAskGHH4QCF7EWi5qfKmklERSQYZB7zIwjPLM5VrbiIxCsikgCEBTtiSYXBMHZk5VKVPk4kyb_MfGblw"
	type="text/javascript"></script>

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js"></script>

<tiles:insertAttribute name="import_files" ignore="true" />

<spring:message code="application_name" var="app_name"
	htmlEscape="false" />
<title><spring:message code="welcome_h3" arguments="${app_name}" /></title>

</head>

<body class="tundra spring">
	<div id="main_div">
		<tiles:insertAttribute name="header" ignore="true" />
		<div id="main">
			<tiles:insertAttribute name="body" />
		</div>
	</div>
</body>
</html>
