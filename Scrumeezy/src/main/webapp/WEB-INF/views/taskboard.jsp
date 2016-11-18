<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html lang="en">
<head>
<title>Scrumeezy</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="/app/resources/js/taskboard.js"></script>

</head>
<body>

	<jsp:include page="navbar.jsp" />

	<!-- Begin Headers: Story, TODO, IN-PROGRESS etc.  -->
	<div class="container">
		<div class="row">
			<div class="col-sm-3">
				<div class="panel panel-primary">
					<div class="panel-heading">STORY</div>

				</div>
			</div>
			<div class="col-sm-3">
				<div class="panel panel-default">
					<div class="panel-heading">TO-DO</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="panel panel-info">
					<div class="panel-heading">IN-PROGRESS</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="panel panel-success">
					<div class="panel-heading">DONE</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<!-- End Headers: Story, TODO, IN-PROGRESS etc.  -->
	<!-- Begin main body -->


	<c:choose>
		<c:when test="${sprintFound}">

			<c:forEach var="storyItem" items="${currentSprintStories}">
				<div class="container">
					<div class="row">
						<input type="hidden" name="storyId" value="${storyItem.id}">
						<!-- Begin story -->
						<div class="col-sm-3">

							<div class="panel panel-primary">

								<div class="panel-heading">


									<!-- Begin story options body such as add task -->
									<c:choose>
							          		<c:when test="${userInRole=='TM'}">
							          			<div class="btn-group">
													<button type="button"
														class="btn btn-sm btn-primary dropdown-toggle "
														data-toggle="dropdown" aria-haspopup="true"
														aria-expanded="false">
														Action <span class="caret"></span>
													</button>
													<ul class="dropdown-menu">
														<li><a href="#" class="add-task">Add Task</a></li>
			
													</ul>
												</div>
							          		</c:when>
							          	</c:choose>

									<!-- End story options body such as add task -->
									<span class="badge pull-right">${storyItem.points}</span>
								</div>
								<div class="panel-body">${storyItem.description}</div>

							</div>
						</div>
						<!-- End story  -->

						<!-- Begin TO-DO tasks-->
						<div class="col-sm-3">

							<!-- Iterate here -->
							<c:forEach var="taskItem" items="${storyItem.tasks}">
								<c:choose>
									<c:when test="${taskItem.status == 'T'}">
										<div class="panel panel-default">

											<div class="panel-heading">
												<!-- Begin task options body such as mark 'in-progress' -->
												<div class="btn-group">
													<button type="button"
														class="btn btn-sm btn-default dropdown-toggle"
														data-toggle="dropdown" aria-haspopup="true"
														aria-expanded="false">
														Action <span class="caret"></span>
													</button>
													 <input type="hidden" name="taskId" value="${taskItem.id}" />
													<ul class="dropdown-menu">
														<li><a href="#" class="mark-progress">Mark
																In-Progress</a></li>
														<li><a href="#" class="mark-done">Mark Done</a></li>
													</ul>
												</div>
												<span class="pull-right">${taskItem.assignedTo.userName}</span>

												<!-- End task options body such as mark 'in-progress' -->
												
											</div>
											<div class="panel-body">${taskItem.description}<br />
												Created: ${taskItem.creationDate}


											</div>
											<div class="panel-footer">Due: ${taskItem.dueDate}<c:choose>
													<c:when test="${now gt taskItem.dueDate}">
														<span
															class="pull-right glyphicon glyphicon-flag text-danger"></span>
													</c:when>
												</c:choose></div>
										</div>
									</c:when>
								</c:choose>
							</c:forEach>
						</div>
						<!-- End TO-DO tasks-->


						<!-- Begin IN_PROGRESS tasks-->
						<div class="col-sm-3">
							<!-- Iterate here -->
							<c:forEach var="taskItem" items="${storyItem.tasks}">
								<c:choose>
									<c:when test="${taskItem.status == 'P'}">
										<div class="panel panel-info">

											<div class="panel-heading">
												<!-- Begin task options body such as mark 'done' -->
												<div class="btn-group">
													<button type="button"
														class="btn btn-sm btn-info dropdown-toggle"
														data-toggle="dropdown" aria-haspopup="true"
														aria-expanded="false">
														Action <span class="caret"></span>
													</button>
													<input type="hidden" name="taskId" value="${taskItem.id}">
													<ul class="dropdown-menu">
														<li><a href="#" class="mark-todo">Mark To-Do</a></li>
														<li><a href="#" class="mark-done">Mark Done</a></li>
													</ul>
												</div>
												<span class="pull-right">${taskItem.assignedTo.userName}</span>
												<!-- End task options body such as mark 'done' -->
												

											</div>
											<div class="panel-body">${taskItem.description}<br />
												Created: ${taskItem.creationDate}

											</div>
											<div class="panel-footer">Due: ${taskItem.dueDate}<c:choose>
													<c:when test="${now gt taskItem.dueDate}">
														<span
															class="pull-right glyphicon glyphicon-flag text-danger"></span>
													</c:when>
												</c:choose></div>

										</div>
									</c:when>
								</c:choose>
							</c:forEach>
						</div>
						<!-- End IN_PROGRESS tasks-->

						<!-- Begin DONE tasks-->
						<div class="col-sm-3">
							<!-- Iterate here -->
							<c:forEach var="taskItem" items="${storyItem.tasks}">
								<c:choose>
									<c:when test="${taskItem.status == 'D'}">
										<div class="panel panel-success">

											<div class="panel-heading">
												<!-- Begin task options body such as mark 'in-progress' -->
												<div class="btn-group">
													<button type="button"
														class="btn btn-sm btn-success dropdown-toggle"
														data-toggle="dropdown" aria-haspopup="true"
														aria-expanded="false">
														Action <span class="caret"></span>
													</button>
													
													<input type="hidden" name="taskId" value="${taskItem.id}">
													<ul class="dropdown-menu">
														<li><a href="#" class="mark-progress">Mark
																In-Progress</a></li>

													</ul>

													<!-- End task options body such as mark 'in-progress' -->

												</div>
												<span class="pull-right">${taskItem.assignedTo.userName}</span>
											</div>
											<div class="panel-body">${taskItem.description}
												<br /> Created: ${taskItem.creationDate}

											</div>
											<div class="panel-footer">Done: ${taskItem.doneDate}</div>

										</div>
									</c:when>
								</c:choose>
							</c:forEach>
						</div>
						<!-- End DONE tasks-->
					</div>
				</div>
				<br>
			</c:forEach>
		</c:when>
		<c:otherwise>
			Steps to get started - PROJECT -> FEATURES -> TEAM -> SPRINT -> TASKS
		</c:otherwise>
	</c:choose>

	<!-- End main body -->
	
	<div id="dialog-form-task" title="Add new user story">
		<p id="resultMessageStory"></p>

		<form>
			<fieldset>
				<label for="description">Enter description</label> <br /> <input
					type="text" name="description" id="description"
					class="text ui-widget-content ui-corner-all" /><br /> <label
					for="dueDate">Due date</label> <br /> <input type="date"
					name="dueDate" id="dueDate"
					class="text ui-widget-content ui-corner-all" />


				<!-- Allow form submission with keyboard without duplicating the dialog button -->
				<input type="submit" tabindex="-1"
					style="position: absolute; top: -1000px" />
			</fieldset>
		</form>
	</div>

	<br />
	<footer class="container-fluid text-center">
		<p>
			<span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>Scrumeezy
			- we make Agile Scrum easy!
		</p>

	</footer>

</body>
</html>
