package entities;

import javax.persistence.*;

@Entity
@Table(name = "ГРУППЫ_ПОЛЬЗОВАТЕЛЕЙ", schema = "s242419", catalog = "studs")
public class UserGroupsEntity {
    private String email;
    private String group;

    @Id
    @Column(name = "эл_почта")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "группа")
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGroupsEntity that = (UserGroupsEntity) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }
}
