
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.net.URL;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
public class Inode extends JPanel
                      implements TreeSelectionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JEditorPane textPane;
    private JTree tree;
    private URL helpURL;
    private static boolean DEBUG = false;

    
   private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";
    
    private static boolean useSystemLookAndFeel = false;

    public Inode() {

    	super(new GridLayout(1,0));
    	
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("Root");
        createNodes(top);

        
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        
        tree.addTreeSelectionListener(this);

       if (playWithLineStyle) {
            System.out.println("line style = " + lineStyle);
            tree.putClientProperty("JTree.lineStyle", lineStyle);
       
       }
         
        JScrollPane treeView = new JScrollPane(tree);

        
        textPane = new JEditorPane();
        textPane.setEditable(false);
        initHelp();
        JScrollPane textView = new JScrollPane(textPane);

        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(textView);

        Dimension minimumSize = new Dimension(100, 50);
        textView.setMinimumSize(minimumSize);
       treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100); 
        splitPane.setPreferredSize(new Dimension(500, 300));

        
        add(splitPane);
    }

   
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();

        if (node == null) return;

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            FileInfo file = (FileInfo)nodeInfo;
            displayURL(file.fileURL);
            if (DEBUG) {
                System.out.print(file.fileURL + ":  \n    ");
            }
        } else {
            displayURL(helpURL); 
        }
        if (DEBUG) {
            System.out.println(nodeInfo.toString());
        }
    }

    private class FileInfo {
        public String fileName;
        public URL fileURL;

        public FileInfo(String file, String filename) {
            fileName = file;
            fileURL = getClass().getResource(filename);
            if (fileURL == null) {
                System.err.println("Couldn't find file: "
                                   + filename);
            }
        }

        public String toString() {
            return fileName;
        }
    }

    private void initHelp() {
        String s = "root//help.txt";
        helpURL = getClass().getResource(s);
        if (helpURL == null) {
            System.err.println("Couldn't open help file: " + s);
        } else if (DEBUG) {
            System.out.println("Help URL is " + helpURL);
        }

        displayURL(helpURL);
    }

    private void displayURL(URL url) {
        try {
            if (url != null) {
                textPane.setPage(url);
            } else {
		textPane.setText("File Not Found");
                if (DEBUG) {
                    System.out.println("Attempted to display a null URL.");
                }
            }
        } catch (IOException e) {
            System.err.println("Attempted to read a bad URL: " + url);
        }
    }

    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode file = null;

        category = new DefaultMutableTreeNode("Folder1");
        top.add(category);
         File folder=new File("C://Users//HOME//workspace//osjs//bin//root//folder1");
         File[] listOfFiles=folder.listFiles();
         String files;
         for(int i=0;i<listOfFiles.length;i++)
         {
        	 if(listOfFiles[i].isFile())
        	 {
        		 files=listOfFiles[i].getName();
        		 file = new DefaultMutableTreeNode(new FileInfo
     		            ("Folder1->"+files,
     		            "root//folder1//"+files));
              		
        		        category.add(file);
        	 }
        	 
         }
    
        

        category = new DefaultMutableTreeNode("Folder2");
        top.add(category);
        File folder1=new File("C://Users//HOME//workspace//osjs//bin//root//folder2");
        File[] listOfFiles1=folder1.listFiles();
        String files1;
        for(int i=0;i<listOfFiles1.length;i++)
        {
       	 if(listOfFiles1[i].isFile())
       	 {
       		 files1=listOfFiles1[i].getName();
       		 file = new DefaultMutableTreeNode(new FileInfo
    		            ("Folder2->"+files1,
    		            "root//folder2//"+files1));
             		
       		category.add(file);
       	 }
       	 
        }
        
        
        
        File folder2=new File("C://Users//HOME//workspace//osjs//bin//root");
        File[] listOfFiles2=folder2.listFiles();
        String files2;
        for(int i=0;i<listOfFiles2.length;i++)
        {
       	 if(listOfFiles2[i].isFile())
       	 {
       		 files2=listOfFiles2[i].getName();
       		 file = new DefaultMutableTreeNode(new FileInfo
    		            (files2,
    		            "root//"+files2));
             		
       		        top.add(file);
       	 }
       	 
        }
        
        
   

        
        
    }
        
        static void createAndShowGUI() {
        if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        }

    
        JFrame frame = new JFrame("Inode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    
        frame.add(new Inode());

    
        frame.pack();
        frame.setVisible(true);
    }
        public static void main(String[] args) {
        	
            
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                   createAndShowGUI();
                }
            });
        }

}
