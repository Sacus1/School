function changeImage(src) {
    let image = $("#defi-image");
    let send = $("#send");
    image.attr("src", src);
    if (src === "") {
        send.hide();
        image.hide();
    } else {
        send.show();
        image.show();
    }
}

let defi = $('#defi');

function enableCam() {
    // On affiche la webcam
    Webcam.set({
        width: 320,
        height: 240,
        image_format: 'png',
        jpeg_quality: 90
    });
    Webcam.attach('#defi');
    // On affiche le bouton pour prendre la photo
    defi.append('<button id="defi-photo">Prendre la photo</button>');
    // Quand le bouton est cliqué
    $('#defi-photo').click(function () {
        // On prend la photo
        Webcam.snap(function (data_uri) {
            // On affiche la photo
            changeImage(data_uri);
        });
    });
}


function reset() {
    // On affiche le bouton défiez-moi
    $('#defi-button').show();
    // On supprime les autres boutons
    $('#defi-photo').remove();
    $('#defi-file').remove();
    $('#defi-retour').remove();
    // On supprime l’image
    changeImage("");
    // On supprime la webcam
    Webcam.reset();
    // supprime defi-reponse
    $('#defi-reponse').remove();
}

function changeFile() {
    // On récupère la photo
    let file = $('#defi-file')[0].files[0];
    // On la convertit en base64
    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = function () {
        // On affiche la photo
        changeImage(reader.result);
    };
}

// Quand le bouton est cliqué
$('#defi-button').click(function () {
    // Ajoute 2 boutons, prendre une photo ou choisir dans la galerie. Puis remplacer le bouton défiez-moi par retour
    $('#defi-button').hide();
    defi.append('<button id="defi-retour">Retour</button>');
    $('#defi-retour').click(reset);
    defi.append('<button id="defi-photo">Prendre la photo</button>');
    $('#defi-photo').click(enableCam);
    defi.append('<input type="file" id="defi-file" accept="image/png, image/jpeg">');
    $('#defi-file').change(changeFile);
});
// email : obelix@irreductibles.fr
// pass : Pas Besoin de Potion Magique
const url = "https://asterix-dev.micard-family.fr/api/"
$('#send').click(function() {
    console.log("appuyer");
    // send file to server
    let file = $('#defi-file')[0].files[0];
    let formData = new FormData();
    formData.append("guessimage",file);
    $.ajax({
        url:url+"guesses",
        type:"POST",
        data:formData,
        cache:false,
        contentType:false,
        processData:false
    })
        .done(function(retour){
            console.log(retour);
            defi.append('<div id="defi-reponse"> </div>')
            let reponse = $('#defi-reponse');
            // add guess
            reponse.append("<p>"+retour.guess.guess+"</p>");
            // add 3 buttons : Vrai , Faux , ambigu
            reponse.append('<button id="defi-vrai">Vrai</button>');
            reponse.append('<button id="defi-faux">Faux</button>');
            reponse.append('<button id="defi-ambigu">Ambigu</button>');
            // hide send button
            $('#send').hide();
            $('#defi-vrai').click(function() {
                guess(retour.guess.id,1);
            });
            $('#defi-faux').click(function() {
                guess(retour.guess.id,-1);
            });
            $('#defi-ambigu').click(function() {
                guess(retour.guess.id,0);
            });
        })
        .fail(function(request,message,error){
            console.error(error);
        });
})

function guess(id, answer) {
    // 1 = AI won, 0 = Stalemate, -1 = lose
    console.log("appuyer");
    // send file to server
    let formData = new FormData();
    formData.append("answer", answer);
    $.ajax({
        url: url + "guesses/" + id,
        type: "PUT",
        cache: false,
        contentType: false,
        processData: false
    })
        .done(function(retour) {
            console.log(retour);
            reset();
        })
        .fail(function(request, message, error) {
            console.error(error);
        });
}
