package cn.mrerror.one.service;

import cn.mrerror.one.entity.Product;

import java.util.List;

/**
 * 产品
 */
public interface ProductService {

    public List<Product> selectProduct();
    public List<Product> selectProductBy(Product product);
    public boolean deleteProduct(int id);
    public boolean insertProduct(Product product);
    public boolean updateProduct(Product product);

}