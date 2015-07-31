
package ru.tsystems.shalamov.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.tsystems.shalamov.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ShiftBegin_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "shiftBegin");
    private final static QName _DriverStatusToPrimaryResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverStatusToPrimaryResponse");
    private final static QName _CargoStatusChangedToShippedResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "cargoStatusChangedToShippedResponse");
    private final static QName _CargoStatusChangedToDeliveredResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "cargoStatusChangedToDeliveredResponse");
    private final static QName _DriverStatusToAuxiliary_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverStatusToAuxiliary");
    private final static QName _DriverAssignmentInformation_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverAssignmentInformation");
    private final static QName _ShiftEndResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "shiftEndResponse");
    private final static QName _CargoStatusChangedToShipped_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "cargoStatusChangedToShipped");
    private final static QName _DriverStatusToPrimary_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverStatusToPrimary");
    private final static QName _ShiftBeginResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "shiftBeginResponse");
    private final static QName _DriverStatusToAuxiliaryResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverStatusToAuxiliaryResponse");
    private final static QName _ShiftEnd_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "shiftEnd");
    private final static QName _DriverStatusToRest_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverStatusToRest");
    private final static QName _CargoStatusChangedToDelivered_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "cargoStatusChangedToDelivered");
    private final static QName _DriverAssignmentInformationResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverAssignmentInformationResponse");
    private final static QName _DriverStatusToRestResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverStatusToRestResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.tsystems.shalamov.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.CargoStatusChangedToShippedResponse }
     * 
     */
    public CargoStatusChangedToShippedResponse createCargoStatusChangedToShippedResponse() {
        return new CargoStatusChangedToShippedResponse();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.ShiftBegin }
     * 
     */
    public ShiftBegin createShiftBegin() {
        return new ShiftBegin();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverStatusToPrimaryResponse }
     * 
     */
    public DriverStatusToPrimaryResponse createDriverStatusToPrimaryResponse() {
        return new DriverStatusToPrimaryResponse();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.CargoStatusChangedToDeliveredResponse }
     * 
     */
    public CargoStatusChangedToDeliveredResponse createCargoStatusChangedToDeliveredResponse() {
        return new CargoStatusChangedToDeliveredResponse();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverStatusToAuxiliary }
     * 
     */
    public DriverStatusToAuxiliary createDriverStatusToAuxiliary() {
        return new DriverStatusToAuxiliary();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverAssignmentInformation }
     * 
     */
    public DriverAssignmentInformation createDriverAssignmentInformation() {
        return new DriverAssignmentInformation();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.ShiftBeginResponse }
     * 
     */
    public ShiftBeginResponse createShiftBeginResponse() {
        return new ShiftBeginResponse();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.ShiftEndResponse }
     * 
     */
    public ShiftEndResponse createShiftEndResponse() {
        return new ShiftEndResponse();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.CargoStatusChangedToShipped }
     * 
     */
    public CargoStatusChangedToShipped createCargoStatusChangedToShipped() {
        return new CargoStatusChangedToShipped();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverStatusToPrimary }
     * 
     */
    public DriverStatusToPrimary createDriverStatusToPrimary() {
        return new DriverStatusToPrimary();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverStatusToAuxiliaryResponse }
     * 
     */
    public DriverStatusToAuxiliaryResponse createDriverStatusToAuxiliaryResponse() {
        return new DriverStatusToAuxiliaryResponse();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.ShiftEnd }
     * 
     */
    public ShiftEnd createShiftEnd() {
        return new ShiftEnd();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverStatusToRest }
     * 
     */
    public DriverStatusToRest createDriverStatusToRest() {
        return new DriverStatusToRest();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverAssignmentInformationResponse }
     * 
     */
    public DriverAssignmentInformationResponse createDriverAssignmentInformationResponse() {
        return new DriverAssignmentInformationResponse();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverStatusToRestResponse }
     * 
     */
    public DriverStatusToRestResponse createDriverStatusToRestResponse() {
        return new DriverStatusToRestResponse();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.CargoStatusChangedToDelivered }
     * 
     */
    public CargoStatusChangedToDelivered createCargoStatusChangedToDelivered() {
        return new CargoStatusChangedToDelivered();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.TruckEntity }
     * 
     */
    public TruckEntity createTruckEntity() {
        return new TruckEntity();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.CargoEntity }
     * 
     */
    public CargoEntity createCargoEntity() {
        return new CargoEntity();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverAssignment }
     * 
     */
    public DriverAssignment createDriverAssignment() {
        return new DriverAssignment();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverStatusEntity }
     * 
     */
    public DriverStatusEntity createDriverStatusEntity() {
        return new DriverStatusEntity();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.OrderEntity }
     * 
     */
    public OrderEntity createOrderEntity() {
        return new OrderEntity();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverEntity }
     * 
     */
    public DriverEntity createDriverEntity() {
        return new DriverEntity();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.ShiftBegin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "shiftBegin")
    public JAXBElement<ShiftBegin> createShiftBegin(ShiftBegin value) {
        return new JAXBElement<ShiftBegin>(_ShiftBegin_QNAME, ShiftBegin.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.DriverStatusToPrimaryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "driverStatusToPrimaryResponse")
    public JAXBElement<DriverStatusToPrimaryResponse> createDriverStatusToPrimaryResponse(DriverStatusToPrimaryResponse value) {
        return new JAXBElement<DriverStatusToPrimaryResponse>(_DriverStatusToPrimaryResponse_QNAME, DriverStatusToPrimaryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.CargoStatusChangedToShippedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "cargoStatusChangedToShippedResponse")
    public JAXBElement<CargoStatusChangedToShippedResponse> createCargoStatusChangedToShippedResponse(CargoStatusChangedToShippedResponse value) {
        return new JAXBElement<CargoStatusChangedToShippedResponse>(_CargoStatusChangedToShippedResponse_QNAME, CargoStatusChangedToShippedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.CargoStatusChangedToDeliveredResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "cargoStatusChangedToDeliveredResponse")
    public JAXBElement<CargoStatusChangedToDeliveredResponse> createCargoStatusChangedToDeliveredResponse(CargoStatusChangedToDeliveredResponse value) {
        return new JAXBElement<CargoStatusChangedToDeliveredResponse>(_CargoStatusChangedToDeliveredResponse_QNAME, CargoStatusChangedToDeliveredResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.DriverStatusToAuxiliary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "driverStatusToAuxiliary")
    public JAXBElement<DriverStatusToAuxiliary> createDriverStatusToAuxiliary(DriverStatusToAuxiliary value) {
        return new JAXBElement<DriverStatusToAuxiliary>(_DriverStatusToAuxiliary_QNAME, DriverStatusToAuxiliary.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.DriverAssignmentInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "driverAssignmentInformation")
    public JAXBElement<DriverAssignmentInformation> createDriverAssignmentInformation(DriverAssignmentInformation value) {
        return new JAXBElement<DriverAssignmentInformation>(_DriverAssignmentInformation_QNAME, DriverAssignmentInformation.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.ShiftEndResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "shiftEndResponse")
    public JAXBElement<ShiftEndResponse> createShiftEndResponse(ShiftEndResponse value) {
        return new JAXBElement<ShiftEndResponse>(_ShiftEndResponse_QNAME, ShiftEndResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.CargoStatusChangedToShipped }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "cargoStatusChangedToShipped")
    public JAXBElement<CargoStatusChangedToShipped> createCargoStatusChangedToShipped(CargoStatusChangedToShipped value) {
        return new JAXBElement<CargoStatusChangedToShipped>(_CargoStatusChangedToShipped_QNAME, CargoStatusChangedToShipped.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.DriverStatusToPrimary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "driverStatusToPrimary")
    public JAXBElement<DriverStatusToPrimary> createDriverStatusToPrimary(DriverStatusToPrimary value) {
        return new JAXBElement<DriverStatusToPrimary>(_DriverStatusToPrimary_QNAME, DriverStatusToPrimary.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.ShiftBeginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "shiftBeginResponse")
    public JAXBElement<ShiftBeginResponse> createShiftBeginResponse(ShiftBeginResponse value) {
        return new JAXBElement<ShiftBeginResponse>(_ShiftBeginResponse_QNAME, ShiftBeginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.DriverStatusToAuxiliaryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "driverStatusToAuxiliaryResponse")
    public JAXBElement<DriverStatusToAuxiliaryResponse> createDriverStatusToAuxiliaryResponse(DriverStatusToAuxiliaryResponse value) {
        return new JAXBElement<DriverStatusToAuxiliaryResponse>(_DriverStatusToAuxiliaryResponse_QNAME, DriverStatusToAuxiliaryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.ShiftEnd }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "shiftEnd")
    public JAXBElement<ShiftEnd> createShiftEnd(ShiftEnd value) {
        return new JAXBElement<ShiftEnd>(_ShiftEnd_QNAME, ShiftEnd.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.DriverStatusToRest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "driverStatusToRest")
    public JAXBElement<DriverStatusToRest> createDriverStatusToRest(DriverStatusToRest value) {
        return new JAXBElement<DriverStatusToRest>(_DriverStatusToRest_QNAME, DriverStatusToRest.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.CargoStatusChangedToDelivered }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "cargoStatusChangedToDelivered")
    public JAXBElement<CargoStatusChangedToDelivered> createCargoStatusChangedToDelivered(CargoStatusChangedToDelivered value) {
        return new JAXBElement<CargoStatusChangedToDelivered>(_CargoStatusChangedToDelivered_QNAME, CargoStatusChangedToDelivered.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.DriverAssignmentInformationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "driverAssignmentInformationResponse")
    public JAXBElement<DriverAssignmentInformationResponse> createDriverAssignmentInformationResponse(DriverAssignmentInformationResponse value) {
        return new JAXBElement<DriverAssignmentInformationResponse>(_DriverAssignmentInformationResponse_QNAME, DriverAssignmentInformationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.DriverStatusToRestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "driverStatusToRestResponse")
    public JAXBElement<DriverStatusToRestResponse> createDriverStatusToRestResponse(DriverStatusToRestResponse value) {
        return new JAXBElement<DriverStatusToRestResponse>(_DriverStatusToRestResponse_QNAME, DriverStatusToRestResponse.class, null, value);
    }

}
