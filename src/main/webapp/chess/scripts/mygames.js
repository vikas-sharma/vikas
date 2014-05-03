$(document).ready(function() {

	function autoPlay() {

		if (playMode && !loading_flag) {
			goforward(true);
			if (currentMoveNo >= moveList.length + 1) {
				pause();
			}
		}
	}

	setInterval(autoPlay, 1000);
});

var moveList;

var currentMoveNo = -1;

var wking = "<img src='images/WK.png' style='position:relative; top:10%; left:10%;'>";
var wqueen = "<img src='images/WQ.png' style='position:relative; top:10%; left:10%;'>";
var wrook = "<img src='images/WR.png' style='position:relative; top:10%; left:10%;'>";
var wbishop = "<img src='images/WB.png' style='position:relative; top:10%; left:10%;'>";
var wknight = "<img src='images/WN.png' style='position:relative; top:10%; left:10%;'>";
var wpawn = "<img src='images/WP.png' style='position:relative; top:18%; left:24%;'>";

var bking = "<img src='images/BK.png' style='position:relative; top:10%; left:10%;'>";
var bqueen = "<img src='images/BQ.png' style='position:relative; top:10%; left:10%;'>";
var brook = "<img src='images/BR.png' style='position:relative; top:10%; left:10%;'>";
var bbishop = "<img src='images/BB.png' style='position:relative; top:10%; left:10%;'>";
var bknight = "<img src='images/BN.png' style='position:relative; top:10%; left:10%;'>";
var bpawn = "<img src='images/BP.png' style='position:relative; top:10%; left:24%;'>";

var EMPTY = 0;
var ROOK = 1;
var KNIGHT = 2;
var BISHOP = 3;
var QUEEN = 4;
var KING = 5;
var PAWN = 6;

var oldMoveNo = -1;

var playMode = false;

var loading_flag = false;

var lastKey = 0;

function loadGame(gameNo) {

	if (lastKey != 0) {
		document.getElementById('game_' + lastKey).style.background = "white";
	}
	document.getElementById('game_' + gameNo).style.background = "gray";
	lastKey = gameNo;

	loading_flag = true;

	$("#pgn_string").html("Loading...");

	$.getJSON("pgnparser/moves.htm", {
		gameNo : gameNo
	}, function(data) {

		moveList = data.moveList;

		$.getJSON("pgnparser/pgn.htm", {
			gameNo : gameNo
		}, function(data) {
			$("#pgn_string").html(data.pgnString);
			loading_flag = false;
		});
	});

	currentMoveNo = -1;
	oldMoveNo = -1;

	pause();

	var square, piece;

	for ( var sq = 0; sq <= 63; sq++) {
		piece = $("#square_" + sq).children("img");
		if (piece.length != 0) {
			piece.remove();
		}
	}

	for ( var sq = 0; sq <= 63; sq++) {

		square = $("#square_" + sq);

		if (sq >= 16 && sq <= 47) {
			continue;
		} else if (sq >= 48 && sq <= 55) {
			square.append(wpawn);
		} else if (sq >= 8 && sq <= 15) {
			square.append(bpawn);
		} else if (sq >= 0 && sq <= 7) {
			if (sq == 0 || sq == 7) {
				square.append(brook);
			} else if (sq == 1 || sq == 6) {
				square.append(bknight);
			} else if (sq == 2 || sq == 5) {
				square.append(bbishop);
			} else if (sq == 3) {
				square.append(bqueen);
			} else if (sq == 4) {
				square.append(bking);
			}
		} else if (sq >= 56 && sq <= 63) {
			if (sq == 56 || sq == 63) {
				square.append(wrook);
			} else if (sq == 57 || sq == 62) {
				square.append(wknight);
			} else if (sq == 58 || sq == 61) {
				square.append(wbishop);
			} else if (sq == 59) {
				square.append(wqueen);
			} else if (sq == 60) {
				square.append(wking);
			}
		}
	}
	return false; // required to stop post request
}

function begin() {

	if (loading_flag) {
		return;
	}

	pause();

	while (currentMoveNo >= 0) {
		goback();
	}
}

function goback() {

	if (loading_flag || currentMoveNo < 0) {
		return;
	}

	pause();

	var move = moveList[currentMoveNo];

	var piece = (move >>> 12) & 7;
	var capturedPiece = (move >>> 15) & 7;
	var from_sq = move & 63;
	var to_sq = (move >>> 6) & 63;
	var pp = (move >>> 18) & 7;

	if (piece == PAWN && capturedPiece == EMPTY) { // if no piece on to square
		var sq_diff = from_sq - to_sq;
		if ((sq_diff == 7 || sq_diff == 9 || sq_diff == -7 || sq_diff == -9)) { // enpassant
			// move
			if (currentMoveNo % 2 == 0) { // white side
				$("#square_" + (to_sq + 8)).append(bpawn);
			} else {
				$("#square_" + (to_sq - 8)).append(wpawn);
			}
		}
	}

	var to_piece = $("#square_" + to_sq).children("img");

	if (pp == EMPTY) {
		$("#square_" + from_sq).append(to_piece);
	} else {
		to_piece.remove();
		if (currentMoveNo % 2 == 0) { // white side
			$("#square_" + from_sq).append(wpawn);
		} else {
			$("#square_" + from_sq).append(bpawn);
		}
	}

	if (capturedPiece != EMPTY) {
		var to_square = $("#square_" + to_sq);
		if (currentMoveNo % 2 == 0) { // white side
			if (capturedPiece == QUEEN) {
				to_square.append(bqueen);
			} else if (capturedPiece == ROOK) {
				to_square.append(brook);
			} else if (capturedPiece == BISHOP) {
				to_square.append(bbishop);
			} else if (capturedPiece == KNIGHT) {
				to_square.append(bknight);
			} else if (capturedPiece == PAWN) {
				to_square.append(bpawn);
			}
		} else {
			if (capturedPiece == QUEEN) {
				to_square.append(wqueen);
			} else if (capturedPiece == ROOK) {
				to_square.append(wrook);
			} else if (capturedPiece == BISHOP) {
				to_square.append(wbishop);
			} else if (capturedPiece == KNIGHT) {
				to_square.append(wknight);
			} else if (capturedPiece == PAWN) {
				to_square.append(wpawn);
			}
		}
	}

	if (piece == KING) { // castling check
		if (from_sq == 60 && to_sq == 62) {
			from_piece = $("#square_61").children("img");
			$("#square_63").append(from_piece);
		} else if (from_sq == 60 && to_sq == 58) {
			from_piece = $("#square_59").children("img");
			$("#square_56").append(from_piece);
		} else if (from_sq == 4 && to_sq == 6) {
			from_piece = $("#square_5").children("img");
			$("#square_7").append(from_piece);
		} else if (from_sq == 4 && to_sq == 2) {
			from_piece = $("#square_3").children("img");
			$("#square_0").append(from_piece);
		}
	}
	currentMoveNo--;
	changeFocusToCurrentMove();
}

function goforward(autoPlay) {

	if (loading_flag || currentMoveNo >= moveList.length + 1) {
		return;
	}

	if (!autoPlay) {
		pause();
	}

	currentMoveNo++;
	var move = moveList[currentMoveNo];
	var piece = (move >>> 12) & 7;
	var from_sq = move & 63;
	var to_sq = (move >>> 6) & 63;
	var pp = (move >>> 18) & 7;

	var to_piece = $("#square_" + to_sq).children("img");

	if (to_piece.length == 0) { // if no piece on to square
		sq_diff = from_sq - to_sq;
		if (piece == PAWN
				&& (sq_diff == 7 || sq_diff == 9 || sq_diff == -7 || sq_diff == -9)) { // enpassant
			// move
			if (currentMoveNo % 2 == 0) { // white side
				to_piece = $("#square_" + (to_sq + 8)).children("img");
			} else {
				to_piece = $("#square_" + (to_sq - 8)).children("img");
			}
			to_piece.remove();
		}
	} else {
		to_piece.remove();
	}

	var from_piece = $("#square_" + from_sq).children("img");

	if (pp == EMPTY) {
		$("#square_" + to_sq).append(from_piece);
	} else {
		from_piece.remove();
		if (currentMoveNo % 2 == 0) { // white side

			if (pp == QUEEN) {
				$("#square_" + to_sq).append(wqueen);
			} else if (pp == KNIGHT) {
				$("#square_" + to_sq).append(wknight);
			} else if (pp == ROOK) {
				$("#square_" + to_sq).append(wrook);
			} else if (pp == BISHOP) {
				$("#square_" + to_sq).append(wbishop);
			}
		} else { // black side

			if (pp == QUEEN) {
				$("#square_" + to_sq).append(bqueen);
			} else if (pp == KNIGHT) {
				$("#square_" + to_sq).append(bknight);
			} else if (pp == ROOK) {
				$("#square_" + to_sq).append(brook);
			} else if (pp == BISHOP) {
				$("#square_" + to_sq).append(bbishop);
			}
		}
	}

	if (piece == KING) { // castling check
		if (from_sq == 60 && to_sq == 62) {
			from_piece = $("#square_63").children("img");
			$("#square_61").append(from_piece);
		} else if (from_sq == 60 && to_sq == 58) {
			from_piece = $("#square_56").children("img");
			$("#square_59").append(from_piece);
		} else if (from_sq == 4 && to_sq == 6) {
			from_piece = $("#square_7").children("img");
			$("#square_5").append(from_piece);
		} else if (from_sq == 4 && to_sq == 2) {
			from_piece = $("#square_0").children("img");
			$("#square_3").append(from_piece);
		}
	}
	changeFocusToCurrentMove();
}

function end() {

	if (loading_flag) {
		return;
	}

	pause();
	while (currentMoveNo < moveList.length) {
		goforward();
	}
}

function goToPosition(moveNo) {

	if (loading_flag) {
		return;
	}

	pause();

	if (currentMoveNo > moveNo) {
		while (currentMoveNo > moveNo) {
			goback();
		}
	} else if (currentMoveNo < moveNo) {
		while (currentMoveNo < moveNo) {
			goforward();
		}
	}
	changeFocusToCurrentMove();
}

function play() {

	if (loading_flag) {
		return;
	}

	playMode = true;
	$("#playBtn").hide();
	$("#pauseBtn").show();
}

function pause() {

	playMode = false;
	$("#pauseBtn").hide();
	$("#playBtn").show();
}

function changeFocusToCurrentMove() {

	if (currentMoveNo < 0 || currentMoveNo >= moveList.length) {
		return;
	}

	if (oldMoveNo != -1) {
		document.getElementById('pgn_' + oldMoveNo).style.background = "#FEFDE8";
	}
	document.getElementById('pgn_' + currentMoveNo).style.background = "gray";
	oldMoveNo = currentMoveNo;
}

function filter(opt, length) {

	for ( var i = 1; i <= length; i++) {
		$("#game_" + i).show();
		if (opt == 'imgm') {
			if (i != 39 && i != 51) {
				$("#game_" + i).hide();
			}
		} else if (opt == 'fav') {
			if (i != 15 && i != 22 && i != 27 && i != 34 && i != 35 && i != 39
					&& i != 41 && i != 42 && i != 49 && i != 50 && i != 51) {
				$("#game_" + i).hide();
			}
		}
	}

	if (opt == 'all') {
		loadGame(1);
	} else if (opt == 'imgm') {
		loadGame(26);
	} else {
		loadGame(5);
	}
}