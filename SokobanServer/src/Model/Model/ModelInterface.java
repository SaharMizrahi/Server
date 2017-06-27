package Model.Model;

public interface ModelInterface {
	
	public void SearchForSolution(String levelDescription);
	public void SearchForDBData(String data);
	public void postRecord(String record);
	public boolean isSolution();
	public boolean isDB();
	public String getSolution();
	public String getDBData();
	
	
}

