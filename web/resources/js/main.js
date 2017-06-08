/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    $(function () {
        initialize();
    });

    var placeSearch, autocomplete;
    var longval = "#hidLong";
    var latval = "#hidLat";

    var componentForm = {
        street_number: 'short_name',
        route: 'long_name',
        locality: 'long_name',
        administrative_area_level_1: 'short_name',
        country: 'long_name',
        postal_code: 'short_name',
    };

    function initialize() {
        var initialLat = $(latval).val();
        var initialLong = $(longval).val();
        if (initialLat == '') {
            initialLat = "37.1773363";
            initialLong = "-3.5985570999999936";
        }
        var latlng = new google.maps.LatLng(initialLat, initialLong);
        // Create the autocomplete object, restricting the search
        // to geographical location types.
        autocomplete = new google.maps.places.Autocomplete(
                /** @type {HTMLInputElement} */(document.getElementById('autocomplete')),
                {types: ['geocode']});
        // When the user selects an address from the dropdown,
        // populate the address fields in the form.
        google.maps.event.addListener(autocomplete, 'place_changed', function () {
            fillInAddress();
        });
    }


    function fillInAddress() {
        // Get the place details from the autocomplete object.
        var place = autocomplete.getPlace();

        for (var component in componentForm) {
            document.getElementById(component).value = "";
            document.getElementById(component).disabled = false;
        }


        var lat = place.geometry.location.lat(),
                lng = place.geometry.location.lng();
        $(latval).val(place.geometry.location.lat());
        $(longval).val(place.geometry.location.lng());

// Then do whatever you want with them

        // Get each component of the address from the place details
        // and fill the corresponding field on the form.
        for (var i = 0; i < place.address_components.length; i++) {
            var addressType = place.address_components[i].types[0];
            if (componentForm[addressType]) {
                var val = place.address_components[i][componentForm[addressType]];
                document.getElementById(addressType).value = val;

            }
        }
    }


// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
    function geolocate() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                var geolocation = new google.maps.LatLng(
                        position.coords.latitude, position.coords.longitude);

                var circle = new google.maps.Circle({
                    center: geolocation,
                    radius: position.coords.accuracy
                });
                autocomplete.setBounds(circle.getBounds());
            });
        }
    }

    //activadores JavaScript para MaterializeCss
    $(".modal-trigger").leanModal({
    });
  
    $(".button-collapse").sideNav();
    $('select').material_select();
    $(".tooltipped").tooltip({delay: 50});
    $('.datepicker').pickadate({
        selectMonths: false, // Creates a dropdown to control month
        selectYears: 2, // Creates a dropdown of 15 years to control year
        format: 'dd/mm/yyyy'


    });
    $(".datepicker").open;


    

//jquery valor valoración para el Rating
    var estrellas
    function verValoracion(event) {

        estrellas = $(this).find('.estrellas').html();
        $('.mostrarestrellas').html(estrellas);
    }
});

function dataMessage(event) {

        console.log("asdfasdfasdfasdfasdfsadf sad fsad fasdf asdf asdfasdf asdf asd f");
        console.log($('.receiver').val());
        
        $(".emaildest").val(
                $('.receiver').val()
                );

    }