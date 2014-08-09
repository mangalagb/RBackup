package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;
import play.data.DynamicForm;
import play.data.Form;
import views.html.*;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	
	
    public static Result review(){
    	Form<Function> formData = Form.form(Function.class);
    	String[] functionsToBeProcessed = { "Axis", "persp" };
    	
    	return ok(review.render(formData, functionsToBeProcessed));
    }
    
    public static Result doReview() throws IOException{
    	
    	 // Get the submitted form data from the request object, and run validation.
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
       
    	Function function = new Function();
    	function.functionName = dynamicForm.get("functionName");
    	
    	//ReadFile r = new ReadFile();
    	String path = "./public/functions/allSnippets/" + function.functionName + "CodeSnippets.txt";
    	
    	//HashMap<String, String> result = r.returnClusterMembers(function.functionName, path);
    	
    	temp t = new temp();
    	HashMap<SourceCode, List<SourceCode>> result1 = t.returnClusterMembers(function.functionName, path);
    	
    	
    	  
      // return ok(doReview.render(function.functionName, result));
       
       return ok(tempwhat.render(function.functionName, result1));
    	
     }
    
   
}
