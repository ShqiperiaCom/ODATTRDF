/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package at.jku.xlwrap.gui;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import test.xlwrap.opendata.ConvertCSVtoRDFTemplate1;

/*
 * FileChooserDemo.java uses these files:
 *   images/Open16.gif
 *   images/Save16.gif
 */
public class FileChooserDemo extends JPanel
                             implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    JTextArea descr;
    JFileChooser fc;
    JList list; //shtoj JList per te perzgjedhur template
    //nderfaqja 
    public FileChooserDemo() {
        super(new BorderLayout());

        //Create the mylog first, because the action listeners
        //need to refer to it.
        log = new JTextArea(10,20);        
        log.setMargin(new Insets(5,5,5,5));
        log.setPreferredSize(new Dimension(200, 200));
        log.setEditable(false);
        log.setLineWrap(true);
        JScrollPane logScrollPane = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //shtoj nje textarea qe pershkruan template
        descr = new JTextArea(4,20);        
        descr.setMargin(new Insets(5,5,5,5));
        descr.setPreferredSize(new Dimension(300, 300));
        descr.setEditable(false);
        descr.setLineWrap(true);
        JScrollPane descrScrollPane = new JScrollPane(descr, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //Create a file chooser
        fc = new JFileChooser();
        fc.addChoosableFileFilter(new XlsFilter());

        //Uncomment one of the following lines to try a different
        //file selection mode.  The first allows just directories
        //to be selected (and, at least in the Java look and feel,
        //shown).  The second allows both files and directories
        //to be selected.  If you leave these lines commented out,
        //then the default mode (FILES_ONLY) will be used.
        //
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        //Create the open button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        openButton = new JButton("Open a File...",null);
                                 //createImageIcon("images/Open16.gif"));
        openButton.addActionListener(this);

        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        saveButton = new JButton("Convert to RDF", null);
                                 //createImageIcon("images/Save16.gif"));
        saveButton.addActionListener(this);
       
        // Create a list per te perzgjdhuer template
        String[] items = {"Template1", "Template2", "Template3"};
        list = new JList(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);   
        // Register a selection listener
        list.addListSelectionListener(new MyListSelectionListener());        
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 80));
        

        
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout        
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        
        JPanel scrollPanel = new JPanel(); //use FlowLayout
        scrollPanel.setBorder(BorderFactory.createTitledBorder("Conversion Template"));
        scrollPanel.add(listScroller);
        scrollPanel.add(descrScrollPane);
        /**
        JPanel logsPanel = new JPanel(); //use FlowLayout
        logsPanel.setBorder(BorderFactory.createTitledBorder("Conversion Template"));
        logsPanel.add(logScrollPane);        
**/
        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(scrollPanel, BorderLayout.CENTER);
        add(logScrollPane, BorderLayout.SOUTH);
    }
    
    


    public void actionPerformed(ActionEvent e) {
        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(FileChooserDemo.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
            	log.setText("");
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                log.append("Opening: " + file.getAbsolutePath() + "." + newline);
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

        //Handle save button action.
        } 
        else if (e.getSource() == saveButton) {
        	File file = fc.getSelectedFile();            
            Object selectedTemplate = list.getSelectedValue();
            log.append("Selected Template: "+ selectedTemplate + "." + newline);  
        	log.append("Converting to RDF: " + file.getName() + "." + newline);
        	ConvertCSVtoRDFTemplate1 converter = new ConvertCSVtoRDFTemplate1();
        	int returnVal = converter.convert(file,selectedTemplate,log);
            //int returnVal = fc.showSaveDialog(FileChooserDemo.this);
            
//        	if (returnVal == JFileChooser.APPROVE_OPTION) {
//                //This is where a real application would save the file.
//                log.append("did it" + newline);
//            } else {
//                log.append("Save command cancelled by user." + newline);
//            }
        	if(returnVal == 1){
        		log.append("Convertion was successfull!" + newline);
        		
        	}
            log.setCaretPosition(log.getDocument().getLength());
        }
    }
    


    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = FileChooserDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Convert File to RDF/XML");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new FileChooserDemo());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
    
    
    class MyListSelectionListener implements ListSelectionListener {
        // This method is called each time the user changes the set of selected items
        public void valueChanged(ListSelectionEvent evt) {
            // When the user release the mouse button and completes the selection,
            // getValueIsAdjusting() becomes false
            if (!evt.getValueIsAdjusting()) {
                JList list = (JList) evt.getSource();
                Object selectedTemplate = list.getSelectedValue();
                if (selectedTemplate.equals("Template1")){
                	descr.setText("Template1 for RDF/XML conversion of XLS files with data arranged in a 2-dimensional table (country, year) for a specified indicator");
                }
                else if (selectedTemplate.equals("Template2")){
                	descr.setText("Template2 for RDF/XML conversion of XLS files with data arranged in a 2-dimensional table (product, year) for a specified indicator");
                }
                else if (selectedTemplate.equals("Template3")){
                	descr.setText("Template3 for RDF/XML conversion of XLS files with data arranged in a 2-dimensional table (city, year) for a specified indicator");
                }
                //log.append("Selected Template"+Selection+ newline);  
                /**
                int selections[] = list.getSelectedIndices();
                Object selectionValues[] = list.getSelectedValues();
                if (selections.length == 0) {
                	log.append("No template selected");                    
                }
                else {
                    for (int i = 0, n = selections.length; i < n; i++) { 
                      log.append(selections[i] + "/" + selectionValues[i] + " ");    
                    }
                }**/
            }
        	

        }
    }
    

    
}


