package domain;

import java.util.Random;

public class User {
    //属性：id、用户名、密码、状态
    private String id;
    private String username;
    private String password;
    private boolean status;

    //无参构造方法
    public User() {
        //调用createId方法生成id
        this.id = createId();
        //修改status的值
        this.status = true;
    }

    //有参构造方法
    public User(String username, String password) {
        this.id = createId();
        this.username = username;
        this.password = password;
        this.status = true;
    }

    //id：
    //用户无法设置，是自动生成的，格式为：yonghu+5位数字的随机数
    public String createId() {
        StringBuilder sb = new StringBuilder("yonghu");
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            int num = r.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
