package com.vikas.engine;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class OpeningPosition {

	long zobristKey;
	LinkedList<MoveGroup> moveGroups = new LinkedList<MoveGroup>();

	OpeningPosition(long zobristKey, int move) {
		this.zobristKey = zobristKey;
		addMove(move);
	}

	void addMove(int newMove) {
		boolean same = false;
		for(MoveGroup moveGroup : moveGroups) {

			if(moveGroup.contains(newMove))
			{
				moveGroup.increaseFrequency();
				same = true;
				break;
			}
		}
		if(!same) {
			moveGroups.add(new MoveGroup(newMove));
		}
	}

	int getTotalNumOfMovesAdded() {

		int total = 0;
		for(MoveGroup moveGroup : moveGroups)
		{
			total += moveGroup.frequency;
		}
		return total;
	}

	void sortMoveGroups() {
		Collections.sort(moveGroups, new Comparator<MoveGroup>(){
			public int compare(MoveGroup mg1, MoveGroup mg2) {
				if(mg1.frequency > mg2.frequency) {
					return -1;
				}
				return 1;
			}
		});
	}
}
