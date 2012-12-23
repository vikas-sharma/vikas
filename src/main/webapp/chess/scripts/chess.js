$(document).ready(function() {

	newGame(fen, false);

	$('#WQ').click(function() {
		$("#square_" + promotedSq).children("img").remove();
		$("#square_" + promotedSq).append(wqueen);
		playMove(pawnFromSq, promotedSq, QUEEN);
	});
	$('#WR').click(function() {
		$("#square_" + promotedSq).children("img").remove();
		$("#square_" + promotedSq).append(wrook);
		playMove(pawnFromSq, promotedSq, ROOK);
	});
	$('#WB').click(function() {
		$("#square_" + promotedSq).children("img").remove();
		$("#square_" + promotedSq).append(wbishop);
		playMove(pawnFromSq, promotedSq, BISHOP);
	});
	$('#WN').click(function() {
		$("#square_" + promotedSq).children("img").remove();
		$("#square_" + promotedSq).append(wknight);
		playMove(pawnFromSq, promotedSq, KNIGHT);
	});
	$('#BQ').click(function() {
		$("#square_" + promotedSq).children("img").remove();
		$("#square_" + promotedSq).append(bqueen);
		playMove(pawnFromSq, promotedSq, QUEEN);
	});
	$('#BR').click(function() {
		$("#square_" + promotedSq).children("img").remove();
		$("#square_" + promotedSq).append(brook);
		playMove(pawnFromSq, promotedSq, ROOK);
	});
	$('#BB').click(function() {
		$("#square_" + promotedSq).children("img").remove();
		$("#square_" + promotedSq).append(bbishop);
		playMove(pawnFromSq, promotedSq, BISHOP);
	});
	$('#BN').click(function() {
		$("#square_" + promotedSq).children("img").remove();
		$("#square_" + promotedSq).append(bknight);
		playMove(pawnFromSq, promotedSq, KNIGHT);
	});
});

var fen = 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1';

var separator = "XXX";

var direction = false;
var thinkingMode = false;

function initDragDrop() {

	for ( var sq = 0; sq < 64; sq++) {
		$("#square_" + sq).children("img").draggable({
			addClasses : false
		}, {
			containment : '#chess-container'
		}, {
			revert : 'invalid'
		});
	}
}

function destroyAllDragDrop() {

	for ( var sq = 0; sq < 64; sq++) {
		if ($("#square_" + sq).children("img").length != 0
				&& $("#square_" + sq).children("img").draggable().length != 0) {
			$("#square_" + sq).children("img").draggable('destroy');
		}
		if ($("#square_" + sq).droppable()) {
			$("#square_" + sq).droppable('destroy');
		}
	}
}

// droppable is toSq and draggableArray is list of fromSq

function updateDragDrop(droppable, draggableArray) {
	draggableArray = draggableArray.replace(/\[/g, "").replace(/\]/g, "")
			.split(",");

	// alert(droppable + " :: " + draggableArray);

	$(droppable).droppable(
			{
				addClasses : false
			},
			{
				accept : function(draggable) {

					for ( var i in draggableArray) {
						if (direction) {
							dragFrom = 63 - parseInt(draggableArray[i].replace(
									/^\s+|\s+$/g, ''));
							if (("square_" + dragFrom) == $(draggable).parent()
									.attr("id")) {
								return true;
							}
						} else {
							if (("square_" + draggableArray[i].replace(
									/^\s+|\s+$/g, '')) == $(draggable).parent()
									.attr("id")) {
								return true;
							}
						}
					}
				}
			},
			{
				drop : function(event, ui) {

					var fromSq = $(ui.draggable).parent().attr("id").substring(
							7);
					var toSq = droppable.substring(8);

					var to_piece = $(droppable).children("img");

					if (to_piece.length == 0) { // if no piece on to square
						var sq_diff = fromSq - toSq;
						// enpassant move
						if (sq_diff == 7 || sq_diff == 9 || sq_diff == -7
								|| sq_diff == -9) {
							if ($(ui.draggable).hasClass('wpawn')) { // white
								// pawn
								to_piece = $("#square_" + (parseInt(toSq) + 8))
										.children("img");
								to_piece.remove();
							} else if ($(ui.draggable).hasClass('bpawn')) { // black
								// pawn
								to_piece = $("#square_" + (parseInt(toSq) - 8))
										.children("img");
								to_piece.remove();
							}
						}
					} else {
						to_piece.remove();
					}

					var $this = $(this);
					$this.append(ui.draggable);
					var width = $this.width();
					var height = $this.height();
					var cntrLeft = width / 2 - ui.draggable.width() / 2;
					var cntrTop = height / 2 - ui.draggable.height() / 2 + 2;
					if (ui.draggable.hasClass('wpawn')
							|| ui.draggable.hasClass('bpawn')) {
						cntrLeft -= 10;
					} else {
						cntrLeft -= 4;
					}
					ui.draggable.css({
						left : cntrLeft + "px",
						top : cntrTop + "px"
					});

					// castling move
					if ($(ui.draggable).hasClass('wking')
							|| $(ui.draggable).hasClass('bking')) {
						if (direction) {
							if (fromSq == 3 && toSq == 1) {
								$("#square_2").append(
										$("#square_0").children("img"));
							} else if (fromSq == 3 && toSq == 5) {
								$("#square_4").append(
										$("#square_7").children("img"));
							} else if (fromSq == 59 && toSq == 57) {
								$("#square_58").append(
										$("#square_56").children("img"));
							} else if (fromSq == 59 && toSq == 61) {
								$("#square_60").append(
										$("#square_63").children("img"));
							}
						} else {
							if (fromSq == 60 && toSq == 62) {
								$("#square_61").append(
										$("#square_63").children("img"));
							} else if (fromSq == 60 && toSq == 58) {
								$("#square_59").append(
										$("#square_56").children("img"));
							} else if (fromSq == 4 && toSq == 6) {
								$("#square_5").append(
										$("#square_7").children("img"));
							} else if (fromSq == 4 && toSq == 2) {
								$("#square_3").append(
										$("#square_0").children("img"));
							}
						}
					}

					if (direction) {
						fromSq = 63 - parseInt(fromSq);
						toSq = 63 - parseInt(toSq);
					}

					// pawn promotion move
					if ($(ui.draggable).hasClass('wpawn') && fromSq >= 8
							&& fromSq <= 15) {

						openPromotionWindow('white', fromSq, toSq);
					} else if ($(ui.draggable).hasClass('bpawn')
							&& fromSq >= 48 && fromSq <= 55) {

						openPromotionWindow('black', fromSq, toSq);
					} else {
						playMove(fromSq, toSq, EMPTY);
					}
				}
			});
}

function trim(txt) {
	if (txt == null) {
		return "";
	}
	return txt.replace(/^\s*/g, "").replace(/\s*$/g, "");
}

var moveList;

var wking = "<img src='images/WK.png' class='wking'>";
var wqueen = "<img src='images/WQ.png' class='wqueen'>";
var wrook = "<img src='images/WR.png' class='wrook'>";
var wbishop = "<img src='images/WB.png' class='wbishop'>";
var wknight = "<img src='images/WN.png' class='wknight'>";
var wpawn = "<img src='images/WP.png' class='wpawn'>";

var bking = "<img src='images/BK.png' class='bking'>";
var bqueen = "<img src='images/BQ.png' class='bqueen'>";
var brook = "<img src='images/BR.png' class='brook'>";
var bbishop = "<img src='images/BB.png' class='bbishop'>";
var bknight = "<img src='images/BN.png' class='bknight'>";
var bpawn = "<img src='images/BP.png' class='bpawn'>";

var EMPTY = 0;
var ROOK = 1;
var KNIGHT = 2;
var BISHOP = 3;
var QUEEN = 4;
var KING = 5;
var PAWN = 6;

function newGame(f, switchFlag) {
	if (f == undefined) {
		fen = 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1';
	} else {
		fen = f;
	}

	// if its not a switch side event then white on top
	if (!switchFlag) {
		direction = false;
	}

	$("#white_promotion_window").css({
		visibility : 'hidden'
	});
	$("#black_promotion_window").css({
		visibility : 'hidden'
	});

	for ( var sq = 0; sq <= 63; sq++) {
		piece = $("#square_" + sq).children("img");
		if (piece.length != 0) {
			piece.remove();
		}
	}

	var arr = trim(fen).split("\\s+");
	var boardArr = arr[0].split("/");

	var sq, square;

	for ( var row = 0; row < 8; row++) {
		var index = 0;
		for ( var i = 0; i < boardArr[row].length; i++) {

			sq = row * 8 + index;
			if (direction) {
				sq = 63 - sq;
			}

			square = $("#square_" + sq);

			var c = boardArr[row].charAt(i);

			if (!/(^\d\d*$)/.test(c)) {
				index++;
			}

			switch (c) {
			case 'p':
				square.append(bpawn);
				break;
			case 'n':
				square.append(bknight);
				break;
			case 'b':
				square.append(bbishop);
				break;
			case 'r':
				square.append(brook);
				break;
			case 'q':
				square.append(bqueen);
				break;
			case 'k':
				square.append(bking);
				break;
			case 'P':
				square.append(wpawn);
				break;
			case 'N':
				square.append(wknight);
				break;
			case 'B':
				square.append(wbishop);
				break;
			case 'R':
				square.append(wrook);
				break;
			case 'Q':
				square.append(wqueen);
				break;
			case 'K':
				square.append(wking);
				break;
			default:
				index += parseInt(c);
			}
		}
	}

	if (switchFlag) {
		return;
	}

	destroyAllDragDrop();

	thinkingMode = true;
	var d = new Date();
	var url = "engine/init.htm?epoch=" + d.getMilliseconds();
	$.getJSON(url, {
		fen : fen
	}, function(data) {
		var response = data.dragDrop.split(separator);
		var dragDrop = response[0];
		fen = response[1];
		pgnMoves = response[2];
		dragDrop = dragDrop.replace(/\{/g, "").replace(/\}/g, "").split('=');

		initDragDrop();

		var lastKey = null;
		for ( var i in dragDrop) {
			arr = dragDrop[i].split(',');
			key = arr[arr.length - 1];
			if (lastKey != null) {
				if (arr.length > 1 && i < dragDrop.length - 1) {
					values = arr.splice(0, arr.length - 1); // delete last
					// element
				} else {
					values = arr;
				}
				if (direction) {
					updateDragDrop('#square_' + (63 - parseInt(lastKey)),
							values.join(','));
				} else {
					updateDragDrop('#square_' + lastKey, values.join(','));
				}
			}
			lastKey = trim(key);
		}
		thinkingMode = false;
	});
}

var pawnFromSq, promotedSq;

function openPromotionWindow(color, fromSq, toSq) {

	// disable all the previous draggable
	for ( var sq = 0; sq < 64; sq++) {
		if ($("#square_" + sq).children("img").draggable()) {
			$("#square_" + sq).children("img").draggable('disable');
		}
	}

	pawnFromSq = fromSq;
	promotedSq = toSq;

	if (color == 'white') {
		$("#white_promotion_window").css({
			visibility : 'visible'
		});
	} else {
		$("#black_promotion_window").css({
			visibility : 'visible'
		});
	}
}

function playMove(fromSq, toSq, promotedPiece) {

	if (promotedPiece != EMPTY) {
		$("#white_promotion_window").css({
			visibility : 'hidden'
		});
		$("#black_promotion_window").css({
			visibility : 'hidden'
		});

		for ( var sq = 0; sq < 64; sq++) {
			if ($("#square_" + sq).children("img").draggable()) {
				$("#square_" + sq).children("img").draggable('enable');
			}
		}
	}

	destroyAllDragDrop();
	thinkingMode = true;
	var d = new Date();
	var url = "engine/drag_drop.htm?epoch=" + d.getMilliseconds();
	$.getJSON(url, {
		fromSq : fromSq,
		toSq : toSq,
		promotedPiece : promotedPiece,
		fen : fen
	}, function(data) {
		var response = data.dragDrop.split(separator);
		// var userMove = response[0];
		var dragDrop = response[1];
		var compMove = response[2];
		fen = response[3];
		var message = response[4];
		pgnMoves = response[5];

		makeCompMove(compMove);

		if (trim(message) != '') {
			alert(message);
			thinkingMode = false;
			return;
		}

		initDragDrop();

		dragDrop = dragDrop.replace(/\{/g, "").replace(/\}/g, "").split('=');
		var lastKey = null;
		for ( var i in dragDrop) {
			arr = dragDrop[i].split(',');
			key = arr[arr.length - 1];
			if (lastKey != null) {
				if (arr.length > 1) {
					values = arr.splice(0, arr.length - 1); // delete last
					// element
				} else {
					values = arr;
				}
				if (direction) {
					updateDragDrop('#square_' + (63 - parseInt(lastKey)),
							values.join(','));
				} else {
					updateDragDrop('#square_' + lastKey, values.join(','));
				}
			}
			lastKey = trim(key);
		}
		thinkingMode = false;
	});
}

function makeCompMove(move) {

	var piece = (move >>> 12) & 7;
	var fromSq = move & 63;
	var toSq = (move >>> 6) & 63;
	var pp = (move >>> 18) & 7;

	if (direction) {
		fromSq = 63 - fromSq;
		toSq = 63 - toSq;
	}

	var fromPiece = $("#square_" + fromSq).children("img");
	var toPiece = $("#square_" + toSq).children("img");

	if (toPiece.length == 0) { // if no piece on to square
		sqDiff = fromSq - toSq;
		if (piece == PAWN
				&& (sqDiff == 7 || sqDiff == 9 || sqDiff == -7 || sqDiff == -9)) { // enpassant
			// move
			if (fromPiece.hasClass('wpawn') != direction) { // white side
				toPiece = $("#square_" + (toSq + 8)).children("img");
			} else {
				toPiece = $("#square_" + (toSq - 8)).children("img");
			}
			toPiece.remove();
		}
	} else {
		toPiece.remove();
	}

	if (pp == EMPTY) {
		$("#square_" + toSq).append(fromPiece);
	} else {
		if (fromPiece.hasClass('wpawn')) { // white side

			fromPiece.remove();
			if (pp == QUEEN) {
				$("#square_" + toSq).append(wqueen);
			} else if (pp == KNIGHT) {
				$("#square_" + toSq).append(wknight);
			} else if (pp == ROOK) {
				$("#square_" + toSq).append(wrook);
			} else if (pp == BISHOP) {
				$("#square_" + toSq).append(wbishop);
			}
		} else { // black side

			fromPiece.remove();
			if (pp == QUEEN) {
				$("#square_" + toSq).append(bqueen);
			} else if (pp == KNIGHT) {
				$("#square_" + toSq).append(bknight);
			} else if (pp == ROOK) {
				$("#square_" + toSq).append(brook);
			} else if (pp == BISHOP) {
				$("#square_" + toSq).append(bbishop);
			}
		}
	}

	if (piece == KING) { // castling check
		if (direction) {
			if (fromSq == 3 && toSq == 1) {
				fromPiece = $("#square_0").children("img");
				$("#square_2").append(fromPiece);
			} else if (fromSq == 3 && toSq == 5) {
				fromPiece = $("#square_7").children("img");
				$("#square_4").append(fromPiece);
			} else if (fromSq == 59 && toSq == 57) {
				fromPiece = $("#square_56").children("img");
				$("#square_58").append(fromPiece);
			} else if (fromSq == 59 && toSq == 61) {
				fromPiece = $("#square_63").children("img");
				$("#square_60").append(fromPiece);
			}
		} else {
			if (fromSq == 60 && toSq == 62) {
				fromPiece = $("#square_63").children("img");
				$("#square_61").append(fromPiece);
			} else if (fromSq == 60 && toSq == 58) {
				fromPiece = $("#square_56").children("img");
				$("#square_59").append(fromPiece);
			} else if (fromSq == 4 && toSq == 6) {
				fromPiece = $("#square_7").children("img");
				$("#square_5").append(fromPiece);
			} else if (fromSq == 4 && toSq == 2) {
				fromPiece = $("#square_0").children("img");
				$("#square_3").append(fromPiece);
			}
		}
	}
}

function switchSides() {

	if (thinkingMode) {
		return;
	}

	direction = !direction;
	newGame(fen, true);

	destroyAllDragDrop();

	thinkingMode = true;
	var d = new Date();
	var url = "engine/play.htm?epoch=" + d.getMilliseconds();
	$.getJSON(url, {
		fen : fen
	}, function(data) {
		var response = data.dragDrop.split(separator);
		var dragDrop = response[0];
		var compMove = response[1];
		fen = response[2];
		var message = response[3];
		pgnMoves = response[4];

		makeCompMove(compMove);

		if (trim(message) != '') {
			alert(message);
			thinkingMode = false;
			return;
		}

		initDragDrop();

		dragDrop = dragDrop.replace(/\{/g, "").replace(/\}/g, "").split('=');
		var lastKey = null;
		for ( var i in dragDrop) {
			arr = dragDrop[i].split(',');
			key = arr[arr.length - 1];
			if (lastKey != null) {
				if (arr.length > 1) {
					values = arr.splice(0, arr.length - 1); // delete last
					// element
				} else {
					values = arr;
				}
				if (direction) {
					updateDragDrop('#square_' + (63 - parseInt(lastKey)),
							values.join(','));
				} else {
					updateDragDrop('#square_' + lastKey, values.join(','));
				}
			}
			lastKey = trim(key);
		}
		thinkingMode = false;
	});
}

function takebackMove() {
	alert('lol takeback is not allowed, my dear friend');
}