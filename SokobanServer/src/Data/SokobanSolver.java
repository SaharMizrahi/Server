package Data;

import java.util.List;

import Model.Data.Level;
import Model.Data.Level2D;
import StripsLib.PlanAction;
import StripsLib.Strips;
/**
 * 
 * @author Sahar Mizrahi and Gal Ezra
 * 
 */
public class SokobanSolver
{
	public SokobanSolution solve2DLevel(Level level)
	{
		SokobanSolution solution = null;
		if(level instanceof Level2D)
		{
			PlannableSokoban ps=new PlannableSokoban((Level2D) level);
			Strips strips=new Strips();
			List<PlanAction> list=strips.plan(ps);
			solution=new SokobanSolution(list);
		}
		if(solution==null)
			return null;
		else 
			return solution;
		
	}

}
