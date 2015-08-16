package ru.tsystems.shalamov.model;

import ru.tsystems.shalamov.entities.CargoEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.services.Util;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by viacheslav on 22.06.2015.
 */
public class CargoModel {

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "alphanumeric characters only")
    private String cargoIdentifier;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9 ]+", message = "alphanumeric characters only and whitespaces")
    private String denomination;

    @NotNull
    @Size(min = 0, max = 100000, message = "weight should be non-negative and at most 100 000 kg")
    private Integer weight;

    @NotNull
    private CargoStatus status;

    @NotNull
    private String orderIdentifier;


    public CargoModel(String cargoIdentifier, String denomination, int weight, CargoStatus status, String orderIdentifier) {
        if (cargoIdentifier == null || cargoIdentifier.isEmpty())
            this.cargoIdentifier = Util.generateRandomId();
        else
            this.cargoIdentifier = cargoIdentifier;
        this.denomination = denomination;
        this.weight = weight;
        this.status = status;
        this.orderIdentifier = orderIdentifier;
    }

    public CargoModel(String denomination, int weight, CargoStatus status, String orderIdentifier) {
        this.cargoIdentifier = Util.generateRandomId();
        this.denomination = denomination;
        this.weight = weight;
        this.status = status;
        this.orderIdentifier = orderIdentifier;
    }


    public CargoModel(CargoEntity cargoEntity) {
        this(cargoEntity.getCargoIdentifier(),
                cargoEntity.getDenomination(),
                cargoEntity.getWeight(),
                cargoEntity.getStatus(),
                cargoEntity.getOrderEntity().getOrderIdentifier()
        );
    }

    public CargoEntity getEntity(OrderEntity order) {
        return new CargoEntity(denomination, weight, status, order, cargoIdentifier);
    }

    public String getCargoIdentifier() {
        return cargoIdentifier;
    }

    public void setCargoIdentifier(String cargoIdentifier) {
        this.cargoIdentifier = cargoIdentifier;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public CargoStatus getStatus() {
        return status;
    }

    public void setStatus(CargoStatus status) {
        this.status = status;
    }

    public String getOrderIdentifier() {
        return orderIdentifier;
    }

    public void setOrderIdentifier(String orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }
}
