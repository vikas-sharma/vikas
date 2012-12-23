package com.vikas.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Drawer extends JPanel implements MouseListener, MouseMotionListener, Constants {

	private Image []images = new Image[13];

	private JButton flipBtn;
	private JButton playBtn;
	private JButton newGameBtn;
	private JTextField fenTextField;

	// dragging variables
	private int px;
	private int py;

	private int offX;
	private int offY;

	boolean dragging = false;

	private int width;
	private int height;

	private int fromSq;

	private int startX;
	private int startY;

	private boolean direction;

	private Engine s = new Engine();

	private boolean illegalFlag = false;
	private String message = "";

	private BitBoard board = null;

	public Drawer(Image []images) {

		this.images = images;

		addMouseListener(this);
		addMouseMotionListener(this);

        width = 50;
		height = 50;

		startX = 50;
		startY = 50;

		String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		fenTextField = new JTextField(fen);

		newGameBtn = new JButton("New Game");
		newGameBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				initBoard();
				repaint();
			}
		});      

		playBtn = new JButton("Play");
		playBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				direction = !board.color;
				play();
			}
		});      

		flipBtn = new JButton("Flip");
		flipBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				direction = !direction;
				repaint();
			}
		});      

		add(fenTextField);
		add(newGameBtn);
		add(playBtn);
		add(flipBtn);

		// uncomment below 2 lines only when opening data change.
//		PGNParser.readPGN();
//		OpeningTable.generateOpeningBook();
		
		// commented as i am using colin's database instead of mine
//		OpeningTable.readOpeningBook();

		initBoard();
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                    RenderingHints.VALUE_ANTIALIAS_ON);

		if (board == null) {
			initBoard();
		}

		boolean whiteSq = true;
		for(int sq = A8; sq <= H1; sq++) {
			
			if (sq % 8 != 0) {
				whiteSq = !whiteSq;
			}
			if (whiteSq) {
				g2.setColor(new Color(Integer.parseInt( "F3DCC2", 16)));
			} else {
				g2.setColor(new Color(Integer.parseInt( "DDA37B", 16)));
			}
			int x = (sq % 8) * height;
			int y = (sq / 8) * width;
			if (!direction) {
				x = ((63 - sq) % 8) * height;
				y = ((63 - sq) / 8) * width;
			}
			g2.fillRect(x + startX, y + startY, width, height);

			if ((board.whitepawns & mask[sq]) != 0) {
				drawImage(g2, images[WPAWN], sq);
			}
			else if ((board.whiteknights & mask[sq]) != 0) {
				drawImage(g2, images[WKNIGHT], sq);
			}
			else if ((board.whitebishops & mask[sq]) != 0) {
				drawImage(g2, images[WBISHOP], sq);
			}
			else if ((board.whiterooks & mask[sq]) != 0) {
				drawImage(g2, images[WROOK], sq);
			}
			else if ((board.whitequeens & mask[sq]) != 0) {
				drawImage(g2, images[WQUEEN], sq);
			}
			else if ((board.whiteking & mask[sq]) != 0) {
				drawImage(g2, images[WKING], sq);
			}
			else if ((board.blackpawns & mask[sq]) != 0) {
				drawImage(g2, images[BPAWN], sq);
			}
			else if ((board.blackknights & mask[sq]) != 0) {
				drawImage(g2, images[BKNIGHT], sq);
			}
			else if ((board.blackbishops & mask[sq]) != 0) {
				drawImage(g2, images[BBISHOP], sq);
			}
			else if ((board.blackrooks & mask[sq]) != 0) {
				drawImage(g2, images[BROOK], sq);
			}
			else if ((board.blackqueens & mask[sq]) != 0) {
				drawImage(g2, images[BQUEEN], sq);
			}
			else if ((board.blackking & mask[sq]) != 0) {
				drawImage(g2, images[BKING], sq);
			}
		}

		if (illegalFlag) {

			g2.setColor(Color.red);
			g2.drawString("Illegal Move.", startX, startY + height * 9);
		}
	}

	private void drawImage(Graphics g, Image image, int sq) {

		if (dragging && ((direction && sq == fromSq) || (!direction && 63 - sq == fromSq))) {
			g.drawImage(image, px + startX, py + startY, width, height, this);
		} else {
			if (!direction) {
				sq = 63 - sq;
			}
			int x = (sq % 8) * height;
			int y = (sq / 8) * width;
			g.drawImage(image, x + startX, y + startY, width, height, this);
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mousePressed(MouseEvent me) {
		int x = me.getX() - startX;
		int y = me.getY() - startY;
		int topleftx = width * (x / width);
		int toplefty = height * (y / height);

		// SET OFFSET FROM TOP LEFT CORNER OF SQUARE PRESSED ON
		offX = x - topleftx;
		offY = y - toplefty;
		
		fromSq = (y / height) * 8 + (x / width);

		dragging = true;
	}

	public void mouseDragged(MouseEvent me) {
		if(dragging)
		{
			px = me.getX() - startX - offX;
			py = me.getY() - startY - offY;
			repaint();
		}
	}

	public void mouseReleased(MouseEvent me) {
		if(dragging)
		{
			dragging = false;

			int toSq = ((me.getY() - startY) / height) * 8 + ((me.getX() - startX) / width);
			
			if (fromSq < A8 || fromSq > H1 || toSq < A8 || toSq > H1) {
				return;
			}
			if (direction) {
				movePiece(fromSq, toSq);
			} else {
				movePiece(63 - fromSq, 63 - toSq);
			}
			repaint();
		}
	}

	private void movePiece(int from, int to) {

		int p = getPiece(from);
		int c = getPiece(to);

		illegalFlag = false;
		if (p == -1 || from == -1 || to == -1) {
			illegalFlag = true;
			repaint();
			return;
		}

		int mv;
		if (p == KING && from == E1 && to == G1) {
			mv = (KING << 12) | (G1 << 6) | E1;
		} else if (p == KING && from == E1 && to == C1) {
			mv = (KING << 12) | (C1 << 6) | E1;
		} else if (p == KING && from == E8 && to == G8) {
			mv = (KING << 12) | (G8 << 6) | E8;
		} else if (p == KING && from == E8 && to == C8) {
			mv = (KING << 12) | (C8 << 6) | E8;
		} else {
			if ((board.color && p == PAWN && from <= H7) ||
				(!board.color && p == PAWN && from >= A2)) {
				mv = (QUEEN << 18) | (c << 15) | (p << 12) | (to << 6) | from;
			} else {
				mv = (c << 15) | (p << 12) | (to << 6) | from;
			}
		}

		boolean legal = false;
		int []moves = new int[256];
		int noMoves = MoveGenerator.generateMoveList(board, moves);

//		PGNParser.parseMoveList(board, moves, noMoves);

		for (int i = 0; i < noMoves; i++) {
			if (moves[i] == mv) {
				legal = true;
				break;
			}
		}
		
		if (!legal) {
			illegalFlag = true;
			repaint();
			return;
		}
		int castle = board.castle;
		int ep = board.enpassantSquare;
		int score = board.score;
		long zobristKey = board.zobristKey;
		legal = Engine.makeMove(board, mv);

		if (!legal) {
			illegalFlag = true;
			board.castle = castle;
			board.enpassantSquare = ep;
			board.score = score;
			board.zobristKey = zobristKey;
			Engine.undoMove(board, mv);
			repaint();
			return;
		}
		
		int pp = (mv >>> 18) & 7;
		if (pp != EMPTY) {

			board.castle = castle;
			board.enpassantSquare = ep;
			board.score = score;
			board.zobristKey = zobristKey;
			Engine.undoMove(board, mv);
			pp = getPromotionPiece();
			mv = (pp << 18) | (c << 15) | (p << 12) | (to << 6) | from;
			Engine.makeMove(board, mv);
		}

		repaint();
		play();
	}

	private void play() {

		if (board == null) {
			initBoard();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public final void run() {
				long t = System.currentTimeMillis();
				message = s.search(board);
				System.out.println("time taken: " + (System.currentTimeMillis() - t));
				repaint();
				if (!"".equals(message)) {
					JOptionPane.showMessageDialog(null, message, "result",
		                    JOptionPane.INFORMATION_MESSAGE);
				}
		}});
	}

	private int getPiece(int fromSq) {

		if ((board.whitebishops & mask[fromSq]) != 0 || (board.blackbishops & mask[fromSq]) != 0) {
			return BISHOP;
		}
		if ((board.whiteking & mask[fromSq]) != 0 || (board.blackking & mask[fromSq]) != 0) {
			return KING;
		}
		if ((board.whiteknights & mask[fromSq]) != 0 || (board.blackknights & mask[fromSq]) != 0) {
			return KNIGHT;
		}
		if ((board.whitepawns & mask[fromSq]) != 0 || (board.blackpawns & mask[fromSq]) != 0) {
			return PAWN;
		}
		if ((board.whitequeens & mask[fromSq]) != 0 || (board.blackqueens & mask[fromSq]) != 0) {
			return QUEEN;
		}
		if ((board.whiterooks & mask[fromSq]) != 0 || (board.blackrooks & mask[fromSq]) != 0) {
			return ROOK;
		}

		return EMPTY;
	}

	private int getPromotionPiece() {

		int newPiece = EMPTY;
		while (true)
		{
			String piece = JOptionPane.showInputDialog("Choose a new piece Q / R / B / N");
			if (piece.equalsIgnoreCase("Q")) {
				newPiece = QUEEN;
				break;
			}
			if (piece.equalsIgnoreCase("R")) {
				newPiece = ROOK;
				break;
			}
			if (piece.equalsIgnoreCase("B")) {
				newPiece = BISHOP;
				break;
			}
			if (piece.equalsIgnoreCase("N")) {
					newPiece = KNIGHT;
				break;
			}
		}
		return newPiece;
	}

	private void initBoard() {

		illegalFlag = false;
		direction = true;
		
		String fen = fenTextField.getText();

		board = new BitBoard(fen);
	}
}
