package com.krsoft.zedpay.services.email;

import com.krsoft.zedpay.entities.Property;
import com.krsoft.zedpay.models.ResponseAPI;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Optional;

public interface EmailSenderService {
    ResponseAPI sendEmailAppointmentSukissime(String to, String user, String customername, String service, String templateId, String date) throws TemplateException, IOException;
}
