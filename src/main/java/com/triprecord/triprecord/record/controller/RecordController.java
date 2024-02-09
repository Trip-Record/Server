package com.triprecord.triprecord.record.controller;


import com.triprecord.triprecord.global.util.ResponseMessage;
import com.triprecord.triprecord.record.controller.request.CommentContent;
import com.triprecord.triprecord.record.controller.request.RecordCreateRequest;
import com.triprecord.triprecord.record.controller.request.RecordModifyRequest;
import com.triprecord.triprecord.record.controller.response.RecordPageResponse;
import com.triprecord.triprecord.record.controller.response.RecordResponse;
import com.triprecord.triprecord.record.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/records")
public class RecordController {

    private final RecordService recordService;

    @PostMapping()
    public ResponseEntity<ResponseMessage> createRecord(Authentication authentication, @Valid RecordCreateRequest request){
        recordService.createRecord(Long.valueOf(authentication.getName()), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("기록 생성에 성공했습니다."));
    }

    @GetMapping()
    public ResponseEntity<RecordPageResponse> getRecordPage(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(recordService.getRecordPage(pageable));
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<RecordResponse> getRecordData(@PathVariable Long recordId){
        return ResponseEntity.status(HttpStatus.OK).body(recordService.getRecordResponseData(recordId));
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<ResponseMessage> deleteRecord(Authentication authentication, @PathVariable Long recordId){
        recordService.deleteRecord(Long.valueOf(authentication.getName()), recordId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("기록 삭제에 성공했습니다."));
    }

    @PatchMapping("/{recordId}")
    public ResponseEntity<ResponseMessage> modifyRecord(Authentication authentication,
                                                        @PathVariable Long recordId,
                                                        @Valid RecordModifyRequest request) {
        recordService.modifyRecord(Long.valueOf(authentication.getName()), recordId, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("기록 수정에 성공했습니다."));
    }

    @PostMapping("/{recordId}/comments")
    public ResponseEntity<ResponseMessage> postComment(Authentication authentication,
                                                       @PathVariable Long recordId,
                                                       @RequestBody @Valid CommentContent request) {
        recordService.postCommentToRecord(Long.valueOf(authentication.getName()), recordId, request.commentContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("댓글 전송에 성공했습니다."));
    }

    @PostMapping("/{recordId}/likes")
    public ResponseEntity<ResponseMessage> postLike(Authentication authentication, @PathVariable Long recordId){
        recordService.postLikeToRecord(Long.valueOf(authentication.getName()), recordId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("좋아요 전송에 성공했습니다."));
    }

    @DeleteMapping("/{recordId}/likes")
    public ResponseEntity<ResponseMessage> cancelLike(Authentication authentication, @PathVariable Long recordId) {
        recordService.cancelLikeToRecord(Long.valueOf(authentication.getName()), recordId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("좋아요 취소에 성공했습니다."));
    }

}
