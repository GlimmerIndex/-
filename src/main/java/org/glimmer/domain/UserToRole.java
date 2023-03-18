package org.glimmer.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("sys_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToRole {
    Long userId;
    Long roleId;
}
