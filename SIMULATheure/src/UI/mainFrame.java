
package UI;

import java.awt.BorderLayout;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFileChooser;

import Application.Controller.Tool;
import Domain.Node.Node;
import Application.Controller.Simulation;
import Domain.Trips.Trip;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.nio.file.*;
/*import Domain.Node.*;
import Domain.Client.*;
import Domain.Generation.*;
import Domain.Positions.GeographicPosition;
import Domain.Trips.*;
import Domain.Vehicule.*;*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Elise
 */
public class mainFrame extends javax.swing.JFrame {

    double scaleFactor = 1.0;
    Simulation controller;
    boolean nodeSelectedForSegment = false;
    
    /**
     * Creates new form mainFrame
     */
    public mainFrame() {
        initComponents();
        String lbl = "<html>" + "Générateur" + "<br>" + "de clients" + "</html>";
        btnClientGenerator.setText(lbl);
        lbl = "<html>" + "Générateur" + "<br>" + "de véhicules" + "</html>";
        btnVehiculeGenerator.setText(lbl);
        lbl = "<html>" + "Profil" + "<br>" + "client" + "</html>";
        btnClientProfile.setText(lbl);
        btnAdd.setVisible(false);
        lstToolItems.setVisible(false);
        scrollPaneTool.setVisible(false);
        
        this.controller = new Simulation();
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowClosing(WindowEvent winEvt) {
            controller.deleteIniialSaves();
        }
    });
        
        zp.addMouseMotionListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseMoved(java.awt.event.MouseEvent e) {
                lblCoordinate.setText("Coordonnées: Latitude " + (int)(e.getX() / zp.getZoom()) + " Longitude " + (int)(e.getY()/zp.getZoom()));
                
                if(nodeSelectedForSegment)
                {
                    if (lines.size() == 4)
                    {
                        lines.remove(3);
                        lines.remove(2);
                    }
                    lines.add((int)(e.getX() / zp.getZoom()));
                    lines.add((int)(e.getY() / zp.getZoom()));
                }
                else
                {
                    lines.removeAll(lines);
                }
                zp.repaint();
            }
        });
        zp.addMouseWheelListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseWheelMoved(java.awt.event.MouseWheelEvent e){
               int rotation = e.getWheelRotation();

               if (rotation < 0) {
                    scaleFactor = zp.getZoom() * 1.1;
                } else {
                    scaleFactor = zp.getZoom() / 1.1;
                }  
                zp.setZoom(scaleFactor); 
             
        } 
        });
        
        zp.addMouseListener(new java.awt.event.MouseAdapter(){
        @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                
                switch(controller.getCurrentTool())
                {
                    case STOP:
                        if(controller.getNodeAtPostion((float)(e.getX()/zp.getZoom()), (float)(e.getY()/zp.getZoom())) != null)
                        {
                            ModifyStop form = new ModifyStop(controller, 
                                                             controller.getNodeAtPostion((float)(e.getX()/zp.getZoom()), (float)(e.getY()/zp.getZoom())).getName(),
                                                             controller.getNodeAtPostion((float)(e.getX()/zp.getZoom()), (float)(e.getY()/zp.getZoom())).getGeographicPosition().getXPosition(),
                                                             controller.getNodeAtPostion((float)(e.getX()/zp.getZoom()), (float)(e.getY()/zp.getZoom())).getGeographicPosition().getYPosition());
                            form.addWindowListener(new java.awt.event.WindowAdapter (){
                                @Override
                                public void windowClosed(java.awt.event.WindowEvent e){
                                    updateListStop();
                                    doAction();
                                }
                            });
                            form.setVisible(true);
                            //zp.repaint();
                        }
                        else
                        {
                            double zoom = zp.getZoom();
                            controller.addNode((float)(e.getX() / zoom), (float)(e.getY() / zoom));
                            updateListStop();
                            doAction();
                            zp.repaint();
                        }
                        break;
                    case SEGMENT:
                        if(e.getButton() == MouseEvent.BUTTON3) //Efface le segment en cours lorsqu'on appuie sur click droit
                        {
                            nodeSelectedForSegment = false;
                            lines.removeAll(lines);
                            zp.repaint();
                        }
                        else if(nodeSelectedForSegment && controller.getNodeAtPostion((float)(e.getX() / zp.getZoom()), (float)(e.getY()/zp.getZoom())) != null)
                        {
                            nodeSelectedForSegment = false;
                            controller.addSegment(controller.getNodeAtPostion(lines.get(0), lines.get(1)), controller.getNodeAtPostion((float)(e.getX()/zp.getZoom()), (float)(e.getY()/zp.getZoom())));
                            updateListSegment();
                            doAction();
                        }
                        else if (controller.getNodeAtPostion((float)(e.getX() / zp.getZoom()), (float)(e.getY() / zp.getZoom())) != null)
                        {
                            nodeSelectedForSegment = (controller.getNodeAtPostion((float)(e.getX()/zp.getZoom()), (float)(e.getY()/zp.getZoom())) != null);
                            lines.add((int)controller.getNodeAtPostion((float)(e.getX()/zp.getZoom()), (float)(e.getY()/zp.getZoom())).getGeographicPosition().getXPosition());
                            lines.add((int)controller.getNodeAtPostion((float)(e.getX()/zp.getZoom()), (float)(e.getY()/zp.getZoom())).getGeographicPosition().getYPosition());
                        }
                        else if(controller.getSegmentAtPostion((float)(e.getX()/zp.getZoom()),(float)(e.getY()/zp.getZoom())) != null)
                        {
                            ModifySegment form = new ModifySegment(controller, (float)(e.getX()/zp.getZoom()),(float)(e.getY()/zp.getZoom()));
                            form.addWindowListener(new java.awt.event.WindowAdapter (){
                                @Override
                                public void windowClosed(java.awt.event.WindowEvent e){
                                    updateListSegment();
                                    doAction();
                                }
                            });
                            form.setVisible(true);
                        }
                        break;
                    case TRIP:
                        break;
                    case VEHICULE:
                        //controller.addVehicule(null, null, null);
                        updateListVehicule();
                        break;
                    case CLIENT:
                        controller.addClient(null);
                        updateListClient();
                        break;
                    case CLIENT_GENERATOR:
                        updateListClientGenerator();
                        break;
                    case VEHICULE_GENERATOR:
                        break;
                    case CLIENT_PROFILE:  
                        controller.addClientProfile(null);
                        updateListClientProfile();
                        break;
                }
            }
        });
        
        lstToolItems.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent evt) {
            JList list = (JList)evt.getSource();
            if (evt.getClickCount() == 2) {
                switch(controller.getCurrentTool())
                {
                    case STOP:
                        float x = 0;
                        float y = 0;
                        for (int i = 0; i < controller.getListNode().size(); i++)
                        {
                            if (controller.getListNode().get(i).getName().equals(lstToolItems.getSelectedValue().toString()))
                            {
                                x = controller.getListNode().get(i).getGeographicPosition().getXPosition();
                                y = controller.getListNode().get(i).getGeographicPosition().getYPosition();
                                break;
                            }
                        }
                        ModifyStop formStop = new ModifyStop(controller, lstToolItems.getSelectedValue().toString(), x, y);
                        formStop.addWindowListener(new java.awt.event.WindowAdapter (){
                                        @Override
                                        public void windowClosed(java.awt.event.WindowEvent e){
                                            doAction();
                                        }
                        });
                        formStop.setVisible(true);
                        break;
                    case SEGMENT:
                        Node originNode = null;
                        Node destinationNode = null;
                        for (int i = 0; i < controller.getListSegment().size(); i++)
                        {
                            String nameSegment = controller.getListSegment().get(i).getOriginNode().getName() + " " + controller.getListSegment().get(i).getDestinationNode().getName();
                            if (nameSegment.equals(lstToolItems.getSelectedValue().toString()))
                            {
                                originNode = controller.getListSegment().get(i).getOriginNode();
                                destinationNode = controller.getListSegment().get(i).getDestinationNode();
                                break;
                            }
                        }
                        ModifySegment formSegment = new ModifySegment(controller, originNode, destinationNode);
                        formSegment.addWindowListener(new java.awt.event.WindowAdapter (){
                                        @Override
                                        public void windowClosed(java.awt.event.WindowEvent e){
                                            doAction();
                                        }
                        });
                        formSegment.setVisible(true);
                        break;
                    case TRIP:
                        String selectedTrip = lstToolItems.getSelectedValue().toString();
                        Trip trip = new Trip();
                        for (int i = 0; i < controller.getListTrip().size(); i++)
                        {
                            if (controller.getListTrip().get(i).getName().equalsIgnoreCase(selectedTrip))
                            {
                                trip = controller.getListTrip().get(i);
                            }
                        }
                        ModifyTrip formTrip = new ModifyTrip(controller,trip);
                        formTrip.addWindowListener(new java.awt.event.WindowAdapter (){
                                @Override
                                public void windowClosed(java.awt.event.WindowEvent e){
                                    updateListTrip();
                                    doAction();
                                }
                            });
                        formTrip.setVisible(true);
                        break;
                    case VEHICULE:
                        String selectedVehiculeAndTrip = lstToolItems.getSelectedValue().toString();
                        String selectedVehicule = selectedVehiculeAndTrip.substring(0, selectedVehiculeAndTrip.indexOf('|') - 1);
                        Domain.Vehicule.Vehicule vehicule = null;
                        for (int i = 0; i < controller.getListVehicule().size(); i++)
                        {
                            if (controller.getListVehicule().get(i).getName().equalsIgnoreCase(selectedVehicule))
                            {
                                vehicule = controller.getListVehicule().get(i);
                            }
                        }
                        ModifyVehicule formVehicule = new ModifyVehicule(mainFrame.this.controller, vehicule);
                        formVehicule.addWindowListener(new java.awt.event.WindowAdapter (){
                            @Override
                            public void windowClosed(java.awt.event.WindowEvent e){
                                updateListVehicule();
                                doAction();
                            }
                        });
                        formVehicule.setVisible(true);
                        break;
                    case CLIENT:
                        break;
                    case CLIENT_GENERATOR:
                        if(controller.getListTrip().size() > 0)
                        {
                            for(int i = 0; i < controller.getListClientGenerator().size(); i++)
                            {
                                if (controller.getListClientGenerator().get(i).getName().equalsIgnoreCase(lstToolItems.getSelectedValue().toString()))
                                {
                                    ClientGenerator formClientGenerator = new ClientGenerator(controller, controller.getListClientGenerator().get(i));
                                    formClientGenerator.addWindowListener(new java.awt.event.WindowAdapter (){
                                        @Override
                                        public void windowClosed(java.awt.event.WindowEvent e){
                                            updateListClientGenerator();
                                            doAction();
                                        }
                                    });
                                    formClientGenerator.setVisible(true);
                                }
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(mainFrame.this, "Il n'y a pas de trajets existant pour créer un générateur de vehicule.");
                        }
                        break;
                    case VEHICULE_GENERATOR:
                        String selectedGeneratorAndTrip = lstToolItems.getSelectedValue().toString();
                        String selectedGenerator = selectedGeneratorAndTrip.substring(0, selectedGeneratorAndTrip.indexOf('|') - 1);
                        Domain.Generation.VehiculeGenerator vehiculeGenerator;
                        for (int i = 0; i < controller.getListVehiculeGenerator().size(); i++)
                        {
                            if (controller.getListVehiculeGenerator().get(i).getName().equalsIgnoreCase(selectedGenerator))
                            {
                                vehiculeGenerator = controller.getListVehiculeGenerator().get(i);
                                VehiculeGenerator formVehiculeGenerator = new VehiculeGenerator(mainFrame.this.controller, vehiculeGenerator);
                                formVehiculeGenerator.addWindowListener(new java.awt.event.WindowAdapter (){
                                    @Override
                                    public void windowClosed(java.awt.event.WindowEvent e){
                                        updateListVehiculeGenerator();
                                        doAction();
                                    }
                                });
                                formVehiculeGenerator.setVisible(true);
                            }
                        }
                        break;
                    case CLIENT_PROFILE:
                        
                        for(int i = 0; i < controller.getListClientProfile().size(); i++)
                        {
                            if (controller.getListClientProfile().get(i).getName().equalsIgnoreCase(lstToolItems.getSelectedValue().toString()))
                            {
                                ModifyClientProfile formClientProfile = new ModifyClientProfile(controller, controller.getListClientProfile().get(i));
                                formClientProfile.addWindowListener(new java.awt.event.WindowAdapter (){
                                    @Override
                                    public void windowClosed(java.awt.event.WindowEvent e){
                                        updateListClientProfile();
                                        doAction();
                                    }
                                });
                                formClientProfile.setVisible(true);
                            }
                        }
                        break;
                    default:
                        break;
                }
                } 
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        tlbTools = new javax.swing.JToolBar();
        btnStop = new javax.swing.JButton();
        btnSegment = new javax.swing.JButton();
        btnTrip = new javax.swing.JButton();
        btnVehicule = new javax.swing.JButton();
        btnClientGenerator = new javax.swing.JButton();
        btnVehiculeGenerator = new javax.swing.JButton();
        btnClientProfile = new javax.swing.JButton();
        lblCoordinate = new javax.swing.JLabel();
        tlbSpeed = new javax.swing.JToolBar();
        btnRestart = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();
        btnPause = new javax.swing.JButton();
        btnAccelerate = new javax.swing.JButton();
        btnStopSimu = new javax.swing.JButton();
        pnlBackground = new javax.swing.JPanel(new BorderLayout());
        pnlTool = new javax.swing.JPanel();
        lblToolName = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        scrollPaneTool = new javax.swing.JScrollPane();
        lstToolItems = new javax.swing.JList();
        lblTime = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemNew = new javax.swing.JMenuItem();
        menuItemOpen = new javax.swing.JMenuItem();
        menuItemSave = new javax.swing.JMenuItem();
        menuItemSaveAs = new javax.swing.JMenuItem();
        menuItemQuit = new javax.swing.JMenuItem();
        menuItemImg = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuItemCancel = new javax.swing.JMenuItem();
        menuItemRedo = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuItemHelp = new javax.swing.JMenuItem();
        menuItemAbout = new javax.swing.JMenuItem();

        zp = new ZoomPanel(1.0);				
        JScrollPane pnlScroll = new JScrollPane(zp);
        pnlScroll.setViewportView(zp);
        pnlBackground.add(pnlScroll, BorderLayout.CENTER);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SimulatHEURE");
        setFont(new java.awt.Font("Chaparral Pro", 0, 10)); // NOI18N
        setForeground(java.awt.Color.darkGray);

        tlbTools.setBackground(new java.awt.Color(102, 102, 255));
        tlbTools.setOrientation(javax.swing.SwingConstants.VERTICAL);
        tlbTools.setRollover(true);
        tlbTools.setFloatable(false);		
        tlbSpeed.setPreferredSize(new java.awt.Dimension(150, 23));		
        tlbSpeed.setFloatable(false);

        btnStop.setText("Arrêt");
        btnStop.setFocusable(false);
        btnStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStop.setPreferredSize(new java.awt.Dimension(59, 59));
        btnStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        tlbTools.add(btnStop);

        btnSegment.setText("Segment");
        btnSegment.setFocusable(false);
        btnSegment.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSegment.setName(""); // NOI18N
        btnSegment.setPreferredSize(new java.awt.Dimension(59, 59));
        btnSegment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSegment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSegmentActionPerformed(evt);
            }
        });
        tlbTools.add(btnSegment);

        btnTrip.setText("Trajet");
        btnTrip.setFocusable(false);
        btnTrip.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTrip.setPreferredSize(new java.awt.Dimension(59, 59));
        btnTrip.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTripActionPerformed(evt);
            }
        });
        tlbTools.add(btnTrip);

        btnVehicule.setText("Véhicule");
        btnVehicule.setFocusable(false);
        btnVehicule.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVehicule.setPreferredSize(new java.awt.Dimension(59, 59));
        btnVehicule.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVehicule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVehiculeActionPerformed(evt);
            }
        });
        tlbTools.add(btnVehicule);


        btnClientGenerator.setText("Générateur de clients");
        btnClientGenerator.setFocusable(false);
        btnClientGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClientGenerator.setMaximumSize(new java.awt.Dimension(69, 21));
        btnClientGenerator.setMinimumSize(new java.awt.Dimension(69, 21));
        btnClientGenerator.setPreferredSize(new java.awt.Dimension(69, 59));
        btnClientGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClientGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientGeneratorActionPerformed(evt);
            }
        });
        tlbTools.add(btnClientGenerator);

        btnVehiculeGenerator.setText("Générateur de véhicules");
        btnVehiculeGenerator.setFocusable(false);
        btnVehiculeGenerator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVehiculeGenerator.setMaximumSize(new java.awt.Dimension(69, 21));
        btnVehiculeGenerator.setMinimumSize(new java.awt.Dimension(69, 21));
        btnVehiculeGenerator.setPreferredSize(new java.awt.Dimension(69, 59));
        btnVehiculeGenerator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVehiculeGenerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVehiculeGeneratorActionPerformed(evt);
            }
        });
        tlbTools.add(btnVehiculeGenerator);

        btnClientProfile.setText("Profil client");
        btnClientProfile.setFocusable(false);
        btnClientProfile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClientProfile.setPreferredSize(new java.awt.Dimension(59, 59));
        btnClientProfile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClientProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientProfileActionPerformed(evt);
            }
        });
        tlbTools.add(btnClientProfile);

        lblCoordinate.setText("Coordonnées: Latitdue X Longitude Y");

        tlbSpeed.setRollover(true);
        tlbSpeed.setPreferredSize(new java.awt.Dimension(100, 23));

        btnRestart.setText("Recommencer");
        btnRestart.setFocusable(false);
        btnRestart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRestart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRestart.addActionListener(new java.awt.event.ActionListener() {		
            public void actionPerformed(java.awt.event.ActionEvent evt) {		
                btnRestartActionPerformed(evt);		
            }		
        });
        tlbSpeed.add(btnRestart);

        btnStart.setText("Démarrer");
        btnStart.setFocusable(false);
        btnStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tlbSpeed.add(btnStart);

        btnPause.setText("Pause");
        btnPause.setFocusable(false);
        btnPause.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPause.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPause.addActionListener(new java.awt.event.ActionListener() {		
            public void actionPerformed(java.awt.event.ActionEvent evt) {		
                btnPauseActionPerformed(evt);		
            }		
        });
        tlbSpeed.add(btnPause);

        btnAccelerate.setText("Accélérer");
        btnAccelerate.setFocusable(false);
        btnAccelerate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAccelerate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAccelerate.addActionListener(new java.awt.event.ActionListener() {		
            public void actionPerformed(java.awt.event.ActionEvent evt) {		
                btnAccelerateActionPerformed(evt);		
            }		
        });
        tlbSpeed.add(btnAccelerate);

        btnStopSimu.setText("Arrêter");
        btnStopSimu.setFocusable(false);
        btnStopSimu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStopSimu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tlbSpeed.add(btnStopSimu);

        btnStopSimu.addActionListener(new java.awt.event.ActionListener() {		
            public void actionPerformed(java.awt.event.ActionEvent evt) {		     
                btnStopSimuActionPerformed(evt);		
            }		       
        });

        pnlTool.setBackground(new java.awt.Color(102, 102, 102));
        pnlTool.setMaximumSize(new java.awt.Dimension(275, 150));
        pnlTool.setMinimumSize(new java.awt.Dimension(275, 150));
        pnlTool.setName(""); // NOI18N
        pnlTool.setPreferredSize(new java.awt.Dimension(275, 150));
        pnlTool.setRequestFocusEnabled(false);

        lblToolName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnAdd.setText("Ajouter");

        scrollPaneTool.setViewportView(lstToolItems);

        javax.swing.GroupLayout pnlToolLayout = new javax.swing.GroupLayout(pnlTool);
        pnlTool.setLayout(pnlToolLayout);
        pnlToolLayout.setHorizontalGroup(
            pnlToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlToolLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(pnlToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlToolLayout.createSequentialGroup()
                        .addComponent(lblToolName, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlToolLayout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addGap(99, 99, 99))))
            .addComponent(scrollPaneTool, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlToolLayout.setVerticalGroup(
            pnlToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlToolLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(lblToolName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scrollPaneTool, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        //jLabel1.setText("Heure: -");

        tickEvent = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {		
                if(!mainFrame.this.controller.getIsSimuationPaused())		
                {		
                    mainFrame.this.controller.updateSimulation();		
                    lblTime.setText("Heure: " + controller.getCurrentTime().getTimeStringNoSecond());		
                    mainFrame.this.updateListVehicule();		
                    mainFrame.this.zp.repaint();		
                }		
            }		
        };		
        //lblTime.setText("Heure: -");
        
        menuFile.setText("Fichier");
        menuFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileActionPerformed(evt);
            }
        });

        menuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuItemNew.setText("Nouveau");
        menuFile.add(menuItemNew);

        menuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuItemOpen.setText("Ouvrir");
        menuFile.add(menuItemOpen);
        
        menuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemOpenActionPerformed(evt);
            }
        });
        
        menuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuItemSave.setText("Sauvegarder");
        menuFile.add(menuItemSave);

        menuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSaveActionPerformed(evt);
            }
        });
        
        menuItemSaveAs.setText("Sauvegarder en tant que");
        menuFile.add(menuItemSaveAs);
        
        
        menuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSaveAsActionPerformed(evt);
            }
        });

        menuItemQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.CTRL_MASK));
        menuItemQuit.setText("Quitter");
        menuItemQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemQuitActionPerformed(evt);
            }
        });
        menuFile.add(menuItemQuit);

        menuItemImg.setText("Importer une image");
        menuItemImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemImgActionPerformed(evt);
            }
        });
        menuFile.add(menuItemImg);

        menuBar.add(menuFile);

        menuEdit.setText("Édition");

        menuItemCancel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        menuItemCancel.setText("Annuler");
        menuEdit.add(menuItemCancel);
        menuItemCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCancelActionPerformed(evt);
            }
        });

        menuItemRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        menuItemRedo.setText("Refaire");
        menuItemRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRedoActionPerformed(evt);
            }
        });
        menuEdit.add(menuItemRedo);

        menuBar.add(menuEdit);

        menuHelp.setText("?");

        menuItemHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        menuItemHelp.setText("Aide");
        menuItemHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemHelpActionPerformed(evt);
            }
        });
        menuHelp.add(menuItemHelp);

        menuItemAbout.setText("À propos");
        menuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuItemAbout);

        menuBar.add(menuHelp);

        setJMenuBar(menuBar);
        
        btnStart.addActionListener(new java.awt.event.ActionListener() {		
            public void actionPerformed(java.awt.event.ActionEvent evt) {		
                btnStartActionPerformed(evt);		
            }		
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tlbTools, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlTool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(402, 402, 402)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTime)
                                    .addComponent(lblCoordinate)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(315, 315, 315)
                                .addComponent(tlbSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(546, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tlbSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lblTime)
                .addGap(9, 9, 9)
                .addComponent(lblCoordinate))
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTool, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                .addGap(74, 74, 74))
            .addComponent(tlbTools, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
        );

        btnAdd.addActionListener(new java.awt.event.ActionListener() {		
            public void actionPerformed(java.awt.event.ActionEvent evt) {		
                btnAddActionPerformed(evt);		
            }		
        });
        
        pack();
    }
    
    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {
        lblToolName.setText("Arrêt");
        lblToolName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setVisible(false);
        lstToolItems.setVisible(true);
        updateListStop();
        scrollPaneTool.setVisible(true);
        
        this.controller.setCurrentTool(Tool.STOP);
    }

    private void menuItemRedoActionPerformed(java.awt.event.ActionEvent evt) 
    {
        Redo();
        switch(controller.getCurrentTool())
        {
            case STOP:
                updateListStop();
                break;
            case SEGMENT:
                updateListSegment();
                break;
            case TRIP:
                updateListTrip();
                break;
            case VEHICULE:
                updateListVehicule();
                break;
            case CLIENT:
                updateListClient();
                break;
            case VEHICULE_GENERATOR:
                updateListVehiculeGenerator();
                break;
            case CLIENT_GENERATOR:
                updateListClientGenerator();
                break;
            case CLIENT_PROFILE:
                updateListClientProfile();
                break;
        }
    }
    
    private void menuItemCancelActionPerformed(java.awt.event.ActionEvent evt) 
    {
        Undo();
        switch(controller.getCurrentTool())
        {
            case STOP:
                updateListStop();
                break;
            case SEGMENT:
                updateListSegment();
                break;
            case TRIP:
                updateListTrip();
                break;
            case VEHICULE:
                updateListVehicule();
                break;
            case CLIENT:
                updateListClient();
                break;
            case VEHICULE_GENERATOR:
                updateListVehiculeGenerator();
                break;
            case CLIENT_GENERATOR:
                updateListClientGenerator();
                break;
            case CLIENT_PROFILE:
                updateListClientProfile();
                break;
        }
    }

    private void menuItemQuitActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void menuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {
        About aboutFrame = new About();
        aboutFrame.setVisible(true);
    }

    private void menuItemHelpActionPerformed(java.awt.event.ActionEvent evt) {
        Help helpFrame = new Help();
        helpFrame.setVisible(true);
    }
    
    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {
        if(this.controller.getIsSimuationPaused()){
            if (controller.getIsSimuationStarted())
            {
                this.controller.setIsSimulationPaused(false);
                this.controller.SetSpeedMultiplier(1);
                ticker.start();
            }
        }else{
            if(!controller.getIsSimuationStarted())
            {
                if(this.controller.getListVehicule().size() > 0 || this.controller.getListVehiculeGenerator().size()>0)
                {
                    StartSimulation form = new StartSimulation(this.controller);
                    form.addWindowListener(new java.awt.event.WindowAdapter (){
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e){
                            if (controller.getIsSimuationStarted())
                            {
                                mainFrame.this.controller.play();
                                ticker = new javax.swing.Timer(1000/30 ,tickEvent);
                                ticker.setRepeats(true);
                                ticker.start();
                            }
                        }
                    });
                    form.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(this, "Il n'y a pas de véhicules existants à simuler.");
                }
            }else
            {
                this.controller.SetSpeedMultiplier(1);
            }
        }
    }
    private void btnPauseActionPerformed(java.awt.event.ActionEvent evt) {
        if(this.controller.getIsSimuationStarted())
        {
            controller.pause();
        }
    }
    private void btnAccelerateActionPerformed(java.awt.event.ActionEvent evt) {
        if(this.controller.getIsSimuationStarted())
        {
            controller.SetSpeedMultiplier(controller.getSpeedMultiplier() * 2);
        }
    }
    private void btnRestartActionPerformed(java.awt.event.ActionEvent evt) {
        if(this.controller.getIsSimuationStarted())
        {
            this.stopSimulation();
            controller.play();
            ticker.start();
        }
    }
    
    private void btnStopSimuActionPerformed(java.awt.event.ActionEvent evt) {
        if(this.controller.getIsSimuationStarted())
        {
            this.stopSimulation();
            lblTime.setText("");
        }
    }
    
    private void btnClientProfileActionPerformed(java.awt.event.ActionEvent evt) {
        lblToolName.setText("Profil client");
        lblToolName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setVisible(true);
        scrollPaneTool.setVisible(true);
        updateListClientProfile();
        controller.setCurrentTool(Tool.CLIENT_PROFILE);
    }

    private void btnSegmentActionPerformed(java.awt.event.ActionEvent evt) {
        lblToolName.setText("Segment");
        lblToolName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setVisible(false);
        lstToolItems.setVisible(true);
        updateListSegment();
        scrollPaneTool.setVisible(true);
        
        controller.setCurrentTool(Tool.SEGMENT);
    }

    private void btnTripActionPerformed(java.awt.event.ActionEvent evt) {
        lblToolName.setText("Trajet");
        lblToolName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setVisible(true);
        lstToolItems.setVisible(true);
        updateListTrip();
        scrollPaneTool.setVisible(true);
        
        controller.setCurrentTool(Tool.TRIP);
    }

    private void btnVehiculeActionPerformed(java.awt.event.ActionEvent evt) {
        lblToolName.setText("Véhicule");
        lblToolName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setVisible(true);
        lstToolItems.setVisible(true);
        updateListVehicule();
        scrollPaneTool.setVisible(true);
        
        controller.setCurrentTool(Tool.VEHICULE);
    }

    private void btnClientGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        lblToolName.setText("Générateur de clients");
        lblToolName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setVisible(true);
        lstToolItems.setVisible(true);
        updateListClientGenerator();
        scrollPaneTool.setVisible(true);
        
        controller.setCurrentTool(Tool.CLIENT_GENERATOR);
    }

    private void btnVehiculeGeneratorActionPerformed(java.awt.event.ActionEvent evt) {
        lblToolName.setText("Générateur de véhicules");
        lblToolName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setVisible(true);
        lstToolItems.setVisible(true);
        updateListVehiculeGenerator();
        scrollPaneTool.setVisible(true);
        
        controller.setCurrentTool(Tool.VEHICULE_GENERATOR);
    }

    private void menuFileActionPerformed(java.awt.event.ActionEvent evt) {
        
    }

    private void menuItemImgActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JFileChooser jfc;
        jfc = new javax.swing.JFileChooser();     
        int result = jfc.showOpenDialog(this);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                backgroundFile = jfc.getSelectedFile();
            }
    }
    
    private void menuItemOpenActionPerformed(java.awt.event.ActionEvent evt) 
    {
        if((controller.getIsSimuationStarted() && this.controller.getIsSimuationPaused()) || !controller.getIsSimuationStarted())
        {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
            {
                File file = fileChooser.getSelectedFile();
                String path = file.getPath();
                String hggh = path.substring(path.length() - 4, path.length());
                if(!file.exists())
                    JOptionPane.showMessageDialog(this, "Le fichier n'existe pas.");
                else if(!path.substring(path.length() - 4, path.length()).equals(".ser"))
                    JOptionPane.showMessageDialog(this, "Le fichier doit avoir l'extension '.ser'.");
                else                
                    loadFile(path);
                
            }
        }
    }
            
    private void menuItemSaveAsActionPerformed(java.awt.event.ActionEvent evt) 
    {
        if((controller.getIsSimuationStarted() && this.controller.getIsSimuationPaused()) || !controller.getIsSimuationStarted())
        {
            saveDialog();
        }        
    }
    
    private void menuItemSaveActionPerformed(java.awt.event.ActionEvent evt) 
    {
        if((controller.getIsSimuationStarted() && this.controller.getIsSimuationPaused()) || !controller.getIsSimuationStarted())
        {
            if(controller.getCurrentFilePath() == null)
            {
                saveDialog();
            }
            else
            {
                controller.saveFile(controller.getCurrentFilePath());
            }
        }        
    }
    
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        switch(controller.getCurrentTool())
        {
            case STOP:
                break;
            case SEGMENT:
                break;
            case TRIP:
                if(this.controller.getListSegment().size() > 0)
                {
                    ModifyTrip form = new ModifyTrip(this.controller);
                    form.addWindowListener(new java.awt.event.WindowAdapter (){
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e){
                            updateListTrip();
                            doAction();
                        }
                    });
                    form.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(this, "Il n'y a pas de segments existant pour créer un trajet.");
                }
                break;
            case VEHICULE:
                if(this.controller.getListTrip().size() > 0)
                {
                    ModifyVehicule formVehicule = new ModifyVehicule(this.controller);
                    formVehicule.addWindowListener(new java.awt.event.WindowAdapter (){
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e){
                            updateListVehicule();
                            doAction();
                        }
                    });
                    formVehicule.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(this, "Il n'y a pas de trajets existant pour créer un vehicule.");
                }
                break;
            case CLIENT:
                break;
            case CLIENT_GENERATOR:
                if(this.controller.getListClientProfile().size() > 0)
                {
                    ClientGenerator formClientGenerator = new ClientGenerator(controller, null);
                    formClientGenerator.addWindowListener(new java.awt.event.WindowAdapter (){
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e){
                            updateListClientGenerator();
                            doAction();
                        }
                    });
                    formClientGenerator.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Il n'y a pas de profils de clients existant pour créer un générateur de clients.");
                }
                break;
            case VEHICULE_GENERATOR:
                if(this.controller.getListTrip().size() > 0)
                {
                    VehiculeGenerator formVehiculeGenerator = new VehiculeGenerator(this.controller);
                    formVehiculeGenerator.addWindowListener(new java.awt.event.WindowAdapter (){
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e){
                            updateListVehiculeGenerator();
                            doAction();
                        }
                    });
                    formVehiculeGenerator.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(this, "Il n'y a pas de trajets existant pour créer un générateur de véhicules.");
                }
                break;
            case CLIENT_PROFILE:
                if(this.controller.getListTrip().size() > 0)
                {
                    ModifyClientProfile formClientProfile = new ModifyClientProfile(controller);
                    formClientProfile.addWindowListener(new java.awt.event.WindowAdapter (){
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e){
                            updateListClientProfile();
                            doAction();
                        }
                    });
                    formClientProfile.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Il n'y a pas de trajets existant pour créer un profil de client.");
                }
                break;
            default:
                break;
        }
    }
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainFrame().setVisible(true);
            }
        });
    }
    
    public class ZoomPanel extends JPanel{ 
        protected double zoom; 
        public ZoomPanel(double initialZoom) { 
            super(new GridLayout()); 
            zoom = initialZoom; 
        } 
        @Override
        public void paint(Graphics g) { 
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g; 
            AffineTransform backup = g2.getTransform(); 
            g2.scale(zoom, zoom); 
            super.paint(g); 
            try {
                g2.drawImage(ImageIO.read(backgroundFile), 0, 0, null);
            } catch (IOException ex) {
                Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(lines.size() == 4) 
            {
                g2.setColor(java.awt.Color.BLACK);
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(lines.get(0), lines.get(1), lines.get(2), lines.get(3));
            }
            
            for (int i = 0; i < controller.getListSegment().size(); i++) 
            {
                g2.setColor(java.awt.Color.BLACK);
                g2.setStroke(new BasicStroke(3));
                g2.drawLine((int)controller.getListSegment().get(i).getOriginNode().getGeographicPosition().getXPosition(), 
                            (int)controller.getListSegment().get(i).getOriginNode().getGeographicPosition().getYPosition(), 
                            (int)controller.getListSegment().get(i).getDestinationNode().getGeographicPosition().getXPosition(), 
                            (int)controller.getListSegment().get(i).getDestinationNode().getGeographicPosition().getYPosition());
            }
            
            for(int i = 0; i < controller.getListVehicule().size(); i++)
            {
                int width = controller.getListVehicule().get(i).getWidth();
                g2.setColor(Color.BLUE);
                g2.fillOval((int)controller.getListVehicule().get(i).getCurrentPosition().getGeographicPosition().getXPosition() - (width /2), 
                            (int)controller.getListVehicule().get(i).getCurrentPosition().getGeographicPosition().getYPosition() - (width /2), 
                            width, width);
                int clientNumber = controller.getListVehicule().get(i).getInboardClients().size();
                if(clientNumber > 0)
                {
                    int x = Math.round(controller.getListVehicule().get(i).getGeographicPosition().getXPosition()) - (width/4);
                    int y = Math.round(controller.getListVehicule().get(i).getGeographicPosition().getYPosition()) + (width/3);
                    
                    if(clientNumber >=10)
                        x = Math.round(controller.getListNode().get(i).getGeographicPosition().getXPosition() - (width/3));
                    
                    g2.setColor(Color.white);
                    g2.setFont(g.getFont().deriveFont(g.getFont().getSize() * 0.9F));//Reduce font size
                    g2.drawChars(String.valueOf(clientNumber).toCharArray(), 0, String.valueOf(clientNumber).length(), x, y);
                }
            }
            
            for (int i = 0; i < controller.getListNode().size(); i++) 
            {
                int diameter = controller.getListNode().get(i).getDiameter();
                g2.setColor(java.awt.Color.BLACK);
                g2.fillOval((int)controller.getListNode().get(i).getGeographicPosition().getXPosition() - (diameter/2), 
                            (int)controller.getListNode().get(i).getGeographicPosition().getYPosition() - (diameter/2),
                            diameter, diameter);
                if(controller.getListNode().get(i) instanceof Domain.Node.Stop)
                {
                    int clientNumber = ((Domain.Node.Stop)controller.getListNode().get(i)).getClients().size();
                    if(clientNumber > 0)
                    {
                        int x = Math.round(controller.getListNode().get(i).getGeographicPosition().getXPosition()) - (diameter/4);
                        int y = Math.round(controller.getListNode().get(i).getGeographicPosition().getYPosition()) + (diameter/4);
                        if(clientNumber >=10)
                            x = Math.round(controller.getListNode().get(i).getGeographicPosition().getXPosition() - (diameter/3));
                        
                        g2.setColor(Color.white);
                        g2.setFont(g.getFont().deriveFont(g.getFont().getSize() * 0.9F));//Reduce font size
                        g2.drawChars(String.valueOf(clientNumber).toCharArray(), 0, String.valueOf(clientNumber).length(), x, y);
                        
                    }
                }
            }
            
            /*for(int i = 0; i < controller.getListVehiculeGenerator().size(); i++)
            {
                int diameter = controller.getListNode().get(i).getDiameter();
                g2.setColor(Color.GRAY);
                g2.fillOval((int)controller.getListVehiculeGenerator().get(i).getSpawnSegment().getOriginNode().getGeographicPosition().getXPosition() - (diameter / 2),
                            (int)controller.getListVehiculeGenerator().get(i).getSpawnSegment().getOriginNode().getGeographicPosition().getYPosition() - (diameter / 2), 
                            diameter, diameter);   
            }*/
            g2.setTransform(backup);
        } 

        @Override
        public Dimension getPreferredSize() { 
            Dimension unzoomed = new Dimension(1082,702);
            Dimension zoomed 
              = new Dimension((int) ((double) unzoomed.width*zoom), 
                              (int) ((double) unzoomed.height*zoom));
            System.out.println(zoomed);
            return zoomed; 
        }
         
        public void setZoom(double newZoom)
        {
            double oldZoom = zoom; 
            if (newZoom != oldZoom) { 
                zoom = newZoom; 
                revalidate(); 
                repaint(); 
            } 
        } 
        public double getZoom() { 
            return zoom; 
        }
    } 
    
    public void handleDrag(JPanel panel){
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                me.translatePoint(me.getComponent().getLocation().x-x, me.getComponent().getLocation().y-y);
                panel.setLocation(me.getX(), me.getY());
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me)
            {
                x = me.getX();
                y = me.getY();
            }
            @Override
            public void mouseReleased(MouseEvent me) {
                xDrag = xDrag + zp.getX();
                yDrag = yDrag + zp.getY();
                zp.setLocation(xDrag, yDrag);
            }
        });
     
    }
    
    public void updateListStop()
    {
        listModel = new DefaultListModel();
        for (int i = 0; i < controller.getListNode().size(); i++)
        {
            listModel.addElement(controller.getListNode().get(i).getName());
        }
        lstToolItems.setModel(listModel);
    }
    
    public void updateListClientProfile()
    {
        listModel = new DefaultListModel();
        for (int i = 0; i < controller.getListClientProfile().size(); i++)
        {
            listModel.addElement(controller.getListClientProfile().get(i).getName());
        }
        lstToolItems.setModel(listModel);
    }
    
    public void updateListSegment()
    {
        listModel = new DefaultListModel();
        for (int i = 0; i < controller.getListSegment().size(); i++)
        {
            listModel.addElement(controller.getListSegment().get(i).getOriginNode().getName() + " " + controller.getListSegment().get(i).getDestinationNode().getName());
        }
        lstToolItems.setModel(listModel);
    }
    
    public void updateListVehicule()
    {
        listModel = new DefaultListModel();
        for (int i = 0; i < controller.getListVehicule().size(); i++)
        {
            listModel.addElement(controller.getListVehicule().get(i).getName() + " | " + controller.getListVehicule().get(i).getTrip().getName());
        }
        lstToolItems.setModel(listModel);
    }
    
    public void updateListTrip()
    {
        listModel = new DefaultListModel();
        for (int i = 0; i < controller.getListTrip().size(); i++)
        {
            listModel.addElement(controller.getListTrip().get(i).getName());
        }
        lstToolItems.setModel(listModel);
    }
    
    public void updateListClient()
    {
        listModel = new DefaultListModel();
        for (int i = 0; i < controller.getListClient().size(); i++)
        {
            listModel.addElement("Client " + i);
        }
        lstToolItems.setModel(listModel);
    }
    
    public void updateListVehiculeGenerator()
    {
        listModel = new DefaultListModel();
        for (int i = 0; i < controller.getListVehiculeGenerator().size(); i++)
        {
            listModel.addElement(controller.getListVehiculeGenerator().get(i).getName() + " | " + controller.getListVehiculeGenerator().get(i).getTrip().getName());
        }
        lstToolItems.setModel(listModel);
    }
    
    public void updateListClientGenerator()
    {
        listModel = new DefaultListModel();
        for (int i = 0; i < controller.getListClientGenerator().size(); i++)
        {
            listModel.addElement(controller.getListClientGenerator().get(i).getName());
        }
        lstToolItems.setModel(listModel);
    }
    
    private void stopSimulation()
    {
        ticker.stop();
        this.loadInitialState();
        this.controller.setIsSimulationStarted(false);
        this.controller.setIsSimulationPaused(false);
        zp.repaint();
    }
    
    private void saveDialog()
    {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) 
        {
            String path = fileChooser.getSelectedFile().getPath();
            if(!path.substring(path.length() - 4, path.length()).equals(".ser"))
                JOptionPane.showMessageDialog(this, "Le fichier doit avoir l'extension '.ser'.");
            else            
            {
                controller.saveFile(path);
                JOptionPane.showMessageDialog(this, "Le fichier a été enregistré avec succès.");
            }
        }
    }
    
    private void loadFile(String _path)
    {
        try
        {
            FileInputStream fileIn = new FileInputStream(_path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            controller = (Simulation) in.readObject();

            in.close();
            fileIn.close();
            controller.deleteIniialSaves();
            controller.setCurrentFilePath(_path);
            controller.setNbActionSaved(0);
        }
        catch(IOException i)
        {
            JOptionPane.showMessageDialog(this, "Il y a eu une erreur lors du chargment, le fichier peut être corrompu.");
            i.printStackTrace();
        }
        catch(ClassNotFoundException c)
        {
           System.out.println(c.getClass() + " class not found");
        } 
    }
    
    private void loadInitialState()
    {
        try
        {
            FileInputStream fileIn = new FileInputStream("N-Team_Simulatheure_Saves/InitialState.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            controller = (Simulation) in.readObject();

            in.close();
            fileIn.close();
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }
        catch(ClassNotFoundException c)
        {
           System.out.println(c.getClass() + " class not found");
        } 
    }
    
    private void Undo()
    {
        if(controller.getNbActionSaved() > 0)
        {
            try
            {
                if(controller.getNbActionSaved() == 1)
                {
                    controller.getListNode().clear();
                }
                else
                {
                    controller.setNbActionSaved(controller.getNbActionSaved() - 1);
                    FileInputStream fileIn = new FileInputStream("N-Team_Simulatheure_Saves/ActionSave" + 
                                                                 controller.getNbActionSaved() + ".ser");
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    controller = (Simulation) in.readObject();

                    in.close();
                    fileIn.close();
                }
            }
            catch(IOException i)
            {
                i.printStackTrace();
            }
            catch(ClassNotFoundException c)
            {
               System.out.println(c.getClass() + " class not found");
            } 
        }
    }
    
    private void Redo()
    {
        if(controller.getNbActionSaved() > 0)
        {
            File file = new File(("N-Team_Simulatheure_Saves/ActionSave" + (controller.getNbActionSaved() + 1) + ".ser"));

            if (file.exists())
            {
                try
                {
                    controller.setNbActionSaved(controller.getNbActionSaved() + 1);
                    FileInputStream fileIn = new FileInputStream("N-Team_Simulatheure_Saves/ActionSave" + 
                                                                 controller.getNbActionSaved() + ".ser");
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    controller = (Simulation) in.readObject();

                    in.close();
                    fileIn.close();
                }
                catch(IOException i)
                {
                    i.printStackTrace();
                }
                catch(ClassNotFoundException c)
                {
                   System.out.println(c.getClass() + " class not found");
                }
            }
        }
    }
    
    private void doAction()
    {
        controller.saveAction();
        //Path path = FileSystems.getDefault().getPath("/j","sa");
        //Files.setAttribute(path, "dos:hidden", true);
    }
    
    private int x;
    private int y;
    private int xDrag = 0;
    private int yDrag = 0;
    private JScrollPane pnlScroll;
    private static List<Integer> lines = new ArrayList<>();
    private static List<Integer> segments = new ArrayList<>();
    private ZoomPanel zp;
    private File backgroundFile = new File("src/Resources/image/grid.jpg");
    private javax.swing.JButton btnAccelerate;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClientGenerator;
    private javax.swing.JButton btnClientProfile;
    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnRestart;
    private javax.swing.JButton btnSegment;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btnStopSimu;
    private javax.swing.JButton btnTrip;
    private javax.swing.JButton btnVehicule;
    private javax.swing.JButton btnVehiculeGenerator;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblCoordinate;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblToolName;
    private javax.swing.JList lstToolItems;
    private DefaultListModel listModel = new DefaultListModel();
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemAbout;
    private javax.swing.JMenuItem menuItemCancel;
    private javax.swing.JMenuItem menuItemHelp;
    private javax.swing.JMenuItem menuItemImg;
    private javax.swing.JMenuItem menuItemNew;
    private javax.swing.JMenuItem menuItemOpen;
    private javax.swing.JMenuItem menuItemQuit;
    private javax.swing.JMenuItem menuItemRedo;
    private javax.swing.JMenuItem menuItemSave;
    private javax.swing.JMenuItem menuItemSaveAs;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlTool;
    private javax.swing.JScrollPane scrollPaneTool;
    private javax.swing.JToolBar tlbSpeed;
    private javax.swing.JToolBar tlbTools;
    private ActionListener tickEvent;
    javax.swing.Timer ticker;
}


