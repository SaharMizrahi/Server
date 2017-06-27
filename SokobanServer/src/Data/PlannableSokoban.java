package Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import Model.Data.Item;
import Model.Data.Level2D;
import Model.Data.Position;
import SearchLib.Action;
import SearchLib.BFS;
import SearchLib.Solution;
import StripsLib.Clause;
import StripsLib.PlanAction;
import StripsLib.Plannable;
import StripsLib.Predicate;
/**
 * 
 * @author Sahar Mizrahi and Gal Ezra
 * implemetation of plannable
 *
 */
public class PlannableSokoban implements Plannable
{
	private Level2D level;
	private Clause goal;
	private Clause kb;
	private BFS mainBfs;
	private BoxSearchable searchable;
	private static Position playerCurrentPosition;
	private static LinkedList<Position> boxCurrentPositions;
	
	/*************************/
	/***implemented methods***/
	/*************************/
	@Override
	public Clause getGoal()
	{
		// TODO Auto-generated method stub
		return this.goal;
	}

	@Override
	public Clause getKnowledgebase()
	{
		// TODO Auto-generated method stub
		return this.kb;
	}
	@Override
	public Set<PlanAction> getSatisfyingActions(Predicate topPredicate)
	{
		// TODO Auto-generated method stub
		Set<PlanAction> satisfyingActions=null;
		Predicate boxKbPred=null;
		Predicate playerKbPred=null;
		Position playerPos=null,boxPos=null,goalPos=null;
		if(topPredicate.getType().equals("BoxAt"))
		{

			for(Predicate p : kb.getPredicatesSet())
			{
				if(topPredicate.getId().equals(p.getId()))
					boxKbPred=p;
				if(p.getType().equals("PlayerAt"))
					playerKbPred=p;
			}
			playerPos=new Position(playerKbPred.getValue().toCharArray()[1]-48, playerKbPred.getValue().toCharArray()[3]-48);
			boxPos=new Position(boxKbPred.getValue().toCharArray()[1]-48, boxKbPred.getValue().toCharArray()[3]-48);
			goalPos=new Position(topPredicate.getValue().toCharArray()[1]-48, topPredicate.getValue().toCharArray()[3]-48);
			PlayerSearchable ps=new PlayerSearchable(playerPos, boxPos, level);
			ps.setCurrentBoxPositions(boxCurrentPositions);
			BFS pbfs=new BFS<>();
			searchable.setFromPosition(boxPos);
			searchable.setToPosition(goalPos);
			searchable.setSearcher(pbfs);
			searchable.setPlayerPosition(playerPos);
			searchable.setCurrentBoxPositions(boxCurrentPositions);
			searchable.setCurrentSearchableBox(boxPos);
			
			Solution solution=mainBfs.search(searchable);
		
			if(solution!=null)
			{
				//this is the player last position
				playerCurrentPosition=(Position) mainBfs.getFinalState().getCameFrom().getState();
				boxCurrentPositions.remove(boxPos);
				boxCurrentPositions.add(goalPos);
				
				satisfyingActions=new HashSet<>();
				PlanAction pa=new PlanAction("MoveBox",topPredicate.getId() , topPredicate.getValue());
				pa.setEffects(new Clause(
						new SokoPredicate("BoxAt", pa.getId(), pa.getValue()),
						new SokoPredicate("PlayerAt", "Player", playerCurrentPosition.toString()),
						new SokoPredicate("ClearAt", playerPos.toString(), playerPos.toString())));
				pa.setPreConditions(new Clause(new SokoPredicate("ClearAt", topPredicate.getValue(), topPredicate.getValue())));
				pa.setSubActions(solution.getActionList());
				satisfyingActions.add(pa);
			}
			else
				return null;
		}
		
		return satisfyingActions;
	}

	//constructor
	public PlannableSokoban(Level2D level) {
		super();
		this.level = level;
		boxCurrentPositions=new LinkedList<>();
		mainBfs=new BFS<>();
		if (level != null) {
			kb=new Clause(null);
			goal=new Clause(null);
			Predicate p = null;
			Clause g = new Clause(null);
			
			int boxCounter=1;
			for(int i=0;i<level.getWidth();i++)
				for(int j=0;j<level.getLength();j++)
				{
					
					switch(level.getMap()[i][j].getChar())
					{
					case 'A':
						p=new SokoPredicate("PlayerAt", "Player", "("+i+","+j+")");
						playerCurrentPosition=new Position(i, j);
						kb.update(p);

						break;
					case ' ':
						p=new SokoPredicate("ClearAt", "("+i+","+j+")" , "("+i+","+j+")");
						kb.update(p);


						break;
					case '@':
						p=new SokoPredicate("BoxAt", "b"+boxCounter, "("+i+","+j+")");
						boxCurrentPositions.add(new Position(i, j));
						kb.update(p);

						boxCounter++;
						break;
					case 'o':
						p=new SokoPredicate("ClearAt", "("+i+","+j+")" , "("+i+","+j+")");
						kb.update(p);

						break;
						
	
					

					}

				}
			 HashMap<String, String> hm= this.getCoupledBoxAndDest(level);
			for(String id : hm.keySet())
			{
				p=new SokoPredicate("BoxAt", id, hm.get(id));
				goal.update(p);
			}
			searchable=new BoxSearchable(playerCurrentPosition, playerCurrentPosition, level, mainBfs, new PlayerSearchable(playerCurrentPosition, playerCurrentPosition, level));
			searchable.getPlayerSearchable().setCurrentBoxPositions(boxCurrentPositions);

		}

	}

	/*********************/
	/**private methods****/
	/*********************/
	private HashMap<String, String> getCoupledBoxAndDest(Level2D level)
	{
		HashMap<String, String> coupleTable=new HashMap<>();
		ArrayList<Position> bl=new ArrayList<>();
		for(Item b : level.getBoxList())
		{
			bl.add(b.getPos());
		}
		ArrayList<Position> dl=new ArrayList<>();
		for (Item d : level.getDestinationList())
		{
			dl.add(d.getPos());
		}
		for(int i=0;i<dl.size();i++)
		{
			coupleTable.put("b"+(i+1), dl.get(i).toString());
		}
		
		
		return coupleTable;
	}



	
	private Clause getSolutionPreConditions(Solution solution,Position pos)
	{
		Clause pre=new Clause(null);
		Position next=null;
		Action a=null;
		for(int i=0;i<solution.getActionList().size()-1;i++)
		{
			a=solution.getActionList().get(i);
			switch(a)
			{
			case UP:
				next=pos.getUp();
				break;
			case DOWN:
				next=pos.getDown();
				break;
			case LEFT:
				next=pos.getLeft();
				break;
			case RIGHT:
				next=pos.getRight();
				
				break;
			}
			pre.update(new Predicate("ClearAt", next.toString(), next.toString()));
			pos=next;
			
			
		}
		return pre;
		
		
	}

}
