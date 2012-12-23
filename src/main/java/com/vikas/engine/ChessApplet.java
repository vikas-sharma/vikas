package com.vikas.engine;

import java.awt.Image;

import javax.swing.JApplet;

@SuppressWarnings("serial")
public class ChessApplet extends JApplet implements Constants {

	public void init() {
		super.init();

		Image []images = new Image[13];

		images[BROOK] = getImage(getCodeBase(), "images/BR.png");
		images[BKNIGHT] = getImage(getCodeBase(), "images/BN.png");
		images[BBISHOP] = getImage(getCodeBase(), "images/BB.png");
		images[BQUEEN] = getImage(getCodeBase(), "images/BQ.png");
		images[BKING] = getImage(getCodeBase(), "images/BK.png");
		images[BPAWN] = getImage(getCodeBase(), "images/BP.png");
		images[WROOK] = getImage(getCodeBase(), "images/WR.png");
		images[WKNIGHT] = getImage(getCodeBase(), "images/WN.png");
		images[WBISHOP] = getImage(getCodeBase(), "images/WB.png");
		images[WQUEEN] = getImage(getCodeBase(), "images/WQ.png");
		images[WKING] = getImage(getCodeBase(), "images/WK.png");
		images[WPAWN] = getImage(getCodeBase(), "images/WP.png");

		Drawer drawer = new Drawer(images);
		this.getContentPane().add(drawer);
	}
}