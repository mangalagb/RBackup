
function highlight(startHighlight,stopHighlight,i)
{  
	var ti = i;
	
	//var stopHighlight = "(";
	
	//alert(startHighlight);
	 //var index = innerHTML.indexOf(text);
	
	
	
    inputText = document.getElementById("inputText"+ti.toString());
    var innerHTML = inputText.innerHTML;
    
    var startIndex = innerHTML.indexOf(startHighlight);
    var stopIndex = innerHTML.indexOf(stopHighlight, startHighlight);
    
   // alert(startIndex);
	//alert(stopIndex);
   // alert( innerHTML.match( startHighlight ) )  
    
	if(stopIndex >= startIndex)
	   {
	   innerHTML = innerHTML.substring(0,startIndex) + "<span class='highlight'>" 
	   + innerHTML.substring(startIndex,stopIndex)
	   + "</span>" + innerHTML.substring(stopIndex);
	   }

	inputText.innerHTML=innerHTML;
   
   
}