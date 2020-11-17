package buisnessLogic;

import java.util.Comparator;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import dbModelsnDAOs.Comments;
import dbModelsnDAOs.Picture;
import dbModelsnDAOs.User;

@Service
public class MulitComparator implements MulitComparatorIter
{

	@Override
	public int compare(Object o1, Object o2) {
		
		String o1Class = o1.getClass().getSimpleName();
		String o2Class = o2.getClass().getSimpleName();	
		
		if(o1Class.equals(o2Class))
		{
			switch(o1Class) 
			{
				case "Picture":
				{
					Picture pic1 = (Picture) o1;
					Picture pic2 = (Picture) o2;
					
					if(pic1.getId() < pic2.getId())
					{
						return 1;
					}
					else if(pic1.getId() > pic2.getId())
					{
						return -1;
					}
					else
					{
						return 0;
					}
				}
				case "Comments":
				{
					Comments com1 = (Comments) o1;
					Comments com2 = (Comments) o2;
					
					if(com1.getId() < com2.getId())
					{
						return 1;
					}
					else if(com1.getId() > com2.getId())
					{
						return -1;
					}
					else
					{
						return 0;
					}
				}
				case "User":
				{
					User u1 = (User) o1;
					User u2 = (User) o2;
					
					if(u1.getName().charAt(0) < u2.getName().charAt(0))
					{
						return -1;
					}
					else if(u1.getName().charAt(0) > u2.getName().charAt(0))
					{
						return 1;
					}
					else
					{
						return 0;
					}
				}
			
			}
			
		}		
		
		return 0; 
	}

}
