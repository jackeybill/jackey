import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

class AddressBook implements ActionListener
    {

          JPanel topPanel,bottomPanel;
    	  JScrollPane scrollPane;
    	  JFrame frame;

    	  JMenuBar menubar = new JMenuBar(); ;
       	  JMenu menu = new JMenu();
       	  JMenuItem menuItem;

       	  Toolkit kit = Toolkit.getDefaultToolkit();
       	  Dimension screenSize = kit.getScreenSize();
       	  int screenHeight = screenSize.height;
       	  int screenWidth = screenSize.width;

       	  Image img = kit.getImage("images/icon.JPG");

    	  AddressBook()
    	    {

            	    frame = new JFrame("Address Book");
            	    frame.setSize(680,200);
                    
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLocation(screenWidth/4, screenHeight/4);
                    frame.setIconImage(img);
                    addWidgets();
                    frame.show();

            }



         public void addWidgets()

            {
            	   	menubar.add(menu);

       	            menu = new JMenu("Options");
       	            menuItem = new JMenuItem("Add New Contact");
       	            menu.add(menuItem);
       	            menuItem.addActionListener(this);

       	            menuItem = new JMenuItem("Delete Contact");
       	            menu.add(menuItem);
       	            menuItem.addActionListener(this);

       	            menuItem = new JMenuItem("Search Contacts");
       	            menu.add(menuItem);
       	            menuItem.addActionListener(this);

       	            menuItem = new JMenuItem("Sort Contacts");
       	            menu.add(menuItem);
       	            menuItem.addActionListener(this);

       	            menuItem = new JMenuItem("View All Contacts");
       	            menu.add(menuItem);
       	            menuItem.addActionListener(this);

       	            menuItem = new JMenuItem("Backup Contacts");
       	            menu.add(menuItem);
       	            menuItem.addActionListener(this);


       	            menubar.add(menu);

       	            menu = new JMenu("Help");

       	            menuItem = new JMenuItem("Help Contents");
       	            menu.add(menuItem);
       	            menuItem.addActionListener(this);

       	            menuItem = new JMenuItem("About");
       	            menu.add(menuItem);
       	            menuItem.addActionListener(this);

       	            menubar.add(menu);

       	            frame.setJMenuBar(menubar);


       	            JPanel topPanel = new JPanel();
                    JPanel bottomPanel = new JPanel();

                    //Add Buttons To Bottom Panel
                    JButton AddNew = new JButton("Add New Contact");
                    JButton DeleteContact = new JButton("Delete Contact");
                    JButton SearchContacts = new JButton("Search Contacts");
                    JButton SortContacts = new JButton("Sort Contacts");
                    JButton ViewContactList = new JButton("View All Contacts");

                    JLabel label = new JLabel("<HTML><FONT FACE = ARIALSIZE = 2><B>Use The options below and In The Menu To Manage Contacts");

                    //Add Action Listeners
                    AddNew.addActionListener(this);
                    DeleteContact.addActionListener(this);
                    SearchContacts.addActionListener(this);
                    SortContacts.addActionListener(this);
                    ViewContactList.addActionListener(this);

                    topPanel.add(label);

                    bottomPanel.add(AddNew);
                    bottomPanel.add(DeleteContact);
                    bottomPanel.add(SearchContacts);
                    bottomPanel.add(SortContacts);
                    bottomPanel.add(ViewContactList);

                    frame.getContentPane().add(topPanel,
BorderLayout.NORTH);
                    frame.getContentPane().add(bottomPanel,
BorderLayout.SOUTH);
                    frame.setResizable(false);



            }


         public static void main(String args[])
            {
            	AddressBook add = new AddressBook();

            }


    	 OperationHandler oh = new OperationHandler();

    	 public void actionPerformed(ActionEvent ae)
    	    {
    	        if(ae.getActionCommand() == "Add New Contact")
    	            {
    	                 oh.AddNew();

    	            }

    	        else  if(ae.getActionCommand() == "Search Contacts")
    	            {
    	                 oh.SearchContacts();

    	            }

    	        else  if(ae.getActionCommand() == "Sort Contacts")
    	            {
    	                 oh.SortContacts();

    	            }

    	        else  if(ae.getActionCommand() == "Delete Contact")
    	            {
    	                  oh.DeleteContact();

    	            }

    	        else  if(ae.getActionCommand() == "View All Contacts")
    	            {

    	                  oh.ViewAllContacts();

    	            }

    	        else  if(ae.getActionCommand() == "About")
    	            {
    	                  //JOptionPane.showMessageDialog(frame, "About Address",JOptionPane.INFORMATION_MESSAGE);

    	            }
    	        else  if(ae.getActionCommand() == "Help Contents")
    	            {
    	                try
    	                 {
    	                     oh.showHelp();
    	                 }
    	                catch(IOException e)
    	                 {
    	                 }

    	            }
    	         else  if(ae.getActionCommand() == "Backup Contacts")
    	            {
    	                JFileChooser chooser = new JFileChooser();
    	     	        chooser.setCurrentDirectory(new File("."));
    	     	        chooser.setMultiSelectionEnabled(false);

chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	     	        chooser.showSaveDialog(frame);
    	     	        FileOutputStream  bfout = null;
    	     	        FileInputStream bfin = null;
    	     	        String filename=null;

    	     	        int p;

    	     	        try
    	     	         {
    	     	             filename = chooser.getSelectedFile().getPath();
    	     	         }
    	     	        catch(Exception e)
    	     	         {
    	     	         }

    	     	        try
    	     	           {
    	     	              bfout = new FileOutputStream(filename +"\\data.dat");
    	     	           }
    	     	        catch(Exception e)
    	     	           {

    	     	           }
    	     	        try
    	     	          {
    	     	             bfin = new FileInputStream("data/data.dat");
    	     	          }
    	     	        catch(Exception e)
    	     	          {

    	     	          }

    	     	        try
    	     	           {
    	     	           	   do
    	     	           	     {  p = bfin.read();
    	     	           	        if(p!=-1)
    	     	           	            bfout.write(p);
    	     	           	     }while(p!=-1);
    	     	           	}
    	     	         catch(Exception e)
    	     	            {

    	     	            }


    	            }

    	    }


    }





