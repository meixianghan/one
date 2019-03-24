package cn.mrerror.one.dao;

import cn.mrerror.one.entity.Blog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface BlogDao {
    public Blog selectBlog(Blog blog);
    public List<Blog> selectBlogAuthor(Blog blog);
    public Blog selectBlogDoubleAuthor(Blog blog);
    public Blog selectBlogPostCollection(Blog blog);
    public Blog selectBlogPosts(Blog blog);
}
