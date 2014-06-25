/*
 * SingleAppInstance.java
 *
 * Copyright (c) 2004-2011 Gregory Kotsaftis
 * gregkotsaftis@yahoo.com
 * http://zeus-jscl.sourceforge.net/
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package gr.zeus.ui;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

/**
 * This helper component provides a simple solution to the multiple applications
 * instances problem. Just invoke <code>onInit()</code> at your application's
 * startup and <code>onExit()</code> at your application's shutdown and that's
 * all there is to it. <code>SingleAppInstance</code> creates and locks a file
 * within the startup directory so if anyone is to execute your application
 * again the second <code>onInit()</code> call fails with a message.
 * The <code>onExit()</code> method unlocks and deletes the file.
 * If <code>m_reportPopup</code> is true, a popup window is displayed for the
 * user to take action, else the user is informed via <code>stderr</code>.
 * If you wish to change the look and feel of the popup window invoke
 * <code>SingleAppInstance</code> after setting the look and feel inside your
 * application. If you wish to translate the messages for this component create
 * a new property file for your locale and place it inside
 * <b><code>/gr/zeus/</code></b> where the default
 * <b>"singleappinstance.properties"</b> resides.
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.05
 */
public final class SingleAppInstance {

    /**
     * Singleton.
     */
    private static SingleAppInstance s_instance = null;

    /**
     * Instance file name.
     */
    private static final String INSTANCE_FILENAME = "INSTANCE.RUN";

    /**
     * Used to create the file.
     */
    private File m_file = null;

    /**
     * Used to read and write the file.
     */
    private FileChannel m_channel = null;

    /**
     * Used to lock the temporary file.
     */
    private FileLock m_lock = null;

    /**
     * Check to report errors via a popup if <code>true</code> or
     * via <code>stderr</code> if <code>false</code>.
     */
    private boolean m_reportPopup = true;

    /**
     * File lock error title.
     */
    private static final String m_fileLockErrorTitle = ResourceBundle.getBundle(
        "gr/zeus/res/singleappinstance").getString("fileLockErrorTitle");

    /**
     * File lock error message 1.
     */
    private static final String m_fileLockErrorMsg1 = ResourceBundle.getBundle(
        "gr/zeus/res/singleappinstance").getString("fileLockErrorMsg1");

    /**
     * File lock error message 2.
     */
    private static final String m_fileLockErrorMsg2 = ResourceBundle.getBundle(
        "gr/zeus/res/singleappinstance").getString("fileLockErrorMsg2");

    /**
     * File delete error title.
     */
    private static final String m_fileDeleteErrorTitle =
        ResourceBundle.getBundle(
            "gr/zeus/res/singleappinstance").getString("fileDeleteErrorTitle");

    /**
     * File delete error message 1.
     */
    private static final String m_fileDeleteErrorMsg1 =
        ResourceBundle.getBundle(
            "gr/zeus/res/singleappinstance").getString("fileDeleteErrorMsg1");

    /**
     * File delete error message 2.
     */
    private static final String m_fileDeleteErrorMsg2 =
        ResourceBundle.getBundle(
            "gr/zeus/res/singleappinstance").getString("fileDeleteErrorMsg2");

    /**
     * File exists title.
     */
    private static final String m_fileExistsTitle = ResourceBundle.getBundle(
        "gr/zeus/res/singleappinstance").getString("fileExistsTitle");

    /**
     * File exists error message 1.
     */
    private static final String m_fileExistsMsg1 = ResourceBundle.getBundle(
        "gr/zeus/res/singleappinstance").getString("fileExistsMsg1");

    /**
     * File exists error message 2.
     */
    private static final String m_fileExistsMsg2 = ResourceBundle.getBundle(
        "gr/zeus/res/singleappinstance").getString("fileExistsMsg2");

    /**
     * File exists error message 3.
     */
    private static final String m_fileExistsMsg3 = ResourceBundle.getBundle(
        "gr/zeus/res/singleappinstance").getString("fileExistsMsg3");


    /**
     * This method returns the single instance of this class.
     * <p>
     * @return  The single instance of this class.
     */
    public synchronized static SingleAppInstance getInstance()
    {
        // lazy instantiation
        if( s_instance==null )
        {
            s_instance = new SingleAppInstance();
        }

        return( s_instance );
    }


    /**
     * This method returns the single instance of this class.
     * Uses the filename parameter instead of default filename.
     * <p>
     * @param filename  The filename to create and lock, should be unique and
     *                  within current folder.
     * <p>
     * @return          The single instance of this class.
     */
    public synchronized static SingleAppInstance getInstance(String filename)
    {
        // lazy instantiation
        if( s_instance==null )
        {
            s_instance = new SingleAppInstance( filename );
        }

        return( s_instance );
    }


    /**
     * Creates a file and obtains a lock. If the file exists or
     * there is any error creating it this method invokes
     * <code>System.exit()</code>. Should be invoked <b>ONCE</b>
     * inside <code>main()</code> in your application.
     */
    public synchronized void onInit()
    {
        // if file already exists...
        if( m_file.exists() )
        {
            // notify user and ask for instructions...
            if( inform_user()==false )
            {
                System.exit( 1 );
            }

            // ok, he is sure. try to delete the old file.
            if( force_delete_file()==false )
            {
                System.exit( 2 );
            }
        }

        // try to create and lock the file.
        if( lock_file()==false )
        {
            System.exit( 3 );
        }
    }


    /**
     * Release the file lock and delete the file.
     * Should be invoked <b>ONCE</b> just before calling
     * <code>System.exit()</code> on your application.
     */
    public synchronized void onExit()
    {
        try
        {
            // Release the lock, close and delete the file
            m_lock.release();
            m_channel.close();
            m_file.delete();
        }
        catch(Exception e)
        {
            // not much to do here, since the application is possibly exiting...
            e.printStackTrace();
        }
    }


    /**
     * Gets whether to report via a popup or via <code>stderr</code>.
     * <p>
     * @return  If <code>true</code>, a popup window is displayed,
     *          else the user is informed via <code>stderr</code>.
     */
    public boolean getReportPopup()
    {
        return( m_reportPopup );
    }


    /**
     * Sets whether to report via a popup or via <code>stderr</code>.
     * <p>
     * @param f     If <code>true</code>, via a popup window,
     *              else via <code>stderr</code>.
     */
    public synchronized void setReportPopup(boolean f)
    {
        m_reportPopup = f;
    }


    /**
     * Private constructor.
     */
    private SingleAppInstance()
    {
        m_file = new File( INSTANCE_FILENAME );
    }


    /**
     * Private constructor.
     * <p>
     * @param filename  The filename to create and lock, should be unique and
     *                  within current folder.
     */
    private SingleAppInstance(String filename)
    {
        m_file = new File( filename );
    }


    /**
     * Reports a lock error.
     * <p>
     * @param s     The error message.
     */
    private void report_lock_error(String s)
    {
        String msg = m_fileLockErrorMsg1 + m_file.getAbsolutePath() +
                     m_fileLockErrorMsg2 + s;
        String title = m_fileLockErrorTitle;

        if( m_reportPopup )
        {
            JOptionPane.showMessageDialog(null, msg, title,
                JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            System.err.println( msg );
        }
    }


    /**
     * Reports a delete error.
     * <p>
     * @param s     The error message.
     */
    private void report_delete_error(String s)
    {
        String msg = m_fileDeleteErrorMsg1 + m_file.getAbsolutePath();
        if( s!=null )
        {
            msg += m_fileDeleteErrorMsg2 + s;
        }

        String title = m_fileDeleteErrorTitle;

        if( m_reportPopup )
        {
            JOptionPane.showMessageDialog(null, msg, title,
                JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            System.err.println( msg );
        }
    }


    /**
     * Creates and locks the file.
     * <p>
     * @return  <code>true</code> if file is locked,
     *          <code>false</code> otherwise.
     */
    private boolean lock_file()
    {
        try
        {
            // create the file and obtain a lock to it.
            // this way nobody can delete this file as long as this
            // application is running!
            m_channel = new RandomAccessFile(m_file, "rw").getChannel();
            m_lock = m_channel.lock();

            return( true );
        }
        catch(Exception ex)
        {
            report_lock_error( ex.getMessage() );

            return( false );
        }
    }


    /**
     * Force deletion of the file.
     * <p>
     * @return  <code>true</code> if file is deleted,
     *          <code>false</code> otherwise.
     */
    private boolean force_delete_file()
    {
        try
        {
            boolean deleted = m_file.delete();

            if( !deleted )
            {
                report_delete_error( null );
            }

            return( deleted );
        }
        catch(Exception ex)
        {
            report_delete_error( ex.getMessage() );

            return( false );
        }
    }


    /**
     * Informs the user that the file exists.
     * <p>
     * @return  <code>true</code> if user is informed,
     *          <code>false</code> otherwise.
     */
    private boolean inform_user()
    {
        String msg_gui = m_fileExistsMsg1 + m_fileExistsMsg2;
        String msg_txt = m_fileExistsMsg1 + m_fileExistsMsg3 +
                         m_file.getAbsolutePath();
        String title = m_fileExistsTitle;

        if( m_reportPopup )
        {
            int res = JOptionPane.showConfirmDialog(null, msg_gui, title,
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE);
            return( res==0 ); // if YES return true
        }
        else
        {
            System.err.println( msg_txt );

            return( false );
        }
    }

}
