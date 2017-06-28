var autocomplete;

function initAutocomplete() {
    autocomplete = new google.maps.places.Autocomplete(
        /** @type {!HTMLInputElement} */(document.getElementById('smartsearch')),
        {types: ['geocode']});
    autocomplete.addListener('place_changed', fillInCoordinate);
}

function fillInCoordinate() {
    var place = autocomplete.getPlace();
    document.getElementById("search-latitude").value = place.geometry.location.lat();
    document.getElementById("search-longitude").value = place.geometry.location.lng();
}

function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var geolocation = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            var circle = new google.maps.Circle({
                center: geolocation,
                radius: position.coords.accuracy
            });
            autocomplete.setBounds(circle.getBounds());
        });
    }
}

$( document ).ready(function() {
    console.log("Init google map search api.")
    initAutocomplete();
});
