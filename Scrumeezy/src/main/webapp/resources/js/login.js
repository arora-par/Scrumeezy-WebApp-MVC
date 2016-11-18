
$(document).ready(function(){
    
	
    //initially the button should be disabled
    $("#login").prop('disabled', true);
    
    $("input").keyup(function(){
        $("#errmsg").html("");
        username=$("#username").val();
        password=$("#password").val();
        
        if (username.length >0 && password.length>0) {
            $("#login").prop('disabled', false);
        } else {
            $("#login").prop('disabled', true);
        }
    });
        
    

    $("#login").click(function(){
    	alert("login cicked");
      username=$("#username").val();
      password=$("#password").val();
        
      $.ajax({
       type: "POST",
       url: "login.jsp",
       data: "user="+username+"&password="+password,    
       success: function(result){    
           
            if(result.indexOf("true") > -1)    {
                
                window.location="dashboard.jsp";
            }
            else    {

                $("#errmsg").html("Wrong username or password");
            }
       }
      });
    return false;
    });
});


