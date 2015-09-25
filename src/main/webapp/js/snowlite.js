function doWindowResize(){
  setScreenSize(400,600);
}

function setScreenSize(w,h){
  window.resizeTo(w,h);
}

function doOperation(selectedSection){
	
	if(selectedSection == "#myIncidents"){
		getIncidents();
	}
}

function getIncidents(){
	$.ajax({
		url: "/snowlite/getUserIncident",
        type: "GET",
        success: function(response) {
            console.log(response);        
        }
    });
}

function validateNewIncident(){
	var incRequestedFor = $("#incRequestedFor").val();
	var incDescription = $("#incDescription").val();
	var incEnvironment = $("#incEnvironment").val();
	var incBusinessService = $("#incBusinessService").val();
	
	if(incRequestedFor != "" && incDescription != "" && incEnvironment != "" && incBusinessService != ""){
		$("#newIncidentButton").removeClass("disabled");
	}else{
		$("#newIncidentButton").addClass("disabled");
	}
}

function saveNewIncident(){
	var incRequestedFor = $("#incRequestedFor").val();
	var incDescription = $("#incDescription").val();
	var incEnvironment = $("#incEnvironment").val();
	var incBusinessService = $("#incBusinessService").val();
	var json = { "requestedFor" : incRequestedFor, "description" : incDescription, "businessService": incBusinessService, "environment" : incEnvironment};
	
	$.ajax({
		headers: { 
		    'Accept': 'application/json',
		    'Content-Type': 'application/json' 
		},
        url: "/snowlite/saveIncident",
        data: JSON.stringify(json),
        type: "POST",
        success: function(response) {
            console.log(response);   
            $("#newIncidentForm").reset();
            $('#snl-option').val("myIncidents").selectric('refresh');
            $("#myIncidents").show();
            doOperation("#myIncidents");
        }
    });
	
}