$(document).ready(function() {

	$("#accordion").accordion({
		heightStyle : "fill"
	});

	$("#accordion-resizer").resizable({
		minHeight : 840,
		minWidth : 500,
		resize : function() {
			$("#accordion").accordion("refresh");
		}
	});
});