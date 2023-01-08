<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">	
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
	
	<title>Accreditamenti - Comun-ity</title>	
	
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/styles/main.css" />
	<link rel=stylesheet
		href="${pageContext.request.contextPath}/styles/bacheca.css" />
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/styles/lista_utenti.css" />
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/styles/accreditamento.css" />
</head>
<body id="body-pd">

	<%@ include file="../header.jsp"%>
	
	<div class="container-fluid">
	
		<h2>Accreditamenti</h2>
		
		<%List<Accreditamento> lista = (List<Accreditamento>) request.getAttribute("accreditamenti"); 
		
		if(!lista.isEmpty()){
		
		
			Iterator it = lista.iterator();
			
			while(it.hasNext()){
			
				Accreditamento accr = (Accreditamento) it.next();
			%>
				
				<div class="shadow p-3 mb-5 bg-body-tertiary rounded">
					<div class="row">
						<div class="col">
							<p class="text-center">Utente: <%=accr.getRichiedente() %></p>
						</div>
						<div class="col">
							<p class="text-center">Professione: <%=accr.getAbilitazione() %></p>
						</div>
						<div class="col">
							<div style="margin: 0 auto;">
								<button class="button" id="info" 
								data-bs-toggle="modal" data-bs-target="#info">
									<i class='bx bx-info-circle'></i>
								</button>
		
								<button class="button" id="del">
									<i class='bx bx-x-circle' ></i>
								</button>
		
								<button class="button" id="accept">
									<i class='bx bx-check-circle' ></i>
								</button>
		
							</div>
						</div>
					</div>
				</div>	
				<%}
			}else{%>
			<h4>Non ci sono accreditamenti in sospeso</h4>
		<%}%>
		
		<!-- Valutazione -->
		<div class="modal fade" tabindex="-1" id="info">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<form action="AssegnaValutazioneServlet" method="post" id="valutazioneForm">
						<div class="modal-header">
							<h5 class="modal-title">Aggiungi valutazione</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"
								aria-label="Close"></button>
						</div>
						<div class="modal-body">
							<div class="container-fluid">
								<div class="row">
									<div class="col">
										<input type="number" required class="form-control" name="valutazione" value="1" min="1" max="5"
											id="valutazione" /> <label class="form-label" for="valutazione">Inserisci una valutazione</label>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Chiudi</button>
							<button type=button id="sendForm" class="btn btn-success">Assegna valutazione</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>