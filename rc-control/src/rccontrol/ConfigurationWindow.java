/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConfigurationWindow.java
 *
 * Created on 15.11.2010, 12:26:58
 */
package rccontrol;

import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author David
 */
public class ConfigurationWindow extends javax.swing.JFrame {

    static Configuration configuration;

    private void setCaptureDeviceList() {
        this.jComboBoxCaptureDevice.removeAllItems();
        // nicht schön! besser kapseln..
        List<String> captureDeviceList = configuration.callback.videoInput.showCaptureDevices();

        for (int i = 0; i < captureDeviceList.size(); i++) {
            this.jComboBoxCaptureDevice.insertItemAt(captureDeviceList.get(i), i);
        }
        jComboBoxCaptureDevice.setSelectedIndex(0);
        setCaptureFormatList();

    }

    private void setCaptureFormatList() {
        this.jComboBoxCaptureFormat.removeAllItems();
        // nicht schön! besser kapseln..
        List<String> captureFormatList = configuration.callback.videoInput.showCaptureFormats(0);
        //jComboBoxCaptureDevice.getSelectedIndex());

//      System.out.println("format count: " +captureFormatList.size());
        for (int i = 0; i < captureFormatList.size(); i++) {
//            System.out.println("format added: "+captureFormatList.get(i) );

// hoehe mal breite aus string extrahieren..
            this.jComboBoxCaptureFormat.insertItemAt(captureFormatList.get(i).substring(44, 44 + 20), i);
        }
        if (captureFormatList.size() > 4) {
            // for testing set to index 3
            // default should be 0
            jComboBoxCaptureFormat.setSelectedIndex(4);
        } else if (captureFormatList.size() > 0) {
            jComboBoxCaptureFormat.setSelectedIndex(0);
        }

    }

    private void setFramerateList() {
        String[] fpsList = {"1", "2", "5", "10", "15", "20", "24", "25", "29", "30", "50", "60", "75", "100", "120", "150", "200"};
        this.jComboBoxCaptureFramerate.removeAllItems();
        for (int i = 0; i < fpsList.length; i++) {
            this.jComboBoxCaptureFramerate.insertItemAt(fpsList[i], i);
        }
        this.jComboBoxCaptureFramerate.setSelectedIndex(7);
    }

    private void setCommPortList() {
// System.out.println("setCommPortList called");
        List<String> ports = configuration.callback.output.showPorts();
        this.jComboBoxComPort.removeAllItems();
        for (int i = 0; i < ports.size(); i++) {
            this.jComboBoxComPort.insertItemAt(ports.get(i), i);
        }
        if (ports.size() > 1) {
            this.jComboBoxComPort.setSelectedIndex(1);
        } else if (ports.size() > 0) {
            this.jComboBoxComPort.setSelectedIndex(0);
        }

    }

    private void setCommPortSpeeds() {
        String[] speedList = {"1200", "2400", "9600", "19200", "38400", "57600", "115200", "230400", "460800", "921600", "1382400"};
        this.jComboBoxComPortSpeed.removeAllItems();
        for (int i = 0; i < speedList.length; i++) {
            this.jComboBoxComPortSpeed.insertItemAt(speedList[i], i);
        }
        this.jComboBoxComPortSpeed.setSelectedIndex(6);
    }

    private void setInputDeviceList() {
        this.jComboBoxInputDevice.removeAllItems();
        List<String> inputDevices = configuration.callback.input.showInputDevices();
        for (int i = 0; i < inputDevices.size(); i++) {
            this.jComboBoxInputDevice.insertItemAt(inputDevices.get(i), i);
        }
        if (inputDevices.size() > 2) {
            this.jComboBoxInputDevice.setSelectedIndex(2);
        } else {
            this.jComboBoxInputDevice.setSelectedIndex(0);
        }

    }

    private void setChannelLists() {
        List<String> deviceComponents = configuration.callback.input.showInputDeviceComponents(this.jComboBoxInputDevice.getSelectedIndex());
        this.jComboBoxInputChannel1.removeAllItems();
        this.jComboBoxInput2Channel1.removeAllItems();
        this.jComboBoxInputChannel2.removeAllItems();
        this.jComboBoxInput2Channel2.removeAllItems();
        this.jComboBoxInputChannel3.removeAllItems();
        this.jComboBoxInputChannel4.removeAllItems();
        for (int i = 0; i < deviceComponents.size(); i++) {
            this.jComboBoxInputChannel1.insertItemAt(deviceComponents.get(i), i);
            this.jComboBoxInput2Channel1.insertItemAt(deviceComponents.get(i), i);
            this.jComboBoxInputChannel2.insertItemAt(deviceComponents.get(i), i);
            this.jComboBoxInput2Channel2.insertItemAt(deviceComponents.get(i), i);
            this.jComboBoxInputChannel3.insertItemAt(deviceComponents.get(i), i);
            this.jComboBoxInputChannel4.insertItemAt(deviceComponents.get(i), i);
        }
//        System.out.println("|"+(String)this.jComboBoxInputDevice.getSelectedItem() +"|");


// nicht schön! viel zu detailiert und alles muss neu eingestellt werden.. bis rein nach configuration..
// ..nur zum testen / prototypen drin lassen
        if (((String) this.jComboBoxInputDevice.getSelectedItem()).equals((String) "Cyborg Evo Force")) {
            this.jComboBoxInputChannel1.setSelectedIndex(2);
            this.jCheckBoxchannel1secondinput.setSelected(false);
            configuration.useSecondInput[0] = false;
            this.jCheckBoxchannel2secondinput.setSelected(false);
            configuration.useSecondInput[1] = false;
            this.jTextFieldOffset1.setValue(0);
            this.jSliderOffset2.setValue(0);
            this.jComboBoxInput2Channel1.setSelectedIndex(-1);
            this.jComboBoxInputChannel2.setSelectedIndex(1);
            this.jComboBoxInputChannel3.setSelectedIndex(16);
            this.jComboBoxInputChannel4.setSelectedIndex(0);
        }
        else if (((String) this.jComboBoxInputDevice.getSelectedItem()).equals((String) "Controller (Xbox 360 Wireless Receiver for Windows)")) {
            this.jComboBoxInputChannel1.setSelectedIndex(0);
            configuration.sensitivity[0] = (float) 2.0;
            configuration.channelOffset[0] = -127;
            this.jTextFieldOffset1.setValue(configuration.channelOffset[0]);
            this.jTextField5.setText("" + this.jTextFieldOffset1.getValue());
            this.jCheckBoxchannel1secondinput.setSelected(false);
            configuration.useSecondInput[0] = this.jCheckBoxchannel1secondinput.isSelected();
            this.jCheckBoxchannel2secondinput.setSelected(false);
            configuration.useSecondInput[1] = this.jCheckBoxchannel2secondinput.isSelected();
            
            this.jSliderOffset2.setValue(0);
            this.jComboBoxInput2Channel1.setSelectedIndex(-1);
            this.jComboBoxInputChannel2.setSelectedIndex(2);
            this.jComboBoxInputChannel3.setSelectedIndex(1);
            this.jComboBoxInputChannel4.setSelectedIndex(3);

        }
        else if (((String) this.jComboBoxInputDevice.getSelectedItem()).equals((String) "FGT Rumble 3-in-1")) {
            this.jComboBoxInputChannel1.setSelectedIndex(0);
            this.jCheckBoxchannel1secondinput.setSelected(false);
            configuration.useSecondInput[0] = false;
            this.jTextFieldOffset1.setValue(0);

            configuration.sensitivity[1] = (float) 0.3;
            this.jSliderOffset2.setValue(-38);
            configuration.channelOffset[1] = -38;

            this.jComboBoxInputChannel2.setSelectedIndex(14);
            this.jTextFieldOffset2.setText("" + this.jSliderOffset2.getValue());
            this.jCheckBoxchannel2secondinput.setSelected(true);
            this.jLabel18.setEnabled(true);
            
            this.jCheckBox2.setSelected(false);
            configuration.reverse[1] = false;

            this.jComboBoxInput2Channel2.setEnabled(true);
            configuration.useSecondInput[1] = true;
            configuration.sensitivity2[1] = (float) 0.3;
            this.jCheckBox8.setEnabled(true);
            this.jCheckBox8.setSelected(false);
            configuration.reverse2[1] = false;
            this.jComboBoxInput2Channel2.setSelectedIndex(15);

            this.jCheckBox5.setSelected(true);
            configuration.reverse2[2] = true;

            this.jComboBoxInputChannel3.setSelectedIndex(-1);
            this.jComboBoxInputChannel4.setSelectedIndex(16);
        }
    }

    /** Creates new form ConfigurationWindow */
    public ConfigurationWindow(Configuration conf_in) {
        configuration = conf_in;
        initComponents();
// ConfigurationWindow wird doppelt erzeugt -> doppelter Aufruf showCommPortList  etc..

        this.setCaptureDeviceList();
//        this.setCaptureFormatList();
        this.setFramerateList();
        this.setCommPortList();
        this.setCommPortSpeeds();
        this.setInputDeviceList();
        this.setChannelLists();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxComPort = new javax.swing.JComboBox();
        jComboBoxComPortSpeed = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jCheckBoxchannel1secondinput = new javax.swing.JCheckBox();
        jPanel11 = new javax.swing.JPanel();
        jComboBoxInput2Channel1 = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jCheckBox6 = new javax.swing.JCheckBox();
        jComboBoxInputChannel1 = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jTextFieldOffset1 = new javax.swing.JSlider();
        jLabel19 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jComboBoxInputChannel2 = new javax.swing.JComboBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBoxchannel2secondinput = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        jComboBoxInput2Channel2 = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jCheckBox8 = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        jSliderOffset2 = new javax.swing.JSlider();
        jTextFieldOffset2 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jComboBoxInputChannel3 = new javax.swing.JComboBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jPanel10 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jComboBoxInputChannel4 = new javax.swing.JComboBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jComboBoxInputDevice = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxCaptureDevice = new javax.swing.JComboBox();
        jComboBoxCaptureFormat = new javax.swing.JComboBox();
        jComboBoxCaptureFramerate = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jCheckBoxConfirmation = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuration");
        setMinimumSize(new java.awt.Dimension(784, 747));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Output"));

        jLabel2.setText("Port");

        jLabel3.setText("Speed");

        jComboBoxComPort.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxComPortSpeed.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBoxComPort, 0, 386, Short.MAX_VALUE)
                    .addComponent(jComboBoxComPortSpeed, 0, 386, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxComPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxComPortSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 0, 460, 90));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Input"));
        jPanel6.setPreferredSize(new java.awt.Dimension(300, 520));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Channel 1"));

        jTextField1.setEditable(false);
        jTextField1.setText("Throttle");

        jCheckBoxchannel1secondinput.setText("use second input");
        jCheckBoxchannel1secondinput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxchannel1secondinputActionPerformed(evt);
            }
        });

        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBoxInput2Channel1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxInput2Channel1.setEnabled(false);

        jLabel14.setText("Input:");
        jLabel14.setEnabled(false);

        jCheckBox6.setText("invert");
        jCheckBox6.setEnabled(false);
        jCheckBox6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox6StateChanged(evt);
            }
        });
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxInput2Channel1, 0, 557, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox6)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jCheckBox6)
                    .addComponent(jComboBoxInput2Channel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jComboBoxInputChannel1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxInputChannel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxInputChannel1ActionPerformed(evt);
            }
        });

        jLabel13.setText("Input:");

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("invert");
        jCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jTextFieldOffset1.setMaximum(255);
        jTextFieldOffset1.setMinimum(-255);
        jTextFieldOffset1.setValue(0);
        jTextFieldOffset1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTextFieldOffset1StateChanged(evt);
            }
        });

        jLabel19.setText("Offset:");

        jTextField5.setText("0");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1))
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBoxchannel1secondinput)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jTextFieldOffset1, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                            .addComponent(jComboBoxInputChannel1, 0, 615, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxInputChannel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jTextFieldOffset1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBoxchannel1secondinput))
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Channel 2"));
        jPanel8.setEnabled(false);

        jTextField2.setEditable(false);
        jTextField2.setText("Nick");

        jLabel15.setText("Input:");

        jComboBoxInputChannel2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCheckBox2.setSelected(true);
        jCheckBox2.setText("invert");
        jCheckBox2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox2StateChanged(evt);
            }
        });

        jCheckBoxchannel2secondinput.setText("use second input");
        jCheckBoxchannel2secondinput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxchannel2secondinputActionPerformed(evt);
            }
        });

        jPanel12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBoxInput2Channel2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxInput2Channel2.setEnabled(false);

        jLabel18.setText("Input:");
        jLabel18.setEnabled(false);

        jCheckBox8.setText("invert");
        jCheckBox8.setEnabled(false);
        jCheckBox8.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox8StateChanged(evt);
            }
        });
        jCheckBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxInput2Channel2, 0, 557, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox8)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jCheckBox8)
                    .addComponent(jComboBoxInput2Channel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel20.setText("Offset:");

        jSliderOffset2.setMaximum(255);
        jSliderOffset2.setMinimum(-255);
        jSliderOffset2.setValue(0);
        jSliderOffset2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderOffset2StateChanged(evt);
            }
        });

        jTextFieldOffset2.setText("0");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxInputChannel2, 0, 620, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBoxchannel2secondinput))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSliderOffset2, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldOffset2, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxInputChannel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldOffset2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jSliderOffset2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxchannel2secondinput)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Channel 3"));

        jTextField3.setEditable(false);
        jTextField3.setText("Rudder");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel16.setText("Input:");

        jComboBoxInputChannel3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCheckBox3.setSelected(true);
        jCheckBox3.setText("invert");
        jCheckBox3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox3StateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxInputChannel3, 0, 620, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox3)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxInputChannel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Channel 4"));

        jTextField4.setEditable(false);
        jTextField4.setText("Roll");

        jLabel17.setText("Input:");

        jComboBoxInputChannel4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCheckBox5.setText("invert");
        jCheckBox5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox5StateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxInputChannel4, 0, 620, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox5)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxInputChannel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Device"));

        jComboBoxInputDevice.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxInputDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxInputDeviceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jComboBoxInputDevice, 0, 688, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jComboBoxInputDevice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 750));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Video"));

        jLabel4.setText("Capture Device");

        jLabel5.setText("Capture Format");

        jComboBoxCaptureDevice.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxCaptureDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCaptureDeviceActionPerformed(evt);
            }
        });
        jComboBoxCaptureDevice.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBoxCaptureDevicePropertyChange(evt);
            }
        });

        jComboBoxCaptureFormat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxCaptureFramerate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxCaptureFramerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCaptureFramerateActionPerformed(evt);
            }
        });

        jLabel6.setText("Framerate");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxCaptureFramerate, javax.swing.GroupLayout.Alignment.TRAILING, 0, 350, Short.MAX_VALUE)
                    .addComponent(jComboBoxCaptureFormat, 0, 350, Short.MAX_VALUE)
                    .addComponent(jComboBoxCaptureDevice, 0, 350, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxCaptureDevice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxCaptureFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCaptureFramerate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 90, 460, 130));

        jButton1.setText("save to File");
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 230, 140, 40));

        jButton2.setText("load from File");
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 230, 140, 40));

        jButton3.setText("Start");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 700, 290, 40));

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Haftungsausschluss\n- Benutzung auf eigene Gefahr\n- keine Garantie für gar nichts!\n\nErklärungen\n- Inputdevice\n- Throttle & Brake\n- Output\n- Video\n\nSicherheitshinweise\n- Throttle to 0 !\n- Transmitter within reach,\n  switch from digipot to sticks\n  in case anything happens\n");
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 280, 460, 390));

        jCheckBoxConfirmation.setText("gelesen und Haftungsausschluss akzeptiert");
        jCheckBoxConfirmation.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBoxConfirmationStateChanged(evt);
            }
        });
        getContentPane().add(jCheckBoxConfirmation, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 670, 290, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBoxchannel1secondinputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxchannel1secondinputActionPerformed
        // TODO add your handling code here:
        configuration.useSecondInput[0] = this.jCheckBoxchannel1secondinput.isSelected();
//        this.jLabel12.setEnabled(this.jCheckBox4.isSelected());
        this.jLabel14.setEnabled(this.jCheckBoxchannel1secondinput.isSelected());
//        this.jSlider5.setEnabled(this.jCheckBox4.isSelected());
        this.jComboBoxInput2Channel1.setEnabled(this.jCheckBoxchannel1secondinput.isSelected());
//        configuration.channelNeutral[0] = this.jSlider5.getValue();
        this.jCheckBox6.setEnabled(this.jCheckBoxchannel1secondinput.isSelected());

}//GEN-LAST:event_jCheckBoxchannel1secondinputActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (configuration.confirmed == true) {
            try {
                configuration.useVideo = true;
                configuration.captureDeviceIndex = this.jComboBoxCaptureDevice.getSelectedIndex();
                configuration.captureDeviceFormatIndex = this.jComboBoxCaptureFormat.getSelectedIndex();
                configuration.framerate = Integer.parseInt(this.jComboBoxCaptureFramerate.getSelectedItem().toString());
                configuration.comPort = (String) this.jComboBoxComPort.getSelectedItem();
                configuration.serialSpeed = Integer.parseInt(this.jComboBoxComPortSpeed.getSelectedItem().toString());
                configuration.inputDeviceIndex = this.jComboBoxInputDevice.getSelectedIndex();
                configuration.channelMapping[0] = this.jComboBoxInputChannel1.getSelectedIndex();
                configuration.channelMapping[1] = this.jComboBoxInputChannel2.getSelectedIndex();
                configuration.channelMapping[2] = this.jComboBoxInputChannel3.getSelectedIndex();
                configuration.channelMapping[3] = this.jComboBoxInputChannel4.getSelectedIndex();

                configuration.channelMapping2[0] = this.jComboBoxInput2Channel1.getSelectedIndex();
                configuration.channelMapping2[1] = this.jComboBoxInput2Channel2.getSelectedIndex();

                configuration.startCore();
                this.dispose();
            } catch (Exception e) {
                System.out.println("Start fehlgeschlagen");
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jCheckBoxConfirmationStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBoxConfirmationStateChanged
        // TODO add your handling code here:
        configuration.confirmed = this.jCheckBoxConfirmation.isSelected();
        this.jButton3.setEnabled(this.jCheckBoxConfirmation.isSelected());

    }//GEN-LAST:event_jCheckBoxConfirmationStateChanged

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        System.exit(0);

        /*
        if (configuration.confirmed == false){
        System.exit(0);
        }
        else{
        System.exit(0);
        }
         */

    }//GEN-LAST:event_formWindowClosing

    private void jComboBoxCaptureDevicePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBoxCaptureDevicePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCaptureDevicePropertyChange

    private void jComboBoxCaptureDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCaptureDeviceActionPerformed
        // TODO add your handling code here:
        this.setCaptureFormatList();
    }//GEN-LAST:event_jComboBoxCaptureDeviceActionPerformed

    private void jComboBoxInputDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxInputDeviceActionPerformed
        // TODO add your handling code here:

        if (this.jComboBoxInputDevice.getSelectedIndex() > -1) {
            this.setChannelLists();
        }
    }//GEN-LAST:event_jComboBoxInputDeviceActionPerformed

    private void jCheckBox1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox1StateChanged
        // TODO add your handling code here:
        configuration.reverse[0] = jCheckBox1.isSelected();
    }//GEN-LAST:event_jCheckBox1StateChanged

    private void jCheckBox2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox2StateChanged
        // TODO add your handling code here:
        configuration.reverse[1] = jCheckBox2.isSelected();
    }//GEN-LAST:event_jCheckBox2StateChanged

    private void jCheckBox3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox3StateChanged
        // TODO add your handling code here:
        configuration.reverse[2] = jCheckBox3.isSelected();
    }//GEN-LAST:event_jCheckBox3StateChanged

    private void jCheckBox5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox5StateChanged
        // TODO add your handling code here:
        configuration.reverse[3] = jCheckBox5.isSelected();
    }//GEN-LAST:event_jCheckBox5StateChanged

    private void jComboBoxCaptureFramerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCaptureFramerateActionPerformed
        // TODO add your handling code here:
        if (this.jComboBoxCaptureFramerate.getSelectedItem() != null){
            String framerate =  this.jComboBoxCaptureFramerate.getSelectedItem().toString() ;
            configuration.framerate = Integer.parseInt(framerate);
        }
    }//GEN-LAST:event_jComboBoxCaptureFramerateActionPerformed

    private void jCheckBox6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox6StateChanged
        // TODO add your handling code here:
        configuration.reverse2[0] = jCheckBox6.isSelected();
    }//GEN-LAST:event_jCheckBox6StateChanged

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox6ActionPerformed

    private void jComboBoxInputChannel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxInputChannel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxInputChannel1ActionPerformed

    private void jCheckBoxchannel2secondinputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxchannel2secondinputActionPerformed
        // TODO add your handling code here:
        configuration.useSecondInput[1] = this.jCheckBoxchannel2secondinput.isSelected();
//        this.jLabel12.setEnabled(this.jCheckBox4.isSelected());
        this.jLabel18.setEnabled(this.jCheckBoxchannel2secondinput.isSelected());
//        this.jSlider5.setEnabled(this.jCheckBox7.isSelected());
        this.jComboBoxInput2Channel2.setEnabled(this.jCheckBoxchannel2secondinput.isSelected());
//        configuration.channelNeutral[0] = this.jSlider5.getValue();
        this.jCheckBox8.setEnabled(this.jCheckBoxchannel2secondinput.isSelected());
    }//GEN-LAST:event_jCheckBoxchannel2secondinputActionPerformed

    private void jCheckBox8StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox8StateChanged
        // TODO add your handling code here:
        configuration.reverse2[1] = jCheckBox8.isSelected();
    }//GEN-LAST:event_jCheckBox8StateChanged

    private void jCheckBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox8ActionPerformed

    private void jTextFieldOffset1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTextFieldOffset1StateChanged
        // TODO add your handling code here:
        configuration.channelOffset[0] = jTextFieldOffset1.getValue();
        this.jTextField5.setText("" + jTextFieldOffset1.getValue());

    }//GEN-LAST:event_jTextFieldOffset1StateChanged

    private void jSliderOffset2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderOffset2StateChanged
        // TODO add your handling code here:
        configuration.channelOffset[1] = jSliderOffset2.getValue();
        this.jTextFieldOffset2.setText("" + jSliderOffset2.getValue());

    }//GEN-LAST:event_jSliderOffset2StateChanged



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ConfigurationWindow(configuration).setVisible(true);

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBoxConfirmation;
    private javax.swing.JCheckBox jCheckBoxchannel1secondinput;
    private javax.swing.JCheckBox jCheckBoxchannel2secondinput;
    private javax.swing.JComboBox jComboBoxCaptureDevice;
    private javax.swing.JComboBox jComboBoxCaptureFormat;
    private javax.swing.JComboBox jComboBoxCaptureFramerate;
    private javax.swing.JComboBox jComboBoxComPort;
    private javax.swing.JComboBox jComboBoxComPortSpeed;
    private javax.swing.JComboBox jComboBoxInput2Channel1;
    private javax.swing.JComboBox jComboBoxInput2Channel2;
    private javax.swing.JComboBox jComboBoxInputChannel1;
    private javax.swing.JComboBox jComboBoxInputChannel2;
    private javax.swing.JComboBox jComboBoxInputChannel3;
    private javax.swing.JComboBox jComboBoxInputChannel4;
    private javax.swing.JComboBox jComboBoxInputDevice;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSliderOffset2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JSlider jTextFieldOffset1;
    private javax.swing.JTextField jTextFieldOffset2;
    // End of variables declaration//GEN-END:variables
}
