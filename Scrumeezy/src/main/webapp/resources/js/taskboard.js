$(document)
		.ready(
				function() {
					
					
					// mark in progress
					
					$(".mark-progress").on("click", function(){
						event.preventDefault();
						
						var taskIdVal = $(this).closest("div").find("input[name='taskId']").val();
						$.post("/app/sprint/markProgress.htm", 
								{taskId: taskIdVal})
						.done(function(data){
							if (data.indexOf("success") >= 0) {

								window.location = "/app/login.htm";
							} else {
								alert(data);
							}
						});
					});
					
					
					// mark done
					
					$(".mark-done").on("click", function(){
						event.preventDefault();
						
						var taskIdVal = $(this).closest("div").find("input[name='taskId']").val();
						$.post("/app/sprint/markDone.htm", 
								{taskId: taskIdVal})
						.done(function(data){
							if (data.indexOf("success") >= 0) {

								window.location = "/app/login.htm";
							} else {
								alert(data);
							}
						});
					});
					
					// mark todo
					
					$(".mark-todo").on("click", function(){
						event.preventDefault();
						
						var taskIdVal = $(this).closest("div").find("input[name='taskId']").val();
						$.post("/app/sprint/markTodo.htm", 
								{taskId: taskIdVal})
						.done(function(data){
							if (data.indexOf("success") >= 0) {

								window.location = "/app/login.htm";
							} else {
								alert(data);
							}
						});
					});

					// add task

					
					var addTaskLinkRef, dialogAddTask, formAddTask, taskDescription = $("#description"), dueDateVal = $("#dueDate");

					function addTask() {
						var storyIdVal = $(addTaskLinkRef).closest(
								"div[class='row']")
								.find("input[type='hidden']");

						$.post("/app/sprint/addTask.htm", {
							description : taskDescription.val(),
							dueDate : dueDateVal.val(),
							storyId : storyIdVal.val()
						}).done(function(data) {
							if (data.indexOf("success") >= 0) {

								window.location = "/app/login.htm";
							} else {

								alert(data);
							}

							$(dialog).dialog("close");
						});
					}

					dialogAddTask = $("#dialog-form-task").dialog({
						autoOpen : false,
						height : 300,
						width : 350,
						modal : true,
						buttons : {
							"Add Task" : addTask,
							Cancel : function() {
								dialogAddTask.dialog("close");
							}
						},
						close : function() {

							taskDescription.removeClass("ui-state-error");

						}
					});

					formAddTask = dialogAddTask.find("form").on("submit",
							function(event) {
								event.preventDefault();
								addTask();
							});

					$(".add-task").button().on("click", function() {
						addTaskLinkRef = $(this);
						dialogAddTask.dialog("open");
						return false;
					});

				});
