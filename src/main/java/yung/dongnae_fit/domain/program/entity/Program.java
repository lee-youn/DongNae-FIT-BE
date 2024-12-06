package yung.dongnae_fit.domain.program.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yung.dongnae_fit.domain.programFacility.entity.ProgramFacility;
import yung.dongnae_fit.domain.review.entity.Review;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Program {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "program_facility_id")
    private ProgramFacility programFacility;

    private Long facilityId;
    private String name;
    private String target;
    private String start;
    private String end;
    private String week;
    private String time;
    private Long recruit;
    private Long price;
    private String url;

    @OneToMany(mappedBy = "program")
    private List<Review> reviews = new ArrayList<>();
}
