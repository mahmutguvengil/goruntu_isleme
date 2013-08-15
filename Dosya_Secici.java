package yontemler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import yontemler.NewJFrame;



public class Dosya_Secici extends JFrame {
  JFileChooser  chooser = new JFileChooser();
  ImagePreviewer previewer = new ImagePreviewer();
  PreviewPanel  previewPanel = new PreviewPanel();
  public static String yol;

  class PreviewPanel extends JPanel {
    public PreviewPanel() {
      JLabel label = new JLabel("Resim Önizleme", SwingConstants.CENTER);
      setPreferredSize(new Dimension(150,0));
      setBorder(BorderFactory.createEtchedBorder());

      setLayout(new BorderLayout());

      label.setBorder(BorderFactory.createEtchedBorder());
      add(label, BorderLayout.NORTH);
      add(previewer, BorderLayout.CENTER);
    }
  }
  public Dosya_Secici() {
    super("Resim Secici");

    Container contentPane = getContentPane();
    JButton  button = new JButton("Dosya Sec");

    contentPane.setLayout(new FlowLayout());
    contentPane.add(button);

    chooser.setAccessory(previewPanel);

    
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int state = chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        String s = "Ýptal";
        

        if(file != null &&
           state == JFileChooser.APPROVE_OPTION) {
          s = "Dosya Secildi: " + file.getPath();
          yol= file.getPath();
          NewJFrame j=new NewJFrame();
          j.bos=yol;
        }
        JOptionPane.showMessageDialog(null, s);
        
       
        
     
      }
     
    });

   
    chooser.addPropertyChangeListener(
                  new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        if(e.getPropertyName().equals(
          JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
          File f = (File)e.getNewValue();
          String s = f.getPath(), suffix = null;
          int i = s.lastIndexOf('.');

          if(i > 0 && i < s.length() - 1)
            suffix = s.substring(i+1).toLowerCase();

          if(suffix.equals("png") ||
            suffix.equals("jpg"))
            previewer.configure(f);
        }
      }
    });
  }
  
  public static void main(String a[]) {
       
	
    JFrame f = new Dosya_Secici();
    f.setBounds(300, 300, 300, 75);
    f.setVisible(true);
    
    f.setDefaultCloseOperation(
                WindowConstants.DISPOSE_ON_CLOSE);

    f.addWindowListener(new WindowAdapter() {
      public void windowClosed(WindowEvent e) {
        System.exit(0);
      }
    });
    
  }
}
class ImagePreviewer extends JLabel {
	  public void configure(File f) {
	    Dimension size = getSize();
	    Insets insets = getInsets();
	    ImageIcon icon = new ImageIcon(f.getPath());
	    

	    setIcon(new ImageIcon(icon.getImage().getScaledInstance(
	            size.width - insets.left - insets.right,
	            size.height - insets.top - insets.bottom,
	            Image.SCALE_SMOOTH)));
	  }
	}

