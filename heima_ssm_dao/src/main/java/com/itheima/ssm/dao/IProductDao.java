package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IProductDao {
    //查询所有产品
    @Select("select * from product")
    public List<Product> findAll() throws Exception;

    //保存添加
    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    public void save(Product product) throws Exception;

    //通过id查找
    @Select("select * from product where id = #{id}")
    public Product findById(Integer id) throws Exception;
}
