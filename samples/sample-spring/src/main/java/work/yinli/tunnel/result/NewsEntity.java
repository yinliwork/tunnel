package work.yinli.tunnel.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsEntity {
    private Integer id;
    private String title;
    private String content;
}
