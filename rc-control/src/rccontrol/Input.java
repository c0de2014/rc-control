/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rccontrol;

import java.util.LinkedList;
import java.util.List;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 *
 * @author David
 */
public class Input extends Thread {

    private static Controller joystick;
    private static Component[] components;

    public List<String> showInputDevices() {
        List<String> inputDevices = new LinkedList();
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (int i = 0; i < controllers.length; i++) {
            inputDevices.add(controllers[i].getName());
        }

        return inputDevices;
    }

    public List<String> showInputDeviceComponents(int deviceIndex) {
        List<String> inputDeviceComponents = new LinkedList();
        Component[] deviceComponents = ControllerEnvironment.getDefaultEnvironment().getControllers()[deviceIndex].getComponents();
        for (int i = 0; i < deviceComponents.length; i++) {
            inputDeviceComponents.add(deviceComponents[i].getName());
        }

        return inputDeviceComponents;
    }

    public void initInputDevice(int deviceIndex) {
        joystick = ControllerEnvironment.getDefaultEnvironment().getControllers()[deviceIndex];
        components = joystick.getComponents();
    }

    public void poll() {
        joystick.poll();
    }

    public float getValueOfComponent(int componentIndex) {
        if (componentIndex > -1){
            return components[componentIndex].getPollData();
        }
        else{
//            System.out.println("input nicht ausgewählt");
            return 0;
        }
    }

}
