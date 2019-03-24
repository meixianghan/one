package cn.mrerror.one.service.imp;

import cn.mrerror.one.dao.PostDao;
import cn.mrerror.one.entity.Post;
import cn.mrerror.one.service.PostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("postService")
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    /**
     * 分页查询
     * @param currentPage 第几页
     * @param pageSize 每页数量
     * @param post 条件对象
     * @return 返回PageInfo对象
     */
    @Override
    public PageInfo<Post> selectPosts(int currentPage, int pageSize,Post post) {
        //通过插件设置分页
        PageHelper.startPage(currentPage,pageSize);
        PageInfo<Post> pageInfo = new PageInfo<Post>(postDao.selectPosts(post));
        return pageInfo;
    }

    @Override
    public List<Post> selectPosts(Post post) {
        return postDao.selectPosts(post);
    }

    /**
     * 批量插入
     * @param posts 文档对象集合
     * @return 返回被插入的对象集合，且赋值插入对象的ID
     */
    @Override
    @Transactional
    public List<Post> insertPosts(List<Post> posts) {
        postDao.insertPosts(posts);
        return posts;
    }
}
