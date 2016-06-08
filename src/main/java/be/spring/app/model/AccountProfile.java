package be.spring.app.model;

import be.spring.app.data.PositionsEnum;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by u0090265 on 11/8/15.
 */
@Entity
@Table(name = "account_profile")
public class AccountProfile extends BaseClass{
    private Account account;
    private Image avatar;
    private String mobilePhone;
    private String phone;
    private PositionsEnum favouritePosition;
    private String description;
    private Address address;

    @OneToOne(mappedBy = "accountProfile")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "avatar_id", insertable = true, updatable = true, nullable = true)
    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "mobile_phone")
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "favourite_position", length = 20)
    public PositionsEnum getFavouritePosition() {
        return favouritePosition;
    }

    public void setFavouritePosition(PositionsEnum favouritePosition) {
        this.favouritePosition = favouritePosition;
    }

    @Size(min = 1, max = 200)
    @Column(name = "content")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "adrress_id", insertable = true, updatable = true, nullable = true)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
