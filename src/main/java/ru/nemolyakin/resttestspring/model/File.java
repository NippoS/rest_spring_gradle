package ru.nemolyakin.resttestspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.net.URL;

@Entity
@Table(name = "files")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class File extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private URL location;

    @Transient
    @JsonIgnore
    @OneToOne(mappedBy = "files")
    private Event event;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}