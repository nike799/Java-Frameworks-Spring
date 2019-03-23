package residentevil.domain.models.view;
import residentevil.domain.entities.BaseEntity;

public class CapitalViewModel extends BaseEntity {
    private String name;
    private Double latitude;
    private Double longitude;

    public CapitalViewModel() {
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }
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
