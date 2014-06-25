/*
 * TypeSafeTextDocumentFilter.java
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

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * This class creates a custom filter for JTextComponents by extending the
 * DocumentFilter. With this filter we achieve the following data validation
 * within a field:
 * <li>max characters</li>
 * <li>uppercase/lowercase or not</li>
 * <li>valid characters</li>
 * <li>invalid characters</li>
 * <li>replaceable/replaced character pairs</li>
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.20
 */
public final class TypeSafeTextDocumentFilter extends DocumentFilter {

    // if you need more, give me a call...
    public static final int     DEFAULT_MAXCHARS = 4*1024;

    private static final boolean DEFAULT_UPPERCASE = false;
    private static final boolean DEFAULT_LOWERCASE = false;
    private static final String  DEFAULT_VALIDCHARS = "";
    private static final String  DEFAULT_INVALIDCHARS = "";
    private static final String  DEFAULT_REPLACABLECHARS = "";
    private static final String  DEFAULT_REPLACEDCHARS = "";

    /**
     * Should we convert to uppercase?
     */
    private boolean m_upperCase = DEFAULT_UPPERCASE;

    /**
     * Should we convert to lowercase?
     */
    private boolean m_lowerCase = DEFAULT_LOWERCASE;

    /**
     * Max characters to be typed.
     */
    private int     m_maxChars = DEFAULT_MAXCHARS;

    /**
     * Only these characters will be accepted.
     */
    private String  m_validChars = DEFAULT_VALIDCHARS;

    /**
     * Characters not to be accepted.
     */
    private String  m_invalidChars = DEFAULT_INVALIDCHARS;

    /**
     *  Characters that must be replaced.
     */
    private String  m_replacableChars = DEFAULT_REPLACABLECHARS;

    /**
     * Characters to replace with.
     */
    private String  m_replacedChars = DEFAULT_REPLACEDCHARS;


    /**
     * Constructor.
     */
    public TypeSafeTextDocumentFilter()
    {
    }


    /**
     * Invoked prior to insertion of text into the specified Document.
     * <p>
     * @param fb            The FilterBypass.
     * @param offset        The offset.
     * @param text          The text to insert.
     * @param attrs         The AttributeSet.
     */
    public void insertString(DocumentFilter.FilterBypass fb, int offset,
            String text, AttributeSet attrs)
        throws BadLocationException
    {
        replace(fb, offset, 0, text, attrs);
    }


    /**
     * Invoked prior to removal of the specified region
     * in the specified Document.
     * <p>
     * @param fb            The FilterBypass.
     * @param offset        The offset.
     * @param length        The length to remove.
     */
    public void remove(DocumentFilter.FilterBypass fb, int offset, int length)
        throws BadLocationException
    {
        replace(fb, offset, length, "", null);
    }


    /**
     * Invoked prior to replacing a region of text in the specified Document.
     * <p>
     * @param fb            The FilterBypass.
     * @param offset        The offset.
     * @param length        The length of the text.
     * @param text          The text to replace.
     * @param attrs         The AttributeSet.
     */
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
            String text, AttributeSet attrs)
        throws BadLocationException
    {
        if( text==null )
        {
            text = "";
        }

        Document doc = fb.getDocument();
        int currentLength = doc.getLength();
        String currentContent = doc.getText(0, currentLength);
        String before = currentContent.substring(0, offset);
        String after = currentContent.substring(length+offset, currentLength);
        String newValue = before + text + after;

        /* here we go... */
        // max limit
        if( newValue.length() > m_maxChars )
        {
            throw new BadLocationException("MaxChars limit exceeded",
                    newValue.length());
        }

        // valid characters
        if( m_validChars.length() > 0 )
        {
            for(int i=0; i<text.length(); i++)
            {
                if( m_validChars.indexOf(text.charAt(i)) == -1 )
                {
                    throw new BadLocationException("Invalid character", i);
                }
            }
        }

        // invalid characters
        if( m_invalidChars.length() > 0 )
        {
            for(int i=0; i<text.length(); i++)
            {
                if( m_invalidChars.indexOf(text.charAt(i)) != -1 )
                {
                    throw new BadLocationException("Invalid character", i);
                }
            }
        }

        // replacable characters
        if( m_replacableChars.length() > 0 && m_replacedChars.length() > 0 &&
            m_replacableChars.length() == m_replacedChars.length() )
        {
            char[] data = text.toCharArray();
            for(int i=0; i<data.length; i++)
            {
                for(int j=0; j<m_replacableChars.length(); j++)
                {
                    if( data[i] == m_replacableChars.charAt(j) )
                    {
                        data[i] = m_replacedChars.charAt(j);
                        break;
                    }
                }
            }
            text = String.copyValueOf(data);
        }

        text = (m_upperCase==true) ? text.toUpperCase() : text;
        text = (m_lowerCase==true) ? text.toLowerCase() : text;

        fb.replace(offset, length, text, attrs);
    }


    //--- ACCESS METHODS ---


    /**
     * Gets uppercase flag.
     * <p>
     * @return      true/false.
     */
    public boolean getUpperCase()
    {
        return( m_upperCase );
    }


    /**
     * Sets uppercase flag.
     * <p>
     * @param f     true/false.
     */
    public void setUpperCase(boolean f)
    {
        m_upperCase = f;
    }


    /**
     * Gets lowercase flag.
     * <p>
     * @return      true/false.
     */
    public boolean getLowerCase()
    {
        return( m_lowerCase );
    }


    /**
     * Sets lowercase flag.
     * <p>
     * @param f     true/false.
     */
    public void setLowerCase(boolean f)
    {
        m_lowerCase = f;
    }


    /**
     * Gets max characters allowed.
     * <p>
     * @return      maxChars.
     */
    public int getMaxChars()
    {
        return( m_maxChars );
    }


    /**
     * Sets max characters allowed.
     * <p>
     * @param num   maxChars.
     */
    public void setMaxChars(int num)
    {
        // max limit
        if( num <= 0 )
        {
            System.err.println("TypeSafeTextDocumentFilter: invalid maxChars(" +
                    num + "), reverting to default(" + DEFAULT_MAXCHARS + ")");
            m_maxChars = DEFAULT_MAXCHARS;
        }
        else
        {
            m_maxChars = num;
        }
    }


    /**
     * Gets valid characters.
     * <p>
     * @return      validChars.
     */
    public String getValidChars()
    {
        return( m_validChars );
    }


    /**
     * Sets valid characters.
     * <p>
     * @param all   validChars.
     */
    public void setValidChars(String all)
    {
        if( all==null )
        {
            m_validChars = DEFAULT_VALIDCHARS;
        }
        else
        {
            m_validChars = all;
        }
    }


    /**
     * Gets invalid characters.
     * <p>
     * @return      invalidChars.
     */
    public String getInvalidChars()
    {
        return( m_invalidChars );
    }


    /**
     * Sets invalid characters.
     * <p>
     * @param all   invalidChars.
     */
    public void setInvalidChars(String all)
    {
        if( all==null )
        {
            m_invalidChars = DEFAULT_INVALIDCHARS;
        }
        else
        {
            m_invalidChars = all;
        }
    }


    /**
     * Gets the replacable characters.
     * <b>Must be used in conjuction with replaced characters.</b>
     * <p>
     * @return      replacableChars.
     */
    public String getReplacableChars()
    {
        return( m_replacableChars );
    }


    /**
     * Sets replacable characters.
     * <b>Must be used in conjuction with replaced characters.</b>
     * <p>
     * @param all   replacableChars.
     */
    public void setReplacableChars(String all)
    {
        if( all==null )
        {
            m_replacableChars = DEFAULT_REPLACABLECHARS;
        }
        else
        {
            m_replacableChars = all;
        }
    }


    /**
     * Gets the replaced characters.
     * <b>Must be used in conjuction with replacable characters.</b>
     * <p>
     * @return      replacedChars.
     */
    public String getReplacedChars()
    {
        return( m_replacedChars );
    }


    /**
     * Sets replaced characters.
     * <b>Must be used in conjuction with replacable characters.</b>
     * <p>
     * @param all   replacedChars.
     */
    public void setReplacedChars(String all)
    {
        if( all==null )
        {
            m_replacedChars = DEFAULT_REPLACEDCHARS;
        }
        else
        {
            m_replacedChars = all;
        }
    }

}
