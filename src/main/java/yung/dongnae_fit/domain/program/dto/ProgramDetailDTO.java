package yung.dongnae_fit.domain.program.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yung.dongnae_fit.domain.program.entity.Program;

@Data
@NoArgsConstructor
public class ProgramDetailDTO {
    private Long programId;
    private boolean programSaveStatus;
    private String programName;
    private String programStart;
    private String programEnd;
    private String programTarget;
    private String programWeek;
    private String programTime;
    private Long programPrice;
    private String programUrl;
    @Builder
    public ProgramDetailDTO(Program program, boolean programSaveStatus) {
        this.programId = program.getId();
        this.programSaveStatus = programSaveStatus;
        this.programName = program.getName();
        this.programStart = program.getStart();
        this.programEnd = program.getEnd();
        this.programTarget = program.getTarget();
        this.programWeek = program.getWeek();
        this.programTime = program.getTime();
        this.programPrice = program.getPrice();
        this.programUrl = program.getUrl();
    }
}
