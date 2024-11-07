package login;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class UsersData {
    private ArrayList<User> users;
    private static final String FILE_PATH = "경로/파일이름.파일형식";
    private static final String IdTxt = null;

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
            //  System.out.println("withdraw 메서드 호출: " + userId);

            User userToRemove = null;

            for (User user : users) {
                if (user.getId().equalsIgnoreCase(userId)) {
                    userToRemove = user;
                    break;
                }
            }

            if (userToRemove != null) {
                users.remove(userToRemove); // 사용자 목록에서 사용자 제거
                removeUserFromFile(IdTxt); // 파일에서 사용자 제거
                saveUserData(); // 변경된 사용자 목록을 파일에 저장
                // System.out.println("withdraw 메서드 종료");
            } else {
                System.out.println("ID에 해당하는 사용자를 찾을 수 없습니다. (입력된 ID: " + IdTxt + ")");
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

    // 사용자가 users 목록에 포함되어 있는지를 확인
    public boolean contains(User user) {
        return users.contains(user);
    }

    //FILE_PATH에서 사용자 데이터를 읽어와 users 목록을 user 객체로채움
    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 6) {
                    String id = userData[0];
                    String pw = userData[1];
                    String name = userData[2];
                    String nickName = userData[3];
                    String phoneNumber = userData[4];
                    String smsOk = userData[5];
                    User user = new User(id, pw, name, nickName, phoneNumber, smsOk);
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

    // users 목록에서 사용자 데이터를 파일로 씀(기존 내용을 덮어쓰기), 메서드가 호출될때마다 파일을 업데이트함
    private void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(user.getId() + ",");
                writer.write(user.getPw() + ",");
                writer.write(user.getName() + ",");
                writer.write(user.getnickName() + ",");
                writer.write(user.getphoneNumber() + ",");
                writer.write(user.getSmsOk() + "\n");
            }
            System.out.println("User list before writing to file:");
            users.forEach(System.out::println);

            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}