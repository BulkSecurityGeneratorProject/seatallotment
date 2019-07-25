package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.SeatPartition} entity.
 */
public class SeatPartitionDTO implements Serializable {

    private UUID id;

    private String district;

    private Integer total;

    private Integer vacant;

    private Integer occupied;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getVacant() {
        return vacant;
    }

    public void setVacant(Integer vacant) {
        this.vacant = vacant;
    }

    public Integer getOccupied() {
        return occupied;
    }

    public void setOccupied(Integer occupied) {
        this.occupied = occupied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeatPartitionDTO seatPartitionDTO = (SeatPartitionDTO) o;
        if (seatPartitionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seatPartitionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeatPartitionDTO{" +
            "id=" + getId() +
            ", district='" + getDistrict() + "'" +
            ", total=" + getTotal() +
            ", vacant=" + getVacant() +
            ", occupied=" + getOccupied() +
            "}";
    }
}
