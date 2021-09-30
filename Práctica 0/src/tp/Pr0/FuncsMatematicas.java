package tp.Pr0;

public class FuncsMatematicas {

	public static int factorial (int n)
	{
		int fact = 1;
		
		if (n < 0)
		{
			fact = 0;
		}
		
		else if (n > 0)
		{
			for(int i = n; i > 0; i--)
			{
				fact = fact*i;
			}
		}
		
		return fact;
	}
	
	public static int combinatorio (int n, int k)
	{
		int comb = 0;
		
		if((k >= 0) && (k <= n))
		{
			comb = (factorial(n)/(factorial(k)*factorial(n-k)));
		}
		
		return comb;	
	}
	
	public static void main(String[] args) 
	{
		for (int i = 0; i < 6; ++i) 
		{
			for (int j = 0; j <= i; ++j)
				System.out.print(FuncsMatematicas.combinatorio(i,j) + " ");
			
			System.out.println();
		}

	}

}
