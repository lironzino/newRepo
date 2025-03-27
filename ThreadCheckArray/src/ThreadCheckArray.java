public class ThreadCheckArray implements Runnable 
{
	private long startTime;
	private long endTime;
	private boolean flag;
	private boolean [] winArray;
	private boolean isWinner = false;
	SharedData sd;
	int[] array;
	int b;

	public long getExecutionTime() {
		return endTime - startTime;
	}

	public boolean isWinner() {
		return isWinner;
	}
	
	public ThreadCheckArray(SharedData sd) 
	{
		this.sd = sd;	
		synchronized (sd) 
		{
			array = sd.getArray();
			b = sd.getB();
		}		
		winArray = new boolean[array.length];
	}
	
	void rec(int n, int b)
	{
		synchronized (sd) 
		{
			if (sd.getFlag())
				return;
		}	
		if (n == 1)
		{
			if(b == 0 || b == array[n-1])
			{
				flag = true;
				synchronized (sd) {
					if (!sd.getFlag()) {
						sd.setFlag(true);
						sd.setWinnerThreadName(Thread.currentThread().getName());
					}
				}			
			}
			if (b == array[n-1])
				winArray[n-1] = true;
			return;
		}
		
		rec(n-1, b - array[n-1]);
		if (flag)
			winArray[n-1] = true;
		synchronized (sd) 
		{
			if (sd.getFlag())
				return;
		}	
		rec(n-1, b);
	}

	public void run() {
		startTime = System.currentTimeMillis();

		if (array.length != 1)
			if (Thread.currentThread().getName().equals("thread1"))
				rec(array.length-1, b - array[array.length - 1]);
			else 
				rec(array.length-1, b);
		if (array.length == 1)
			if (b == array[0] && !flag)
			{
				winArray[0] = true;
				flag = true;
				synchronized (sd) 
				{
					sd.setFlag(true);
					sd.setWinnerThreadName(Thread.currentThread().getName());
				}
			}
		if (flag)
		{
			if (Thread.currentThread().getName().equals("thread1"))
				winArray[array.length - 1] = true;
			synchronized (sd) 
			{
				sd.setWinArray(winArray);
				if (!isWinner) isWinner = true;
			}	
		}

		endTime = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName() + " took " + getExecutionTime() + " ms");
	}
}
