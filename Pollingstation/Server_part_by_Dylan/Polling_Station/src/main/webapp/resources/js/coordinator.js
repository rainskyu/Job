function deleteQuestion(questionNumber)
{
	eleToRemove = document.getElementById(questionNumber+"group");	
	eleToRemove.parentNode.removeChild(eleToRemove);
	var questions = parseInt(document.forms['myform'].elements['questions'].value )-1;
	questionElement = document.getElementById("questions");
	questionElement.setAttribute("value",questions);	
	
	reorderQuestions();
}

function reorderQuestions()
{
	//get number of questions
	var questions = parseInt(document.forms['myform'].elements['questions'].value );
	//nextValue is the value of the next answer number in order (i.e. the next value if there are no missing values)
	var nextValue = 1;
	//currentValue is the counter for going through the actual answer number (i.e. with the possibility of missing values) 
	var currentValue = 1;
	
	//keep going until we've handled every answer
	while (nextValue <= questions)
	{

		//try and get the answer corresponding to current value
		questionElement = document.getElementById("question"+currentValue)
		if (!(questionElement==null)) 
		{

			//change element ID
			questionElement.setAttribute("id","question"+nextValue);	

			//change group element ID
			questionElement = document.getElementById("question"+currentValue+"group")
			questionElement.setAttribute("id","question"+nextValue+"group");
			
			//change legend
			questionElement = document.getElementById("question"+currentValue+"header")
			questionElement.setAttribute("id","question"+nextValue+"header");
			questionElement.setAttribute("href","#question"+nextValue);
			questionElement.innerHTML="Question "+nextValue;		
			
	
			
			//change question text
			questionElement = document.getElementById("questiontext"+currentValue)
			questionElement.setAttribute("id","questiontext"+nextValue);
			questionElement.setAttribute("name","questiontext"+nextValue);
			
		
		

		
			

			//loop through answers and change
			var questionNumber="question"+currentValue;
			var answers = parseInt(document.getElementsByName(questionNumber+"answers")[0].value);	
			for (var i=1; i<=answers;i++)
			{
			
				answerElement = document.getElementById(questionNumber+"answer"+i);
				answerElement.setAttribute("id", "question"+nextValue+"answer"+i);
	
				answerElement = document.getElementById(questionNumber+"answer"+i+"label")
				answerElement.setAttribute("for", "question"+nextValue+"answer"+i+"text");
				answerElement.setAttribute("id", "question"+nextValue+"answer"+i+"label");

				//change label for text
				answerElement = document.getElementById(questionNumber+"answer"+i+"text")			
				answerElement.setAttribute("name", "question"+nextValue+"answer"+i+"text");
				answerElement.setAttribute("id", "question"+nextValue+"answer"+i+"text");	
				
				//change deleteanswer
				answerElement = document.getElementById(questionNumber+"answer"+i+"deleteanswer")
				answerElement.setAttribute("name", "question"+nextValue+"answer"+i+"deleteanswer");
				answerElement.setAttribute("id", "question"+nextValue+"answer"+i+"deleteanswer");
				//done both ways for IE8 compatibility
				answerElement.setAttribute("onclick","deleteAnswer('"+"question"+nextValue+"','answer"+i+"')");	
				var questionText = "question"+nextValue;
				var answerText = "answer"+i;
				answerElement.onclick = function(){deleteAnswer(questionText,answerText);};
			}

			//change answers element
			questionElement = document.getElementById("question"+currentValue+"answers")
			questionElement.setAttribute("id","question"+nextValue+"answers");
			questionElement.setAttribute("name","question"+nextValue+"answers");
			
			//change delete question group
			questionElement = document.getElementById("deletequestion"+currentValue+"block")
			questionElement.setAttribute("id","deletequestion"+nextValue+"block");
	
			
			//change delete question button
			questionElement = document.getElementById("deletequestion"+currentValue)
			questionElement.setAttribute("id","deletequestion"+nextValue);
			//questionElement.setAttribute("onclick","deleteQuestion('question"+nextValue+"')");	
			questionElement.setAttribute("name","deletequestion"+nextValue);			
			var questionText = "question"+nextValue;
			function deleteFunction(x) {return function() { deleteQuestion(x)}};
			questionElement.onclick = deleteFunction(questionText);
			
			//change add answer button
			questionElement = document.getElementById("addanswerq"+currentValue)
			questionElement.setAttribute("id","addanswerq"+nextValue);
			questionElement.setAttribute("onclick","addAnswer('question"+nextValue+"')");
			questionElement.onclick = function(){addAnswer(questionText);};
			

			//we've found an actual element so increase nextValue
			nextValue++;
		}
		//consider the next possible answer value
		currentValue++;
		
	}

}

function deleteAnswer(questionNumber,answerNumber)
{
	eleToRemove = document.getElementById(questionNumber+answerNumber);	
	//alert(eleToRemove);	
	 eleToRemove.parentNode.removeChild(eleToRemove);
	var answerElement = document.getElementsByName(questionNumber+"answers")[0];

	var answers = parseInt(answerElement.value) - 1;
	answerElement.setAttribute("value", answers);	

	reorderAnswers(questionNumber);
}

//sorts out the answer numbers after an answer is deleted to prevent any gaps in the numbering
function reorderAnswers(questionNumber)
{
	//get number of answers
	var answers = parseInt(document.getElementsByName(questionNumber+"answers")[0].value);
	//nextValue is the value of the next answer number in order (i.e. the next value if there are no missing values)
	var nextValue = 1;
	//currentValue is the counter for going through the actual answer number (i.e. with the possibility of missing values) 
	var currentValue = 1;
	
	//keep going until we've handled every answer
	while (nextValue <= answers)
	{
		//try and get the answer corresponding to current value
		answerElement = document.getElementById(questionNumber+"answer"+currentValue)
		if (!(answerElement==null)) 
		{
		
			//change element ID
			answerElement.setAttribute("id", questionNumber+"answer"+nextValue);	
			
			//change label ID and for values
			answerElement = document.getElementById(questionNumber+"answer"+currentValue+"label")
			answerElement.setAttribute("for", questionNumber+"answer"+nextValue+"text");
			answerElement.setAttribute("id", questionNumber+"answer"+nextValue+"label");
			//change label for text
			answerElement = document.getElementById(questionNumber+"answer"+currentValue+"text")			
			answerElement.setAttribute("name", questionNumber+"answer"+nextValue+"text");
			answerElement.setAttribute("id", questionNumber+"answer"+nextValue+"text");			
			//change deleteanswer
			answerElement = document.getElementById(questionNumber+"answer"+currentValue+"deleteanswer")
			answerElement.setAttribute("name", questionNumber+"answer"+nextValue+"deleteanswer");
			answerElement.setAttribute("id", questionNumber+"answer"+nextValue+"deleteanswer");
			
			//Need to do it both way for compatibility with IE8
			answerElement.setAttribute("onclick","deleteAnswer('"+questionNumber+"','answer"+nextValue+"')");	
			var answerText = "answer"+nextValue;
			answerElement.onclick = function() {deleteAnswer(questionNumber,answerText);};
			//we've found an actual element so increase nextValue
			nextValue++;
		}
		//consider the next possible answer value
		currentValue++;
		
	}
	questionElement = document.getElementById(questionNumber+"answer1text");	
	questionElement.setAttribute("required", "");

}

function addAnswer(questionNumber)
{

	//eleToRemove = document.getElementById(questionNumber+'addanswerblock');
	//addAnswerBlock = eleToRemove;
	
	

	
	//eleToRemove.parentNode.removeChild(eleToRemove);
	eleToRemove = document.getElementById('delete'+questionNumber+"block");	
	deleteElement = eleToRemove; 
	questionElement=eleToRemove.parentNode;
	eleToRemove.parentNode.removeChild(eleToRemove);

	//increment answers
	var answers = parseInt(document.getElementsByName(questionNumber+"answers")[0].value) + 1;

	document.getElementsByName(questionNumber+"answers")[0].value = answers;
	
	//add in new answer block
	var mainElement = document.createElement("fieldset");

 
	//var legendElement = document.createElement("legend");
	//var legendText = document.createTextNode("Answer");
	//legendElement.appendChild(legendText);
	//mainElement.appendChild(legendElement);


	//generate answertext element
	
	var div = document.createElement("div");
	div.className="span12"; //row -> control-group
	var span = document.createElement("label"); //Span -> label
	span.className="control-label"; //label2 -> control-label
	span.setAttribute("for", questionNumber+"answer"+answers+"text");
	span.setAttribute("id", questionNumber+"answer"+answers+"label");	
	var labelText = document.createTextNode("Candidate");
	span.appendChild(labelText);
	div.appendChild(span);
	
	var span = document.createElement("div");
	span.className="controls";
	
	var textElement = document.createElement("input");
	textElement.setAttribute("type", "text");
	textElement.setAttribute("name", questionNumber+"answer"+answers+"text");
	textElement.setAttribute("id", questionNumber+"answer"+answers+"text");
	
	var deleteButton = document.createElement("input");
	deleteButton.setAttribute("value", "Delete Candidate");
	deleteButton.className="btn";
	deleteButton.setAttribute("type","button");
	deleteButton.setAttribute("onclick","deleteAnswer('"+questionNumber+"','answer"+answers+"')");
	var answerText = "answer"+answers;
	deleteButton.onclick = function() {deleteAnswer(questionNumber,answerText);};
	
   	deleteButton.setAttribute("name", questionNumber+"answer"+answers+"deleteanswer");
	deleteButton.setAttribute("id", questionNumber+"answer"+answers+"deleteanswer");
   
	span.appendChild(textElement);

	var pad = document.createTextNode("  ");
	span.appendChild(pad);
	span.appendChild(deleteButton);
	
	div.appendChild(span);
	mainElement.appendChild(div);
	
	//var div = document.createElement("div");
	//div.className="controls";
	//div.setAttribute("name", "delete"+questionNumber+"answer"+answers);
	
/**
	var deleteButton = document.createElement("input");
	deleteButton.setAttribute("value", "Delete Answer");

	deleteButton.setAttribute("type","button");
	deleteButton.setAttribute("onclick","deleteAnswer('"+questionNumber+'answer'+answers+"')");
	div.appendChild(deleteButton);**/
	//mainElement.appendChild(div);




	
	var mainDiv = document.createElement("div");
	mainDiv.setAttribute("id", questionNumber+"answer"+answers);
	mainDiv.className="control-group";
	
	mainDiv.appendChild(mainElement);
	questionElement.appendChild(mainDiv);

	//add back in addanswerblock and delete
	//questionElement.appendChild(addAnswerBlock);
	questionElement.appendChild(deleteElement);	
}

function addQuestion()
{

	//alert("Added a question");
	document.forms['myform'].elements['questions'].value = parseInt(document.forms['myform'].elements['questions'].value)+1;

	var questions = parseInt(document.forms['myform'].elements['questions'].value);
	eleToRemove = document.forms['myform'].elements['sendbutton'];	 
	eleToRemove.parentNode.removeChild(eleToRemove);
	eleToRemove = document.getElementById('addquestionblock');	 
    	eleToRemove.parentNode.removeChild(eleToRemove);

	
	var mainForm = document.getElementById('myform');

	
	var mainElement = document.createElement("fieldset");
 
	//Assign different attributes to the element.
    	var legendElement = document.createElement("legend");
	var legendText = document.createTextNode("Question " + questions);
	var legendLink = document.createElement("a");
	legendLink.setAttribute("href", "#question"+questions);
	legendLink.setAttribute("id", "question"+questions+"header");
	legendLink.setAttribute("data-toggle", "collapse");
	legendLink.appendChild(legendText);
	legendElement.appendChild(legendLink);
	mainElement.appendChild(legendElement);
	

	//add subelements
	var bigDiv = document.createElement("div");//TODO
	bigDiv.setAttribute("id", "question"+questions);//TODO
	bigDiv.className="collapse in";//TODO
	


	
	
   	var div = document.createElement("div");
	div.className="control-group hidden";//test (change to "row")
	var span = document.createElement("label"); //Anothertest - changed from span -> label
	span.className="control-label"; //label2 -> control-label


	var labelElement = document.createElement("label");
	labelElement.setAttribute("for", "questionlocked"+questions);
	var labelText = document.createTextNode("Question Locked");
	labelElement.appendChild(labelText);
	span.appendChild(labelElement);
	div.appendChild(span);

	var span = document.createElement("div");
	span.className="controls";
	
	var selectElement = document.createElement("select");

	selectElement.setAttribute("name", "questionlocked"+questions);
	
	var optionElement = document.createElement("option");
	optionElement.setAttribute("value","no");
	var optionText = document.createTextNode("No");
	optionElement.appendChild(optionText);
	selectElement.appendChild(optionElement);

	var optionElement = document.createElement("option");
	optionElement.setAttribute("value","yes");
	var optionText = document.createTextNode("Yes");
	optionElement.appendChild(optionText);
	selectElement.appendChild(optionElement);
	
	span.appendChild(selectElement);	


	div.appendChild(span);
	bigDiv.appendChild(div);
	
	

   	var div = document.createElement("div");
	div.className="control-group"; //Another test
	


	var labelElement = document.createElement("label");
		labelElement.className="control-label"; //label2 -> control-label
	labelElement.setAttribute("for", "questiontext"+questions);
	var labelText = document.createTextNode("Question Text");
	labelElement.setAttribute("id", "questiontext"+questions+"label");
	labelElement.appendChild(labelText);
	div.appendChild(labelElement);
	
	var span = document.createElement("div");
	span.className="controls";
	
	var textElement = document.createElement("input");
	textElement.setAttribute("type", "text");
	textElement.setAttribute("name", "questiontext"+questions);
	textElement.setAttribute("id", "questiontext"+questions);	
	textElement.setAttribute("required", "");
	span.appendChild(textElement);	
	var message = document.createElement("span");
	message.setAttribute("id", "questiontext"+questions+"message");

	message.className="label label-info"; 
	var messageText = document.createTextNode("Specify your question");
	message.appendChild(messageText);
	var tempText = document.createTextNode(" ");
	span.appendChild(tempText);
	span.appendChild(message);
   	div.appendChild(span);
	
	
	
   	bigDiv.appendChild(div);


	
   	
	mainForm.appendChild(bigDiv);
	

	var h2Element = document.createElement("h2");
	//h3Element.setAttribute("class","span8");
	var h2Text = document.createTextNode("Candidates");
	h2Element.appendChild(h2Text);
	bigDiv.appendChild(h2Element);
	
	var div = document.createElement("div");
	div.className="control-group";

	
   	var hiddenElement = document.createElement("input");
   	hiddenElement.setAttribute("name","question"+questions+"answers");
   	hiddenElement.setAttribute("id","question"+questions+"answers");
   	hiddenElement.setAttribute("value","0");
   	hiddenElement.setAttribute("type","hidden");
   
	bigDiv.appendChild(hiddenElement);


	//var div = document.createElement("span");
	//div.className="";
	//div.setAttribute("id","question"+questions+"addanswerblock");

	
	//bigDiv.appendChild(div);

	var innerDiv = document.createElement("div");
	innerDiv.className="misc-button2";


	var div = document.createElement("span");
	div.className="";
	div.setAttribute("id","deletequestion"+questions+"block");


	var answerButton = document.createElement("input");
	answerButton.setAttribute("value","Add Candidate");
	answerButton.className="btn btn-info";
	answerButton.setAttribute("name","addanswerq"+questions);
	answerButton.setAttribute("id","addanswerq"+questions);
	answerButton.setAttribute("type","button");

	//needs to be done both ways for IE8 compatibility
	answerButton.setAttribute("onclick","addAnswer('question"+questions+"')");	
	var questionValue = "question"+questions;
	answerButton.onclick = function (){addAnswer(questionValue);};
	innerDiv.appendChild(answerButton);

	var deleteButton = document.createElement("input");
	deleteButton.className="btn btn-danger";
	deleteButton.setAttribute("value", "Delete Question");
    deleteButton.setAttribute("name", "deletequestion"+questions);
    deleteButton.setAttribute("id", "deletequestion"+questions);
	deleteButton.setAttribute("type","button");
	deleteButton.onclick = function (){deleteQuestion(questionValue);};

	var pad = document.createTextNode("  ");
	innerDiv.appendChild(pad);
	innerDiv.appendChild(deleteButton);

	//This is a bit hacky, but it works
	var tbreak = document.createElement("br");
	var tbreak1 = document.createElement("br");
	innerDiv.appendChild(tbreak);
	innerDiv.appendChild(tbreak1);
	//End of hacky stuff
	
	div.appendChild(innerDiv);
	
	bigDiv.appendChild(div);   
   
   
	//add to form
	var maindiv = document.createElement("div");
   	//maindiv.className="control-group";

	mainElement.appendChild(bigDiv);
   
	maindiv.setAttribute("id", "question"+questions+"group");
	maindiv.appendChild(mainElement);
	mainForm.appendChild(maindiv);


	
	var div = document.createElement("div");
	div.setAttribute("id","addquestionblock");
	div.className="misc-button";
	var addButton = document.createElement("input");
	addButton.className="btn btn-large btn-info";
	addButton.setAttribute("value", "Add Question");
    	addButton.setAttribute("name", "addquestion");
	addButton.setAttribute("type","button");
	//done both ways for IE8 compatibility
	addButton.setAttribute("onclick","addQuestion()");
	addButton.onclick = function() {addQuestion();};


	div.appendChild(addButton);

	var pad = document.createTextNode("  ");
	div.appendChild(pad);

	var submitButton = document.createElement("input");
	submitButton.setAttribute("value", "Create Election");
	
	//do both ways for IE8 compatibility
	submitButton.setAttribute("class", "btn btn-large btn-success"); //Added
	submitButton.className ="btn btn-large btn-success";
    submitButton.setAttribute("name", "sendbutton");
    submitButton.setAttribute("id", "sendbutton");
	//done both ways for IE8 compatibility
	submitButton.setAttribute("name", "sendbutton");
	submitButton.name = "sendbutton";
	submitButton.setAttribute("type","submit");
	div.appendChild(submitButton);
	
	mainForm.appendChild(div);
	
		
	addAnswer("question"+questions);
	questionElement = document.getElementById("question"+questions+"answer1text");	
	questionElement.setAttribute("required", "");
	addAnswer("question"+questions);
	addAnswer("question"+questions);
	addAnswer("question"+questions);
	

}





function onPageLoad()
{
//	addAnswer('question1');
//	addAnswer('question1');

	var admin = false;
	var hyperlinks = document.getElementsByTagName("a");
	for(var i=0; i< hyperlinks.length; i++)
	{
		if(hyperlinks[i].href.match(/admin.php/))
		{
			admin = true;
		}
	}
	if (admin)
	{
		 $.validator.addMethod('positiveinteger', function(value, element, param) {
            return (value>0) && (value == parseInt(value, 10));
        }, 'Please enter a positive integer value');
		$( "#myform" ).validate(
		{

			errorPlacement: function(error, element) 
		{
			if (element.attr("name") == "users") 
			{
				error.insertAfter("#userMessage");
			}
			else if (element.attr("name").substring(0,12)=="questiontext")
			{
				error.insertAfter("#"+element.attr("id")+"message");
			}

			else if (element.attr("name").substring(0,8)=="question" && element.attr("name").substring(element.attr("id").length-4,element.attr("id").length)=="text")
			{
				error.insertAfter("#"+element.attr("id").substring(0,element.attr("id").length-4)+"deleteanswer");
			}
			else
			{
				error.insertAfter(element);
			}
		},
		rules: {

		users: {
				positiveinteger: true

				}

				}
		});
	}
	else
	{
		$.validator.addMethod('positiveinteger', function(value, element, param) {
            return (value>0) && (value < 201) && (value == parseInt(value, 10));
        }, 'Please enter a value between 0 and 200');
		$( "#myform" ).validate(
		{

			errorPlacement: function(error, element) 
		{
			if (element.attr("name") == "users") 
			{
				error.insertAfter("#userMessage");
			}
			else if (element.attr("name").substring(0,12)=="questiontext")
			{
				error.insertAfter("#"+element.attr("id")+"message");
			}
			else if (element.attr("name").substring(0,8)=="question" && element.attr("name").substring(element.attr("id").length-4,element.attr("id").length)=="text")
			{
				error.insertAfter("#"+element.attr("id").substring(0,element.attr("id").length-4)+"deleteanswer");
			}
			else
			{
				error.insertAfter(element);
			}
		},
		rules: {

		users: {
				positiveinteger: true

				}

				}
		});
	
	
	
	}
	
}

