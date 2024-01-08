package jv.com.bci.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number",nullable = false)
    private String number;

    @Column(name = "citycode",nullable = false)
    private String citycode;

    @Column(name = "countrycode",nullable = false)
    private String countrycode;
}
