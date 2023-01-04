<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Utente" import="model.Annuncio"
	import="java.util.*" import="java.time.format.*" import="java.time.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Area Personale - Comun-ity</title>
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
<script src="/Comun-ity/js/main.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/main.css" />
<link rel=stylesheet
	href="${pageContext.request.contextPath}/styles/bacheca.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/lista_utenti.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/areaPersonale.css" />

<script>
	$(document).ready(function() {

		var valutazione = $('.card-body span').text();

		for (let i = 1; i <= 5; i++) {
			if (i <= Math.round(valutazione)) {
				$('#star' + i).addClass("checked");
			} else {
				break;
			}
		}
	});
</script>

</head>
<body id="body-pd">

	<%@ include file="../header.jsp"%>

	<%
	Utente user = (Utente) session.getAttribute("user");
	%>

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
		<h1 class="text-center">Area Personale</h1>
		<div class="row main-row">
			<div class="col">
				<div class="shadow p-3 mb-5 bg-body-tertiary rounded" id="card1">
					<div class="row">
						<h3 class="text-center"><%=user.getNome()%>
							<%=user.getCognome()%></h3>
					</div>
					<div class="card-body">
						<p>
							Età:
							<%=user.getEta()%><br> Email:
							<%=user.getMail()%><br> Sesso:
							<%=user.getSesso()%><br> Ruolo:
							<%=user.getRuolo()%><br> Valutazione: <span class="heading"
								id="val"><%=user.getReputazione()%></span> <i
								class='bx bxs-star' id="star1"></i> <i class='bx bxs-star'
								id="star2"></i> <i class='bx bxs-star' id="star3"></i> <i
								class='bx bxs-star' id="star4"></i> <i class='bx bxs-star'
								id="star5"></i>
						</p>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="shadow p-3 mb-5 bg-body-tertiary rounded" id="card1">
					<div class="row">
						<h3 class="text-center">Modifica i tuoi dati</h3>
					</div>
					<div class="d-flex justify-content-center">
						<button class="btn btn-primary" data-bs-toggle="modal"
							<%if (user.getRuolo().equals("professionista") || user.getRuolo().equals("admin")) {%>
							disabled <%}%> data-bs-target="#professionista">Iscriviti
							come professionista</button>
						<button class="btn btn-danger" data-bs-toggle="modal"
							data-bs-target="#password">Modifica password</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid">

		<h1>I tuoi annunci</h1>
		<div class="row">
			<div class="container-fluid">
				<div class="row">
					<%	
					if(request.getAttribute("annunci") != null){
						List<Annuncio> annunci = (List<Annuncio>) request.getAttribute("annunci");
						Iterator<Annuncio> it = annunci.iterator();
							
						Date date = new Date();
						ZoneId defaultZoneId = ZoneId.systemDefault();
						
						while(it.hasNext()){
							Annuncio annuncio = it.next();%>
					<div class="col">
						<div class="card center">
							<div class="additional">
								<div class="user-card">
									<img class='center' src="/Comun-ity/images/to-do-list.png"
										width="100%">
								</div>
								<div class="more-info">
									<h3 class="text-center"><%=annuncio.getTitolo()%></h3>
									<div class="row justify-content-center">
										<form action="CancellaAnnuncioServlet" method="post"
											style="width: auto;">
											<input type="hidden" name="annuncio" id="annuncio"
												value="<%=annuncio.getId() %>">
											<%if(!date.after(Date.from(annuncio.getDataFine().atStartOfDay(defaultZoneId).toInstant()))) {%>
											<button type="submit" class="btn btn-danger" id="bottone">Rimuovi</button>
											<% }else {%><h5>L'annuncio non è rimuovibile</h5><%} %>
										</form>
									</div>
								</div>
							</div>
							<div class="general">
								<h3 class="text-center"><%=annuncio.getTitolo()%></h3>
								<hr>
								<p>
									Autore:
									<%=annuncio.getAutore()%><br> Descrizione:
									<%=annuncio.getDescrizione()%><br> Indirizzo:
									<%=annuncio.getIndirizzo()%><br> Data Fine:
									<%=annuncio.getDataFine()%><br> Valutazione Utente: <span
										class="heading" id="val"><%=user.getReputazione()%></span> <i
										class='bx bxs-star' id="star1"></i> <i class='bx bxs-star'
										id="star2"></i> <i class='bx bxs-star' id="star3"></i> <i
										class='bx bxs-star' id="star4"></i> <i class='bx bxs-star'
										id="star5"></i>
								</p>
								<span class="more">Muovi il mouse per accettare</span>
							</div>
						</div>
					</div>
					<%}%>
				</div>
			</div>
		</div>
	</div>
	<%}%>
	<!-- Professionista -->
	<div class="modal fade" tabindex="-1" id="professionista">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Iscriviti come professionista</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<form>
							<div class="row">
								<div class="col">
									<input type="text" class="form-control" name="professione"
										id="professione" /> <label class="form-label"
										for="professione">Professione</label>
								</div>
								<div class="col">
									<input class="form-control" type="file" id="formFileMultiple"
										name="file">
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Chiudi</button>
					<button type="button" class="btn btn-primary">Invia la
						candidatura</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Password -->
	<div class="modal fade" tabindex="-1" id="password">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<form action="ModificaPasswordServlet" method="post">
					<div class="modal-header">
						<h5 class="modal-title">Modifica password</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">

							<div class="row">
								<div class="col">
									<input type="text" class="form-control" name="oldPass"
										id="oldPass" /> <label class="form-label" for="professione">Inserisci
										la password precedente</label>
								</div>
								<div class="col">
									<input type="text" minlength="8" maxlength="20"
										class="form-control" name="newPass" id="newPass" /> <label
										class="form-label" for="professione">Inserisci la
										nuova password</label>
								</div>
							</div>

						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Chiudi</button>
						<button type="submit" class="btn btn-danger">Modifica
							Password</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>