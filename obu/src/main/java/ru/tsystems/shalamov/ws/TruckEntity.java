
package ru.tsystems.shalamov.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for truckEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="truckEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="capacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="crewSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="driverStatusEntities" type="{http://ws.shalamov.tsystems.ru/}driverStatusEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="registrationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://ws.shalamov.tsystems.ru/}truckStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "truckEntity", propOrder = {
    "capacity",
    "crewSize",
    "driverStatusEntities",
    "id",
    "registrationNumber",
    "status"
})
public class TruckEntity {

    protected int capacity;
    protected int crewSize;
    @XmlElement(nillable = true)
    protected List<DriverStatusEntity> driverStatusEntities;
    protected int id;
    protected String registrationNumber;
    @XmlSchemaType(name = "string")
    protected TruckStatus status;

    /**
     * Gets the value of the capacity property.
     * 
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     * 
     */
    public void setCapacity(int value) {
        this.capacity = value;
    }

    /**
     * Gets the value of the crewSize property.
     * 
     */
    public int getCrewSize() {
        return crewSize;
    }

    /**
     * Sets the value of the crewSize property.
     * 
     */
    public void setCrewSize(int value) {
        this.crewSize = value;
    }

    /**
     * Gets the value of the driverStatusEntities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the driverStatusEntities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDriverStatusEntities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ru.tsystems.shalamov.ws.DriverStatusEntity }
     * 
     * 
     */
    public List<DriverStatusEntity> getDriverStatusEntities() {
        if (driverStatusEntities == null) {
            driverStatusEntities = new ArrayList<DriverStatusEntity>();
        }
        return this.driverStatusEntities;
    }

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
     * Gets the value of the registrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the value of the registrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrationNumber(String value) {
        this.registrationNumber = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ru.tsystems.shalamov.ws.TruckStatus }
     *     
     */
    public TruckStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.tsystems.shalamov.ws.TruckStatus }
     *     
     */
    public void setStatus(TruckStatus value) {
        this.status = value;
    }

}
