package cn.mrerror.one.service;


import cn.mrerror.one.entity.Blog;

import java.util.List;

/**
 * 博客文章接口
 */
public interface BlogService {
    public Blog selectBlog(Blog blog);
    public List<Blog> selectBlogAuthor(Blog blog);
    public Blog selectBlogDoubleAuthor(Blog blog);
    public Blog selectBlogPostCollection(Blog blog);
    public Blog selectBlogPosts(Blog blog);
}
