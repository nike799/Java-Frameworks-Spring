package residentevil.domain.models.view;

import residentevil.domain.entities.Role;

import java.util.Set;

public class UserViewModel {
    private Long id;
    private String username;
    private String email;
    private Boolean isAdmin;
    private Boolean isModerator;

    public UserViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }
    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getModerator() {
        return isModerator;
    }

    public void setModerator(Boolean moderator) {
        isModerator = moderator;
    }

}
