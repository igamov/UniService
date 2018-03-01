package io.vscale.uniservice.domain;

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
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
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
@EqualsAndHashCode(exclude = {"profile", "cooperatorFiles"})
@ToString(exclude = {"profile", "cooperatorFiles"})
@Entity(name = "Cooperator")
@Table(name = "cooperator")
public class Cooperator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cooperator_seq_gen")
    @SequenceGenerator(name = "cooperator_seq_gen", allocationSize = 1, sequenceName = "cooperator_seq")
    private Long id;

    @Column(name = "record_of_service")
    private Byte recordOfService;

    @Column(name = "appointment", columnDefinition = "TEXT")
    private String appointment;

    @ManyToOne(cascade  = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "group_statistics",
               joinColumns = @JoinColumn(name = "cooperator_id"),
               inverseJoinColumns = @JoinColumn(name = "coop_file_id"))
    private Set<FileOfService> cooperatorFiles;

}
