/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rccontrol;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class Output extends Thread {

    static InputStream input;
    static OutputStream output;
    static CommPortIdentifier portId;
//            = CommPortIdentifier.getPortIdentifier("COM5");
    static SerialPort port;
//    = (SerialPort) portId.open("serial talk", 4000);
    static core callback;
    static int pollingDelay = 5;

    public Output(core in) {
        callback = in;


    }

    public List<String> showPorts() {
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
        List<String> commPorts = new LinkedList();
//System.out.println(" showports called");
        while (ports.hasMoreElements()) {
            CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
            if (port.getPortType() == CommPortIdentifier.PORT_SERIAL) {
//System.out.println("Port found: " + port.getName());
                commPorts.add(port.getName());
            }
        }
        return commPorts;
    }

    public void initOutput(String commPort, int commSpeed) {
        try {
            portId = CommPortIdentifier.getPortIdentifier(commPort);
            port = (SerialPort) portId.open("ser", 4000);
            input = port.getInputStream();
            output = port.getOutputStream();

            port.setSerialPortParams(commSpeed,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (Exception e) {
            System.out.println("Initialization of Output failed");
            e.printStackTrace();
        }
    }

    private int[] pollInputAndCalculateNewValues() {
        float inValues[] = new float[callback.configuration.channelCount];
        float inSecondValues[] = new float[callback.configuration.channelCount];
        int newValues[] = new int[callback.configuration.channelCount];
//              System.out.println("Device: " + callback.input.showInputDevices().get(callback.configuration.inputDeviceIndex));
        callback.input.poll();

        for (int i = 0; i < callback.configuration.channelCount; i++) {
            inValues[i] = callback.input.getValueOfComponent(callback.configuration.channelMapping[i]);
            if (callback.configuration.useSecondInput[i]){
                inSecondValues[i] = callback.input.getValueOfComponent(callback.configuration.channelMapping2[i]);
            }
        }

// calculate new Values.

        for (int i = 0; i < callback.configuration.channelCount; i++) {

//    System.out.print("X"+i+" ");
//    System.out.print("vorher: " +inValues[i]);
            if (callback.configuration.reverse[i] == true) {
                inValues[i] = inValues[i] * (-1);

            }
            inValues[i] = (inValues[i] * callback.configuration.sensitivity[i]);

            if (callback.configuration.useSecondInput[i]){
                inSecondValues[i] = (inSecondValues[i] * callback.configuration.sensitivity2[i]);
//                System.out.println("vor:  "+ inValues[i] + " - " + inSecondValues[i]);
                if (callback.configuration.reverse2[i] == true) {
//                    System.out.println("invert");
                    inSecondValues[i] = inSecondValues[i] * (-1);
                }
//                System.out.print("nach: "+inValues[i] + " - " + inSecondValues[i]);
                inValues[i] = inValues[i] - inSecondValues[i];
//                System.out.print(" = " +inValues[i] );
            }

//    System.out.print(" nachher: " +inValues[i]+" ");


            newValues[i] = (int) (((((inValues[i]) // calculate with sensitivity factor
                    //                                / 10
                    ) // sensitivity 1.0 is 10
                    + 1.0) / 2 //                             (callback.configuration.sensitivity[i] + 1)
                    ) * 255); // normalize to value between 0..255



            newValues[i] = newValues[i] + callback.configuration.trimms[i] + callback.configuration.channelOffset[i];     // add trimms

            if (newValues[i] > 255) {                                       // cut after trimming
                newValues[i] = 255;
            }
            if (newValues[i] < 0) {
                newValues[i] = 0;
            }

        }

//        for (int i = 0; i < callback.configuration.channelCount; i++) {
//                                        System.out.print("Value[" + i + "-"
            //+ callback.configuration.channelMapping[i]
//                            + "]: " + inValues[i]+"  ");
//            System.out.print(" -> " //+ callback.configuration.channelMapping[i]
//                    + newValues[i] + "\t");
//        }
//        System.out.print("\n");

        
        callback.window.showInputValues(newValues);
        

        return newValues;
//        return newValues;
    }

//    public static void send_values(int a, int b, int c, int d) {
    public static void send_values(int values[]) {
        try {
            output.write('A');
            output.write(values[0]);
            output.write(values[1]);
            output.write(values[2]);
            output.write(values[3]);
//            System.out.println("gesendet");
        } catch (Exception e) {
            System.out.println("Exception occured while trying to send values to commPort");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

// Start erst wenn Gashebel auf 0 gestellt ist
            System.out.println("set Throttle low!");
            JOptionPane.showMessageDialog(null, "Still keep RC-Modell OFF!!\n" +
                    "You will be notified when you can try to power it on..\n" +
                    "\n" +
                    "Now set Throttle input low!", "Start", JOptionPane.OK_OPTION);


            callback.window.toFront();
   //         initOutput(callback.configuration.comPort, callback.configuration.serialSpeed);

            // pollInputAndCalculateNewValues()[0] liest den ersten Wert im Array
            // .. dh throttle wert wird geprüft

        callback.input.initInputDevice(callback.configuration.inputDeviceIndex);

//        JOptionPane.showMessageDialog(null, "Input init done, now your RC-Modell could go wild!", "Start", JOptionPane.OK_OPTION);

        initOutput(callback.configuration.comPort, callback.configuration.serialSpeed);

//        JOptionPane.showMessageDialog(null, "Output init done, now your RC-Modell could go wild!", "Start", JOptionPane.OK_OPTION);

            while (pollInputAndCalculateNewValues()[0] > 5) {
                // warteschleife.. logik steckt im schleifenkopf
//                System.out.print(".");
//                Thread.sleep(500);
            }
//System.out.print("\n");

            System.out.println("Throttle is now low, click 'send values' to start transmission");

JOptionPane.showMessageDialog(null, "Now you can try to power on your RC-Modell..\n" +
        "BE CAREFUL!\n" +
        "Check controls before you free your RC-Modell!", "Start", JOptionPane.OK_OPTION);

            

            while (true) {

                Thread.sleep(pollingDelay);
                if (callback.configuration.sendValues == true){
                    send_values(pollInputAndCalculateNewValues());
                }
                else{
                    
                    pollInputAndCalculateNewValues();
                }

            }

        } catch (Exception e) {
            System.out.println("Error while trying get input");
        }

    }
}
