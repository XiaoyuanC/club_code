package club.server.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("club_member")
public class ClubMemberEntity {
  @TableId(type = IdType.ASSIGN_UUID)
  private String id;
  private String studentNo;
  private String clubCode;
  private String post;
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createDate;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateDate;
}
