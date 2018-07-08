/* Generated from Java with JSweet 2.0.0-rc1 - http://www.jsweet.org */
declare var marked : any;


interface MarkedOptions {
    /**
     * Type: object Default: new Renderer()
     * 
     * An object containing functions to render tokens to HTML.
     */
    renderer? : MarkedRenderer;

    /**
     * Enable GitHub flavored markdown.
     */
    gfm? : boolean;

    /**
     * Enable GFM tables. This option requires the gfm option to be true.
     */
    tables? : boolean;

    /**
     * Enable GFM line breaks. This option requires the gfm option to be true.
     */
    breaks? : boolean;

    /**
     * Conform to obscure parts of markdown.pl as much as possible. Don't fix any of the original markdown bugs or poor behavior.
     */
    pedantic? : boolean;

    /**
     * Sanitize the output. Ignore any HTML that has been input.
     */
    sanitize? : boolean;

    /**
     * Use smarter list behavior than the original markdown. May eventually be default with the old behavior moved into pedantic.
     */
    smartLists? : boolean;

    /**
     * Shows an HTML error message when rendering fails.
     */
    silent? : boolean;

    /**
     * A function to highlight code blocks. The function takes three arguments: code, lang, and callback.
     * @param {string} code
     * @param {string} lang
     * @param {Function} callback
     * @return {string}
     */
    highlight(code : string, lang : string, callback : Function) : string;

    /**
     * Set the prefix for code block classes.
     */
    langPrefix? : string;

    /**
     * Use "smart" typograhic punctuation for things like quotes and dashes.
     */
    smartypants? : boolean;

    /**
     * A function to highlight code blocks. The function takes three arguments: code, lang, and callback.
     * @param {string} code
     * @param {string} lang
     * @return {string}
     */
    highlight(code : string, lang : string) : string;
}

interface MarkedStatic {
    /**
     * Compiles markdown to HTML.
     * 
     * @param {string} src String of markdown source to be compiled
     * @param {Function} callback Function called when the markdownString has been fully parsed when using async highlighting
     * @return {string} String of compiled HTML
     */
    (src : string, callback : Function) : string;

    /**
     * Compiles markdown to HTML.
     * 
     * @param {string} src String of markdown source to be compiled
     * @param {*} options Hash of options
     * @param {Function} callback Function called when the markdownString has been fully parsed when using async highlighting
     * @return {string} String of compiled HTML
     */
    (src : string, options : MarkedOptions, callback : Function) : string;

    /**
     * @param {string} src String of markdown source to be compiled
     * @param {*} options Hash of options
     * @return {Array}
     */
    lexer(src : string, options : MarkedOptions) : any[];

    /**
     * Compiles markdown to HTML.
     * 
     * @param {string} src String of markdown source to be compiled
     * @param {Function} callback Function called when the markdownString has been fully parsed when using async highlighting
     * @return {string} String of compiled HTML
     */
    parse(src : string, callback : Function) : string;

    /**
     * Compiles markdown to HTML.
     * 
     * @param {string} src String of markdown source to be compiled
     * @param {*} options Hash of options
     * @param {Function} callback Function called when the markdownString has been fully parsed when using async highlighting
     * @return {string} String of compiled HTML
     */
    parse(src : string, options : MarkedOptions, callback : Function) : string;

    /**
     * @param {*} options Hash of options
     * @param {Array} src
     * @return {string}
     */
    parser(src : any[], options : MarkedOptions) : string;

    /**
     * Sets the default options.
     * 
     * @param {*} options Hash of options
     * @return {*}
     */
    setOptions(options : MarkedOptions) : MarkedStatic;

    Renderer : any;

    Parser : any;

    /**
     * Compiles markdown to HTML.
     * 
     * @param {string} src String of markdown source to be compiled
     * @param {*} options Hash of options
     * @param callback Function called when the markdownString has been fully parsed when using async highlighting
     * @return {string} String of compiled HTML
     */
    (src : string, options : MarkedOptions) : string;

    /**
     * Compiles markdown to HTML.
     * 
     * @param {string} src String of markdown source to be compiled
     * @param options Hash of options
     * @param callback Function called when the markdownString has been fully parsed when using async highlighting
     * @return {string} String of compiled HTML
     */
    (src : string) : string;

    /**
     * @param {string} src String of markdown source to be compiled
     * @param options Hash of options
     * @return {Array}
     */
    lexer(src : string) : any[];

    /**
     * Compiles markdown to HTML.
     * 
     * @param {string} src String of markdown source to be compiled
     * @param {*} options Hash of options
     * @param callback Function called when the markdownString has been fully parsed when using async highlighting
     * @return {string} String of compiled HTML
     */
    parse(src : string, options : MarkedOptions) : string;

    /**
     * Compiles markdown to HTML.
     * 
     * @param {string} src String of markdown source to be compiled
     * @param options Hash of options
     * @param callback Function called when the markdownString has been fully parsed when using async highlighting
     * @return {string} String of compiled HTML
     */
    parse(src : string) : string;

    /**
     * @param options Hash of options
     * @param {Array} src
     * @return {string}
     */
    parser(src : any[]) : string;
}

interface MarkedRenderer {
    code(code : string, language : string) : string;

    blockquote(quote : string) : string;

    html(html : string) : string;

    heading(text : string, level : number, raw : string) : string;

    hr() : string;

    list(body : string, ordered : boolean) : string;

    listitem(text : string) : string;

    paragraph(text : string) : string;

    table(header : string, body : string) : string;

    tablerow(content : string) : string;

    tablecell(content : string, flags : any) : string;

    strong(text : string) : string;

    em(text : string) : string;

    codespan(code : string) : string;

    br() : string;

    del(text : string) : string;

    link(href : string, title : string, text : string) : string;

    image(href : string, title : string, text : string) : string;

    text(text : string) : string;
}

interface MarkedParser {
    parse(source : any[]) : string;
}



declare module "marked";
