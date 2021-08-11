package org.zcx.feign_client.swing;

import org.springframework.stereotype.Component;
import org.zcx.feign_client.service.UserService;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Component
public class SwingWin {
    @Resource
    private UserService userService;

    public void create() {
        JFrame frame = new JFrame("抢座工具");
        frame.setSize(580, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {

        Font font = new Font("宋体", Font.BOLD, 20);
        Font buttonFont = new Font("宋体", Font.BOLD, 20);
        int buttonWidth = 120;
        int buttonHeight = 35;

        panel.setLayout(null);

//        JTextField userText = new JTextField(20);
//        userText.setBounds(30, 30, 500, 50);
//        userText.setFont(font);
//        panel.add(userText);
//
//        JButton runButton = new JButton(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    String id = null;
//                    if (!"".equals(userText.getText())) {
//                        id = userText.getText();
//                    }
//                    crawlerService.zhihuRun(id);
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//            }
//        });
//        runButton.setText("开始");
//        runButton.setFont(buttonFont);
//        runButton.setBounds(30, 100, 150, 50);
//        panel.add(runButton);

        JButton createUser = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateUserDialog();
            }
        });
        createUser.setText("添加账号");
        createUser.setFont(buttonFont);
        createUser.setBounds(380, 100, buttonWidth, buttonHeight);
        panel.add(createUser);
        //保存到文件
        JButton saveButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userService.saveToFile();
            }
        });
        saveButton.setText("保存信息");
        saveButton.setFont(buttonFont);


        saveButton.setBounds(210, 100, buttonWidth, buttonHeight);
        panel.add(saveButton);

//
//        JButton stopButton = new JButton(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                crawlerService.zhihuStop();
//            }
//        });
//        stopButton.setText("停止");
//        stopButton.setFont(buttonFont);
//        stopButton.setBounds(380, 100, 150, 50);
//        panel.add(stopButton);
//
//        JTextArea console = crawlerService.getConsole();
//        console.setFont(new Font("宋体", Font.BOLD, 20));
//        console.setBounds(30, 180, 500, 200);
//        panel.add(console);
//
//        JScrollPane scroll = new JScrollPane(console);
//        scroll.setBounds(30, 180, 500, 200);
//        panel.add(scroll);
    }

}