package com.marshmellow.myboard.dto;

import lombok.Getter;

@Getter
public class LikeResponse {
    private final boolean liked;
    private final int likeCount;
    public LikeResponse(boolean liked, int likeCount) {
        this.liked = liked;
        this.likeCount = likeCount;
    }
}
