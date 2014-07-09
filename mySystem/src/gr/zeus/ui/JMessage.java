/*
 * JMessage.java
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

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ResourceBundle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.UIManager;

/**
 * This component is similar to <code>javax.swing.JOptionPane</code> component.
 * It can be used to display message and error dialogs. The main features of
 * <code>JMessage</code> are:<br>
 * <ul>
 * <li>Displays simple messages and the stacktrace of an exception</li>
 * <li>Supports html text for messages with build-in plain text conversion</li>
 * <li>The dialogs are modal. In addition they stay on top of other windows at
 * all times</li>
 * <li>Supports for <code>JOptionPane</code> OK,CANCEL,YES,NO buttons</li>
 * <li>Selects a default button in the dialog</li>
 * <li>Supports all <code>JOptionPane</code> messageType(s) & optionType(s)</li>
 * <li>Uses UI default icons or user supplied icons</li>
 * <li>Uses localized dialog titles and button text, or user supplied text</li>
 * <li>Returns dialog status depending on which button was pressed</li>
 * </ul>
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.40
 */
public final class JMessage extends JDialog {

    /**
     * Localized title for ERROR dialog.
     */
    private static final String ERROR_TITLE = ResourceBundle.getBundle(
        "gr/zeus/res/jmessage").getString("errorTitle");

    /**
     * Localized title for INFO dialog.
     */
    private static final String INFO_TITLE = ResourceBundle.getBundle(
        "gr/zeus/res/jmessage").getString("infoTitle");

    /**
     * Localized title for WARNING dialog.
     */
    private static final String WARNING_TITLE = ResourceBundle.getBundle(
        "gr/zeus/res/jmessage").getString("warningTitle");

    /**
     * Localized title for QUESTION dialog.
     */
    private static final String QUESTION_TITLE = ResourceBundle.getBundle(
        "gr/zeus/res/jmessage").getString("questionTitle");

    /**
     * Localized title for PLAIN dialog.
     */
    private static final String PLAIN_TITLE = ResourceBundle.getBundle(
        "gr/zeus/res/jmessage").getString("plainTitle");

    /**
     * Localized text for OK button.
     */
    private static final String OK_BUTTON_TEXT = ResourceBundle.getBundle(
        "gr/zeus/res/jmessage").getString("okButtonText");

    /**
     * Localized text for CANCEL button.
     */
    private static final String CANCEL_BUTTON_TEXT = ResourceBundle.getBundle(
        "gr/zeus/res/jmessage").getString("cancelButtonText");

    /**
     * Localized text for YES button.
     */
    private static final String YES_BUTTON_TEXT = ResourceBundle.getBundle(
        "gr/zeus/res/jmessage").getString("yesButtonText");

    /**
     * Localized text for NO button.
     */
    private static final String NO_BUTTON_TEXT = ResourceBundle.getBundle(
        "gr/zeus/res/jmessage").getString("noButtonText");

    /**
     * Icon for button that shows the exception's pane.
     */
    private static final ImageIcon EXPAND_ICON = new ImageIcon(
        JMessage.class.getResource("/gr/zeus/res/expand.gif"));

    /**
     * Icon for button that hides the exception's pane.
     */
    private static final ImageIcon COLLAPSE_ICON = new ImageIcon(
        JMessage.class.getResource("/gr/zeus/res/collapse.gif"));

    /**
     * Min columns displayed for the textarea's exception panel when the
     * message is not big enough.
     */
    private static final int MIN_COLUMNS = 50;

    /**
     * Number of rows displayed for the textarea's exception panel.
     */
    private static final int NUM_ROWS = 15;

    /**
     * Enum with all the supported buttons of the dialog.
     */
    public enum JMessageButtonEnum { OK, CANCEL, YES, NO;

        JButton whichOne(JButton btn_ok, JButton btn_cancel, JButton btn_yes,
            JButton btn_no)
        {
            switch( this ) {
                case OK:        return(btn_ok);
                case CANCEL:    return(btn_cancel);
                case YES:       return(btn_yes);
                case NO:        return(btn_no);
            }
            throw new AssertionError("Unknown JMessageButtonEnum: " + this);
        }
    }

    /**
     * Which is the default button for this dialog.
     */
    private JMessageButtonEnum m_defaultButton = JMessageButtonEnum.OK;

    /**
     * The return status of this dialog.
     */
    private int m_returnStatus = JOptionPane.CLOSED_OPTION;




    /**
     * Shows a question window with YES/NO button options. Default button is YES.
     * Also enables button to print the stacktrace of an exception.
     * NOTE: On dialog close, it also returns false.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     * @param ex            An exception to display it's stacktrace,
     *                      <code>null</code> will disable the stacktrace button
     *                      functionality.
     * <p>
     * @return              true for YES, false for NO
     */
    public static boolean showErrorQuestionMessageY(Frame parent, String message,
            Throwable ex)
    {
        int messageType = JOptionPane.ERROR_MESSAGE;

        JMessage msg = new JMessage(
            parent,
            message,
            null, // default dialog title for current locale
            messageType,
            JOptionPane.YES_NO_OPTION,
            JMessage.JMessageButtonEnum.YES,
            get_default_ico(messageType),
            ex,
            null, // default text
            null, // default text
            null, // default text
            null // default text
        );

        msg.setVisible(true);

        return( msg.getReturnStatus() == JOptionPane.YES_OPTION );
    }


    /**
     * Wrapper method for showMessageDialog(). Shows an error window with
     * default button option.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     */
    public static void showErrorMessage(Frame parent, String message)
    {
        showErrorMessage(parent, message, null);
    }


    /**
     * Wrapper method for showMessageDialog(). Shows an error window with
     * default button option.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     * @param ex            An exception to display it's stacktrace,
     *                      <code>null</code> will disable the stacktrace button
     *                      functionality.
     */
    public static void showErrorMessage(Frame parent, String message,
            Throwable ex)
    {
        showMessageDialog(parent, message, JOptionPane.ERROR_MESSAGE,
            JOptionPane.DEFAULT_OPTION, ex);
    }


    /**
     * Wrapper method for showMessageDialog(). Shows a warning window with
     * default button option.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     */
    public static void showWarningMessage(Frame parent, String message)
    {
        showWarningMessage(parent, message, null);
    }


    /**
     * Wrapper method for showMessageDialog(). Shows a warning window with
     * default button option.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     * @param ex            An exception to display it's stacktrace,
     *                      <code>null</code> will disable the stacktrace button
     *                      functionality.
     */
    public static void showWarningMessage(Frame parent, String message,
            Throwable ex)
    {
        showMessageDialog(parent, message, JOptionPane.WARNING_MESSAGE,
            JOptionPane.DEFAULT_OPTION, ex);
    }


    /**
     * Wrapper method for showMessageDialog(). Shows an info window with
     * default button option.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     */
    public static void showInfoMessage(Frame parent, String message)
    {
        showInfoMessage(parent, message, null);
    }


    /**
     * Wrapper method for showMessageDialog(). Shows an info window with
     * default button option.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     * @param ex            An exception to display it's stacktrace,
     *                      <code>null</code> will disable the stacktrace button
     *                      functionality.
     */
    public static void showInfoMessage(Frame parent, String message,
            Throwable ex)
    {
        showMessageDialog(parent, message, JOptionPane.INFORMATION_MESSAGE,
            JOptionPane.DEFAULT_OPTION, ex);
    }


    /**
     * Wrapper method for showMessageDialog(). Shows a question window with
     * YES/NO button options. Default button is NO, e.g. for use with delete
     * confirmation dialogs. NOTE: On dialog close, it also returns false.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     * <p>
     * @return              true for YES, false for NO
     */
    public static boolean showQuestionMessageN(Frame parent, String message)
    {
        int res = showMessageDialog(parent, message,
                        JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION,
                        JMessage.JMessageButtonEnum.NO);

        return( res == JOptionPane.YES_OPTION );
    }


    /**
     * Wrapper method for showMessageDialog(). Shows a question window with
     * YES/NO button options. Default button is YES, e.g. for use with save
     * confirmation dialogs. NOTE: On dialog close, it also returns false.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     * <p>
     * @return              true for YES, false for NO
     */
    public static boolean showQuestionMessageY(Frame parent, String message)
    {
        int res = showMessageDialog(parent, message,
                        JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION,
                        JMessage.JMessageButtonEnum.YES);

        return( res == JOptionPane.YES_OPTION );
    }


    /**
     * Factory method, create-show-return.
     * Creates a dialog relative to <code>parent</code> frame using UI defaults
     * for icon and a locale specific frame title.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     * @param messageType   Same as <code>JOptionPane</code>, one of:
     *                      ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE,
     *                      QUESTION_MESSAGE, or PLAIN_MESSAGE
     * @param optionType    Same as <code>JOptionPane</code>, one of:
     *                      DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION,
     *                      OK_CANCEL_OPTION
     * <p>
     * @return              The status of the dialog depending on which button
     *                      was pressed. Same as <code>JOptionPane</code>,
     *                      one of: CANCEL_OPTION, NO_OPTION, YES_OPTION,
     *                      OK_OPTION or CLOSED_OPTION if the dialog was closed.
     */
    public static int showMessageDialog(Frame parent, String message,
        int messageType, int optionType)
    {
        JMessage msg = new JMessage(
            parent,
            message,
            null, // default dialog title for current locale
            messageType,
            optionType,
            null, // default button
            get_default_ico(messageType),
            null, // no exception
            null, // default text
            null, // default text
            null, // default text
            null // default text
        );

        msg.setVisible(true);

        return( msg.getReturnStatus() );
    }


    /**
     * Factory method, create-show-return.
     * Creates a dialog relative to <code>parent</code> frame using UI defaults
     * for icon and a locale specific frame title.
     * Use <code>JMessageButtonEnum</code> to setup the default button.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     * @param messageType   Same as <code>JOptionPane</code>, one of:
     *                      ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE,
     *                      QUESTION_MESSAGE, or PLAIN_MESSAGE
     * @param optionType    Same as <code>JOptionPane</code>, one of:
     *                      DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION,
     *                      OK_CANCEL_OPTION
     * @param defaultButton The default selected button for the dialog, if
     *                      <code>null</code> the default for
     *                      <code>optionType</code> will be selected.
     * <p>
     * @return              The status of the dialog depending on which button
     *                      was pressed. Same as <code>JOptionPane</code>,
     *                      one of: CANCEL_OPTION, NO_OPTION, YES_OPTION,
     *                      OK_OPTION or CLOSED_OPTION if the dialog was closed.
     */
    public static int showMessageDialog(Frame parent, String message,
        int messageType, int optionType, JMessageButtonEnum defaultButton)
    {
        JMessage msg = new JMessage(
            parent,
            message,
            null, // default dialog title for current locale
            messageType,
            optionType,
            defaultButton,
            get_default_ico(messageType),
            null, // no exception
            null, // default text
            null, // default text
            null, // default text
            null // default text
        );

        msg.setVisible(true);

        return( msg.getReturnStatus() );
    }


    /**
     * Factory method, create-show-return.
     * Creates a dialog relative to <code>parent</code> frame using UI defaults
     * for icon and a locale specific frame title. Also enables button to print
     * the stacktrace of an exception.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     * @param messageType   Same as <code>JOptionPane</code>, one of:
     *                      ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE,
     *                      QUESTION_MESSAGE, or PLAIN_MESSAGE
     * @param optionType    Same as <code>JOptionPane</code>, one of:
     *                      DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION,
     *                      OK_CANCEL_OPTION
     * @param ex            An exception to display it's stacktrace,
     *                      <code>null</code> will disable the stacktrace button
     *                      functionality.
     * <p>
     * @return              The status of the dialog depending on which button
     *                      was pressed. Same as <code>JOptionPane</code>,
     *                      one of: CANCEL_OPTION, NO_OPTION, YES_OPTION,
     *                      OK_OPTION or CLOSED_OPTION if the dialog was closed.
     */
    public static int showMessageDialog(Frame parent, String message,
        int messageType, int optionType, Throwable ex)
    {
        JMessage msg = new JMessage(
            parent,
            message,
            null, // default dialog title for current locale
            messageType,
            optionType,
            null, // default button
            get_default_ico(messageType),
            ex,
            null, // default text
            null, // default text
            null, // default text
            null // default text
        );

        msg.setVisible(true);

        return( msg.getReturnStatus() );
    }


    /**
     * Factory method, create-show-return.
     * Creates fully customizable message dialog.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     * @param frameTitle    The title for the frame, if <code>null</code> the
     *                      default is used.
     * @param messageType   Same as <code>JOptionPane</code>, one of:
     *                      ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE,
     *                      QUESTION_MESSAGE, or PLAIN_MESSAGE
     * @param optionType    Same as <code>JOptionPane</code>, one of:
     *                      DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION,
     *                      OK_CANCEL_OPTION
     * @param defaultButton The default selected button for the dialog, if
     *                      <code>null</code> the default for
     *                      <code>optionType</code> will be selected.
     * @param ico           The custom icon to use, <code>null</code> means no
     *                      icon.
     * @param ex            An exception to display it's stacktrace,
     *                      <code>null</code> will disable the stacktrace button
     *                      functionality.
     * @param okText        Ok button text, if <code>null</code> the default
     *                      is used.
     * @param cancelText    Cancel button text, if <code>null</code> the default
     *                      is used.
     * @param yesText       Yes button text, if <code>null</code> the default
     *                      is used.
     * @param noText        No button text, if <code>null</code> the default
     *                      is used.
     * <p>
     * @return              The status of the dialog depending on which button
     *                      was pressed. Same as <code>JOptionPane</code>,
     *                      one of: CANCEL_OPTION, NO_OPTION, YES_OPTION,
     *                      OK_OPTION or CLOSED_OPTION if the dialog was closed.
     */
    public static int showMessageDialog(Frame parent, String message,
        String frameTitle, int messageType, int optionType,
        JMessageButtonEnum defaultButton, Icon ico, Throwable ex, String okText,
        String cancelText, String yesText, String noText)
    {
        JMessage msg = new JMessage(
            parent,
            message,
            frameTitle,
            messageType,
            optionType,
            defaultButton,
            ico,
            ex,
            okText,
            cancelText,
            yesText,
            noText);

        msg.setVisible(true);

        return( msg.getReturnStatus() );
    }


    /**
     * Constructor. Creates fully customizable message dialog.
     * <p>
     * @param parent        The parent relative frame, can be <code>null</code>.
     * @param message       The message to display, can be plain or html.
     * @param frameTitle    The title for the frame, if <code>null</code> the
     *                      default is used.
     * @param messageType   Same as <code>JOptionPane</code>, one of:
     *                      ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE,
     *                      QUESTION_MESSAGE, or PLAIN_MESSAGE
     * @param optionType    Same as <code>JOptionPane</code>, one of:
     *                      DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION,
     *                      OK_CANCEL_OPTION
     * @param defaultButton The default selected button for the dialog, if
     *                      <code>null</code> the default for
     *                      <code>optionType</code> will be selected.
     * @param ico           The custom icon to use, <code>null</code> means no
     *                      icon.
     * @param ex            An exception to display it's stacktrace,
     *                      <code>null</code> will disable the stacktrace button
     *                      functionality.
     * @param okText        Ok button text, if <code>null</code> the default
     *                      is used.
     * @param cancelText    Cancel button text, if <code>null</code> the default
     *                      is used.
     * @param yesText       Yes button text, if <code>null</code> the default
     *                      is used.
     * @param noText        No button text, if <code>null</code> the default
     *                      is used.
     */
    public JMessage(Frame parent, String message, String frameTitle,
        int messageType, int optionType, JMessageButtonEnum defaultButton,
        Icon ico, Throwable ex, String okText, String cancelText,
        String yesText, String noText)
    {
        super(
            parent,
            frameTitle!=null ? frameTitle : get_localized_title(messageType),
            true // modal
        );

        m_defaultButton = defaultButton;
        if( m_defaultButton==null )
        {
            m_defaultButton = get_default_button(optionType);
        }

        // MUST be called before the dialog becomes visible!
        if( JDialog.isDefaultLookAndFeelDecorated() )
        {
            boolean supportsWindowDecorations =
                UIManager.getLookAndFeel().getSupportsWindowDecorations();

            if( supportsWindowDecorations )
            {
                setUndecorated(true);
                getRootPane().setWindowDecorationStyle(
                    styleFromMessageType(messageType));
            }
        }

        initComponents();

        // remove the scrollpane used for the design form
        m_centerPane.remove( m_scrollPane );

        // set the icon
        m_icon.setIcon( ico );

        // display only used buttons
        setup_buttons(optionType, okText, cancelText, yesText, noText);

        // display the message
        m_messageText.setText( convert_message_to_html(message) );

        // display the exception
        boolean hasException = (ex!=null);
        if( hasException )
        {
            m_messageException.setColumns( MIN_COLUMNS );
            m_messageException.setRows( NUM_ROWS );

            StringWriter sw = new StringWriter();
            ex.printStackTrace( new PrintWriter(sw) );
            m_messageException.setText( sw.toString() );

            // Scroll up to the first line
            m_messageException.setCaretPosition( 0 );
        }

        // disable exception display functionality if it is not needed
        m_detailsButton.setVisible( hasException );

        // lockdown the panel size on the left based on the panel on the right
        // in order for the dialog to look nicer on screen.
        m_emptySpace.setMinimumSize( m_icon.getPreferredSize() );
        m_emptySpace.setMaximumSize( m_icon.getPreferredSize() );
        m_emptySpace.setPreferredSize( m_icon.getPreferredSize() );

        // revalidate and show the components
        pack();

        // java bug, component's layout is corrupt!
        pack();

        setLocationRelativeTo( parent );
    }


    /**
     * Returns the status of the dialog depending on which button was pressed.
     * <p>
     * @return              Same as <code>JOptionPane</code>, one of:
     *                      CANCEL_OPTION, NO_OPTION, YES_OPTION, OK_OPTION or
     *                      CLOSED_OPTION if the dialog was closed.
     */
    public int getReturnStatus()
    {
        return( m_returnStatus );
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        m_westPane = new javax.swing.JPanel();
        m_icon = new javax.swing.JLabel();
        m_centerPane = new javax.swing.JPanel();
        m_messagePane = new javax.swing.JPanel();
        m_messageText = new javax.swing.JLabel();
        m_detailsButton = new javax.swing.JToggleButton();
        m_scrollPane = new javax.swing.JScrollPane();
        m_messageException = new javax.swing.JTextArea();
        m_buttonsPane = new javax.swing.JPanel();
        m_okButton = new javax.swing.JButton();
        m_yesButton = new javax.swing.JButton();
        m_noButton = new javax.swing.JButton();
        m_cancelButton = new javax.swing.JButton();
        m_eastPane = new javax.swing.JPanel();
        m_emptySpace = new javax.swing.JLabel();

        setAlwaysOnTop(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt)
            {
                formWindowOpened(evt);
            }
        });

        m_westPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));
        m_westPane.add(m_icon);

        getContentPane().add(m_westPane, java.awt.BorderLayout.WEST);

        m_centerPane.setLayout(new java.awt.BorderLayout());

        m_messagePane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 20));
        m_messagePane.add(m_messageText);

        m_detailsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gr/zeus/res/expand.gif"))); // NOI18N
        m_detailsButton.setFocusPainted(false);
        m_detailsButton.setMaximumSize(new java.awt.Dimension(18, 18));
        m_detailsButton.setMinimumSize(new java.awt.Dimension(18, 18));
        m_detailsButton.setPreferredSize(new java.awt.Dimension(18, 18));
        m_detailsButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_detailsButtonActionPerformed(evt);
            }
        });
        m_messagePane.add(m_detailsButton);

        m_centerPane.add(m_messagePane, java.awt.BorderLayout.NORTH);

        m_messageException.setEditable(false);
        m_scrollPane.setViewportView(m_messageException);

        m_centerPane.add(m_scrollPane, java.awt.BorderLayout.CENTER);

        m_buttonsPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        m_okButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_okButtonActionPerformed(evt);
            }
        });
        m_buttonsPane.add(m_okButton);

        m_yesButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_yesButtonActionPerformed(evt);
            }
        });
        m_buttonsPane.add(m_yesButton);

        m_noButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_noButtonActionPerformed(evt);
            }
        });
        m_buttonsPane.add(m_noButton);

        m_cancelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_cancelButtonActionPerformed(evt);
            }
        });
        m_buttonsPane.add(m_cancelButton);

        m_centerPane.add(m_buttonsPane, java.awt.BorderLayout.SOUTH);

        getContentPane().add(m_centerPane, java.awt.BorderLayout.CENTER);

        m_eastPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));
        m_eastPane.add(m_emptySpace);

        getContentPane().add(m_eastPane, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened

        if( m_defaultButton!=null )
        {
            /* For some reason this piece of code is not working within the
             * constructor. Also the requestFocus() should not be needed, but it
             * does! Must check with Sun to see if this actually another swing
             * bug...?
             */

            JButton tmp = m_defaultButton.whichOne(m_okButton, m_cancelButton,
                m_yesButton, m_noButton);

            // only setup the default button if it is visible
            if( tmp.isVisible() )
            {
                getRootPane().setDefaultButton( tmp );
                tmp.requestFocus();
            }
        }

    }//GEN-LAST:event_formWindowOpened

    private void m_cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_cancelButtonActionPerformed
    {//GEN-HEADEREND:event_m_cancelButtonActionPerformed
        doClose(JOptionPane.CANCEL_OPTION);
    }//GEN-LAST:event_m_cancelButtonActionPerformed

    private void m_noButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_noButtonActionPerformed
    {//GEN-HEADEREND:event_m_noButtonActionPerformed
        doClose(JOptionPane.NO_OPTION);
    }//GEN-LAST:event_m_noButtonActionPerformed

    private void m_yesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_yesButtonActionPerformed
    {//GEN-HEADEREND:event_m_yesButtonActionPerformed
        doClose(JOptionPane.YES_OPTION);
    }//GEN-LAST:event_m_yesButtonActionPerformed

    private void m_okButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_okButtonActionPerformed
    {//GEN-HEADEREND:event_m_okButtonActionPerformed
        doClose(JOptionPane.OK_OPTION);
    }//GEN-LAST:event_m_okButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        doClose(JOptionPane.CLOSED_OPTION);
    }//GEN-LAST:event_formWindowClosing

    private void m_detailsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_detailsButtonActionPerformed
    {//GEN-HEADEREND:event_m_detailsButtonActionPerformed

        if( m_detailsButton.isSelected()==false )
        {
            m_detailsButton.setIcon( EXPAND_ICON );
            m_centerPane.remove( m_scrollPane );
        }
        else
        {
            m_detailsButton.setIcon( COLLAPSE_ICON );
            m_centerPane.add(m_scrollPane, BorderLayout.CENTER);
        }

        // revalidate and show the components
        pack();

    }//GEN-LAST:event_m_detailsButtonActionPerformed


    /**
     * Closes the dialog and sets the return value of the dialog.
     * <p>
     * @param retStatus     Same as <code>JOptionPane</code>, one of:
     *                      CANCEL_OPTION, NO_OPTION, YES_OPTION, OK_OPTION or
     *                      CLOSED_OPTION
     */
    private void doClose(int retStatus)
    {
        m_returnStatus = retStatus;
        setVisible(false);
        dispose();
    }


    /**
     * Gets the localized message title.
     * <p>
     * @param messageType   Same as <code>JOptionPane</code>, one of:
     *                      ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE,
     *                      QUESTION_MESSAGE, or PLAIN_MESSAGE
     * <p>
     * @return              The locale specific title for the dialog.
     */
    private static final String get_localized_title(int messageType)
    {
        switch(messageType) {
            case JOptionPane.ERROR_MESSAGE:
                return(ERROR_TITLE);

            case JOptionPane.INFORMATION_MESSAGE:
                return(INFO_TITLE);

            case JOptionPane.WARNING_MESSAGE:
                return(WARNING_TITLE);

            case JOptionPane.QUESTION_MESSAGE:
                return(QUESTION_TITLE);

            case JOptionPane.PLAIN_MESSAGE:
            default:
                return(PLAIN_TITLE);
        }
    }


    /**
     * Gets the UI default icon.
     * <p>
     * @param messageType   Same as <code>JOptionPane</code>, one of:
     *                      ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE,
     *                      QUESTION_MESSAGE, or PLAIN_MESSAGE
     * <p>
     * @return              The icon.
     */
    private static final Icon get_default_ico(int messageType)
    {
        switch(messageType) {
            case JOptionPane.ERROR_MESSAGE:
                return( UIManager.getIcon("OptionPane.errorIcon") );

            case JOptionPane.INFORMATION_MESSAGE:
                return( UIManager.getIcon("OptionPane.informationIcon") );

            case JOptionPane.WARNING_MESSAGE:
                return( UIManager.getIcon("OptionPane.warningIcon") );

            case JOptionPane.QUESTION_MESSAGE:
                return( UIManager.getIcon("OptionPane.questionIcon") );

            case JOptionPane.PLAIN_MESSAGE:
            default:
                return( null );
        }
    }


    /**
     * Setups the buttons required by the dialog.
     * <p>
     * @param optionType    Same as <code>JOptionPane</code>, one of:
     *                      DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION,
     *                      OK_CANCEL_OPTION
     * @param okText        Ok button text, if <code>null</code> the default
     *                      is used.
     * @param cancelText    Cancel button text, if <code>null</code> the default
     *                      is used.
     * @param yesText       Yes button text, if <code>null</code> the default
     *                      is used.
     * @param noText        No button text, if <code>null</code> the default
     *                      is used.
     */
    private void setup_buttons(int optionType, String okText, String cancelText,
        String yesText, String noText)
    {
        m_okButton.setText(
            okText!=null ? okText : OK_BUTTON_TEXT );

        m_cancelButton.setText(
            cancelText!=null ? cancelText : CANCEL_BUTTON_TEXT );

        m_yesButton.setText(
            yesText!=null ? yesText : YES_BUTTON_TEXT );

        m_noButton.setText(
            noText!=null ? noText : NO_BUTTON_TEXT );

        switch(optionType) {
            case JOptionPane.YES_NO_CANCEL_OPTION:
                m_okButton.setVisible( false );
                m_cancelButton.setVisible( true );
                m_yesButton.setVisible( true );
                m_noButton.setVisible( true );
                break;

            case JOptionPane.YES_NO_OPTION:
                m_okButton.setVisible( false );
                m_cancelButton.setVisible( false );
                m_yesButton.setVisible( true );
                m_noButton.setVisible( true );
                break;

            case JOptionPane.OK_CANCEL_OPTION:
                m_okButton.setVisible( true );
                m_cancelButton.setVisible( true );
                m_yesButton.setVisible( false );
                m_noButton.setVisible( false );
                break;

            case JOptionPane.DEFAULT_OPTION:
            default:
                m_okButton.setVisible( true );
                m_cancelButton.setVisible( false );
                m_yesButton.setVisible( false );
                m_noButton.setVisible( false );
                break;
        }
    }


    /**
     * Converts a plain text string to an html string if needed. Also replaces
     * all '\n' with '<br>'.
     * <p>
     * @param message       The message.
     * <p>
     * @return              The html string.
     */
    private static String convert_message_to_html(String message)
    {
        String htmlMessage = (message!=null) ? message : "";

        htmlMessage = htmlMessage.trim();

        if( !htmlMessage.startsWith("<html>") )
        {
            htmlMessage = "<html>" + htmlMessage;
        }

        if( !htmlMessage.endsWith("</html>") )
        {
            htmlMessage = htmlMessage + "</html>";
        }

        htmlMessage = htmlMessage.replaceAll("\n", "<br>");

        return( htmlMessage );
    }


    /**
     * Gets UI style from 'messageType'.
     * <p>
     * @param messageType   Same as <code>JOptionPane</code>, one of:
     *                      ERROR_MESSAGE, INFORMATION_MESSAGE, WARNING_MESSAGE,
     *                      QUESTION_MESSAGE, or PLAIN_MESSAGE
     * <p>
     * @return              One of: JRootPane.ERROR_DIALOG,
     *                      JRootPane.QUESTION_DIALOG, JRootPane.WARNING_DIALOG,
     *                      JRootPane.INFORMATION_DIALOG, JRootPane.PLAIN_DIALOG
     */
    private static int styleFromMessageType(int messageType)
    {
        switch( messageType ) {
            case JOptionPane.ERROR_MESSAGE:
                return(JRootPane.ERROR_DIALOG);

            case JOptionPane.QUESTION_MESSAGE:
                return(JRootPane.QUESTION_DIALOG);

            case JOptionPane.WARNING_MESSAGE:
                return(JRootPane.WARNING_DIALOG);

            case JOptionPane.INFORMATION_MESSAGE:
                return(JRootPane.INFORMATION_DIALOG);

            case JOptionPane.PLAIN_MESSAGE:
            default:
                return(JRootPane.PLAIN_DIALOG);
        }
    }


    /**
     * Gets a default button for dialog.
     * <p>
     * @param optionType    Same as <code>JOptionPane</code>, one of:
     *                      DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION,
     *                      OK_CANCEL_OPTION
     * <p>
     * @return              JMessageButtonEnum.YES or JMessageButtonEnum.OK
     */
    private static JMessageButtonEnum get_default_button(
        int optionType)
    {
        switch(optionType) {
            case JOptionPane.YES_NO_CANCEL_OPTION:
            case JOptionPane.YES_NO_OPTION:
                return( JMessageButtonEnum.YES );

            case JOptionPane.OK_CANCEL_OPTION:
            case JOptionPane.DEFAULT_OPTION:
            default:
                return( JMessageButtonEnum.OK );
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel m_buttonsPane;
    private javax.swing.JButton m_cancelButton;
    private javax.swing.JPanel m_centerPane;
    private javax.swing.JToggleButton m_detailsButton;
    private javax.swing.JPanel m_eastPane;
    private javax.swing.JLabel m_emptySpace;
    private javax.swing.JLabel m_icon;
    private javax.swing.JTextArea m_messageException;
    private javax.swing.JPanel m_messagePane;
    private javax.swing.JLabel m_messageText;
    private javax.swing.JButton m_noButton;
    private javax.swing.JButton m_okButton;
    private javax.swing.JScrollPane m_scrollPane;
    private javax.swing.JPanel m_westPane;
    private javax.swing.JButton m_yesButton;
    // End of variables declaration//GEN-END:variables

}
