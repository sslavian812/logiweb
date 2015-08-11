
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
    private final static QName _CargoStatusChangedToPreparedResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "cargoStatusChangedToPreparedResponse");
    private final static QName _DriverStatusToPrimaryResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverStatusToPrimaryResponse");
    private final static QName _CargoStatusChangedToShippedResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "cargoStatusChangedToShippedResponse");
    private final static QName _CargoStatusChangedToDeliveredResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "cargoStatusChangedToDeliveredResponse");
    private final static QName _DriverStatusToAuxiliary_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverStatusToAuxiliary");
    private final static QName _GetDriverAssignmentInformationResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "getDriverAssignmentInformationResponse");
    private final static QName _CargoStatusChangedToPrepared_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "cargoStatusChangedToPrepared");
    private final static QName _ShiftEndResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "shiftEndResponse");
    private final static QName _CargoStatusChangedToShipped_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "cargoStatusChangedToShipped");
    private final static QName _CompleteOrderResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "completeOrderResponse");
    private final static QName _DriverStatusToPrimary_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverStatusToPrimary");
    private final static QName _ShiftBeginResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "shiftBeginResponse");
    private final static QName _DriverStatusToAuxiliaryResponse_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverStatusToAuxiliaryResponse");
    private final static QName _GetDriverAssignmentInformation_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "getDriverAssignmentInformation");
    private final static QName _CompleteOrder_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "completeOrder");
    private final static QName _ShiftEnd_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "shiftEnd");
    private final static QName _ServiceFault_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "ServiceFault");
    private final static QName _DriverStatusToRest_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "driverStatusToRest");
    private final static QName _CargoStatusChangedToDelivered_QNAME = new QName("http://ws.shalamov.tsystems.ru/", "cargoStatusChangedToDelivered");
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
     * Create an instance of {@link ru.tsystems.shalamov.ws.CargoStatusChangedToPreparedResponse }
     * 
     */
    public CargoStatusChangedToPreparedResponse createCargoStatusChangedToPreparedResponse() {
        return new CargoStatusChangedToPreparedResponse();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverStatusToPrimaryResponse }
     * 
     */
    public DriverStatusToPrimaryResponse createDriverStatusToPrimaryResponse() {
        return new DriverStatusToPrimaryResponse();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.GetDriverAssignmentInformationResponse }
     * 
     */
    public GetDriverAssignmentInformationResponse createGetDriverAssignmentInformationResponse() {
        return new GetDriverAssignmentInformationResponse();
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
     * Create an instance of {@link ru.tsystems.shalamov.ws.CargoStatusChangedToPrepared }
     * 
     */
    public CargoStatusChangedToPrepared createCargoStatusChangedToPrepared() {
        return new CargoStatusChangedToPrepared();
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
     * Create an instance of {@link ru.tsystems.shalamov.ws.CompleteOrderResponse }
     * 
     */
    public CompleteOrderResponse createCompleteOrderResponse() {
        return new CompleteOrderResponse();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverStatusToPrimary }
     * 
     */
    public DriverStatusToPrimary createDriverStatusToPrimary() {
        return new DriverStatusToPrimary();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.CompleteOrder }
     * 
     */
    public CompleteOrder createCompleteOrder() {
        return new CompleteOrder();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverStatusToAuxiliaryResponse }
     * 
     */
    public DriverStatusToAuxiliaryResponse createDriverStatusToAuxiliaryResponse() {
        return new DriverStatusToAuxiliaryResponse();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.GetDriverAssignmentInformation }
     * 
     */
    public GetDriverAssignmentInformation createGetDriverAssignmentInformation() {
        return new GetDriverAssignmentInformation();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.FaultBean }
     * 
     */
    public FaultBean createFaultBean() {
        return new FaultBean();
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
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverAssignmentModel }
     * 
     */
    public DriverAssignmentModel createDriverAssignmentModel() {
        return new DriverAssignmentModel();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.DriverModel }
     * 
     */
    public DriverModel createDriverModel() {
        return new DriverModel();
    }

    /**
     * Create an instance of {@link ru.tsystems.shalamov.ws.CargoModel }
     * 
     */
    public CargoModel createCargoModel() {
        return new CargoModel();
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
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.CargoStatusChangedToPreparedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "cargoStatusChangedToPreparedResponse")
    public JAXBElement<CargoStatusChangedToPreparedResponse> createCargoStatusChangedToPreparedResponse(CargoStatusChangedToPreparedResponse value) {
        return new JAXBElement<CargoStatusChangedToPreparedResponse>(_CargoStatusChangedToPreparedResponse_QNAME, CargoStatusChangedToPreparedResponse.class, null, value);
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
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.GetDriverAssignmentInformationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "getDriverAssignmentInformationResponse")
    public JAXBElement<GetDriverAssignmentInformationResponse> createGetDriverAssignmentInformationResponse(GetDriverAssignmentInformationResponse value) {
        return new JAXBElement<GetDriverAssignmentInformationResponse>(_GetDriverAssignmentInformationResponse_QNAME, GetDriverAssignmentInformationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.CargoStatusChangedToPrepared }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "cargoStatusChangedToPrepared")
    public JAXBElement<CargoStatusChangedToPrepared> createCargoStatusChangedToPrepared(CargoStatusChangedToPrepared value) {
        return new JAXBElement<CargoStatusChangedToPrepared>(_CargoStatusChangedToPrepared_QNAME, CargoStatusChangedToPrepared.class, null, value);
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
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.CompleteOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "completeOrderResponse")
    public JAXBElement<CompleteOrderResponse> createCompleteOrderResponse(CompleteOrderResponse value) {
        return new JAXBElement<CompleteOrderResponse>(_CompleteOrderResponse_QNAME, CompleteOrderResponse.class, null, value);
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
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.GetDriverAssignmentInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "getDriverAssignmentInformation")
    public JAXBElement<GetDriverAssignmentInformation> createGetDriverAssignmentInformation(GetDriverAssignmentInformation value) {
        return new JAXBElement<GetDriverAssignmentInformation>(_GetDriverAssignmentInformation_QNAME, GetDriverAssignmentInformation.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.CompleteOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "completeOrder")
    public JAXBElement<CompleteOrder> createCompleteOrder(CompleteOrder value) {
        return new JAXBElement<CompleteOrder>(_CompleteOrder_QNAME, CompleteOrder.class, null, value);
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
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.FaultBean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "ServiceFault")
    public JAXBElement<FaultBean> createServiceFault(FaultBean value) {
        return new JAXBElement<FaultBean>(_ServiceFault_QNAME, FaultBean.class, null, value);
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
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.tsystems.shalamov.ws.DriverStatusToRestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.shalamov.tsystems.ru/", name = "driverStatusToRestResponse")
    public JAXBElement<DriverStatusToRestResponse> createDriverStatusToRestResponse(DriverStatusToRestResponse value) {
        return new JAXBElement<DriverStatusToRestResponse>(_DriverStatusToRestResponse_QNAME, DriverStatusToRestResponse.class, null, value);
    }

}
