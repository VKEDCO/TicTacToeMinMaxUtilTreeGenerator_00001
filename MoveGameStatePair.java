package org.vkedco.tic_tac_toe_engine;

/*
 **********************************************************
 * bugs to vladimir dot kulyukin at gmail dot com
 **********************************************************
 */

import java.io.Serializable;
import java.util.Comparator;

class MoveGameStatePairComparator 
	implements Comparator<MoveGameStatePair>
{
	@Override
	public int compare(MoveGameStatePair mgsp0, MoveGameStatePair mgsp1) {
		return mgsp0.getGameState().compareTo(mgsp1.getGameState());
	}
}

class MoveGameStatePair implements Serializable {
	
	private static final long serialVersionUID = 3143536200483181033L;
	protected Move mMove;
	protected GameStateTree mGameState;
	
	MoveGameStatePair(Move move, GameStateTree gs) {
		mMove = move;
		mGameState = gs;
	}
	
	Move getMove() { return mMove; }
	
	GameStateTree getGameState() { return mGameState; }
	
}

