var baseUrl = "http://localhost:8080/";
var foodTruck = [];
var foodTruckMarker = []; 

var map = L.map('map').setView([45.506467, -73.5717479], 13);

L.tileLayer('http://tuile.lookupia.com/{z}/{x}/{y}.png', {
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors',
        maxZoom: 18
    }).addTo(map);;

var onSearchClick = function() {

	foodTruck = [];
	var dateDebut = document.getElementById("dateDebut").value; 
	var dateFin = document.getElementById("dateFin").value; 

    var req = createRequest(); 
    var requestUrl = baseUrl + "horaires-camions/" + dateDebut + "/" + dateFin; 
    req.onreadystatechange = function() { request
    	if(req.readyState === req.DONE && req.status === 200) handleFoodTruck (req); 
    }
    req.open("GET", requestUrl, true);
    //req.setRequestHeader("Content-Type", "application/json");
    req.send();
}

var handleFoodTruck = function(request){

	removeMarker(); 

	foodTruckList = JSON.parse(request.responseText); 

	alert(foodTruckList.length + " food truck trouvés !");
	console.log(foodTruckList.length + " foodTruck found !"); 

	console.log(foodTruckList);

	for(var i = 0; i < foodTruckList.length; i++){
		var marker = new L.Marker().setLatLng([foodTruckList[i].geometry.coordinates[1], foodTruckList[i].geometry.coordinates[0] ]);
		marker.addTo(map);
		//marker.setIcon(L.icon ( {iconUrl: "images/marker-property.png"})); 
		marker.on('click', onPropertyClick); 
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

var onPropertyClick = function(e){

}


var removeMarker = function(){
	for (var i = foodTruckMarker.length - 1; i >= 0; i--) {
		map.removeLayer(foodTruckMarker[i]); 
	};
	foodTruckMarker = []; 
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