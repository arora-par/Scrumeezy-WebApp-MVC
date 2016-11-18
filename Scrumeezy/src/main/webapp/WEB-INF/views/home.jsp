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

<link rel="stylesheet" href="resources/css/scrumeezy.css"></link>


</head>
<body>

	<div class="container">
		<div class="jumbotron login-jumbotron">
			<header>
				<h1><span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>Scrumeezy</h1>
				<p>We make Agile Scrum easy. Never say never
					again!</p>
			</header>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="well">
					<div id="errmsg"></div>
					<form:form action="login.htm" method="post" commandName="user">
					

						<div class="form-group">
							<label class="login-dialog" for="user">User Name</label> <form:input path="userName"
								type="text" class="form-control"
								placeholder="User Name" required="required"/>

						</div>
						<div class="form-group">
							<label class="login-dialog" for="password">Password</label> <form:input
								type="password" class="form-control" path="password"
								 placeholder="Password" required="required"/>
						</div>
						<button type="submit" id="login" class="btn btn-success">Login</button>
						<div class="pull-right">
							<a href="register.htm" class="btn btn-warning">Register</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
