package adr.server.listen.startup;


import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.Window;
import java.net.MalformedURLException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.mbeans.MBeanUtils;
import org.apache.tomcat.util.modeler.Registry;


/**
 * Implementation of <code>LifecycleListener</code> that instantiates the
 * set of MBeans associated with global JNDI resources that are subject to
 * management.
 *
 * @author Craig R. McClanahan
 * @since 4.1
 */
public class NotifyServerLifecycleListener
    implements LifecycleListener {

    // ----------------------------------------------------- Instance Variables


    /**
     * The owning Catalina component that we are attached to.
     */
    protected Lifecycle component = null;


    /**
     * The configuration information registry for our managed beans.
     */
    protected static Registry registry = MBeanUtils.createRegistry();


    // ---------------------------------------------- LifecycleListener Methods


    /**
     * Primary entry point for startup and shutdown events.
     *
     * @param event The event that has occurred
     */
    @Override
    public void lifecycleEvent(LifecycleEvent event) {

        if (Lifecycle.AFTER_START_EVENT.equals(event.getType())) {            
            if (SystemTray.isSupported()) {            	
                try {
					displayTray();					
				} catch (MalformedURLException e) {					
				} catch (AWTException e) {					
				}
            } 
            showMessageDialog();
        }

    }
    
    public void showMessageDialog() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Window w = new TranslucentWindow();
                w.setVisible(true);
            }
        });
    }
    
    private void displayTray() throws AWTException, java.net.MalformedURLException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getToolkit().createImage(getClass().getResource("icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Tray Tomcat");
        //Let the system resizes the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon Tomcat");
        tray.add(trayIcon);
        trayIcon.displayMessage("Notification", "Tomcat server already startup successfully", MessageType.INFO);
    }
    
    

}
