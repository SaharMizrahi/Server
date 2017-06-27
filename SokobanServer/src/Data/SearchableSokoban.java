package Data;


import java.util.ArrayList;
import java.util.HashMap;

import Model.Data.Item;
import Model.Data.Level2D;
import Model.Data.Position;
import SearchLib.Action;
import SearchLib.ComplexAction;
import SearchLib.Searchable;
import SearchLib.State;

public class SearchableSokoban implements Searchable<SokobanState>
{

	private Level2D level;
	private State<SokobanState> initialState;
	private State<SokobanState> goalState;

	
	
	public Level2D getLevel()
	{
		return level;
	}

	public void setLevel(Level2D level)
	{
		this.level = level;
	}

	public void setInitialState(State<SokobanState> initialState)
	{
		this.initialState = initialState;
	}

	public void setGoalState(State<SokobanState> goalState)
	{
		this.goalState = goalState;
	}

	public SearchableSokoban(Level2D level2) {
		super();

		this.level = level2;
		this.initInitialState();

		this.initGoalState();
		
	}

	@Override
	public State<SokobanState> getInitialState()
	{
		// TODO Auto-generated method stub
		return this.initialState;
	}

	@Override
	public State<SokobanState> getGoalState()
	{
		// TODO Auto-generated method stub
		return goalState;
	}

	@Override
	public HashMap<ComplexAction, State<SokobanState>> getAllPossibleStates(State<SokobanState> s)
	{
		// TODO Auto-generated method stub
		HashMap<ComplexAction,State<SokobanState>> possibleStates=new HashMap<>();
		State<SokobanState> resultState=null;
		Position playerPos=null;
		//up
		playerPos=s.getState().getBoxPosition().get(0).getDown();
		//down
		playerPos=s.getState().getBoxPosition().get(0).getUp();

		//right
		playerPos=s.getState().getBoxPosition().get(0).getLeft();

		//left
		playerPos=s.getState().getBoxPosition().get(0).getRight();

		
		
		/*//up
		if (checkDirection(s.getState().getPlayerPosition(), Action.UP))
		{
			resultState=createState(s, Action.UP);
			if (resultState!=null)
				possibleStates.put(resultState.getAction(), resultState);
		}
		//down
		if (checkDirection(s.getState().getPlayerPosition(), Action.DOWN))
		{

			resultState=createState(s, Action.DOWN);
			
			if (resultState!=null)
				possibleStates.put(resultState.getAction(), resultState);

		}
		//left
		if (checkDirection(s.getState().getPlayerPosition(), Action.LEFT))
		{
			resultState=createState(s, Action.LEFT);
			
			if (resultState!=null)
				possibleStates.put(resultState.getAction(), resultState);
		}
		//down
		if (checkDirection(s.getState().getPlayerPosition(), Action.RIGHT))
		{
			resultState=createState(s, Action.RIGHT);
			if (resultState!=null)
				possibleStates.put(resultState.getAction(), resultState);
		}*/
		
			
		return possibleStates;
	}
	public State<SokobanState> createState(State<SokobanState> state, ComplexAction action)
	{
		State<SokobanState> createdState=new State();
		Position firstPos=null;
		Position secondPos=null;
		//which direction
		switch (action.getAction())
		{
		case UP:
			firstPos=state.getState().getPlayerPosition().getUp();
			secondPos=firstPos.getUp();
			break;
		case DOWN:
			firstPos=state.getState().getPlayerPosition().getDown();
			secondPos=firstPos.getDown();
			break;
		case LEFT:
			firstPos=state.getState().getPlayerPosition().getLeft();
			secondPos=firstPos.getLeft();
			break;
		case RIGHT:
			firstPos=state.getState().getPlayerPosition().getRight();
			secondPos=firstPos.getRight();
			break;
		}
		createdState.setState(new SokobanState());

		for (Position p: state.getState().getBoxPosition())
		{
			if(p.equals(firstPos))
			{
				createdState.getState().getBoxPosition().add(secondPos);
			}
			
			else 
			{
				createdState.getState().getBoxPosition().add(p);
			}

			
		}
		createdState.getState().setPlayerPosition(firstPos);
		
		
		createdState.setAction(action);
		System.out.println("new state: "+createdState.getState()+" "+createdState.getAction());
		return createdState;
		
	}
	public boolean checkDirection(Position PlayerPos,Action action)
	{
		Position firstPos=null;
		Position secondPos=null;
		switch(action)
		{
			case UP:
				firstPos=PlayerPos.getUp();
				secondPos=firstPos.getUp();
				break;

			case DOWN:
				firstPos=PlayerPos.getDown();
				secondPos=firstPos.getDown();
				break;
			case LEFT:
				firstPos=PlayerPos.getLeft();
				secondPos=firstPos.getLeft();
				break;
			case RIGHT:
				firstPos=PlayerPos.getRight();
				secondPos=firstPos.getRight();
				break;
		}
		if(!(level.getItemByPosition(firstPos)==null))
		{
			//if is empty cell
			if(level.getItemByPosition(firstPos).getChar()==' ')
			{
				return true;
			}
			//if is box
			if(level.getItemByPosition(firstPos).getChar()=='@')
			{
				if((level.getItemByPosition(secondPos).getChar()==' ')||(level.getItemByPosition(secondPos).getChar()=='o'))
					return true;
				else
					return false;

			}
			//if is a wall
			else
				return false;
		}
		else
			return false;
		
	}



	public void initGoalState()
	{
		if (level!=null)
		{
			SokobanState inerState=new SokobanState();
			inerState.setPlayerPosition(level.getCharacter().getPos());
			ArrayList<Item> destinationList=level.getDestinationList();
			ArrayList<Position> positionList=new ArrayList<>();
			for (Item d: destinationList)
			
				positionList.add(d.getPos());
			
			inerState.setBoxPosition(positionList);
			State<SokobanState> returnState=new State<>(null,1000,inerState,null);
			this.goalState=returnState;
			System.out.println("goal state:"+goalState.getState().getPlayerPosition()+" "+goalState.getState().getBoxPosition().get(0));
			
		}
	}
	public void initInitialState()
	{
		if (level!=null)
		{
			SokobanState inerState=new SokobanState();
			inerState.setPlayerPosition(level.getCharacter().getPos());
			ArrayList<Item> boxList=level.getBoxList();
			ArrayList<Position> positionList=new ArrayList<>();
			for (Item b: boxList)
				positionList.add(b.getPos());
			inerState.setBoxPosition(positionList);
			State<SokobanState> returnState=new State<>(null,1000,inerState,null);
			this.initialState=returnState;
			System.out.println("init state:"+initialState.getState().getPlayerPosition()+" "+initialState.getState().getBoxPosition().get(0));
			
		}
		
	}
}
