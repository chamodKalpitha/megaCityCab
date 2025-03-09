<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        
<c:if test="${not empty error}">
    <script>
        $(document).ready(function(){
            $('#errorToast').toast('show');
        });
    </script>
</c:if>
		
<div id="errorToast" class="toast bg-danger" style="position: absolute; bottom: 10px; right: 10px;" data-autohide="false">
	<div class="d-flex">
	    <div class="toast-body text-white">
	    <c:out value="${error}"/>
	    </div>
    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-dismiss="toast" aria-label="Close"></button>
  </div>

</div>


<c:if test="${not empty success}">
    <script>
        $(document).ready(function(){
            $('#sucessToast').toast('show');
        });
    </script>
</c:if>
		
<div id="sucessToast" class="toast bg-success" style="position: absolute; bottom: 10px; right: 10px;" data-autohide="false">
	<div class="d-flex">
	    <div class="toast-body text-white">
	    <c:out value="${success}"/>
	    </div>
    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-dismiss="toast" aria-label="Close"></button>
  </div>

</div>
