package club.server.service.impl;

import club.common.utils.R;
import club.common.utils.StringUtil;
import club.server.dao.StudentInfoDao;
import club.server.model.entity.StudentInfoEntity;
import club.server.model.query.StudentInfoQuery;
import club.server.service.StudentInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/31 22:09
 */
@Service("studentInfoService")
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoDao, StudentInfoEntity> implements StudentInfoService {
    @Override
    public R getStudentInfoByStudentNo(String studentNo) {
        StudentInfoEntity byId = this.getById(studentNo);
        return R.success(byId);
    }

    @Override
    public R queryStudentInfo(StudentInfoQuery query) {
        LambdaQueryWrapper<StudentInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtil.notNullOrEmpty(query.getStudentNo()), StudentInfoEntity::getStudentNo, query.getStudentNo());
        lqw.eq(StringUtil.notNullOrEmpty(query.getSex()), StudentInfoEntity::getSex, query.getSex());
        lqw.like(StringUtil.notNullOrEmpty(query.getName()), StudentInfoEntity::getName, query.getName());
        lqw.like(StringUtil.notNullOrEmpty(query.getCollege()), StudentInfoEntity::getCollege, query.getCollege());
        lqw.like(StringUtil.notNullOrEmpty(query.getMajor()), StudentInfoEntity::getMajor, query.getMajor());
        Page<StudentInfoEntity> page = this.page(query.getIPage(), lqw);
        return R.success(page);
    }
}
