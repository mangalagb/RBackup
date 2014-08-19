function highlight(text,i)
{   var ti = i;
    inputText = document.getElementById("inputText"+ti.toString());
   // alert(inputText);
    var innerHTML = inputText.innerHTML
    //alert(innerHTML);
    var re = new RegExp(text, 'g');
    innerHTML = innerHTML.replace(re, "<span class='highlight'>"+text+"</span>");
        //alert(innerHTML);
    inputText.innerHTML=innerHTML;
    
}