package cn.mrerror.one.service.imp;

import cn.mrerror.one.dao.ProductDao;
import cn.mrerror.one.entity.Product;
import cn.mrerror.one.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productSerivce")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> selectProduct() {
        return productDao.selectProduct();
    }

    @Override
    public List<Product> selectProductBy(Product product) {
        return productDao.selectProductBy(product);
    }

    @Transactional
    @Override
    public boolean deleteProduct(int id) {
        return productDao.deleteProduct(id);
    }

    @Transactional
    @Override
    public boolean insertProduct(Product product) {
        return productDao.insertProduct(product);
    }

    @Transactional
    @Override
    public boolean updateProduct(Product product) {
        return productDao.updateProduct(product);
    }
}
