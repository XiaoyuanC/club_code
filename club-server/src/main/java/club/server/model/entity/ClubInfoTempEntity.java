package club.server.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@TableName("club_info_temp")
public class ClubInfoTempEntity {

  private String id;
  @NotBlank
  private String clubCode;
  private String clubPrincipal;
  private String clubDescribe;
  private String mark;
  private String remark;
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createDate;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateDate;

}
