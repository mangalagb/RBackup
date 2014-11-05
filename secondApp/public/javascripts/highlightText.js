
function highlight(i)
{  
	var ti = i;
	
    inputText = document.getElementById("inputText"+ti.toString());
    var innerHTML = inputText.innerHTML;
  
    innerHTML = innerHTML.replace(/StartHighlightPosition/g,"<span class='highlight'>");
    innerHTML = innerHTML.replace(/StopHighlightPosition/g,"</span>");
   // alert(innerHTML);
    
    
    inputText.innerHTML=innerHTML;
   
   
}