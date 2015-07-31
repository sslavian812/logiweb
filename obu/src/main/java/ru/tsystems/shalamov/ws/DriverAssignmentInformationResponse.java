
package ru.tsystems.shalamov.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for driverAssignmentInformationResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="driverAssignmentInformationResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ws.shalamov.tsystems.ru/}driverAssignment" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "driverAssignmentInformationResponse", propOrder = {
    "_return"
})
public class DriverAssignmentInformationResponse {

    @XmlElement(name = "return")
    protected DriverAssignment _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link ru.tsystems.shalamov.ws.DriverAssignment }
     *     
     */
    public DriverAssignment getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.tsystems.shalamov.ws.DriverAssignment }
     *     
     */
    public void setReturn(DriverAssignment value) {
        this._return = value;
    }

}
