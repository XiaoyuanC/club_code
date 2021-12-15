package club.server.model.vo;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClubActVo {
  private String id;
  private String actName;
  private String actContent;
  private String clubCode;
  private String clubName;
  private String department;
  private String clubPrincipal;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
}
