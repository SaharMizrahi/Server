package Data;

import java.util.ArrayList;
import java.util.List;

import Model.Data.Position;

public class SokobanState
{
	private Position playerPosition;
	private ArrayList<Position> boxPosition;

	public Position getPlayerPosition()
	{
		return playerPosition;
	}

	public void setPlayerPosition(Position playerPosition)
	{
		this.playerPosition = playerPosition;
	}

	public List<Position> getBoxPosition()
	{
		return boxPosition;
	}

	public void setBoxPosition(ArrayList<Position> boxPosition)
	{
		this.boxPosition = boxPosition;
	}

	public SokobanState(Position playerPosition, ArrayList<Position> boxList) {
		super();
		this.playerPosition = playerPosition;
		this.boxPosition = boxList;
	}

	public SokobanState() {
		super();
		// TODO Auto-generated constructor stub
		this.boxPosition = new ArrayList<>();
		this.playerPosition = new Position();
	}

	public String toString()
	{
		return "player: (" + this.getPlayerPosition() + ") box: (" + this.getBoxPosition() + ")";
	}

	@Override
	public boolean equals(Object obj)
	{


			if (boxListComparing(this.boxPosition, ((SokobanState) obj).boxPosition)) {
				return true;
			} else
				return false;


	}

	/*@Override
	public int hashCode()
	{
		
		return 0;
		
	}*/
	public boolean boxListComparing(List<Position> A, List<Position> B)
	{
		int counter = 0;
		for (Position p1 : A) {
			for (Position p2 : B) {
				if (p1.equals(p2))
					counter++;
			}
		}
		if (counter == A.size())
			return true;
		else
			return false;

	}

}
