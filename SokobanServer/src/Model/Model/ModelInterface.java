package Model.Model;
/**
 * 
 * @author Sahar Mizrahi and Gal Ezra
 * 
 * This interface is our interface to our model in the sokoban server (MVVM)
 *
 */
public interface ModelInterface {
	/**
	 * This method search a solution for sokoban level
	 * @param levelDescription-compressed level description
	 * 
	 */
	public void SearchForSolution(String levelDescription);
	/**
	 * This method search a data base data
	 * @param data-data to use in the sql query
	 */
	public void SearchForDBData(String data);
	/**
	 * This method store a new data base data
	 * @param record-new record
	 */
	public void postRecord(String record);
	/**
	 * 
	 * @return true if the solution arrived from the WS
	 */
	public boolean isSolution();
	/**
	 * 
	 * @return true if the db data arrived from the WS
	 */
	public boolean isDB();
	/**
	 * 
	 * @return the arrived solution
	 */
	public String getSolution();
	/**
	 * 
	 * @return the arrived DB data
	 */
	public String getDBData();
	
	
}

