<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="/semantic/dist/semantic.min.css">
<script
  src="https://code.jquery.com/jquery-3.1.1.min.js"
  integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
  crossorigin="anonymous"></script>
<script src="/semantic/dist/semantic.min.js"></script>
<script id="twitter-wjs" src="https://platform.twitter.com/widgets.js"></script>
	<title>Home</title>
<script type="text/javascript">
	window.onload = function(){
		$.ajax({
		    url: "listdata",
		    type: "GET",
		    dataType: "JSON",
		    success: function(data){
		    	var jsonData = data.data;
		    	for(var i=0; i<jsonData.length; i++){
					var rowItem = "<tr>";
		    		var idTd = '<td>' + jsonData[i].id + '</td>';
		    		var selectBox = "";
		    		var avg = 0;
		    		for (var j=0; j<jsonData.length-1; j++){
			    		selectBox += '<td><div class="ui dropdown label temperate" id="date_' + i +'"> '
			   		     + '<div class="text" id="date2_' + i + '">' + jsonData[i].temperature + '</div>'
			   		     + '<i class="dropdown icon"></i>'
			   		     + '<div class="menu">'
			   		     + '<div class="item">1</div>'
			   		     + '<div class="item">2</div>'
			   		     + '<div class="item">3</div>'
			   		     + '<div class="item">4</div>'
			   		     + '<div class="item">5</div></td>';
			   		  	var valueID = "#date2_" + i;
		    			avg = avg + Number(jsonData[i].temperature);
		    		}
		   			rowItem += idTd +  selectBox + "<td>" + avg/(jsonData.length-1) + "</td> </tr>";
					$(rowItem).appendTo(document.getElementsByClassName("tbody"));
		    	}
				$('.ui.dropdown').dropdown({
		            direction:'auto', 
		            duration:100,
		            onChange:function(value, text, $choice){
		            }
		        });
		        $('#target').accordion({exclusive:false});
		    },

		    error: function (request, status, error){        
				alert(status);
		    }

		  });

	}
	
</script>

</head>
<body>
<h1>
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
      <th>AVG</th>
    </tr>
  </thead>
  <tbody class="tbody">
</tbody>
  
</table>
</body>
</html>
