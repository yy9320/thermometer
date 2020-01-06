<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" type="text/css" href="/semantic/dist/semantic.min.css">
<script
  src="https://code.jquery.com/jquery-3.1.1.min.js"
  integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
  crossorigin="anonymous"></script>
<script src="/semantic/dist/semantic.min.js"></script>
	<title>Home</title>
<script type="text/javascript">
	var temperateDate = "${temperateDate}";
	window.onload = function(){
		var rowItem = "";
		for(var i = 0; i < 14; i++){
			var temperate = temperateDate;
			rowItem += "<td>" + temperate + "</td>"
		}
		$(rowItem).appendTo(document.getElementsByClassName("name"));
	}
</script>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<table class="ui celled table">
  <thead>
    <tr>
      <th>Name</th>
      <th>1/1</th>
      <th>1/2</th>
      <th>1/3</th>
      <th>1/4</th>
      <th>1/4</th>
      <th>1/4</th>
      <th>1/4</th>
      <th>1/4</th>
      <th>1/4</th>
      <th>1/4</th>
      <th>1/4</th>
      <th>1/4</th>
      <th>1/4</th>
      <th>1/4</th>
    </tr>
  </thead>
  <tbody>
    <tr class="name">
      <td>John</td>
    </tr>
    <tr class="name">
      <td>John</td>
    </tr>
    <tr class="name">
      <td>John</td>
    </tr>
    <tr class="name">
      <td>John</td>
    </tr>
    <tr class="name">
      <td>John</td>
    </tr>
    <tr class="name">
      <td>John</td>
    </tr>
    <tr class="name">
      <td>John</td>
    </tr>
    <tr class="name">
      <td>John</td>
    </tr>
    <tr class="name">
      <td>John</td>
    </tr>
  </tbody>
</table>
</body>
</html>
