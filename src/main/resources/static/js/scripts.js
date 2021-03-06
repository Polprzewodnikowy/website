// add classes to html tags generated by flexmark parser

function Element(htmlTag, htmlClass) {
    this.htmlTag = htmlTag;
    this.htmlClass = htmlClass;
}

var elements = [
    new Element('h1', 'entry-h1 py-2'),
    new Element('h2', 'entry-h2 py-2'),
    new Element('hr', 'entry-hr'),
    new Element('table', 'entry-table table table-bordered table-striped'),
    new Element('img', 'entry-img img-fluid'),
    new Element('blockquote', 'entry-blockquote pl-3 text-muted')
];

function fixMarkdown() {
    elements.forEach(function(element, index, array) {
        $('.entry-contents ' + element.htmlTag).each(function(i, tag) {
            tag.setAttribute('class', element.htmlClass);
        });
    });
    $('.entry-contents .youtube').wrap('<div class="youtube-container"></div>');
}

function previewEntryReady(data, textStatus, jqXHR) {
    var previewTitle = data.title;
    var previewBody = data.body;
    $('#preview-title').html(previewTitle);
    $('#preview-body').html(previewBody);
    fixMarkdown();
    $('.entry').removeClass('invisible');
    $('pre code').each(function(i, block) {
        hljs.highlightBlock(block);
    });
}

function previewEntry() {
    var data = {_csrf: $("input[name='_csrf']").val(), title: $('#entry-title').val(), body: $('#entry-body').val()}
    $.post("/blog/edit/preview", data, previewEntryReady);
}

$(document).ready(fixMarkdown);
