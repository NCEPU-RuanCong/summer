package ruan.cong.summerframework.test.aop;

import java.util.Random;
import ruan.cong.summerframework.beans.context.annotation.Component;
import ruan.cong.summerframework.beans.factory.annotation.Autowired;
import ruan.cong.summerframework.beans.factory.annotation.Value;
import ruan.cong.summerframework.test.domain.User;

//@Component("userService")
public class UserService implements IUserService {

    @Value("${token}")
    private String token;

//    @Autowired
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ruanCong，6666，beijing");
        return "finish!";
    }

    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "token='" + token + '\'' +
                "}【" + user.toString() + "]";
    }
}