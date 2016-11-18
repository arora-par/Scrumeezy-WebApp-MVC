<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Scrumeezy</title>
<meta charset="utf-8"></meta>
<meta name="viewport" content="width=device-width, initial-scale=1"></meta>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"></link>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>


</head>
<body>

	<jsp:include page="navbar.jsp" />
	<div class="container">
		<div class="well">
			<header>
				<h1><span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>Scrumeezy</h1>
				<p>We make Agile Scrum easy. Never say never
					again!</p>
			</header>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="well">
				
					
					<form:form action="save.htm" method="post" commandName="project">
					

						<div class="form-group">
							<label class="login-dialog" for="projectName">Short Name</label> <form:input path="projectName"
								type="text" class="form-control"
								placeholder="Project Short Name" required="required"/>

						</div>
						
						<div class="form-group">
							<label class="login-dialog" for="details">Details</label> <form:textarea
								type="text" class="form-control" path="projectDetails"
								placeholder="Project Details" required="required"/>

						</div>
						
						<div class="form-group">
							<label class="login-dialog" for="startDate">Start Date</label> <form:input
								type="date" class="form-control" path="startDate"
								 required="required"/>

						</div>
						<div class="form-group">
							<label class="login-dialog" for="endDate">End Date</label> <form:input
								type="date" class="form-control" path="endDate"
								 required="required"/>

						</div>
						
						<div class="form-group">
							<label class="login-dialog" for="sprintLength">Sprint Length (# weeks: 1 to 4)</label> <form:input path="sprintLength"
								type="range" class="form-control" min="1" max="4"
								 required="required"/>
						</div>
						
						
						<button type="submit"  class="btn btn-primary">Register</button>
						
					</form:form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
