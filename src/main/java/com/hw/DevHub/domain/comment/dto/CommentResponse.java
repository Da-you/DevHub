package com.hw.DevHub.domain.comment.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String profileImagePath;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;

}
