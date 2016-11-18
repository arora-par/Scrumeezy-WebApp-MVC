$(document).ready(function() {
	var dialog, form, 
		emailId = $("#emailId"),
		resultMessage = $("#resultMessage");

	function addUser() {
		$.post( "/app/team/add.htm", form.serialize() )
		  .done(function( data ) {
		    if (data.indexOf("success") >= 0 ) {
		    	window.location = "/app/team/manage.htm";
		    } else {
		    	
		    	alert(data);
		    }
		    
		    $(dialog).dialog( "close" );
		  });
	}
	
	dialog = $("#dialog-form").dialog({
		autoOpen : false,
		height : 300,
		width : 350,
		modal : true,
		buttons : {
			"Add Member" : addUser,
			Cancel : function() {
				dialog.dialog("close");
			}
		},
		close : function() {
			form[0].reset();
			emailId.removeClass("ui-state-error");
		}
	});

	form = dialog.find("form").on("submit", function(event) {
		event.preventDefault();
		addUser();
	});

	$("#add-member").button().on("click", function() {
		dialog.dialog("open");
	});

});
