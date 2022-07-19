package work.yinli.tunnel.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsEntity {
    private Integer id;
    private String title;
    private String content;
}
