package residentevil.domain.models.binding;

import org.springframework.format.annotation.DateTimeFormat;
import residentevil.domain.enums.Creator;
import residentevil.domain.enums.Magnitude;
import residentevil.domain.enums.Mutation;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class VirusAddBindingModel {
    private String name;
    private String description;
    private String sideEffects;
    private Creator creator;
    private Boolean isDeadly;
    private Boolean IsCurable;
    private Mutation mutation;
    private Integer turnoverRate;
    private Integer hoursUntilTurn;
    private Magnitude magnitude;
    private LocalDate releasedOn;
    private List<Long> capitalsId;

    @Size(min = 3, max = 10, message = "Name must be between 3 and 10 characters")
    public String getName() {
        return name;
    }

    @Size(min = 5,max = 100, message = "Description must be between 5 and 100 characters")
    public String getDescription() {
        return description;
    }

    @Size(max = 50, message = "Side Effects can have max size 50 characters")
    public String getSideEffects() {
        return sideEffects;
    }

    public Creator getCreator() {
        return creator;
    }

    public Boolean getDeadly() {
        return isDeadly;
    }

    public Boolean getCurable() {
        return IsCurable;
    }

    public Mutation getMutation() {
        return mutation;
    }

    @Max(value = 100, message = "Turnover rate must be maximum 100")
    @Min(value = 0, message = "Turnover rate must be minimum 0")
    public Integer getTurnoverRate() {
        return turnoverRate;
    }

    @Max(value = 100, message = "Hours until turn can be maximum value 100 ")
    @Min(value = 0, message = "Hours until turn can have minimum value 0 ")
    public Integer getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    public Magnitude getMagnitude() {
        return magnitude;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    public List<Long> getCapitalsId() {
        return capitalsId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public void setDeadly(Boolean deadly) {
        isDeadly = deadly;
    }

    public void setCurable(Boolean curable) {
        IsCurable = curable;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    public void setTurnoverRate(Integer turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    public void setReleasedOn(LocalDate releasedOn) {
        this.releasedOn = releasedOn;
    }

    public void setCapitalsId(List<Long> capitalsId) {
        this.capitalsId = capitalsId;
    }
}
