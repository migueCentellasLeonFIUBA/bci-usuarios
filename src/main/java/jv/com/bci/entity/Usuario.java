package jv.com.bci.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre",nullable = false)
    private String name;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    private List<PhoneUsuario> phoneUsuarios;

    @Transient
    private List<Phone> phones;

    @Column(name = "created")
    private Date createAt;

    @Column(name = "modified")
    private Date updateAt;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "isActive")
    private boolean isActive;

    @Lob
    @Column(name="token")
    private String token;

    public void addPhoneUsuario(PhoneUsuario phoneUsuario) {
        phoneUsuarios.add(phoneUsuario);
    }

    public Usuario(){
        phoneUsuarios = new ArrayList<>();
        createAt = new Date();
        updateAt = new Date();
        lastLogin = new Date();
        isActive = true;
    }

}
