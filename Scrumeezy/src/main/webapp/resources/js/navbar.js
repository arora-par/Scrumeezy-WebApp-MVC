$(document).ready(function() {
					
	// switchProject
	
	function switchTeam(){
		
		var selectTeam = $("#user-teams").val();
		var selectTeamArr = selectTeam.split("-");
		
		if (selectTeamArr.length > 1) {
			$.post("/app/project/switchTeam.htm",
					{teamId:selectTeamArr[0]})
			.done(function(data){
				if (data.indexOf("success") >= 0) {
					window.location = "/app/login.htm";
				} else {
					alert(data);
				}
			});
		}
		
	}
	
	var selectTeamDialog = $("#dialog-select-team").dialog({
		autoOpen : false,
		height : 300,
		width : 350,
		modal : true,
		buttons : {
			"Switch" : switchTeam,
			Cancel : function() {
				selectTeamDialog.dialog("close");
			}
		},
		close : function() {
			$("#user-teams").empty();
		}
	});
	
		$("#switchProject").on("click", function(){
			
			$.get( "/app/project/getProjectsForUser.htm", function( data ) {
				  
				// 4-team1,7-team2
				  var teamsArr = data.split(",");
				  $.each(teamsArr, function(index, idHyphenTeam){
					  $("#user-teams").append("<option>" + idHyphenTeam + "</option>")
				  });
				  
				  selectTeamDialog.dialog("open");
			});
		});
});
