package entities;

public class maps {

	  public static	int[][] one =
			{
					{1,1,1,1,1},
					{1,6,2,4,1},
					{1,0,2,4,1},
					{1,1,1,1,1},
			
					
				

			 };
	  public static int[][] two =
			{
				{1,1,1,1,1,1,1,1},
				{1,1,1,0,0,0,1,1},
				{1,6,2,0,1,0,1,1},
				{1,0,1,0,0,4,0,1},
				{1,0,0,0,0,1,0,1},
				{1,1,0,1,0,0,0,1},
				{1,1,0,0,0,1,1,1},
				{1,1,1,1,1,1,1,1}
					
				

			 };
	  public static int[][] deepCopyIntMatrix(int[][] input) {
		    if (input == null)
		        return null;
		    int[][] result = new int[input.length][];
		    for (int r = 0; r < input.length; r++) {
		        result[r] = input[r].clone();
		    }
		    return result;
		}

	
}//end maps
