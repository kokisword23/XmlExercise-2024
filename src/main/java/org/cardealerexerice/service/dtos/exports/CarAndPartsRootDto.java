package org.cardealerexerice.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarAndPartsRootDto implements Serializable {

    @XmlElement(name = "car")
    private List<CarAndPartsDto> carAndPartsDtoList;

    public List<CarAndPartsDto> getCarAndPartsDtoList() {
        return carAndPartsDtoList;
    }

    public void setCarAndPartsDtoList(List<CarAndPartsDto> carAndPartsDtoList) {
        this.carAndPartsDtoList = carAndPartsDtoList;
    }
}
