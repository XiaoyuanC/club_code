package club.server.service;

import club.common.utils.R;
import club.server.model.entity.StudentInfoEntity;
import club.server.model.query.StudentInfoQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/31 22:09
 */
public interface StudentInfoService extends IService<StudentInfoEntity> {
    //Obtain student information through student ID
    R getStudentInfoByStudentNo(String studentNo);
    //Obtain student information
    R queryStudentInfo(StudentInfoQuery query);
}
