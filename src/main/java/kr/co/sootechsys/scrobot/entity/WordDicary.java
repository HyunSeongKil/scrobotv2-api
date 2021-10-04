package kr.co.sootechsys.scrobot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "word_dicary")
public class WordDicary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_dicary_id")
    private Integer wordDicaryId;

    @Column(name = "word_nm")
    private String wordNm;

    @Column(name = "word_eng_nm")
    private String wordEngNm;

    @Column(name = "eng_abrv_nm")
    private String engAbrvNm;

    @Column(name = "cn", length = 4000)
    private String cn;

    @Column(name = "thema_relm_nm")
    private String themaRelmNm;

    @Column(name = "regist_dt")
    private Date registDt;
    
}
