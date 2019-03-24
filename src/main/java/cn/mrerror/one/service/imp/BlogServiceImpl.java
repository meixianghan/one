package cn.mrerror.one.service.imp;

import cn.mrerror.one.dao.BlogDao;
import cn.mrerror.one.entity.Blog;
import cn.mrerror.one.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("blogService")
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;

    @Override
    public Blog selectBlog(Blog blog) {
        return blogDao.selectBlog(blog);
    }

    @Override
    public List<Blog> selectBlogAuthor(Blog blog) {
        return blogDao.selectBlogAuthor(blog);
    }

    @Override
    public Blog selectBlogDoubleAuthor(Blog blog) {
        return blogDao.selectBlogDoubleAuthor(blog);
    }

    @Override
    public Blog selectBlogPostCollection(Blog blog) {
        return blogDao.selectBlogPostCollection(blog);
    }

    @Override
    public Blog selectBlogPosts(Blog blog) {
        return blogDao.selectBlogPosts(blog);
    }
}
