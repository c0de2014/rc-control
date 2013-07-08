/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rccontrol;

// install jmf-2_1_1e-windows-i586.exe..
// ..and add C:\Program Files (x86)\JMF2.1.1e\lib\jmf.jar to Libraries
// Then run JMF Registry and detect CaptureDevices, otherwise no camera..
// ..will be found
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author David
 */
public class core {

    /**
     * @param args the command line arguments
     */
    static GUI window;
    static VideoInput videoInput;
    static core core = new core();
    static Configuration configuration;
    static Output output;
    static Input input;

    public void showImage(BufferedImage img) {
        window.showNewImage(img);
    }

    public core() {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
            System.out.println("LookAndFeel not supported");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Fehler");
            e.printStackTrace();
            System.exit(0);
        }
    }

// Exception Handling einbauen!
// ..muss von Video Klasse geworfen und behandelt werden!
//    public static void main(String[] args) throws Exception {
    public static void main(String[] args) throws Exception {
        JOptionPane.showMessageDialog(null, "Keep RC-Modell OFF!!\n" +
                "..until you are sure that Input and Output are correct!\n" +
                "Otherwise your RC-Modell and more could be seriously damaged!\n" +
                "The author won't take over any responsibilty! ", "Start", JOptionPane.OK_OPTION);

        videoInput = new VideoInput(core);
        output = new Output(core);
        input = new Input();

        configuration = new Configuration(core);
        configuration.openConfigurationWindow();
    }

    public static void start() throws Exception {
        // create videoInput, output, input and configuration
        // then start configuration
        // then , if confirmed, initialize with settings
        // then start GUI

        if (configuration.confirmed == false) {
            System.exit(0);
        }

//        input.initInputDevice(configuration.inputDeviceIndex);
//        output.initOutput(configuration.comPort, configuration.serialSpeed);

        

        window = new GUI(core);
        //window.openGUI();

//        videoInput.showCaptureDevices();

//        videoInput.showCaptureFormats(0);
        videoInput.start();
        output.start();

        //videoInput.startGrabbing();






    }
    /*
    //        videoInput.startCapture();
    System.out.println("core still running");

    Thread.sleep(10000);
    videoInput.stopCapture();


    // -------------------
    Thread.sleep(5000);
    videoInput.stop();
    videoInput = null;
    System.gc();
    videoInput = new VideoInput(window);

    //videoInput.startGrabbing();
    videoInput.initVideo();
    videoInput.start();



    //        videoInput.startCapture();
    System.out.println("core still running");
    Thread.sleep(10000);
    videoInput.stopCapture();

     */
}
