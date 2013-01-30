
public class Knight extends Piece{
	public Knight(Board board, int color, int xLoc, int yLoc){
		super(board, color, xLoc, yLoc);
	}
	
	public boolean canMoveTo(int xPosition, int yPosition){
		if(canMoveGenerics(xPosition,yPosition)){
			return knightMovement(xPosition, yPosition);
		}
		return false;
	}
	
	/**
	 * Specifies the rules for how a knight can move.
	 * Knights should move in a L shaped motion.
	 * Knights can also hop units, so no 'in-between' checks
	 * are required.
	 * 
	 * @param xPosition - The x direction the knight wants to move
	 * @param yPosition - the y direction the knight wants to move
	 * @return - True if the location is a valid spot to move.
	 */
	private boolean knightMovement(int xPosition, int yPosition){
		if (Math.abs(this.getXLocation() - xPosition) == 2 && Math.abs(this.getYLocation() - yPosition) == 1)
			return true;
		if (Math.abs(this.getXLocation() - xPosition) == 1 && Math.abs(this.getYLocation() - yPosition) == 2)
			return true;
		return false;
	}
}
