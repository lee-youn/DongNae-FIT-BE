package yung.dongnae_fit.domain.programFacility.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yung.dongnae_fit.domain.program.entity.Program;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramFacility {

    @Id
    private Long id;
    private String name;
    private String type;
    private String province;
    private String district;
    private String addr;
    private String latitude;
    private String longitude;
    private String image;

    @OneToMany(mappedBy = "programFacility")
    private List<Program> programs = new ArrayList<>();
}
