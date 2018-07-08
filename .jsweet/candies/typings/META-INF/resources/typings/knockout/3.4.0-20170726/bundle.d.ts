/* Generated from Java with JSweet 2.0.0-rc1 - http://www.jsweet.org */
interface KnockoutNativeTemplateEngine {
    renderTemplateSource(templateSource : any, bindingContext : KnockoutBindingContext, options : any) : any[];

    renderTemplateSource(templateSource : any, bindingContext : KnockoutBindingContext) : any[];

    renderTemplateSource(templateSource : any) : any[];
}

interface KnockoutComputed<T> extends KnockoutObservable<T> {
    fn : KnockoutComputedFunctions<any>;

    dispose();

    isActive() : boolean;

    getDependenciesCount() : number;

    extend(requestedExtenders : any) : KnockoutComputed<T>;
}

interface KnockoutSubscribable<T> extends KnockoutSubscribableFunctions<T> {
    subscribe(callback : (p1: T) => void, target : any, event : "beforeChange") : KnockoutSubscription;

    subscribe(callback : (p1: T) => void, target : any, event : "change") : KnockoutSubscription;

    subscribe<TEvent>(callback : (p1: TEvent) => void, target : any, event : string) : KnockoutSubscription;

    extend(requestedExtenders : any) : KnockoutSubscribable<T>;

    getSubscriptionsCount() : number;

    subscribe(callback : (p1: T) => void, target : any) : KnockoutSubscription;

    subscribe(callback : (p1: T) => void) : KnockoutSubscription;
}

interface KnockoutComponents {
    register(componentName : string, config : KnockoutComponentTypes.Config);

    isRegistered(componentName : string) : boolean;

    unregister(componentName : string);

    get(componentName : string, callback : (p1: KnockoutComponentTypes.Definition) => void);

    clearCachedDefinition(componentName : string);

    defaultLoader : KnockoutComponentTypes.Loader;

    loaders : KnockoutComponentTypes.Loader[];

    getComponentNameForNode(node : Node) : string;

    register(componentName : string, config : KnockoutComponentTypes.EmptyConfig);
}

interface KnockoutMemoization {
    memoize(callback : () => string) : string;

    unmemoize(memoId : string, callbackParams : any[]) : boolean;

    unmemoizeDomNodeAndDescendants(domNode : any, extraCallbackParamsArray : any[]) : boolean;

    parseMemoText(memoText : string) : string;
}

interface KnockoutUtils {
    domData : any;

    domNodeDisposal : any;

    addOrRemoveItem<T>(array : T[], value : T, included : T);

    arrayFilter<T>(array : T[], predicate : (p1: T) => boolean) : T[];

    arrayFirst<T>(array : T[], predicate : (p1: T) => boolean, predicateOwner : any) : T;

    arrayForEach<T>(array : T[], action : (p1: T, p2: number) => void);

    arrayGetDistinctValues<T>(array : T[]) : T[];

    arrayIndexOf<T>(array : T[], item : T) : number;

    arrayMap<T, U>(array : T[], mapping : (p1: T) => U) : U[];

    arrayPushAll<T>(array : T[], valuesToPush : T[]) : T[];

    arrayRemoveItem(array : any[], itemToRemove : any);

    compareArrays<T>(a : T[], b : T[]) : Array<KnockoutArrayChange<T>>;

    extend(target : any, source : any) : any;

    fieldsIncludedWithJsonPost : any[];

    getFormFields(form : any, fieldName : string) : any[];

    objectForEach(obj : any, action : (p1: any, p2: any) => void);

    parseHtmlFragment(html : string) : any[];

    parseJson(jsonString : string) : any;

    postJson(urlOrForm : any, data : any, options : any);

    peekObservable<T>(value : KnockoutObservable<T>) : T;

    range(min : any, max : any) : any;

    registerEventHandler(element : any, eventType : any, handler : Function);

    setHtml(node : Element, html : () => string);

    setHtml(node : Element, html : string);

    setTextContent(element : any, textContent : string);

    stringifyJson(data : any, replacer : Function, space : string) : string;

    toggleDomNodeCssClass(node : any, className : string, shouldHaveClass : boolean);

    triggerEvent(element : any, eventType : any);

    unwrapObservable<T>(value : KnockoutObservable<T>) : T;

    arrayFirst<T>(array : T[], predicate : (p1: T) => boolean) : T;

    stringifyJson(data : any, replacer : Function) : string;

    stringifyJson(data : any) : string;

    addOrRemoveItem<T>(array : KnockoutObservable<T>, value : T, included : T);

    arrayPushAll<T>(array : KnockoutObservableArray<T>, valuesToPush : T[]) : T[];

    setTextContent(element : any, textContent : KnockoutObservable<string>);

    unwrapObservable<T>(value : T) : T;
}

interface KnockoutBindingHandler {
    after? : Array<string>;

    init? : any;

    update? : any;

    options? : any;

    preprocess? : (p1: string, p2: string, p3: (p1: string, p2: string) => void) => string;
}

interface KnockoutComputedStatic {
    fn : KnockoutComputedFunctions<any>;

    <T>() : KnockoutComputed<T>;

    <T>(func : () => T, context : any, options : any) : KnockoutComputed<T>;

    <T>(def : KnockoutComputedDefine<T>, context : any) : KnockoutComputed<T>;

    <T>(func : () => T, context : any) : KnockoutComputed<T>;

    <T>(func : () => T) : KnockoutComputed<T>;

    <T>(def : KnockoutComputedDefine<T>) : KnockoutComputed<T>;
}

declare var ko : KnockoutStatic;

declare var knockout : KnockoutStatic;


interface KnockoutTemplateSourcesDomElement {
    text() : any;

    text(value : any);

    data(key : string) : any;

    data(key : string, value : any) : any;
}

interface KnockoutObservable<T> extends KnockoutSubscribable<T> {
    () : T;

    (value : T);

    peek() : T;

    valueHasMutated? : any;

    valueWillMutate? : any;

    extend(requestedExtenders : any) : KnockoutObservable<T>;

    equalityComparer(a : any, b : any) : boolean;
}

interface KnockoutStatic {
    utils : KnockoutUtils;

    memoization : KnockoutMemoization;

    bindingHandlers : KnockoutBindingHandlers;

    getBindingHandler(handler : string) : KnockoutBindingHandler;

    virtualElements : KnockoutVirtualElements;

    extenders : KnockoutExtenders;

    applyBindings(viewModelOrBindingContext : any, rootNode : any);

    applyBindingsToDescendants(viewModelOrBindingContext : any, rootNode : any);

    applyBindingAccessorsToNode(node : Node, bindings : any, bindingContext : KnockoutBindingContext);

    applyBindingAccessorsToNode(node : Node, bindings : any, viewModel : any);

    applyBindingsToNode(node : Node, bindings : any, viewModelOrBindingContext : any) : any;

    subscribable : KnockoutSubscribableStatic;

    observable : any;

    computed : any;

    pureComputed<T>(evaluatorFunction : () => T, context : any) : KnockoutComputed<T>;

    pureComputed<T>(options : KnockoutComputedDefine<T>, context : any) : KnockoutComputed<T>;

    observableArray : any;

    contextFor(node : any) : any;

    isSubscribable(instance : any) : boolean;

    toJSON(viewModel : any, replacer : Function, space : any) : string;

    toJS(viewModel : any) : any;

    isObservable(instance : any) : boolean;

    isWriteableObservable(instance : any) : boolean;

    isComputed(instance : any) : boolean;

    dataFor(node : any) : any;

    removeNode(node : Element);

    cleanNode(node : Element) : Element;

    renderTemplate(template : Function, viewModel : any, options : any, target : any, renderMode : any) : any;

    renderTemplate(template : string, viewModel : any, options : any, target : any, renderMode : any) : any;

    unwrap<T>(value : KnockoutObservable<T>) : T;

    computedContext : KnockoutComputedContext;

    templateSources : KnockoutTemplateSources;

    templateEngine : any;

    templateRewriting : any;

    nativeTemplateEngine : any;

    jqueryTmplTemplateEngine : any;

    setTemplateEngine(templateEngine : KnockoutNativeTemplateEngine);

    renderTemplate(template : Function, dataOrBindingContext : KnockoutBindingContext, options : any, targetNodeOrNodeArray : Node, renderMode : string) : any;

    renderTemplate(template : any, dataOrBindingContext : KnockoutBindingContext, options : any, targetNodeOrNodeArray : Node, renderMode : string) : any;

    renderTemplate(template : Function, dataOrBindingContext : any, options : any, targetNodeOrNodeArray : Node, renderMode : string) : any;

    renderTemplate(template : any, dataOrBindingContext : any, options : any, targetNodeOrNodeArray : Node, renderMode : string) : any;

    renderTemplate(template : Function, dataOrBindingContext : KnockoutBindingContext, options : any, targetNodeOrNodeArray : Node[], renderMode : string) : any;

    renderTemplate(template : any, dataOrBindingContext : KnockoutBindingContext, options : any, targetNodeOrNodeArray : Node[], renderMode : string) : any;

    renderTemplate(template : Function, dataOrBindingContext : any, options : any, targetNodeOrNodeArray : Node[], renderMode : string) : any;

    renderTemplate(template : any, dataOrBindingContext : any, options : any, targetNodeOrNodeArray : Node[], renderMode : string) : any;

    renderTemplateForEach(template : Function, arrayOrObservableArray : any[], options : any, targetNode : Node, parentBindingContext : KnockoutBindingContext) : any;

    renderTemplateForEach(template : any, arrayOrObservableArray : any[], options : any, targetNode : Node, parentBindingContext : KnockoutBindingContext) : any;

    renderTemplateForEach(template : Function, arrayOrObservableArray : KnockoutObservable<any>, options : any, targetNode : Node, parentBindingContext : KnockoutBindingContext) : any;

    renderTemplateForEach(template : any, arrayOrObservableArray : KnockoutObservable<any>, options : any, targetNode : Node, parentBindingContext : KnockoutBindingContext) : any;

    expressionRewriting : any;

    bindingProvider : any;

    selectExtensions : any;

    components : KnockoutComponents;

    options : any;

    tasks : KnockoutTasks;

    onError? : (p1: Error) => void;

    applyBindings(viewModelOrBindingContext : any);

    applyBindings();

    applyBindingsToNode(node : Node, bindings : any) : any;

    pureComputed<T>(evaluatorFunction : () => T) : KnockoutComputed<T>;

    pureComputed<T>(options : KnockoutComputedDefine<T>) : KnockoutComputed<T>;

    toJSON(viewModel : any, replacer : Function) : string;

    toJSON(viewModel : any) : string;

    renderTemplate(template : Function, viewModel : any, options : any, target : any) : any;

    renderTemplate(template : Function, viewModel : any, options : any) : any;

    renderTemplate(template : Function, viewModel : any) : any;

    renderTemplate(template : string, viewModel : any, options : any, target : any) : any;

    renderTemplate(template : string, viewModel : any, options : any) : any;

    renderTemplate(template : string, viewModel : any) : any;

    unwrap<T>(value : T) : T;

    setTemplateEngine(templateEngine : any);
}

interface KnockoutAllBindingsAccessor {
    () : any;

    get(name : string) : any;

    has(name : string) : boolean;
}

interface KnockoutSubscription {
    dispose();
}

interface KnockoutObservableArray<T> extends KnockoutObservable<T[]> {
    subscribe(callback : (p1: KnockoutArrayChange<T>[]) => void, target : any, event : "arrayChange") : KnockoutSubscription;

    subscribe(callback : (p1: T[]) => void, target : any, event : "beforeChange") : KnockoutSubscription;

    subscribe(callback : (p1: T[]) => void, target : any, event : "change") : KnockoutSubscription;

    subscribe<TEvent>(callback : (p1: TEvent) => void, target : any, event : string) : KnockoutSubscription;

    extend(requestedExtenders : any) : KnockoutObservableArray<T>;

    indexOf(searchElement : T, fromIndex : number) : number;

    slice(start : number, end : number) : T[];

    splice(start : number) : T[];

    splice(start : number, deleteCount : number, ...items : T[]) : T[];

    pop() : T;

    push(...items : T[]);

    shift() : T;

    unshift(...items : T[]) : number;

    reverse() : KnockoutObservableArray<T>;

    sort() : KnockoutObservableArray<T>;

    sort(compareFunction : (p1: T, p2: T) => number) : KnockoutObservableArray<T>;

    [key : string]: ((KnockoutBindingHandler)|(any));

    replace(oldItem : T, newItem : T);

    remove(item : T) : T[];

    remove(removeFunction : (p1: T) => boolean) : T[];

    removeAll(items : T[]) : T[];

    removeAll() : T[];

    destroy(item : T);

    destroy(destroyFunction : (p1: T) => boolean);

    destroyAll(items : T[]);

    destroyAll();

    subscribe(callback : (p1: T[]) => void, target : any) : KnockoutSubscription;

    subscribe(callback : (p1: T[]) => void) : KnockoutSubscription;

    indexOf(searchElement : T) : number;

    slice(start : number) : T[];
}

interface KnockoutObservableArrayFunctions<T> {
    indexOf(searchElement : T, fromIndex : number) : number;

    slice(start : number, end : number) : T[];

    splice(start : number) : T[];

    splice(start : number, deleteCount : number, ...items : T[]) : T[];

    pop() : T;

    push(...items : T[]);

    shift() : T;

    unshift(...items : T[]) : number;

    reverse() : KnockoutObservableArray<T>;

    sort() : KnockoutObservableArray<T>;

    sort(compareFunction : (p1: T, p2: T) => number) : KnockoutObservableArray<T>;

    [key : string]: ((KnockoutBindingHandler)|(any));

    replace(oldItem : T, newItem : T);

    remove(item : T) : T[];

    remove(removeFunction : (p1: T) => boolean) : T[];

    removeAll(items : T[]) : T[];

    removeAll() : T[];

    destroy(item : T);

    destroy(destroyFunction : (p1: T) => boolean);

    destroyAll(items : T[]);

    destroyAll();

    indexOf(searchElement : T) : number;

    slice(start : number) : T[];
}

interface KnockoutTemplateSources {
    domElement : any;

    anonymousTemplate : any;
}

interface KnockoutBindingHandlers {
    [bindingHandler : string]: KnockoutBindingHandler;

    visible : KnockoutBindingHandler;

    text : KnockoutBindingHandler;

    html : KnockoutBindingHandler;

    css : KnockoutBindingHandler;

    style : KnockoutBindingHandler;

    attr : KnockoutBindingHandler;

    foreach : KnockoutBindingHandler;

    if : KnockoutBindingHandler;

    ifnot : KnockoutBindingHandler;

    with : KnockoutBindingHandler;

    click : KnockoutBindingHandler;

    event : KnockoutBindingHandler;

    submit : KnockoutBindingHandler;

    enable : KnockoutBindingHandler;

    disable : KnockoutBindingHandler;

    value : KnockoutBindingHandler;

    textInput : KnockoutBindingHandler;

    hasfocus : KnockoutBindingHandler;

    checked : KnockoutBindingHandler;

    options : KnockoutBindingHandler;

    selectedOptions : KnockoutBindingHandler;

    uniqueName : KnockoutBindingHandler;

    template : KnockoutBindingHandler;

    component : KnockoutBindingHandler;
}

interface KnockoutTasks {
    scheduler : (p1: Function) => any;

    schedule(task : Function) : number;

    cancel(handle : number);

    runEarly();
}

interface KnockoutObservableStatic {
    fn : KnockoutObservableFunctions<any>;

    <T>(value : T) : KnockoutObservable<T>;

    <T>() : KnockoutObservable<T>;
}

interface KnockoutVirtualElements {
    allowedBindings : any;

    emptyNode(node : KnockoutVirtualElement);

    firstChild(node : KnockoutVirtualElement) : KnockoutVirtualElement;

    insertAfter(container : KnockoutVirtualElement, nodeToInsert : Node, insertAfter : Node);

    nextSibling(node : KnockoutVirtualElement) : Node;

    prepend(node : KnockoutVirtualElement, toInsert : Node);

    setDomNodeChildren(node : KnockoutVirtualElement, newChildren : any);

    childNodes(node : KnockoutVirtualElement) : Node[];
}

interface StringTypes {}

declare namespace StringTypes {

    /**
     * Generated to type the string "beforeChange".
     * @exclude
     * @class
     */
    export interface beforeChange {    }

    /**
     * Generated to type the string "change".
     * @exclude
     * @class
     */
    export interface change {    }

    /**
     * Generated to type the string "arrayChange".
     * @exclude
     * @class
     */
    export interface arrayChange {    }

    /**
     * Generated to type the string "added".
     * @exclude
     * @class
     */
    export interface added {    }

    /**
     * Generated to type the string "deleted".
     * @exclude
     * @class
     */
    export interface deleted {    }

    /**
     * Generated to type the string "retained".
     * @exclude
     * @class
     */
    export interface retained {    }
}


interface KnockoutTemplateEngine extends KnockoutNativeTemplateEngine {
    createJavaScriptEvaluatorBlock(script : string) : string;

    makeTemplateSource(template : any, templateDocument : Document) : any;

    renderTemplate(template : any, bindingContext : KnockoutBindingContext, options : any, templateDocument : Document) : any;

    isTemplateRewritten(template : any, templateDocument : Document) : boolean;

    rewriteTemplate(template : any, rewriterCallback : Function, templateDocument : Document);

    makeTemplateSource(template : any) : any;
}

interface KnockoutComputedContext {
    getDependenciesCount() : number;

    isInitial : () => boolean;

    isSleeping : boolean;
}

interface KnockoutObservableArrayStatic {
    fn : KnockoutObservableArrayFunctions<any>;

    <T>(value : T[]) : KnockoutObservableArray<T>;

    <T>() : KnockoutObservableArray<T>;
}

interface KnockoutObservableFunctions<T> {
    [key : string]: ((KnockoutBindingHandler)|(any));

    equalityComparer(a : any, b : any) : boolean;
}

interface KnockoutSubscribableFunctions<T> {
    [key : string]: ((KnockoutBindingHandler)|(any));

    notifySubscribers(valueToWrite : T, event : string);

    notifySubscribers(valueToWrite : T);

    notifySubscribers();
}

declare namespace KnockoutComponentTypes {
    export interface EmptyConfig {    }
}
declare namespace KnockoutComponentTypes {
    export interface ComponentInfo {
        element : Node;

        templateNodes : Node[];
    }
}
declare namespace KnockoutComponentTypes {
    export interface AMDModule {
        require : string;
    }
}
declare namespace KnockoutComponentTypes {}
declare namespace KnockoutComponentTypes {
    export interface Loader {
        getConfig(componentName : string, callback : (p1: KnockoutComponentTypes.ComponentConfig) => void);

        loadComponent(componentName : string, config : KnockoutComponentTypes.ComponentConfig, callback : (p1: KnockoutComponentTypes.Definition) => void);

        loadTemplate(componentName : string, templateConfig : any, callback : (p1: Node[]) => void);

        loadViewModel(componentName : string, viewModelConfig : any, callback : (p1: any) => void);

        suppressLoaderExceptions? : boolean;
    }
}
declare namespace KnockoutComponentTypes {
    export interface TemplateElement {
        element : ((string)|(Node));
    }
}
declare namespace KnockoutComponentTypes {
    export interface ViewModelFactoryFunction {
        createViewModel : (p1: any, p2: KnockoutComponentTypes.ComponentInfo) => any;
    }
}
declare namespace KnockoutComponentTypes {
    export interface Definition {
        template : Node[];

        createViewModel(params : any, options : any) : any;
    }
}
declare namespace KnockoutComponentTypes {
    export interface ComponentConfig {
        viewModel? : any;

        template : any;

        createViewModel? : any;
    }
}
declare namespace KnockoutComponentTypes {
    export interface ViewModelSharedInstance {
        instance : any;
    }
}
declare namespace KnockoutComponentTypes {
    export interface ViewModelFunction {
        (params : any) : any;

        () : any;
    }
}
declare namespace KnockoutComponentTypes {
    export interface Config {
        viewModel? : any;

        template : any;

        synchronous? : boolean;
    }
}
interface KnockoutTemplateAnonymous extends KnockoutTemplateSourcesDomElement {
    nodes() : any;

    nodes(value : any);
}

interface KnockoutComputedDefine<T> {
    read() : T;

    write(value : T);

    disposeWhenNodeIsRemoved? : Node;

    disposeWhen() : boolean;

    owner? : any;

    deferEvaluation? : boolean;

    pure? : boolean;
}

interface KnockoutExtenders {
    throttle(target : any, timeout : number) : KnockoutComputed<any>;

    notify(target : any, notifyWhen : string) : any;

    rateLimit(target : any, timeout : number) : any;

    rateLimit(target : any, options : any) : any;

    trackArrayChanges(target : any) : any;
}

interface KnockoutArrayChange<T> {
    status : ((StringTypes.added)|(StringTypes.deleted)|(StringTypes.retained));

    value : T;

    index : number;

    moved? : number;
}

interface KnockoutSubscribableStatic {
    fn : KnockoutSubscribableFunctions<any>;
}

interface KnockoutBindingProvider {
    nodeHasBindings(node : Node) : boolean;

    getBindings(node : Node, bindingContext : KnockoutBindingContext) : any;

    getBindingAccessors(node : Node, bindingContext : KnockoutBindingContext) : any;
}

interface KnockoutBindingContext {
    $parent : any;

    $parents : any[];

    $root : any;

    $data : any;

    $rawData : ((any)|(KnockoutObservable<any>));

    $index? : any;

    $parentContext? : KnockoutBindingContext;

    $component : any;

    $componentTemplateNodes : Node[];

    extend(properties : any) : any;

    createChildContext(dataItemOrAccessor : any, dataItemAlias : any, extendCallback : Function) : any;

    createChildContext(dataItemOrAccessor : any, dataItemAlias : any) : any;

    createChildContext(dataItemOrAccessor : any) : any;
}

interface KnockoutVirtualElement {}

interface KnockoutComputedFunctions<T> {
    [key : string]: ((KnockoutBindingHandler)|(any));
}



declare module "knockout";
