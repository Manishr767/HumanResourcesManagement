package ImageFromHardDIsk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImageLoader extends JFrame {
    public ImageLoader(){
        setTitle("Image Viwer");
        setSize(400,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JLabel imagelabel = new JLabel("No Image Selected",SwingConstants.CENTER);
        JButton selectBtn = new JButton("Select Image");
        selectBtn.setFont(new Font("Arial",Font.BOLD,25));
        selectBtn.setBackground(Color.cyan);
        add(imagelabel,BorderLayout.CENTER);
        add(selectBtn,BorderLayout.SOUTH);

        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

              JFileChooser fileChooser = new JFileChooser();
              if(JFileChooser.APPROVE_OPTION== fileChooser.showOpenDialog(ImageLoader.this)){
                  File selectedFile = fileChooser.getSelectedFile();
                  ImageIcon imgIcon = new ImageIcon(selectedFile.getAbsolutePath());
                  Image img =imgIcon.getImage().getScaledInstance(imagelabel.getWidth(),imagelabel.getWidth(),Image.SCALE_SMOOTH);
                  imagelabel.setIcon(new ImageIcon(img));

              }

            }
        });
        setVisible(true);


    }

    public static void main(String[] args) {
        ImageLoader Il = new ImageLoader();
    }
}
