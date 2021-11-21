package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
@ApiModel(value = "코멘트")
public class Comment {
  @Id
  @Column(name = "comment_id")
  private Long commentId;

  @Column(name = "comment_sj_nm")
  private String commentSjNm;

  @Column(name = "comment_cn")
  private String commentCn;

  @Column(name = "register_id")
  private String registerId;

  @Column(name = "register_nm")
  private String registerNm;

  @Column(name = "regist_dt")
  private java.util.Date registDt;
}
