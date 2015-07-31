
package ru.tsystems.shalamov.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for orderEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orderEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cargoEntities" type="{http://ws.shalamov.tsystems.ru/}cargoEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="orderIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://ws.shalamov.tsystems.ru/}orderStatus" minOccurs="0"/>
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
@XmlType(name = "orderEntity", propOrder = {
    "cargoEntities",
    "id",
    "orderIdentifier",
    "status",
    "truckEntity"
})
public class OrderEntity {

    @XmlElement(nillable = true)
    protected List<CargoEntity> cargoEntities;
    protected int id;
    protected String orderIdentifier;
    @XmlSchemaType(name = "string")
    protected OrderStatus status;
    protected TruckEntity truckEntity;

    /**
     * Gets the value of the cargoEntities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cargoEntities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCargoEntities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ru.tsystems.shalamov.ws.CargoEntity }
     * 
     * 
     */
    public List<CargoEntity> getCargoEntities() {
        if (cargoEntities == null) {
            cargoEntities = new ArrayList<CargoEntity>();
        }
        return this.cargoEntities;
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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ru.tsystems.shalamov.ws.OrderStatus }
     *     
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.tsystems.shalamov.ws.OrderStatus }
     *     
     */
    public void setStatus(OrderStatus value) {
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
