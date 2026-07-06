package com.example.common.service.entities;
import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.NoArgsConstructor;
        import lombok.Setter;

        import java.time.LocalDateTime;

@Entity
@Table(name = "ROOM_MEMBER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomMemberEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "CONTRACT_ID", nullable = false)
    private Long contractId;

}