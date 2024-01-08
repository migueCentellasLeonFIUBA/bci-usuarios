package jv.com.bci.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
