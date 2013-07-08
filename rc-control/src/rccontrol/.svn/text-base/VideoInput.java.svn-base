/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rccontrol;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.Manager;
import javax.media.Player;
import javax.media.control.FormatControl;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;

/**
 *
 * @author David
 */
public class VideoInput extends Thread {

    static GUI window;
    static core callback;
    private static int deviceIndex = 0;
    private static int formatIndex = 3;
    private static int framerate = 25;
    private static boolean running = false;
    private static boolean ready = false;
    private static Player player;
    private static FormatControl formatControl;
    private static VideoFormat vidformat = new VideoFormat(VideoFormat.YUV);

    public boolean isReadyForCapture() {
        return ready;
    }

    public void startCapture() throws Exception {
        System.out.println("startCapture called");
        if (ready == true) {
            running = true;
            startGrabbing();
        } else {
            System.out.println("CaptureDevice is not ready");
        }
    }

    public void stopCapture() {
        System.out.println("stopCapture called");
        running = false;
    }

    public void setFramerate(int fps) {
        framerate = fps;
    }

    public void configureVideo(int devIndex, int formIndex, int fps) {
        deviceIndex = devIndex;
        formatIndex = formIndex;
        framerate = fps;
    }

    public List<String> showCaptureDevices() {
        List<String> captureDeviceList = new LinkedList();
//        VideoFormat vidformat = new VideoFormat(VideoFormat.YUV);
        for (int i = 0; i < CaptureDeviceManager.getDeviceList(vidformat).size(); i++) {
            captureDeviceList.add(((CaptureDeviceInfo) CaptureDeviceManager.getDeviceList(vidformat).get(i)).getName());
//            System.out.println("  Device[" + i + "]: " + captureDeviceList.get(i));
        }
        return captureDeviceList;
    }

    public List<CaptureDeviceInfo> showCaptureDevicesInfo() {
//        VideoFormat vidformat = new VideoFormat(VideoFormat.YUV);
        for (int i = 0; i < CaptureDeviceManager.getDeviceList(vidformat).size(); i++) {
            CaptureDeviceInfo deviceInfo = (CaptureDeviceInfo) CaptureDeviceManager.getDeviceList(vidformat).get(i);
//            System.out.println("  Device[" + i + "]: " + deviceInfo.getName());
        }
        return CaptureDeviceManager.getDeviceList(vidformat);
    }

    public List<String> showCaptureFormats(int deviceIndex) {
        List<String> captureFormatList = new LinkedList();
        CaptureDeviceInfo deviceInfo = showCaptureDevicesInfo().get(deviceIndex);
        Format[] formats = deviceInfo.getFormats();
        for (int i = 0; i < deviceInfo.getFormats().length; i++) {
//            System.out.println("supported Format: " + (deviceInfo.getFormats())[i].toString());
            captureFormatList.add(formats[i].toString());
        }
        return captureFormatList;
    }

    public void initVideo() {
        System.out.println("initVideo called");
        try {
            // VideoFormat vidformat = new VideoFormat(VideoFormat.YUV);
            List<CaptureDeviceInfo> deviceList = CaptureDeviceManager.getDeviceList(vidformat);
            CaptureDeviceInfo deviceInfo = deviceList.get(deviceIndex);
            player = Manager.createRealizedPlayer(deviceInfo.getLocator());
            formatControl = (FormatControl) player.getControl(
                    "javax.media.control.FormatControl");
            // video format setzen ...liste nicht aktuell!
            // 0 = 320 x 240 @ ?
            // 1 = 160 x 120
            // 3 = 352 x 288
            // 4 = 640 x 480 @ ? interlaced ?
            // 7 = 320 x 240 @ 25fps
            // 8 = 352 x 288
            // 9 = 640 x 480 @ ? interlaced ?
            formatControl.setFormat(deviceInfo.getFormats()[formatIndex]);

            ready = true;
        } catch (Exception e) {
            System.out.println("Error during video initialization");
            ready = false;
        }
    }

    public VideoInput(core in) {
        callback = in;

    }

    @Override
    public void run() {
        try {

            this.initVideo();
            this.startCapture();
        } catch (Exception e) {
            System.out.println("Error while trying to capture");
        }
    }

    public void startGrabbing() throws Exception {

        player.start();
//        System.out.println("player started");

        FrameGrabbingControl frameGrabber = (FrameGrabbingControl) player.getControl("javax.media.control.FrameGrabbingControl");

        Buffer buf = null;
        Image img = null;
        BufferedImage buffImg;
        Graphics2D g;
        String grabbed;

        // start capture device and wait until it can start
        System.out.println("wait until CaptureDevice is initialized  ");
        while (img == null) {
            System.out.print(".");
            Thread.sleep(1000);
            // Grab a frame from the capture device
            buf = frameGrabber.grabFrame();
            // Convert frame to an buffered image so it can be processed and saved
            img = (new BufferToImage((VideoFormat) buf.getFormat()).createImage(buf));
        }
        System.out.println();
        System.out.println("CaptureDevice is ready, now capturing..");

        while (running == true) {
            buf = frameGrabber.grabFrame();
            img = (new BufferToImage((VideoFormat) buf.getFormat()).createImage(buf));
            buffImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // add date to image
            g = buffImg.createGraphics();
            g.drawImage(img, null, null);
            // Overlay curent time on image
            g.setColor(Color.RED);
            g.setFont(new Font("Verdana", Font.BOLD, 16));
            g.drawString((new Date()).toString(), 10, 25);
            // show Image on GUI (via core callback)
            callback.window.showNewImage(buffImg);
            // wait for next Image
            Thread.sleep(1000 / callback.configuration.framerate);
            // prevent memory leaks
            //            System.gc();
        }
        player.close();
        player.deallocate();
    }
}
