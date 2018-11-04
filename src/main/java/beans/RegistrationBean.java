package beans;

import dao.UserDao;
import entities.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.sql.Date;

@Stateless(name = "RegistrationEJB")
public class RegistrationBean {
    public RegistrationBean() {
    }

    public boolean signUp(String name, String surname, String middleName, String email, String sex, String birthDateStr, String mobileTelephone, boolean disability, short familySize) {
        boolean alreadyExists = false;

        UserDao userDao = new UserDao();
        try {
            userDao.findByEmail(email);
        } catch (NoResultException e) {
            alreadyExists = true;
        }

        if (alreadyExists) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setName(name);
        user.setSurname(surname);
        if (middleName != null) user.setMiddleName(middleName);
        user.setEmail(email);
        user.setSex(sex);
        user.setBirthDate(Date.valueOf(birthDateStr));
        if (mobileTelephone != null) user.setMobileTelephone(mobileTelephone);
        user.setDisability(disability);
        user.setFamilySize(familySize);

        userDao.create(user);

        return true;
    }
}
