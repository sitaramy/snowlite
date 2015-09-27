var newLineCharacters = "@@nl@@";

function doWindowResize(){
  screenDefault();
}

function setScreenSize(w,h){
  window.resizeTo(w,h);
  window.moveTo(screen.width-w, 0);
  window.focus();
}

function screenDefault() {
	var height = screen.height;
	var width = screen.width;
	setScreenSize((width/3), height-(height/20));
}

function selectSection(section, doDropdownSelect){
	console.log("calling select section for " + section + " with dropdownselect as " + doDropdownSelect);
	if(doDropdownSelect){
		var sc = section;
		$('#snl-option').selectpicker('val', sc);
		section = "#" + section;
	}
	show(section);
	doOperation(section);
}

function doOperation(selectedSection){
	
	if(selectedSection == "#myIncidents"){
		getIncidents();
	}
	
	else if(selectedSection == "#newIncident"){
		getUsers();
	}
	
	else if(selectedSection == "#createDBR"){
		getDBReleases();
	}
	
	else if(selectedSection == "#pendingItems") {
		getOtherPendingRequests();
	}
}

function getIncidents(){
	$.ajax({
		url: "/snowlite/getUserIncidents",
        type: "GET",
        dataType: "json",
        success: function(response) {
        	$('#myIncidents tbody').empty();
        	if(response != null && response != undefined){
            	for (index = 0; index < response.length; index++) { 
            		var id = "" + response[index].id;
            		var prefix = response[index].displayPrefix;
            		var customId = "" + prefix + id;
            		var rowContent = "<tr>";
            		rowContent = rowContent + '<td style="width:20%"><a href="javascript:;" onclick="getTaskDescription(\'' + id + '\', \'' + prefix + '\')">' + customId + '</a></td>';
            		rowContent = rowContent + '<td>' + response[index].shortDescription + '</td>';
            		rowContent = rowContent + '</tr>';
            		$('#myIncidents tbody').append(rowContent);
            	}
            }   
        	
        }
    });
}

function getUsers(){
	$.ajax({
		url: "/snowlite/users",
        type: "GET",
        dataType: "json",
        success: function(response) {
        	if(response != null && response != undefined){
            	var select = document.getElementById("incRequestedFor");
            	select.options.length = 0;
            	for (index = 0; index < response.length; index++) { 
            		opt = document.createElement("option");
            		opt.value = response[index].name;
            		opt.textContent = response[index].name;
            		select.appendChild(opt);
            	}
            	$('#incRequestedFor').selectpicker('refresh');
            	$('#incRequestedFor').selectpicker('val', loggedInUserName);
            }  
        }
    });
}

function doSearch(){
	var searchText = $("searchTxtBox").val();
	if(searchText == ""){
		return;
	}
	alert("Text entered is: " + searchText);
	return false;
}

function getTaskDescription(taskId, taskType){
	console.log("Fetching task description...");
	taskType = "" + taskType;
	var url = "/snowlite/"
	if(taskType == "REQ"){
		url = url + "request/";
	}
	else{
		url = url + "incident/";
	}
	url = url + taskId;
	
	$.ajax({
		url: url,
        type: "GET",
        dataType: "json",
        success: function(response) {
            console.log(response); 
        }
    });
}

function getDBReleases(){
	$.ajax({
		url: "/snowlite/getDBReleases",
        type: "GET",
        dataType: "json",
        success: function(response) {
            if(response != null && response != undefined){
            	var select = document.getElementById("dbrRelease");
            	select.options.length = 0;
            	for (index = 0; index < response.length; index++) { 
            		opt = document.createElement("option");
            		opt.value = response[index].releaseId;
            		opt.textContent = "" + response[index].displayPrefix + response[index].releaseId;
            		select.appendChild(opt);
            	}
            	$('#dbrRelease').selectpicker('refresh');
            }        
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
	var operation = "newIncident";
	var incRequestedFor = $("#incRequestedFor").val();
	var incDescription = $("#incDescription").val();
	var incEnvironment = $("#incEnvironment").val();
	var incBusinessService = $("#incBusinessService").val();
	var json = { "requestedFor" : incRequestedFor, 
					"shortDescription": incDescription, 
					"description" : incDescription, 
					"businessService": incBusinessService, 
					"environment" : incEnvironment,
					"operationId" : operation};
	
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
            $("#newIncidentForm")[0].reset();
            selectSection('myIncidents', true);
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
            selectSection('myIncidents', true);
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
            selectSection('myIncidents', true);
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
            selectSection('myIncidents', true);
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
            selectSection('myIncidents', true);
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
            selectSection('myIncidents', true);
        }
    });
}

function validateDBRelease(){
	var application = $("#dbRelApplication").val();
	var description = $("#dbRelDescription").val();
	
	if(application != "" && description != ""){
		$("#newDBRelButton").removeClass("disabled");
	}else{
		$("#newDBRelButton").addClass("disabled");
	}
}

function saveDBRelease(){
	var application = $("#dbRelApplication").val();
	var description = $("#dbRelDescription").val();
	
	var businessService = "7";
	var operation = "newDatabaseRelease";
	var requestedResource = "New Database Release";
	
	var json = {
				"description" : description, 
				"applicationId" : application, 
				"operationId" : operation,
				"businessService" : businessService, 
				"requestedResource": requestedResource};
	
	$.ajax({
		headers: { 
		    'Accept': 'application/json',
		    'Content-Type': 'application/json' 
		},
        url: "/snowlite/saveDBRelease",
        data: JSON.stringify(json),
        type: "POST",
        success: function(response) {
            console.log(response);   
            $("#newDBRelForm")[0].reset();
            selectSection('myIncidents', true);
        }
    });
}

function validateDBRequest(){
	var dbrRelease = $("#dbrRelease").val();
	var dbrEnv = $("#dbrEnv").val();
	var dbrDescription = $("#dbrDescription").val();
	
	if(dbrRelease != "" && dbrEnv != "" && dbrDescription != ""){
		$("#dbrButton").removeClass("disabled");
	}else{
		$("#dbrButton").addClass("disabled");
	}
}

function saveDBRequest(){
	var dbrRelease = $("#dbrRelease").val();
	var dbrEnv = $("#dbrEnv").val();
	var dbrDescription = $("#dbrDescription").val();
	
	var businessService = "7";
	var operation = "createDBR";
	var requestedResource = "New Database Request";
	
	var json = {
				"description" : dbrDescription, 
				"environment" : dbrEnv, 
				"releaseId" : dbrRelease, 
				"operationId" : operation,
				"businessService" : businessService, 
				"requestedResource": requestedResource};
	
	$.ajax({
		headers: { 
		    'Accept': 'application/json',
		    'Content-Type': 'application/json' 
		},
        url: "/snowlite/saveDBRequest",
        data: JSON.stringify(json),
        type: "POST",
        success: function(response) {
            console.log(response);   
            $("#newDBRelForm")[0].reset();
            selectSection('myIncidents', true);
        }
    });
}

function getOtherPendingRequests(){
	$.ajax({
		url: "/snowlite/getPendingRequests?type=others",
        type: "GET",
        dataType: "json",
        success: function(response) {
        	$('#otherpendingrequests tbody').empty();
        	if(response != null && response != undefined){
            	for (index = 0; index < response.length; index++) { 
            		var id = "" + response[index].id;
            		var prefix = response[index].displayPrefix;
            		var customId = "" + prefix + id;
            		var rowContent = "<tr>";
            		rowContent = rowContent + '<td>' + customId + '</td>';
            		rowContent = rowContent + '<td>' + response[index].shortDescription + '</td>';
            		rowContent = rowContent + '<td>' + response[index].user.name + '</td>';
            		rowContent = rowContent + '<td style="width:20%;"> <button type="button" onclick="updateOthPendAppvlStatus(\'' + id + '\', \'Y\')" class="btn btn-primary btn-xs btn btn-success"><i class="glyphicon glyphicon-ok"></i></button>  <button type="button" onclick="updateOthPendAppvlStatus(\'' + id + '\', \'N\')" class="btn btn-primary btn-xs btn btn-danger"><i class="glyphicon glyphicon-remove"></i></button></td>';            		
            		rowContent = rowContent + '</tr>';
            		$('#otherpendingrequests tbody').append(rowContent);
            	}
            }   
        	
        }
    });
}

function updateOthPendAppvlStatus (id, status) {
	var _url = "/snowlite/updateApprovalStatus?requestid="+id+"&status="+status;
	$.ajax({
	    url: _url,
	    type: "GET",
	    dataType: "json",
	    success: function(response) {
	        console.log(response);   
	        getOtherPendingRequests();
	    }
	});
}