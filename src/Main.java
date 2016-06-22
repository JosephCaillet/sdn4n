public class Main
{
	public static void sleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		LaunchkeyMini launchkeyMini = new LaunchkeyMini();
		launchkeyMini.open();

		launchkeyMini.resetPad();
		for(int i = 1; i<=21 ;i++)
		{
			launchkeyMini.resetPad();
			launchkeyMini.setPadColor(i, 3, 0);
			launchkeyMini.setPadColor(i-1, 2, 0);
			launchkeyMini.setPadColor(i-2, 1, 0);
			launchkeyMini.setPadColor(i-3, 0, 0);
			sleep(200);
		}

		for(int i = 1; i<=21 ;i++)
		{
			launchkeyMini.resetPad();
			launchkeyMini.setPadColor(i, 3, 3);
			launchkeyMini.setPadColor(i-1, 2, 2);
			launchkeyMini.setPadColor(i-2, 1, 1);
			launchkeyMini.setPadColor(i-3, 0, 0);
			sleep(200);
		}

		for(int i = 1; i<=21 ;i++)
		{
			launchkeyMini.resetPad();
			launchkeyMini.setPadColor(i, 0, 3);
			launchkeyMini.setPadColor(i-1, 0, 2);
			launchkeyMini.setPadColor(i-2, 0, 1);
			launchkeyMini.setPadColor(i-3, 0, 0);
			sleep(200);
		}

		launchkeyMini.testAllPadColor();

		launchkeyMini.close();
	}
}