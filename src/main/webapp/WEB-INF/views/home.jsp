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
<script id="twitter-wjs" src="https://platform.twitter.com/widgets.js"></script>
	<title>Home</title>
<script type="text/javascript">
	var temperateDate = "${temperateDate}";
	var userId = "${id}";
	var temperatureList = "${temperatureList}";
	var a = "${temperatureList[0].id}";
	
	window.onload = function(){
		
		$.ajax({
		    url: "listdata",
		    type: "GET",
		    dataType: "JSON",
		    success: function(data){
		    	var jsonData = data.data;
		    	for(var i=0; i<jsonData.length; i++){
		    		
			    	alert(jsonData[i].id);
		    	}
		    },

		    error: function (request, status, error){        
				alert(status);
		    }

		  });

		var rowItem = "";
		for(var i = 0; i < 7; i++){
			var temperate = temperateDate;
			var selectBox = '<div class="ui dropdown label temperate"> '
		     + '<div class="text" id="date_' + i + '">1</div>'
		     + '<i class="dropdown icon"></i>'
		     + '<div class="menu">'
		     + '<div class="item">2</div>'
		     + '<div class="item">3</div>'
		     + '<div class="item">4</div>'
		     + '<div class="item">5</div>';
			rowItem += "<td>" + selectBox + "</td>"
		}
		$(rowItem).appendTo(document.getElementsByClassName("name"));
		$('.ui.dropdown').dropdown({
            direction:'auto', 
            duration:100,
            onChange:function(value, text, $choice){
            }
        });
        $('#target').accordion({exclusive:false});
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
      <th>AVG</th>
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
</tbody>
  
</table>
</body>
 <script>
        $('.ui.dropdown').dropdown({
            direction:'auto', 
            duration:100,
            onChange:function(value, text, $choice){
            }
        });
        $('#target').accordion({exclusive:false});
 
    </script>
</html>
