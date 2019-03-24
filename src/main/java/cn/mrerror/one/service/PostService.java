package cn.mrerror.one.service;

import cn.mrerror.one.entity.Post;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PostService {

    public PageInfo<Post> selectPosts(int currentPage, int pageSize, Post post);
    public List<Post> selectPosts(Post post);
    public List<Post> insertPosts(List<Post> posts);
}
