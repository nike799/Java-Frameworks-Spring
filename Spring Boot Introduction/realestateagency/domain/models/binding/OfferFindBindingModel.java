package realestateagency.domain.models.binding;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class OfferFindBindingModel {
    private BigDecimal familyBudget;
    private String familyApartmentType;
    private String familyName;

    public OfferFindBindingModel() {
    }

    @DecimalMin("0.0001")
    public BigDecimal getFamilyBudget() {
        return familyBudget;
    }

    public void setFamilyBudget(BigDecimal familyBudget) {
        this.familyBudget = familyBudget;
    }

    @NotNull
    @NotEmpty
    public String getFamilyApartmentType() {
        return familyApartmentType;
    }

    public void setFamilyApartmentType(String familyApartmentType) {
        this.familyApartmentType = familyApartmentType;
    }
    @NotNull
    @Pattern(regexp = "[A-Za-z]+")
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}
