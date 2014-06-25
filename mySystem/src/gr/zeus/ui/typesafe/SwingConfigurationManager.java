/*
 * SwingConfigurationManager.java
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

import java.awt.Component;
import java.awt.Container;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.JTextComponent;

/**
 * This class is the central point of execution for all the classes inside
 * <code>gr.zeus.ui.typesafe</code> package. Although each class can be used as
 * standalone, the SwingConfigurationManager provides an easy way to configure
 * multiple swing components within many forms via a property file. You can have
 * as many instances of this class as you need, just keep in mind that each
 * instance is attached to a single property file. The property file can also
 * exist inside a ResourceBundle. Aside the constructors the main methods are:
 * applyFocus(), initializes the focus for a container and configComponent(),
 * that configures all the swing components. Property file format example:
 * <br>customfocustraversalpolicy=true/false
 * <br>(tagname).(param)=(value)
 * <p>
 * <b>Note: Due to the internal design of the JFormattedTextField, the
 * TypeSafeTextDocumentFilter is not applied (see Sun's javadocs). Only the
 * Verifier's functionality is applied.</b>
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.20
 */
public final class SwingConfigurationManager {

    /**
     * Constants used for the property file parsing.
     */
    // global
    private static final String PROP_CUSTOMFOCUS = "customfocustraversalpolicy";
    private static final String PROP_SIMPLETEXTAREATRAVERSAL =
                                                    "simpletextareatraversal";
    private static final String PROP_SIMPLETEXTPANETRAVERSAL =
                                                    "simpletextpanetraversal";
    private static final String PROP_SIMPLEEDITORPANETRAVERSAL =
                                                    "simpleeditorpanetraversal";
    // per component
    private static final String PROP_CLASSTYPE = ".classtype";
    private static final String PROP_SELECTALL = ".selectall";
    private static final String PROP_TRIMSPACES = ".trimspaces";
    private static final String PROP_UPPERCASE = ".uppercase";
    private static final String PROP_LOWERCASE = ".lowercase";
    private static final String PROP_MAXCHARS = ".maxchars";
    private static final String PROP_MINCHARS = ".minchars";
    private static final String PROP_VALIDCHARS = ".validchars";
    private static final String PROP_INVALIDCHARS = ".invalidchars";
    private static final String PROP_REPLACABLECHARS = ".replacablechars";
    private static final String PROP_REPLACEDCHARS = ".replacedchars";
    private static final String PROP_DEFAULTSONERROR = ".defaultsonerror";
    private static final String PROP_DEFAULTNUM = ".defaultnum";
    private static final String PROP_MAXNUM = ".maxnum";
    private static final String PROP_MINNUM = ".minnum";
    private static final String PROP_DATEPATTERN = ".datepattern";
    private static final String PROP_DEFAULTDATE = ".defaultdate";

    /**
     * The properties.
     */
    private Properties m_props = new Properties();


    /**
     * Constructor, loads the property file from the specified pathname.
     * <p>
     * @param propertyFile      The pathname to the property file.
     */
    public SwingConfigurationManager(String propertyFile)
    {
        if( propertyFile==null )
        {
            System.err.println("Initialization failure for: " +
                "SwingConfigurationManager; null filename!");
            return;
        }

        FileInputStream fin = null;
        try
        {
            fin = new FileInputStream( propertyFile );
            m_props.load( fin );
        }
        catch(Exception e)
        {
            System.err.println("Initialization failure for: " +
                "SwingConfigurationManager; Failed to load property file: " +
                propertyFile + " ; " + e.getMessage());
        }
        finally
        {
            if( fin!=null )
            {
                try { fin.close(); } catch(Exception ignored) { }
            }
        }
    }


    /**
     * Constructor, creates the properties from the specified ResourceBundle.
     * <p>
     * @param res               The resource bundle to load the properties from.
     */
    public SwingConfigurationManager(ResourceBundle res)
    {
        if( res==null )
        {
            System.err.println("Initialization failure for: " +
                "SwingConfigurationManager; null ResourceBundle!");
            return;
        }

        try
        {
            Enumeration<String> e = res.getKeys();
            while( e.hasMoreElements() )
            {
                String key = e.nextElement();
                m_props.setProperty(key, res.getString(key));
            }
        }
        catch(Exception e)
        {
            System.err.println("Initialization failure for: " +
                "SwingConfigurationManager; Failed reading ResourceBundle; " +
                e.getMessage());
        }
    }


    /**
     * Apply focus policy to all the components in the array, depending on the
     * component's placement within this array.
     * Also changes the traversal keys for all JTextArea, JTextPane and
     * JEditorPane within this array if the appropriate properties are set in
     * the configuration file.
     * <p>
     * @param c                 The swing container, e.g. a JFrame.
     * @param all               The array with the components.
     */
    public void applyFocus(Container c, Component[] all)
    {
        if( get_prop(PROP_CUSTOMFOCUS).equalsIgnoreCase("true") )
        {
            c.setFocusTraversalPolicy( new CustomFocusTraversalPolicy(all) );
        }

        if( all != null )
        {
            for(int i=0; i<all.length; i++)
            {
                if( all[i] instanceof JTextArea && get_prop(
                    PROP_SIMPLETEXTAREATRAVERSAL).equalsIgnoreCase("true") )
                {
                    SimpleTextAreaTraversal trav =
                        new SimpleTextAreaTraversal( (JTextArea)all[i] );
                    trav.changeTraveralKeys();
                }
                else if(all[i] instanceof JTextPane && get_prop(
                    PROP_SIMPLETEXTPANETRAVERSAL).equalsIgnoreCase("true") )
                {
                    SimpleTextPaneTraversal trav =
                        new SimpleTextPaneTraversal( (JTextPane)all[i] );
                    trav.changeTraveralKeys();
                }
                else if(all[i] instanceof JEditorPane && get_prop(
                    PROP_SIMPLEEDITORPANETRAVERSAL).equalsIgnoreCase("true") )
                {
                    SimpleEditorPaneTraversal trav =
                        new SimpleEditorPaneTraversal( (JEditorPane)all[i] );
                    trav.changeTraveralKeys();
                }
            }
        }
    }


    /**
     * Apply a configuration to one or more components.
     * <p>
     * @param name              The tagname within the property file.
     * @param all               One or more components to apply the
     *                          configuration rule.
     */
    public void configComponent(String name, JTextComponent... all)
        throws Exception
    {
        for(JTextComponent c : all)
        {
            config_component(name, c);
        }
    }


    //--- PRIVATE HELPER METHODS ---


    private String get_prop(String p)
    {
        String s = m_props.getProperty(p);
        return( s!=null ? s : "" );
    }


    /**
     * This is the actual component configuration method.
     */
    private void config_component(String name, JTextComponent c)
        throws Exception
    {
        String cls = get_prop(name+PROP_CLASSTYPE);
        if( cls.length() < 1 )
        {
            cls = "java.lang.String";
        }

        TypeSafeTextDocumentFilter tstdf = new TypeSafeTextDocumentFilter();
        TypeSafeVerifier tsv = new TypeSafeVerifier( Class.forName(cls) );
        SelectAllTextOnFocus satof = new SelectAllTextOnFocus();

        /* TAKEN FROM JDK DOCUMENTATION:
         * Warning: As the AbstractFormatter will typically install a
         * DocumentFilter on the Document, and a NavigationFilter on the
         * JFormattedTextField you should not install your own. If you do,
         * you are likely to see odd behavior in that the editing policy of the
         * AbstractFormatter will not be enforced.
         */
        if( !(c instanceof JFormattedTextField) )
        {
            ((AbstractDocument) c.getDocument()).setDocumentFilter( tstdf );
        }

        c.setInputVerifier( tsv );
        c.addFocusListener( satof );

        if( get_prop(name+PROP_SELECTALL).equalsIgnoreCase("true") )
        {
            satof.setEnabled( true );
        }
        else if( get_prop(name+PROP_SELECTALL).equalsIgnoreCase("false") )
        {
            satof.setEnabled( false );
        }

        if( get_prop(name+PROP_TRIMSPACES).equalsIgnoreCase("true") )
        {
            tsv.setTrimSpaces( true );
        }
        else if( get_prop(name+PROP_TRIMSPACES).equalsIgnoreCase("false") )
        {
            tsv.setTrimSpaces( false );
        }

        if( get_prop(name+PROP_UPPERCASE).equalsIgnoreCase("true") )
        {
            tstdf.setUpperCase( true );
        }
        else if( get_prop(name+PROP_UPPERCASE).equalsIgnoreCase("false") )
        {
            tstdf.setUpperCase( false );
        }

        if( get_prop(name+PROP_LOWERCASE).equalsIgnoreCase("true") )
        {
            tstdf.setLowerCase( true );
        }
        else if( get_prop(name+PROP_LOWERCASE).equalsIgnoreCase("false") )
        {
            tstdf.setLowerCase( false );
        }

        String s = get_prop(name+PROP_MAXCHARS);
        if( s.length() > 0 )
        {
            int i = Integer.parseInt(s);
            tstdf.setMaxChars( i );
        }

        s = get_prop(name+PROP_MINCHARS);
        if( s.length() > 0 )
        {
            int i = Integer.parseInt(s);
            tsv.setMinChars( i );
        }

        s = get_prop(name+PROP_VALIDCHARS);
        if( s.length() > 0 )
        {
            tstdf.setValidChars( s );
        }

        s = get_prop(name+PROP_INVALIDCHARS);
        if( s.length() > 0 )
        {
            tstdf.setInvalidChars(s);
        }

        s = get_prop(name+PROP_REPLACABLECHARS);
        if( s.length() > 0 )
        {
            tstdf.setReplacableChars(s);
        }

        s = get_prop(name+PROP_REPLACEDCHARS);
        if( s.length() > 0 )
        {
            tstdf.setReplacedChars(s);
        }

        if( get_prop(name+PROP_DEFAULTSONERROR).equalsIgnoreCase("true") )
        {
            tsv.setDefaultsOnError( true );
        }
        else if( get_prop(name+PROP_DEFAULTSONERROR).equalsIgnoreCase("false") )
        {
            tsv.setDefaultsOnError( false );
        }

        s = get_prop(name+PROP_DEFAULTNUM);
        if( s.length() > 0 )
        {
            Class vclass = tsv.getVerifierClassType();
            if( vclass.equals(String.class) )
            {
                /* ignored */
            }
            else if( vclass.equals(Byte.class) )
            {
                tsv.setDefaultByteValue( Byte.parseByte(s) );
            }
            else if( vclass.equals(Short.class) )
            {
                tsv.setDefaultShortValue( Short.parseShort(s) );
            }
            else if( vclass.equals(Integer.class) )
            {
                tsv.setDefaultIntegerValue( Integer.parseInt(s) );
            }
            else if( vclass.equals(Long.class) )
            {
                tsv.setDefaultLongValue( Long.parseLong(s) );
            }
            else if( vclass.equals(Float.class) )
            {
                tsv.setDefaultFloatValue( Float.parseFloat(s) );
            }
            else if( vclass.equals(Double.class) )
            {
                tsv.setDefaultDoubleValue( Double.parseDouble(s) );
            }
        }

        s = get_prop(name+PROP_MAXNUM);
        if( s.length() > 0 )
        {
            Class vclass = tsv.getVerifierClassType();
            if( vclass.equals(String.class) )
            {
                /* ignored */
            }
            else if( vclass.equals(Byte.class) )
            {
                tsv.setMaxByteValue( Byte.parseByte(s) );
            }
            else if( vclass.equals(Short.class) )
            {
                tsv.setMaxShortValue( Short.parseShort(s) );
            }
            else if( vclass.equals(Integer.class) )
            {
                tsv.setMaxIntegerValue( Integer.parseInt(s) );
            }
            else if( vclass.equals(Long.class) )
            {
                tsv.setMaxLongValue( Long.parseLong(s) );
            }
            else if( vclass.equals(Float.class) )
            {
                tsv.setMaxFloatValue( Float.parseFloat(s) );
            }
            else if( vclass.equals(Double.class) )
            {
                tsv.setMaxDoubleValue( Double.parseDouble(s) );
            }
        }

        s = get_prop(name+PROP_MINNUM);
        if( s.length() > 0 )
        {
            Class vclass = tsv.getVerifierClassType();
            if( vclass.equals(String.class) )
            {
                /* ignored */
            }
            else if( vclass.equals(Byte.class) )
            {
                tsv.setMinByteValue( Byte.parseByte(s) );
            }
            else if( vclass.equals(Short.class) )
            {
                tsv.setMinShortValue( Short.parseShort(s) );
            }
            else if( vclass.equals(Integer.class) )
            {
                tsv.setMinIntegerValue( Integer.parseInt(s) );
            }
            else if( vclass.equals(Long.class) )
            {
                tsv.setMinLongValue( Long.parseLong(s) );
            }
            else if( vclass.equals(Float.class) )
            {
                tsv.setMinFloatValue( Float.parseFloat(s) );
            }
            else if( vclass.equals(Double.class) )
            {
                tsv.setMinDoubleValue( Double.parseDouble(s) );
            }
        }

        s = get_prop(name+PROP_DATEPATTERN);
        if( s.length() > 0 )
        {
            tsv.setDatePattern( s );
        }

        s = get_prop(name+PROP_DEFAULTDATE);
        if( s.length() > 0 )
        {
            try
            {
                SimpleDateFormat frm = new SimpleDateFormat(
                                                        tsv.getDatePattern() );
                java.util.Date dt = frm.parse( s );
                tsv.setDefaultDateValue( dt );
            }
            catch(Exception e)
            {
                /* set nothing */
                System.err.println("Configuration failure for: " +
                    "SwingConfigurationManager; defaultdate is invalid!");
            }
        }
    }

}
