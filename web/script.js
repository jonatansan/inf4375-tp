var baseUrl = "http://localhost:8080/";
var foodTruckList = [];
var foodTruckMarker = []; 
var bixiList = [];
var bixiMarker = []; 
var arceauList = [];
var arceauMarker = []; 

var map = L.map('map').setView([45.506467, -73.5717479], 13);

L.tileLayer('http://tuile.lookupia.com/{z}/{x}/{y}.png', {
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors',
        maxZoom: 18
    }).addTo(map);;

var onSearchClick = function() {

	foodTruck = [];
	removeMarkerBixi();
	removeMarkerArceau();
	removeMarkerFoodTruck(); 
	
	var dateDebut = document.getElementById("dateDebut").value; 
	var dateFin = document.getElementById("dateFin").value; 

    var req = createRequest(); 
    var requestUrl = baseUrl + "horaires-camions/" + dateDebut + "/" + dateFin; 
    req.onreadystatechange = function() { request
    	if(req.readyState === req.DONE && req.status === 200) handleFoodTruck (req); 
    	else if(req.readyState === req.DONE && req.status === 400) {
    		alert("Erreur dans le format de date. Entrez les dates sous le format 2000-01-31. ");
    	}
    }
    req.open("GET", requestUrl, true);
    //req.setRequestHeader("Content-Type", "application/json");
    req.send();
}

var handleFoodTruck = function(request){

	removeMarkerFoodTruck(); 

	foodTruckList = JSON.parse(request.responseText); 

	alert(foodTruckList.length + " food truck trouvés !");
	console.log(foodTruckList.length + " foodTruck found !"); 

	console.log(foodTruckList);

	for(var i = 0; i < foodTruckList.length; i++){
		var marker = new L.Marker().setLatLng([foodTruckList[i].geometry.coordinates[1], foodTruckList[i].geometry.coordinates[0] ]);
		marker.addTo(map);
		//marker.setIcon(L.icon ( {iconUrl: "images/marker-property.png"})); 
		marker.on('click', onFoodTruckClick); 
		marker.setIcon(L.icon ( {iconUrl: "truck-icon.png"}));
		marker.bindPopup(
				"Camion: " + foodTruckList[i].properties.Camion +
				"<br>Date: " + foodTruckList[i].properties.Date + 
				"<br>Heure arrivée: " + foodTruckList[i].properties.Heure_debut + 
				"<br>Heure départ: " + foodTruckList[i].properties.Heure_fin + 
				"<br>Emplacement: " + foodTruckList[i].properties.Lieu
			);
		foodTruckMarker.push(marker); 
		marker.id = i; 
	}

}

var onFoodTruckClick = function(e){
	//Fetch Bixi at 200m
	var req = createRequest(); 
    var requestUrl = baseUrl + "bixis?lat=" + e.latlng.lat + "&lng=" + e.latlng.lng; 
    req.onreadystatechange = function() { request
    	if(req.readyState === req.DONE && req.status === 200) handleBixi (req); 
    }
    req.open("GET", requestUrl, true);
    //req.setRequestHeader("Content-Type", "application/json");
    req.send();

    //Fetch arceau
    var req2 = createRequest(); 
    var requestUrl2 = baseUrl + "arceaux?lat=" + e.latlng.lat + "&lng=" + e.latlng.lng; 
    req2.onreadystatechange = function() { request
    	if(req2.readyState === req2.DONE && req2.status === 200) handleArceau (req2); 
    }
    req2.open("GET", requestUrl2, true);
    //req.setRequestHeader("Content-Type", "application/json");
    req2.send();
}

var handleBixi = function(request){
	bixiList = JSON.parse(request.responseText); 
	//console.log(bixiList.length + " bixi found !"); 

	//console.log(bixiList);

	removeMarkerBixi(); 

	for(var i = 0; i < bixiList.length; i++){
		var marker = new L.Marker().setLatLng([bixiList[i].lat, bixiList[i].lng ]);
		marker.addTo(map);

		marker.bindPopup(
			"Nom de la station : " + bixiList[i].name + 
			"<br>Nombre de vélos disponibles : " + bixiList[i].nbBikes +
			"<br>Nombre d'emplacements vides : " + bixiList[i].nbEmptyDocks
		);

		bixiMarker.push(marker); 
	}
}


var handleArceau = function(request){
	arceauList = JSON.parse(request.responseText); 
	removeMarkerArceau(); 

	for (var i = 0; i < arceauList.length; i++) {
		var marker = new L.Marker().setLatLng([arceauList[i].lat, arceauList[i].lng ]);
		marker.addTo(map);
		marker.bindPopup(
			"Un magnifique arceau !"
		);
		marker.setIcon(L.icon ( {iconUrl: "lock.png"}));
		arceauMarker.push(marker); 
	};
}

var removeMarkerFoodTruck = function(){
	for (var i = foodTruckMarker.length - 1; i >= 0; i--) {
		map.removeLayer(foodTruckMarker[i]); 
	};
	foodTruckMarker = []; 
}

var removeMarkerBixi = function(){
	for (var i = bixiMarker.length - 1; i >= 0; i--) {
		map.removeLayer(bixiMarker[i]); 
	};
	bixiMarker = []; 
}

var removeMarkerArceau = function(){
	for (var i = arceauMarker.length - 1; i >= 0; i--) {
		map.removeLayer(arceauMarker[i]); 
	};
	arceauMarker = []; 
}

var createRequest = function() {
	this.request = null; 
    try {
        this.request = new XMLHttpRequest();
    } catch (trymicrosoft) {
        try {
            this.request = new ActiveXObject("MsXML2.XMLHTTP");
        } catch (othermicrosoft) {
            try {
                this.request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (failed) {
                this.request = null;
            }
        }
    }

    if (this.request === null){
        alert("Error creating request object!");
    }
    else { 
    	return this.request; 
    }
}