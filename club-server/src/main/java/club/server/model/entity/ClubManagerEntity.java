package club.server.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("club_manager")
public class ClubManagerEntity {

  private String id;
  private String userId;
  private String clubCode;
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createDate;
}
