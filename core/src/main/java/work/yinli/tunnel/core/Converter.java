package work.yinli.tunnel.core;

/**
 * @author yangji
 */
public interface Converter<Source, Target> {
    /**
     * 数据转换
     *
     * @param source 输入源
     * @return 输出
     */
    Target convert(Source source);
}
