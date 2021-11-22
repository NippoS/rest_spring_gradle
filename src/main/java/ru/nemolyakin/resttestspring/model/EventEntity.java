package ru.nemolyakin.resttestspring.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "events")
public class EventEntity extends BaseEntity {

    @Column(name = "created")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}