package club.server.model.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("club_act")
public class ClubActEntity {
  @TableId(type = IdType.ASSIGN_UUID)
  private String id;
  private String actName;
  private String actContent;
  private String clubCode;
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createDate;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateDate;
}
