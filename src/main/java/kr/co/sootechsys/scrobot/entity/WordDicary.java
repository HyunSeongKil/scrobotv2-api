package kr.co.sootechsys.scrobot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "word_dicary")
@ApiModel(value = "용어 사전 엔티티")
public class WordDicary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_dicary_id")
    @ApiModelProperty(value = "용어 사전 아이디")
    private Integer wordDicaryId;

    @Column(name = "word_nm")
    @ApiModelProperty(value = "단어 명")
    private String wordNm;

    @Column(name = "word_eng_nm")
    @ApiModelProperty(value = "단어 영문 명")
    private String wordEngNm;

    @Column(name = "eng_abrv_nm")
    @ApiModelProperty(value = "영문 약어 명")
    private String engAbrvNm;

    @Column(name = "cn", length = 4000)
    @ApiModelProperty(value = "내용")
    private String cn;

    @Column(name = "thema_relm_nm")
    @ApiModelProperty(value = "주제 명")
    private String themaRelmNm;

    @Column(name = "regist_dt")
    @ApiModelProperty(value = "등록 일시")
    private Date registDt;

}
