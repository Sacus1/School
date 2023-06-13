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
