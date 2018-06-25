$(document).ready(function() {
    $("#task-add-field").keypress(function(e) { 
	if (e.which == 13) {
	    createTask();
	}
    });
    
    $(".task-checkbox").each(function () {
	var parentTask = $(this).parent();
        $(this).click(function(e) {
	    crossTask(parentTask);
	});
    });
    
    $(".task-remove").each(function () {
	var parentTask = $(this).parent();
        $(this).click(function(e) {
	    removeTask(parentTask);
	});
    });
    
   $('.task').each(function () {
       colorTaskBackground($(this));
   });
});
