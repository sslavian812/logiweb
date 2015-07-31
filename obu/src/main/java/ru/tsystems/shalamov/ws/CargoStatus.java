
package ru.tsystems.shalamov.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cargoStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="cargoStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PREPARED"/>
 *     &lt;enumeration value="SHIPPED"/>
 *     &lt;enumeration value="DELIVERED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "cargoStatus")
@XmlEnum
public enum CargoStatus {

    PREPARED,
    SHIPPED,
    DELIVERED;

    public String value() {
        return name();
    }

    public static CargoStatus fromValue(String v) {
        return valueOf(v);
    }

}
