package org.vkedco.tic_tac_toe_engine;

/*
 **********************************************************
 * bugs to vladimir dot kulyukin at gmail dot com
 **********************************************************
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class TicTacToeMinMaxMoveUtilTreeGenerator {
	
	static final char X = 'x';
	static final char O = 'o';
	static final char Q = '?';
	static final Move[] MOVES = {
		new Move(0), new Move(1), new Move(2),
		new Move(3), new Move(4), new Move(5),
		new Move(6), new Move(7), new Move(8)
	};
	
	static final Player MAX = Player.X;
	static final Player MIN = Player.O;

	static final GameStateTree applyMove(GameStateTree gs, Move m) {
		Player player = gs.getPlayer();
		char[] new_board = gs.getBoardState().clone();
		switch ( player ) {
		case X: new_board[m.getPos()] = X; break;
		case O: new_board[m.getPos()] = O; break;
		default: return null;
		}
		
		return new GameStateTree(new_board, switchPlayer(player));
	}
	
	static final Player switchPlayer(Player p) {
		switch ( p ) {
		case X: return Player.O;
		case O: return Player.X;
		default: return null;
		}
	}
	
	static final boolean isDraw(GameStateTree gs) {
		for(char c: gs.getBoardState()) {
			if ( c == TicTacToeMinMaxMoveUtilTreeGenerator.Q )
				return false;
		}
		return true;
	}
	
	static final Player getWinner(GameStateTree gs) {
		char[] board = gs.getBoardState();
		// 1st row
		if ( board[0] == TicTacToeMinMaxMoveUtilTreeGenerator.X && 
			 board[1] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
			 board[2] == TicTacToeMinMaxMoveUtilTreeGenerator.X )
			return Player.X;
		else if ( board[0] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[1] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[2] == TicTacToeMinMaxMoveUtilTreeGenerator.O )
			return Player.O;
		// 2nd row
		else if ( board[3] == TicTacToeMinMaxMoveUtilTreeGenerator.X && 
				  board[4] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[5] == TicTacToeMinMaxMoveUtilTreeGenerator.X )
			return Player.X;
		else if ( board[3] == TicTacToeMinMaxMoveUtilTreeGenerator.O && 
				  board[4] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[5] == TicTacToeMinMaxMoveUtilTreeGenerator.O )
			return Player.O;
		// 3rd row
		else if ( board[6] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[7] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[8] == TicTacToeMinMaxMoveUtilTreeGenerator.X )
			return Player.X;
		else if ( board[6] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[7] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[8] == TicTacToeMinMaxMoveUtilTreeGenerator.O )
			return Player.O;
		// 1st column
		else if ( board[0] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[3] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[6] == TicTacToeMinMaxMoveUtilTreeGenerator.X ) 
			return Player.X;
		else if ( board[0] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[3] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[6] == TicTacToeMinMaxMoveUtilTreeGenerator.O ) 
			return Player.O;
		// 2nd column
		else if ( board[1] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[4] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[7] == TicTacToeMinMaxMoveUtilTreeGenerator.X ) 
			return Player.X;
		else if ( board[1] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[4] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[7] == TicTacToeMinMaxMoveUtilTreeGenerator.O ) 
			return Player.O;
		// 3rd column
		else if ( board[2] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[5] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[8] == TicTacToeMinMaxMoveUtilTreeGenerator.X ) 
			return Player.X;
		else if ( board[2] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[5] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[8] == TicTacToeMinMaxMoveUtilTreeGenerator.O ) 
			return Player.O;
		// from bot left to top right
		else if ( board[2] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[4] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[6] == TicTacToeMinMaxMoveUtilTreeGenerator.X ) 
			return Player.X;
		else if ( board[2] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[4] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[6] == TicTacToeMinMaxMoveUtilTreeGenerator.O ) 
			return Player.O;
		// from bot right to top left
		else if ( board[0] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[4] == TicTacToeMinMaxMoveUtilTreeGenerator.X &&
				  board[8] == TicTacToeMinMaxMoveUtilTreeGenerator.X ) 
			return Player.X;
		else if ( board[0] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[4] == TicTacToeMinMaxMoveUtilTreeGenerator.O &&
				  board[8] == TicTacToeMinMaxMoveUtilTreeGenerator.O ) 
			return Player.O;
		else
			return null;
	}
	
	static final boolean isTerminal(GameStateTree gs) {
		return isDraw(gs) || ( getWinner(gs) != null );
	}
	
	static final short getUtility(GameStateTree gs) throws Exception
	{
		Player player = getWinner(gs);
		if ( player != null ) {
			if ( player == Player.X )
				return 1;
			else if ( player == Player.O )
				return -1;
			else
				throw new Exception("Unknown player");
		}
		if ( isDraw(gs) ) {
			return 0;
		}
		else
			throw new Exception("Utility computation failure");
	}
	
	static final ArrayList<Move> getApplicableMoves(GameStateTree gs) {
		if ( isTerminal(gs) ) return null;
		ArrayList<Move> moves = new ArrayList<Move>();
		char[] board = gs.getBoardState();
		for(int i = 0; i < 9; i++) {
			if ( board[i] == Q )
				moves.add(MOVES[i]);
		}
		return moves;
	}
	
	// generates 549,946 Tic-Tac-Toe board configurations, of which
	// - 131,184 wins for X
	// - 77,904  wins for O
	// - 46,080  draws
	static final void generateGameStateTree(GameStateTree gs) {
		ArrayList<Move> moves = getApplicableMoves(gs);
		if ( moves == null ) return;
		for(Move m: moves) {
			gs.addSuccessor(m, applyMove(gs, m));
		}
		for(MoveGameStatePair mgsp: gs.getSuccessors()) {
			generateGameStateTree(mgsp.getGameState());
		}
	}
	
	static final long countInGameStateTreeWinsFor(GameStateTree gs, Player p) {
		ArrayList<Move> moves = getApplicableMoves(gs);
		if ( moves == null ) {
			if ( getWinner(gs) == p ) 
				return 1L;
			else
				return 0L;
		}
		for(Move m: moves) {
			gs.addSuccessor(m, applyMove(gs, m));
		}
		long rslt = 0L;
		for(MoveGameStatePair mgsp: gs.getSuccessors()) {
			rslt += countInGameStateTreeWinsFor(mgsp.getGameState(), p);
		}
		return rslt;
	}
	
	static final long countInComputedGameStateTreeWinsFor(GameStateTree gs, Player p) {
		if ( gs == null ) return 0L;
	
		if ( getWinner(gs) == p ) { 
			return 1L;
		}
		else {
			long rslt = 0L;
			for(MoveGameStatePair mgsp: gs.getSuccessors()) {
				rslt += countInComputedGameStateTreeWinsFor(mgsp.getGameState(), p);
			}
			return rslt;
		}
	}
	
	static final long countInGameStateMapWinsFor(TreeMap<String, String> map, Player p) {
		GameStateTree gst = null;
		long rslt = 0;
		for(Map.Entry<String, String> e: map.entrySet()) {
			gst = stringToGameStateTree(e.getKey(), p);
			//System.out.println(new String(gst.getBoardState()) + " " + e.getKey());
			if ( getWinner(gst) == p ) {
				rslt += 1;
			}
		}
		
		return rslt;
	}
	
	static final void computeGameStateUtility(GameStateTree gs) {
		try {
			if ( isTerminal(gs) ) {
				gs.setUtility(getUtility(gs));
			}
			else {
				for(MoveGameStatePair mgsp: gs.getSuccessors()) {
					computeGameStateUtility(mgsp.getGameState());
				}
				gs.sortSuccessorsByUtility();
				if ( gs.getPlayer() == MAX ) {
					gs.setUtility(gs
									.getMaxUtilityMoveGameStatePair()
									.getGameState()
									.getUtility());
				}
				else if ( gs.getPlayer() == MIN ) {
					gs.setUtility(gs
							.getMinUtilityMoveGameStatePair()
							.getGameState()
							.getUtility());
				}
				else
					throw new Exception("Uknown Player");
			}
		}
		catch ( Exception ex ) {
			ex.printStackTrace();
		}
	}
	
	static final void saveGameStateTreeAux(GameStateTree gst, TreeMap<String, String> boardStateLookup, PrintWriter pw) {
		if ( gst == null || boardStateLookup.containsKey(new String(gst.getBoardState())) ) 
			return;
		
		String boardStateKey = new String(gst.getBoardState());
		StringBuilder boardStateValue = new StringBuilder("");
		boardStateValue.append(gst.getPlayer() + ";");
		StringBuilder fileLine = new StringBuilder("");
		fileLine.append(boardStateKey + ";");
		fileLine.append(gst.getPlayer() + ";");
		if ( gst.getSuccessors().size() == 0 ) {
			fileLine.append(gst.getUtility());
			pw.println(fileLine.toString());
			boardStateValue.append(gst.getUtility());
			boardStateLookup.put(boardStateKey, boardStateValue.toString());
			return;
		}
		else {
			for(MoveGameStatePair mgsp: gst.getSuccessors()) {
				fileLine.append(mgsp.getMove().getPos() + ":" + mgsp.getGameState().getUtility() + " ");
				boardStateValue.append(mgsp.getMove().getPos() + ":" + mgsp.getGameState().getUtility() + ";");
			}
			pw.println(fileLine.toString());
			boardStateLookup.put(boardStateKey, boardStateValue.toString());
			for(MoveGameStatePair mgsp: gst.getSuccessors()) {
				saveGameStateTreeAux(mgsp.getGameState(), boardStateLookup, pw);
			}
		}
	}
	
	
	static final void saveGameStateTree(GameStateTree root, TreeMap<String, String> boardStateLookup, String fp) {
		try {
			PrintWriter pw = new PrintWriter(new File(fp));
			saveGameStateTreeAux(root, boardStateLookup, pw);
			pw.flush();
			pw.close();
		}
		catch ( IOException ex ) {
			ex.printStackTrace();
		}
	}
	
	static final void serializeGameStateTree(GameStateTree root, String fp) {
		try {
			FileOutputStream fos = new FileOutputStream(fp);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(root);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static final GameStateTree deserializeGameStateTree(String fp) {
		try {
			FileInputStream fis = new FileInputStream(fp);
			ObjectInputStream ois = new ObjectInputStream(fis);
			GameStateTree gt = (GameStateTree) ois.readObject();
			return gt;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static TreeMap<String, String> convertGameStateTreeToTreeMap(GameStateTree gst) {
		TreeMap<String, String> tm = new TreeMap<String, String>();
		convertGameStateTreeToTreeMapAux(gst, tm);
		return tm;
	}
	
	static final void serializeGameStateTreeMap(TreeMap<String, String> tm, String fp) {
		try {
			FileOutputStream   fos = new FileOutputStream(fp);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tm);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static final TreeMap<String, String> deserializeGameStateTreeMap(String fp) {
		try {
			FileInputStream fos = new FileInputStream(fp);
			ObjectInputStream ois = new ObjectInputStream(fos);
			TreeMap<String, String> tm = (TreeMap<String, String>) ois.readObject();
			return tm;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static final void convertGameStateTreeToTreeMapAux(GameStateTree gst, TreeMap<String, String> tm) {
		
		if ( gst == null ) return;

		final String boardStateKey = gst.boardStateToString();
		if ( !tm.containsKey(boardStateKey) ) {
			StringBuilder boardStateValue = new StringBuilder("");
			boardStateValue.append(gst.getPlayer() + ";");
			if ( gst.getSuccessors().size() == 0 ) {
				boardStateValue.append(gst.getUtility());
				tm.put(boardStateKey, boardStateValue.toString());
				return;
			}
			else {
				for(MoveGameStatePair mgsp: gst.getSuccessors()) {
					boardStateValue.append(mgsp.getMove().getPos() + ":" + mgsp.getGameState().getUtility() + ";");
				}
				tm.put(boardStateKey, boardStateValue.toString());
				for(MoveGameStatePair mgsp: gst.getSuccessors()) {
					convertGameStateTreeToTreeMapAux(mgsp.getGameState(), tm);
				}
			}
		}
		else {
			for(MoveGameStatePair mgsp: gst.getSuccessors()) {
				convertGameStateTreeToTreeMapAux(mgsp.getGameState(), tm);
			}
		}
	}
	
	static GameStateTree stringToGameStateTree(String boardstate, Player p) {
		char[] board = new char[] {
				Q, Q, Q,
				Q, Q, Q,
				Q, Q, Q	
		};
		
		for(int i = 0; i < boardstate.length(); i++) {
			if ( boardstate.charAt(i) == X ) {
				board[i] = X;
			}
			else if ( boardstate.charAt(i) == O ) {
				board[i] = O;
			}
		}
		
		return new GameStateTree(board, p);
	}
	
	public static void main(String... args) {
		char[] board = new char[] {
			Q, Q, Q,
			Q, Q, Q,
			Q, Q, Q
		};
		
		GameStateTree gst01 = new GameStateTree(board, Player.X);
		generateGameStateTree(gst01);
		System.out.println("game tree generated");
		
		System.out.println("Number of wins for X in tree is " + countInComputedGameStateTreeWinsFor(gst01, Player.X));

		TreeMap<String, String> game_state_tree_map = convertGameStateTreeToTreeMap(gst01);
		System.out.println("game state tree map's size is " + game_state_tree_map.size());
		System.out.println("Number of wins for X in map is " + countInGameStateMapWinsFor(game_state_tree_map, Player.X));
				
		computeGameStateUtility(gst01);
		System.out.println("game state utilities computed");
		
		TreeMap<String, String> boardStateLookup = new TreeMap<String, String>();
		saveGameStateTree(gst01, boardStateLookup, "game_state_tree_01.txt");
		System.out.println("game state tree 01 saved in a txt file");
		System.out.println("board state lookup tree map has " + boardStateLookup.size() + " states");
		
		serializeGameStateTreeMap(boardStateLookup, "game_state_tree_map_01.ser");
		System.out.println("game state tree map 01 serialized");
		
		serializeGameStateTree(gst01, "game_state_tree_01.ser");
		System.out.println("game state tree 01 serialized");
		
		GameStateTree gst02 = deserializeGameStateTree("game_state_tree_01.ser");
		System.out.println("game state tree deserialized");
		saveGameStateTree(gst02, boardStateLookup, "game_state_tree_02.txt");
		System.out.println("deserialized game state tree 02 saved in a txt file");
		
		TreeMap<String, String> boardStateLookup_02 = convertGameStateTreeToTreeMap(gst01);
		serializeGameStateTreeMap(boardStateLookup_02, "game_state_tree_map_02.ser");
		System.out.println("serialized game state tree map 02");
		
		TreeMap<String, String> tm_01 = deserializeGameStateTreeMap("game_state_tree_map_01.ser");
		System.out.println("game state tree map 01 deserialized");
		TreeMap<String, String> tm_02 = deserializeGameStateTreeMap("game_state_tree_map_01.ser");
		System.out.println("game state tree map 02 deserialized");
		
		System.out.println("deserialized game tree map 01's number of states is " + tm_01.size());
		System.out.println("deserialized game tree map 02's number of states is " + tm_02.size());
	}
}
 
