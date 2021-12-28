package hello.hellospring.domain;

import javax.persistence.*;

// JPA가 관리하는 Entity
@Entity
public class Member {

    // PK매핑, DB에서 id를 자동 생성=>Identity전략,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
