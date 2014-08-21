package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import models.*;
import play.data.DynamicForm;
import play.data.Form;
import views.html.*;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	
	
    public static Result review(){
    	Form<Function> formData = Form.form(Function.class);
    	String[] functionsToBeProcessed = { "abline", "arrows", "Axis", "axis",
				"axis.POSIXct", "barplot", "box", "boxplot", "bxp", "cdplot",
				"co.intervals", "contour", "coplot", "curve", "dotchart",
				"filled.contour", "grconvertX", "grid", "hist", "identify",
				"image", "layout", "legend", "lines", "locator", "matplot",
				"mosaicplot", "mtext", "pairs", "persp", "pie", "points",
				"polygon", "polypath", "rasterImage", "rect", "rug",
				"segments", "smoothScatter", "spineplot", "stars", "stem",
				"strheight", "stripchart", "sunflowerplot", "symbols", "text",
				"title"};
    	
    	return ok(review.render(formData, functionsToBeProcessed));
    }
    
    public static Result doReview() throws IOException{
    	
    	 // Get the submitted form data from the request object, and run validation.
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
       
    	Function function = new Function();
    	function.functionName = dynamicForm.get("functionName");
    	
    	
    	String path = "./public/functions/allSnippets/" + function.functionName + ".txt";
    	
    	//HashMap<String, String> result = r.returnClusterMembers(function.functionName, path);
    	
    	ReadFile r = new ReadFile();
    	HashMap<SourceCode, List<SourceCode>> result1 = r.returnClusterMembers(function.functionName, path);
    	
    	
    	  
      // return ok(doReview.render(function.functionName, result));
       
       return ok(tempwhat.render(function.functionName, result1));
    	
     }
    
    
    
   
}
