<header class="header" id="header">
	<div class="header_toggle">
		<i class="bx bx-menu" id="header-toggle"></i>
	</div>
</header>
<div class="l-navbar" id="nav-bar">
	<nav class="nav">
		<div>
			<a href="/Comun-ity/IndexServlet" class="nav_logo"> <img
				src="/Comun-ity/images/logo2.png" width="45" height="45"
				class="bx bx-layer nav_logo-icon" /> <span class="nav_logo-name">Comun-ity</span>
			</a>
			<div class="nav_list">
				<a href="HomeServlet" class="nav_link" id="dashboard"> <i
					class="bx bxs-dashboard nav_icon"></i> <span class="nav_name">Dashboard</span>
				</a> <a href="ListaAnnunciServlet" class="nav_link" id="bacheca"> <i
					class="bx bx-clipboard nav_icon"></i> <span class="nav_name">Bacheca
						Annunci</span>
				</a> <a href="#" class="nav_link" id="impostazioni"> <i
					class="bx bx-cog nav_icon"></i> <span class="nav_name">Impostazioni</span>
				</a>
				<% if((Boolean) session.getAttribute("admin")){ %>
				<a href="ListaUtenti" class="nav_link" id="utenti"> <i
					class="bx bx-user nav_icon"></i> <span class="nav_name">Lista
						Utenti</span>
				</a> <a href="ArchivioServlet" class="nav_link" id="archivio"> <i
					class="bx bx-box nav_icon"></i> <span class="nav_name">Archivio
						Annunci</span>
				</a>
				<a href="#" class="nav_link" id="accreditamenti">
					<div class="icon-badge-container nav_icon">
						<span class="badge badge-light icon-badge text-center">9</span> <i
							class="bx bxs-edit nav_icon"></i>
					</div> <span class="nav_name">Accreditamenti</span>
				</a>
				<%} %>
			</div>
		</div>
		<div>
			<a href="AreaPersonale" class="nav_link" id="areaPersonale"> <i
				class="bx bx-user-circle nav_icon"></i> <span class="nav_name">Area
					Personale</span>
			</a> <a href="/Comun-ity/LogoutServlet" class="nav_link"> <i
				class="bx bx-log-out nav_icon"></i> <span class="nav_name">Log
					out</span>
			</a>
		</div>
	</nav>
</div>

<% if (request.getAttribute("link") != null){%>
<script>
  		
  			$(document).ready(function () {
  				
  				var value = <%=(String) request.getAttribute("link")%>;
  			
  				console.log(value);
  				value.classList.add("active");
  			});
  		
  			
  		</script>
<%}else{%>
<script>
	  	$(document).ready(function () {
	  		$('#dashboard').addClass("active");
	  	});
  	</script>

<%}%>