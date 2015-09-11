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
		
		var sections = ["#myIncidents", "#newIncident", "#createDBR", "#bridgeRequest", "#awsNonProdDeploy", "#approveIncidents"];
	
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
		  
		  //Dropdown on change
		  snlOptions.on('change', function() {
			  console.log("Value selected: " + $(this).val());
			  var selectedSection = "#" + $(this).val();
			  console.log("Selected section id: " + selectedSection);
			  for(var idx = 0; idx < sections.length; idx++){
				  $(sections[idx]).hide();
			  }
			  $(selectedSection).show();
		  });
		  
		}
		
		function doWindowResize(){
		  setScreenSize(400,600);
		}
		
		function setScreenSize(w,h){
		  window.resizeTo(w,h);
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
					
				</div>
			</div>
			
			<!-- New incidents -->
			<div class="row" id="newIncident" style="display:none;">
				<div class="col-xs-12 col-sm-12">			
					<h5>Create a new incident</h5>
					<table class="table">
						<tbody>
							<tr>
								<td><label>Assignment Group</label></td>
								<td>
									<div class="form-group">
										<input type="text" name="assignmentGroupNI" id="assignmentGroupNI" class="form-control"/>
									</div>
								</td>
							</tr>
							<tr>
								<td><label>Short Description</label></td>
								<td>
									<div class="form-group">
										<input type="text" name="shortDescriptionNI" id="shortDescriptionNI" class="form-control"/>
									</div>
								</td>
							</tr>
							<tr>
								<td><label>Description</label></td>
								<td>
									<div class="form-group">
										<input type="text" name="descriptionNI" id="descriptionNI" class="form-control"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="text-align:center">
									<button class="btn btn-primary">Submit</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<!-- Bridge Request -->
			<div class="row" id="bridgeRequest" style="display:none;">
				<div class="col-xs-12 col-sm-12">			
					<h5>Open a bridge request to discuss production issues</h5>
					<table class="table">
						<tbody>
							<tr>
								<td><label>Incident</label></td>
								<td>
									<div class="form-group">
										<input type="text" name="incidentBR" id="incidentBR" class="form-control"/>
									</div>
								</td>
							</tr>
							<tr>
								<td><label>Description</label></td>
								<td>
									<div class="form-group">
										<input type="text" name="descriptionBR" id="descriptionBR" class="form-control"/>
									</div>
								</td>
							</tr>
							<tr>
								<td><label>Requested People</label></td>
								<td>
									<div class="form-group">
										<input type="text" name="requestedPeopleBR" id="requestedPeopleBR" class="form-control"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="text-align:center">
									<button class="btn btn-primary">Submit</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<!-- Create DBR -->
			<div class="row" id="createDBR" style="display:none;">
				<div class="col-xs-12 col-sm-12">			
					<h5>Raise DBR to get help in Database operations</h5>
					<table class="table">
						<tbody>
							<tr>
								<td><label>Environment</label></td>
								<td>
									<div class="form-group">
										<input type="text" name="environmentDBR" id="environmentDBR" class="form-control"/>
									</div>
								</td>
							</tr>
							<tr>
								<td><label>Short Description</label></td>
								<td>
									<div class="form-group">
										<input type="text" name="shortDescriptionDBR" id="shortDescriptionDBR" class="form-control"/>
									</div>
								</td>
							</tr>
							<tr>
								<td><label>Assignment Group</label></td>
								<td>
									<div class="form-group">
										<input type="text" name="assignmentGroupDBR" id="assignmentGroupDBR" class="form-control"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="text-align:center">
									<button class="btn btn-primary">Submit</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<!-- Approve incidents -->
			<div class="row" id="approveIncidents" style="display:none;">
				<div class="col-xs-12 col-sm-12">	
				
				</div>
			</div>	
			
			<!-- AWS Non Prod Deployment -->
			<div class="row" id="awsNonProdDeploy" style="display:none;">
				<div class="col-xs-12 col-sm-12">			
					<h5>Deploy infrastructure and application to Virtual Private Cloud</h5>
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
								<td><label>Path of archive</label></td>
								<td>
									<div class="form-group">
										<input type="text" name="pathOfArchiveAWS" id="pathOfArchiveAWS" class="form-control"/>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="text-align:center">
									<button class="btn btn-primary">Submit</button>
								</td>
							</tr>
						</tbody>
					</table>
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
