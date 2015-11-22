
package UI;

import java.awt.BorderLayout;
import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Application.Controller.Tool;
import Domain.Node.Node;
import Domain.Simulation.Simulation;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.JList;
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
                    zp.repaint();
                }
                else
                {
                    lines.removeAll(lines);
                    zp.repaint();
                }
            }
        });
        pnlBackground.addMouseWheelListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseWheelMoved(java.awt.event.MouseWheelEvent e){
               int rotation = e.getWheelRotation();

               if (rotation < 0) {
                    scaleFactor = zp.getZoom() * 1.1;
                } else {
                    scaleFactor = zp.getZoom() / 1.1;
                }  
                
                try { 
                    zp.setZoom(scaleFactor); 
                } catch (PropertyVetoException ex) { 
                    JOptionPane.showMessageDialog 
                        ((Component) e.getSource(), 
                        "Zoom minimal atteint"); 
                }
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
                                }
                            });
                            form.setVisible(true);
                        }
                        break;
                    case TRIP:
                        break;
                    case VEHICULE:
                        controller.addVehicule(null, null, null);
                        updateListVehicule();
                        break;
                    case CLIENT:
                        controller.addClient(null);
                        updateListClient();
                        break;
                    case CLIENT_GENERATOR:
                        controller.addClientGenerator(null);
                        updateListClientGenerator();
                        break;
                    case VEHICULE_GENERATOR:
                        if(controller.getVehiculeGeneratorAtPosition(e.getX(),e.getY()) != null)
                        {
                            VehiculeGenerator form = new VehiculeGenerator(controller,
                                                                           controller.getNodeAtPostion(e.getX(), e.getY()).getGeographicPosition().getXPosition(),
                                                                           controller.getNodeAtPostion(e.getX(), e.getY()).getGeographicPosition().getYPosition());
                            form.setVisible(true);
                        }
                        else
                        {
                            if(controller.getNodeAtPostion(e.getX(), e.getY()) != null)
                            {
                                controller.addVehiculeGenerator(controller.getNodeAtPostion(e.getX(), e.getY()));
                                VehiculeGenerator form = new VehiculeGenerator(controller, 
                                                                               controller.getNodeAtPostion(e.getX(), e.getY()).getGeographicPosition().getXPosition(),
                                                                               controller.getNodeAtPostion(e.getX(), e.getY()).getGeographicPosition().getYPosition());
                                form.setVisible(true);
                            }
                        }
                        updateListVehiculeGenerator();
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
                        formSegment.setVisible(true);
                        break;
                    case TRIP:
                        break;
                    case VEHICULE:
                        break;
                    case CLIENT:
                        break;
                    case CLIENT_GENERATOR:
                        break;
                    case VEHICULE_GENERATOR:
                        break;
                    case CLIENT_PROFILE:
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
        btnClient = new javax.swing.JButton();
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
        

        
        
        //TEST ELISE
        zp = new ZoomPanel(1.0);
        handleDrag(zp);
        pnlBackground.add(zp,BorderLayout.CENTER);
        
        
        
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SimulatHEURE");
        setFont(new java.awt.Font("Chaparral Pro", 0, 10)); // NOI18N
        setForeground(java.awt.Color.darkGray);

        tlbTools.setBackground(new java.awt.Color(102, 102, 255));
        tlbTools.setOrientation(javax.swing.SwingConstants.VERTICAL);
        tlbTools.setRollover(true);

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

        btnClient.setText("Client");
        btnClient.setFocusable(false);
        btnClient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClient.setPreferredSize(new java.awt.Dimension(59, 59));
        btnClient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientActionPerformed(evt);
            }
        });
        tlbTools.add(btnClient);

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
        tlbSpeed.add(btnPause);

        btnAccelerate.setText("Avancer");
        btnAccelerate.setFocusable(false);
        btnAccelerate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAccelerate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tlbSpeed.add(btnAccelerate);

        btnStopSimu.setText("Arrêter");
        btnStopSimu.setFocusable(false);
        btnStopSimu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStopSimu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tlbSpeed.add(btnStopSimu);

        
        

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

        menuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuItemSave.setText("Sauvegarder");
        menuFile.add(menuItemSave);

        menuItemSaveAs.setText("Sauvegarder en tant que");
        menuFile.add(menuItemSaveAs);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tlbTools, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 357, Short.MAX_VALUE)
                        .addComponent(tlbSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(537, 537, 537))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlTool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(402, 402, 402)
                        .addComponent(lblCoordinate)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tlbSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
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
        btnAdd.setVisible(true);
        lstToolItems.setVisible(true);
        updateListStop();
        scrollPaneTool.setVisible(true);
        
        this.controller.setCurrentTool(Tool.STOP);
    }

    private void menuItemRedoActionPerformed(java.awt.event.ActionEvent evt) {
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
        btnAdd.setVisible(true);
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

    private void btnClientActionPerformed(java.awt.event.ActionEvent evt) {
        lblToolName.setText("Client");
        lblToolName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setVisible(true);
        lstToolItems.setVisible(true);
        updateListClient();
        scrollPaneTool.setVisible(true);
        
        controller.setCurrentTool(Tool.CLIENT);
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
    
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        switch(controller.getCurrentTool())
        {
            case STOP:
                break;
            case SEGMENT:
                break;
            case TRIP:               
                ModifyTrip form = new ModifyTrip(this.controller);
                form.setVisible(true);                               
                break;
            case VEHICULE:
                break;
            case CLIENT:
                break;
            case CLIENT_GENERATOR:
                break;
            case VEHICULE_GENERATOR:
                break;
            case CLIENT_PROFILE:  
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
            super(new FlowLayout()); 
            zoom = initialZoom; 
        } 
        @Override
        public void paint(Graphics g) { 
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g; 
            AffineTransform backup = g2.getTransform(); 
            g2.scale(zoom, zoom); 
            //super.paint(g); 
            try {
                g2.drawImage(ImageIO.read(backgroundFile), 0, 0, null);
            } catch (IOException ex) {
                Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (int i = 0; i < controller.getListNode().size(); i++) {
                int diameter = controller.getListNode().get(i).getDiameter();
                g2.setColor(java.awt.Color.BLACK);
                g2.fillOval((int)controller.getListNode().get(i).getGeographicPosition().getXPosition() - (diameter/2), 
                            (int)controller.getListNode().get(i).getGeographicPosition().getYPosition() - (diameter/2),
                            diameter, diameter);
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
            
            for(int i = 0; i < controller.getListVehiculeGenerator().size(); i++)
            {
                int diameter = controller.getListNode().get(i).getDiameter();
                g2.setColor(Color.GRAY);
                g2.fillOval((int)controller.getListVehiculeGenerator().get(i).getSpawnSegment().getOriginNode().getGeographicPosition().getXPosition() - (diameter / 2),
                            (int)controller.getListVehiculeGenerator().get(i).getSpawnSegment().getOriginNode().getGeographicPosition().getYPosition() - (diameter / 2), 
                            diameter, diameter);
                
            }
            
            g2.setTransform(backup);
            //this.setLocation(xScroll, yScroll);
        } 
        @Override
        public boolean isOptimizedDrawingEnabled() { 
            return false; 
        }
        @Override
        public Dimension getPreferredSize() { 
            Dimension unzoomed 
              = getLayout().preferredLayoutSize(this); 
            Dimension zoomed 
              = new Dimension((int) ((double) unzoomed.width*zoom), 
                              (int) ((double) unzoomed.height*zoom));
            return zoomed; 
        }
         
        public void setZoom(double newZoom)
            throws PropertyVetoException { 
            if (newZoom <= 0.0) { 
                throw new PropertyVetoException 
                    ("Zoom minimal atteint", 
                     new PropertyChangeEvent(this, 
                                             "zoom", 
                                             new Double(zoom), 
                                             new Double(newZoom))); 
            } 
            double oldZoom = zoom; 
            if (newZoom != oldZoom) { 
                Dimension oldSize = getPreferredSize(); 
                zoom = newZoom; 
                Dimension newSize = getPreferredSize(); 
                firePropertyChange("zoom", oldZoom, newZoom); 
                firePropertyChange("preferredSize", 
                                   oldSize, newSize); 
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
                xScroll = me.getX();
                yScroll = me.getY();
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me)
            {
                x = me.getX();
                y = me.getY();
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
            listModel.addElement("Profil client " + i);
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
            listModel.addElement("Véhicule " + i);
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
            listModel.addElement("Générateur de véhicules " + i);
        }
        lstToolItems.setModel(listModel);
    }
    
    public void updateListClientGenerator()
    {
        listModel = new DefaultListModel();
        for (int i = 0; i < controller.getListClientGenerator().size(); i++)
        {
            listModel.addElement("Générateur de clients " + i);
        }
        lstToolItems.setModel(listModel);
    }
 
    private int x;
    private int y;
    private int xScroll;
    private int yScroll;
    private static List<Integer> lines = new ArrayList<>();
    private static List<Integer> segments = new ArrayList<>();
    private ZoomPanel zp;
    private File backgroundFile = new File("src/Resources/image/grid.jpg");
    private javax.swing.JButton btnAccelerate;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClient;
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
    private javax.swing.JLabel lblCoordinate;
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
}


