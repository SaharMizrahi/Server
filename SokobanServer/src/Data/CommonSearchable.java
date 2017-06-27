package Data;

import Model.Data.Level2D;
import Model.Data.Position;
import SearchLib.Action;
import SearchLib.Searchable;
import SearchLib.State;
public abstract class CommonSearchable implements Searchable<Position>
{
	
	private Position fromPosition;
	private Position toPosition;
	private Level2D level;
	
	
	

	

	public Position getFromPosition()
	{
		return fromPosition;
	}

	public void setFromPosition(Position fromPosition)
	{
		this.fromPosition = fromPosition;
	}

	public Position getToPosition()
	{
		return toPosition;
	}

	public void setToPosition(Position toPosition)
	{
		this.toPosition = toPosition;
	}

	public Level2D getLevel()
	{
		return level;
	}

	public void setLevel(Level2D level)
	{
		this.level = level;
	}

	public abstract boolean checkPossibleMove(Position currentPosition,Action action);

	public CommonSearchable(Position fromPosition, Position toPosition, Level2D level) {
		super();
		this.fromPosition = fromPosition;
		this.toPosition = toPosition;

		this.level = level;
	}


	@Override
	public State<Position> getInitialState()
	{
		// TODO Auto-generated method stub
		State<Position> start=new State<>(null,0,fromPosition,null);
		
		return start;
	}

	@Override
	public State<Position> getGoalState()
	{
		// TODO Auto-generated method stub
		State<Position> goal=new State<>(null,0,toPosition,null);
		return goal;
	}



}
