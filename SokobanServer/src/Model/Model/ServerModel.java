package Model.Model;

import java.util.Observable;

import Data.SokobanSolution;
import Data.SokobanSolver;
import Model.LevelCompressorAndGenerator;
import Model.Data.Level;

public class ServerModel  extends Observable implements ModelInterface   {
	private String solution;
	private LevelCompressorAndGenerator lcag;
	private String dbData;
	private boolean isSolution;
	private boolean isDB;

	/**
	 * Default constructor
	 */
	public ServerModel() {
		super();
		this.solution = "block";
		this.lcag = new LevelCompressorAndGenerator();
	}
	/*********************************/
	/******Implemented Methods********/
	/*********************************/
	@Override
	public void SearchForSolution(String levelDescription) {
		// TODO Auto-generated method stub
		String res=askForSolution(levelDescription);
		if(res.compareTo("block")==0)
			res=solveLevel(levelDescription);
		setSolution(res);

	}
	@Override
	public void SearchForDBData(String data) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void postRecord(String record) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getDBData() {
		// TODO Auto-generated method stub
		isDB=false;
		return this.dbData;
	}
	@Override
	public String getSolution() {
		// TODO Auto-generated method stub
		isSolution=false;
		return this.solution;
	}
	@Override
	public boolean isSolution()
	{
		return isSolution;
	}
	@Override
	public boolean isDB()
	{
		return isDB;
	}
	/********************************/
	/********getters and setters****/
	/********************************/
	public void setDBFlag(boolean bool)
	{
		isDB=bool;
	}
	public void setSolutionFlag(boolean bool)
	{
		isSolution=bool;
	}
	/**
	 * update the arrived db data and notify all observers
	 * @param dbData-new dbData
	 */
	public void setDbData(String dbData) {
		this.dbData = dbData;
		isDB=true;
		setChanged();
		notifyObservers();
	}
	/**
	 * update the arrived solution and notify all observers
	 * @param solution-new solution
	 */
	public void setSolution(String solution) {
		this.solution = solution;
		isSolution=true;
		setChanged();
		notifyObservers();
	}

	/**********************************/
	/*************private methods******/
	/**********************************/
	/**
	 * This method checks if there is a solution to the level described in 'levelDescription'
	 * @param levelDescription
	 * @return solution from WS or "block" if there isn't
	 */
	private String askForSolution(String levelDescription) {
		
		return "block";

	}
	/**
	 * 
	 * @param levelDescription
	 * @return solution  or "block" if he blocked
	 */
	private String solveLevel(String levelDescription) {
		Level level=lcag.generate(levelDescription);
		SokobanSolver solver=new SokobanSolver();
		SokobanSolution solution=solver.solve2DLevel(level);
		if(solution==null)
		{
			return "block";
		}
		else
			return solution.getCompresedSolution();
	}



	

}
