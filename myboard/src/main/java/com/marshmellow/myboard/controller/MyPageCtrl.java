package com.marshmellow.myboard.controller;

import com.marshmellow.myboard.dto.UserDto;
import com.marshmellow.myboard.entity.Board;
import com.marshmellow.myboard.entity.Likes;
import com.marshmellow.myboard.entity.Reply;
import com.marshmellow.myboard.entity.User;
import com.marshmellow.myboard.service.BoardService;
import com.marshmellow.myboard.service.LikesService;
import com.marshmellow.myboard.service.RepliesService;
import com.marshmellow.myboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class MyPageCtrl {

    private final UserService userService;
    private final BoardService boardService;
    private final RepliesService repliesService;
    private final LikesService likesService;

    @GetMapping
    public String getUserInfo(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        User user = userService.findByUsername(userDetails);
        UserDto dto = UserDto.from(user);
        model.addAttribute("user", UserDto.from(user));
        model.addAttribute("postCount", boardService.countByUser(user));
        model.addAttribute("commentCount", repliesService.countByUser(user));
        model.addAttribute("likeCount", likesService.countByUser(user));
        return "mypage_info";
    }

    /** 내 글 목록 */
    @GetMapping("/posts")
    public String myPosts(
            @AuthenticationPrincipal UserDetails ud,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable,
            Model model
    ) {
        User user = userService.findByUsername(ud);
        Page<Board> page = boardService.readByAuthor(user, pageable);
        model.addAttribute("boardPage", page);
        return "mypage_posts";  // src/main/resources/templates/mypage_posts.html
    }

    /** 내 댓글 목록 */
    @GetMapping("/comments")
    public String myComments(
            @AuthenticationPrincipal UserDetails ud,
            Model model
    ) {
        User user = userService.findByUsername(ud);
        List<Reply> replies = repliesService.findByUser(user);
        model.addAttribute("replies", replies);
        return "mypage_comments";  // templates/mypage_comments.html
    }

    /** 내 좋아요한 글 목록 */
    @GetMapping("/likes")
    public String myLikes(
            @AuthenticationPrincipal UserDetails ud,
            Model model
    ) {
        User user = userService.findByUsername(ud);
        List<Likes> likes = likesService.findByUser(user);
        model.addAttribute("likes", likes);
        return "mypage_likes";  // templates/mypage_likes.html
    }
}

