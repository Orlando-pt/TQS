//map.js

//Set up some of our variables.
var map; //Will contain map object.
var marker = false; ////Has the user plotted their location marker? 

// para fazer o parsing entre coordenadas e cidades

// https://maps.googleapis.com/maps/api/geocode/json?address=Ovar,%20Portugal&key=AIzaSyCPzw45SG9x0XQS6cxU6lINfEVSxuJ1g6s

// https://maps.googleapis.com/maps/api/geocode/json?latlng=40.8596399,-8.6253313&key=AIzaSyCPzw45SG9x0XQS6cxU6lINfEVSxuJ1g6s

        
//Function called to initialize / create the map.
//This is called when the page has loaded.
function initMap() {

    //The center location of our map.
    var centerOfMap = new google.maps.LatLng(40.61008088007539, -8.667846121607104);

    //Map options.
    var options = {
      center: centerOfMap, //Set center.
      zoom: 7 //The zoom value.
    };

    //Create the map object.
    map = new google.maps.Map(document.getElementById('map'), options);

    //Listen for any clicks on the map.
    google.maps.event.addListener(map, 'click', function(event) {                
        //Get the location that the user clicked.
        var clickedLocation = event.latLng;
        //If the marker hasn't been added.
        if(marker === false){
            //Create the marker.
            marker = new google.maps.Marker({
                position: clickedLocation,
                map: map,
                draggable: true //make it draggable
            });
            //Listen for drag events!
            google.maps.event.addListener(marker, 'dragend', function(event){
                markerLocation();
            });
        } else{
            //Marker has already been added, so just change its location.
            marker.setPosition(clickedLocation);
        }
        //Get the marker's location.
        markerLocation();
    });
}

const updateMap = async () => {
    const country = document.getElementById('input_country').value;
    const city = document.getElementById('input_city').value;

    // call google api to make coordinates resolution with given city
    const url = `https://maps.googleapis.com/maps/api/geocode/json?address=${city},%20${country}&key=AIzaSyCPzw45SG9x0XQS6cxU6lINfEVSxuJ1g6s`;

    const response = await fetch(url, {credentials:'include'});
    const myJson = await response.json();
    console.log(myJson);
}


        
//This function will get the marker's current location and then add the lat/long
//values to our textfields so that we can save the location.
function markerLocation(){
    //Get location.
    var currentLocation = marker.getPosition();
    //Add lat and lng values to a field that we can save.
    document.getElementById('lat').value = currentLocation.lat(); //latitude
    document.getElementById('lng').value = currentLocation.lng(); //longitude
}
        
        
//Load the map when the page has finished loading.
google.maps.event.addDomListener(window, 'load', initMap);