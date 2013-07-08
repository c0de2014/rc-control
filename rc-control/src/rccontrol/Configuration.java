/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rccontrol;

/**
 *
 * @author David
 */
public class Configuration {

// video settings
    public static boolean useVideo;
    public static int captureDeviceIndex;
    // public static int captureDeviceName;
    public static int captureDeviceFormatIndex;
    public static int framerate = 25;
// output settings
    public static String comPort;
    public static int serialSpeed;
// input settings
    public static int inputDeviceIndex;
    //    public static int inputDeviceName;
    public static int channelCount = 4;
    public static int trimms[] = {0, 0, 0, 0};
    //new int[channelCount];
    public static float sensitivity[] = {1, 1, (float) 0.5, 1};
    public static float sensitivity2[] = {1, 1, (float) 0.5, 1};
    //new int[channelCount];
    public static int channelMapping[] = new int[channelCount];
    public static boolean reverse[] = {true, true, true, false};
    //new boolean[channelCount];
    public static int channelMapping2[] = new int[channelCount];
    public static boolean useSecondInput[] = new boolean[channelCount];
    public static int channelOffset[] = {0,0,0,0};
    public static boolean reverse2[] = {false, false, false, false};
    // etc
    public static boolean confirmed = false;

    public static boolean sendValues = false;

    public static core callback;
    private static ConfigurationWindow configurationWindow;

    public void startCore() throws Exception {
        callback.start();
    }

    public Configuration(core in) {
        callback = in;
        configurationWindow = new ConfigurationWindow(this);
    }

    public void loadFile() {
    }

    public void saveFile() {
    }

    public void openConfigurationWindow() {
        configurationWindow.main(null);
    }
}
