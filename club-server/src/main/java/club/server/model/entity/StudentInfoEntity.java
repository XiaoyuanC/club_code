package club.server.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("student_info")
public class StudentInfoEntity {
  @TableId(type = IdType.INPUT)
  private String studentNo;
  private String name;
  private String sex;
  private String college;
  private String major;
  private String email;
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createDate;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateDate;
}
