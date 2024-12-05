package yung.dongnae_fit.domain.facility.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Facility {
    @Id
    private Long id;

    private String name;
    private String filter;
    private String type;
    private String province;
    private String district;
    private String addr;
    private Double latitude;
    private Double longitude;

}
