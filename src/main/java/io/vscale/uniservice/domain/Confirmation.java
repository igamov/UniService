package io.vscale.uniservice.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Column;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;

import java.util.List;
import java.util.Set;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"profile", "fileOfService", "students"})
@ToString(exclude = {"profile", "fileOfService", "students"})
@Entity(name = "Confirmation")
@Table(name = "confirmation")
@Document(indexName = "confirmation", type = "confirmations")
public class Confirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "confirmation_seq_gen")
    @SequenceGenerator(name = "confirmation_seq_gen", allocationSize = 1, sequenceName = "confirmation_seq")
    private Long id;

    @Column(columnDefinition = "TEXT", name = "description")
    private String description;

    @ManyToOne(cascade  = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(cascade  = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "con_file_id")
    private FileOfService fileOfService;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "student_to_confirmation",
               joinColumns = @JoinColumn(name = "confirmation_id"),
               inverseJoinColumns = @JoinColumn(name = "confirm_student_id"))
    private Set<Student> students;

}
