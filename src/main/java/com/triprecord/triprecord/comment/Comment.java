package com.triprecord.triprecord.comment;


import com.triprecord.triprecord.global.util.EntityBaseTime;
import com.triprecord.triprecord.record.Record;
import com.triprecord.triprecord.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends EntityBaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record commentedRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User commentedUser;

    @Builder
    public Comment(String commentContent, Record recordId, User userId) {
        this.commentContent = commentContent;
        this.commentedRecord = recordId;
        this.commentedUser = userId;
    }
}
