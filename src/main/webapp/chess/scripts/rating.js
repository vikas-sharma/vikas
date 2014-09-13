$(function() {

	var datasets = {
		"Carlsen" : {
			label : "Carlsen",
			data : [ [ 2001, 2064 ], [ 2002, 2127 ], [ 2003, 2279 ],
					[ 2004, 2484 ], [ 2005, 2553 ], [ 2006, 2625 ],
					[ 2007, 2690 ], [ 2008, 2733 ], [ 2009, 2776 ],
					[ 2010, 2810 ], [ 2011, 2814 ], [ 2012, 2835 ],
					[ 2013, 2861 ], [ 2014, 2872 ], [ 2015, 2863 ] ]
		},
		"Caruana" : {
			label : "Caruana",
			data : [ [ 2002, 2032 ], [ 2003, 2134 ], [ 2004, 2122 ],
					[ 2005, 2219 ], [ 2006, 2409 ], [ 2007, 2492 ],
					[ 2008, 2598 ], [ 2009, 2646 ], [ 2010, 2675 ],
					[ 2011, 2721 ], [ 2012, 2736 ], [ 2013, 2781 ],
					[ 2014, 2782 ], [ 2015, 2836 ] ]
		},
		"Aronian" : {
			label : "Aronian",
			data : [ [ 2000, 2587 ], [ 2001, 2522 ], [ 2002, 2584 ],
					[ 2003, 2610 ], [ 2004, 2648 ], [ 2005, 2684 ],
					[ 2006, 2752 ], [ 2007, 2744 ], [ 2008, 2739 ],
					[ 2009, 2750 ], [ 2010, 2781 ], [ 2011, 2805 ],
					[ 2012, 2805 ], [ 2013, 2802 ], [ 2014, 2812 ],
					[ 2015, 2793 ] ]
		},
		"Grischuk" : {
			label : "Grischuk",
			data : [ [ 2000, 2581 ], [ 2001, 2663 ], [ 2002, 2671 ],
					[ 2003, 2712 ], [ 2004, 2719 ], [ 2005, 2710 ],
					[ 2006, 2717 ], [ 2007, 2717 ], [ 2008, 2711 ],
					[ 2009, 2733 ], [ 2010, 2736 ], [ 2011, 2773 ],
					[ 2012, 2761 ], [ 2013, 2764 ], [ 2014, 2777 ],
					[ 2015, 2789 ] ]
		},
		"Topalov" : {
			label : "Topalov",
			data : [ [ 2000, 2702 ], [ 2001, 2718 ], [ 2002, 2739 ],
					[ 2003, 2743 ], [ 2004, 2735 ], [ 2005, 2757 ],
					[ 2006, 2801 ], [ 2007, 2783 ], [ 2008, 2780 ],
					[ 2009, 2796 ], [ 2010, 2805 ], [ 2011, 2775 ],
					[ 2012, 2770 ], [ 2013, 2771 ], [ 2014, 2785 ],
					[ 2015, 2789 ] ]
		},
		"Anand" : {
			label : "Anand",
			data : [ [ 2000, 2769 ], [ 2001, 2790 ], [ 2002, 2757 ],
					[ 2003, 2753 ], [ 2004, 2766 ], [ 2005, 2786 ],
					[ 2006, 2792 ], [ 2007, 2779 ], [ 2008, 2799 ],
					[ 2009, 2791 ], [ 2010, 2790 ], [ 2011, 2810 ],
					[ 2012, 2799 ], [ 2013, 2772 ], [ 2014, 2773 ],
					[ 2015, 2785 ] ]
		},
		"Karjakin" : {
			label : "Karjakin",
			data : [ [ 2000, 2206 ], [ 2001, 2269 ], [ 2002, 2460 ],
					[ 2003, 2547 ], [ 2004, 2566 ], [ 2005, 2599 ],
					[ 2006, 2660 ], [ 2007, 2678 ], [ 2008, 2732 ],
					[ 2009, 2706 ], [ 2010, 2720 ], [ 2011, 2776 ],
					[ 2012, 2769 ], [ 2013, 2780 ], [ 2014, 2759 ],
					[ 2015, 2777 ] ]
		},
		"Nakamura" : {
			label : "Nakamura",
			data : [ [ 2000, 2261 ], [ 2001, 2364 ], [ 2002, 2430 ],
					[ 2003, 2520 ], [ 2004, 2571 ], [ 2005, 2613 ],
					[ 2006, 2644 ], [ 2007, 2651 ], [ 2008, 2670 ],
					[ 2009, 2699 ], [ 2010, 2708 ], [ 2011, 2751 ],
					[ 2012, 2759 ], [ 2013, 2769 ], [ 2014, 2789 ],
					[ 2015, 2764 ] ]
		},
		"Vachier-Lagrave" : {
			label : "Vachier-Lagrave",
			data : [ [ 2000, 2186 ], [ 2001, 2198 ], [ 2002, 2278 ],
					[ 2003, 2348 ], [ 2004, 2427 ], [ 2005, 2488 ],
					[ 2006, 2542 ], [ 2007, 2573 ], [ 2008, 2637 ],
					[ 2009, 2696 ], [ 2010, 2730 ], [ 2011, 2715 ],
					[ 2012, 2699 ], [ 2013, 2713 ], [ 2014, 2745 ],
					[ 2015, 2763 ] ]
		},
		"Mamedyarov" : {
			label : "Mamedyarov",
			data : [ [ 2000, 2201 ], [ 2001, 2479 ], [ 2002, 2522 ],
					[ 2003, 2596 ], [ 2004, 2646 ], [ 2005, 2657 ],
					[ 2006, 2709 ], [ 2007, 2754 ], [ 2008, 2760 ],
					[ 2009, 2724 ], [ 2010, 2741 ], [ 2011, 2772 ],
					[ 2012, 2747 ], [ 2013, 2766 ], [ 2014, 2757 ],
					[ 2015, 2760 ] ]
		},
		"Kramnik" : {
			label : "Kramnik",
			data : [ [ 2000, 2758 ], [ 2001, 2799 ], [ 2002, 2811 ],
					[ 2003, 2809 ], [ 2004, 2777 ], [ 2005, 2754 ],
					[ 2006, 2741 ], [ 2007, 2766 ], [ 2008, 2799 ],
					[ 2009, 2759 ], [ 2010, 2788 ], [ 2011, 2784 ],
					[ 2012, 2801 ], [ 2013, 2810 ], [ 2014, 2787 ],
					[ 2015, 2760 ] ]
		}
	};

	// insert checkboxes
	var choiceContainer = $("#choices");

	choiceContainer
			.append("<input type='checkbox' id='selectall' checked='checked' /> Select All");

	$.each(datasets, function(key, val) {
		choiceContainer
				.append("<br/><input class='checkbox1' type='checkbox' name='"
						+ key + "' checked='checked' id='id" + key
						+ "'></input>" + "<label for='id" + key + "'>"
						+ val.label + "</label>");
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

});
