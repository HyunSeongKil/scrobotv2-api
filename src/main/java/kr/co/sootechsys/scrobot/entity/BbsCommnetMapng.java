package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bbs_comment_mapng")
public class BbsCommnetMapng {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bbs_comment_mapng_id")

  private Long bbsCommentMapngId;

  @Column(name = "bbs_id")
  private Long bbsId;

  @Column(name = "comment_id")
  private Long commentId;
}
