package mathClasses;

import java.util.ArrayList;
import java.util.Random;

public class GenerateBar {
	
	private int fraction;
	private int total;
	private int randomAnsIndex;
	
	public GenerateBar(int total)
	{
		this.total = total;
		randFract();
	}
	

	public void randFract()
	{
		Random r = new Random();
		fraction = (int)(r.nextDouble()*(total+1));
		randomAnsIndex = (int)(r.nextDouble()*4) + 1;
	}

	public int getAnswer()
	{
		return fraction;
	}
	
	public ArrayList<String> randomAnswers(int num)
	{
		Random r = new Random();
		ArrayList<String> ans = new ArrayList<String>();
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for(int i = 0; i < num; i ++)
		{
			int temp = (int)(r.nextDouble()*(total+1));
			while(temp == fraction|| nums.contains(ans))
				temp = (int)(r.nextDouble()*total+1);
			String a = ""+temp + " / " + total;
			
			ans.add(a);
			nums.add(temp);
		}
		return ans;
		
	}
	
	public String getAnswerString()
	{
		return ""+fraction+" / 10";
	}
	
	public int getRandom()
	{
		return randomAnsIndex;
	}
	
	public boolean isBigger(float x, float y)
	{
		return Math.abs(x) >= Math.abs(y);
	}
}
