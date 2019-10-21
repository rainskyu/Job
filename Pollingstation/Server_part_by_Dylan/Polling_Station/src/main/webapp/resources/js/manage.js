function confirmfinish(sessionId) 
{
        var r = confirm("Are you sure you want to finish election " + sessionId + "?");

        if(r == true) {
		var url = '../finishallquestions.php';
		var form = $('<form style="visibility:hidden" action="' + url + '" method="post">' +
		  '<input type="text" name="sessionid" value="' + sessionId + '" />' +
		  '</form>');
		$('body').append(form);
		$(form).submit();
	}else{ //Do nothing 
	}
}

function unlocksession(sessionId) {
	var r = confirm("Are you sure you want to unlock election " + sessionId + "?");
	if(r == true) {
		var url = "unlock_all_questions.php";
		$.post("unlock_all_questions.php",{sessionid: sessionId},function(data,result){
    			if(result == 'success'){
				alert("Election " + sessionId + " was successfully unlocked.  Voting may now commence.");
			}else {
				alert("Error during unlocking.  Please contact a system administrator");
			}
  		});
	}else { //Do nothing
	}
}
//workaround for issue with earlier versions of IE and option.onclick
function coordaction(selectbox)
{
	if (selectbox.options[selectbox.selectedIndex].value!="select")
	{
		eval(selectbox.options[selectbox.selectedIndex].value);
	}
}

function viewresults(id){
	location.href="../allresults.php?sessionid=" + id;
}

function viewpdf(id){
	location.href="../backup.php?sessionid=" + id;
}

function duplicateelection(id)
{
	location.href="../coordinator.php?duplicate=" + id;
}
