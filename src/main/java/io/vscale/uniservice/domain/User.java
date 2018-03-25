package io.vscale.uniservice.domain;

import io.vscale.uniservice.security.states.UserState;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.SequenceGenerator;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import java.util.Set;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"profile", "roles"})
@ToString(exclude = {"profile", "roles"})
@Entity(name = "User")
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", allocationSize = 1, sequenceName = "user_seq")
    private Long id;

    @Column(name = "login", unique = true, columnDefinition = "VARCHAR(70)")
    private String login;

    @Column(name = "password", columnDefinition = "TEXT")
    private String password;

    @Column(name = "temp_password", columnDefinition = "TEXT")
    private String tempPassword;

    @Column(name = "token", columnDefinition = "TEXT")
    private String token;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_to_role",
               joinColumns = @JoinColumn(name = "existed_user_id"),
               inverseJoinColumns = @JoinColumn(name = "existed_role_id"))
    private Set<RoleType> roles;

    @Column(name = "user_state")
    @Enumerated(EnumType.STRING)
    private UserState state;

}
