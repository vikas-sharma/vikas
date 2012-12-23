package com.vikas.engine;

public class MoveGroup {

	int move;
	int frequency;
	
	MoveGroup(int move) {

		this.move = move;
		this.frequency = 1;
	}

	void increaseFrequency() {
		frequency++;
	}

	boolean contains(int newMove) {
		return (move == newMove);
	}
}
