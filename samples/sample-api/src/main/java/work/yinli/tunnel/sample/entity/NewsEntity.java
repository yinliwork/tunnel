package work.yinli.tunnel.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsEntity {
    private Integer id;
    private String title;
    private String content;
}
