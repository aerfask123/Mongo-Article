package com.itcast.article.service;

import com.itcast.article.po.Comment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void testSaveComment() {
        List comments = new ArrayList();
        Random random = new Random();
        String[] users = {"1001", "1002", "1003", "1004", "1005", "1006", "1007", "1008", "1009", "1010"};
        for (int i = 1; i <= 1000000; i++) {
            Comment comment = new Comment();
            String articleId = String.format("%06d", i);
            comment.setArticleid(articleId);
            comment.setContent("测试添加的数据" + i);
            comment.setCreatedatetime(LocalDateTime.now());
            comment.setUserid(users[random.nextInt(users.length)]);

            comment.setNickname("凯撒大帝" + i);
            comment.setState("1");
            comment.setParentid(random.nextInt(500) + "");
            comment.setLikenum(random.nextInt(100, 1000));
            comment.setReplynum(random.nextInt(10, 500));
            comments.add(comment);
            if (i % 5000 == 0) {
                commentService.saveComments(comments);
                comments.clear();
            }
        }
    }

    @Test
    public void testDelete() {
        long count = commentService.deleteComments();
        System.out.println(count);
    }

    @Test
    public void testFindOne() {
        Comment comment = commentService.findCommentById("1");
        System.out.println(comment);
    }

    @Test
    public void testFindList() {
        List<Comment> commentList = commentService.findCommentList();
        System.out.println(commentList);
    }

    @Test
    public void testFindByParentid() {
        Page<Comment> page = commentService.findByParentid("3", 1, 100);
        System.out.println(page.getTotalElements());
        System.out.println(page.getContent());
    }


    @Test
    public void testIncreaseLikeNum() {
        Comment comment = commentService.findCommentById("1");
        System.out.println(comment);
        commentService.increaseLikenum("1");
        comment = commentService.findCommentById("1");
        System.out.println(comment);
    }
}
