package com.example.shop.model;

import com.example.shop.model.type.CSBoardType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class CustomServiceBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false, length = 100)
    private String title;

    @Enumerated(EnumType.STRING)
    private CSBoardType boardType;

    @ColumnDefault("0")
    private int count;

    @Lob
    private String content;

    @CreationTimestamp
    private Timestamp createTime;

    @OneToMany(mappedBy = "customServiceBoard", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"customServiceBoard", "user"})
    private List<CustomServiceReply> replys;

    @Column(length = 1)
    @ColumnDefault("0")
    private int secret;

}
