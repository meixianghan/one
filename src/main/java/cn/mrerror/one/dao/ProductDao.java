package cn.mrerror.one.dao;

import cn.mrerror.one.entity.Category;
import cn.mrerror.one.entity.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 产品DAO
 */
@Repository()
public interface ProductDao {

    @Select("select * from product")
    @ResultType(Product.class)
    @Results(value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "version", property = "version",javaType = String.class),
            @Result(column = "name", property = "name"),
            @Result(column = "category_id",
                    property = "category",
                    javaType = Category.class,
                    one = @One(select = "cn.com.yinuoc.dao.CategoryDao.selectCategory",
                               fetchType = FetchType.EAGER)),
            @Result(column = "user_id", property = "user_id"),
            @Result(column = "sale_count", property = "sale_count"),
            @Result(column = "comments", property = "comments"),

    })
    public List<Product> selectProduct();

    @Select("<script>select * from product <where>" +
                                                "<trim suffixOverrides=\",\">" +
                                                     "<if test=\"id!=null\">id=#{id},</if>" +
                                                     "<if test=\"name!=null\">name=#{name},</if>" +
                                                 "</trim>" +
                                           "</where></script>")
    public List<Product> selectProductBy(Product product);

    @Select("select * from product where category_id = #{id}")
    public List<Product> selectProductById(int id);

    @Delete("delete from product where id =#{byId}")
    public boolean deleteProduct(@Param("byId") int id);


    @Insert("insert into product(version,name,category_id,user_id,sale_count,comments) values(#{version},#{name},#{category.id},#{user_id},#{sale_count},#{comments})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public boolean insertProduct(Product product);

    @Update("<script>update product <set><if test=\"version!=null\">version=#{version},</if>" +
                                        "<if test=\"name!=null\">name=#{name},</if>" +
                                        "<if test=\"category!=null\">category_id=#{category.id},</if>" +
                                        "<if test=\"user_id!=null\">user_id=#{user_id},</if>" +
                                        "<if test=\"sale_count!=null\">sale_count=#{sale_count},</if>" +
                                        "<if test=\"comments!=null\">comments=#{comments},</if>" +
                                   "</set>" +
                                   "<where>" +
                                       "<if test=\"id!=0\">id=#{id}</if>" +
                                   "</where></script>")
    public boolean updateProduct(Product product);
}