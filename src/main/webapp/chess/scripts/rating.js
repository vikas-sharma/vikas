$(function() {
	
	var d = new Date();
	var url = "rating/init.htm?epoch=" + d.getMilliseconds();
	$.getJSON(url, null, function(datasets) {

		init(datasets);
	});

});
	
function init(datasets) {

	// insert checkboxes
	var choiceContainer = $("#choices");

	choiceContainer
			.append("<input type='checkbox' id='selectall' /> Select All");

	$.each(datasets, function(key, val) {
		choiceContainer
				.append("<br/><input class='checkbox1' type='checkbox' name='"
						+ key + "' id='id" + key + "'></input>"
						+ "<label for='id" + key + "'>" + val.label
						+ "</label>");
	});

	choiceContainer.find("input").click(plotAccordingToChoices);

	function plotAccordingToChoices() {

		var data = [];

		choiceContainer.find("input:checked").each(function() {
			var key = $(this).attr("name");
			if (key && datasets[key]) {
				data.push(datasets[key]);
			}
		});

		$.plot("#placeholder", data, {
			xaxis : {
				max : 2020,
				tickDecimals : 0
			}
		});
	}

	function defaultSelection() {

		$("input[name=Carlsen]").attr('checked', true);
		$("input[name=Caruana]").attr('checked', true);
		$("input[name=Aronian]").attr('checked', true);
		$("input[name=Anand]").attr('checked', true);
	}

	defaultSelection();

	plotAccordingToChoices();

	$('#selectall').click(function(event) { // on click
		if (this.checked) { // check select status
			$('.checkbox1').each(function() { // loop through each checkbox
				this.checked = true; // select all checkboxes with class
				// "checkbox1"
			});
		} else {
			$('.checkbox1').each(function() { // loop through each checkbox
				this.checked = false; // deselect all checkboxes with class
				// "checkbox1"
			});
		}
		plotAccordingToChoices();
	});
}