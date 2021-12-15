package club.server.service;

import club.common.utils.R;
import club.server.model.entity.ClubActEntity;
import club.server.model.query.ClubActQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 21:56
 */
public interface ClubActService extends IService<ClubActEntity> {
    R addActInfo(ClubActEntity entity);

    R deleteActInfoById(String id);

    R updateActInfo(ClubActEntity entity);

    R getActInfoById(String id);

    R getActInfo(ClubActQuery query);
    R queryActInfo(ClubActQuery query);
}
