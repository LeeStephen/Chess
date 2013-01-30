
public class King extends Piece{
	public King(Board board, int color, int xLoc, int yLoc){
		super(board, color, xLoc, yLoc);
	}
	
	public boolean canMoveTo(int xPosition, int yPosition){
		if(canMoveGenerics(xPosition,yPosition)){
			return kingMovement(xPosition, yPosition);
		}
		return false;
	}
	
	/**
	 * Specifies the rules for how a king can move.
	 * Rooks can take any one step in any direction,
	 * as long as a ally unit does not occupy the spot
	 * 
	 * @param xPosition - The x direction the king wants to move
	 * @param yPosition - the y direction the king wants to move
	 * @return - True if the location is a valid spot to move.
	 */
	private boolean kingMovement(int xPosition, int yPosition){
		int absoluteX = Math.abs(xPosition - this.getXLocation());
		int absoluteY = Math.abs(yPosition - this.getYLocation());
		
		if (absoluteX <= 1 && absoluteY <= 1){
			if (absoluteX == 0 && absoluteY == 0){
				return false;
			}
			return true;
		}
		return false;
	}
}
