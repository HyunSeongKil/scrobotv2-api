package kr.co.sootechsys.scrobot.controller;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.javassist.tools.reflect.Reflection;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/xxx")
@Slf4j
public class XxxRestController {

  @GetMapping()
  public void xxx() throws IOException {
    StringBuffer sb = new StringBuffer();

    org.reflections.Reflections reflections = new org.reflections.Reflections("kr.co.sootechsys", new SubTypesScanner(false));
    reflections.getAllTypes().forEach(x -> {
      try {
        Class clz = Class.forName(x);
        Annotation an = clz.getAnnotation(Api.class);
        if (null != an) {
          sb.append("\n" + clz.getSimpleName() + "\t" + ((Api) an).value());
        }
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });

    Files.writeString(Paths.get("c:\\", "temp", "a.csv"), sb.toString());
  }


  @GetMapping(value = "/model")
  public void model() throws IOException {
    StringBuffer sb = new StringBuffer();

    org.reflections.Reflections reflections = new org.reflections.Reflections("kr.co.sootechsys", new SubTypesScanner(false));
    reflections.getAllTypes().forEach(x -> {
      try {
        Class clz = Class.forName(x);
        Annotation an = clz.getAnnotation(ApiModel.class);
        if (null == an) {
          return;
        }

        String tableNm = ((Table) clz.getAnnotation(Table.class)).name();
        String tableComment = ((ApiModel) an).value();
        sb.append("\n\n" + tableNm + "\t" + tableComment);
        sb.append("\n컬럼명\t컬럼ID\tdatatype\tPK\tFK\t널허용");

        Field[] fields = clz.getDeclaredFields();
        for (Field f : fields) {
          sb.append(a(f));
        }

      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });

    Files.writeString(Paths.get("c:\\", "temp", "a.txt"), sb.toString());
  }


  private String a(Field f) {
    if (null == f) {
      return "";
    }

    Annotation c = f.getAnnotation(Column.class);

    if (null == c) {
      return "";
    }

    Annotation apiModelProperty = f.getAnnotation(ApiModelProperty.class);
    if (null == apiModelProperty) {
      return "";
    }

    String pk = "N";
    String isNull = "Y";
    if (null != f.getAnnotation(Id.class)) {
      pk = "Y";
      isNull = "N";
    }

    return "\n" + ((Column) c).name() + "\t" + ((ApiModelProperty) apiModelProperty).value() + "\t" + f.getType() + "\t" + pk + "\tN" + "\t" + isNull;

  }

}
