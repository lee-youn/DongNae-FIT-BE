package yung.dongnae_fit.domain.facility.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    Long id;
    String name;
    String filter;
    String type;
    String province;
    String district;
    String addr;
    String latitude;
    String longitude;

}
