package com.krsoft.zedpay.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseAPI {
    private boolean status = false;
    private String message;
}
