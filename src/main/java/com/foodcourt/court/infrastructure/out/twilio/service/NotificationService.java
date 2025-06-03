package com.foodcourt.court.infrastructure.out.twilio.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    @Value("${external.api.notification-mngr.account-sid}")
    private String accoundSid;

    @Value("${external.api.notification-mngr.auth-token}")
    private String authToken;

    @Value("${external.api.notification-mngr.messaging-service-sid}")
    private String messagingServiceSid;


    public String sendSms(String message, String receiverNumber){
        Twilio.init(accoundSid, authToken);
        Message messageInstance = Message.creator(
                new PhoneNumber(receiverNumber),
                messagingServiceSid,
                message
        ).create();
        return messageInstance.getStatus().toString();
    }
}
