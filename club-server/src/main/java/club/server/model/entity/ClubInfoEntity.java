package club.server.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@TableName("club_info")
public class ClubInfoEntity {
  @TableId(type = IdType.INPUT)
  private String clubCode;
  @NotBlank
  private String clubName;
  private long clubPeople = 0;
  private String department;
  private String clubPrincipal;
  private String clubDescribe;
  @TableLogic(delval = "0",value = "1")
  private String clubExit;
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createDate;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateDate;
}
