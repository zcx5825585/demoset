package org.zcx.feign_client.DAO.file;

import org.springframework.stereotype.Repository;
import org.zcx.feign_client.DAO.UserDAO;
import org.zcx.feign_client.pojo.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImp implements UserDAO {

    public static String filePath = "db";

    @Override
    public void saveUser(List<User> User) {
        try {
            FileOutputStream out = new FileOutputStream(filePath, false);
            OutputStreamWriter osw = new OutputStreamWriter(out, "utf-8");
            PrintWriter p = new PrintWriter(osw);
            User.forEach(one -> p.println(one.toJson()));
            p.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTable() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getSavedUserList() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<User> userList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            while (reader.ready()) {
                String line = reader.readLine();
                userList.add(User.fromJson(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

}
