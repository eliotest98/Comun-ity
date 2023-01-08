<%@ page import="java.util.*"%>
<%@ page import="model.Utente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

<!-- jQuery -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css"
	rel="stylesheet" />
<title>Archivio - Comun-ity</title>

<script src="/Comun-ity/js/main.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/main.css" />
<link rel=stylesheet
	href="${pageContext.request.contextPath}/styles/bacheca.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/lista_utenti.css" />

<script>
	$(document).ready(
			function() {

				$('#commissioni').show();
				$('#lavori').hide();

				$.ajax({
					url : 'ArchivioServlet',
					method : 'POST',
					data : "&action=commissioni",
					success : function(response) {
						$("#commissioniRow").html(response);
						
						
						/*Stars*/
						$('.heading').each(
								function() {
									var valutazione = $(this).text();
									
									console.log(valutazione);
									
									for (let i = 1; i <= 5; i++){

										if (i <= Math.round(valutazione))
											$(this).closest('div').find('.star' + i)
													.addClass("checked");
										else
											break;
									}
								});
					}
				});
				

				/*On click listener per modal*/

				$('#modal').click(function() {
					$('#annunci').modal('show');
				});

				$('#professioneDiv').hide();
				$("#professione").prop('required', false);

				/*Inserimento annuncio switch*/
				$('#lavoroCheck').change(function() {
					if ($(this).is(':checked')) {
						console.log("checked");
						$('#professioneDiv').show();
						$("#professione").prop('required', true);
					} else {
						console.log("unchecked");
						$('#professioneDiv').hide();
						$("#professione").prop('required', false);
					}
				});

				/*Switch commissioni lavori*/
				$("#check").change(function() {
					if (this.checked) {

						$.ajax({
							url : 'ArchivioServlet',
							method : 'POST',
							data : "&action=lavori",
							success : function(response) {
								$("#lavoriRow").html(response);
								
								/*Stars*/
								$('.heading').each(
										function() {
											var valutazione = $(this).text();
											
											console.log(valutazione);
											
											for (let i = 1; i <= 5; i++){

												if (i <= Math.round(valutazione))
													$(this).closest('div').find('.star' + i)
															.addClass("checked");
												else
													break;
											}
										});
							}
						});

						$('#commissioni').hide();
						$('#lavori').show();
					} else {

						$.ajax({
							url : 'ArchivioServlet',
							method : 'POST',
							data : "&action=commissioni",
							success : function(response) {
								$("#commissioniRow").html(response);
								
								/*Stars*/
								$('.heading').each(
										function() {
											var valutazione = $(this).text();
											
											console.log(valutazione);
											
											for (let i = 1; i <= 5; i++){

												if (i <= Math.round(valutazione))
													$(this).closest('div').find('.star' + i)
															.addClass("checked");
												else
													break;
											}
										});
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

	<%@ include file="../header.jsp"%>

	<%
	if (request.getAttribute("error") != null) {
	%>
	<div id="message">
		<div class="alert alert-danger alert-dismissible fade show"
			role="alert">
			<strong>Errore</strong>
			<%=request.getAttribute("error")%>.
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
	</div>
	<%}%>

	<%
	if (request.getAttribute("success") != null) {
	%>
	<div id="message">
		<div class="alert alert-success alert-dismissible fade show"
			role="alert">
			<strong>Successo</strong>
			<%=request.getAttribute("success")%>.
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
	</div>
	<%}%>

	<div class="container-fluid" id="selector">
		<div class="row">
			<div class="col-4 justify-content-center">
				<h6 class="text-center">
					Commissioni
					</h6>
			</div>
			<div class="col-4">
				<label class="switch"> <input type="checkbox" id="check">
					<span class="slider round"></span>
				</label>
			</div>
			<div class="col-4 justify-content-center">
				<h6 class="text-center">
					Lavori
					</h6>
			</div>
		</div>
	</div>

	<section id="commissioni">

		<div class="container-fluid">

			<div class="row" id="commissioniRow"></div>

		</div>

	</section>

	<section id="lavori">

		<div class="container-fluid">

			<div class="row" id="lavoriRow"></div>
		</div>

	</section>
</body>
</html>