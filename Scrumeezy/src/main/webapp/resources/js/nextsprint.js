$(document).ready(function() {
	$("#submitBtn").on("click", function(){
		var checkBoxes = $( "input:checked" );
		var storyIdArr = new Array();
		$.each(checkBoxes, function(index, checkBox){
			
			storyIdArr.push($(checkBox).prev().val());
		});
		
		if (storyIdArr.length > 0) {
			
			var storyIdCSL = storyIdArr.toString();
			
			$.post("/app/sprint/nextSprint.htm", {
				storyIdList : storyIdCSL
			}).done(function(data) {
				if (data.indexOf("success") >= 0) {
					
					window.location = "/app/login.htm";
				} else {
					alert(data);
				}
			});
		} else {
			alert("Please select atleast one user story");
		}
		
		
	});
});
