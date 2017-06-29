package Data;

import java.util.LinkedList;
import java.util.List;

import SearchLib.Action;
import StripsLib.PlanAction;
/**
 * 
 * @author Sahar Mizrahi and Gal Ezra
 *
 */
public class SokobanSolution
{
	private List<Action> solution;

	/**
	 * 
	 * @return compressed solution  <direction as char><number>
	 */
	public String getCompresedSolution()
	{
		String compresedSolution="";
		char prevCh=' ';
		char currentCh=' ';
		int numOfChars=0;
		if(solution!=null)
		{
			for(Action a : solution)
			{
				switch (a)
				{
				case UP:
					currentCh='u';
					break;
				case DOWN:
					currentCh='d';
					break;
				case RIGHT:					
					currentCh='r';
					break;
				case LEFT:
					currentCh='l';
					break;

				}
				if(prevCh!=' ')
				{
					if(prevCh==currentCh)
						numOfChars++;
					else
					{
						compresedSolution+=""+prevCh+""+numOfChars+"P";
						prevCh=currentCh;
						numOfChars=1;
					}
				}
				else//im in the first action
				{
					numOfChars=1;
					prevCh=currentCh;
				}
			}
			compresedSolution+=""+prevCh+""+numOfChars;
			return compresedSolution;
		}
		else
			return null;
		
	}
	//constructor	
	public SokobanSolution(List<PlanAction> solutionList) {
		super();
		solution=new LinkedList<>();
		for (PlanAction pa : solutionList)
		{
			for(Action action : pa.getSubActions())
			{
				solution.add(action);
			}
		}
	}
	public void print()
	{
		System.out.println("Solution is: ");
		System.out.println(toString());
	}
	public String toString()
	{
		String str="";
		for(Action a : solution)
		{
			str+=a.toString();
			str+=" ";
		}
		return str;
	}
	public List<Action> getSolution() {
		return solution;
	}
	public void setSolution(List<Action> solution) {
		this.solution = solution;
	}
	
	
	

}
