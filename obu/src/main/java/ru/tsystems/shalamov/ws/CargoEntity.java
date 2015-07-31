
package ru.tsystems.shalamov.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cargoEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cargoEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cargoIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="denomination" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="orderEntity" type="{http://ws.shalamov.tsystems.ru/}orderEntity" minOccurs="0"/>
 *         &lt;element name="status" type="{http://ws.shalamov.tsystems.ru/}cargoStatus" minOccurs="0"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cargoEntity", propOrder = {
    "cargoIdentifier",
    "denomination",
    "id",
    "orderEntity",
    "status",
    "weight"
})
public class CargoEntity {

    protected String cargoIdentifier;
    protected String denomination;
    protected int id;
    protected OrderEntity orderEntity;
    @XmlSchemaType(name = "string")
    protected CargoStatus status;
    protected Integer weight;

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

    /**
     * Gets the value of the denomination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenomination() {
        return denomination;
    }

    /**
     * Sets the value of the denomination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenomination(String value) {
        this.denomination = value;
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
     * Gets the value of the orderEntity property.
     * 
     * @return
     *     possible object is
     *     {@link ru.tsystems.shalamov.ws.OrderEntity }
     *     
     */
    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    /**
     * Sets the value of the orderEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.tsystems.shalamov.ws.OrderEntity }
     *     
     */
    public void setOrderEntity(OrderEntity value) {
        this.orderEntity = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ru.tsystems.shalamov.ws.CargoStatus }
     *     
     */
    public CargoStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ru.tsystems.shalamov.ws.CargoStatus }
     *     
     */
    public void setStatus(CargoStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWeight(Integer value) {
        this.weight = value;
    }

}
