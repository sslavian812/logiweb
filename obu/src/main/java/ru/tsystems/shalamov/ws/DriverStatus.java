
package ru.tsystems.shalamov.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for driverStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="driverStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="REST"/>
 *     &lt;enumeration value="AUXILIARY"/>
 *     &lt;enumeration value="PRIMARY"/>
 *     &lt;enumeration value="UNASSIGNED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "driverStatus")
@XmlEnum
public enum DriverStatus {

    REST,
    AUXILIARY,
    PRIMARY,
    UNASSIGNED;

    public String value() {
        return name();
    }

    public static DriverStatus fromValue(String v) {
        return valueOf(v);
    }

}
