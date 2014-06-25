/*
 * SimpleEditorPaneTraversal.java
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
package gr.zeus.ui.typesafe;

import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JEditorPane;

/**
 * This class can be used to replace the default traversal keys for any
 * JEditorPane object. As you already know when advancing focus inside a form,
 * using the TAB key, when you reach a editor panel you need to press CTRL+TAB
 * in order to advance the focus to the next component. With this class you
 * can address this issue and use the TAB key instead.
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.50
 */
public final class SimpleEditorPaneTraversal {

    /**
     * The new forward traversal keys, TAB and CTRL+TAB.
     */
    private static final Set<AWTKeyStroke> NEW_FORWARD_TRAVERSAL_KEYS =
                                                    new HashSet<AWTKeyStroke>();
    static
    {
        NEW_FORWARD_TRAVERSAL_KEYS.add( AWTKeyStroke.getAWTKeyStroke(
            KeyEvent.VK_TAB, 0) ); // use 0 to specify no modifiers

        NEW_FORWARD_TRAVERSAL_KEYS.add( AWTKeyStroke.getAWTKeyStroke(
            KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK) );
    }

    /**
     * The new backward traversal keys, SHIFT+TAB and CTRL+SHIFT+TAB.
     */
    private static final Set<AWTKeyStroke> NEW_BACKWARD_TRAVERSAL_KEYS =
                                                    new HashSet<AWTKeyStroke>();
    static
    {
        NEW_BACKWARD_TRAVERSAL_KEYS.add( AWTKeyStroke.getAWTKeyStroke(
            KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK) );

        NEW_BACKWARD_TRAVERSAL_KEYS.add( AWTKeyStroke.getAWTKeyStroke(
            KeyEvent.VK_TAB,
                InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK) );
    }

    /**
     * The JEditorPane instance.
     */
    private JEditorPane m_editorPane = null;

    /**
     * A backup copy of the forward traversal keys.
     */
    private Set<AWTKeyStroke> m_oldForwardKeys = null;

    /**
     * A backup copy of the backward traversal keys.
     */
    private Set<AWTKeyStroke> m_oldBackwardKeys = null;


    /**
     * Constructor, Stores a backup copy of the original traversal keys
     * of this editor panel.
     * <p>
     * @param ep    The editor panel object.
     */
    public SimpleEditorPaneTraversal(JEditorPane ep)
    {
        m_editorPane = ep;

        // store original keys
        m_oldForwardKeys = m_editorPane.getFocusTraversalKeys(
                                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        m_oldBackwardKeys = m_editorPane.getFocusTraversalKeys(
                                KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);
    }


    /**
     * Replaces the original traversal keys with new ones.
     * After invoking this method you can advance focus out of the editor panel
     * using only the TAB key instead of CTRL+TAB.
     */
    public void changeTraveralKeys()
    {
        m_editorPane.setFocusTraversalKeys(
            KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                NEW_FORWARD_TRAVERSAL_KEYS);

        m_editorPane.setFocusTraversalKeys(
            KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
                NEW_BACKWARD_TRAVERSAL_KEYS);
    }


    /**
     * Restores the original traversal keys.
     */
    public void restoreTraveralKeys()
    {
        m_editorPane.setFocusTraversalKeys(
            KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, m_oldForwardKeys);

        m_editorPane.setFocusTraversalKeys(
            KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, m_oldBackwardKeys);
    }

}
