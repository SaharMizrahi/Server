package Data;

import java.util.HashMap;
import java.util.LinkedList;

import Model.Data.Item;
import Model.Data.Level2D;
import Model.Data.Position;
import SearchLib.Action;
import SearchLib.ComplexAction;
import SearchLib.Searcher;
import SearchLib.Solution;
import SearchLib.State;
/**
 * 
 * @author Sahar Mizrahi and Gal Ezra
 *
 */
public class BoxSearchable extends CommonSearchable
{
	private Searcher<Position> searcher;
	private PlayerSearchable playerSearchable;
	private Position playerPosition;
	private char[][] charMap;
	private LinkedList<Position> currentBoxPositions;
	private Position currentSearchableBox;

	
	/************************/
	/***implemented methods**/
	/************************/
	@Override
	public HashMap<ComplexAction, State<Position>> getAllPossibleStates(State<Position> s)
	{
		// TODO Auto-generated method stub

		Position temp=null;
		//updating player/box positions for checking possibles moves
		if(s.getCameFrom()!=null)//its not the initial state
		{
		temp=s.getCameFrom().getState();
		playerPosition=temp;
		currentBoxPositions.remove(currentSearchableBox);
		currentBoxPositions.add(s.getState());
		playerSearchable.setCurrentBoxPositions(currentBoxPositions);
		}
		
		
		
		Solution playerPath=null;
		playerSearchable.setFromPosition(playerPosition);
		playerSearchable.setLevel(getLevel());
		State<Position> state=null;
		Position boxNextPos=null;
		ComplexAction ca=null;
		HashMap<ComplexAction, State<Position>> possibleStates=new HashMap<>();
		Position boxPos=s.getState();
		for(Action action: Action.values())
		{
			if(this.checkPossibleMove(boxPos, action))
			{
				playerSearchable.setFromPosition(playerPosition);
				playerSearchable.setToPosition(this.getPlayerNeededPosition(boxPos, action));
				playerSearchable.setLevel(getLevel());
				playerPath=searcher.search(playerSearchable);
				if(playerPath!=null)
				{
					boxNextPos=this.getBoxNextPosition(boxPos, action);
					ca=new ComplexAction(action, playerPath.getActionList());
					state=new State<Position>(s, s.getCost()+1,boxNextPos , ca);
					possibleStates.put(ca, state);

					
				}
			}
		}
		if(s.getCameFrom()!=null)
		{

			currentBoxPositions.remove(s.getState());
			currentBoxPositions.add(currentSearchableBox);

			
		}
		return possibleStates;
		
		

	}

	//constructor
 	public BoxSearchable(Position fromPosition, Position toPosition, Level2D level,
			Searcher<Position> searcher,PlayerSearchable playerSearchable) {
		super(fromPosition, toPosition, level);
		// TODO Auto-generated constructor stub
		currentBoxPositions=new LinkedList<>();
		for(Item it : level.getBoxList())
			currentBoxPositions.add(it.getPos());
		this.searcher=searcher;
		this.playerSearchable=playerSearchable;
		if(playerSearchable!=null)
			this.playerPosition=playerSearchable.getFromPosition();
		initCharMap();
	}
	@Override
	public boolean checkPossibleMove(Position currentPosition, Action action)
	{
		// TODO Auto-generated method stub
		Position playerPos=null;
		Position goalPos=null;
		switch(action)
		{
		case UP:
			playerPos=currentPosition.getDown();
			goalPos=currentPosition.getUp();
			break;
		case DOWN:
			playerPos=currentPosition.getUp();
			goalPos=currentPosition.getDown();

			break;
		case RIGHT:
			playerPos=currentPosition.getLeft();
			goalPos=currentPosition.getRight();
			break;
		case LEFT:
			playerPos=currentPosition.getRight();
			goalPos=currentPosition.getLeft();
			break;
		}
		if(this.getLevel().isValidPosition(playerPos)&&this.getLevel().isValidPosition(goalPos))//it's not out of bounds
		{
			//check if player can go there
			char player,goal;
			player=charMap[playerPos.getRow()][playerPos.getCol()];
			goal=charMap[goalPos.getRow()][goalPos.getCol()];
			
			if((player=='A'||player==' '||player=='o')&&(goal=='A'||goal==' '||goal=='o'))//both are empty slots
				return true;
			else if(player=='#'||goal=='#')//one of them is a wall
					return false;
			else
			{
				for(Position pos : this.currentBoxPositions)
				{
					if(playerPos.equals(pos)||(goalPos.equals(pos)))
						return false;
				}
				return true;
			}
				
			
		}

		return false;
		
		
		

	}

 	
 	
	/***************************/
	/****getters and setters****/
	/***************************/
	public char[][] getCharMap()
	{
		return charMap;
	}
	public void setCharMap(char[][] charMap)
	{
		this.charMap = charMap;
	}
	public Position getCurrentSearchableBox()
	{
		return currentSearchableBox;
	}
	public void setCurrentSearchableBox(Position currentSearchableBox)
	{
		this.currentSearchableBox = currentSearchableBox;
	}
	public Searcher<Position> getSearcher()
	{
		return searcher;
	}
	public void setSearcher(Searcher<Position> searcher)
	{
		this.searcher = searcher;
	}
	public void setPlayerSearchable(PlayerSearchable playerSearchable)
	{
		this.playerSearchable = playerSearchable;
	}
	public Position getPlayerPosition()
	{
		return playerPosition;
	}
	public PlayerSearchable getPlayerSearchable()
	{
		return playerSearchable;
	}
	public void setPlayerPosition(Position playerPosition)
	{
		if(playerSearchable!=null)
			playerSearchable.setFromPosition(playerPosition);
		this.playerPosition = playerPosition;
	}
	public LinkedList<Position> getCurrentBoxPositions()
	{
		return currentBoxPositions;
	}
	public void setCurrentBoxPositions(LinkedList<Position> currentBoxPositions)
	{
		this.currentBoxPositions = currentBoxPositions;
	}
	

	
	/***********************/
	/***private methods*****/
	/***********************/
	private void initCharMap()
	{
		
		Item[][] itemMap=this.getLevel().getMap();
		if(this.getLevel()!=null)
		{
			int width=this.getLevel().getWidth();
			int hight=this.getLevel().getLength();
			charMap=new char[hight][width];
			for(int i=0;i<hight;i++)
				for(int j=0;j<width;j++)
				{
					
					charMap[i][j]=itemMap[i][j].getChar();


				}
		}
	}
	private Position getPlayerNeededPosition(Position boxPos,Action action)
	{
		switch (action)
		{
			case UP:
				return boxPos.getDown();
			case DOWN:
				return boxPos.getUp();
			case RIGHT:
				return boxPos.getLeft();
			case LEFT:
				return boxPos.getRight();
		}
		return null;
	}
	private Position getBoxNextPosition(Position position,Action action)
	{
		
		switch(action)
		{
		case UP:
			return position.getUp();
		case DOWN:
			return position.getDown();
		case RIGHT:
			return position.getRight();
		case LEFT:
			return position.getLeft();
		}
		return null;
		
	}


}
