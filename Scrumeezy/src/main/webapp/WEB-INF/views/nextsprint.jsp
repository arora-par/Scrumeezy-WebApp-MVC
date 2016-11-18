<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<jsp:useBean id="dateUtil" class="org.apache.commons.lang.time.DateUtils" />
<!DOCTYPE html>
<html lang="en">
<head>
<title>Scrumeezy</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="/app/resources/js/nextsprint.js"></script>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
</head>
<body>

	<jsp:include page="navbar.jsp" />

	<div class="container">

		<div class="row">
			<div class="col-md-8 col-md-offset-2">

				Start Date:<fmt:formatDate type="date" value="${now}" />
				
				<span class="pull-right">End Date: ${dateUtil.addWeeks(now, sprintLength)}</span>


				<div class="panel panel-info">
					<!-- Default panel contents -->
					<div class="panel-heading">
						<h3>Choose stories for next sprint</h3>
					</div>
					<div class="panel-body">
						<!-- iterate features --> 
						<c:forEach var="featureItem" items="${featureStoryMap.values()}">
							<div class="list-group">
								<h3>${featureItem.description}</h3>
								
								<!-- iterate stories within feature -->
								<c:forEach var="storyItem" items="${featureItem.stories}">
									<p>
										<input type="hidden" value="${storyItem.id}"><input type="checkbox" /><span>${storyItem.description}</span>
									</p>
								</c:forEach>
								
							</div>
							
						</c:forEach>
					</div>
					

				</div>

				<button type="button" id="submitBtn" class="btn btn-sm  btn-primary pull-right" title="Submit">Submit</button>

			</div>
		</div>
	</div>

	<footer class="container-fluid text-center">
		<p>
			<span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>Scrumeezy
			- we make Agile Scrum easy!
		</p>

	</footer>

</body>
</html>
