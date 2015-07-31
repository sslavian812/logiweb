
package ru.tsystems.shalamov.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for driverStatusEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="driverStatusEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="status" type="{http://ws.shalamov.tsystems.ru/}driverStatus" minOccurs="0"/>
 *         &lt;element name="truckEntity" type="{http://ws.shalamov.tsystems.ru/}truckEntity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "driverStatusEntity", propOrder = {
    "id",
    "status",
    "truckEntity"
})
public class DriverStatusEntity {

    protected int id;
    @XmlSchemaType(name = "string")
    protected DriverStatus status;
    protected TruckEntity truckEntity;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ru.tsystems.shalamov.ws.DriverStatus }
     *     
     */
    public DriverStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.tsystems.shalamov.ws.DriverStatus }
     *     
     */
    public void setStatus(DriverStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the truckEntity property.
     * 
     * @return
     *     possible object is
     *     {@link ru.tsystems.shalamov.ws.TruckEntity }
     *     
     */
    public TruckEntity getTruckEntity() {
        return truckEntity;
    }

    /**
     * Sets the value of the truckEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.tsystems.shalamov.ws.TruckEntity }
     *     
     */
    public void setTruckEntity(TruckEntity value) {
        this.truckEntity = value;
    }

}
