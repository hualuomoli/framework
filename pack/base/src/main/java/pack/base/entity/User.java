package pack.base.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class User {

    /** 用户名 */
    private String username;
    /** 昵称 */
    private String nickname;
    /** 性别 */
    private String gender;
    /** 最后修改时间 */
    private Date lastUpdateTime;
    /** 最后登录时间 */
    private Date lastLoginTime;

}
