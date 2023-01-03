<%@ page import="java.util.*" %>
<%@ page import="model.Utente" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>

<!-- jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css" rel="stylesheet"/>
<title>Benvenuto - Comun-ity</title>

<script src="/Comun-ity/js/main.js"></script>
<link rel=stylesheet href="/Comun-ity/styles/main.css" />
<link rel="stylesheet" href="/Comun-ity/styles/lista_utenti.css"/>

<script>

	$(document).ready(function () {
		
		var firstData = new FormData();
		
		firstData.append("search","prova");
		
		$.ajax({
			method:'POST',
			url:'ListaUtenti',
			data: "&search=" + "",
			dataType: "html",
			success: function (response){
				$('#utentiRow').html(response);
			}
		});
		
		$('#searchtext').on('input',function () {
			
			var val = $('#searchtext').val();
		
			$.ajax({
				method:'POST',
				url:'ListaUtenti',
				data: "&search=" + val,
				dataType: "html",
				success: function (response){
					$('#utentiRow').html(response);
				}
			});
			
		});
		
	});
</script>

</head>

<body id="body-pd">
	
	<%@ include file="../header.jsp" %>
		
	<div class="height-100">
	
	 <% if (request.getAttribute("error") != null){%>
    <div id="error">
		<div class="alert alert-danger alert-dismissible fade show" role="alert">
		  <strong>Errore</strong> <%= request.getAttribute("error")%>.
		  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</div>
	<%}%>
	
	<% if (request.getAttribute("success") != null){%>
    <div id="success">
		<div class="alert alert-success alert-dismissible fade show" role="alert">
		  <strong>Successo</strong> <%= request.getAttribute("success")%>.
		  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</div>
	<%}%>
		<div class="container-fluid">
			<div class="shadow p-3 mb-5 bg-body-tertiary rounded">
				<div clas="container">
					<div class="row" id="searchbar">
					      <input type="text" class="search" id="searchtext" placeholder="Cerca tra gli utenti">
					      <button type="submit" class="searchButton" id="search">
					        <i class='bx bx-search-alt-2'></i>
					     </button>
					</div>
				</div>
				<div class="row justify-content-center" id="utentiRow">
							
				</div>
			</div>
		</div>
	</div>
</body>
</html>