package jmri.util;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Utilities for handling JMRI's threading conventions.
 * <p>
 * For background, see
 * <a href="http://jmri.org/help/en/html/doc/Technical/Threads.shtml">http://jmri.org/help/en/html/doc/Technical/Threads.shtml</a>
 * <p>
 * Note this distinguishes "on layout", for example, Setting a sensor, from "on
 * GUI", for example, manipulating the Swing GUI. That may not be an important
 * distinction now, but it might be later, so we build it into the calls.
 *
 * @author Bob Jacobsen Copyright 2015
 */
public class ThreadingUtil {

    static public interface ThreadAction extends Runnable {

        /**
         * Must handle its own exceptions
         */
        @Override
        public void run();
    }

    /**
     * Run some layout-specific code before returning
     * <p>
     * Typical uses:
     * <p>
     * {@code ThreadingUtil.runOnLayout( ()->{ sensor.setState(value); } );}
     *
     * @param ta What to run, usually as a lambda expression
     */
    static public void runOnLayout(ThreadAction ta) {
        runOnGUI(ta);
    }

    /**
     * Run some layout-specific code at some later point.
     * <p>
     * Please note the operation may have happened before this returns. Or
     * later. No long-term guarantees.
     * <p>
     * Typical uses:
     * <p>
     * {@code ThreadingUtil.runOnLayoutEventually( ()->{ sensor.setState(value); } );}
     *
     * @param ta What to run, usually as a lambda expression
     */
    static public void runOnLayoutEventually(ThreadAction ta) {
        runOnGUIEventually(ta);
    }

    /**
     * Run some layout-specific code at some later point, at least a 
     * known time in the future.
     * <p>
     * There is no long-term guarantee about the accuracy of the interval.
     * <p>
     * Typical uses:
     * <p>
     * {@code ThreadingUtil.runOnLayoutEventually( ()->{ sensor.setState(value); }, 1000 );}
     *
     * @param ta What to run, usually as a lambda expression
     * @param delay interval in milliseconds
     */
    static public void runOnLayoutDelayed(ThreadAction ta, int delay) {
        runOnGUIDelayed(ta, delay);
    }

    /**
     * Check if on the layout-operation thread.
     *
     * @return true if on the layout-operation thread
     */
    static public boolean isLayoutThread() {
        return isGUIThread();
    }

    /**
     * Run some GUI-specific code before returning
     * <p>
     * Typical uses:
     * <p>
     * {@code ThreadingUtil.runOnGUI( ()->{ mine.setVisible(); } );}
     *
     * @param ta What to run, usually as a lambda expression
     */
    static public void runOnGUI(ThreadAction ta) {
        if (isGUIThread()) {
            // run now
            ta.run();
        } else {
            // dispatch to Swing
            try {
                SwingUtilities.invokeAndWait(ta);
            } catch (InterruptedException e) {
                log.warn("While on GUI thread", e);
                // we just continue from InterruptedException for now
            } catch (InvocationTargetException e) {
                log.error("Error while on GUI thread", e.getCause());
                // should have been handled inside the ThreadAction
            }
        }
    }

    /**
     * Run some GUI-specific code at some later point.
     * <p>
     * If invoked from the GUI thread, the work is guaranteed to happen only
     * after the current routine has returned.
     * <p>
     * Typical uses:
     * <p>
     * {@code ThreadingUtil.runOnGUIEventually( ()->{ mine.setVisible(); } );}
     *
     * @param ta What to run, usually as a lambda expression
     */
    static public void runOnGUIEventually(ThreadAction ta) {
        // dispatch to Swing
        SwingUtilities.invokeLater(ta);
    }

    /**
     * Run some GUI-specific code at some later point, at least a 
     * known time in the future.
     * <p>
     * There is no long-term guarantee about the accuracy of the interval.
     * <p>
     * Typical uses:
     * <p>
     * {@code ThreadingUtil.runOnGUIEventually( ()->{ mine.setVisible(); }, 1000 );}
     *
     * @param ta What to run, usually as a lambda expression
     * @param delay interval in milliseconds
     */
    static public void runOnGUIDelayed(ThreadAction ta, int delay) {
        // dispatch to Swing via timer
        Timer timer = new Timer(delay, new java.awt.event.ActionListener(){
                                            public void actionPerformed(java.awt.event.ActionEvent e) {
                                                                        ta.run();
                                                                    }
                                        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Check if on the GUI event dispatch thread.
     *
     * @return true if on the event dispatch thread
     */
    static public boolean isGUIThread() {
        return SwingUtilities.isEventDispatchThread();
    }

    private final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ThreadingUtil.class.getName());
}
