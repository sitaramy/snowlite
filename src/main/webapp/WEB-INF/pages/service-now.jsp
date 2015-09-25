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

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/service-now-lite.css" rel="stylesheet">
    <link href="css/selectric.css"  rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	
	<!-- jQuery -->
    <script src="js/jquery.js"></script>
    <script src="js/snowlite.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/selectric.js"></script>

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
			margin-top: 10px;
		}
		
		.preferred{
			color: #6694b8;
		}
		
		.hidden{
			display:none;
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
		                "#sharedFolderAccess", "#unixAccount", "#pendingItems"];
	
		function doOnLoad() {
		  setScreenSize(400,600);
		  window.onresize = doWindowResize;
		  
		  //hide all the divs
		  for(var idx = 0; idx < sections.length; idx++){
			  $(sections[idx]).hide();
		  }
		  
		  //Select my incidents
		  var snlOptions = $('#snl-option').selectric();
		  $('#snl-option').val("myIncidents").selectric('refresh');
		  $("#myIncidents").show();
		  doOperation("#myIncidents");
		  
		  //Dropdown on change
		  snlOptions.on('change', function() {
			  console.log("Value selected: " + $(this).val());
			  var selectedSection = "#" + $(this).val();
			  console.log("Selected section id: " + selectedSection);
			  for(var idx = 0; idx < sections.length; idx++){
				  $(sections[idx]).hide();
			  }
			  $(selectedSection).show();
			  doOperation(selectedSection);
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
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Service Now Lite</a>
                <div class="pull-right" style="margin-top: 15px; margin-right: 2px;">
				  	<span style="color:white">${loggedInUser.name}</span>
			  	</div>
			  	<div class="clearfix"></div>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="#">About</a>
                    </li>
                    <li>
                        <a href="#">Services</a>
                    </li>
                    <li>
                        <a href="#">Contact</a>
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
							<select class="form-control" id="snl-option">
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
					<ul class="nav nav-tabs">
				    	<li class="active"><a href="#">Requests for Approval</a></li>
					    <li><a href="#">Incidents</a></li>
				  </ul>
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
											<input type="text" name="incRequestedFor" id="incRequestedFor" value="${loggedInUser.name}" class="form-control" onblur="validateNewIncident()"/>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Environment</label></td>
									<td>
										<div class="btn-toolbar">
											<div class="btn-group">
									              <button type="button" onclick="populateEnvironment('incEnvironment', 'Test')" data-switch-set="incEnv" data-switch-value="Test" class="btn btn-default">Test</button>
									              <button type="button" onclick="populateEnvironment('incEnvironment', 'Stage')" data-switch-set="incEnv" data-switch-value="Stage" class="btn btn-default">Stage</button>
									              <button type="button" onclick="populateEnvironment('incEnvironment', 'Prod')" data-switch-set="incEnv" data-switch-value="Prod" class="btn btn-default">Prod</button>
								            </div>
										</div>
										<input type="hidden" id="incEnvironment" name="incEnvironment"/>
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
											<select id="incBusinessService" name="incBusinessService" class="form-control" onchange="validateNewIncident()">
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
											<select id="dbRelApplication" name="dbRelApplication" class="form-control">
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Description</label></td>
									<td>
										<div class="form-group">
											<textarea name="dbRelDescription" id="dbRelDescription" rows="2" class="form-control"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button id="newDBRelButton" type="button" class="btn btn-primary disabled">Submit</button>
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
											<select id="dbrRelease" name="dbrRelease" class="form-control">
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Environment</label></td>
									<td>
										<div class="btn-toolbar">
											<div class="btn-group">
									              <button type="button" onclick="populateEnvironment('dbrEnvironment', 'Test')" data-switch-set="dbrEnv" data-switch-value="Test" class="btn btn-default">Test</button>
									              <button type="button" onclick="populateEnvironment('dbrEnvironment', 'Stage')" data-switch-set="dbrEnv" data-switch-value="Stage" class="btn btn-default">Stage</button>
									              <button type="button" onclick="populateEnvironment('dbrEnvironment', 'Prod')" data-switch-set="dbrEnv" data-switch-value="Prod" class="btn btn-default">Prod</button>
								            </div>
										</div>
										<input type="hidden" id="dbrEnvironment" name="dbrEnvironment"/>
									</td>
								</tr>
								<tr>
									<td><label>Description</label></td>
									<td>
										<div class="form-group">
											<textarea name="dbrDescription" id="dbrDescription" rows="2" class="form-control"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button type="button" id="dbrButton" class="btn btn-primary disabled">Submit</button>
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
									<td><label>Incident</label></td>
									<td>
										<div class="form-group">
											<select id="brIncident" name="brIncident" class="form-control">
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Reason</label></td>
									<td>
										<div class="form-group">
											<textarea name="brReason" id="brReason" rows="2" class="form-control"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Requested People</label></td>
									<td>
										<div class="form-group">
											<textarea name="brRequestedPeople" id="brRequestedPeople" rows="2" class="form-control"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button type="button" id="brButton" class="btn btn-primary disabled">Submit</button>
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
											<input type="text" name="vpcNameAWS" id="vpcNameAWS" class="form-control"/>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>App Stack</label></td>
									<td>
										<div class="form-group">
											<input type="text" name="applicationStackAWS" id="applicationStackAWS" class="form-control"/>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Path of Archive</label></td>
									<td>
										<div class="form-group">
											<input type="text" name="pathOfArchiveAWS" id="pathOfArchiveAWS" class="form-control"/>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button type="button" id="awsButton" class="btn btn-primary disabled">Submit</button>
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
											<input type="text" name="saUnixId" id="saUnixId" class="form-control"/>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Admin Group Name</label></td>
									<td>
										<div class="form-group">
											<input type="text" name="saAdminGroup" id="saAdminGroup" class="form-control"/>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button type="button" id="crButton" class="btn btn-primary disabled">Submit</button>
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
											<textarea name="sfDescription" id="sfDescription" rows="2" class="form-control"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<td><label>Access Type</label></td>
									<td>
										<div class="form-group">
											<select id="sfAccessType" name="sfAccessType" class="form-control">
												<option value="Read">Read</option>
												<option value="Write">Write</option>
												<option value="Delete">delete</option>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:center">
										<button type="button" id="sfButton" class="btn btn-primary disabled">Submit</button>
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
										<button type="button" id="unixButton" class="btn btn-primary">Submit</button>
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
				    	<li class="active"><a href="#">Change Request</a></li>
					    <li><a href="#">Other Requests</a></li>
				  </ul>
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
