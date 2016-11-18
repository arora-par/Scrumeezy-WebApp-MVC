<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="/app/resources/js/manageteam.js"></script>
<link rel="stylesheet" href="/app/resources/css/manageteam.css" />
</head>
<body>

	<jsp:include page="navbar.jsp" />

	<div class="container">

		<div class="row">
			<div class="col-md-6 col-md-offset-3">



				<div class="panel panel-default">
					<!-- Default panel contents -->
					<div class="panel-heading">
						<h3>
							Manage Team
							<button type="button" class="pull-right" id="add-member"
								title="Add new member">
								<span class="glyphicon glyphicon-plus-sign"></span>
							</button>
						</h3>
					</div>

					<!-- Table -->
					<table class="table table-striped">
						<thead>
							<tr>
								<th>User</th>
								<th>Role</th>
								
							</tr>
						</thead>
						<tbody>

							<c:forEach var="memberItem" items="${teamMembers}">
								<tr>
									<td>${memberItem.user.lastName},
										${memberItem.user.firstName}</td>

									<c:choose>
										<c:when test="${memberItem.role == 'TM'}">
											<td>Team Member</td>
										</c:when>
										<c:when test="${memberItem.role == 'PO'}">
											<td>Product Owner</td>
										</c:when>
										
									</c:choose>

								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>

			</div>
		</div>
	</div>

	<div id="dialog-form" title="Add new member">
		<p id="resultMessage">Cannot add already existing members to the
			team again.</p>

		<form>
			<fieldset>
				<label for="emailId">Enter New Member's Email</label> <input
					type="email" name="emailId" id="emailId"
					class="text ui-widget-content ui-corner-all">



				<!-- Allow form submission with keyboard without duplicating the dialog button -->
				<input type="submit" tabindex="-1"
					style="position: absolute; top: -1000px">
			</fieldset>
		</form>
	</div>

	<footer class="container-fluid text-center">
		<p>
			<span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>Scrumeezy
			- we make Agile Scrum easy!
		</p>

	</footer>

</body>
</html>
