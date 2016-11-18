$(document)
		.ready(
				function() {

					// show stories
					var storiesDialog = $("#dialog-stories").dialog({
						autoOpen : false,
						height : 300,
						width : 350,
						modal : true,
						buttons : {
							
							Close : function() {
								storiesDialog.dialog("close");
							}
						},
						close : function() {
							$("#storiesTable").find("tbody").empty();
							
						}
					});

					$(".show-stories").on("click", function() {
						event.preventDefault();
						var featureIdVal = $(this).attr("href");

						$.get("/app/project/listStories.htm", {
							featureId : featureIdVal
						}).done(function(json) {
							var parsed = JSON.parse(json);
							var tblBody = $("#storiesTable").find("tbody");
							
							$.each(parsed, function(index, obj) {
								$(tblBody).append("<tr><td>" + obj.description + "</td><td>" + obj.points + "</td></tr>");
							});
							$(storiesDialog).dialog("open");
						});

					});

					// add feature

					var dialog, form, description = $("#description"), resultMessage = $("#resultMessage");

					function addFeature() {
						$
								.post("/app/project/addFeature.htm",
										form.serialize())
								.done(
										function(data) {
											if (data.indexOf("success") >= 0) {

												window.location = "/app/project/features.htm";
											} else {

												alert(data);
											}

											$(dialog).dialog("close");
										});
					}

					dialog = $("#dialog-form-feature").dialog({
						autoOpen : false,
						height : 300,
						width : 350,
						modal : true,
						buttons : {
							"Add Feature" : addFeature,
							Cancel : function() {
								dialog.dialog("close");
							}
						},
						close : function() {
							form[0].reset();
							description.removeClass("ui-state-error");
						}
					});

					form = dialog.find("form").on("submit", function(event) {
						event.preventDefault();
						addUser();
					});

					$("#add-feature").button().on("click", function() {
						dialog.dialog("open");
					});

					// add user story

					var addStoryBtnRef, dialogUserStory, formUserStory, descriptionUserStory = $("#storyDesc"), storyPoints = $("#points"), resultMessageStory = $("#resultMessageStory");

					function addStory() {
						var featureNum = $(addStoryBtnRef).closest("tr").find(
								"label").html();

						$.post("/app/project/addUserStory.htm", {
							storyDesc : descriptionUserStory.val(),
							points : storyPoints.val(),
							featureId : featureNum
						}).done(function(data) {
							if (data.indexOf("success") >= 0) {

								window.location = "/app/project/features.htm";
							} else {

								alert(data);
							}

							$(dialog).dialog("close");
						});
					}

					dialogUserStory = $("#dialog-form-story").dialog({
						autoOpen : false,
						height : 300,
						width : 350,
						modal : true,
						buttons : {
							"Add Story" : addStory,
							Cancel : function() {
								dialogUserStory.dialog("close");
							}
						},
						close : function() {

							descriptionUserStory.removeClass("ui-state-error");

						}
					});

					formUserStory = dialogUserStory.find("form").on("submit",
							function(event) {
								event.preventDefault();
								addUser();
							});

					$(".add-user-story").button().on("click", function() {
						addStoryBtnRef = $(this);
						dialogUserStory.dialog("open");
					});

				});
