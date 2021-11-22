package ru.nemolyakin.resttestspring.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Table(name = "files")
public class FileEntity extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "file")
    private List<EventEntity> eventEntity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}