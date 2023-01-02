<%@ page import="java.util.*" %>
<%@ page import="model.Utente" %>
<div class="container-fluid">
	<div class="shadow p-3 mb-5 bg-body-tertiary rounded">
	
	<%ArrayList<Utente> lista = (ArrayList<Utente>) request.getAttribute("listaUtenti");
	
	if(lista == null){
		%>
		<h1>Non ci sono utenti</h1> <% 
	}
	
	Iterator it = lista.iterator();
	
	int cont = 0;
	
	while(it.hasNext()){
		
		if(cont == 0){
			
			%>
			<div class="row justify-content-cente">
			<%
		}
		
		if(cont == 3){
			cont = 0; %>
			</div>
			<div class="row justify-content-cente">
		<% } %>
		
		<div class="col-xxl-4">
			<div class="card center">
				<div class="additional">
				<div class="user-card">
					<img class='center' src="./images/user.png">
				</div>
				<div class="more-info">
					<h3 class="text-center">Nome Cognome</h3>
					<div class="row justify-content-center">
						<button type="button" class="button" id="timeout" data-bs-toggle="tooltip" data-bs-placement="top" data-bs-title="Sospensione momentanea">
							<i class='bx bxs-time'></i>
						</button>
						<button type="button" class="button" id="ban" data-bs-toggle="tooltip" data-bs-placement="top" data-bs-title="Ban utente">
							<i class='bx bxs-x-circle'></i>
						</button>
					</div>
				</div>
				</div>
				<div class="general">
				<h3 class="text-center">Nome Cognome</h3>
				<hr>
				<p>Ruolo: admin<br>
					Età: 21<br>
					Sesso: M <br>
					Indirizzo: Via dei morti neri<br>
					Telefono: 333 333 3333
				</p>
				<span class="more">Muovi il mouse per eseguire azioni</span>
				</div>
			</div>
		</div>
		<%} %>
	</div>
</div>