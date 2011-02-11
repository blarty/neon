/*
 * Copyright 2005 neon.jini.org project 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package org.jini.projects.neon.ui;

/*
 * NeonAdminFrame.java
 * 
 * Created Tue Apr 12 10:33:44 BST 2005
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.jini.core.lookup.ServiceID;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.id.Uuid;
import net.jini.id.UuidFactory;

import org.jini.projects.neon.service.AgentService;
import org.jini.projects.neon.service.admin.AgentAdmin;
import org.jini.projects.neon.service.admin.AgentServiceAdmin;
import org.jini.projects.neon.service.admin.DomainDescription;
import org.jini.projects.neon.service.admin.NewAgentPanel;

/**
 * 
 * @author calum
 * 
 */

public class NeonAdminFrame extends javax.swing.JFrame {
	DefaultMutableTreeNode root;
	DefaultMutableTreeNode hosts;
	DefaultMutableTreeNode domains;
	AgentService svcOb;
	AgentAdmin svcAdminOb;
	String globalConfigFile;

	
	public NeonAdminFrame(AgentService adminob) {
		this();
		try {
			svcAdminOb = (AgentAdmin) adminob.getAdmin();
			svcOb = adminob;
			getServiceDetails();
			initialiseMenus();
			initDomainViewer();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void getServiceDetails() throws RemoteException {
		DomainDescription[] domDescs = svcAdminOb.getDomains();
		globalConfigFile = svcAdminOb.getGlobalConfigFile();
		addDomains(domDescs);
	}

	private void initBaseTreeComponents() {
		root = new DefaultMutableTreeNode("Neon");
		hosts = new DefaultMutableTreeNode("Hosts");
		domains = new DefaultMutableTreeNode("Domain");
		ImageIcon imic = new ImageIcon(getClass().getResource("/org/jini/projects/neon/service/neonembosssmall_green.jpg"));
		this.setIconImage(imic.getImage());
		root.add(hosts);
		root.add(domains);
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		domainTree.setModel(treeModel);
	}

	private void addDomains(DomainDescription[] descs) {
		for (int i = 0; i < descs.length; i++) {
			DomainDescription d = descs[i];
			DefaultMutableTreeNode domNode = new DefaultMutableTreeNode(d.getName());
			domains.add(domNode);
		}
	}

	public NeonAdminFrame() {
		setTitle("Neon Administration");
		initComponents();
		initBaseTreeComponents();
		
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		desktopPanel = new javax.swing.JPanel();
	
	
		desktop = new javax.swing.JDesktopPane();
	
		domainDetailsScrollPane = new javax.swing.JScrollPane();
		domainTree = new javax.swing.JTree();
	
		menu = new javax.swing.JMenuBar();
		fileMenu = new javax.swing.JMenu();
		closeMenu = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		

		desktopPanel.setLayout(new BorderLayout());
		desktop.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
		//jSplitPane5.setRightComponent(jDesktopPane1);
		domainDetailsScrollPane.setViewportView(domainTree);
		desktop.setPreferredSize(new Dimension(600,400));
		desktopPanel.add(new JScrollPane(desktop), BorderLayout.CENTER);
		getContentPane().add(desktopPanel, java.awt.BorderLayout.CENTER);
		fileMenu.setMnemonic('F');
		fileMenu.setText("File");
		fileMenu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenu1ActionPerformed(evt);
			}
		});

		closeMenu.setMnemonic('C');
		closeMenu.setText("Close");
		fileMenu.add(closeMenu);

		menu.add(fileMenu);

		setJMenuBar(menu);
		
		pack();
	}

	private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	
	private void initialiseMenus(){
	
		fileMenu.setMnemonic('F');
		fileMenu.setText("File");
		fileMenu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenu1ActionPerformed(evt);
			}
		});
		JMenuItem deployAgentMenu = new JMenuItem();
        deployAgentMenu.setMnemonic('A');
        deployAgentMenu.setText("Deploy Agent");
        deployAgentMenu.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                        runDeployAgent();
                };
        });
        
        JMenuItem manageDomainsMenu = new JMenuItem();
        manageDomainsMenu.setMnemonic('D');
        manageDomainsMenu.setText("Manage Domains");
        manageDomainsMenu.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                        runManageDomains();
                };
        });
        
		closeMenu.setMnemonic('C');
		closeMenu.setText("Close");
		fileMenu.add(deployAgentMenu);
		fileMenu.add(manageDomainsMenu);
        fileMenu.add(closeMenu);
		menu.add(fileMenu);
		
		setJMenuBar(menu);
	    
	}
	
	protected void runManageDomains(){
		try {
			JInternalFrame domFrame = new JInternalFrame("Manage Domains",true,true,true,true);
			domFrame.setLayout(new BorderLayout());
			domFrame.add(new DomainDetailsPanel(this.svcAdminOb), BorderLayout.CENTER);
			domFrame.pack();
			desktop.add(domFrame);
			domFrame.setVisible(true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void runDeployAgent() {
           
	        if(agentDeployFrame==null){
                    
                agentDeployFrame = new JInternalFrame("Deploy New Agent",true, true,true,true);                
                agentDeployFrame.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
                agentDeployFrame.getContentPane().setLayout(new BorderLayout());
                agentDeployFrame.getContentPane().add(new NewAgentPanel(svcOb), BorderLayout.CENTER);
                agentDeployFrame.pack();
                desktop.add( agentDeployFrame);
                agentDeployFrame.setVisible(true);
            } else {
                    agentDeployFrame.setVisible(true);
            }
            
        }

        private void initDomainViewer() {

//		JInternalFrame domFrame = new JInternalFrame("Domains in node: ", true, true, true, true);
//		jDesktopPane1.add(domFrame);
//		domFrame.setVisible(true);
		try {
			NeonAgentPanel agentPanel = new NeonAgentPanel("Host",svcAdminOb.getDomains(),svcOb, desktop);
			agentPanel.setPreferredSize(new Dimension(200,200));
			getContentPane().add(agentPanel, BorderLayout.WEST);
//			JTabbedPane newPane = new JTabbedPane();
//                        newPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
//			AgentServiceAdmin asa = (AgentServiceAdmin) svcOb.getAdmin();
//			for (int i=0;i<asa.getDomains().length;i++){
//				newPane.addTab(asa.getDomains()[i].getName(),new JLabel(asa.getDomains()[i].getName()));
//			}
//			getContentPane().add(newPane, BorderLayout.EAST);
		} catch (RemoteException e) {
			// TODO Handle RemoteException
			e.printStackTrace();
		}
		

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new NeonAdminFrame(new MockAgentService()).setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify

	private javax.swing.JDesktopPane desktop;

	private javax.swing.JMenu fileMenu;
	private javax.swing.JMenuBar menu;
	private javax.swing.JMenuItem closeMenu;
	
	private javax.swing.JPanel desktopPanel;

	private javax.swing.JScrollPane domainDetailsScrollPane;
//	private javax.swing.JScrollPane jScrollPane4;
//	private javax.swing.JScrollPane jScrollPane5;
//	private javax.swing.JSplitPane jSplitPane1;
//	private javax.swing.JSplitPane jSplitPane2;
//	private javax.swing.JSplitPane jSplitPane3;
//	private javax.swing.JSplitPane jSplitPane4;
//	private javax.swing.JSplitPane jSplitPane5;
//	private javax.swing.JTable jTable1;
//	private javax.swing.JTable jTable2;
//	private javax.swing.JToolBar jToolBar1;
	private javax.swing.JTree domainTree;
	// End of variables declaration
        private JInternalFrame agentDeployFrame;

}
