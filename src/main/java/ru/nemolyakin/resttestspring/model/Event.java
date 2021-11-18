package ru.nemolyakin.resttestspring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "events")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Event extends BaseEntity {

    @Column(name = "file_id")
    private int fileId;

    @Column(name = "datetime")
    private Date date;

    @Column(name = "user_id")
    private int userId;

    @Transient
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false,  insertable=false, updatable=false)
    private File file;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}