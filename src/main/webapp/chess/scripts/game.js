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

function newGame(fen, whiteOnTop) {

	var arr = trim(fen).split("\\s+");
	var boardArr = arr[0].split("/");

	var sq, square;

	for (var row = 0; row < 8; row++) {
		var index = 0;
		for (var i = 0; i < boardArr[row].length; i++) {

			sq = row * 8 + index;
			if (!whiteOnTop) {
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
}

var direction = false;

function newGameWithDragDrop(fen, whiteOnTop, dragDrop) {

	newGame(fen, whiteOnTop);

	direction = !whiteOnTop

	destroyAllDragDrop();

	dragDrop = dragDrop.replace(/\{/g, "").replace(/\}/g, "").split('=');

	initDragDrop();

	var lastKey = null;
	for ( var i in dragDrop) {
		arr = dragDrop[i].split(',');
		key = arr[arr.length - 1];
		if (lastKey != null) {
			if (arr.length > 1 && i < dragDrop.length - 1) {
				values = arr.splice(0, arr.length - 1); // delete last element
			} else {
				values = arr;
			}
			if (direction) {
				updateDragDrop('#square_' + (63 - parseInt(lastKey)), values
						.join(','));
			} else {
				updateDragDrop('#square_' + lastKey, values.join(','));
			}
		}
		lastKey = trim(key);
	}
}

function initDragDrop() {

	for (var sq = 0; sq < 64; sq++) {
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

	for (var sq = 0; sq < 64; sq++) {
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
