package residentevil.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "capitals")
public class Capital extends BaseEntity {
    private String name;
    private Double latitude;
    private Double longitude;

    public Capital() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
