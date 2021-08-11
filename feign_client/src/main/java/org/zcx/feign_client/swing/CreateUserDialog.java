package org.zcx.feign_client.swing;

import org.zcx.feign_client.SpringUtils;
import org.zcx.feign_client.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreateUserDialog extends JDialog {

    public CreateUserDialog() {
        UserService userService = SpringUtils.getBean("userService");
        CreateUserDialog createUserDialog = this;
        Font font = new Font("宋体",Font.BOLD, 20);
        Font buttonFont = new Font("宋体", Font.BOLD, 20);
        int buttonWidth = 120;
        int buttonHeight = 35;




        this.setTitle("添加账号");

        this.setSize(350, 230);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);

        JLabel nameLabel = new JLabel("姓名:");
        nameLabel.setBounds(10,20,80,25);
        nameLabel.setFont(font);
        panel.add(nameLabel);
        JTextField nameText = new JTextField();
        nameText.setBounds(100,20,165,25);
        nameText.setFont(font);

        panel.add(nameText);

        JLabel usernameLabel = new JLabel("账号:");
        usernameLabel.setBounds(10,50,80,25);
        usernameLabel.setFont(font);
        panel.add(usernameLabel);
        JTextField usernameText = new JTextField();
        usernameText.setBounds(100,50,165,25);
        usernameText.setFont(font);
        panel.add(usernameText);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(10,80,80,25);
        passwordLabel.setFont(font);
        panel.add(passwordLabel);
        JTextField passwordText = new JTextField();
        passwordText.setBounds(100,80,165,25);
        passwordText.setFont(font);
        panel.add(passwordText);

        //保存
        JButton saveButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String username = usernameText.getText();
                String password = passwordText.getText();
                if (username == null || "".equals(username)
                        || password == null || "".equals(password)
                        || name == null || "".equals(name)) {
                    return;
                }
                userService.createUser(name, username, password);
                createUserDialog.dispose();
            }
        });
        saveButton.setText("保存");
        saveButton.setFont(buttonFont);
        saveButton.setBounds(10, 110, buttonWidth, buttonHeight);
        panel.add(saveButton);

        //取消
        JButton cancelButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUserDialog.dispose();
            }
        });
        cancelButton.setText("取消");
        cancelButton.setFont(buttonFont);
        cancelButton.setBounds(150, 110, buttonWidth, buttonHeight);
        panel.add(cancelButton);


        this.setVisible(true);
    }
}
