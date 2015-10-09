<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Service Now Lite</title>

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	
	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	
    <!-- Custom CSS -->
    <link href="css/service-now-lite.css" rel="stylesheet">
	<link href="css/bootstrap-select.css" rel="stylesheet">
	
	<!-- jQuery -->
    <script src="js/jquery.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
	<script src="js/snowlite.js"></script>
	<script src="js/bootstrap-select.js"></script>
	
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

	<style>
		body{
			width: 100%;
			height: 100%;
			overflow:hidden; 
			font-size: 11px;
			padding: 0px 0px;
		}
		
		footer {
			position:absolute;
			bottom:0;
			width:100%;
			height:20px;			
			background:#d5d5d5;
			margin:0;
			vertical-align: middle;
			font-size: 10px;
		}
		
		.content{
			margin-top: 65px;
		}
		
		.preferred{
			color: #6694b8;
		}
		
		.hidden{
			display:none;
		}
		
		.descriptionStyle{
			max-width: 70%;
			overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
		}
		
	/*	.navbar-header{
			border-color:#3a5795;
		}
		
		.navbar-inverse{
			background-color:#3a5795;
			border-color:#3a5795;
		}
		
		.navbar-inverse .navbar-brand {
			color: #ebf1dd;
		}
		
		.navbar-inverse .navbar-nav>li>a{
			color: #ffffff;
		}
		
		.navbar-toggle:before{
			background-color:#3a5795;
			border-color:#3a5795;
		}
		
		.navbar-toggle:after{
			background-color:#3a5795;
			border-color:#3a5795;
		}
		
		button .navbar-toggle:before:hover{
			background-color:#3a5795;
			border-color:#3a5795;
		}
	*/	
	</style>
	
	<script>
		
		var sections = ["#myIncidents", "#newIncident", "#createDBR", "#bridgeRequest", "#awsNonProdDeploy", "#sudoAccess", "#newDatabaseRelease", 
		                "#sharedFolderAccess", "#unixAccount", "#pendingItems", "#incidentDetail", '#reqDetail'];
	
		var loggedInUserId = "${loggedInUser.userId}";
		var loggedInUserName = "${loggedInUser.name}";
		
		function doOnLoad() {
			screenDefault();
			window.onresize = doWindowResize;
			  
			  //hide all the divs
			  for(var idx = 0; idx < sections.length; idx++){
				  $(sections[idx]).hide();
			  }
			  
			  //Select my incidents
			  selectSection('myIncidents', true, false);
			  
			  //Dropdown on change
			  $('#snl-option').on('change', function() {
				  console.log("Value selected: " + $(this).val());
				  var selectedSection = "#" + $(this).val();
				  selectSection(selectedSection, false, false);
			  });
			  
		}
		
		function populateEnvironment(elementId, value){
			 $('#' + elementId).val(value);
		}
		
</script>
	
</head>



<body onload="doOnLoad()">

    <!-- Header -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
        	<div class="navbar-header pull-left">
		         <a class="navbar-brand" href="#" title="Service Now Lite">Service Now Lite</a>
		    </div>
		   	<div class="navbar-header pull-right" style="margin-right:10px;">
		   		<ul class="nav navbar-nav">
	   				<li style="float: left">
		   				<a href="#" style="color:white">
				   			${loggedInUser.preferredName}
				   		</a>
		   			</li>
		   		</ul>
		   	</div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>
	<!-- Header -->
	
    <!-- Page Content -->
	<div class="content">
		<div class="container">
			<div class="row">
				<div class="col-xs-12 col-sm-12">
					<div class="form-group">
						<div class="view">
							<select class="selectpicker form-control" data-live-search="true" id="snl-option" data-style="btn-primary">
								<optgroup label="Frequently Used">
									<c:forEach items="${preferredOperations}" var="opr"> 
										<option class="preferred" value="${opr.operationUrl}">${opr.operationName}</option>
									</c:forEach>
								</optgroup>
								<optgroup label="Others">
									<c:forEach items="${allOperations}" var="operation"> 
										<option value="${operation.operationUrl}">${operation.operationName}</option>
									</c:forEach>
								</optgroup>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			<!-- My incidents -->
			<div class="row" id="myIncidents" style="display:none;">
				<div class="col-xs-12 col-sm-12">	
    				<div class="input-group">
	                    <input type="text" id="searchTxtBox" class="form-control" placeholder="Search">
	                    <span class="input-group-btn">
	                        <button class="btn btn-default" onclick="doSearch()">
	                        <span class="glyphicon glyphicon-search"></span>
	                        </button>
	                    </span>
	               	 </div>
	            </div>   
				<div class="col-xs-12 col-sm-12">	
					<table class="table">
							<thead>
								<tr>
									<th style="width:20%">ID</th>
									<th>Description</th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
					</table>					
				</div>
			</div>
			
			<div class="row" id="incidentDetail" style="display:none;">
				<div class="col-sm-12">
						<table class="table">
							<tbody>
								<tr>
									<td colspan="2"><a href="#" onclick="backToList();"><font size="3" class="glyphicon glyphicon-circle-arrow-left"></font></a></td>
								</tr>
								<tr>
									<td width="20%"><label>Incident ID</label></td>
									<td>
										<span id="incidentId"></span>
									</td>
								</tr>
								<tr>
									<td><label>Enviornment</label></td>
									<td>
										<span id="incidentEnv"></span>
									</td>
								</tr>
								<tr>
									<td><label>Description</label></td>
									<td>
										<span id="incidentDesc"></span>
									</td>
								</tr>
								<tr>
									<td><label>Details</label></td>
									<td>
										<span id="incidentDtl"></span>
									</td>
								</tr>
								<tr>
									<td><label>Requested For</label></td>
									<td>
										<span id="incidentReqFor"></span>
									</td>
								</tr>
								<tr>
									<td><label>Business Service</label></td>
									<td>
										<span id="incidentBizSvc"></span>
									</td>
								</tr>
								<tr>
									<td><label>Assignment Group</label></td>
									<td>
										<span id="incidentAsGroup"></span>
									</td>
								</tr>
								<tr>
									<td><label>Status</label></td>
									<td>
										<span id="incidentStatus"></span>
									</td>
								</tr>
							</tbody>
						</table>
				</div>
			</div>
			
			<div class="row" id="reqDetail" style="display:none;">
				<div class="col-xs-12 col-sm-12">	
						<table class="table">
							<tbody>
								<tr>
									<td colspan="2"><a href="#" onclick="backToList();"><font size="3" class="glyphicon glyphicon-circle-arrow-left"></font></a></td>
								</tr>
								<tr>
									<td width="20%"><label>Request ID</label></td>
									<td>
										<span id="requestId"></span>
									</td>
								</tr>
								<tr>
									<td><label>Description</label></td>
									<td>
										<span id="requestDesc"></span>
									</td>
								</tr>
								<tr>
									<td><label>Details</label></td>
									<td>
										<span id="requestDtl"></span>
									</td>
								</tr>
								<tr>
									<td><label>Business Service</label></td>
									<td>
										<span id="requestBizSvc"></span>
									</td>
								</tr>
								<tr>
									<td><label>Assignment Group</label></td>
									<td>
										<span id="requestAsGroup"></span>
									</td>
								</tr>
								<tr>
									<td><label>Status</label></td>
									<td>
										<span id="requestStatus"></span>
									</td>
								</tr>
								<tr>
									<td><label>Approval Req ?</label></td>
									<td>
										<span id="requestApproval"></span>
									</td>
								</tr>
								<tr>
									<td><label>Approval Manager</label></td>
									<td>
										<span id="requestApprovalMgr"></span>
									</td>
								</tr>
							</tbody>
						</table>
				</div>
			</div>
			
			<!-- New incidents -->
			<div class="row" id="newIncident" style="display:none;">
				<div class="col-xs-12 col-sm-12">	
					<form id="newIncidentForm">		
						<table class="table">
							<tbody>
								<tr>
									<td><label>Requested For</label></td>
									<td>
										<div class="form-group">
											<select class="selectpicker form-control" data-live-search="true" name="incRequestedFor" data-size="5" id="incRequestedFor" onchange="validateNewIncident()">
												
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Environment</label></td>
									<td>
										<select id="incEnvironment" name="incEnvironment" class="form-control" onchange="validateNewIncident()">
											<option value="Test">Test</option>
											<option value="Stage">Stage</option>
											<option value="Prod">Prod</option>
										</select>
									</td>
								</tr>
								<tr>
									<td><label>Description</label></td>
									<td>
										<div class="form-group">
											<textarea name="incDescription" id="incDescription" rows="2" class="form-control"  onblur="validateNewIncident()"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Business Service</label></td>
									<td>
										<div class="form-group">
											<select id="incBusinessService" name="incBusinessService" class="selectpicker form-control" data-live-search="true" onchange="validateNewIncident()">
												<c:forEach items="${businessServices}" var="bs"> 
													<option value="${bs.serviceId}">${bs.serviceName}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button type="button" id="newIncidentButton" class="btn btn-primary disabled" onclick="saveNewIncident()">Submit</button>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
			<!-- New Database Release -->
			<div class="row" id="newDatabaseRelease" style="display:none;">
				<div class="col-xs-12 col-sm-12">	
					<form id="newDBRelForm">
						<table class="table">
							<tbody>
								<tr>
									<td><label>Application</label></td>
									<td>
										<div class="form-group">
											<select id="dbRelApplication" name="dbRelApplication" data-size="5" class="selectpicker form-control" data-live-search="true" onchange="validateDBRelease()">
												<c:forEach items="${applications}" var="app"> 
													<option value="${app.applicationId}">${app.applicationName}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Description</label></td>
									<td>
										<div class="form-group">
											<textarea name="dbRelDescription" id="dbRelDescription" rows="2" class="form-control" onblur="validateDBRelease()"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button id="newDBRelButton" type="button" class="btn btn-primary disabled" onclick="saveDBRelease()">Submit</button>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
			<!-- Create DBR -->
			<div class="row" id="createDBR" style="display:none;">
				<div class="col-xs-12 col-sm-12">			
					<form id="dbrForm">
						<table class="table">
							<tbody>
								<tr>
									<td><label>Release</label></td>
									<td>
										<div class="form-group">
											<select id="dbrRelease" name="dbrRelease" class="selectpicker form-control" data-size="5" data-live-search="true" onchange="validateDBRequest()">
											
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Environment</label></td>
									<td>
										<div class="form-group">
											<select id="dbrEnv" name="dbrEnv" class="selectpicker form-control" data-live-search="true" onchange="validateDBRequest()">
												<option value="Test">Test</option>
												<option value="Stage">Stage</option>
												<option value="Prod">Prod</option>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Description</label></td>
									<td>
										<div class="form-group">
											<textarea name="dbrDescription" id="dbrDescription" rows="2" class="form-control"  onblur="validateDBRequest()"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button type="button" id="dbrButton" class="btn btn-primary disabled" onclick="saveDBRequest()">Submit</button>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
			<!-- Bridge Request -->
			<div class="row" id="bridgeRequest" style="display:none;">
				<div class="col-xs-12 col-sm-12">			
					<form id="bridgeReqForm">
						<table class="table">
							<tbody>
								<tr>
									<td><label>Reason</label></td>
									<td>
										<div class="form-group">
											<textarea name="brReason" id="brReason" rows="2" class="form-control" onblur="validateBridgeRequest()"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Requested People</label></td>
									<td>
										<div class="form-group">
											<textarea name="brRequestedPeople" id="brRequestedPeople" rows="2" class="form-control" onblur="validateBridgeRequest()"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button type="button" id="brButton" class="btn btn-primary disabled" onclick="saveBridgeRequest()">Submit</button>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
			<!-- AWS Non Prod Deployment -->
			<div class="row" id="awsNonProdDeploy" style="display:none;">
				<div class="col-xs-12 col-sm-12">			
					<form id="awsDeploymentForm">
						<table class="table">
							<tbody>
								<tr>
									<td><label>VPC Name</label></td>
									<td>
										<div class="form-group">
											<input type="text" name="vpcNameAWS" id="vpcNameAWS" class="form-control" onblur="validateAWSNonProdDeploy()"/>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>App Stack</label></td>
									<td>
										<div class="form-group">
											<input type="text" name="applicationStackAWS" id="applicationStackAWS" class="form-control" onblur="validateAWSNonProdDeploy()"/>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Path of Archive</label></td>
									<td>
										<div class="form-group">
											<input type="text" name="pathOfArchiveAWS" id="pathOfArchiveAWS" class="form-control" onblur="validateAWSNonProdDeploy()"/>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button type="button" id="awsButton" class="btn btn-primary disabled" onclick="saveAWSNonProdDeploy()">Submit</button>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
			<!-- New Change request -->
			<div class="row" id="sudoAccess" style="display:none;">
				<div class="col-xs-12 col-sm-12">			
					<form id="sudoAccessForm">
						<table class="table">
							<tbody>
								<tr>
									<td><label>Unix Id</label></td>
									<td>
										<div class="form-group">
											<input type="text" name="saUnixId" id="saUnixId" class="form-control" onblur="validateSudoAccessRequest()"/>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Admin Group Name</label></td>
									<td>
										<div class="form-group">
											<input type="text" name="saAdminGroup" id="saAdminGroup" class="form-control" onblur="validateSudoAccessRequest()"/>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button type="button" id="saButton" class="btn btn-primary disabled" onclick="saveSudoAccessRequest()">Submit</button>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
			<!-- Shared folder access -->
			<div class="row" id="sharedFolderAccess" style="display:none;">
				<div class="col-xs-12 col-sm-12">			
					<form id="sharedFolderAccessForm">
						<table class="table">
							<tbody>
								<tr>
									<td><label>Description</label></td>
									<td>
										<div class="form-group">
											<textarea name="sfDescription" id="sfDescription" rows="2" class="form-control" onblur="validateSharedFolderAccess()"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Access Type</label></td>
									<td>
										<div class="form-group">
											<select id="sfAccessType" name="sfAccessType" class="selectpicker form-control" data-live-search="true">
												<option value="Read">Read</option>
												<option value="Write">Write</option>
												<option value="Delete">Delete</option>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button type="button" id="sfButton" class="btn btn-primary disabled" onclick="saveSharedFolderAccess()">Submit</button>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
			<!-- unix Account -->
			<div class="row" id="unixAccount" style="display:none;">
				<div class="col-xs-12 col-sm-12">			
					<form id="unixAccountForm">
						<table class="table">
							<tbody>
								<tr>
									<td>
										<label>Please click submit to raise a request for UNIX account.</label>
									</td>
								</tr>
								<tr>
									<td style="text-align:center">
										<button type="button" id="unixButton" class="btn btn-primary" onclick="saveUnixAccountRequest()">Submit</button>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
			<!-- Approve incidents -->
			<div class="row" id="pendingItems" style="display:none;">
				<div class="col-xs-12 col-sm-12">	
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab" href="#changerequest">Change Request</a></li>
						<li><a data-toggle="tab" href="#otherpendingrequests">Requests</a></li>
					</ul>
					<div class="tab-content">
						<div id="changerequest" class="tab-pane fade in active">
							<table class="table">
								<thead>
									<tr>
										<th style="width:20%">Change ID</th>
										<th>Short Description</th>
										<th>Creater</th>
										<th>Approve/Reject</th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>	
						</div>
						<div id="otherpendingrequests" class="tab-pane fade">
							<table class="table">
								<thead>
									<tr>
										<th style="width:20%">ID</th>
										<th>Short Description</th>
										<th>Requester</th>
										<th>Approve/Reject</th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>	
			
			
		</div>
	</div>
    <!-- /.container -->

	<!-- Footer -->
	<footer>
		<div class="row" style="margin-top: 4px;">
			<div class="col-xs-12 col-sm-12" style="text-align: center">
				<span>&copy; Sapient Consulting Ltd 2015</span> | <a href="#">Service Now - Web</a>
			</div>
		</div>
	</footer>
</body>

</html>
