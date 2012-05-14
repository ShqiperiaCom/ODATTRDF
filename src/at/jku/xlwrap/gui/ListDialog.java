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
 *    FileChooserDemo.java per selektimin e skedareve qe do konvertohen
 *
 */
public class ListDialog extends JPanel
                             implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    JTextArea descr;
    JFileChooser fc;
    JList list; //shtoj JList per te perzgjedhur template
    //nderfaqja 
    public ListDialog() {
        super(new BorderLayout());
        //Krijon log si fillin sepse do ti referohen action listeners        
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
        //Krijoj nje file chooser
        fc = new JFileChooser();
        fc.addChoosableFileFilter(new XlsFilter());
        
        openButton = new JButton("Hap skedar...",null);                                
        openButton.addActionListener(this);

        saveButton = new JButton("Konverto ne RDF", null);                              
        saveButton.addActionListener(this);       
        // Krijoj nje list per te perzgjdhuer template
        String[] items = {"Template1", "Template2", "Template3"};
        list = new JList(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);   
        // Shtoj nje selection listener
        list.addListSelectionListener(new MyListSelectionListener());        
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 80));       

        JPanel buttonPanel = new JPanel(); //perdor FlowLayout        
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        
        JPanel scrollPanel = new JPanel(); //perdor FlowLayout
        scrollPanel.setBorder(BorderFactory.createTitledBorder("Template konvertimi"));
        scrollPanel.add(listScroller);
        scrollPanel.add(descrScrollPane);

        //Shtoj butonat ne panel
        add(buttonPanel, BorderLayout.PAGE_START);
        add(scrollPanel, BorderLayout.CENTER);
        add(logScrollPane, BorderLayout.SOUTH);
    }
    

    public void actionPerformed(ActionEvent e) {
        //kur klikohet butoni open
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(FileChooserDemo.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
            	log.setText("");
                File file = fc.getSelectedFile();                
                log.append("Duke hapur: " + file.getAbsolutePath() + "." + newline);
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

        //Kur klikohet butoni save.
        } 
        else if (e.getSource() == saveButton) {
        	File file = fc.getSelectedFile();            
            Object selectedTemplate = list.getSelectedValue();
            log.append("Template i perzgjedhur: "+ selectedTemplate + "." + newline);  
        	log.append("Duke u konvertuar ne RDF: " + file.getName() + "." + newline);
        	ConvertCSVtoRDFTemplate1 converter = new ConvertCSVtoRDFTemplate1();
        	int returnVal = converter.convert(file,selectedTemplate,log);
        	if(returnVal == 1){
        		log.append("Konvertim i suksesshem!" + newline);
        		
        	}
            log.setCaretPosition(log.getDocument().getLength());
        }
    }
    
    /**
     * Krijoj GUI dhe e shfaq
     * 
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Konverto skedar ne RDF/XML");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        frame.add(new FileChooserDemo());
        //Shfaq.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Krijoj dhe shfaq GUI per aplikacioni me ane te nje thread.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {                
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
    
    
    class MyListSelectionListener implements ListSelectionListener {
        //Kur seleketohet nje template
        public void valueChanged(ListSelectionEvent evt) {
            if (!evt.getValueIsAdjusting()) {
                JList list = (JList) evt.getSource();
                Object selectedTemplate = list.getSelectedValue();
                if (selectedTemplate.equals("Template1")){
                	descr.setText("Template1 per konvertim ne RDF/XML te skedareve XLS me te dhena te organizuara ne nje tabele dydimensionale (shtet, vit) per nje indikator te dhene.");
                }
                else if (selectedTemplate.equals("Template2")){
                	descr.setText("Template2 per konvertim ne RDF/XML te skedareve XLS me te dhena te organizuara ne nje tabele dydimensionale (produkt, vit) per nje indikator te dhene.");
                }
                else if (selectedTemplate.equals("Template3")){
                	descr.setText("Template3 per konvertim ne RDF/XML te skedareve XLS me te dhena te organizuara ne nje tabele dydimensionale (qytet, vit) per nje indikator te dhene.");
                }
            }        	

        }
    }
    

    
}


