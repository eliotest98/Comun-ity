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

<link href="../styles/login.css" rel="stylesheet" />
<link href="../styles/navbar.css" rel="stylesheet" />
<title>Login - Comun-ity</title>
</head>
<body>
    <%@ include file="../navbar/navbar.html" %>
      <div class="main-wrapper">
        <div id="card" class="shadow p-3 mb-5 bg-white rounded cardcontact">
          <h2 class="card-title text-center">Login</h2>
          <div class="card-body">
            <form>
              <div class="mb-3">
                <label for="exampleInputEmail1" class="form-label">Email address</label>
                <input type="email" placeholder="name@example.com" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" required>
              </div>
              <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label">Password</label>
                <input type="password" placeholder="********" class="form-control" id="exampleInputPassword1" aria-describedby="passwordHelp">
                <a id="passwordHelp" class="form-text" href="#">Password dimenticata?</a>
              </div>
              <div class="row">
              <div class="col-xxl-8">
                <button type="submit" class="btn btn-primary">Login</button>
              </div>
              <div class="col-xxl-4 text-end">
                <a href="#">Non hai un account?</a>
              </div>
            </div>
            </form>
          </div>
        </div>
      </div>
    <%@ include file="../footer/footer.html" %>
</body>
</html>