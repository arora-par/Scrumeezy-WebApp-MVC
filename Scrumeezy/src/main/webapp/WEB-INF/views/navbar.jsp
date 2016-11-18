<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>	
<script src="/app/resources/js/navbar.js"></script>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="/app/login.htm"><span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>Scrumeezy</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
       
         <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Project <span class="caret"></span></a>
          <ul class="dropdown-menu">
          	<c:choose>
          		<c:when test="${userInRole=='PO'}">
          			<li><a href="/app/project/features.htm">Features</a></li>
          		</c:when>
          		
          	</c:choose>
          	
            <li><a href="/app/project/new.htm">New Project</a></li>
            <li><a id="switchProject" href="#">Switch Project</a></li>
            
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Team <span class="caret"></span></a>
          <ul class="dropdown-menu">
          	<c:choose>
          		<c:when test="${userInRole=='PO'}">
          			<li><a href="/app/team/manage.htm">Manage Team</a></li>
          		</c:when>
          		<c:when test="${userInRole=='TM'}">
          			<li><a href="#">Leave Team</a></li>
          		</c:when>
          	</c:choose>
            
            
            
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Sprint <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="/app/sprint/next.htm">Plan Next</a></li>
            
          </ul>
        </li>
        
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#"><span class="glyphicon glyphicon-user"></span> ${userName}</a></li>
        <li><a href="/app/logout.htm"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
      </ul>
    </div>
  </div>
</nav>

	<div id="dialog-select-team" title="Select Team">
		<p id="resultMessageStory"></p>
		<select id="user-teams">
		</select>
		
	</div>