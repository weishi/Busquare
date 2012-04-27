function success(position) {
  var s = document.querySelector('#locateBtn');
  
  if (s.value == 'Locate nearest busstop') {
    return;
  }
  s.value='Locate nearest busstop';
  
  //Update form fields
  document.querySelector('#geolong').value=position.coords.longitude;
  document.querySelector('#geolat').value=position.coords.latitude;
  document.querySelector('#geoaccu').value=position.coords.accuracy;
  document.querySelector('#locateBtn').disabled=false;
}

function error() {
  var s = document.querySelector('#locateBtn');
  s.value = 'Location not availabe';
}

function load(){
	var infowindow = new google.maps.InfoWindow();
	var latlng;
	if(typeof curStop=="undefined"){
		latlng = new google.maps.LatLng(40.443, -79.942);
	}else{
		latlng = new google.maps.LatLng(curStop.lat, curStop.lng);
	}
	var myOptions = {
	    zoom: 15,
	    center: latlng,
	    mapTypeControl: false,
	    navigationControlOptions: {style: google.maps.NavigationControlStyle.SMALL},
	    mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(document.getElementById("mapcanvas"), myOptions);
	
	//Add report points
	for (var i = 0; i < points.length; i++) {
		  var repPoint=points[i];
		  var content=repPoint.routeName + " posted by " + repPoint.userName;
		  var marker = new google.maps.Marker({
		      position: new google.maps.LatLng(repPoint.lat, repPoint.lng), 
		      map: map, 
		      title: content
		  });
		  
		google.maps.event.addListener(marker, 'click', (function(marker, i) {
        return function() {
          infowindow.setContent(content);
          infowindow.open(map, marker);
        }
      })(marker, i));
	}
	 
	//Add busstop points
	var pinColor = "6699CC";
    var pinImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor,
        new google.maps.Size(21, 34),
        new google.maps.Point(0,0),
        new google.maps.Point(10, 34));
	
	for (var i = 0; i < busStops.length; i++) {
		  var stopPoint=busStops[i];
		  var marker = new google.maps.Marker({
		      position: new google.maps.LatLng(stopPoint.lat, stopPoint.lng), 
		      map: map,
		      icon: pinImage,
		      title: stopPoint.stopName
		  });
		  
		  google.maps.event.addListener(marker, 'click', (function(marker, i) {
        return function() {
          infowindow.setContent(stopPoint.stopName);
          infowindow.open(map, marker);
        }
        })(marker, i));
	}
	if(typeof curStop!="undefined"){
		pinColor = "98BF21";
    	pinImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor,
        new google.maps.Size(21, 34),
        new google.maps.Point(0,0),
        new google.maps.Point(10, 34));
        
		var marker = new google.maps.Marker({
		    position: new google.maps.LatLng(curStop.lat, curStop.lng), 
		    map: map,
		    icon: pinImage,
		    title: curStop.stopName
		});
		
		google.maps.event.addListener(marker, 'click', (function(marker) {
        return function() {
          infowindow.setContent(curStop.stopName);
          infowindow.open(map, marker);
        }
        })(marker));
	}
	if (navigator.geolocation) {
	  navigator.geolocation.getCurrentPosition(success, error);
	} else {
	  error();
	}
}