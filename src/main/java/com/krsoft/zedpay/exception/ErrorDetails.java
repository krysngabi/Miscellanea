package com.krsoft.zedpay.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "Objet contenant les informations d'une erreur")
public class ErrorDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Horodatage de l'erreur")
    private final Date timestamp;
    
    @ApiModelProperty(value = "Code de l'erreur")
    private String codeErreur;

    @ApiModelProperty(value = "Messages fonctionnels")
    private final List<String> messages = new ArrayList<>();

    @ApiModelProperty(value = "Détails techniques")
    private String details;
    
    public ErrorDetails() {
        this(null, null);
    }
    
    public ErrorDetails(String code, String message) {
        this(code, message, null);
    }
    
    public ErrorDetails(String code, String message, String details) { 
        this.codeErreur = code;
        this.timestamp = new Date();
        this.messages.add(message);
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCodeErreur() {
        return codeErreur;
    }

    public void setCodeErreur(String codeErreur) {
        this.codeErreur = codeErreur;
    }
    
    
    
}
