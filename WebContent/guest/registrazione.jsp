<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrati - Comun-ity</title>
<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>

<!-- jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

<link href="${pageContext.request.contextPath}/styles/registrazione.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/js/registrazione.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?libraries=places&callback=initAutocomplete&language=nl&output=json&key=YOUR_API_KEY" async defer></script>

<script type="text/javascript">
  function initAutocomplete() {
    var address = document.getElementById('address');
    var autocomplete = new google.maps.places.Autocomplete(address);
  }
</script>

</head>
<body>
	<%@ include file="./navbar/navbar.html" %>
	<% if (request.getAttribute("message") != null){%>
    <div id="message">
		<div class="alert alert-danger alert-dismissible fade show" role="alert">
		  <strong>Errore</strong> <%= request.getAttribute("message")%>.
		  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</div>
	<%}%>
	<div class="main-wrapper">
        <div id="card" class="shadow p-3 mb-5 bg-white rounded cardcontact">
          <h2 class="card-title text-center">Registrazione</h2>
          <div class="card-body">
            <form id="registrazione" action="${pageContext.request.contextPath}/RegistrazioneServlet" method="post" enctype='multipart/form-data'>
			  <div class="row mb-4">
			    <div class="col">
			      <div class="form-outline">
			        <input type="text" id="nome" name="nome" class="form-control" required/>
			        <label class="form-label" for="nome">Nome</label>
			      </div>
			    </div>
			    <div class="col">
			      <div class="form-outline">
			        <input type="text" id="cognome" name="cognome" class="form-control" required/>
			        <label class="form-label" for="cognome">Cognome</label>
			      </div>
			    </div>
			  </div>
			  
			  <div class="row mb-4">
			  	<div class="col">
				    <input type="email" id="email" name="email" class="form-control" required/>
				    <label class="form-label" for="email">Email</label>
			    </div>
			    <div class="col">
				    <input type="password" id="password" name="password" placeholder="********" class="form-control" minlength="8" maxlength="20" required/>
				    <label class="form-label" for="password">Password</label>
			    </div>
			  </div>
			  
			  <div class="row mb-4">
			  	<div class="col">
				    <input type="tel" id="telefono" name="telefono" class="form-control" required/>
				    <label class="form-label" for="telefono">Telefono</label>
			    </div>
			    <div class="col">
				    <input type="date" id="nascita" name="data" class="form-control" required/>
				    <label class="form-label" for="nascita">Data di nascita</label>
			    </div>
			  </div>
			  
			  <div class="row mb-4">
			  	<div class="col">
				    <select class="form-select mb-3" name="sesso" aria-label=".form-select-lg example">
					  <option selected>Scegli il tuo sesso</option>
					  <option value="M">M</option>
					  <option value="F">F</option>
					</select>
			    </div>
			    <div class="col">
				    <input type="text" id="form3Example3" name="indirizzo" placeholder="Via/Piazza nome, civico, Abbreviazione Capoluogo, Citta, Cap" class="form-control" required/>
				    <label class="form-label" for="form3Example3">Indirizzo</label>
			    </div>
			  </div>
			  
			  <div class="form-outline mb-4">
			    <div class="form-check form-switch">
				  <input class="form-check-input" type="checkbox" name="professionista" id="flexSwitchCheckDefault">
				  <label class="form-check-label" for="flexSwitchCheckDefault">Iscriviti come professionista</label>
				</div>
			  </div>
			  
			  <div class="row mb-4" id="professionista">
			  	<div class="col">
				  	<input type="text" class="form-control" name="professione" id="professione"/>
				  	<label class="form-label" for="professione">Professione</label>
			  	</div>
			  	<div class="col">
				  <input class="form-control" type="file" id="formFileMultiple" name="file">
				  <label for="formFileMultiple" class="form-label">Multiple files input example</label>
			  	</div>
			  </div>
			  
			  <div class="row mb-3">
			  	<button type="submit" id="submit" class="btn btn-primary btn-block mb-4">Registrati</button>
			  </div>
			  
			</form>
          </div>
        </div>
      </div>
	
	<%@ include file="./footer/footer.html" %>
</body>
</html>