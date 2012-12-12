package org.vkedco.tic_tac_toe_engine;

/*
 **********************************************************
 * bugs to vladimir dot kulyukin at gmail dot com
 **********************************************************
 */

import java.io.Serializable;

class Move implements Serializable {
	
	private static final long serialVersionUID = 3879027946300860680L;
	protected int mPos;
	
	Move(int p) {
		if ( p < 0 || p > 8 )
			throw new IllegalStateException();
		else
			mPos = p;
	}
	
	int getPos() { return mPos; }
	
	void setPos(int p) {
		if ( p < 0 || p > 8 )
			throw new IllegalStateException();
		else
			mPos = p;
	}

}

