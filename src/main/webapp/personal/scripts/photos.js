$(document).ready(function() {

	$("#access_denied").dialog({
		modal : true
	});
});

lastPhotoNo = 0;
lastFamilyPhotoNo = 0;

function loadAlbum(albumNo) {

	for ( var i = 1; i <= 11; i++) {
		$("#album_" + i).hide();
	}
	$("#album_" + albumNo).show();

	if (lastPhotoNo != 0) {
		document.getElementById('photo_' + lastPhotoNo).style.background = "white";
	}
	document.getElementById('photo_' + albumNo).style.background = "gray";
	lastPhotoNo = albumNo;
}

function loadFamilyAlbum(albumNo) {

	for ( var i = 1; i <= 8; i++) {
		$("#album_" + i).hide();
	}
	$("#album_" + albumNo).show();

	if (lastFamilyPhotoNo != 0) {
		document.getElementById('photo_' + lastFamilyPhotoNo).style.background = "white";
	}
	document.getElementById('photo_' + albumNo).style.background = "gray";
	lastFamilyPhotoNo = albumNo;
}