/*
 * TypeSafeVerifier.java
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

/**
 * This class creates a custom verifier for JTextComponents by extending the
 * InputVerifier. With this verifier we achieve the following data validation
 * within a field:
 * <li>min characters</li>
 * <li>trimspaces or not</li>
 * <li>byte,short,int and long numbers parsing</li>
 * <li>float and double numbers parsing</li>
 * <li>date parsing</li>
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.20
 */
public final class TypeSafeVerifier extends InputVerifier {

    private static final boolean        DEFAULT_TRIMSPACES = false;
    private static final int            DEFAULT_MINCHARS = 0;
    private static final java.util.Date DEFAULT_DATE =
                                            Calendar.getInstance().getTime();
    private static final String         DEFAULT_DATEPATTERN = "dd/MM/yyyy";

    /**
     * The class type of the field.
     */
    private Class m_classType = null;

    /**
     * Print a default value on invalid entry.
     */
    private boolean m_defaultsOnError = false;

    /**
     * Trim spaces flag.
     */
    private boolean m_trimSpaces = DEFAULT_TRIMSPACES;

    /**
     * Min characters for String fields.
     */
    private int m_minChars = DEFAULT_MINCHARS;

    /**
     * The date pattern for SimpleDateFormat.
     */
    private String m_datePattern = DEFAULT_DATEPATTERN;

    /**
     * The default date.
     */
    private java.util.Date m_defaultDateValue = DEFAULT_DATE;

    /**
     * The min byte value.
     */
    private byte m_minByteValue = Byte.MIN_VALUE;

    /**
     * The max byte value.
     */
    private byte m_maxByteValue = Byte.MAX_VALUE;

    /**
     * The default byte value.
     */
    private byte m_defaultByteValue = 0;

    /**
     * The min short value.
     */
    private short m_minShortValue = Short.MIN_VALUE;

    /**
     * The max short value.
     */
    private short m_maxShortValue = Short.MAX_VALUE;

    /**
     * The default short value.
     */
    private short m_defaultShortValue = 0;

    /**
     * The min integer value.
     */
    private int m_minIntegerValue = Integer.MIN_VALUE;

    /**
     * The max integer value.
     */
    private int m_maxIntegerValue = Integer.MAX_VALUE;

    /**
     * The default integer value.
     */
    private int m_defaultIntegerValue = 0;

    /**
     * The min long value.
     */
    private long m_minLongValue = Long.MIN_VALUE;

    /**
     * The max long value.
     */
    private long m_maxLongValue = Long.MAX_VALUE;

    /**
     * The default long value.
     */
    private long m_defaultLongValue = 0L;

    /**
     * The min float value.
     */
    private float m_minFloatValue = Float.MIN_VALUE;

    /**
     * The max float value.
     */
    private float m_maxFloatValue = Float.MAX_VALUE;

    /**
     * The default float value.
     */
    private float m_defaultFloatValue = 0.0F;

    /**
     * The min double value.
     */
    private double m_minDoubleValue = Double.MIN_VALUE;

    /**
     * The max double value.
     */
    private double m_maxDoubleValue = Double.MAX_VALUE;

    /**
     * The default double value.
     */
    private double m_defaultDoubleValue = 0.0D;


    /**
     * Constructor, define the class type of the field.
     */
    public TypeSafeVerifier(Class clsType)
    {
        m_classType = clsType;
    }


    /**
     * Verify the component and return true in order to allow focus to progress
     * to the next component or false to block the user inside this component
     * until a valid input is entered.
     * <p>
     * @param input         The component to verify.
     * <p>
     * @return              true/false.
     */
    public boolean verify(JComponent input)
    {
        JTextComponent tx = null;
        String text = "";

        /* supported JComponents */
        if( input instanceof JTextComponent )
        {
            tx = (JTextComponent) input;
            text = tx.getText();

            // trim spaces
            if( m_trimSpaces )
            {
                tx.setText( text.trim() );
                text = tx.getText();
            }
        }
        else
        {
            System.err.println("TypeSafeVerifier: unsupported JComponent(" +
                    input + ")");
            // don't care for other types of JComponent!
            return( true );
        }

        /* supported object types */
        if( m_classType.equals(String.class) )
        {
            return( check_string(text) );
        }
        if( m_classType.equals(java.util.Date.class) )
        {
            return( check_date(text, tx) );
        }
        else if( m_classType.equals(Byte.class) )
        {
            return( check_byte(text, tx) );
        }
        else if( m_classType.equals(Short.class) )
        {
            return( check_short(text, tx) );
        }
        else if( m_classType.equals(Integer.class) )
        {
            return( check_int(text, tx) );
        }
        else if( m_classType.equals(Long.class) )
        {
            return( check_long(text, tx) );
        }
        else if( m_classType.equals(Float.class) )
        {
            return( check_float(text, tx) );
        }
        else if( m_classType.equals(Double.class) )
        {
            return( check_double(text, tx) );
        }

        System.err.println("TypeSafeVerifier: unsupported classType(" +
                m_classType + ")");
        // on any other case the JComponent should function without blocking!
        return( true );
    }


    //--- ACCESS METHODS ---


    /**
     * Gets the class type.
     * <b>no setXXX() method for this one,
     * only set it through the constructor.</b>
     * <p>
     * @return      classType.
     */
    public Class getVerifierClassType()
    {
        return( m_classType );
    }


    /**
     * Gets defaultsOnError status.
     * <p>
     * @return      true/false.
     */
    public boolean getDefaultsOnError()
    {
        return( m_defaultsOnError );
    }


    /**
     * Sets defaultsOnError status.
     * <p>
     * @param f     true/false.
     */
    public void setDefaultsOnError(boolean f)
    {
        m_defaultsOnError = f;
    }


    /**
     * Gets trimspaces status.
     * <p>
     * @return      true/false.
     */
    public boolean getTrimSpaces()
    {
        return( m_trimSpaces );
    }


    /**
     * Sets trimspaces status.
     * <p>
     * @param f     true/false.
     */
    public void setTrimSpaces(boolean f)
    {
        m_trimSpaces = f;
    }


    /**
     * Gets min characters allowed.
     * <p>
     * @return      minChars.
     */
    public int getMinChars()
    {
        return( m_minChars );
    }


    /**
     * Sets min characters allowed.
     * <p>
     * @param v     minChars.
     */
    public void setMinChars(int v)
    {
        if( v >= 0 && v <= TypeSafeTextDocumentFilter.DEFAULT_MAXCHARS )
        {
            m_minChars = v;
        }
        else
        {
            System.err.println("TypeSafeVerifier: invalid minChars(" +
                    v + "), reverting to default(" + DEFAULT_MINCHARS + ")");
            m_minChars = DEFAULT_MINCHARS;
        }
    }


    /**
     * Gets the date pattern.
     * <p>
     * @return      datePattern.
     */
    public String getDatePattern()
    {
        return( m_datePattern );
    }


    /**
     * Sets the date pattern.
     * <p>
     * @param v     datePattern.
     */
    public void setDatePattern(String v)
    {
        m_datePattern = v;
    }


    /**
     * Gets the default date value.
     * <p>
     * @return      defaultDateValue.
     */
    public java.util.Date getDefaultDateValue()
    {
        return( m_defaultDateValue );
    }


    /**
     * Sets the default date value.
     * <p>
     * @param v     defaultDateValue.
     */
    public void setDefaultDateValue(java.util.Date v)
    {
        m_defaultDateValue = v;
    }


    /**
     * Gets the min byte value.
     * <p>
     * @return      minByteValue.
     */
    public byte getMinByteValue()
    {
        return( m_minByteValue );
    }


    /**
     * Sets the min byte value.
     * <p>
     * @param v     minByteValue.
     */
    public void setMinByteValue(byte v)
    {
        m_minByteValue = v;
    }


    /**
     * Gets the max byte value.
     * <p>
     * @return      maxByteValue.
     */
    public byte getMaxByteValue()
    {
        return( m_maxByteValue );
    }


    /**
     * Sets the max byte value.
     * <p>
     * @param v     maxByteValue.
     */
    public void setMaxByteValue(byte v)
    {
        m_maxByteValue = v;
    }


    /**
     * Gets the default byte value.
     * <p>
     * @return      defaultByteValue.
     */
    public byte getDefaultByteValue()
    {
        return( m_defaultByteValue );
    }


    /**
     * Sets the default byte value.
     * <p>
     * @param v     defaultByteValue.
     */
    public void setDefaultByteValue(byte v)
    {
        m_defaultByteValue = v;
    }


    /**
     * Gets the min short value.
     * <p>
     * @return      minShortValue.
     */
    public short getMinShortValue()
    {
        return( m_minShortValue );
    }


    /**
     * Sets the min short value.
     * <p>
     * @param v     minShortValue.
     */
    public void setMinShortValue(short v)
    {
        m_minShortValue = v;
    }


    /**
     * Gets the max short value.
     * <p>
     * @return      maxShortValue.
     */
    public short getMaxShortValue()
    {
        return( m_maxShortValue );
    }


    /**
     * Sets the max short value.
     * <p>
     * @param v     maxShortValue.
     */
    public void setMaxShortValue(short v)
    {
        m_maxShortValue = v;
    }


    /**
     * Gets the default short value.
     * <p>
     * @return      defaultShortValue.
     */
    public short getDefaultShortValue()
    {
        return( m_defaultShortValue );
    }


    /**
     * Sets the default short value.
     * <p>
     * @param v     defaultShortValue.
     */
    public void setDefaultShortValue(short v)
    {
        m_defaultShortValue = v;
    }


    /**
     * Gets the min integer value.
     * <p>
     * @return      minIntegerValue.
     */
    public int getMinIntegerValue()
    {
        return( m_minIntegerValue );
    }


    /**
     * Sets the min integer value.
     * <p>
     * @param v     minIntegerValue.
     */
    public void setMinIntegerValue(int v)
    {
        m_minIntegerValue = v;
    }


    /**
     * Gets the max integer value.
     * <p>
     * @return      maxIntegerValue.
     */
    public int getMaxIntegerValue()
    {
        return( m_maxIntegerValue );
    }


    /**
     * Sets the max integer value.
     * <p>
     * @param v     maxIntegerValue.
     */
    public void setMaxIntegerValue(int v)
    {
        m_maxIntegerValue = v;
    }


    /**
     * Gets the default integer value.
     * <p>
     * @return      defaultIntegerValue.
     */
    public int getDefaultIntegerValue()
    {
        return( m_defaultIntegerValue );
    }


    /**
     * Sets the default integer value.
     * <p>
     * @param v     defaultIntegerValue.
     */
    public void setDefaultIntegerValue(int v)
    {
        m_defaultIntegerValue = v;
    }


    /**
     * Gets the min long value.
     * <p>
     * @return      minLongValue.
     */
    public long getMinLongValue()
    {
        return( m_minLongValue );
    }


    /**
     * Sets the min long value.
     * <p>
     * @param v     minLongValue.
     */
    public void setMinLongValue(long v)
    {
        m_minLongValue = v;
    }


    /**
     * Gets the max long value.
     * <p>
     * @return      maxLongValue.
     */
    public long getMaxLongValue()
    {
        return( m_maxLongValue );
    }


    /**
     * Sets the max long value.
     * <p>
     * @param v     maxLongValue.
     */
    public void setMaxLongValue(long v)
    {
        m_maxLongValue = v;
    }


    /**
     * Gets the default long value.
     * <p>
     * @return      defaultLongValue.
     */
    public long getDefaultLongValue()
    {
        return( m_defaultLongValue );
    }


    /**
     * Sets the default long value.
     * <p>
     * @param v     defaultLongValue.
     */
    public void setDefaultLongValue(long v)
    {
        m_defaultLongValue = v;
    }


    /**
     * Gets the min float value.
     * <p>
     * @return      minFloatValue.
     */
    public float getMinFloatValue()
    {
        return( m_minFloatValue );
    }


    /**
     * Sets the min float value.
     * <p>
     * @param v     minFloatValue.
     */
    public void setMinFloatValue(float v)
    {
        m_minFloatValue = v;
    }


    /**
     * Gets the max float value.
     * <p>
     * @return      maxFloatValue.
     */
    public float getMaxFloatValue()
    {
        return( m_maxFloatValue );
    }


    /**
     * Sets the max float value.
     * <p>
     * @param v     maxFloatValue.
     */
    public void setMaxFloatValue(float v)
    {
        m_maxFloatValue = v;
    }


    /**
     * Gets the default float value.
     * <p>
     * @return      defaultFloatValue.
     */
    public float getDefaultFloatValue()
    {
        return( m_defaultFloatValue );
    }


    /**
     * Sets the default float value.
     * <p>
     * @param v     defaultFloatValue.
     */
    public void setDefaultFloatValue(float v)
    {
        m_defaultFloatValue = v;
    }


    /**
     * Gets the min double value.
     * <p>
     * @return      minDoubleValue.
     */
    public double getMinDoubleValue()
    {
        return( m_minDoubleValue );
    }


    /**
     * Sets the min double value.
     * <p>
     * @param v     m_minDoubleValue.
     */
    public void setMinDoubleValue(double v)
    {
        m_minDoubleValue = v;
    }


    /**
     * Gets the max double value.
     * <p>
     * @return      maxDoubleValue.
     */
    public double getMaxDoubleValue()
    {
        return( m_maxDoubleValue );
    }


    /**
     * Sets the max double value.
     * <p>
     * @param v     maxDoubleValue.
     */
    public void setMaxDoubleValue(double v)
    {
        m_maxDoubleValue = v;
    }


    /**
     * Gets the default double value.
     * <p>
     * @return      defaultDoubleValue.
     */
    public double getDefaultDoubleValue()
    {
        return( m_defaultDoubleValue );
    }


    /**
     * Sets the default double value.
     * <p>
     * @param v     defaultDoubleValue.
     */
    public void setDefaultDoubleValue(double v)
    {
        m_defaultDoubleValue = v;
    }


    //--- PRIVATE HELPER METHODS ---


    private boolean check_string(String text)
    {
        // min chars
        if( text.length() >= m_minChars )
        {
            return( true );
        }
        else
        {
            return( false );
        }
    }


    private boolean check_date(String text, JTextComponent tx)
    {
        SimpleDateFormat frm = null;
        try
        {
            frm = new SimpleDateFormat( m_datePattern );
            java.util.Date dt = frm.parse( text );
            return( true );
        }
        catch(Exception e)
        {
            String s = "";
            try
            {
                s = (frm!=null) ? frm.format( m_defaultDateValue ) : "";
            }
            catch(Exception ex)
            {
                System.err.println("TypeSafeVerifier: invalid datePattern(" +
                        m_datePattern + ")");
                s = "";
            }
            return( print_default(tx, (s!=null) ? s : "") );
        }
    }


    private boolean check_byte(String text, JTextComponent tx)
    {
        try
        {
            Byte num = new Byte( Byte.parseByte(text) );
            byte val = num.byteValue();

            // check max, min values
            if( val >= m_minByteValue && val <= m_maxByteValue )
            {
                return( true );
            }
            else
            {
                return( print_default(tx, String.valueOf(m_defaultByteValue)) );
            }
        }
        catch(Exception e)
        {
            return( print_default(tx, String.valueOf(m_defaultByteValue)) );
        }
    }


    private boolean check_short(String text, JTextComponent tx)
    {
        try
        {
            Short num = new Short( Short.parseShort(text) );
            short val = num.shortValue();

            // check max, min values
            if( val >= m_minShortValue && val <= m_maxShortValue )
            {
                return( true );
            }
            else
            {
                return( print_default(tx,
                            String.valueOf(m_defaultShortValue)) );
            }
        }
        catch(Exception e)
        {
            return( print_default(tx, String.valueOf(m_defaultShortValue)) );
        }
    }


    private boolean check_int(String text, JTextComponent tx)
    {
        try
        {
            Integer num = new Integer( Integer.parseInt(text) );
            int val = num.intValue();

            // check max, min values
            if( val >= m_minIntegerValue && val <= m_maxIntegerValue )
            {
                return( true );
            }
            else
            {
                return( print_default(tx,
                            String.valueOf(m_defaultIntegerValue)) );
            }
        }
        catch(Exception e)
        {
            return( print_default(tx, String.valueOf(m_defaultIntegerValue)) );
        }
    }


    private boolean check_long(String text, JTextComponent tx)
    {
        try
        {
            Long num = new Long( Long.parseLong(text) );
            long val = num.longValue();

            // check max, min values
            if( val >= m_minLongValue && val <= m_maxLongValue )
            {
                return( true );
            }
            else
            {
                return( print_default(tx, String.valueOf(m_defaultLongValue)) );
            }
        }
        catch(Exception e)
        {
            return( print_default(tx, String.valueOf(m_defaultLongValue)) );
        }
    }


    private boolean check_float(String text, JTextComponent tx)
    {
        try
        {
            Float num = new Float( Float.parseFloat(text) );
            float val = num.floatValue();

            // check max, min values
            if( val >= m_minFloatValue && val <= m_maxFloatValue )
            {
                return( true );
            }
            else
            {
                return( print_default(tx,
                            String.valueOf(m_defaultFloatValue)) );
            }
        }
        catch(Exception e)
        {
            return( print_default(tx, String.valueOf(m_defaultFloatValue)) );
        }
    }


    private boolean check_double(String text, JTextComponent tx)
    {
        try
        {
            Double num = new Double( Double.parseDouble(text) );
            double val = num.doubleValue();

            // check max, min values
            if( val >= m_minDoubleValue && val <= m_maxDoubleValue )
            {
                return( true );
            }
            else
            {
                return( print_default(tx,
                            String.valueOf(m_defaultDoubleValue)) );
            }
        }
        catch(Exception e)
        {
            return( print_default(tx, String.valueOf(m_defaultDoubleValue)) );
        }
    }


    private boolean print_default(JTextComponent tx, String val)
    {
        if( !m_defaultsOnError )
        {
            return( false );
        }

        tx.setText( val );

        return( true );
    }

}
