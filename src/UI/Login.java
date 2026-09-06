package UI;

import domain.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    //登录主界面
    public void start() {
        System.out.println("游戏的登录界面打开了");

        ArrayList<User> list = new ArrayList<>();
        while (true) {
            System.out.println("游戏的登录界面打开了");
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");

            Scanner sc = new Scanner(System.in);
            String choose = sc.next();

            switch (choose) {
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> {
                    System.out.println("退出");
                    System.exit(0);
                }
                default -> System.out.println("输入错误");
            }
        }
    }

    //登录操作
    public void login(ArrayList<User> list) {
        System.out.println("登录");
    }

    //注册操作
    public void register(ArrayList<User> list) {
        System.out.println("注册");
        User u = new User();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入用户名：");
            String username = sc.next();

            // 长度必须在3 ~ 16位
            if (!checkLen(3, 16, username)) {
                System.out.println("用户名长度必须在3 ~ 16位");
                continue;
            }

            // 只能由字母、数字组成，不能是纯数字
            if (!checkUsername(username)) {
                System.out.println("用户名只能由字母、数字组成，不能是纯数字");
                continue;
            }

            // 用户名唯一
            if (contains(list, username)) {
                System.out.println("用户名已存在");
                continue;
            }

            u.setUsername(username);
            break;
        }


        while (true) {
            System.out.println("请输入密码：");
            String password1 = sc.next();
            System.out.println("请再次输入密码：");
            String password2 = sc.next();
            //密码要求：
            // 两次输入的密码必须一致
            if (!password1.equals(password2)) {
                System.out.println("两次输入的密码不一致");
                continue;

            }

            //长度3 ~ 8位
            if (!checkLen(3, 8, password1)) {
                System.out.println("密码长度必须在3 ~ 8位");
                continue;
            }

            //只能是字母加数字的组合，不能有其他字母
            if (!checkPassword(password1)) {
                System.out.println("密码只能由字母、数字组成，不能有其他字母");
                continue;
            }
            u.setPassword(password1);
            break;
        }

        list.add(u);
        System.out.println("注册成功");
    }

    // 长度必须在minLen ~ maxLen之间
    public boolean checkLen(int minLen, int maxLen, String str) {
        return str.length() >= minLen && str.length() <= maxLen;
    }

    // 用户名只能由字母、数字组成，不能是纯数字
    public int[] getCount(String userinfo) {
        int charCount = 0;
        int numCount = 0;
        int otherCount = 0;

        for (int i = 0; i < userinfo.length(); i++) {
            char c = userinfo.charAt(i);
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                charCount++;
            } else if (c >= '0' && c <= '9') {
                numCount++;
            } else {
                otherCount++;
            }
        }
        return new int[]{charCount, numCount, otherCount};
    }
    public boolean checkUsername(String username) {
        int[] counts = getCount(username);
        return counts[0] > 0 && counts[2] == 0;
    }

    public boolean checkPassword(String password) {
        int[] counts = getCount(password);
        return counts[0] > 0 && counts[1] > 0 && counts[2] == 0;
    }

    // 用户名唯一
    public boolean contains(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }


}





