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

<!-- AOS-->
<link rel="stylesheet" href="https://unpkg.com/aos@next/dist/aos.css" />

<link href="./styles/index.css" rel="stylesheet"/>

<title>Landing - Comun-ity</title>

</head>
<body>
    <!-- Navbar -->
    <%@ include file="./guest/navbar/navbar.html" %>
    <!-- Caeousel -->
    <section id="carousel">
        <div id="landingCarousel" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#landingCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#landingCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#landingCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
              </div>
            <div class="carousel-inner text-center">
              <div class="carousel-item active">
                <img src="./images/slide1.jpg" class="d-block w-100" alt="Image Description">
                <div class="carousel-caption d-none d-md-block">
                  <h5>Una comunità smart</h5>
                  <p>Comun-ity è la prima comunità smart, nata allo scopo di collegare tutti i cittadini nel modo più facile e veloce possibile.</p>
                  <a class="btn btn-primary" href="/Comun-ity/RegistrazioneServlet">Registrati</a>
                </div>      
              </div>
              <div class="carousel-item">
                <img src="./images/slide2.jpg" class="d-block w-100" alt="POW Logo">
                <div class="carousel-caption d-none d-md-block">
                  <h5 data-aos="fade-down">Richiedi commissioni o lavori</h5>
                  <p data-aos="fade-down" data-aos-duration="1000">Tramite la nostra piattaforma puoi richiedere aiuto per svolgere commissioni o lavori! Sei impossibilitato nel fare qualcosa? Non temere, registrati e cerca qualcuno che lo faccia per te!</p>
                  <a class="btn btn-primary" data-aos-anchor="#back" data-aos="fade-down" data-aos-duration="1200" href="/Comun-ity/RegistrazioneServlet">Registrati</a>
                </div>      
               </div>
               <div class="carousel-item">
                <img src="./images/slide3.jpg" class="d-block w-100" alt="Image Description">
                <div class="carousel-caption d-none d-md-block">
                  <h5  data-aos="fade-down">Comun-ity per Professionisti</h5>
                  <p data-aos="fade-down" data-aos-duration="1000">Registrati come professionista presso la nostra piattaforma. In questo modo potrai accettare lavori della tua area di competenza richiesti da altri cittadini!</p>
                  <a class="btn btn-primary" data-aos="fade-down" data-aos-duration="1200" href="/Comun-ity/RegistrazioneServlet">Registrati</a>
                </div>      
              </div>
              <button class="carousel-control-prev" type="button" data-bs-target="#landingCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" id="back" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
              </button>
              <button class="carousel-control-next" id="forw" type="button" data-bs-target="#landingCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
              </button>
            </div>
          </div>
    </section>
    
    <!-- Progetto-->
    <section id="progetto">
      <div class="container-fluid">
        <h2 class="card-title text-center">Il nostro progetto</h2>
        <h4 class="card-title text-center" style="margin-top: 20px;">Comun-ity è la prima smart comunity nata allo scopo di favorire la collaborazione tra i cittadini!</h4>
        <div id="card" class="shadow p-3 mb-5 bg-white rounded">
          <div class="card-body">
             <div class="row">
                 <div class="col-xxl-4 text-center">
                     <h2 class="card-title text-center" data-aos="fade-down">Migliorare la quotidianità</h2>
                     <hr>
                     <img src="./images/quotidianita.png" data-aos="fade-down">
                     <p data-aos="fade-down">Costruire un sistema di facile utilizzo che permetta ai cittadini di potersi aiutare a vicenda nello svolgere attività quotidiane.</p>
                 </div>
                 <div class="col-xxl-4 text-center">
                     <h2 class="card-title text-center" data-aos="fade-down">Smart communities</h2>
                     <hr>
                     <img src="./images/comunities.png" data-aos="fade-down">
                     <p data-aos="fade-down">Favorire la collaborazione tra cittadini e la creazione di comunità smart.</p>
                 </div>
                 <div class="col-xxl-4 text-center">
                     <h2 class="card-title text-center" data-aos="fade-down">Semplicità</h2>
                     <hr>
                     <img src="./images/semplicita.png" data-aos="fade-down">
                     <p data-aos="fade-down">Semplificazione della ricerca di professionisti qualificati e di cittadini.</p>
                 </div>
             </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Team section--> <!-- TODO aggiungere immagini di tutto il team -->
    <section id="team">
        <h2 class="card-title text-center">Il nostro team</h2>
        <div id="card" class="shadow p-3 mb-5 bg-white rounded">
            <div class="card-body">
              <h3 class="card-title text-center" data-aos="fade-down">Project Managers</h3>
              <div class="row">
                <div class="col-lg-6 text-center">
                  <img src="./images/semplicita.png" data-aos="fade-right">
                  <h4 class="card-title text-center" data-aos="fade-top">Michele Iannucci</h4>
                </div>
                <div class="col-lg-6 text-center" data-aos="fade-down">
                  <img src="./images/semplicita.png" data-aos="fade-left">
                  <h4 class="card-title text-center" data-aos="fade-top">Elio Testa</h4>
                </div>
              </div>
              <hr>
              <h3 class="card-title text-center" data-aos="fade-down">Team Members</h3>
              <div class="row">
                <div class="col-lg-4 text-center">
                  <img src="./images/andrea_aceto.jpg" data-aos="fade-right">
                  <h4 class="card-title text-center" data-aos="fade-top">Andrea Aceto</h4>
                </div>
                <div class="col-lg-4 text-center">
                  <img src="./images/semplicita.png" data-aos="fade-top">
                  <h4 class="card-title text-center" data-aos="fade-top">Biagio Andreucci</h4>
                </div>
                <div class="col-lg-4 text-center">
                  <img src="./images/semplicita.png" data-aos="fade-left">
                  <h4 class="card-title text-center" data-aos="fade-top">Alessandro Falcone</h4>
                </div>
              </div>
              <div class="row">
                <div class="col-lg-6 text-center">
                  <img src="./images/michele-rabesco.jpg" data-aos="fade-left">
                  <h4 class="card-title text-center" data-aos="fade-top">Michele Rabesco</h4>
                </div>
                <div class="col-lg-6 text-center">
                  <img src="./images/gabriele_santoro.jpg" data-aos="fade-right">
                  <h4 class="card-title text-center" data-aos="fade-top">Gabriele Santoro</h4>
                </div>
              </div>
            </div>
        </div>
    </section>
    <!-- Footer-->
    <%@ include file="./guest/footer/footer.html" %>

    <script src="https://unpkg.com/aos@next/dist/aos.js"></script>
    <script src="./js/index.js"></script>
</body>
</html>