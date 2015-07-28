function initialize() 
{
    var mapOptions = 
    {
        center : new google.maps.LatLng(37.09024, -95.712891),
        zoom : 3
    };
    var map = new google.maps.Map(document.getElementById("map-canvas"),
            mapOptions);
}
google.maps.event.addDomListener(window, 'load', initialize);