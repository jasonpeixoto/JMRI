package jmri.jmrix.marklin;

/**
 * Returns list of valid ESU Marklin Connection Types
 *
 * @author Bob Jacobsen Copyright (C) 2010
 * @author Kevin Dickerson Copyright (C) 2010
 *
 */
public class MarklinConnectionTypeList implements jmri.jmrix.ConnectionTypeList {

    public static final String MARKLIN = "Marklin";

    @Override
    public String[] getAvailableProtocolClasses() {
        return new String[]{
            "jmri.jmrix.marklin.networkdriver.ConnectionConfig"
        };
    }

    @Override
    public String[] getManufacturers() {
        return new String[]{MARKLIN};
    }

}
