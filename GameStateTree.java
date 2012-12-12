package org.vkedco.tic_tac_toe_engine;

/*
 **********************************************************
 * bugs to vladimir dot kulyukin at gmail dot com
 **********************************************************
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

class GameStateTree implements Serializable, Comparable<GameStateTree> {

	private static final long serialVersionUID = 3930649999919819482L;
	protected char[] mBoardState;
	protected short  mUtility;
	protected Player mPlayer;
	protected ArrayList<MoveGameStatePair> mSuccessors;
		
	GameStateTree(char[] boardState, Player player) {
		mBoardState = boardState;
		mPlayer  = player;
		mUtility = 0;
		mSuccessors = new ArrayList<MoveGameStatePair>();
	}
	
	char[] getBoardState()  { return mBoardState; }
	
	short getUtility() { return mUtility; }
	
	void setUtility(short u) { mUtility = u; }
	
	Player getPlayer() { return mPlayer;  }	
	
	void addSuccessor(Move m, GameStateTree gs) {
		mSuccessors.add(new MoveGameStatePair(m, gs));
	}

	@Override
	public int compareTo(GameStateTree gs) {
		if ( mUtility < gs.getUtility() )
			return -1;
		else if ( mUtility > gs.getUtility() )
			return 1;
		else
			return 0;
	}
	
	void sortSuccessorsByUtility() {
		Collections.sort(mSuccessors, new MoveGameStatePairComparator());
	}
	
	MoveGameStatePair getMaxUtilityMoveGameStatePair() {
		if ( mSuccessors.size() == 0 )
			return null;
		else
			return mSuccessors.get(mSuccessors.size()-1);
	}
	
	MoveGameStatePair getMinUtilityMoveGameStatePair() {
		if ( mSuccessors.size() == 0 )
			return null;
		else
			return mSuccessors.get(0);
	}
	
	ArrayList<MoveGameStatePair> getSuccessors() {
		return mSuccessors;
	}
	
	String boardStateToString() {
		return new String(mBoardState);
	}
	
}

