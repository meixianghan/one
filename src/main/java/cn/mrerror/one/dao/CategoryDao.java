package cn.mrerror.one.dao;

import cn.mrerror.one.entity.Category;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

/**
 * 商品类型Dao
 */
public interface CategoryDao {

    @Select("select * from category where id = #{id}")
    @Results(value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "id",
                    property = "product",
                    many = @Many(select = "cn.com.yinuoc.dao.ProductDao.selectProductById",
                            fetchType = FetchType.LAZY))
    })
    public Category selectCategory(@Param("id") int id);

}