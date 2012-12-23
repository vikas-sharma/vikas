$(document).ready ( function () {

	$("#access_denied").dialog({modal: true});
});

function loadAlbum(albumNo) {

	for (var i = 1; i <= 11; i++) {
		$("#album_" + i).hide();
	}
	$("#album_" + albumNo).show();
}

function loadFamilyAlbum(albumNo) {

	for (var i = 1; i <= 8; i++) {
		$("#album_" + i).hide();
	}
	$("#album_" + albumNo).show();
}