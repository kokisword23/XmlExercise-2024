package org.cardealerexerice.service.dtos.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartSeedRootDto implements Serializable {

    @XmlElement(name = "part")
    private List<PartSeedDto> partSeedDtoList;

    public List<PartSeedDto> getPartSeedDtoList() {
        return partSeedDtoList;
    }

    public void setPartSeedDtoList(List<PartSeedDto> partSeedDtoList) {
        this.partSeedDtoList = partSeedDtoList;
    }
}
