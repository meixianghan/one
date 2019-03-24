package cn.mrerror.one.dao;

import cn.mrerror.one.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 帖子仓库
 */
@Repository()
public interface PostDao {

    public List<Post> selectPosts(Post post);

    public int insertPosts(List<Post> posts);

}
