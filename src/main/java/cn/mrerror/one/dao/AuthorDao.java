package cn.mrerror.one.dao;

import cn.mrerror.one.entity.Author;
import org.springframework.stereotype.Repository;

@Repository()
public interface AuthorDao {
    public Author selectAuthor(Author author);
}
