<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Scrumeezy</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>	
<script src="/app/resources/js/managefeatures.js"></script>
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
							Manage Features<button id="add-feature" class="btn btn-sm btn-info pull-right" title="add feature">New!</button>
						</h3>
					</div>

					<!-- Table -->
					<table class="table table-striped">
						<thead>
							<tr>
								<th>#</th>
								<th>Feature</th>
								<th>Change</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="featureItem" items="${features}">
								<tr>

									<td><label>${featureItem.id}</label></td>

									<td><a class="show-stories" href="${featureItem.id}">${featureItem.description}</a></td>

									<td>
										<button type="button" class="btn btn-sm  btn-primary add-user-story"
											title="add story">
											<span class="glyphicon glyphicon-plus-sign"></span>
										</button></td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
				</div>

			</div>
		</div>
	</div>
	
	
	<div id="dialog-stories" title="User stories">
		

		<table class="table table-striped" id="storiesTable">
			<thead><tr><th>Story</th><th>Points</th></tr></thead>
			<tbody></tbody>
		</table>
			
		
	</div>
	
	<div id="dialog-form-feature" title="Add new feature">
		<p id="resultMessage"></p>

		<form>
			<fieldset>
				<label for="description">Enter description</label> <input
					type="text" name="description" id="description"
					class="text ui-widget-content ui-corner-all"/>

				

				<!-- Allow form submission with keyboard without duplicating the dialog button -->
				<input type="submit" tabindex="-1"
					style="position: absolute; top: -1000px"/>
			</fieldset>
		</form>
	</div>
	
	<div id="dialog-form-story" title="Add new user story">
		<p id="resultMessageStory"></p>

		<form>
			<fieldset>
				<label for="storyDesc">Enter description</label><br/> <input
					type="text" name="storyDesc" id="storyDesc"
					class="text ui-widget-content ui-corner-all"/><br/>

				<label for="points">Story points</label><br/> <input
					type="text" name="points" id="points"
					class="text ui-widget-content ui-corner-all"/>
				

				<!-- Allow form submission with keyboard without duplicating the dialog button -->
				<input type="submit" tabindex="-1"
					style="position: absolute; top: -1000px"/>
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
