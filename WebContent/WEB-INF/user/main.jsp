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
<link rel="stylesheet" href="/Comun-ity/styles/main.css"/>
</head>
<body id="body-pd">
	<header class="header" id="header">
	  <div class="header_toggle">
	    <i class="bx bx-menu" id="header-toggle"></i>
	  </div>
	</header>
	<div class="l-navbar" id="nav-bar">
	  <nav class="nav">
	  	<div>
	  	<a href="/Comun-ity/IndexServlet" class="nav_logo">
            <img src="/Comun-ity/images/logo2.png" width="45" height="45" class="bx bx-layer nav_logo-icon"/>
            <span class="nav_logo-name">Comun-ity</span>
          </a>
	      <div class="nav_list">
	        <a href="#" class="nav_link active" id="dashboard">
	          <i class="bx bxs-dashboard nav_icon"></i>
	          <span class="nav_name">Dashboard</span>
	        </a>
	        <a href="#" class="nav_link" id="bacheca">
	          <i class="bx bx-clipboard nav_icon"></i>
	          <span class="nav_name">Bacheca Annunci</span>
	        </a>
	        <a href="#" class="nav_link" id="archivio">
	          <i class="bx bx-box nav_icon"></i>
	          <span class="nav_name">Archivio Annunci</span>
	        </a>
	        <a href="#" class="nav_link" id="impostazioni">
	          <i class="bx bx-cog nav_icon"></i>
	          <span class="nav_name">Impostazioni</span>
	        </a>
	        <% if((Boolean) request.getAttribute("admin")){ %>
	        <!-- TODO mostra solo se admin -->
	        <a href="#" class="nav_link" id="utenti">
	          <i class="bx bx-user nav_icon"></i>
	          <span class="nav_name">Lista Utenti</span>
	        </a>
	        <!-- TODO mostra solo se admin -->
	        <a href="#" class="nav_link" id="accreditamenti">
	        	<div class="icon-badge-container nav_icon">
	        		<span class="badge badge-light icon-badge text-center">9</span>
			       	<i class="bx bxs-edit nav_icon"></i>
			    </div>
			    <span class="nav_name">Accreditamenti</span>
	        </a>
	        <%} %>
	      </div>
	      </div>
	    <div>
		    <a href="#" class="nav_link" id="areaPersonale">
		      <i class="bx bx-user-circle nav_icon"></i>
		      <span class="nav_name">Area Personale</span>
		    </a>
		    <a href="/Comun-ity/LogoutServlet" class="nav_link">
		      <i class="bx bx-log-out nav_icon"></i>
		      <span class="nav_name">Log out</span>
		    </a>
	    </div>
	  </nav>
	</div>
	<div class="height-100">
	</div>
</body>

</body>
</html>