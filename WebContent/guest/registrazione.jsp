<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrati - Comun-ity</title>
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

<link href="${pageContext.request.contextPath}/styles/registrazione.css"
	rel="stylesheet" />
<script src="${pageContext.request.contextPath}/js/registrazione.js"></script>

<script async
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD1-j4UoyADZnRxieozZUOUHmX5CXOCcaI&libraries=places">
</script>
<script type="text/javascript">
  function initAutocomplete() {
    var address = document.getElementById('address');
    var autocomplete = new google.maps.places.Autocomplete(address);
  }
	let autocomplete;
	let address1Field;

function initAutocomplete() {
  address1Field = document.querySelector("#ship-address");
  // Create the autocomplete object, restricting the search predictions to
  // addresses in the US and Canada.
  autocomplete = new google.maps.places.Autocomplete(address1Field, {
    componentRestrictions: { country: ["us", "ca"] },
    fields: ["address_components", "geometry"],
    types: ["address"],
  });
  address1Field.focus();
  // When the user selects an address from the drop-down, populate the
  // address fields in the form.
  autocomplete.addListener("place_changed", fillInAddress);
}

	function fillInAddress() {
	  // Get the place details from the autocomplete object.
	  const place = autocomplete.getPlace();
	  let address1 = "";
	  let postcode = "";
	
	  for (const component of place.address_components) {
	    // @ts-ignore remove once typings fixed
	    const componentType = component.types[0];
	
	    switch (componentType) {
	      case "street_number": {
	        address1 = `${component.long_name} ${address1}`;
	        break;
	      }
	
	      case "route": {
	        address1 += component.short_name;
	        break;
	      }
	
	      case "postal_code": {
	        postcode = `${component.long_name}${postcode}`;
	        break;
	      }
	
	      case "postal_code_suffix": {
	        postcode = `${postcode}-${component.long_name}`;
	        break;
	      }
	      case "locality":
	        document.querySelector("#locality").value = component.long_name;
	        break;
	      case "administrative_area_level_1": {
	        document.querySelector("#state").value = component.short_name;
	        break;
	      }
	      case "country":
	        document.querySelector("#country").value = component.long_name;
	        break;
	    }
	  }
	
	  address1Field.value = address1;
	  postalField.value = postcode;
	}
	
	window.initAutocomplete = initAutocomplete;
</script>

</head>
<body>
	<%@ include file="./navbar/navbar.html"%>
	<%
	if (request.getAttribute("message") != null) {
	%>
	<div id="message">
		<div class="alert alert-danger alert-dismissible fade show"
			role="alert">
			<strong>Errore</strong>
			<%=request.getAttribute("message")%>.
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
	</div>
	<%}%>
	<div class="main-wrapper">
		<div id="card" class="shadow p-3 mb-5 bg-white rounded cardcontact">
			<h2 class="card-title text-center">Registrazione</h2>
			<div class="card-body">
				<form id="registrazione"
					action="${pageContext.request.contextPath}/RegistrazioneServlet"
					method="post" enctype='multipart/form-data'>
					<div class="row mb-4">
						<div class="col">
							<div class="form-outline">
								<input type="text" id="nome" name="nome" class="form-control"
									required /> <label class="form-label" for="nome">Nome</label>
							</div>
						</div>
						<div class="col">
							<div class="form-outline">
								<input type="text" id="cognome" name="cognome"
									class="form-control" required /> <label class="form-label"
									for="cognome">Cognome</label>
							</div>
						</div>
					</div>

					<div class="row mb-4">
						<div class="col">
							<input type="email" id="email" name="email" class="form-control"
								required /> <label class="form-label" for="email">Email</label>
						</div>
						<div class="col">
							<input type="password" id="password" name="password"
								placeholder="********" class="form-control" minlength="8"
								maxlength="20" required /> <label class="form-label"
								for="password">Password</label>
						</div>
					</div>

					<div class="row mb-4">
						<div class="col">
							<input type="tel" id="telefono" name="telefono"
								class="form-control" required /> <label class="form-label"
								for="telefono">Telefono</label>
						</div>
						<div class="col">
							<input type="date" id="nascita" name="data" class="form-control"
								required /> <label class="form-label" for="nascita">Data
								di nascita</label>
						</div>
					</div>

					<div class="row mb-4">
						<div class="col">
							<select class="form-select mb-3" name="sesso"
								aria-label=".form-select-lg example">
								<option selected>Scegli il tuo sesso</option>
								<option value="M">M</option>
								<option value="F">F</option>
							</select>
						</div>
						<div class="col">
							<input id="ship-address" class="form-control" name="indirizzo"
								placeholder="Inserisci un indirizzo" required autocomplete="off" />
							<label class="form-label" for="form3Example3">Indirizzo</label>
						</div>
					</div>

					<div class="form-outline mb-4">
						<div class="form-check form-switch">
							<input class="form-check-input" type="checkbox"
								name="professionista" id="flexSwitchCheckDefault"> <label
								class="form-check-label" for="flexSwitchCheckDefault">Iscriviti
								come professionista</label>
						</div>
					</div>

					<div class="row mb-4" id="professionista">
						<div class="col">
							<input type="text" class="form-control" name="professione"
								id="professione" /> <label class="form-label" for="professione">Professione</label>
						</div>
						<div class="col">
							<input class="form-control" type="file" id="formFileMultiple"
								name="file">
						</div>
					</div>

					<div class="row mb-3">
						<button type="submit" id="submit"
							class="btn btn-primary btn-block mb-4">Registrati</button>
					</div>

				</form>
			</div>
		</div>
	</div>
	<%@ include file="./footer/footer.html"%>
</body>
</html>