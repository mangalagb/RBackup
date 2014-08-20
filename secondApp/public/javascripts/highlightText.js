RegExp.escape = function(text) {
  return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
};
function highlight(newText,i)
{  
	var ti = i;
	var text = newText;
	
    inputText = document.getElementById("inputText"+ti.toString());
    
   
    var innerHTML = inputText.innerHTML
    //alert(innerHTML);
    var newText = RegExp.escape(text);
   // alert(newText);
    var re = new RegExp(RegExp.escape(text), 'g');
    
    innerHTML = innerHTML.replace(re, "<span class='highlight'>"+text+"</span>");
        //alert(innerHTML);
    inputText.innerHTML=innerHTML;
    
}
