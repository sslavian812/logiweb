package ru.tsystems.shalamov.ws;


import javax.xml.ws.WebFault;

/**
 * Created by viacheslav on 11.08.2015.
 */
@WebFault(name = "ServiceFault")
public class ServiceFault extends Exception {

    /**
     * Java type that goes as soapenv:Fault detail element.
     */
    private FaultBean faultInfo;

    public ServiceFault(String message, FaultBean faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public ServiceFault(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return faultInfo;
    }

}