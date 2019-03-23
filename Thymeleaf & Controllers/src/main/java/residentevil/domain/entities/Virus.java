package residentevil.domain.entities;
import residentevil.domain.enums.Creator;
import residentevil.domain.enums.Magnitude;
import residentevil.domain.enums.Mutation;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "viruses")
public class Virus extends BaseEntity {
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
    private List<Capital> capitals;

    public Virus() {
    }

    @NotNull
    @Size(min = 3, max = 10)
    @Column(name = "name")
    public String getName() {
        return name;
    }

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "description", columnDefinition = "text")
    public String getDescription() {
        return description;
    }

    @Size(max = 50)
    @Column(name = "side_effects")
    public String getSideEffects() {
        return sideEffects;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "creator")
    public Creator getCreator() {
        return creator;
    }

    @Column(name = "is_deadly")
    public Boolean getDeadly() {
        return isDeadly;
    }

    @Column(name = "is_curable")
    public Boolean getCurable() {
        return IsCurable;
    }

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "mutation")
    public Mutation getMutation() {
        return mutation;
    }

    @Max(value = 100)
    @Column(name = "turnover_rate")
    public Integer getTurnoverRate() {
        return turnoverRate;
    }

    @Max(value = 100)
    @Column(name = "hours_until_turn")
    public Integer getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "magnitude")
    public Magnitude getMagnitude() {
        return magnitude;
    }

    @Column(name = "released_on")
    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    @ManyToMany(targetEntity = Capital.class)
    @JoinTable(name = "viruses_capitals",
            joinColumns = @JoinColumn(name = "virus_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "capital_id", referencedColumnName = "id"))
    public List<Capital> getCapitals() {
        return capitals;
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

    public void setCapitals(List<Capital> capitals) {
        this.capitals = capitals;
    }
}
