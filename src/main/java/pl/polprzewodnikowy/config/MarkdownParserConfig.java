package pl.polprzewodnikowy.config;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MarkdownParserConfig {

    @Bean
    public MutableDataSet mutableDataSet() {
        MutableDataSet options = new MutableDataSet();
        return options;
    }

    @Bean
    public Parser parser(MutableDataSet options) {
        return Parser.builder(options).build();
    }

    @Bean
    public HtmlRenderer htmlRenderer(MutableDataSet options) {
        return HtmlRenderer.builder(options).build();
    }

}
