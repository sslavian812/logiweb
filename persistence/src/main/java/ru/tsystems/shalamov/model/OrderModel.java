package ru.tsystems.shalamov.model;

import ru.tsystems.shalamov.entities.statuses.OrderStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Created by viacheslav on 22.06.2015.
 */
public class OrderModel {


    public OrderModel(String orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
        this.status = OrderStatus.UNASSIGNED;
    }

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "alphanumeric characters only")
    private String orderIdentifier;

    @NotNull
    private OrderStatus status;

    private List<CargoModel> cargoes;

    @Pattern(regexp = "[a-zA-Z0-9]{2}[0-9]{5}]", message = "two letters and five digits")
    private String truckRgistrationNumber;

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

    public String getTruckRgistrationNumber() {
        return truckRgistrationNumber;
    }

    public void setTruckRgistrationNumber(String truckRgistrationNumber) {
        this.truckRgistrationNumber = truckRgistrationNumber;
    }
}
