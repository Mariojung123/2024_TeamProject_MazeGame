// UsersData.java
package login;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class UsersData {
    private ArrayList<User> users;
    private static final String FILE_PATH = "user.txt";

    public UsersData() {
        users = new ArrayList<>();
        loadUserData();
    }

    public void addUser(User newUser) {
        users.add(newUser);
        saveUserData();
    }

    public boolean isIdOverlap(String userId) {
        return users.stream().anyMatch(user -> user.getId().equals(userId));
    }

    public void withdraw(String userId) {
        try {
            User userToRemove = null;

            for (User user : users) {
                if (user.getId().equalsIgnoreCase(userId)) {
                    userToRemove = user;
                    break;
                }
            }

            if (userToRemove != null) {
                users.remove(userToRemove);
                removeUserFromFile(userId);
                saveUserData();
            } else {
                System.out.println("ID에 해당하는 사용자를 찾을 수 없습니다. (입력된 ID: " + userId + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeUserFromFile(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (!userData[0].equals(id)) {
                    writer.write(line + "\n");
                } else {
                    System.out.println("User to remove: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            // 파일이 없을 경우, 처음 실행하는 경우일 수 있으므로 무시
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 임시 파일을 원본 파일로 복사
        try {
            Files.copy(Paths.get(FILE_PATH + ".tmp"), Paths.get(FILE_PATH), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 임시 파일 삭제
        try {
            Files.delete(Paths.get(FILE_PATH + ".tmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean contains(User user) {
        return users.contains(user);
    }

    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 4) {
                    String id = userData[0];
                    String pw = userData[1];
                    String name = userData[2];
                    String nickName = userData[3];
                    User user = new User(id, pw, name, nickName);
                    users.add(user);
                } else {
                    System.out.println("잘못된 형식의 데이터 " + line);
                }
            }
        } catch (FileNotFoundException e) {
            // 파일이 없을 경우, 처음 실행하는 경우일 수 있으므로 무시
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(user.getId() + ",");
                writer.write(user.getPw() + ",");
                writer.write(user.getName() + ",");
                writer.write(user.getnickName() + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}