<<<<<<<< HEAD:src/main/java/com/triprecord/triprecord/user/dto/request/UserCreateRequest.java
package com.triprecord.triprecord.user.dto.request;
========
package com.triprecord.triprecord.user.controller.dto.request;
>>>>>>>> a4a5d558dceddcaf80a6e4b81c685b280443947c:src/main/java/com/triprecord/triprecord/user/controller/dto/request/UserCreateRequest.java

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserCreateRequest(
        @Email @NotNull String userEmail,
        @NotNull String userPassword,
        @NotNull String userNickname,
        @NotNull LocalDate userAge,
        @NotNull Long userBasicProfileId
) {
}