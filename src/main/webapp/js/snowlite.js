var newLineCharacters = "@@nl@@";

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

function show(id){
	for(var idx = 0; idx < sections.length; idx++){
		$(sections[idx]).hide();
	}
	$(id).show();
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


function saveUnixAccountRequest(){
	var description = "Please create a new unix account for me.";
	var businessService = "3";
	var operation = "unixAccount";
	var requestedResource = "New Unix Account";
	var json = {
			"shortDescription": description, 
			"description" : description, 
			"businessService" : businessService, 
			"operationId" : operation,
			"requestedResource" : requestedResource};
	
	$.ajax({
		headers: { 
		    'Accept': 'application/json',
		    'Content-Type': 'application/json' 
		},
        url: "/snowlite/saveRequest",
        data: JSON.stringify(json),
        type: "POST",
        success: function(response) {
            console.log(response);   
            $("#unixAccountForm")[0].reset();
            $('#snl-option').val("myIncidents").selectric('refresh');
            show("#myIncidents");
            doOperation("#myIncidents");
        }
    });
}

function validateSudoAccessRequest(){
	var unixId = $("#saUnixId").val();
	var adminGroupName = $("#saAdminGroup").val();
	
	if(unixId != "" && adminGroupName != ""){
		$("#saButton").removeClass("disabled");
	}else{
		$("#saButton").addClass("disabled");
	}
}

function saveSudoAccessRequest(){
	var shortDescription = "Please provide me sudo access.";
	var businessService = "3";
	var operation = "sudoAccess";
	var requestedResource = "Sudo Access";
	
	var unixId = $("#saUnixId").val();
	var adminGroupName = $("#saAdminGroup").val();
	
	var description = shortDescription + newLineCharacters + "Details are as below: " + newLineCharacters;
	description = description + "Unix ID: " + unixId + newLineCharacters;
	description = description + "Admin Group Name: " + adminGroupName;
	
	var json = {"shortDescription": shortDescription, 
				"description" : description, 
				"businessService" : businessService, 
				"operationId" : operation,
				"requestedResource": requestedResource};
	
	$.ajax({
		headers: { 
		    'Accept': 'application/json',
		    'Content-Type': 'application/json' 
		},
        url: "/snowlite/saveRequest",
        data: JSON.stringify(json),
        type: "POST",
        success: function(response) {
            console.log(response);   
            $("#sudoAccessForm")[0].reset();
            $('#snl-option').val("myIncidents").selectric('refresh');
            show("#myIncidents");
            doOperation("#myIncidents");
        }
    });
}

function validateSharedFolderAccess(){
	var description = $("#sfDescription").val();
	var accessType = $("#sfAccessType").val();
	
	if(description != "" && accessType != ""){
		$("#sfButton").removeClass("disabled");
	}else{
		$("#sfButton").addClass("disabled");
	}
}

function saveSharedFolderAccess(){
	var userDescription = $("#sfDescription").val();
	var accessType = $("#sfAccessType").val();
	
	var businessService = "3";
	var operation = "sharedFolderAccess";
	var requestedResource = "Shared Folder Access";
	
	var description = userDescription + newLineCharacters + "Details are as below: " + newLineCharacters;
	description = description + "Access Type: " + accessType;

	var json = {"shortDescription": userDescription, 
				"description" : description, 
				"businessService" : businessService, 
				"operationId" : operation,
				"requestedResource": requestedResource};
	
	$.ajax({
		headers: { 
		    'Accept': 'application/json',
		    'Content-Type': 'application/json' 
		},
        url: "/snowlite/saveRequest",
        data: JSON.stringify(json),
        type: "POST",
        success: function(response) {
            console.log(response);   
            $("#sharedFolderAccessForm")[0].reset();
            $('#snl-option').val("myIncidents").selectric('refresh');
            show("#myIncidents");
            doOperation("#myIncidents");
        }
    });
}

function validateAWSNonProdDeploy(){
	var vpcName = $("#vpcNameAWS").val();
	var applicationStack = $("#applicationStackAWS").val();
	var pathOfArchive = $("#pathOfArchiveAWS").val();
	
	if(vpcName != "" && applicationStack != "" && pathOfArchive != ""){
		$("#awsButton").removeClass("disabled");
	}else{
		$("#awsButton").addClass("disabled");
	}
}

function saveAWSNonProdDeploy(){
	var vpcName = $("#vpcNameAWS").val();
	var applicationStack = $("#applicationStackAWS").val();
	var pathOfArchive = $("#pathOfArchiveAWS").val();
	
	var shortDescription = "Please make a non-prod AWS deployment.";
	var businessService = "5";
	var operation = "awsNonProdDeploy";
	var requestedResource = "Non Prod AWS deployment";
	
	var description = shortDescription + newLineCharacters + "Details are as below: " + newLineCharacters;
	description = description + "VPC Name: " + vpcName + newLineCharacters;
	description = description + "Application Stack: " + applicationStack + newLineCharacters;
	description = description + "Path of archive: " + pathOfArchive;

	var json = {"shortDescription": shortDescription, 
				"description" : description, 
				"businessService" : businessService, 
				"operationId" : operation,
				"requestedResource": requestedResource};
	
	$.ajax({
		headers: { 
		    'Accept': 'application/json',
		    'Content-Type': 'application/json' 
		},
        url: "/snowlite/saveRequest",
        data: JSON.stringify(json),
        type: "POST",
        success: function(response) {
            console.log(response);   
            $("#awsDeploymentForm")[0].reset();
            $('#snl-option').val("myIncidents").selectric('refresh');
            show("#myIncidents");
            doOperation("#myIncidents");
        }
    });
}

function validateBridgeRequest(){
	var reason = $("#brReason").val();
	var requestedPeople = $("#brRequestedPeople").val();
	
	if(reason != "" && requestedPeople != ""){
		$("#brButton").removeClass("disabled");
	}else{
		$("#brButton").addClass("disabled");
	}
}

function saveBridgeRequest(){
	var reason = $("#brReason").val();
	var requestedPeople = $("#brRequestedPeople").val();
	
	var shortDescription = "Please open an operations bridge for discussing production issue.";
	var businessService = "4";
	var operation = "bridgeRequest";
	var requestedResource = "Operations Bridge Request";
	
	var description = shortDescription + newLineCharacters + "Details are as below: " + newLineCharacters;
	description = description + "Reason: " + reason + newLineCharacters;
	description = description + "Requested People: " + requestedPeople;

	var json = {"shortDescription": shortDescription, 
				"description" : description, 
				"businessService" : businessService, 
				"operationId" : operation,
				"requestedResource": requestedResource};
	
	$.ajax({
		headers: { 
		    'Accept': 'application/json',
		    'Content-Type': 'application/json' 
		},
        url: "/snowlite/saveRequest",
        data: JSON.stringify(json),
        type: "POST",
        success: function(response) {
            console.log(response);   
            $("#bridgeReqForm")[0].reset();
            $('#snl-option').val("myIncidents").selectric('refresh');
            show("#myIncidents");
            doOperation("#myIncidents");
        }
    });
}