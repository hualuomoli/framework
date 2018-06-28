(function(){

	$('h5').on('click', function(){
		$(this).next('table').toggleClass('fade');
	});

	$('.level-control').on('click', function(){
		$(this).toggleClass('open');
		$(this).find('td:nth-child(1) i, li:nth-child(1) i').toggleClass('hide');
		var needdOpen = $(this).attr('data-open');
		console.log(needdOpen);
		$(needdOpen).toggleClass('hide');
	});

})(jQuery);