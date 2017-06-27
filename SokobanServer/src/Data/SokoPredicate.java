package Data;

import StripsLib.Predicate;
/**
 * 
 * @author Sahar Mizrahi and Gal Ezra
 *
 */
public class SokoPredicate extends Predicate
{

	public SokoPredicate(String type, String id, String value) {
		super(type, id, value);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isContradict(Predicate p)
	{
		return super.isContradict(p) || (!id.equals(p.getId())&& value.equals(p.getValue())||(value.equals(p.getValue())&&(!type.equals(p.getType()))));
		
	}

}
