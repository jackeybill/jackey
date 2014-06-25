/*
 * JConsolePane.java
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

import gr.zeus.util.CurrentDateHelper;
import gr.zeus.util.GUIUtils;
import gr.zeus.util.IOHelper;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * A java console to replace the command line window. Redirects the stdout
 * and stderr. Customizable. Can save its messages to text file. In addition,
 * it can auto dump to a log file and clear the textarea. Can be used with
 * JFrame, JInternalFrame or as a JPanel. If used in conjunction with any exe
 * creator for java, e.g:
 * <a href="http://launch4j.sourceforge.net/">launch4j</a> or
 * <a href="http://jsmooth.sourceforge.net/">JSmooth</a>,
 * it eliminates the dos application windows and your application looks more
 * professional.
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.0
 */
public final class JConsolePane extends JPanel {

    /**
     * Singleton implementation.
     */
    private static JConsolePane s_consolePane = null;

    /**
     * Default output stream.
     */
    private static final PrintStream STDOUT = System.out;

    /**
     * Default error stream.
     */
    private static final PrintStream STDERR = System.err;

    /**
     * Foreground color.
     */
    private static final Color FG_COLOR = Color.WHITE;

    /**
     * Background color.
     */
    private static final Color BG_COLOR = Color.BLACK;

    /**
     * Selected text color.
     */
    private static final Color SLT_COLOR = BG_COLOR;

    /**
     * Selection color.
     */
    private static final Color SL_COLOR = FG_COLOR;

    /**
     * Default text font.
     */
    private static final Font TEXT_FONT = new Font("Courier", 0, 12);

    /**
     * Out print stream.
     */
    private final PrintStream m_stdoutPS = new PrintStream(
        new JTextAreaOutStream( new ByteArrayOutputStream() ) );

    /**
     * Error print stream.
     */
    private final PrintStream m_stderrPS = new PrintStream(
        new JTextAreaOutStream( new ByteArrayOutputStream() ) );

    /**
     * File chooser for saving messages.
     */
    private final JFileChooser m_fileChooser = new JFileChooser();

    /**
     * Confirm title.
     */
    private String m_confimTitle = ResourceBundle.getBundle(
        "gr/zeus/res/jconsole").getString("confimTitle");

    /**
     * File exists warning message.
     */
    private String m_confimMessage = ResourceBundle.getBundle(
        "gr/zeus/res/jconsole").getString("confimMessage");

    /**
     * Default file name, used by the filechooser.
     */
    private String m_messagesFilename = "messages.txt";

    /**
     * Default file name, used by the auto dump functions.
     */
    private String m_traceFilename = "trace.log";

    /**
     * Append or overwrite the trace file
     * the first time JConsolePane tries to write to it?
     */
    private boolean m_appendFirstTime = false;

    /**
     * Auto save the trace file?
     */
    private boolean m_autoSave = false;

    /**
     * Display infinite characters in the textarea, no limit.
     * <p>
     * <b>NOTE:</b> Will slow down your application if a lot of messages
     * are to be displayed to the textarea (more than a couple of Kbytes).
     */
    private int m_maxChars = -1;

    /**
     * Number of times messages have been flushed to the trace file.
     */
    private int m_numFlushes = 0;

    /**
     * Initial buffer size.
     */
    private static final int BUF_SIZE = 4 * 1024;

    /**
     * Store here all the text for <code>dumpConsole()</code> usage.
     */
    private final StringBuilder m_consoleText = new StringBuilder( BUF_SIZE );


    /**
     * Private constructor. Initializes the GUI and prepares new streams.
     * <p>
     * @throws InterruptedException, InvocationTargetException
     */
    private JConsolePane()
        throws InterruptedException,
               InvocationTargetException
    {
        GUIUtils.invokeAndWait( new Runnable() {
            @Override public void run()
            {
                initComponents();

                ta_messages.setForeground( FG_COLOR );
                ta_messages.setBackground( BG_COLOR );
                ta_messages.setSelectedTextColor( SLT_COLOR );
                ta_messages.setSelectionColor( SL_COLOR );
                ta_messages.setFont( TEXT_FONT );

                m_fileChooser.setDialogType( JFileChooser.SAVE_DIALOG );
                m_fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
                m_fileChooser.setMultiSelectionEnabled( false );
                m_fileChooser.setAcceptAllFileFilterUsed( true );
                m_fileChooser.setControlButtonsAreShown( true );
            }
        });

        hideConsole();
    }


    /**
     * Singleton constructor.
     * <p>
     * @return  <code>JConsolePane</code>.
     * <p>
     * @throws ExceptionInInitializerError
     */
    public synchronized static JConsolePane getConsolePane()
    {
        if( s_consolePane==null )
        {
            try
            {
                s_consolePane = new JConsolePane();
            }
            catch(Exception ex)
            {
                // unlikely to happen!
                throw( new ExceptionInInitializerError(ex) );
            }
        }

        return( s_consolePane );
    }


    /**
     * Attachs the new streams to stdout and stderr.
     */
    public synchronized void startConsole()
    {
        System.setOut( m_stdoutPS );
        System.setErr( m_stderrPS );
    }


    /**
     * Attachs the original streams to stdout and stderr.
     */
    public synchronized void stopConsole()
    {
        System.setOut( STDOUT );
        System.setErr( STDERR );
    }


    /**
     * Shows the console.
     */
    public void showConsole()
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                setVisible( true );
            }
        });
    }


    /**
     * Hides the console.
     */
    public void hideConsole()
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                setVisible( false );
            }
        });
    }


    /**
     * Shows the control buttons.
     */
    public void showControlButtons()
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                button_panel.setVisible( true );
            }
        });
    }


    /**
     * Hides the control buttons.
     */
    public void hideControlButtons()
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                button_panel.setVisible( false );
            }
        });
    }


    /**
     * Clears all the messages stored in the internal buffer from the beginning
     * of the <code>JConsolePane</code>. Use this if you have printed too many
     * messages and you want to free up the memory used. Perhaps you should
     * invoke <code>dumpConsole()</code> to store the messages first, unless
     * of course you already have enabled autosave.
     */
    public synchronized void clearBufferMessages()
    {
        m_consoleText.delete(0, m_consoleText.length());
        m_consoleText.ensureCapacity( BUF_SIZE );
    }


    /**
     * Clears only the messages that are displayed in the textarea.
     */
    public void clearScreenMessages()
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                ta_messages.setText("");
            }
        });
    }


    /**
     * Dumps all the console messages (up to now) to a file (not only the
     * messages displayed in the textarea).
     * This method does not clear the messages buffer, use
     * <code>clearBufferMessages()</code> for this.
     * <p>
     * @param filename  The filename to store all console's messages.
     * @param append    If <code>true</code> text is appended in the file,
     *                  else the file gets overwritten.
     * <p>
     * @throws IOException
     */
    public void dumpConsole(String filename, boolean append)
        throws IOException
    {
        String txt = createTimestamp();
        synchronized(this)
        {
            txt += m_consoleText.toString();
        }
        IOHelper.saveTxtFile(filename, txt, append);
    }


    /**
     * Destroys the console.
     * Actually what it does is this:
     * Stops, hides and clears the console.
     * If you are to invoke <code>getConsole()</code> after this
     * method you will get a brand new console with no messages at all. This
     * method is not needed for simple applications that are about to invoke
     * System.exit() or normaly close.
     */
    public void destroyConsole()
    {
        stopConsole();

        hideConsole();

        // Clears the messages from JConsolePane (if not done already), because
        // if we have invoked destroyConsole() and will not call System.exit(),
        // right next, invoking getConsole() again and executing showConsole()
        // will show us our old messages...
        clearScreenMessages();

        // for the same reason, also clear the string buffer.
        clearBufferMessages();
    }


    /**
     * Updates the look and feel of the <code>JConsolePane</code> and it's
     * components. <b>MUST</b> be called <b>AFTER</b> invoking:
     * <code>UIManager.setLookAndFeel()</code> in your application.
     */
    public void updateLNF()
    {
        final Component THIS = this;
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                SwingUtilities.updateComponentTreeUI( THIS );
                SwingUtilities.updateComponentTreeUI( m_fileChooser );
            }
        });
    }


    /**
     * Gets the title for the popup window that confirms file overwrite.
     * <p>
     * @return  The title.
     */
    public String getConfimTitle()
    {
        return( m_confimTitle );
    }


    /**
     * Sets the title for the popup window that confirms file overwrite.
     * <p>
     * @param s The title.
     */
    public void setConfimTitle(String s)
    {
        m_confimTitle = s;
    }


    /**
     * Gets the text for the popup window that confirms file overwrite.
     * <p>
     * @return  The message.
     */
    public String getConfimMessage()
    {
        return( m_confimMessage );
    }


    /**
     * Sets the text for the popup window that confirms file overwrite.
     * <p>
     * @param s The message.
     */
    public void setConfimMessage(String s)
    {
        m_confimMessage = s;
    }


    /**
     * Gets the default filename for the filechooser.
     * <p>
     * @return  The filename.
     */
    public String getMessagesFilename()
    {
        return( m_messagesFilename );
    }


    /**
     * Sets the default filename for the filechooser.
     * <p>
     * @param s The filename.
     */
    public void setMessagesFilename(String s)
    {
        m_messagesFilename = s;
    }


    /**
     * Gets the file used by <code>dumpConsole()</code> and
     * all auto save methods.
     * <p>
     * @return  The filename.
     */
    public String getTraceFilename()
    {
        return( m_traceFilename );
    }


    /**
     * Sets the file used by <code>dumpConsole()</code> and
     * all auto save methods.
     * <p>
     * @param s The filename.
     */
    public void setTraceFilename(String s)
    {
        m_traceFilename = s;
    }


    /**
     * Gets append policy.
     * If the 'traceFilename' exists the first time we try to save the messages,
     * should we append or overwrite?
     * <p>
     * @return  true/false
     */
    public boolean getAppendFirstTime()
    {
        return( m_appendFirstTime );
    }


    /**
     * Sets append policy.
     * If the 'traceFilename' exists the first time we try to save the messages,
     * should we append or overwrite?
     * <p>
     * @param b true/false
     */
    public void setAppendFirstTime(boolean b)
    {
        m_appendFirstTime = b;
    }


    /**
     * Gets auto save status.
     * Auto save the console messages to file 'traceFilename' when 'maxChars'
     * are reached and clear the messages (append new messages when required)?
     * <p>
     * @return  true/false
     */
    public boolean getAutoSave()
    {
        return( m_autoSave );
    }


    /**
     * Sets auto save status.
     * Auto save the console messages to file 'traceFilename' when 'maxChars'
     * are reached and clear the messages (append new messages when required)?
     * <p>
     * @param b true/false
     */
    public void setAutoSave(boolean b)
    {
        m_autoSave = b;
    }


    /**
     * If -1 no limit, else the messages will be flushed to 'traceFilename' and
     * cleared when this limit is reached.
     * <p>
     * @return  The limit.
     */
    public int getMaxChars()
    {
        return( m_maxChars );
    }


    /**
     * If -1 no limit, else the messages will be flushed to 'traceFilename' and
     * cleared when this limit is reached.
     * <p>
     * @param i The limit.
     */
    public void setMaxChars(int i)
    {
        m_maxChars = i;
    }


    /**
     * Gets the foreground color of the textarea.
     * <p>
     * @return  The color.
     */
    public Color getForegroundColor()
    {
        return( ta_messages.getForeground() );
    }


    /**
     * Sets the foreground color of the textarea.
     * <p>
     * @param c The color.
     */
    public void setForegroundColor(final Color c)
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                ta_messages.setForeground( c );
            }
        });
    }


    /**
     * Gets the background color of the textarea.
     * <p>
     * @return  The color.
     */
    public Color getBackgroundColor()
    {
        return( ta_messages.getBackground() );
    }


    /**
     * Sets the background color of the textarea.
     * <p>
     * @param c The color.
     */
    public void setBackgroundColor(final Color c)
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                ta_messages.setBackground( c );
            }
        });
    }


    /**
     * Gets the selected text color of the textarea.
     * <p>
     * @return  The color.
     */
    public Color getSelectedTextColor()
    {
        return( ta_messages.getSelectedTextColor() );
    }


    /**
     * Sets the selected text color of the textarea.
     * <p>
     * @param c The color.
     */
    public void setSelectedTextColor(final Color c)
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                ta_messages.setSelectedTextColor( c );
            }
        });
    }


    /**
     * Gets the selection color of the textarea.
     * <p>
     * @return  The color.
     */
    public Color getSelectionColor()
    {
        return( ta_messages.getSelectionColor() );
    }


    /**
     * Sets the selection color of the textarea.
     * <p>
     * @param c The color.
     */
    public void setSelectionColor(final Color c)
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                ta_messages.setSelectionColor( c );
            }
        });
    }


    /**
     * Gets the text of the clear button.
     * <p>
     * @return  The text.
     */
    public String getClearButtonText()
    {
        return( btn_clear.getText() );
    }


    /**
     * Sets the text of the clear button.
     * <p>
     * @param s The text.
     */
    public void setClearButtonText(final String s)
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                btn_clear.setText( s );
            }
        });
    }


    /**
     * Gets the text of the save button.
     * <p>
     * @return  The text.
     */
    public String getSaveButtonText()
    {
        return( btn_save.getText() );
    }


    /**
     * Sets the text of the save button.
     * <p>
     * @param s The text.
     */
    public void setSaveButtonText(final String s)
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                btn_save.setText( s );
            }
        });
    }


    /**
     * Gets the text of the close button.
     * <p>
     * @return  The text.
     */
    public String getCloseButtonText()
    {
        return( btn_close.getText() );
    }


    /**
     * Sets the text of the close button.
     * <p>
     * @param s The text.
     */
    public void setCloseButtonText(final String s)
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                btn_close.setText( s );
            }
        });
    }


    /**
     * Gets the filechooser title.
     * <p>
     * @return  The title.
     */
    public String getFilechooserTitle()
    {
        return( m_fileChooser.getDialogTitle() );
    }


    /**
     * Sets the filechooser title.
     * <p>
     * @param s The title.
     */
    public void setFilechooserTitle(final String s)
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                m_fileChooser.setDialogTitle( s );
            }
        });
    }


    /**
     * Gets the filechooser approve button text.
     * <p>
     * @return  The text.
     */
    public String getFilechooserApproveButtonText()
    {
        return( m_fileChooser.getApproveButtonText() );
    }


    /**
     * Sets the filechooser approve button text.
     * <p>
     * @param s The text.
     */
    public void setFilechooserApproveButtonText(final String s)
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                m_fileChooser.setApproveButtonText( s );
            }
        });
    }


    /**
     * Gets the font of the textarea.
     * <p>
     * @return  The font.
     */
    public Font getTextFont()
    {
        return( ta_messages.getFont() );
    }


    /**
     * Sets the font of the textarea.
     * <p>
     * @param f The font.
     */
    public void setTextFont(final Font f)
    {
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override public void run()
            {
                ta_messages.setFont( f );
            }
        });
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_panel = new javax.swing.JPanel();
        btn_clear = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_close = new javax.swing.JButton();
        javax.swing.JScrollPane text_scrollpane = new javax.swing.JScrollPane();
        ta_messages = new javax.swing.JTextArea();

        setPreferredSize(new java.awt.Dimension(400, 300));
        setLayout(new java.awt.BorderLayout());

        button_panel.setPreferredSize(new java.awt.Dimension(150, 33));
        button_panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("gr/zeus/res/jconsole"); // NOI18N
        btn_clear.setText(bundle.getString("clearMsg")); // NOI18N
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        button_panel.add(btn_clear);

        btn_save.setText(bundle.getString("saveMsg")); // NOI18N
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });
        button_panel.add(btn_save);

        btn_close.setText(bundle.getString("closeMsg")); // NOI18N
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });
        button_panel.add(btn_close);

        add(button_panel, java.awt.BorderLayout.SOUTH);

        text_scrollpane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        ta_messages.setColumns(40);
        ta_messages.setEditable(false);
        ta_messages.setRows(20);
        text_scrollpane.setViewportView(ta_messages);

        add(text_scrollpane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btn_closeActionPerformed
    {//GEN-HEADEREND:event_btn_closeActionPerformed
        do_btn_close();
    }//GEN-LAST:event_btn_closeActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btn_saveActionPerformed
    {//GEN-HEADEREND:event_btn_saveActionPerformed
        do_btn_save();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btn_clearActionPerformed
    {//GEN-HEADEREND:event_btn_clearActionPerformed
        do_btn_clear();
    }//GEN-LAST:event_btn_clearActionPerformed


    /**
     * Executes close button command.
     */
    private void do_btn_close()
    {
        hideConsole();
    }


    /**
     * Executes clear button command.
     */
    private void do_btn_clear()
    {
        clearScreenMessages();
    }


    /**
     * Executes save button command.
     * Saves only text displayed in the textarea.
     */
    private void do_btn_save()
    {
        m_fileChooser.setSelectedFile( new File(m_messagesFilename) );
        int returnVal = m_fileChooser.showSaveDialog( this );
        if( returnVal==JFileChooser.APPROVE_OPTION )
        {
            File f = m_fileChooser.getSelectedFile();
            if( f.exists() )
            {
                int res = JOptionPane.showConfirmDialog(
                                this,
                                m_confimMessage,
                                m_confimTitle,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                if( res!=0 )
                {
                    return;
                }
            }

            try
            {
                IOHelper.saveTxtFile(f, ta_messages.getText(), false);
            }
            catch(IOException ex)
            {
                String msg = ResourceBundle.getBundle(
                    "gr/zeus/res/jconsole").getString("saveErrorMsg");
                System.err.println(msg + " " + ex.getMessage());
                JMessage.showErrorMessage(null, msg, ex);
            }
        }
    }


    /**
     * Generate a timestamp signature for the log file.
     */
    private String createTimestamp()
    {
        String timestamp = ResourceBundle.getBundle(
            "gr/zeus/res/jconsole").getString("timestampMessage") + " " +
            String.valueOf( CurrentDateHelper.getCurrentDay() ) + "/" +
            String.valueOf( CurrentDateHelper.getCurrentMonth() ) + "/" +
            String.valueOf( CurrentDateHelper.getCurrentYear() ) + " " +
            String.valueOf( CurrentDateHelper.getCurrentHour() ) + ":" +
            String.valueOf( CurrentDateHelper.getCurrentMinutes() ) + ":" +
            String.valueOf( CurrentDateHelper.getCurrentSeconds() + "\r\n\r\n");
        return( timestamp );
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_save;
    private javax.swing.JPanel button_panel;
    private javax.swing.JTextArea ta_messages;
    // End of variables declaration//GEN-END:variables


    /**
     * Private inner class. Filter to redirect the data to the textarea.
     */
    private final class JTextAreaOutStream extends FilterOutputStream {

        /**
         * Constructor.
         * <p>
         * @param aStream   The <code>OutputStream</code>.
         */
        public JTextAreaOutStream( OutputStream aStream )
        {
            super( aStream );
        }


        /**
         * Writes the messages.
         * <p>
         * @param b     The message in a <code>byte[]</code> array.
         * <p>
         * @throws IOException
         */
        public synchronized void write( byte b[] )
            throws IOException
        {
            String s = new String( b );
            m_consoleText.append( s );
            appendMessage( s );
            flushTextArea();
        }


        /**
         * Writes the messages.
         * <p>
         * @param b     The message in a <code>byte[]</code> array.
         * @param off   The offset.
         * @param len   Length.
         * <p>
         * @throws IOException
         */
        public synchronized void write( byte b[], int off, int len )
            throws IOException
        {
            String s = new String(b, off, len);
            m_consoleText.append( s );
            appendMessage( s );
            flushTextArea();
        }


        /**
         * Appends a message to the textarea and/or a textfile.
         * <p>
         * @param s     The message.
         * <p>
         * @throws IOException
         */
        private void appendMessage(final String s)
            throws IOException
        {
            java.awt.EventQueue.invokeLater( new Runnable() {
                @Override public void run()
                {
                    ta_messages.append( s );
                }
            });

            if( m_autoSave )
            {
                boolean append = true;
                if( m_numFlushes==0 && !m_appendFirstTime )
                {
                    append = false;
                }

                IOHelper.saveTxtFile(m_traceFilename, s, append);
                m_numFlushes++;
            }
        }


        /**
         * Clears the textarea if we have enabled a maxChars limit.
         */
        private void flushTextArea()
        {
            java.awt.EventQueue.invokeLater( new Runnable() {
                @Override public void run()
                {
                    int len = ta_messages.getText().length();

                    // Always scroll down to the last line
                    ta_messages.setCaretPosition( len );

                    // if we have set a maximum characters limit and
                    // we have exceeded that limit, clear the messages
                    if( m_maxChars > 0 && len > m_maxChars )
                    {
                        ta_messages.setText("");
                    }
                }
            });
        }

    }

}
