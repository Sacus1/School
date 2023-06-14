const url = "https://asterix-dev.micard-family.fr/"

function login(email, pass) {
    // send file to server
    let formData = new URLSearchParams()
    formData.append("email", email);
    formData.append("pass", pass);
    $.ajax({
        url: url + "api/login",
        type: "POST",
        data: formData,
        cache: false,
        contentType: false,
        processData: false
    })
        .done(function(retour) {
            // set retour.token in local storage
            localStorage.setItem("token", retour.token);
        })
        .fail(function(request, message, error) {
            $("#error").html("Erreur de connexion : " + error + " : " + message);
        });
}
let guesses = []
function getGuesses() {
    $.ajax({
        url: url + "api/guesses",
        type: "GET",
        headers: {
            "x-access-token": localStorage.getItem("token")
        },
        cache: false,
        contentType: false,
        processData: false
    })
        .done(function(retour) {
            guesses = retour
            DrawGuess(0)
        })
        .fail(function(request, message, error) {
            $("#error").html("Erreur de connexion : " + error + " : " + message);
        });
}

getGuesses()

function downloadGuessesImages() {
    $.ajax({
        url: url + "api/guesses/images",
        type: "GET",
        headers: {
            "x-access-token": localStorage.getItem("token")
        },
        cache: false,
        contentType: false,
        processData: false
    })
        .done(function(retour) {
            // download zip in retour
            let a = document.createElement("a");
            a.href = "data:application/zip;base64," + retour;
            a.download = "guesses.zip";
            a.click();
        })
        .fail(function(request, message, error) {
            $("#error").html("Erreur de connexion : " + error + " : " + message);
        });
}

function DrawGuess(n){
    let guess = guesses[n]
    console.log(guess)
    // get guess image
    $("#guess-image").attr("src", url+guess.imagepath);
    // set guess-date
    $("#guess-date").html(guess.date);
    // set guess-answer
    $("#guess-answer").html(guess.guess);
    // set win (1), lose (-1) or draw (0)
    $("#guess-result").html(guess.win===1?"Gagn&eacute;":guess.win===-1?"Perdu":"Egalit&eacute;");
    // disable previous button if n == 0
    const previous = $(".guess-previous");
    if (n === 0) {
        previous.prop("disabled", true);
    }
    else {
        previous.prop("disabled", false);
        previous.attr("onclick", "DrawGuess(" + (n-1) + ")");
    }
    // disable next button if n == guesses.length - 1
    const next = $(".guess-next");
    if (n === guesses.length - 1) {
        next.prop("disabled", true);
    }
    else {
        next.prop("disabled", false);
        next.attr("onclick", "DrawGuess(" + (n+1) + ")");

    }
}
