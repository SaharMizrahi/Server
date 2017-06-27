package Data;

import java.util.HashMap;
import java.util.LinkedList;

import Model.Data.Item;
import Model.Data.Level2D;
import Model.Data.Position;
import Model.Data.Wall;
import SearchLib.Action;
import SearchLib.ComplexAction;
import SearchLib.State;

public class PlayerSearchable extends CommonSearchable
{

	private char[][] charMap;
	private LinkedList<Position> currentBoxPositions;
	public PlayerSearchable(Position fromPosition, Position toPosition, Level2D level) {
		super(fromPosition, toPosition, level);
		// TODO Auto-generated constructor stub
	}

	public LinkedList<Position> getCurrentBoxPositions()
	{
		return currentBoxPositions;
	}

	public void setCurrentBoxPositions(LinkedList<Position> currentBoxPositions)
	{
		this.currentBoxPositions = currentBoxPositions;
	}

	@Override
	public HashMap<ComplexAction, State<Position>> getAllPossibleStates(State<Position> s)
	{
		// TODO Auto-generated method stub
		HashMap<ComplexAction,State<Position>> possibleStates=new HashMap<>();
		Position currentPosition=s.getState();
		
		//move up
		if(checkPossibleMove(currentPosition, Action.UP))
			possibleStates.put(new ComplexAction(Action.UP, null), new State<>(s,s.getCost()+1,currentPosition.getUp(),new ComplexAction(Action.UP, null)));
		//move down
		if(checkPossibleMove(currentPosition, Action.DOWN))
			possibleStates.put(new ComplexAction(Action.DOWN, null), new State<>(s,s.getCost()+1,currentPosition.getDown(),new ComplexAction(Action.DOWN, null)));
		//move left
		if(checkPossibleMove(currentPosition, Action.LEFT))
			possibleStates.put(new ComplexAction(Action.LEFT, null), new State<>(s,s.getCost()+1,currentPosition.getLeft(),new ComplexAction(Action.LEFT, null)));
		//move right
		if(checkPossibleMove(currentPosition, Action.RIGHT))
			possibleStates.put(new ComplexAction(Action.RIGHT, null), new State<>(s,s.getCost()+1,currentPosition.getRight(),new ComplexAction(Action.RIGHT, null)));

		
		return possibleStates;
	}

	@Override
	public boolean checkPossibleMove(Position currentPosition, Action action)
	{
		// TODO Auto-generated method stub
		Position firstPos=null;
		char c;
		Item it=null;

		switch(action)
		{//get the first and second positions
		case UP:
			firstPos=currentPosition.getUp();
			break;
			
		case DOWN:
			firstPos=currentPosition.getDown();
			break;
		case LEFT:
			firstPos=currentPosition.getLeft();
			break;
		case RIGHT:
			firstPos=currentPosition.getRight();
			break;
		}
		
		if(firstPos!=null)
		{
			if(this.getLevel().isValidPosition(firstPos))
			{
				for( Position pos : currentBoxPositions)//if there is a box
				{
					if(firstPos.equals(pos))
						return false;
				}
				
				it=this.getLevel().getItemByPosition(firstPos);
				if(it instanceof Wall)//if its a wall
					return false;
				return true;
				

				
			}
			else
				return false;
		}



		return false;
	}

	public char[][] getCharMap()
	{
		return charMap;
	}

	public void setCharMap(char[][] charMap)
	{
		this.charMap = charMap;
	}

	
}
