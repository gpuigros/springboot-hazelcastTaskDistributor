package com.puigros.task;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by guillem.puigros on 10/05/2017.
 */
@Data
public class Result implements Serializable{
    private String status;
    private String message;



}
