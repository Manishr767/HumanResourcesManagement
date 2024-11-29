package HumanResMgt;

import javax.swing.*;
import java.awt.*;

public class HumanResourcesMgtprof extends JFrame {
    public HumanResourcesMgtprof(){
        setTitle("Human Resources Management");
        setSize(500,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8,2));
        JLabel idLabel =  new JLabel("ID");add(idLabel);
        JTextField idTxtF = new JTextField(); add(idTxtF);
        JButton loadBtn = new JButton("load pic "); add(loadBtn);
        JLabel imageLabel = new JLabel("No Pic . selected"); add(imageLabel);

        add(new JLabel("name")); JTextField nameTxtF = new JTextField();add(nameTxtF);
        add(new JLabel("ssn")); JTextField ssnTxtF = new JTextField();add(ssnTxtF);
        add(new JLabel("address")); JTextField addressTxtF = new JTextField();add(addressTxtF);
        add(new JLabel("nationality")); JTextField nationalityTxtF = new JTextField();add(nationalityTxtF);
        add(new JLabel("position")); JTextField positionTxtF = new JTextField();add(positionTxtF);

        JButton saveBtn = new JButton("saveBtn");add(saveBtn);
        JButton searchBtn = new JButton("searchBtn");add(searchBtn);

        setVisible(true);

    }

    public static void main(String[] args) {
        HumanResourcesMgtprof hmgt = new HumanResourcesMgtprof();
    }
}
