
public class Rook extends Piece{
	public Rook(Board board, int color, int xLoc, int yLoc){
		super(board, color, xLoc, yLoc);
	}
	
	public boolean canMoveTo(int xPosition, int yPosition){
		if(canMoveGenerics(xPosition,yPosition)){
			return rookMovement(xPosition, yPosition);
		}
		return false;
	}
	
	/**
	 * Specifies the rules for how a rook can move.
	 * Rooks can move in straight lines,
	 * as long as no unit is in the way.
	 * 
	 * @param xPosition - The x direction the rook wants to move
	 * @param yPosition - the y direction the rook wants to move
	 * @return - True if the location is a valid spot to move.
	 */
	private boolean rookMovement(int xPosition, int yPosition){
		return isMovingStraight(xPosition, yPosition);
	}
}