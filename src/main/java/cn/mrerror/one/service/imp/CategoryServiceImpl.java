package cn.mrerror.one.service.imp;

import cn.mrerror.one.dao.CategoryDao;
import cn.mrerror.one.entity.Category;
import cn.mrerror.one.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Category selectCategory(int id) {
        return categoryDao.selectCategory(id);
    }
}
