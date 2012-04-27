function success(position) {
  var s = document.querySelector('#status');
  
  if (s.className == 'success') {
    return;
  }
   
  var latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
  var myOptions = {
    zoom: 15,
    center: latlng,
    mapTypeControl: false,
    navigationControlOptions: {style: google.maps.NavigationControlStyle.SMALL},
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  var map = new google.maps.Map(document.getElementById("mapcanvas"), myOptions);
  
  var marker = new google.maps.Marker({
      position: latlng, 
      map: map, 
      title:"You are here! (within a "+position.coords.accuracy+" meter radius)"
  });
  var message="("+position.coords.latitude+","+position.coords.longitude+") within "+position.coords.accuracy+"m";
  s.innerHTML = "You are at "+message;
  s.className = 'success';
  
  //Update form fields
  document.querySelector('#geolong').value=position.coords.longitude;
  document.querySelector('#geolat').value=position.coords.latitude;
  document.querySelector('#geoaccu').value=position.coords.accuracy;
  document.querySelector('#reportBtn').visibility=visible;
}

function error(msg) {
  var s = document.querySelector('#reportBtn');
  s.value = 'Location not availabe';

}

if (navigator.geolocation) {
  navigator.geolocation.getCurrentPosition(success, error);
} else {
  error('not supported');
}