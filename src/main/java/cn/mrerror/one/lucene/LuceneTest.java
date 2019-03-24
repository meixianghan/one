package cn.mrerror.one.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class LuceneTest {

    public static void main(String[] args) {

    }

    public void l3(){
        FSDirectory open = null;
        try {
            open = FSDirectory.open(Paths.get("E:\\lucenestorage"));
            DirectoryReader open1 = DirectoryReader.open(open);
            IndexSearcher indexSearcher = new IndexSearcher(open1);
            StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
            QueryParser content = new QueryParser("content", standardAnalyzer);
            Query apple = content.parse("apple");
            TopDocs search = indexSearcher.search(apple, 100);
            ScoreDoc[] scoreDocs = search.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                Document doc = indexSearcher.doc(scoreDoc.doc);
                System.out.println(doc.get("fullPath"));
                System.out.println(doc.get("id"));
            }
            System.out.println(search.totalHits);
            open1.close();
            open.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void l2(){
        try {
            FSDirectory open = FSDirectory.open(Paths.get("E:\\lucenestorage\\"));
            StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);
            IndexWriter indexWriter = new IndexWriter(open, indexWriterConfig);
            File[] listFIle = new File("E:\\MrError\\").listFiles();
            for (File file:listFIle ){
                Document doc = new Document();
                doc.add(new TextField("id", "1", Field.Store.YES));
                doc.add(new TextField("goodsName", "a apple phone 64G", Field.Store.YES));
                doc.add(new TextField("description", "a apple phoen 64G is very good", Field.Store.YES));
                indexWriter.addDocument(doc);
            }
            indexWriter.close();
            open.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void l1(){
        try {
            //设置在索引存放位置
            Directory dir = FSDirectory.open(Paths.get("E:\\lucenestorage\\"));
            //分词器
            Analyzer analyzer = new StandardAnalyzer();
            //写索引位置
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            //将索引实例与索引目录分词器组织一起
            IndexWriter indexWriter = new IndexWriter(dir, indexWriterConfig);
            //索引文档
            Document indexableFields = new Document();

            //添加字段
            indexableFields.add(new TextField("id", "1", Field.Store.YES));
            indexableFields.add(new TextField("goodsName", "a apple phone 64G", Field.Store.YES));
            indexableFields.add(new TextField("description", "a apple phoen 64G is very good", Field.Store.YES));

            //为索引实力添加document
            indexWriter.addDocument(indexableFields);
            Document indexableFields2 = new Document();

            //添加字段
            indexableFields2.add(new TextField("id", "1", Field.Store.YES));
            indexableFields2.add(new TextField("goodsName", "a apple phone 64G", Field.Store.YES));
            indexableFields2.add(new TextField("description", "a apple phoen 64G is very good", Field.Store.YES));

            //为索引实力添加document
            indexWriter.addDocument(indexableFields);
            Document indexableFields3 = new Document();

            //添加字段
            indexableFields3.add(new TextField("id", "1", Field.Store.YES));
            indexableFields3.add(new TextField("goodsName", "a apple phone 64G", Field.Store.YES));
            indexableFields3.add(new TextField("description", "a apple phoen 64G is very good", Field.Store.YES));

            //为索引实力添加document
            indexWriter.addDocument(indexableFields);
            indexWriter.close();
            dir.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
