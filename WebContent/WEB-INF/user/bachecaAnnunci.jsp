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
<title>Bacheca - Comun-ity</title>

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
					url : 'ListaCommissioniServlet',
					method : 'POST',
					success : function(response) {
						$("#commissioniRow").html(response);
					}
				});

				/*Change stars*/

				$(".card-body span").each(
						function() {
							var valutazione = $(this).text();

							for (let i = 1; i <= 5; i++)

								if (i <= Math.round(valutazione))
									$(this).closest('div').find('.star' + i)
											.addClass("checked");
								else
									break;
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
							url : 'ListaLavoriServlet',
							method : 'POST',
							success : function(response) {
								$("#lavoriRow").html(response);
							}
						});

						$('#commissioni').hide();
						$('#lavori').show();
					} else {

						$.ajax({
							url : 'ListaCommissioniServlet',
							method : 'POST',
							success : function(response) {
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
					</h5>
			</div>
			<div class="col-4">
				<label class="switch"> <input type="checkbox" id="check">
					<span class="slider round"></span>
				</label>
			</div>
			<div class="col-4 justify-content-center">
				<h6 class="text-center">
					Lavori
					</h5>
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
	<!--  Modal annunci -->
	<div class="modal fade" tabindex="-1" id="annunci">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<form action="InserimentoAnnuncioServlet" method="post">
					<div class="modal-header">
						<h5 class="modal-title">Crea annuncio</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">

							<div class="mb-3">
								<label for="titolo" class="form-label">Titolo</label> <input
									type="text" class="form-control" name="titolo" required>
							</div>
							<div class="mb-3">
								<label for="indirizzo" class="form-label">Indirizzo</label> <input
									type="text" class="form-control" name="indirizzo" required>
							</div>
							<div class="mb-3">
								<label for="descrizione">Descrizione</label>
								<textarea class="form-control" id="descrizione"
									name="descrizione" rows="3" required></textarea>
							</div>
							<div class="form-check form-switch">
								<input class="form-check-input" type="checkbox"
									name="professionista" id="lavoroCheck"> <label
									class="form-check-label" id="lavoroCheck"
									for="flexSwitchCheckDefault">Annuncio di lavoro</label>
							</div>
							<div class="mb-3" id="professioneDiv">
								<input type="text" class="form-control" name="professione">
								<label for="professione" class="form-label" id="professione">Professione
									richiesta</label>
							</div>
							<div class="mb-3">
								<input type="date" id="nascita" name="data" class="form-control"
									required /> <label class="form-label" for="nascita">Data
									di fine</label>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Chiudi</button>
						<button type="submit" class="btn btn-primary">Crea
							annuncio</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="floating-container" id="modal">
		<div class="floating-button">+</div>
	</div>

	<form action="inviaMail" method="post">
		<input type="hidden" name="mail" id="mail" value="">
	</form>
</body>
</html>