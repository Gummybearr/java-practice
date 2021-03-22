package study.datajpa.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberDTO {

    private Long id;
    private String username;
    private String teamName;

    public MemberDTO(Long id, String username, String teamName){
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }
}
