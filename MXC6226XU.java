/* Controleverything.com - MXC6226XU
 
 https://www.controleverything.com/content/Accelorometer?sku=MXC6226XU_I2CS
 
 Firmware v1.0 - java

 Author: Yadwinder Singh
 
 4-Position Orientation Detection
 Fully Integrated Thermal Accelerometer
 Shock Survival Greater than 50,000 g
 X/Y Axis, 8-bit, Acceleration A/D Output (+ -2g)
 Produces no Mechanical Sounds
 Operating Supply Voltage from 2.5V to 5.5V
 0x16 I2C Start Address
 Hardware version - Rev A.
 
 Platform : Raspberry pi

 Project uses pi4j library. 
 Please follow a detailed tutorial to install pi4j here.
 
 http://pi4j.com/install.html
 
 Compile the java program with command pi4j Filename.java
 Run it with pi4j Filename
 
 */
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class MXC6226XU
{

   public final static int MXC6226XU_ADDRESS = 0x16;
    
   public final static int X_REG_ADDRESS = 0x00;
   public final static int Y_REG_ADDRESS = 0x01;
   public final static int STATUS_ADDRESS = 0x02;
    
   byte[] data = new byte[2];

    double x_acc = 0.0;
    double y_acc = 0.0;
    double tilt = 0.0;
   private static boolean verbose = true;
  
   private I2CBus bus;
   private I2CDevice mxc6226xu;
  
   public MXC6226XU() throws Exception

    {
        this(MXC6226XU_ADDRESS);
  
    }

  public MXC6226XU(int address)  throws Exception
  {
    try
    {
      // Get i2c bus
      bus = I2CFactory.getInstance(I2CBus.BUS_1); // Depends onthe RasPI version
      if (verbose)
        System.out.println("Connected to bus. OK.");

      // Get device itself
      mxc6226xu = bus.getDevice(address);
      if (verbose)
        System.out.println("Connected to device. OK.");
	setConfig();
    }
    catch (IOException e)
    {
      System.err.println(e.getMessage());
    }
  }

	public void setConfig() throws Exception
	{
		mxc6226xu.write((byte)0x00);

	}

    public double get_Acc() throws Exception
    {
        byte[] b = new byte[2];
               
        try
        {
        
          mxc6226xu.read(X_REG_ADDRESS, b,0,2);
          
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
        
        


	//Calculate heading
        double heading = 180 * Math.atan2(b[1],b[0])/3.14;
        System.out.println("heading  :" + heading  );

	Thread.sleep(10);
        
        /* x_acc = (double) b[0] / 64;
        y_acc = (double) b[1] / 64;
        
        System.out.println(" X ACC: " + b[0] );
        System.out.println(" Y ACC: " + b[1] );
        */

	return heading *3;
    }
   

    
}
