import java.util.Scanner;
import java.util.LinkedList;

public class Game {
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	public final int xBlkKing = 1;
	public final int yBlkKing = 5;
	public final int xWhtKing = 0;
	public final int yWhtKing = 7;
	Scanner userInput = new Scanner(System.in);
	
	private int currentPlayer;
	private Board chessBoard;
	private LinkedList<Piece> blackPieces;
	private LinkedList<Piece> whitePieces;
	private King blackKing;
	private King whiteKing;
	
	public Game(){
		chessBoard = new Board(8,8);
		currentPlayer = WHITE;
		blackPieces = new LinkedList<Piece>();
		whitePieces = new LinkedList<Piece>();
		
		blackKing = new King(chessBoard, BLACK, xBlkKing, yBlkKing);
		whiteKing = new King(chessBoard, WHITE, xWhtKing, yWhtKing);
		blackPieces.add(blackKing);
		whitePieces.add(whiteKing);
	}
	
	
	// This method only used to setup matches for testing obscure situations
	// and the overall game.
	public void testSetup() {
		blackKing.moveTo(5, 0);
		whiteKing.moveTo(7, 1);
		this.addPawn(BLACK, 1, 1);
		this.addPawn(BLACK, 2, 1);
		this.addPawn(BLACK, 3, 1);
		this.addPawn(BLACK, 4, 1);
		this.addPawn(BLACK, 5, 1);
		this.addPawn(BLACK, 6, 1);
		this.addQueen(BLACK, 4, 0);
		this.addRook(BLACK, 3, 0);
		this.addBishop(BLACK, 2, 0);
		currentPlayer = BLACK;
	}
	
	/*
	 * Continues to loop until game is over.
	 * 
	 * IS NOT COMPLETE. only to see if game is working well outside of test cases.
	 */
	public void gameLoop(){
		boolean continueGame = true;
		
		testSetup();
		
		while(continueGame){
			chessBoard.displayBoard();
			
			if (isGameOver()){
				break;
			}
			
			System.out.print("Which piece to move? X-loc: ");
			int nextX = userInput.nextInt();
			System.out.print("Y-loc: ");
			int nextY = userInput.nextInt();
			
			Piece target = chessBoard.pieceAt(nextX, nextY);
			if (target == null){
				System.out.println("That location is invalid");
				continueGame = false;
			}
			else if (target.getColor() != currentPlayer){
				System.out.println("That is not your piece");
				continueGame = false;
			}
			else {
				System.out.print("Where to move this piece? x-loc: ");
				nextX = userInput.nextInt();
				System.out.print("Y-loc: ");
				nextY = userInput.nextInt();
				
				if (target.canMoveTo(nextX, nextY)){
					target.moveTo(nextX, nextY);
				}
				else {
					System.out.println("Cannot move there");
				}
			}
		}
	}
	
	/**
	 * Checks to see if game-ending situation has occurred
	 * 
	 * NOTE: few more game-ending situations should be added,
	 * like 50 move rule, threefold repetition.
	 * 
	 * Added 'no legal move' draw
	 * Added 'checkmate' end
	 * @return - True if game is over
	 */
	public boolean isGameOver(){
		if (isCheckmate(BLACK) || isCheckmate(WHITE)){
			System.out.println("CHECKMATE");
			return true;
		}
		else if (!canMove(currentPlayer)){
			System.out.println("STALEMATE");
			return true;
		}
		return false;
	}
	
	/**
	 * Check to see if the given player
	 * is in a checkmate situation
	 * @param color - color of the player who may be in checkmate
	 * @return - True if player is indeed in checkmate
	 */
	public boolean isCheckmate(int color){	
		if (isKingInCheck(color)){
			if(!canMove(color))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Determines whether the given player has any valid
	 * moves left to play
	 * @param player - Player who's moves are being checked
	 * @return - True if the player still has valid moves
	 */
	public boolean canMove(int player){
		int oldX, oldY;
		Piece target;
		LinkedList<Piece> checkPieces;
		
		if (player == BLACK)
			checkPieces = blackPieces;
		else
			checkPieces = whitePieces;
		
		for (int x = 0; x < chessBoard.getXDimension(); x++){
			for (int y = 0; y < chessBoard.getYDimension(); y++){	
				// If any piece can move to this spot, move here
				// If king is still in check, then go to next location.
				for (Piece currentPiece : checkPieces){
					if (currentPiece.canMoveTo(x, y)){
						//System.out.println(x + ", " + y);
						target = chessBoard.pieceAt(x, y);
						oldX = currentPiece.getXLocation();
						oldY = currentPiece.getYLocation();
						
						currentPiece.moveTo(x, y);
						
						if (!isKingInCheck(player)){
							currentPiece.moveTo(oldX, oldY);
							if (target != null)
								target.moveTo(x, y);
							return true;
						} else {
							currentPiece.moveTo(oldX, oldY);
							if (target != null)
								target.moveTo(x, y);
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if a given player's king is in check
	 * @param color - the color of the player's king being checked
	 * @return - True if the specified king is in check.
	 */
	public boolean isKingInCheck(int color){
		boolean result = false;
		
		LinkedList<Piece> originalList;
		King kingInQuestion;
		
		if (color == BLACK){
			originalList = whitePieces;
			kingInQuestion = blackKing;
		} else {
			originalList = blackPieces;
			kingInQuestion = whiteKing;
		}
		
		int xKingLoc = kingInQuestion.getXLocation();
		int yKingLoc = kingInQuestion.getYLocation();
		
		for (Piece currentPiece : originalList){
			if (currentPiece.canMoveTo(xKingLoc, yKingLoc)){
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * Removes this piece from the game
	 * 
	 * ASSERT that the removed piece is already in game.
	 * @param removeThisPiece the piece to remove.
	 */
	public void removePiece(Piece removeThisPiece){
		removeThisPiece.removePiece();
		int color = removeThisPiece.getColor();
		
		if (color == BLACK)
			blackPieces.remove(removeThisPiece);
		else
			whitePieces.remove(removeThisPiece);
	}
	
	public void switchPlayerTurn(){
		if (currentPlayer == WHITE)
			currentPlayer = BLACK;
		else currentPlayer = WHITE;
	}
	
	public Queen addQueen(int color, int xloc, int yloc){
		Queen queen = new Queen(chessBoard, color, xloc, yloc);
		pieceToColorHelper(queen, color);
		
		return queen;
	}
	
	public Knight addKnight(int color, int xloc, int yloc){
		Knight knight = new Knight(chessBoard, color, xloc, yloc);
		pieceToColorHelper(knight, color);
		
		return knight;
	}
	
	public Rook addRook(int color, int xloc, int yloc){
		Rook rook = new Rook(chessBoard, color, xloc, yloc);
		pieceToColorHelper(rook, color);
		
		return rook;
	}
	
	public Bishop addBishop(int color, int xloc, int yloc){
		Bishop bishop = new Bishop(chessBoard, color, xloc, yloc);
		pieceToColorHelper(bishop, color);
		
		return bishop;
	}
	
	public Pawn addPawn(int color, int xloc, int yloc){
		Pawn pawn = new Pawn(chessBoard, color, xloc, yloc);
		pieceToColorHelper(pawn, color);
		
		return pawn;
	}
	
	private void pieceToColorHelper(Piece piece, int color){
		if (color == BLACK)
			blackPieces.add(piece);
		else
			whitePieces.add(piece);
	}
	
	public int getPlayerTurn(){
		return currentPlayer;
	}
	
	public void setPlayer(int player){
		currentPlayer = player;
	}
	
	public King getBlackKing(){
		return blackKing;
	}
	
	public King getWhiteKing(){
		return whiteKing;
	}
}
