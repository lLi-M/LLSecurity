package ll.security.formProperties.vaildproperties;

import lombok.Data;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/13
  Time: 17:22
*/
@Data
public class VaildCodeProperties {
    private Integer width =80;
    private Integer length =32;
    private String  urls;
    private Integer expire =180;
}
