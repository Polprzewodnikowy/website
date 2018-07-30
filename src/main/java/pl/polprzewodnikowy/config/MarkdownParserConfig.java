package pl.polprzewodnikowy.config;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
class MarkdownParserConfig {

    @Bean
    public MutableDataSet mutableDataSet() {
        return new MutableDataSet()
                .set(HtmlRenderer.SOFT_BREAK, "<br/>")
                .set(TablesExtension.COLUMN_SPANS, false)
                .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
                .set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()));
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
