
public class Board {
	private Piece[][] chessBoard;
	
	public Board(int xDimension, int yDimension){
		chessBoard = new Piece[xDimension][yDimension];
	}
	
	/**
	 * Checks that a spot on the board is empty and
	 * is a movable spot
	 * @param xPosition The x position of spot
	 * @param yPosition The y position of spot
	 * @return true if empty spot.
	 */
	public boolean isEmptyPosition(int xPosition, int yPosition){
		if (isInBounds(xPosition, yPosition)){
			if (chessBoard[xPosition][yPosition] == null)
				return true;
		}
		return false;
	}
	
	/**
	 * Checks if a location is in bounds of the current chess board
	 * @param xPosition x position of target
	 * @param yPosition y position of target
	 * @return true if location is in bounds
	 */
	public boolean isInBounds(int xPosition, int yPosition){
		if (xPosition < getXDimension() && xPosition >= 0 &&
				yPosition < getYDimension() && yPosition >= 0)
			return true;
		return false;
	}
	
	/**
	 * Returns the chess piece located at the specified location
	 * @param xPosition x position of target
	 * @param yPosition y position of target
	 * @return The chesspiece at given location, null otherwise
	 */
	public Piece pieceAt(int xPosition, int yPosition){
		if (isInBounds(xPosition, yPosition)){
			return chessBoard[xPosition][yPosition];
		}
		return null;
	}
	
	/**
	 * Displays the current board
	 * 
	 * THIS IS NOT COMPLETE. MERELY FOR PLAYING WITH GAME EARLY ON.
	 */
	public void displayBoard(){
		for (int xBoard = 0; xBoard < getXDimension(); xBoard++){
			for (int yBoard = 0; yBoard < getYDimension(); yBoard++){
				if (chessBoard[xBoard][yBoard] == null)
					System.out.print(".");
				else{
					if (chessBoard[xBoard][yBoard] instanceof Pawn)
						System.out.print("p");
					else if (chessBoard[xBoard][yBoard] instanceof Knight)
						System.out.print("k");
					else if (chessBoard[xBoard][yBoard] instanceof Queen)
						System.out.print("q");
					else if (chessBoard[xBoard][yBoard] instanceof King)
						System.out.print("K");
					else if (chessBoard[xBoard][yBoard] instanceof Rook)
						System.out.print("r");
					else if (chessBoard[xBoard][yBoard] instanceof Bishop)
						System.out.print("b");
					else
						System.out.print("x");
				}
			}
			System.out.println();
		}
	}
	
	// Getters/Setters below
	
	public int getXDimension(){
		return chessBoard[0].length;
	}
	
	public int getYDimension(){
		return chessBoard.length;
	}
	
	public Piece[][] getChessBoard(){
		return chessBoard;
	}

	public void removeFromBoard(Piece removePiece){
		int oldXLocation = removePiece.getXLocation();
		int oldYLocation = removePiece.getYLocation();
		
		chessBoard[oldXLocation][oldYLocation] = null;
	}

	public void placePiece(Piece chessPiece, int xPosition, int yPosition){
		if (isInBounds(xPosition, yPosition))
			chessBoard[xPosition][yPosition] = chessPiece;
	}
}
