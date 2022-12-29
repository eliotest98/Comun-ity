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

<link href="../styles/registrazione.css" rel="stylesheet" />
<script src="../js/registrazione.js"></script>
</head>
<body>
	<%@ include file="./navbar/navbar.html" %>
	
	<div class="main-wrapper">
        <div id="card" class="shadow p-3 mb-5 bg-white rounded cardcontact">
          <h2 class="card-title text-center">Registrazione</h2>
          <div class="card-body">
            <form>
			  <div class="row mb-4">
			    <div class="col">
			      <div class="form-outline">
			        <input type="text" id="form3Example1" class="form-control" required/>
			        <label class="form-label" for="form3Example1">Nome</label>
			      </div>
			    </div>
			    <div class="col">
			      <div class="form-outline">
			        <input type="text" id="form3Example2" class="form-control" required/>
			        <label class="form-label" for="form3Example2">Cognome</label>
			      </div>
			    </div>
			  </div>
			  
			  <div class="row mb-4">
			  	<div class="col">
				    <input type="email" id="form3Example3" class="form-control" required/>
				    <label class="form-label" for="form3Example3">Email</label>
			    </div>
			    <div class="col">
				    <input type="password" id="form3Example3" class="form-control" required/>
				    <label class="form-label" for="form3Example3">Password</label>
			    </div>
			  </div>
			  
			  <div class="row mb-4">
			  	<div class="col">
				    <input type="tel" id="form3Example3" class="form-control" required/>
				    <label class="form-label" for="form3Example3">Telefono</label>
			    </div>
			    <div class="col">
				    <input type="date" id="form3Example3" class="form-control" required/>
				    <label class="form-label" for="form3Example3">Data di nascita</label>
			    </div>
			  </div>
			  
			  <div class="row mb-4">
			  	<div class="col">
				    <select class="form-select mb-3" aria-label=".form-select-lg example">
					  <option selected>Scegli il tuo sesso</option>
					  <option value="1">M</option>
					  <option value="2">F</option>
					</select>
			    </div>
			    <div class="col">
				    <input type="text" id="form3Example3" class="form-control" required/>
				    <label class="form-label" for="form3Example3">Indirizzo</label>
			    </div>
			  </div>
			  
			  <div class="form-outline mb-4">
			    <div class="form-check form-switch">
				  <input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault">
				  <label class="form-check-label" for="flexSwitchCheckDefault">Iscriviti come professionista</label>
				</div>
			  </div>
			  
			  <div class="row mb-4" id="professionista">
			  	<div class="col">
				  	<input type="text" class="form-control" id="professione"/>
				  	<label class="form-label" for="professione">Professione</label>
			  	</div>
			  	<div class="col">
				  <input class="form-control" type="file" id="formFileMultiple" multiple>
				  <label for="formFileMultiple" class="form-label">Multiple files input example</label>
			  	</div>
			  </div>
			  
			  <div class="row mb-3">
			  	<button type="submit" class="btn btn-primary btn-block mb-4">Registrati</button>
			  </div>
			  
			</form>
          </div>
        </div>
      </div>
	
	<%@ include file="./footer/footer.html" %>
</body>
</html>