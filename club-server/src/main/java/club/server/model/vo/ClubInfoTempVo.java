package club.server.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ClubInfoTempVo {

  private String id;
  private String clubCode;
  private String clubPrincipal;
  private String clubDescribe;
  private String clubName;
  private long clubPeople = 0;
  private String department;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;

}
