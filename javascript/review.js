function load(){
	var infowindow = new google.maps.InfoWindow();
	var latlng;
	if(typeof curRep=="undefined"){
		latlng = new google.maps.LatLng(40.443, -79.942);
	}else{
		latlng = new google.maps.LatLng(curRep.lat, curRep.lng);
	}
	var myOptions = {
	    zoom: 15,
	    center: latlng,
	    mapTypeControl: false,
	    navigationControlOptions: {style: google.maps.NavigationControlStyle.SMALL},
	    mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(document.getElementById("mapcanvas"), myOptions);
	
	//Add cur report
	var marker = new google.maps.Marker({
	    position: new google.maps.LatLng(curRep.lat, curRep.lng), 
	    map: map, 
	    title: curRep.routeName + " posted by " + curRep.userName
	});
	
	google.maps.event.addListener(marker, 'click', (function(marker) {
        return function() {
          infowindow.setContent(curRep.routeName + " posted by " + curRep.userName);
          infowindow.open(map, marker);
        }
    })(marker));
	
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

}