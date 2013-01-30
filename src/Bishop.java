
public class Bishop extends Piece{
	public Bishop(Board board, int color, int xLoc, int yLoc){
		super(board, color, xLoc, yLoc);
	}
	
	public boolean canMoveTo(int xPosition, int yPosition){
		if(canMoveGenerics(xPosition,yPosition)){
			return bishopMovement(xPosition, yPosition);
		}
		return false;
	}
	
	/**
	 * Specifies the rules for how a bishop can move.
	 * Bishops can move in diagonal lines,
	 * as long as no unit is in the way.
	 * 
	 * @param xPosition - The x direction the bishop wants to move
	 * @param yPosition - the y direction the bishop wants to move
	 * @return - True if the location is a valid spot to move.
	 */
	private boolean bishopMovement(int xPosition, int yPosition){
		return isMovingDiagonal(xPosition, yPosition);
	}
}