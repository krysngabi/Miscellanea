package com.krsoft.abovebytes.services.email;

import com.krsoft.abovebytes.models.ResponseAPI;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface EmailSenderService {
    ResponseAPI sendEmailAppointmentSukissime(String to, String user, String customername, String service, String templateId, String date) throws TemplateException, IOException;
}
