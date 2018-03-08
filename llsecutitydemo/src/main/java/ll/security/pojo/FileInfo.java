package ll.security.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
  Created by IntelliJ IDEA.
  User: 李 雷
  Date: 2018/2/11
  Time: 13:13
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    private String path;
    private String name;
    private String originalName;
}
