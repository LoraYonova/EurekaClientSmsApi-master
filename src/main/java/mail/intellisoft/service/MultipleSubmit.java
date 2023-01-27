package mail.intellisoft.service;

import org.jsmpp.bean.Address;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.*;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MultipleSubmit {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultipleSubmit.class);

    private static final TimeFormatter TIME_FORMATTER = new AbsoluteTimeFormatter();

    private final String smppIp = "127.0.0.1";

    private int port = 8086;

    private final String username = "localhost";

    private final String password = "password";

    private final String address = "AX-DEV";

    private static final String SERVICE_TYPE = "CMT";

    public void broadcastMessage(String message, List numbers) {
        LOGGER.info("Broadcasting sms");
        SubmitMultiResult result = null;
        var addresses = prepareAddress(numbers);
        SMPPSession session = initSession();
        if(session != null) {
            try {
                result = session.submitMultiple(SERVICE_TYPE, TypeOfNumber.NATIONAL, NumberingPlanIndicator.UNKNOWN, address,
                        addresses, new ESMClass(), (byte) 0, (byte) 1, TIME_FORMATTER.format(new Date()), null,
                        new RegisteredDelivery(SMSCDeliveryReceipt.FAILURE), ReplaceIfPresentFlag.REPLACE,
                        new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte) 0,
                        message.getBytes());

                LOGGER.info("Messages submitted, result is {}", result);
                Thread.sleep(1000);
            } catch (PDUException e) {
                LOGGER.error("Invalid PDU parameter", e);
            } catch (ResponseTimeoutException e) {
                LOGGER.error("Response timeout", e);
            } catch (InvalidResponseException e) {
                LOGGER.error("Receive invalid response", e);
            } catch (NegativeResponseException e) {
                LOGGER.error("Receive negative response", e);
            } catch (IOException e) {
                LOGGER.error("I/O error occured", e);
            } catch (Exception e) {
                LOGGER.error("Exception occured submitting SMPP request", e);
            }
        }else {
            LOGGER.error("Session creation failed with SMPP broker.");
        }
        if(result != null && result.getUnsuccessDeliveries() != null && result.getUnsuccessDeliveries().length > 0) {
            LOGGER.error(DeliveryReceiptState.getDescription(result.getUnsuccessDeliveries()[0].getErrorStatusCode()).description() + " - " +result.getMessageId());
        }else {
            LOGGER.info("Pushed message to broker successfully");
        }
        if(session != null) {
            session.unbindAndClose();
        }
    }

    private Address[] prepareAddress(List numbers) {
        Address[] addresses = new Address[numbers.size()];
        for(int i = 0; i< numbers.size(); i++){
            addresses[i] = new Address(TypeOfNumber.NATIONAL, NumberingPlanIndicator.UNKNOWN, String.valueOf(numbers.get(i)));
        }
        return addresses;
    }

    private SMPPSession initSession() {
        SMPPSession session = new SMPPSession();
        try {
            session.setMessageReceiverListener(new MessageReceiverListenerImpl());
            String systemId = session.connectAndBind(smppIp, Integer.valueOf(port), new BindParameter(BindType.BIND_TX, username, password, "cp", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));
            LOGGER.info("Connected with SMPP with system id {}", systemId);
        } catch (IOException e) {
            LOGGER.error("I/O error occured", e);
            session = null;
        }
        return session;
    }
}
