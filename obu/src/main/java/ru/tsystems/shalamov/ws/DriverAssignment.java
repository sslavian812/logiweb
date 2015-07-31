
package ru.tsystems.shalamov.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for driverAssignment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="driverAssignment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cargos" type="{http://ws.shalamov.tsystems.ru/}cargoEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="coDrivers" type="{http://ws.shalamov.tsystems.ru/}driverEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="driverPersonalNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="driverStatus" type="{http://ws.shalamov.tsystems.ru/}driverStatus" minOccurs="0"/>
 *         &lt;element name="orderIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="truckRegistrationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "driverAssignment", propOrder = {
    "cargos",
    "coDrivers",
    "driverPersonalNumber",
    "driverStatus",
    "orderIdentifier",
    "truckRegistrationNumber"
})
public class DriverAssignment {

    @XmlElement(nillable = true)
    protected List<CargoEntity> cargos;
    @XmlElement(nillable = true)
    protected List<DriverEntity> coDrivers;
    protected String driverPersonalNumber;
    @XmlSchemaType(name = "string")
    protected DriverStatus driverStatus;
    protected String orderIdentifier;
    protected String truckRegistrationNumber;

    /**
     * Gets the value of the cargos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cargos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCargos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ru.tsystems.shalamov.ws.CargoEntity }
     * 
     * 
     */
    public List<CargoEntity> getCargos() {
        if (cargos == null) {
            cargos = new ArrayList<CargoEntity>();
        }
        return this.cargos;
    }

    /**
     * Gets the value of the coDrivers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coDrivers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCoDrivers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ru.tsystems.shalamov.ws.DriverEntity }
     * 
     * 
     */
    public List<DriverEntity> getCoDrivers() {
        if (coDrivers == null) {
            coDrivers = new ArrayList<DriverEntity>();
        }
        return this.coDrivers;
    }

    /**
     * Gets the value of the driverPersonalNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDriverPersonalNumber() {
        return driverPersonalNumber;
    }

    /**
     * Sets the value of the driverPersonalNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDriverPersonalNumber(String value) {
        this.driverPersonalNumber = value;
    }

    /**
     * Gets the value of the driverStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ru.tsystems.shalamov.ws.DriverStatus }
     *     
     */
    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    /**
     * Sets the value of the driverStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.tsystems.shalamov.ws.DriverStatus }
     *     
     */
    public void setDriverStatus(DriverStatus value) {
        this.driverStatus = value;
    }

    /**
     * Gets the value of the orderIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderIdentifier() {
        return orderIdentifier;
    }

    /**
     * Sets the value of the orderIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderIdentifier(String value) {
        this.orderIdentifier = value;
    }

    /**
     * Gets the value of the truckRegistrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTruckRegistrationNumber() {
        return truckRegistrationNumber;
    }

    /**
     * Sets the value of the truckRegistrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTruckRegistrationNumber(String value) {
        this.truckRegistrationNumber = value;
    }

}
