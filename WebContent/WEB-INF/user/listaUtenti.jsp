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
</head>

<body id="body-pd">
	
	<%@ include file="../header.jsp" %>
	
	<%ArrayList<Utente> lista = (ArrayList<Utente>) request.getAttribute("listaUtenti");%>
	
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
				<div class="row justify-content-center">
			
			<% 
			if(lista == null){
				%>
				<h1>Non ci sono utenti</h1> <% 
			}
			
			Iterator it = lista.iterator();
			
			int cont = 0;
			
			while(it.hasNext()){
				
				Utente user = (Utente)it.next(); %>
				
				<div class="col">
					<div class="card center">
						<div class="additional">
						<div class="user-card">
							<img class='center' src="./images/user.png">
						</div>
						<div class="more-info">
							<h3 class="text-center"><%=user.getNome()%> <%=user.getCognome()%></h3>
							<div class="row justify-content-center">
								<form action="ModerazioneUtenza" method="POST">
									<button type="submit" class="button" name="mail" id="timeout" value=<%=user.getMail() %> onclick="timeoutUtente(this.value)" data-bs-toggle="tooltip" data-bs-placement="top" data-bs-title="Sospensione momentanea">
										<i class='bx bxs-time'></i>
									</button>
									<input type="hidden" value="timeout" name="action">
								</form>
								<form action="ModerazioneUtenza" method="POST">
									<button type="submit" class="button" id="ban" name="mail" value=<%=user.getMail() %> onclick="banUtente(this.value)" data-bs-toggle="tooltip" data-bs-placement="top" data-bs-title="Ban utente">
										<i class='bx bxs-x-circle'></i>
									</button>
									<input type="hidden" value="ban" name="action">
								</form>
							</div>
						</div>
						</div>
						<div class="general">
						<h3 class="text-center"><%=user.getNome()%> <%=user.getCognome()%></h3>
						<hr>
						<p>Ruolo: <%=user.getRuolo() %><br>
							Età: <%=user.getEta() %><br>
							Sesso: <%=user.getSesso() %> <br>
							Indirizzo:<%=user.getIndirizzo() %><br>
							Email: <%=user.getMail() %>
						</p>
						<span class="more">Muovi il mouse per eseguire azioni</span>
						</div>
					</div>
				</div>
				<%} %>
			</div>
		</div>
	</div>
</body>
</html>