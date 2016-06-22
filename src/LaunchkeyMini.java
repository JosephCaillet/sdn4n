import javax.sound.midi.*;
import java.util.HashMap;

/**
 * Created by joseph on 22/06/16.
 */
public class LaunchkeyMini
{
	private MidiDevice midiDevice = null;
	private Receiver midiReceiver;

	private static HashMap<Integer, Integer> padNumber;

	static
	{
		padNumber = new HashMap<Integer, Integer>();
		padNumber.put(1, 0x60);
		padNumber.put(2, 0x61);
		padNumber.put(3, 0x62);
		padNumber.put(4, 0x63);
		padNumber.put(5, 0x64);
		padNumber.put(6, 0x65);
		padNumber.put(7, 0x66);
		padNumber.put(8, 0x67);
		padNumber.put(9, 0x68);
		padNumber.put(10, 0x70);
		padNumber.put(11, 0x71);
		padNumber.put(12, 0x72);
		padNumber.put(13, 0x73);
		padNumber.put(14, 0x74);
		padNumber.put(15, 0x75);
		padNumber.put(16, 0x76);
		padNumber.put(17, 0x77);
		padNumber.put(18, 0x78);
	}

	public boolean open()
	{
		close();

		MidiDevice.Info[] devicesTabInfo = MidiSystem.getMidiDeviceInfo();
		for(MidiDevice.Info info : devicesTabInfo)
		{
			try
			{
				if(info.getDescription().contains("Launchkey Mini") && info.getName().contains("1"))
				{
					midiDevice = MidiSystem.getMidiDevice(info);

					if(midiDevice.isOpen())
					{
						System.out.println("Chosen device:");
						System.out.println("Name:\t" + info.getName());
						System.out.println("Desc:\t" + info.getDescription());
						System.out.println("Vendor:\t" + info.getVendor());
						System.out.println("Ver:\t" + info.getVersion());
						System.out.println("Device is already opened in another program.");
						close();
						break;
					}

					midiDevice.open();

					if(midiDevice.getMaxReceivers() == 0)
					{
						close();
						continue;
					}

					System.out.println("Chosen device:");
					System.out.println("Name:\t" + info.getName());
					System.out.println("Desc:\t" + info.getDescription());
					System.out.println("Vendor:\t" + info.getVendor());
					System.out.println("Ver:\t" + info.getVersion());

					midiReceiver = midiDevice.getReceiver();
					enableInControl();
					break;
				}
			}
			catch(MidiUnavailableException e)
			{
				e.printStackTrace();
			}
		}

		return midiDevice != null;
	}

	public void close()
	{
		if(midiDevice != null)
		{
			midiDevice.close();
			midiDevice = null;
		}
	}

	public void sendData(int data1, int data2)
	{
		try
		{
			midiReceiver.send(new ShortMessage(0x90, data1, data2), -1);
		}
		catch(InvalidMidiDataException e)
		{
			e.printStackTrace();
		}
	}

	private void enableInControl()
	{
		sendData(0x0c, 0x7f);
	}

	public void setPadColor(int num, int green, int red)
	{
		if(!padNumber.containsKey(num))
		{
			return;
		}
		sendData(padNumber.get(num), (green << 4) + red);
	}

	public void resetPad()
	{
		resetPad(0,0);
	}

	public void resetPad(int green, int red)
	{
		for(int i = 1; i < 19; i++)
		{
			setPadColor(i, green, red);
		}
	}

	public void testAllPadColor()
	{
		setPadColor(1,3,3);
		setPadColor(2,0,1);
		setPadColor(3,0,2);
		setPadColor(4,0,3);
		setPadColor(5,1,0);
		setPadColor(6,1,1);
		setPadColor(7,1,2);
		setPadColor(8,1,3);

		setPadColor(10,2,0);
		setPadColor(11,2,1);
		setPadColor(12,2,2);
		setPadColor(13,2,3);
		setPadColor(14,3,0);
		setPadColor(15,3,1);
		setPadColor(16,3,2);
		setPadColor(17,3,3);

		setPadColor(9,3,0);
		setPadColor(18,0,3);
	}
}