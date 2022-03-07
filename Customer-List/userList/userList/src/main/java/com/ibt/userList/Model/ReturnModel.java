package com.ibt.userList.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnModel {
    private int statusCode;
    private Object body;
    private boolean successful;
    private String message;

}
