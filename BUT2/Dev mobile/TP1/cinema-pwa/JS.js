let list_acteurs = [];
let list_acteurs_origin = [];
let list_markers = new Set();

navigator.serviceWorker.register("service-worker.js");

function openActor() {
    {
        const navigator = document.querySelector('#myNavigator');
        navigator.pushPage('acteur.html');
    }
}
function openTicket() {
    {
        const navigator = document.querySelector('#myNavigator');
        navigator.pushPage('ticket.html');
    }
}
async function addStar(number)  {
    let topPage = myNavigator.topPage;
    const id = topPage.data.id_film;
    let mean = topPage.data.note??0;
    let nNotes = (topPage.data.n_notes??0);
    let note = (mean * nNotes + number + 1) / (nNotes+1)
    topPage.data.note = note;
    topPage.data.n_notes += 1;
    // round note to 3 decimals
    note = Math.round(note * 1000) / 1000
    // get id of the stars
    let star = document.querySelector("#page3 #stars");
    star.style.setProperty("--pvote", note/5*100+"%");
    const data = await fetch(`http://127.0.0.1:8002/records/Film/${id}`, {
        method: "PUT",
        body: JSON.stringify({
            "note": note,
            "n_notes": nNotes
        })
    })
    topPage.querySelector(".note").innerHTML = "note de " + note + " sur 5";
    console.log(data)

}
document.addEventListener("init", function (event) {
    const page = event.target;

    if (page.id === "acteur") {
        getPersonnes();
    } else if (page.id === "page2") {
        page.querySelector("ons-toolbar .center").innerHTML = page.data.title;
        getFilms(page.data.id);
    } else if (page.id === "page3") {
        page.querySelector("ons-toolbar .center").innerHTML = page.data.title;
        page.querySelector(".sortie").innerHTML = "sortie en" + page.data.sortie;
        page.querySelector(".duree").innerHTML = "durée de " + Math.floor(page.data.duree / 60) + "h" + page.data.duree % 60;
        page.querySelector(".note").innerHTML = "note de " + page.data.note + " sur 5";
        let star = document.querySelector("#page3 #stars");
        star.style.setProperty("--pvote", page.data.note/5*100+"%");
        page.querySelector(".note").innerHTML = "note de " + page.data.note + " sur 5";

    } else if (page.id === "carte") {
        list_markers = new Set();
        let map = L.map('map', {
            boxZoom: true,
        }).locate({setView: true, maxZoom: 12});
        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(map);
        map.addEventListener("moveend", function () {
            setMarker(map);
        });
        setMarker(map);
    }
    else if (page.id === "ticket") {
        const html5QrCode = new Html5Qrcode("reader",
            {
                formatsToSupport: [Html5QrcodeSupportedFormats.QR_CODE],
            });
        const qrCodeSuccessCallback = (decodedText, decodedResult) => {
            /* handle success */
            console.log(`QR Code detected: ${decodedText}`);
            const qrcode = document.querySelector("#qrcode");
            // use qrious to generate a QR code
            const qr = new QRious({
                element: qrcode,
                value: decodedText,
                size: 250
            });
            // put the QR code in the qrcode div
            qrcode.innerHTML = "";
            qrcode.appendChild(qr.canvas);
        };
        const config = { fps: 10, qrbox: { width: 250, height: 250 } };

        // If you want to prefer front camera
        html5QrCode.start({ facingMode: "user" }, config, qrCodeSuccessCallback);
    }
});
function createLigneActeur(index) {
    // create a new list item element in person list
    // Methode 1 : createElement
    /*
       var newItem = document.createElement("ons-list-item");
    newItem.innerHTML = element.nom;
    newItem.setAttribute("tappable", "");
    newItem.setAttribute("onclick", "showPersonne(" + element.id + ")");
    list.appendChild(newItem);
    */
    // Methode 2 : template
    let element = list_acteurs[index];
    let newItem = document.querySelector("#ligne_personne").content.cloneNode(true);
    newItem.querySelector(".list-item__title").innerHTML = `${element.nom} ${element.prenom}`;
    newItem.querySelector(".naissance").innerHTML = `${
        new Intl.DateTimeFormat("fr-FR", {
            year: "numeric",
            month: "long",
            day: "2-digit",
            weekday: "long",
        }).format(new Date(element.naissance))
    }`;
    if (element.deces)
        newItem.querySelector(".deces").innerHTML = ` - ${
            new Intl.DateTimeFormat("fr-FR", {
                year: "numeric",
                month: "long",
                day: "2-digit",
                weekday: "long",
            }).format(new Date(element.deces))
        }`;
    newItem.querySelector(".nbFilm").innerHTML = element.nbFilm;
    // get image
    const img = `assets/photos/${element.id}.jpg`;
    const photo = new Image();
    photo.src = img;
    const thumbnail = newItem.querySelector(".list-item__thumbnail");
    photo.onload = function () {
        thumbnail.setAttribute("src", img);
    }
    photo.onerror = function () {
        thumbnail.setAttribute("src", "assets/photos/actor_default.jpg");
    }
    newItem.firstElementChild.addEventListener("click", function (evt) {
        const navigator = document.querySelector("#myNavigator");
        navigator.pushPage("page2.html", {
            data: {
                title: element.nom,
                id: element.id
            },
        })
    });
    return newItem.firstElementChild;
}

function sortByNationality() {
    const list = document.querySelector("#acteur ons-lazy-repeat#personne");
    // sort by country
    list_acteurs.sort((a, b) => {
        if (a.nationalite < b.nationalite) return -1;
        if (a.nationalite > b.nationalite) return 1;
        return 0;
    });

    list.refresh();
}

function showDeadOnly() {
    const list = document.querySelector("#acteur ons-lazy-repeat#personne");
    // remove all actors with deces to None
    list_acteurs = list_acteurs_origin.filter((a) => a.deces !== null);
    list.refresh();
}
function neurosama(value) {
    // filter the list of actors with the value of the search bar
    const list = document.querySelector("#acteur ons-lazy-repeat#personne");
    list_acteurs = list_acteurs_origin.filter((a) => a.artiste.toLowerCase().includes(value.toLowerCase()) );
    list.refresh();
}
function limitTo(nb) {
    const list = document.querySelector("#acteur ons-lazy-repeat#personne");
    // remove all actors with deces to None
    list_acteurs = list_acteurs.slice(0, nb);
    list.refresh();
}
async function getPersonnes() {
    const response = await fetch(`http://localhost:8002/records/Acteur`);
    const data = await response.json();
    const list = document.querySelector("#acteur ons-lazy-repeat#personne");
    list_acteurs = data.records;
    list_acteurs_origin = data.records;
    list.delegate = {
        countItems: () => list_acteurs.length,
        createItemContent: createLigneActeur
    }
    sortByNationality();
    showDeadOnly();
    limitTo(30);
}

async function getFilms(id) {
    const response = await fetch(`http://localhost:8002/records/Personne/` + id + `?join=Equipe%2CFilm`);
    const data = await response.json();
    const list = document.querySelector("#page2 ons-list#films");
    for (const equipe of data.Equipe) {
        // create a new list item element in person list
        var newItem = document
            .querySelector("#ligne_film")
            .content.cloneNode(true);
        newItem.querySelector(".list-item__title").innerHTML = equipe.film.titre;
        newItem.querySelector(".role").innerHTML = equipe.role;
        newItem.querySelector(".alias").innerHTML = equipe.alias = "\"\"" ? "" : `(${equipe.alias})`
        newItem.querySelector(".list-item__title").setAttribute("data-annee", equipe.film.annee);
        // get image
        const img = `assets/films/${equipe.film.id}.jpg`;
        const photo = new Image();
        photo.src = img;
        const thumbnail = newItem.querySelector(".image");
        photo.onload = function () {
            thumbnail.setAttribute("src", img);
        }
        newItem.firstElementChild.addEventListener("click", function (evt) {
            const navigator = document.querySelector("#myNavigator");
            navigator.pushPage("page3.html", {
                data: {
                    <!-- Titre , sortie , duree -->
                    title: equipe.film.titre,
                    sortie: equipe.film.sortie,
                    duree: equipe.film.duree,
                    note: equipe.film.note,
                    n_notes: equipe.film.n_notes,
                    id_film: equipe.film.id,
                    id: evt.target.id,
                },
            });
        });
        list.appendChild(newItem);
    }
}

function openMap() {
    {
        const navigator = document.querySelector('#myNavigator');
        navigator.pushPage('carte.html');
    }
}

async function setMarker(map) {
    const b = map.getBounds();
    let bbox = `${b._southWest.lat},${b._southWest.lng},${b._northEast.lat},${b._northEast.lng}`;
    let r = await fetch("http://localhost:8002/geojson/Cinema?bbox=" + bbox);
    const data = await r.json();
    let cinemas = data.features;
    for (let i = 0; i < cinemas.length; i++) {
        if (list_markers.has(cinemas[i].id)) continue;
        let cinema = cinemas[i];
        L.marker([cinema.geometry.coordinates[0], cinema.geometry.coordinates[1]])
            .addTo(map)
            .bindPopup(cinema.properties.nom + "<br>" + cinema.properties.ville + "<br>" + cinema.properties.voie);
        list_markers.add(cinema.id);
    }
}
