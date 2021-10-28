(function() => {

    "use strict";

    const form = document.getElementById("form");

    form.addEventListener("submit", (event) => {
        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }
    form.classList.add("was-validated");
    }, false);
}())

