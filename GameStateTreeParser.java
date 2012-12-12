package org.vkedco.tic_tac_toe_engine;

/*
 **********************************************************
 * bugs to vladimir dot kulyukin at gmail dot com
 **********************************************************
 */

import java.util.ArrayList;

public class GameStateTreeParser {
	
	class MoveUtil {
		short mPos;
		short mUtil;
	}
	
	class ParsedGameTreeState {
		String mBoard;
		Player mPlayer;
		ParsedGameTreeState(String b, Player p) {
			mBoard = b;
			mPlayer = p;
		}
	}
	
	class FinalParsedGameTreeState extends ParsedGameTreeState {
		short mUtil;
		FinalParsedGameTreeState(String board, Player p, short util) {
			super(board, p);
			mUtil = util;
		}
	}
	
	class NonFinalParsedGameTreeState extends ParsedGameTreeState {
		ArrayList<MoveUtil> mMoveUtils;
		NonFinalParsedGameTreeState(String board, Player p, ArrayList<MoveUtil> mutils) {
			super(board, p);
			mMoveUtils = mutils;
		}
	}
	
	final static ParsedGameTreeState inflateParsedGameState(String line) {
		if ( line == null ) return null;
		if ( line.length() == 0 ) return null;
		if ( line == "\n" ) return null;
		String[] segments = line.trim().split(";");
		for (String seg: segments) {
			System.out.println(seg);
		}
		String b = segments[0];
		Player p;
		if ( segments[1].equals("O") )
			p = Player.O;
		else if ( segments[1].equals("X") )
			p = Player.X;
		
		if ( segments[2].indexOf(":") != -1) {
			// parse segments[2] into ArrayList<MoveUtil>
			// construct a NonFinalParsedGameState
		}
		else {
			// parse segments[2] into a short
			// return a short
			// construct a FinalParsedGameState
		}
		return null;
	}
	
	public static void main(String... args) {
		inflateParsedGameState("?????????;X;-1");
	}
	
	

}
