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
<title>Bacheca - Comun-ity</title>

<script src="/Comun-ity/js/main.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" />
<link rel=stylesheet href="${pageContext.request.contextPath}/styles/bacheca.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/lista_utenti.css"/>

<script>

	$(document).ready(function () {

		$('#commissioni').show();
		$('#lavori').hide();
		
		$.ajax({
			url:'ListaCommissioniServlet',
			method:'POST',
			success: function(response){
				$("#commissioniRow").html(response); 
			}
		});

		$("#cmn-toggle-4").change(function() {
			if(this.checked) {
				
				$.ajax({
					url:'ListaLavoriServlet',
					method:'POST',
					success: function(response){
						$("#lavoriRow").html(response); 
					}
				});
				
				$('#commissioni').hide();
				$('#lavori').show();
			}else{
				
				$.ajax({
					url:'ListaCommissioniServlet',
					method:'POST',
					success: function(response){
						$("#commissioniRow").html(response); 
					}
				});
				
				$('#lavori').hide();
				$('#commissioni').show();
			}
		});

	})

</script>
</head>

<body id="body-pd">
	
	<%@ include file="../header.jsp" %>
	
	<div class="container-fluid" id="selector">
		<div class="row">
			<div class="col-4 justify-content-center">
				<h5 class="text-center">Commissioni</h5>
			</div>
			<div class="col-4">
				<div class="switch">
					<input id="cmn-toggle-4" class="cmn-toggle cmn-toggle-round-flat" type="checkbox">
					<label for="cmn-toggle-4"></label>
				</div>
			</div>
			<div class="col-4 justify-content-center">
				<h5 class="text-center">Lavori</h5>
			</div>
		</div>
	</div>

	<section id="commissioni">

	<div class="container-fluid">

		<div class="row" id="commissioniRow">


		</div>

	</div>

	</section>

	<section id="lavori">

		<div class="container-fluid">

			<div class="row" id="lavoriRow">

			</div>
		</div>

	</section>
	
	<form action="inviaMail" method="post">
			<input type="hidden" name="mail" id="mail" value="">
	</form>
</body>
</html>