navigator.serviceWorker.register("service-worker.js");
function showMap() {
    const navigator = document.querySelector('#myNavigator');
    navigator.pushPage('carte.html');
}
let list_participant = [];
document.addEventListener("init", async function (event) {
    const page = event.target;

    if (page.id === "carte") {
        list_markers = new Set();
        let map = L.map('map', {
            boxZoom: true,
        }).locate({setView: true, maxZoom: 12});
        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(map);
        // get geojson data from the assets folder
        const data = await fetch("assets/16.geojson")
        L.geoJSON(await data.json(), {
            onEachFeature: function (feature, layer) {
                layer.bindPopup(feature.properties.name);
                list_markers.add(layer);
            }
        }).addTo(map);
    }else if (page.id == "participant"){
        const response = await fetch(`http://localhost:8002/records/Participant`);
        const data = await response.json();
        const list = document.querySelector("#participant ons-lazy-repeat#personne");
        list_participant = data.records;
        list_participant.sort((a, b) => a.nom.localeCompare(b.nom));
        // split into multiple arrays by nationality
        let list_participant_nationalite = {};
        list_participant.forEach(element => {
            if (list_participant_nationalite[element.nationalite] === undefined) {
                list_participant_nationalite[element.nationalite] = [];
            }
            list_participant_nationalite[element.nationalite].push(element);
        });
        // create an <ons-list-title> for each nationality
        for (const [key, value] of Object.entries(list_participant_nationalite)) {
            let header = document.createElement("ons-list-header");
            header.innerHTML = key;
            header.setAttribute("id", key);
            // add image in assets/flags
            const img = `assets/flags/${key}.png`;
            const photo = new Image();
            photo.src = img;
            const thumbnail = document.createElement("img");
            photo.onload = function () {
                thumbnail.setAttribute("src", img);
            }
            photo.onerror = function () {

            }
            header.appendChild(thumbnail);
            list.appendChild(header);
        }
        // add each person to the list
        list_participant.forEach(element => {
            let newItem = document.querySelector("#ligne_participant").content.cloneNode(true);
            newItem.querySelector(".list-item__title").innerHTML = `${element.nom}`;
            newItem.querySelector(".list-item__subtitle").innerHTML = `${element.temps}`;
            // get image
            const img = `assets/profils/${element.id}.png`;
            const photo = new Image();
            photo.src = img;
            const thumbnail = newItem.querySelector(".list-item__thumbnail");
            photo.onload = function () {
                thumbnail.setAttribute("src", img);
            }
            photo.onerror = function () {
                thumbnail.setAttribute("src", "assets/photos/actor_default.jpg");
            }
            // add the new item to the list in the nationality section
            list.querySelector(`#${element.nationalite}`).appendChild(newItem);
        });
    }
    else if (page.id="qrcode"){
        const html5QrCode = new Html5Qrcode("reader",
            {
                formatsToSupport: [Html5QrcodeSupportedFormats.AZTEC],
            });
        const qrCodeSuccessCallback = (decodedText, decodedResult) => {
            console.log(`QR Code detected: ${decodedText}`)
            let response = $.post(
                "http://localhost:8002/records/Controles",
                {
                    "participant": 1,
                    "point": decodedText,
                    "horodatage": new Date().toISOString().slice(0, 19).replace('T', ' ')
                },
            )
            response.done(function (data) {
                console.log(data);
            });
        };
        const config = { fps: 10, qrbox: { width: 250, height: 250 } };

        // If you want to prefer front camera
        html5QrCode.start({ facingMode: "user" }, config, qrCodeSuccessCallback);
    }
});
