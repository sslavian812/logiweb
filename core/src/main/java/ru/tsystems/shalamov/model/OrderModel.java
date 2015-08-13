package ru.tsystems.shalamov.model;

import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by viacheslav on 22.06.2015.
 */
public class OrderModel {


    public OrderModel(String orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
        this.status = OrderStatus.UNASSIGNED;
    }

    public OrderModel(OrderEntity orderEntity) {
        this.orderIdentifier = orderEntity.getOrderIdentifier();
        this.status = orderEntity.getStatus();
        this.cargoes = orderEntity.getCargoEntities().stream()
                .map(c -> new CargoModel(c)).collect(Collectors.toList());
        if(orderEntity.getTruckEntity()!= null)
            this.truckRegistrationNumber = orderEntity.getTruckEntity().getRegistrationNumber();
    }

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "alphanumeric characters only")
    private String orderIdentifier;

    @NotNull
    private OrderStatus status;

    private List<CargoModel> cargoes;

    @Pattern(regexp = "[a-zA-Z0-9]{2}[0-9]{5}]", message = "two letters and five digits")
    private String truckRegistrationNumber;

    /**
     * Provides total weight of all cargoes included int the order.
     *
     * @return
     */
    public int getTotalweight() {
        int total = 0;
        for (CargoModel cargo : cargoes) {
            total += cargo.getWeight();
        }
        return total;
    }

    public String getOrderIdentifier() {
        return orderIdentifier;
    }

    public void setOrderIdentifier(String orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<CargoModel> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<CargoModel> cargoes) {
        this.cargoes = cargoes;
    }

    public String getTruckRegistrationNumber() {
        return truckRegistrationNumber;
    }

    public void setTruckRegistrationNumber(String truckRegistrationNumber) {
        this.truckRegistrationNumber = truckRegistrationNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderModel that = (OrderModel) o;

        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (orderIdentifier != null ? !orderIdentifier.equals(that.orderIdentifier) : that.orderIdentifier != null)
            return false;
        return true;
    }
}
