package kr.co.sootechsys.scrobot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_scrin_mapng")
public class MenuScrinMapng {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "menu_scrin_mapng_id")
  private Long menuScrinMapngId;

  @Column(name = "menu_id")
  private String menuId;

  @Column(name = "scrin_id")
  private String scrinId;
}
