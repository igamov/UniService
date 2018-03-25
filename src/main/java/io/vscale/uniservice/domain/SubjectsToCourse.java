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
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.SequenceGenerator;

/**
 * 02.03.2018
 *
 * @author Dias Arkharov
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Entity(name = "SubjectsToCourse")
@Table(name = "subjects_to_course")
public class SubjectsToCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "course_seq_gen")
    @SequenceGenerator(name = "course_seq_gen", allocationSize = 1, sequenceName = "course_seq")
    private Byte courseNumber;

    @Column(name = "subject_name")
    private String subjectName;

}
