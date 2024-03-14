package org.cardealerexerice.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartRootDto implements Serializable {

    @XmlElement(name = "part")
    private List<PartDto> partDtos;

    public List<PartDto> getPartDtos() {
        return partDtos;
    }

    public void setPartDtos(List<PartDto> partDtos) {
        this.partDtos = partDtos;
    }
}
