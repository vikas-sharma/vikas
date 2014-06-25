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

	for ( var row = 0; row < 8; row++) {
		var index = 0;
		for ( var i = 0; i < boardArr[row].length; i++) {

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

	function trim(txt) {
		if (txt == null) {
			return "";
		}
		return txt.replace(/^\s*/g, "").replace(/\s*$/g, "");
	}
}