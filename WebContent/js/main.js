$(document).ready(function () {
	
	const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
 	const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))

    $('#dashboardSection').show();

	const showNavbar = (toggleId, navId, bodyId, headerId) => {
          const toggle = $('#'+toggleId)[0],
            nav = $('#'+navId)[0],
            bodypd = $('#'+bodyId)[0],
            headerpd = $('#'+headerId)[0];

          // Validate that all variables exist
          if (toggle && nav && bodypd && headerpd) {
            
            toggle.addEventListener("click", () => {
              // show navbar
              nav.classList.toggle("show");
              // change icon
              toggle.classList.toggle("bx-x");
              // add padding to body
              bodypd.classList.toggle("body-pd");
              // add padding to header
              headerpd.classList.toggle("body-pd");
            });
          }
        };
        
        showNavbar("header-toggle", "nav-bar", "body-pd", "header");	
});