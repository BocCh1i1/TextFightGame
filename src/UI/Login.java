package UI;

import domain.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {

    private Scanner sc = new Scanner(System.in);
    //登录主界面
    public void start() {

        ArrayList<User> list = new ArrayList<>();



        while (true) {
            System.out.println("游戏的登录界面打开了");
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");


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
        //登录功能：
        //. 键盘录入用户名
        //. 键盘录入密码
        //. 键盘录入验证码
        //. 登录最多重试三次，三次错误账号锁定
        //
        //             **验证要求：**
        //
        //用户名如果未注册提示：用户名未注册，请先注册
        //
        //用户被锁定提示：用户xxx已经锁定，请联系程序员官方客服：XXX-XXXXX
        //
        //验证码错误提示：验证码输入错误，请重新输入，并生成一个新的验证码
        //
        //判断用户名和密码是否正确，有3次机会，满3次账户锁定。



        System.out.println("请输入用户名：");
        String username = sc.next();
        if (!contains(list, username)) {
            System.out.println("用户名未注册，请先注册");
            return;
        }

        int index = findIndex(list, username);
        User u = list.get(index);
        if (!u.isStatus()) {
            System.out.println("用户" + username + "已经锁定，请联系程序员官方客服：XXX-XXXXX");
            return;
        }

        String rightPassword = u.getPassword();

        for (int i = 0; i < 3; i++) {
            System.out.println("请输入密码：");
            String password = sc.next();


            while (true) {
                String rightCode = getCode();
                System.out.println("验证码是：" + rightCode);

                System.out.println("请输入验证码：");
                String code = sc.next();


                if (!code.equals(rightCode)) {
                    System.out.println("验证码输入错误，请重新输入，并生成一个新的验证码");
                } else {
                    break;
                }
            }

            if (password.equals(rightPassword)) {
                System.out.println("登录成功");
                FightingGame fg = new FightingGame();
                fg.gameStart(u.getUsername());
                return;
            } else {
                System.out.println("密码输入错误，请重新输入");
                if (i == 2) {
                    u.setStatus(false);
                    System.out.println("用户" + username + "已经锁定，请联系程序员官方客服：XXX-XXXXX");
                    return;
                } else {
                    System.out.println("还剩" + (2 - i) + "次机会");
                }
            }

        }


    }


    //注册操作
    public void register(ArrayList<User> list) {
        System.out.println("注册");
        User u = new User();

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


    //根据username找索引
    public int findIndex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            if (u.getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
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

    // 用户名只能由字母、数字组成，不能是纯数字
    public boolean checkUsername(String username) {
        int[] counts = getCount(username);
        return counts[0] > 0 && counts[2] == 0;
    }

    // 密码只能由字母、数字组成，不能有其他字母
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

    // 验证码
    public String getCode() {
        //#### 2.3 验证码规则：
        //长度为5
        //由4位大写或者小写字母和1位数字组成，同一个字母可重复
        //比如：aQa1K

        //    验证码的字符集
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
        }

        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            char c = list.get(index);
            sb.append(c);
        }

        sb.append(r.nextInt(10));

        //	数字可以出现在任意位置
        char[] arr = sb.toString().toCharArray();
        int i = r.nextInt(arr.length);
        char temp = arr[i];
        arr[i] = arr[arr.length - 1];
        arr[arr.length - 1] = temp;

        String code = new String(arr);

        return code;
    }

}





