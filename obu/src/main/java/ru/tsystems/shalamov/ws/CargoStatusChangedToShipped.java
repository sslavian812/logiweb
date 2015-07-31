
package ru.tsystems.shalamov.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cargoStatusChangedToShipped complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cargoStatusChangedToShipped">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CargoIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cargoStatusChangedToShipped", propOrder = {
    "cargoIdentifier"
})
public class CargoStatusChangedToShipped {

    @XmlElement(name = "CargoIdentifier")
    protected String cargoIdentifier;

    /**
     * Gets the value of the cargoIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCargoIdentifier() {
        return cargoIdentifier;
    }

    /**
     * Sets the value of the cargoIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCargoIdentifier(String value) {
        this.cargoIdentifier = value;
    }

}
