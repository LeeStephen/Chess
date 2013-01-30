import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;
import org.junit.After;
import org.junit.BeforeClass;

public class ChessTest {
	private static Board standardBoard;
	private static Piece genericPieceOnBoard;
	private static Game gameLogic;
	private static final int standardBoardDimension = 8;
	private static final int xGenericPieceLocation = 4;
	private static final int yGenericPieceLocation = 4;
	private static final int xEmptyPosition = 0;
	private static final int yEmptyPosition = 0;
	private static final int BLACK = 0;
	private static final int WHITE = 1;
	
	/**
	 * Sets up reused objects for all test cases
	 * @throws Exception
	 */
	@BeforeClass
	public static void setupBeforeClass() throws Exception {
		standardBoard = new Board(standardBoardDimension, standardBoardDimension);
		genericPieceOnBoard = new Piece(standardBoard, BLACK);
		gameLogic = new Game();
		genericPieceOnBoard.moveTo(xGenericPieceLocation, yGenericPieceLocation);
	}

	@After
	public void tearDown() throws Exception {
		standardBoard = new Board(standardBoardDimension, standardBoardDimension);
		genericPieceOnBoard = new Piece(standardBoard, BLACK);
		gameLogic = new Game();
		genericPieceOnBoard.moveTo(xGenericPieceLocation, yGenericPieceLocation);
	}
	
	/**
	 * This test checks that the Chess Board is properly initialized.
	 * Assuming rectangular chess board
	 */
	@Test
	public void instantiateBoard() {
		assertEquals(standardBoard.getXDimension(), standardBoardDimension);
		assertEquals(standardBoard.getYDimension(), standardBoardDimension);
	}
	
	/**
	 * Checks whether chess pieces are correctly being marked as
	 * occupying a position on the chess board.
	 */
	@Test
	public void checkPositionOccupancy() {
		// Check placed position
		assertFalse(standardBoard.isEmptyPosition(xGenericPieceLocation, yGenericPieceLocation));
		
		// In bounds
		assertTrue(standardBoard.isEmptyPosition(xEmptyPosition, yEmptyPosition));
		
		// Out of bounds
		assertFalse(standardBoard.isEmptyPosition(standardBoardDimension, standardBoardDimension));
		assertFalse(standardBoard.isEmptyPosition(standardBoardDimension - 1, standardBoardDimension));
		assertFalse(standardBoard.isEmptyPosition(standardBoardDimension, standardBoardDimension - 1));
	}
	
	/**
	 * Makes sure to check that any out-of-bounds locations are rejected.
	 */
	@Test
	public void testBoundsOfBoard() {
		assertFalse(standardBoard.isInBounds(9, 9));
		assertFalse(standardBoard.isInBounds(-1, -1));
		assertTrue(standardBoard.isInBounds(4, 7));
	}
	
	/**
	 * Checks that the board can return pieces on the board.
	 * If there is no chess piece at a given location, the return should be null.
	 */
	@Test
	public void checkForPieceOnBoard() {
		Piece tempPiece = standardBoard.pieceAt(xGenericPieceLocation, yGenericPieceLocation);
		Piece nullPiece1 = standardBoard.pieceAt(xEmptyPosition, yEmptyPosition);
		Piece nullPiece2 = standardBoard.pieceAt(standardBoardDimension, standardBoardDimension);
		
		assertEquals(tempPiece, genericPieceOnBoard);
		assertNull(nullPiece1);
		assertNull(nullPiece2);
	}
	
	@Test
	public void instantiatePiece() {
		assertEquals(BLACK, genericPieceOnBoard.getColor());
		assertEquals(4, genericPieceOnBoard.getXLocation());
		assertEquals(4, genericPieceOnBoard.getYLocation());
		
		Board currChessBoard = genericPieceOnBoard.getBoard();
		assertEquals(currChessBoard, standardBoard);
	}
	
	/**
	 * Test whether a generic chess piece can move to certain spots.
	 */
	@Test
	public void canMoveToTest() {
		Piece genericBlackPiece = new Piece(standardBoard, BLACK);
		Piece genericWhitePiece = new Piece(standardBoard, WHITE);
		genericBlackPiece.moveTo(0,0);
		genericWhitePiece.moveTo(2,2);
		
		// A generic piece should be able to move anywhere, except ally spots
		assertFalse(genericBlackPiece.canMoveTo(xGenericPieceLocation, yGenericPieceLocation)); // friendly
		assertTrue(genericBlackPiece.canMoveTo(1, 1)); // empty
		assertFalse(genericBlackPiece.canMoveTo(0, 0)); // same spot
		assertTrue(genericBlackPiece.canMoveTo(2,2)); // enemy spot
	}
	
	/**
	 * Test to make sure pawns move properly.
	 * 
	 * Note: White pawns move up, Black pawns move down.
	 */
	@Test
	public void testPawnMovements(){
		Pawn testPawn = new Pawn(standardBoard, WHITE, 3, 3);
		
		//One step
		assertTrue(testPawn.canMoveTo(2, 3));
		//Two step first move
		assertTrue(testPawn.canMoveTo(1, 3));
		//Three step
		assertFalse(testPawn.canMoveTo(0, 3));
		//Back step
		assertFalse(testPawn.canMoveTo(4, 3));
		
		// Diagonal without enemies
		assertFalse(testPawn.canMoveTo(2, 2));
		
		// Diagonal with enemies
		Pawn testPawn2 = new Pawn(standardBoard, BLACK, 2, 2);
		assertTrue(testPawn.canMoveTo(2, 2));
		
		// One step, black
		assertTrue(testPawn2.canMoveTo(3, 2));
		// back step, black
		assertFalse(testPawn2.canMoveTo(1, 2));
		
		//Invalid move, out-of-bounds
		testPawn.moveTo(0, 0);
		assertFalse(testPawn.canMoveTo(-1, 0));
		
		// Invalid move, partner in front
		testPawn.moveTo(3, 3);
		Pawn testPawn3 = new Pawn(standardBoard, WHITE, 2, 3);
		assertFalse(testPawn.canMoveTo(2, 3));
		
		// Two step, already moved
		testPawn3.moveTo(5, 5);
		assertFalse(testPawn3.canMoveTo(3, 5));
	}
	
	/**
	 * Test to make sure knight moves properly.
	 */
	@Test
	public void testKnightMovements(){
		Knight testKnight = new Knight(standardBoard, WHITE, 3, 3);
		
		// All 8 valid movements
		assertTrue(testKnight.canMoveTo(1,2));
		assertTrue(testKnight.canMoveTo(1,4));
		assertTrue(testKnight.canMoveTo(5,2));
		assertTrue(testKnight.canMoveTo(5,4));
		assertTrue(testKnight.canMoveTo(2,1));
		assertTrue(testKnight.canMoveTo(4,1));
		assertTrue(testKnight.canMoveTo(2,5));
		assertTrue(testKnight.canMoveTo(4,5));
		
		// same spot
		assertFalse(testKnight.canMoveTo(3, 3));
		
		// empty spot, but invalid movement
		assertFalse(testKnight.canMoveTo(4, 4));
		
		// out of bounds
		testKnight.moveTo(1, 1);
		assertFalse(testKnight.canMoveTo(-1, 0));
		
		// ally spot
		assertTrue(testKnight.canMoveTo(3, 2));
		Pawn testPawn = new Pawn(standardBoard, WHITE, 3, 2);
		assertFalse(testKnight.canMoveTo(3, 2));
	}
	
	/**
	 * Test to make sure rook moves properly.
	 */
	@Test
	public void testRookMovements(){
		Rook testRook = new Rook(standardBoard, WHITE, 1, 1);
		
		straightMovementCheck(testRook);
	}
	
	/**
	 * Test to make sure bishop moves properly.
	 */
	@Test
	public void testBishopMovements(){
		Bishop testBishop = new Bishop(standardBoard, WHITE, 1, 1);
		
		diagonalMovementCheck(testBishop);
	}
	
	/**
	 * Test to verify the Queen moves properly
	 */
	@Test
	public void testQueenMovements(){
		Queen testQueen = new Queen(standardBoard, WHITE, 1, 1);
		
		// Test straight
		straightMovementCheck(testQueen);
		
		// Test diagonal
		diagonalMovementCheck(testQueen);
	}
	
	/**
	 * Helper function for testing if a piece that moves
	 * diagonally works properly (bishops, queen)
	 * @param testPiece - the test piece being tested
	 */
	private void diagonalMovementCheck(Piece testPiece){
		testPiece.moveTo(1, 1);
		assertTrue(testPiece.canMoveTo(3, 3));
		assertTrue(testPiece.canMoveTo(0, 2));
		
		// Units in the way
		Pawn testEnemyPawn = new Pawn(standardBoard, BLACK, 2, 2);
		Pawn testAllyPawn = new Pawn(standardBoard, WHITE, 2, 0);
		
		assertTrue(testPiece.canMoveTo(2, 2));
		assertFalse(testPiece.canMoveTo(3, 3));
		assertFalse(testPiece.canMoveTo(2, 0));
		
		// Out of Bounds
		assertFalse(testPiece.canMoveTo(-1, -1));
	}
	
	/**
	 * Helper function for testing if a piece that moves
	 * straight works properly (rook, queen)
	 * @param testPiece - the test piece being tested
	 */
	private void straightMovementCheck(Piece testPiece){
		testPiece.moveTo(1, 1);
		assertTrue(testPiece.canMoveTo(5, 1));
		assertTrue(testPiece.canMoveTo(1, 4));
		
		// Units in the way
		Pawn testEnemyPawn = new Pawn(standardBoard, BLACK, 2, 1);
		Pawn testAllyPawn = new Pawn(standardBoard, WHITE, 1, 1);
		
		assertTrue(testPiece.canMoveTo(2, 1));
		assertFalse(testPiece.canMoveTo(3, 1));
		assertFalse(testPiece.canMoveTo(1, 1));
		
		// Out of Bounds
		assertFalse(testPiece.canMoveTo(1, -1));
	}
	
	/**
	 * Test to verify the King moves properly
	 */
	@Test
	public void testKingMovements(){
		King testKing = new King(standardBoard, WHITE, 1, 1);
		
		// Test all 8 valid spots
		assertTrue(testKing.canMoveTo(0, 0));
		assertTrue(testKing.canMoveTo(0, 1));
		assertTrue(testKing.canMoveTo(0, 2));
		assertTrue(testKing.canMoveTo(1, 0));
		assertTrue(testKing.canMoveTo(1, 2));
		assertTrue(testKing.canMoveTo(2, 0));
		assertTrue(testKing.canMoveTo(2, 1));
		assertTrue(testKing.canMoveTo(2, 2));
		
		// Test same spot
		assertFalse(testKing.canMoveTo(1,1));
		
		// Test too many steps
		assertFalse(testKing.canMoveTo(3, 1));
		
		// Test out of bounds
		testKing.moveTo(0, 0);
		assertFalse(testKing.canMoveTo(-1, 0));
		
		// Ally unit in the way
		assertTrue(testKing.canMoveTo(1, 1));
		Pawn testPawn = new Pawn(standardBoard, WHITE, 1, 1);
		assertFalse(testKing.canMoveTo(1, 1));
	}
	
	/**
	 * Moving pieces on board should be handled appropriately, as well
	 * as placement adjustments. The pieces handle movement, while
	 * the board is just the data structure.
	 */
	@Test
	public void placePiecesOnBoard() {
		//Placing a piece on the board from one location to another should change locations
		genericPieceOnBoard.moveTo(1, 1);
		
		Piece tempPiece1 = standardBoard.pieceAt(xGenericPieceLocation, yGenericPieceLocation);
		Piece tempPiece2 = standardBoard.pieceAt(1, 1);
		
		assertEquals(tempPiece2, genericPieceOnBoard);
		assertNull(tempPiece1);
		
		// Placing piece out of bounds
		genericPieceOnBoard.moveTo(-1, -1);
		
		tempPiece1 = standardBoard.pieceAt(1, 1);
		
		assertNull(tempPiece1);
	}
	
	/**
	 * Test whether pieces "know" they have moved.
	 */
	@Test
	public void movePieces() {
		// Test current position
		didPiecesMoveTo(genericPieceOnBoard, xGenericPieceLocation, yGenericPieceLocation);
		
		// Test moving piece
		genericPieceOnBoard.moveTo(0, 0);
		didPiecesMoveTo(genericPieceOnBoard, 0, 0);
		
		Piece samePiece = standardBoard.pieceAt(0, 0);
		assertEquals(samePiece, genericPieceOnBoard);
		
		// Test moving out-of-bounds
		genericPieceOnBoard.moveTo(-1, -1);
		didPiecesMoveTo(genericPieceOnBoard, -1, -1);
	}
	
	/**
	 * Helper function for guaranteeing that a piece is 
	 * located at the specified location
	 * 
	 * @param piece The piece being checked.
	 * @param xLocation The x location the piece should be at
	 * @param yLocation The y location the piece should be at
	 */
	private void didPiecesMoveTo(Piece piece, int xLocation, int yLocation){
		int xLoc = piece.getXLocation();
		int yLoc = piece.getYLocation();
		
		assertEquals(xLoc, xLocation);
		assertEquals(yLoc, yLocation);
	}
	
	/**
	 * Test the pieces can be removed.
	 */
	@Test
	public void removePieceTest(){
		genericPieceOnBoard.removePiece();
		assertFalse(genericPieceOnBoard.onBoard());
		assertTrue(standardBoard.isEmptyPosition(xGenericPieceLocation, yGenericPieceLocation));
	}
	
	/**
	 * Checks that pieces can capture other pieces.
	 * Capturing a piece also entails that the captured is removed from
	 * the board.
	 */
	@Test
	public void capturePieceTest() {
		Piece genericWhitePiece = new Piece(standardBoard, WHITE, 0, 0);
		genericPieceOnBoard.capturePiece(genericWhitePiece);
		
		assertFalse(genericWhitePiece.onBoard());
	}
	
	/**
	 * Tests that pieces that are on the chess board
	 * correctly replies that they are on board.
	 */
	@Test
	public void onBoardTest() {
		Piece genericWhitePiece = new Piece(standardBoard, WHITE);
		
		assertTrue(genericPieceOnBoard.onBoard());
		assertFalse(genericWhitePiece.onBoard());
		
		genericPieceOnBoard.removePiece();
		assertFalse(genericPieceOnBoard.onBoard());
		
		genericWhitePiece.moveTo(0, 0);
		assertTrue(genericWhitePiece.onBoard());
	}
	
	/**
	 * Test to find checkmates
	 */
	@Test
	public void checkmateFound() {
		King blackKing = gameLogic.getBlackKing();
		
		blackKing.moveTo(4, 4);
		Queen queen1 = gameLogic.addQueen(WHITE,3,3);
		Queen queen2 = gameLogic.addQueen(WHITE,5,5);
		
		assertTrue(gameLogic.isCheckmate(BLACK));
		
		gameLogic.removePiece(queen1);
		assertFalse(gameLogic.isCheckmate(BLACK));
	}
	
	/**
	 * Test that a player has a legal move available.
	 */
	@Test
	public void legalMoves() {
		King blackKing = gameLogic.getBlackKing();
		King whiteKing = gameLogic.getWhiteKing();
		Queen queen = gameLogic.addQueen(BLACK, 2, 6);
		
		blackKing.moveTo(1, 5);
		whiteKing.moveTo(0, 7);
		
		// Stalemate if white moves
		assertFalse(gameLogic.canMove(WHITE));
		
		// Still valid moves if black moves
		assertTrue(gameLogic.canMove(BLACK));
	}
	
	/**
	 * Test whether a king has been checked.
	 */
	@Test
	public void kingChecked() {
		King blackKing = gameLogic.getBlackKing();
		blackKing.moveTo(0, 0);
		
		// Test checkmate
		Queen queen = gameLogic.addQueen(WHITE, 3, 0);
		assertTrue(gameLogic.isKingInCheck(BLACK));
		
		// Test non-checkmate
		gameLogic.removePiece(queen);
		assertFalse(gameLogic.isKingInCheck(BLACK));
	}
	
	/**
	 * Verify that turns can be switched properly.
	 */
	@Test
	public void switchingTurns() {
		gameLogic.setPlayer(BLACK);
		
		assertEquals(gameLogic.getPlayerTurn(), BLACK);
		gameLogic.switchPlayerTurn();
		assertEquals(gameLogic.getPlayerTurn(), WHITE);
	}
}
