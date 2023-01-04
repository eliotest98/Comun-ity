<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Utente"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
	crossorigin="anonymous"></script>

<!-- jQuery -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css"
	rel="stylesheet" />
<title>Benvenuto - Comun-ity</title>

<script src="/Comun-ity/js/main.js"></script>
<link rel="stylesheet" href="/Comun-ity/styles/main.css" />
</head>
<body id="body-pd">

	<%@ include file="../header.jsp"%>

	<div class="height-100">

		<%
		Utente user = (Utente) session.getAttribute("user");
		%>

		<h1>
			Benvenuto,
			<%=user.getNome()%></h1>
	</div>
</body>

</body>
</html>