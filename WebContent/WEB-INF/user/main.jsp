<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"  import ="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

<!-- jQuery -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/lista_utenti.css" />
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

		<h1>
			Benvenuto,
			<%=user.getNome()%></h1>
		
		<div class="contanier-fluid" style="margin-top: 50px;">
			<div class="row">
			<div class="container-fluid" class="main">
				<div class="row">
				<h3>Incarichi accettati</h3>
					<%	
					List<Annuncio> annunci = (List<Annuncio>) request.getAttribute("annunci");
					if(!annunci.isEmpty()){
						Iterator<Annuncio> it = annunci.iterator();
						
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
												<form action="AnnuncioCompletato" method="post"
													style="width: auto;">
													<input type="hidden" name="annuncio" id="annuncio"
														value="<%=annuncio.getId() %>">
													<button type="submit" class="btn btn-success" id="bottone">Termina</button>
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
											<%=annuncio.getDataFine()%><br>
										</p>
										<span class="more">Muovi il mouse per rimuovere</span>
									</div>
								</div>
							</div>
							<%}%>
						</div>
					</div>
				</div>
			
			<%}else{ %>
				<h5>Non hai accettato incarichi</h5>
			<%}%>
		</div>
		
		
	</div>
</body>

</body>
</html>