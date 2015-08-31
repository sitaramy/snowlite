<html>
<head>
<meta charset="UTF-8">
<title>Service Now - Lite</title>
<script src="https://code.jquery.com/jquery-1.11.2.min.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script>
function doOnLoad() {
  //window.opener.close();
  //alert("parent win closed..");
  <!-- defines size of window -->
  ScreenSize(400,600);
  window.onresize = test2;
  
}
function test2(){
  ScreenSize(400,600);
}
function ScreenSize(w,h){
  window.resizeTo(w,h);
  }
</script>

<script type="text/javascript">
    $(function () {
    $("#myModal").modal();
});
</script>
</head>
<body onload="doOnLoad();">
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">How to Use The app...</h4>
      </div>
      <div class="modal-body">
        This is how you would use the app....
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>

</body>
</html>