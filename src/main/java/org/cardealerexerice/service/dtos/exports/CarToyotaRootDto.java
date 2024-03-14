package org.cardealerexerice.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarToyotaRootDto implements Serializable {

    @XmlElement(name = "car")
    private List<CarToyotaDto> carToyotaDtoList;

    public List<CarToyotaDto> getCarToyotaDtoList() {
        return carToyotaDtoList;
    }

    public void setCarToyotaDtoList(List<CarToyotaDto> carToyotaDtoList) {
        this.carToyotaDtoList = carToyotaDtoList;
    }
}
