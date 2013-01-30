
public class Queen extends Piece{
	public Queen(Board board, int color, int xLoc, int yLoc){
		super(board, color, xLoc, yLoc);
	}
	
	public boolean canMoveTo(int xPosition, int yPosition){
		if(canMoveGenerics(xPosition,yPosition)){
			return queenMovement(xPosition, yPosition);
		}
		return false;
	}
	
	/**
	 * Specifies the rules for how a queen can move.
	 * Queens can move in all 8 directions,
	 * as long as no unit is in the way.
	 * 
	 * @param xPosition - The x direction the queen wants to move
	 * @param yPosition - the y direction the queen wants to move
	 * @return - True if the location is a valid spot to move.
	 */
	private boolean queenMovement(int xPosition, int yPosition){
		if (isMovingStraight(xPosition, yPosition) ||
				isMovingDiagonal(xPosition, yPosition))
				return true;
		return false;
	}
}
