// YardmasterFrame.java
package jmri.jmrit.operations.locations;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import jmri.jmrit.operations.OperationsFrame;
import jmri.jmrit.operations.setup.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Yardmaster Frame. Shows work at one location.
 *
 * @author Dan Boudreau Copyright (C) 2013
 * @version $Revision: 18630 $
 */
public class YardmasterFrame extends OperationsFrame {

    public YardmasterFrame() {
        super(Bundle.getMessage("Yardmaster"), new YardmasterPanel());
    }

    public void initComponents(Location location) {
        super.initComponents();
        ((YardmasterPanel) this.getContentPane()).initComponents(location);

        if (location != null) {

            // build menu
            JMenuBar menuBar = new JMenuBar();
            JMenu toolMenu = new JMenu(Bundle.getMessage("Tools"));
            JMenuItem print = toolMenu.add(new PrintSwitchListAction(Bundle.getMessage("MenuItemPrint"), location,
                    false));
            JMenuItem preview = toolMenu.add(new PrintSwitchListAction(Bundle.getMessage("MenuItemPreview"), location,
                    true));
            menuBar.add(toolMenu);
            setJMenuBar(menuBar);

            // add tool tip if in consolidation mode: "Disabled when switch list is in consolidation mode"
            if (!Setup.isSwitchListRealTime()) {
                print.setToolTipText(Bundle.getMessage("TipDisabled"));
                preview.setToolTipText(Bundle.getMessage("TipDisabled"));
            }
            setTitle(Bundle.getMessage("Yardmaster") + " (" + location.getName() + ")");
        }

        addHelpMenu("package.jmri.jmrit.operations.Operations_Locations", true); // NOI18N

        pack();
        setVisible(true);
    }

    private static final Logger log = LoggerFactory.getLogger(YardmasterFrame.class.getName());
}
