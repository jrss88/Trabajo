var PostCodeid = "#location";
var longval = "#hidLong";
var latval = "#hidLat";
var geocoder;
var map;
var marker;

function initialize() {
    //MAP
    var initialLat = $(latval).val();
    var initialLong = $(longval).val();
    if (initialLat == '') {
        initialLat = "37.1773363";
        initialLong = "-3.5985570999999936";
    }
    var latlng = new google.maps.LatLng(initialLat, initialLong);
    var options = {
        zoom: 16,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        disableDoubleClickZoom: true,
        disableDefaultUI: true,
        keyboardShortcuts: true,
        
        componentRestrictions: {country: "es"}         
    };

    var autocompleteOptions = {
        types: ['geocode']
    };

    map = new google.maps.Map(document.getElementById("geomap"), options);

    geocoder = new google.maps.Geocoder();
    var infowindow = new google.maps.InfoWindow();
    marker = new google.maps.Marker({
        map: map,
        draggable: true,
        position: latlng
    });

    google.maps.event.addListener(marker, "dragend", function(event) {
        var point = marker.getPosition();
        map.panTo(point);
    });

};

$(document).ready(function() {
  
    initialize();

    var options = {
                  componentRestrictions: {country: 'es'}
            };
    var input = document.getElementById('location');
        var autocomplete = new google.maps.places.Autocomplete(input,options);

    $('#findbutton').click(function(e) {
        var address = $(PostCodeid).val();
        geocoder.geocode({
            'address': address,
        }, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                map.setCenter(results[0].geometry.location);
                marker.setPosition(results[0].geometry.location);
                $(latval).val(marker.getPosition().lat());
                $(longval).val(marker.getPosition().lng());
            } else {
                alert("Geocode was not successful for the following reason: " + status);
            }
        });
        e.preventDefault();
    });

    //Add listener to marker for reverse geocoding
    google.maps.event.addListener(marker, 'drag', function() {
        geocoder.geocode({
            'latLng': marker.getPosition()
        }, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if (results[0]) {
                    $(latval).val(marker.getPosition().lat());
                    $(longval).val(marker.getPosition().lng());
                }
            }
        });
    });

});

