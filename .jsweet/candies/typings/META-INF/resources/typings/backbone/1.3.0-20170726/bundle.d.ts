/* Generated from Java with JSweet 2.0.0-rc1 - http://www.jsweet.org */
declare namespace Backbone {
    export interface CollectionFetchOptions extends Backbone.PersistenceOptions {
        reset? : boolean;

        parse? : any;
    }
}
declare namespace Backbone {
    export interface Parseable {
        parse? : any;
    }
}
declare namespace Backbone {
    export class ModelBase extends Backbone.Events {
        public url : any;

        public parse(response : any, options : any) : any;

        public toJSON(options : any) : any;

        public sync(...arg : any[]) : JQueryXHR;

        public parse(response : any) : any;

        public toJSON() : any;
    }
}
declare namespace Backbone {
    export interface RouterOptions {
        routes : any;
    }
}
declare namespace Backbone {
    export class Router extends Backbone.Events {
        public routes : ((Backbone.RoutesHash)|(any));

        public constructor(options : Backbone.RouterOptions);

        public initialize(options : Backbone.RouterOptions);

        public route(route : string, name : string, callback : Function) : Router;

        public navigate(fragment : string, options : Backbone.NavigateOptions) : Router;

        public navigate(fragment : string, trigger : boolean) : Router;

        public execute(callback : Function, args : any[], name : string);

        public constructor();

        public initialize();

        public route(route : string, name : string) : Router;

        public navigate(fragment : string) : Router;

        public route(route : RegExp, name : string, callback : Function) : Router;

        public route(route : RegExp, name : string) : Router;
    }
}
declare namespace Backbone {
    export interface Validable {
        validate? : boolean;
    }
}
declare namespace Backbone {
    export interface AddOptions extends Backbone.Silenceable {
        at? : number;

        merge? : boolean;
    }
}
declare namespace Backbone {}
declare namespace Backbone {
    export var history : Backbone.History;

    export function sync(method : string, model : Backbone.Model, options : JQueryAjaxSettings) : any;

    export function ajax(options : JQueryAjaxSettings) : JQueryXHR;

    export var emulateHTTP : boolean;

    export var emulateJSON : boolean;

    export var $ : any;

    export function sync(method : string, model : Backbone.Model) : any;

    export function ajax() : JQueryXHR;

}
declare namespace Backbone {
    export interface EventsHash {
        [selector : string]: ((string)|(any));
    }
}
declare namespace Backbone {
    export interface ObjectHash {
        [key : string]: any;
    }
}
declare namespace Backbone {
    export interface Silenceable {
        silent? : boolean;
    }
}
declare namespace Backbone {
    export class History extends Backbone.Events {
        public handlers : any[];

        public interval : number;

        public start(options : Backbone.HistoryOptions) : boolean;

        public getHash(window : Window) : string;

        public getFragment(fragment : string) : string;

        public decodeFragment(fragment : string) : string;

        public getSearch() : string;

        public stop();

        public route(route : string, callback : Function) : number;

        public checkUrl(e : any);

        public getPath() : string;

        public matchRoot() : boolean;

        public atRoot() : boolean;

        public loadUrl(fragmentOverride : string) : boolean;

        public navigate(fragment : string, options : any) : boolean;

        public static started : boolean;

        public options : any;

        public start() : boolean;

        public getHash() : string;

        public getFragment() : string;

        public checkUrl();

        public loadUrl() : boolean;

        public navigate(fragment : string) : boolean;
    }
}
declare namespace Backbone {
    export interface ModelSaveOptions extends Backbone.Silenceable {
        patch? : boolean;

        wait? : any;

        validate? : boolean;

        parse? : any;

        url? : string;

        data? : any;

        beforeSend? : (p1: JQueryXHR) => void;

        success? : (p1: any, p2: any, p3: any) => void;

        error? : (p1: any, p2: JQueryXHR, p3: any) => void;
    }
}
declare namespace Backbone {
    export class Collection<TModel extends Backbone.Model> extends Backbone.ModelBase {
        public model : (p1: any) => TModel;

        public models : TModel[];

        public length : number;

        public constructor(models : TModel[], options : any);

        public initialize(models : TModel[], options : any);

        public fetch(options : Backbone.CollectionFetchOptions) : JQueryXHR;

        /**
         * Specify a model attribute name (string) or function that will be used to sort the collection.
         */
        public comparator : ((string)|((p1: TModel) => ((number)|(string)))|((p1: TModel, p2: TModel) => number));

        public add(model : any, options : Backbone.AddOptions) : TModel;

        public add(models : any[], options : Backbone.AddOptions) : TModel[];

        public at(index : number) : TModel;

        public get(id : number) : TModel;

        public has(key : number) : boolean;

        public create(attributes : any, options : Backbone.ModelSaveOptions) : TModel;

        public pluck(attribute : string) : any[];

        public push(model : TModel, options : Backbone.AddOptions) : TModel;

        public pop(options : Backbone.Silenceable) : TModel;

        public remove(model : any, options : Backbone.Silenceable) : TModel;

        public remove(models : any[], options : Backbone.Silenceable) : TModel[];

        public reset(models : TModel[], options : Backbone.Silenceable) : TModel[];

        public set(models : TModel[], options : Backbone.Silenceable) : TModel[];

        public shift(options : Backbone.Silenceable) : TModel;

        public sort(options : Backbone.Silenceable) : Collection<TModel>;

        public unshift(model : TModel, options : Backbone.AddOptions) : TModel;

        public where(properties : any) : TModel[];

        public findWhere(properties : any) : TModel;

        public modelId(attrs : any) : any;

        /**
         * Return a shallow copy of this collection's models, using the same options as native Array#slice.
         * @param {number} min
         * @param {number} max
         * @return {Array}
         */
        public slice(min : number, max : number) : TModel[];

        public all(iterator : _.ListIterator<TModel, boolean>, context : any) : boolean;

        public any(iterator : _.ListIterator<TModel, boolean>, context : any) : boolean;

        public chain() : any;

        public collect<TResult>(iterator : _.ListIterator<TModel, TResult>, context : any) : TResult[];

        public contains(value : TModel) : boolean;

        public countBy(iterator : _.ListIterator<TModel, any>) : _.Dictionary<number>;

        public countBy(iterator : string) : _.Dictionary<number>;

        public detect(iterator : _.ListIterator<TModel, boolean>, context : any) : TModel;

        public difference(others : TModel[]) : TModel[];

        public drop(n : number) : TModel[];

        public each(iterator : _.ListIterator<TModel, void>, context : any) : TModel[];

        public every(iterator : _.ListIterator<TModel, boolean>, context : any) : boolean;

        public filter(iterator : _.ListIterator<TModel, boolean>, context : any) : TModel[];

        public find(iterator : _.ListIterator<TModel, boolean>, context : any) : TModel;

        public findIndex(predicate : _.ListIterator<TModel, boolean>, context : any) : number;

        public findLastIndex(predicate : _.ListIterator<TModel, boolean>, context : any) : number;

        public first() : TModel;

        public first(n : number) : TModel[];

        public foldl<TResult>(iterator : _.MemoIterator<TModel, TResult>, memo : TResult, context : any) : TResult;

        public foldr<TResult>(iterator : _.MemoIterator<TModel, TResult>, memo : TResult, context : any) : TResult;

        public forEach(iterator : _.ListIterator<TModel, void>, context : any) : TModel[];

        public groupBy(iterator : _.ListIterator<TModel, any>, context : any) : _.Dictionary<TModel[]>;

        public groupBy(iterator : string, context : any) : _.Dictionary<TModel[]>;

        public head() : TModel;

        public head(n : number) : TModel[];

        public include(value : TModel) : boolean;

        public includes(value : TModel) : boolean;

        public indexBy(iterator : _.ListIterator<TModel, any>, context : any) : _.Dictionary<TModel>;

        public indexBy(iterator : string, context : any) : _.Dictionary<TModel>;

        public indexOf(value : TModel, isSorted : boolean) : number;

        public initial() : TModel;

        public initial(n : number) : TModel[];

        public inject<TResult>(iterator : _.MemoIterator<TModel, TResult>, memo : TResult, context : any) : TResult;

        public invoke(methodName : string, ...args : any[]) : any;

        public isEmpty() : boolean;

        public last() : TModel;

        public last(n : number) : TModel[];

        public lastIndexOf(value : TModel, from : number) : number;

        public map<TResult>(iterator : _.ListIterator<TModel, TResult>, context : any) : TResult[];

        public max(iterator : _.ListIterator<TModel, any>, context : any) : TModel;

        public min(iterator : _.ListIterator<TModel, any>, context : any) : TModel;

        public partition(iterator : _.ListIterator<TModel, boolean>) : TModel[][];

        public reduce<TResult>(iterator : _.MemoIterator<TModel, TResult>, memo : TResult, context : any) : TResult;

        public reduceRight<TResult>(iterator : _.MemoIterator<TModel, TResult>, memo : TResult, context : any) : TResult;

        public reject(iterator : _.ListIterator<TModel, boolean>, context : any) : TModel[];

        public rest(n : number) : TModel[];

        public sample() : TModel;

        public sample(n : number) : TModel[];

        public select(iterator : _.ListIterator<TModel, boolean>, context : any) : TModel[];

        public shuffle() : TModel[];

        public size() : number;

        public some(iterator : _.ListIterator<TModel, boolean>, context : any) : boolean;

        public sortBy<TSort>(iterator : _.ListIterator<TModel, TSort>, context : any) : TModel[];

        public sortBy(iterator : string, context : any) : TModel[];

        public tail(n : number) : TModel[];

        public take() : TModel;

        public take(n : number) : TModel[];

        public toArray() : TModel[];

        public without(...values : TModel[]) : TModel[];

        public constructor(models : TModel[]);

        public constructor();

        public initialize(models : TModel[]);

        public initialize();

        public fetch() : JQueryXHR;

        public add(model : any) : TModel;

        public add(models : any[]) : TModel[];

        public push(model : TModel) : TModel;

        public pop() : TModel;

        public remove(model : any) : TModel;

        public remove(models : any[]) : TModel[];

        public reset(models : TModel[]) : TModel[];

        public reset() : TModel[];

        public set(models : TModel[]) : TModel[];

        public set() : TModel[];

        public shift() : TModel;

        public sort() : Collection<TModel>;

        public unshift(model : TModel) : TModel;

        /**
         * Return a shallow copy of this collection's models, using the same options as native Array#slice.
         * @param {number} min
         * @return {Array}
         */
        public slice(min : number) : TModel[];

        public all(iterator : _.ListIterator<TModel, boolean>) : boolean;

        public all() : boolean;

        public any(iterator : _.ListIterator<TModel, boolean>) : boolean;

        public any() : boolean;

        public collect<TResult>(iterator : _.ListIterator<TModel, TResult>) : TResult[];

        public countBy() : _.Dictionary<number>;

        public detect(iterator : _.ListIterator<TModel, boolean>) : TModel;

        public drop() : TModel[];

        public each(iterator : _.ListIterator<TModel, void>) : TModel[];

        public every(iterator : _.ListIterator<TModel, boolean>) : boolean;

        public filter(iterator : _.ListIterator<TModel, boolean>) : TModel[];

        public find(iterator : _.ListIterator<TModel, boolean>) : TModel;

        public findIndex(predicate : _.ListIterator<TModel, boolean>) : number;

        public findLastIndex(predicate : _.ListIterator<TModel, boolean>) : number;

        public foldl<TResult>(iterator : _.MemoIterator<TModel, TResult>, memo : TResult) : TResult;

        public foldl<TResult>(iterator : _.MemoIterator<TModel, TResult>) : TResult;

        public foldr<TResult>(iterator : _.MemoIterator<TModel, TResult>, memo : TResult) : TResult;

        public foldr<TResult>(iterator : _.MemoIterator<TModel, TResult>) : TResult;

        public forEach(iterator : _.ListIterator<TModel, void>) : TModel[];

        public groupBy(iterator : _.ListIterator<TModel, any>) : _.Dictionary<TModel[]>;

        public groupBy(iterator : string) : _.Dictionary<TModel[]>;

        public indexBy(iterator : _.ListIterator<TModel, any>) : _.Dictionary<TModel>;

        public indexBy(iterator : string) : _.Dictionary<TModel>;

        public indexOf(value : TModel) : number;

        public inject<TResult>(iterator : _.MemoIterator<TModel, TResult>, memo : TResult) : TResult;

        public inject<TResult>(iterator : _.MemoIterator<TModel, TResult>) : TResult;

        public lastIndexOf(value : TModel) : number;

        public map<TResult>(iterator : _.ListIterator<TModel, TResult>) : TResult[];

        public max(iterator : _.ListIterator<TModel, any>) : TModel;

        public max() : TModel;

        public min(iterator : _.ListIterator<TModel, any>) : TModel;

        public min() : TModel;

        public reduce<TResult>(iterator : _.MemoIterator<TModel, TResult>, memo : TResult) : TResult;

        public reduce<TResult>(iterator : _.MemoIterator<TModel, TResult>) : TResult;

        public reduceRight<TResult>(iterator : _.MemoIterator<TModel, TResult>, memo : TResult) : TResult;

        public reduceRight<TResult>(iterator : _.MemoIterator<TModel, TResult>) : TResult;

        public reject(iterator : _.ListIterator<TModel, boolean>) : TModel[];

        public rest() : TModel[];

        public select(iterator : _.ListIterator<TModel, boolean>) : TModel[];

        public some(iterator : _.ListIterator<TModel, boolean>) : boolean;

        public some() : boolean;

        public sortBy<TSort>(iterator : _.ListIterator<TModel, TSort>) : TModel[];

        public sortBy<TSort>() : TModel[];

        public sortBy(iterator : string) : TModel[];

        public tail() : TModel[];

        public constructor(models : any[], options : any);

        public initialize(models : any[], options : any);

        public add(model : TModel, options : Backbone.AddOptions) : TModel;

        public add(models : TModel[], options : Backbone.AddOptions) : TModel[];

        public get(id : Backbone.Model) : TModel;

        public get(id : string) : TModel;

        public has(key : Backbone.Model) : boolean;

        public has(key : string) : boolean;

        public remove(model : TModel, options : Backbone.Silenceable) : TModel;

        public remove(models : TModel[], options : Backbone.Silenceable) : TModel[];

        public constructor(models : any[]);

        public initialize(models : any[]);

        public add(model : TModel) : TModel;

        public add(models : TModel[]) : TModel[];

        public remove(model : TModel) : TModel;

        public remove(models : TModel[]) : TModel[];
    }
}
declare namespace Backbone {
    export class View<TModel extends Backbone.Model> extends Backbone.Events {
        public constructor(options : Backbone.ViewOptions<TModel>);

        public initialize(options : Backbone.ViewOptions<TModel>);

        public events() : Backbone.EventsHash;

        public $(selector : string) : JQuery;

        public model : TModel;

        public collection : Backbone.Collection<TModel>;

        public setElement(element : HTMLElement, delegate : boolean) : View<TModel>;

        public id : string;

        public cid : string;

        public className : string;

        public tagName : string;

        public el : any;

        public $el : JQuery;

        public setElement(element : any) : View<TModel>;

        public attributes : any;

        public $(selector : any) : JQuery;

        public render() : View<TModel>;

        public remove() : View<TModel>;

        public delegateEvents(events : Backbone.EventsHash) : any;

        public delegate(eventName : string, selector : string, listener : Function) : View<TModel>;

        public undelegateEvents() : any;

        public undelegate(eventName : string, selector : string, listener : Function) : View<TModel>;

        public _ensureElement();

        public constructor();

        public initialize();

        public setElement(element : HTMLElement) : View<TModel>;

        public delegateEvents() : any;

        public undelegate(eventName : string, selector : string) : View<TModel>;

        public undelegate(eventName : string) : View<TModel>;

        public setElement(element : JQuery, delegate : boolean) : View<TModel>;

        public setElement(element : JQuery) : View<TModel>;
    }
}
declare namespace Backbone {
    export interface ModelFetchOptions extends Backbone.PersistenceOptions {
        validate? : boolean;

        parse? : any;
    }
}
declare namespace Backbone {
    export interface HistoryOptions extends Backbone.Silenceable {
        pushState? : boolean;

        root? : string;
    }
}
declare namespace Backbone {
    export interface ModelSetOptions extends Backbone.Silenceable {
        validate? : boolean;
    }
}
declare namespace Backbone {
    export interface ViewOptions<TModel extends Backbone.Model> {
        model? : TModel;

        collection? : Backbone.Collection<Backbone.Model>;

        el? : any;

        events? : Backbone.EventsHash;

        id? : string;

        className? : string;

        tagName? : string;

        attributes? : any;
    }
}
declare namespace Backbone {
    export interface NavigateOptions {
        trigger? : boolean;

        replace? : boolean;
    }
}
declare namespace Backbone {
    export interface RoutesHash {
        [routePattern : string]: ((string)|(any));
    }
}
declare namespace Backbone {
    export class Model extends Backbone.ModelBase {
        public attributes : any;

        public changed : any[];

        public cidPrefix : string;

        public cid : string;

        public collection : Backbone.Collection<Model>;

        public defaults() : Backbone.ObjectHash;

        public id : any;

        public idAttribute : string;

        public validationError : any;

        public urlRoot : any;

        public constructor(attributes : any, options : any);

        public initialize(attributes : any, options : any);

        public fetch(options : Backbone.ModelFetchOptions) : JQueryXHR;

        public get(attributeName : string) : any;

        public set(attributeName : string, value : any, options : Backbone.ModelSetOptions) : Model;

        public set(obj : any, options : Backbone.ModelSetOptions) : Model;

        /**
         * Return an object containing all the attributes that have changed, or
         * false if there are no changed attributes. Useful for determining what
         * parts of a view need to be updated and/or what attributes need to be
         * persisted to the server. Unset attributes will be set to undefined.
         * You can also pass an attributes object to diff against the model,
         * determining if there *would be* a change.
         * @param {*} attributes
         * @return {*}
         */
        public changedAttributes(attributes : any) : any;

        public clear(options : Backbone.Silenceable) : any;

        public clone() : Model;

        public destroy(options : Backbone.ModelDestroyOptions) : any;

        public escape(attribute : string) : string;

        public has(attribute : string) : boolean;

        public hasChanged(attribute : string) : boolean;

        public isNew() : boolean;

        public isValid(options : any) : boolean;

        public previous(attribute : string) : any;

        public previousAttributes() : any[];

        public save(attributes : any, options : Backbone.ModelSaveOptions) : any;

        public unset(attribute : string, options : Backbone.Silenceable) : Model;

        public validate(attributes : any, options : any) : any;

        public keys() : string[];

        public values() : any[];

        public pairs() : any[];

        public invert() : any;

        public pick(...keys : string[]) : any;

        public pick(fn : (p1: any, p2: any, p3: any) => any) : any;

        public omit(...keys : string[]) : any;

        public omit(fn : (p1: any, p2: any, p3: any) => any) : any;

        public chain() : any;

        public isEmpty() : boolean;

        public matches(attrs : any) : boolean;

        public constructor(attributes : any);

        public constructor();

        public initialize(attributes : any);

        public initialize();

        public fetch() : JQueryXHR;

        public set(attributeName : string, value : any) : Model;

        public set(obj : any) : Model;

        /**
         * Return an object containing all the attributes that have changed, or
         * false if there are no changed attributes. Useful for determining what
         * parts of a view need to be updated and/or what attributes need to be
         * persisted to the server. Unset attributes will be set to undefined.
         * You can also pass an attributes object to diff against the model,
         * determining if there *would be* a change.
         * @return {*}
         */
        public changedAttributes() : any;

        public clear() : any;

        public destroy() : any;

        public hasChanged() : boolean;

        public isValid() : boolean;

        public save(attributes : any) : any;

        public save() : any;

        public unset(attribute : string) : Model;

        public validate(attributes : any) : any;
    }
}
declare namespace Backbone {
    export class Events {
        public on(eventName : string, callback : Function, context : any) : any;

        public on(eventMap : Backbone.EventsHash) : any;

        public off(eventName : string, callback : Function, context : any) : any;

        public trigger(eventName : string, ...args : any[]) : any;

        public bind(eventName : string, callback : Function, context : any) : any;

        public unbind(eventName : string, callback : Function, context : any) : any;

        public once(events : string, callback : Function, context : any) : any;

        public listenTo(object : any, events : string, callback : Function) : any;

        public listenToOnce(object : any, events : string, callback : Function) : any;

        public stopListening(object : any, events : string, callback : Function) : any;

        public on(eventName : string, callback : Function) : any;

        public on(eventName : string) : any;

        public off(eventName : string, callback : Function) : any;

        public off(eventName : string) : any;

        public off() : any;

        public bind(eventName : string, callback : Function) : any;

        public unbind(eventName : string, callback : Function) : any;

        public unbind(eventName : string) : any;

        public unbind() : any;

        public once(events : string, callback : Function) : any;

        public stopListening(object : any, events : string) : any;

        public stopListening(object : any) : any;

        public stopListening() : any;
    }
}
declare namespace Backbone {
    export interface ModelDestroyOptions extends Backbone.Waitable {
        url? : string;

        data? : any;

        beforeSend? : (p1: JQueryXHR) => void;

        success? : (p1: any, p2: any, p3: any) => void;

        error? : (p1: any, p2: JQueryXHR, p3: any) => void;
    }
}
declare namespace Backbone {
    export interface PersistenceOptions {
        url? : string;

        data? : any;

        beforeSend? : (p1: JQueryXHR) => void;

        success? : (p1: any, p2: any, p3: any) => void;

        error? : (p1: any, p2: JQueryXHR, p3: any) => void;
    }
}
declare namespace Backbone {
    export interface Waitable {
        wait? : any;
    }
}

