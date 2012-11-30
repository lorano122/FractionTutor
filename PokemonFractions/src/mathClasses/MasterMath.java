package mathClasses;

import java.util.Random;

public class MasterMath {
	private int ans;
	private Random r;
	private int first=-1,second=-1;
	private int denom;
	private String equation;
	
	public MasterMath(int denom)
	{
		this.denom = denom;
		r = new Random();
	}
	
	public void createEquation()
	{
		int temp1 = first;
		int temp2 = second;
		first = (int) (r.nextDouble()*(denom+1));
		while(temp1 == first || temp2 == first)
			first = (int) (r.nextDouble()*(denom+1));
		second = (int) (r.nextDouble()*(denom+1));
		while(temp2 == second || temp1 == first)
			second = (int) (r.nextDouble()*(denom+1));
		while((first + second) > denom)
		{
			if(first > second)
				first = (int) (r.nextDouble()*(denom+1));
			else if(second > first)
				second = (int) (r.nextDouble()*(denom+1));
			else
				second = (int) (r.nextDouble()*(denom+1));
		}
		equation = " "+first+" / denom  + " + second +"/ denom";
	}
	
	public String getEquation()
	{
		return equation;
	}

	public int getAnswerNum()
	{
		return ans;
	}
	
	public String getAnswerString()
	{
		return " "+ ans+" / "+denom+" ";
	}
	
	public boolean isCorrect(int x)
	{
		return x==ans;
	}
}
