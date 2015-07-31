
package ru.tsystems.shalamov.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for truckStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="truckStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INTACT"/>
 *     &lt;enumeration value="BROKEN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "truckStatus")
@XmlEnum
public enum TruckStatus {

    INTACT,
    BROKEN;

    public String value() {
        return name();
    }

    public static TruckStatus fromValue(String v) {
        return valueOf(v);
    }

}
