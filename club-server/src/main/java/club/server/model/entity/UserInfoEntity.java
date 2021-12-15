package club.server.model.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
@Data
@TableName("user_info")
public class UserInfoEntity {
  @TableId(type= IdType.ASSIGN_UUID)
  @Null
  private String id;
  private String username;
  @NotBlank
  private String password;
  @TableField(exist = false)
  private String email;
  @TableField(exist = false)
  private String code;
  private Integer type;
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createDate;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateDate;
}
