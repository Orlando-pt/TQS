//map.js
//Set up some of our variables.
var map; //Will contain map object.
var marker; ////Has the user plotted their location marker?

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

    marker = new google.maps.Marker({
        position: centerOfMap,
        map: map,
        draggable: true //make it draggable
    });

    marker.addListener('dragend', () => {
        markerLocation();
    });

    map.addListener('click', (event) => {
        var clickedLocation = event.latLng;

        setMarkerLocation(clickedLocation);
        markerLocation();

        updateLocation(clickedLocation);
    })

    markerLocation();
    updateLocation(centerOfMap);
}

async function updateLocation(location) {
    const url = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${location.lat()},${location.lng()}&key=AIzaSyCPzw45SG9x0XQS6cxU6lINfEVSxuJ1g6s`
    const response = await fetch(url, {credentials:'omit'});
    const myJson = await response.json();
    console.log(myJson);

    document.getElementById('input_country').value = myJson['results']['0']['address_components']['3']['long_name'];
    document.getElementById('input_city').value = myJson['results']['0']['address_components']['2']['long_name'];
}

const updateMap = async () => {
    const country = document.getElementById('input_country').value;
    const city = document.getElementById('input_city').value;

    // call google api to make coordinates resolution with given city
    const url = `https://maps.googleapis.com/maps/api/geocode/json?address=${city},%20${country}&key=AIzaSyCPzw45SG9x0XQS6cxU6lINfEVSxuJ1g6s`;

    const response = await fetch(url, {credentials:'omit'});
    const myJson = await response.json();

    const location = myJson['results']['0']['geometry']['location'];

    setMarkerLocation(new google.maps.LatLng(location['lat'], location['lng']));
}

function setMarkerLocation(location) {
    marker.setPosition(location);
    map.panTo(location);

    markerLocation();
}

function markerLocation(){
    //Get location.
    var currentLocation = marker.getPosition();
    //Add lat and lng values to a field that we can save.
    document.getElementById('lat').value = currentLocation.lat(); //latitude
    document.getElementById('lng').value = currentLocation.lng(); //longitude

    // para o form
    document.getElementById('form_latitude').value = currentLocation.lat(); //longitude
    document.getElementById('form_longitude').value = currentLocation.lng(); //longitude
}

google.maps.event.addDomListener(window, 'load', initMap);