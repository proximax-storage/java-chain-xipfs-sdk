/* Generated from Java with JSweet 2.0.0-rc1 - http://www.jsweet.org */
declare namespace _ {
    export interface _ChainSingle<T> {
        value() : T;
    }
}
declare namespace _ {
    export interface _ChainOfArrays<T> extends _._Chain<T[]> {
        flatten(shallow : boolean) : _._Chain<T>;

        mapObject(fn : _.ListIterator<T, any>) : _ChainOfArrays<T>;

        flatten() : _._Chain<T>;
    }
}
declare namespace _ {
    export interface Cancelable {
        cancel();
    }
}
declare namespace _ {
    export interface Dictionary<T> extends _.Collection<T> {
        [index : string]: T;
    }
}
declare namespace _ {
    export interface ThrottleSettings {
        leading? : boolean;

        trailing? : boolean;
    }
}
declare namespace _ {}
declare namespace _ {
    export interface TemplateSettings {
        evaluate? : RegExp;

        interpolate? : RegExp;

        escape? : RegExp;

        variable? : string;
    }
}
declare namespace _ {
    export interface _Chain<T> {
        each(iterator : _.ListIterator<T, void>, context : any) : _Chain<T>;

        each(iterator : _.ObjectIterator<T, void>, context : any) : _Chain<T>;

        forEach(iterator : _.ListIterator<T, void>, context : any) : _Chain<T>;

        forEach(iterator : _.ObjectIterator<T, void>, context : any) : _Chain<T>;

        map<TArray>(iterator : any, context : any) : _._ChainOfArrays<TArray>;

        map<TResult>(iterator : any, context : any) : _Chain<TResult>;

        map<TArray>(iterator : any, context : any) : _._ChainOfArrays<TArray>;

        map<TResult>(iterator : any, context : any) : _Chain<TResult>;

        collect<TResult>(iterator : _.ListIterator<T, TResult>, context : any) : _Chain<TResult>;

        collect<TResult>(iterator : _.ObjectIterator<T, TResult>, context : any) : _Chain<TResult>;

        reduce<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : _._ChainSingle<TResult>;

        inject<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : _._ChainSingle<TResult>;

        foldl<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : _._ChainSingle<TResult>;

        reduceRight<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : _._ChainSingle<TResult>;

        foldr<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : _._ChainSingle<TResult>;

        find<T>(iterator : _.ListIterator<T, boolean>, context : any) : _._ChainSingle<T>;

        find<T, U extends any>(interator : U) : _._ChainSingle<T>;

        find<T>(interator : string) : _._ChainSingle<T>;

        detect<T>(iterator : _.ListIterator<T, boolean>, context : any) : _._ChainSingle<T>;

        detect<T, U extends any>(interator : U) : _._ChainSingle<T>;

        detect<T>(interator : string) : _._ChainSingle<T>;

        filter(iterator : _.ListIterator<T, boolean>, context : any) : _Chain<T>;

        select(iterator : _.ListIterator<T, boolean>, context : any) : _Chain<T>;

        where<U extends any>(properties : U) : _Chain<T>;

        findWhere<U extends any>(properties : U) : _._ChainSingle<T>;

        reject(iterator : _.ListIterator<T, boolean>, context : any) : _Chain<T>;

        all(iterator : _.ListIterator<T, boolean>, context : any) : _._ChainSingle<boolean>;

        every(iterator : _.ListIterator<T, boolean>, context : any) : _._ChainSingle<boolean>;

        any(iterator : _.ListIterator<T, boolean>, context : any) : _._ChainSingle<boolean>;

        some(iterator : _.ListIterator<T, boolean>, context : any) : _._ChainSingle<boolean>;

        contains(value : T, fromIndex : number) : _._ChainSingle<boolean>;

        include(value : T, fromIndex : number) : _._ChainSingle<boolean>;

        includes(value : T, fromIndex : number) : _._ChainSingle<boolean>;

        invoke(methodName : string, ...args : any[]) : _Chain<T>;

        pluck(propertyName : string) : _Chain<any>;

        max() : _._ChainSingle<T>;

        max(iterator : any, context : any) : _._ChainSingle<T>;

        max(iterator : any, context : any) : _._ChainSingle<T>;

        min() : _._ChainSingle<T>;

        min(iterator : any, context : any) : _._ChainSingle<T>;

        min(iterator : any, context : any) : _._ChainSingle<T>;

        sortBy(iterator : _.ListIterator<T, any>, context : any) : _Chain<T>;

        sortBy(iterator : string, context : any) : _Chain<T>;

        groupBy(iterator : _.ListIterator<T, any>, context : any) : _._ChainOfArrays<T>;

        groupBy(iterator : string, context : any) : _._ChainOfArrays<T>;

        indexBy(iterator : _.ListIterator<T, any>, context : any) : _Chain<T>;

        indexBy(iterator : string, context : any) : _Chain<T>;

        countBy(iterator : _.ListIterator<T, any>, context : any) : _Chain<T>;

        countBy(iterator : string, context : any) : _Chain<T>;

        shuffle() : _Chain<T>;

        sample<T>(n : number) : _Chain<T>;

        sample<T>() : _Chain<T>;

        toArray() : _Chain<T>;

        size() : _._ChainSingle<number>;

        first() : _._ChainSingle<T>;

        first(n : number) : _Chain<T>;

        head() : _Chain<T>;

        head(n : number) : _Chain<T>;

        take() : _Chain<T>;

        take(n : number) : _Chain<T>;

        initial(n : number) : _Chain<T>;

        last() : _._ChainSingle<T>;

        last(n : number) : _Chain<T>;

        rest(n : number) : _Chain<T>;

        tail(n : number) : _Chain<T>;

        drop(n : number) : _Chain<T>;

        compact() : _Chain<T>;

        flatten(shallow : boolean) : _Chain<any>;

        without(...values : T[]) : _Chain<T>;

        partition(iterator : _.ListIterator<T, boolean>, context : any) : _Chain<T[]>;

        union(...arrays : _.List<T>[]) : _Chain<T>;

        intersection(...arrays : _.List<T>[]) : _Chain<T>;

        difference(...others : _.List<T>[]) : _Chain<T>;

        uniq(isSorted : boolean, iterator : _.ListIterator<T, any>) : _Chain<T>;

        uniq<TSort>(iterator : _.ListIterator<T, TSort>, context : any) : _Chain<T>;

        unique<TSort>(isSorted : boolean, iterator : _.ListIterator<T, TSort>) : _Chain<T>;

        unique<TSort>(iterator : _.ListIterator<T, TSort>, context : any) : _Chain<T>;

        zip(...arrays : any[][]) : _Chain<T>;

        unzip(...arrays : any[][]) : _Chain<T>;

        object(...keyValuePairs : any[][]) : _Chain<T>;

        object(values : any) : _Chain<T>;

        indexOf(value : T, isSorted : boolean) : _._ChainSingle<number>;

        indexOf(value : T, startFrom : number) : _._ChainSingle<number>;

        lastIndexOf(value : T, from : number) : _._ChainSingle<number>;

        findIndex<T>(predicate : _.ListIterator<T, boolean>, context : any) : _._ChainSingle<number>;

        findLastIndex<T>(predicate : _.ListIterator<T, boolean>, context : any) : _._ChainSingle<number>;

        sortedIndex(value : T, iterator : (p1: T) => any, context : any) : _._ChainSingle<number>;

        range(stop : number, step : number) : _Chain<T>;

        range() : _Chain<T>;

        chunk() : _Chain<T>;

        bind(object : any, ...args : any[]) : _Chain<T>;

        bindAll(...methodNames : string[]) : _Chain<T>;

        partial(...args : any[]) : _Chain<T>;

        memoize(hashFn : (p1: any) => string) : _Chain<T>;

        defer(...args : any[]) : _Chain<T>;

        delay(wait : number, ...args : any[]) : _Chain<T>;

        delay(...args : any[]) : _Chain<T>;

        throttle(wait : number, options : _.ThrottleSettings) : _Chain<T>;

        debounce(wait : number, immediate : boolean) : _Chain<T>;

        once() : _Chain<T>;

        restArgs(startIndex : number) : _Chain<T>;

        after(func : Function) : _Chain<T>;

        before(fn : Function) : _Chain<T>;

        wrap(wrapper : Function) : () => _Chain<T>;

        negate() : _Chain<T>;

        compose(...functions : Function[]) : _Chain<T>;

        keys() : _Chain<string>;

        allKeys() : _Chain<string>;

        values() : _Chain<T>;

        pairs() : _Chain<T>;

        invert() : _Chain<T>;

        functions() : _Chain<T>;

        methods() : _Chain<T>;

        extend(...sources : any[]) : _Chain<T>;

        findKey(predicate : _.ObjectIterator<any, boolean>, context : any) : _Chain<T>;

        pick(keys : any[]) : _Chain<T>;

        pick(fn : (p1: any, p2: any, p3: any) => any) : _Chain<T>;

        omit(keys : string[]) : _Chain<T>;

        omit(iteratee : Function) : _Chain<T>;

        defaults(...defaults : any[]) : _Chain<T>;

        clone() : _Chain<T>;

        tap(interceptor : (p1: any) => any) : _Chain<T>;

        has(key : string) : _Chain<T>;

        matches<TResult>() : _Chain<T>;

        matcher<TResult>() : _Chain<T>;

        property() : _Chain<T>;

        propertyOf() : _Chain<T>;

        isEqual(other : any) : _Chain<T>;

        isEmpty() : _Chain<T>;

        isMatch() : _Chain<T>;

        isElement() : _Chain<T>;

        isArray() : _Chain<T>;

        isSymbol() : _Chain<T>;

        isObject() : _Chain<T>;

        isArguments() : _Chain<T>;

        isFunction() : _Chain<T>;

        isError() : _Chain<T>;

        isString() : _Chain<T>;

        isNumber() : _Chain<T>;

        isFinite() : _Chain<T>;

        isBoolean() : _Chain<T>;

        isDate() : _Chain<T>;

        isRegExp() : _Chain<T>;

        isNaN() : _Chain<T>;

        isNull() : _Chain<T>;

        isUndefined() : _Chain<T>;

        identity() : _Chain<T>;

        constant() : _Chain<T>;

        noop() : _Chain<T>;

        times<TResult>(iterator : (p1: number) => TResult, context : any) : _Chain<T>;

        random() : _Chain<T>;

        random(max : number) : _Chain<T>;

        mixin() : _Chain<T>;

        iteratee(context : any) : _Chain<T>;

        uniqueId() : _Chain<T>;

        escape() : _Chain<T>;

        unescape() : _Chain<T>;

        result(property : string, defaultValue : any) : _Chain<T>;

        template(settings : _.TemplateSettings) : (p1: any) => _Chain<T>;

        concat(...arr : Array<T[]>[]) : _Chain<T>;

        join(separator : any) : _._ChainSingle<T>;

        pop() : _._ChainSingle<T>;

        push(...item : Array<T>[]) : _Chain<T>;

        reverse() : _Chain<T>;

        shift() : _._ChainSingle<T>;

        slice(start : number, end : number) : _Chain<T>;

        sort(compareFn : (p1: T, p2: T) => boolean) : _Chain<T>;

        splice(index : number, quantity : number, ...items : Array<T>[]) : _Chain<T>;

        toString() : _._ChainSingle<T>;

        unshift(...items : Array<T>[]) : _Chain<T>;

        chain() : _Chain<T>;

        value<TResult>() : T[];

        each(iterator : _.ListIterator<T, void>) : _Chain<T>;

        each(iterator : _.ObjectIterator<T, void>) : _Chain<T>;

        forEach(iterator : _.ListIterator<T, void>) : _Chain<T>;

        forEach(iterator : _.ObjectIterator<T, void>) : _Chain<T>;

        map<TArray>(iterator : _.ListIterator<T, TArray[]>) : _._ChainOfArrays<TArray>;

        map<TArray>(iterator : _.ObjectIterator<T, TArray[]>) : _._ChainOfArrays<TArray>;

        collect<TResult>(iterator : _.ListIterator<T, TResult>) : _Chain<TResult>;

        collect<TResult>(iterator : _.ObjectIterator<T, TResult>) : _Chain<TResult>;

        reduce<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult) : _._ChainSingle<TResult>;

        reduce<TResult>(iterator : _.MemoIterator<T, TResult>) : _._ChainSingle<TResult>;

        inject<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult) : _._ChainSingle<TResult>;

        inject<TResult>(iterator : _.MemoIterator<T, TResult>) : _._ChainSingle<TResult>;

        foldl<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult) : _._ChainSingle<TResult>;

        foldl<TResult>(iterator : _.MemoIterator<T, TResult>) : _._ChainSingle<TResult>;

        reduceRight<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult) : _._ChainSingle<TResult>;

        reduceRight<TResult>(iterator : _.MemoIterator<T, TResult>) : _._ChainSingle<TResult>;

        foldr<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult) : _._ChainSingle<TResult>;

        foldr<TResult>(iterator : _.MemoIterator<T, TResult>) : _._ChainSingle<TResult>;

        find<T>(iterator : _.ListIterator<T, boolean>) : _._ChainSingle<T>;

        detect<T>(iterator : _.ListIterator<T, boolean>) : _._ChainSingle<T>;

        filter(iterator : _.ListIterator<T, boolean>) : _Chain<T>;

        select(iterator : _.ListIterator<T, boolean>) : _Chain<T>;

        reject(iterator : _.ListIterator<T, boolean>) : _Chain<T>;

        all(iterator : _.ListIterator<T, boolean>) : _._ChainSingle<boolean>;

        all() : _._ChainSingle<boolean>;

        every(iterator : _.ListIterator<T, boolean>) : _._ChainSingle<boolean>;

        every() : _._ChainSingle<boolean>;

        any(iterator : _.ListIterator<T, boolean>) : _._ChainSingle<boolean>;

        any() : _._ChainSingle<boolean>;

        some(iterator : _.ListIterator<T, boolean>) : _._ChainSingle<boolean>;

        some() : _._ChainSingle<boolean>;

        contains(value : T) : _._ChainSingle<boolean>;

        include(value : T) : _._ChainSingle<boolean>;

        includes(value : T) : _._ChainSingle<boolean>;

        max(iterator : _.ListIterator<T, number>) : _._ChainSingle<T>;

        min(iterator : _.ListIterator<T, number>) : _._ChainSingle<T>;

        sortBy(iterator : _.ListIterator<T, any>) : _Chain<T>;

        sortBy() : _Chain<T>;

        sortBy(iterator : string) : _Chain<T>;

        groupBy(iterator : _.ListIterator<T, any>) : _._ChainOfArrays<T>;

        groupBy() : _._ChainOfArrays<T>;

        groupBy(iterator : string) : _._ChainOfArrays<T>;

        indexBy(iterator : _.ListIterator<T, any>) : _Chain<T>;

        indexBy(iterator : string) : _Chain<T>;

        countBy(iterator : _.ListIterator<T, any>) : _Chain<T>;

        countBy() : _Chain<T>;

        countBy(iterator : string) : _Chain<T>;

        initial() : _Chain<T>;

        rest() : _Chain<T>;

        tail() : _Chain<T>;

        drop() : _Chain<T>;

        flatten() : _Chain<any>;

        partition(iterator : _.ListIterator<T, boolean>) : _Chain<T[]>;

        uniq(isSorted : boolean) : _Chain<T>;

        uniq() : _Chain<T>;

        uniq<TSort>(iterator : _.ListIterator<T, TSort>) : _Chain<T>;

        unique<TSort>(isSorted : boolean) : _Chain<T>;

        unique<TSort>() : _Chain<T>;

        unique<TSort>(iterator : _.ListIterator<T, TSort>) : _Chain<T>;

        object() : _Chain<T>;

        indexOf(value : T) : _._ChainSingle<number>;

        lastIndexOf(value : T) : _._ChainSingle<number>;

        findIndex<T>(predicate : _.ListIterator<T, boolean>) : _._ChainSingle<number>;

        findLastIndex<T>(predicate : _.ListIterator<T, boolean>) : _._ChainSingle<number>;

        sortedIndex(value : T, iterator : (p1: T) => any) : _._ChainSingle<number>;

        sortedIndex(value : T) : _._ChainSingle<number>;

        range(stop : number) : _Chain<T>;

        memoize() : _Chain<T>;

        throttle(wait : number) : _Chain<T>;

        debounce(wait : number) : _Chain<T>;

        restArgs() : _Chain<T>;

        findKey(predicate : _.ObjectIterator<any, boolean>) : _Chain<T>;

        create() : _Chain<T>;

        times<TResult>(iterator : (p1: number) => TResult) : _Chain<T>;

        iteratee() : _Chain<T>;

        result(property : string) : _Chain<T>;

        template() : (p1: any) => _Chain<T>;

        join() : _._ChainSingle<T>;

        slice(start : number) : _Chain<T>;

        find<T>(iterator : _.ObjectIterator<T, boolean>, context : any) : _._ChainSingle<T>;

        detect<T>(iterator : _.ObjectIterator<T, boolean>, context : any) : _._ChainSingle<T>;

        findIndex<T>(predicate : any, context : any) : _._ChainSingle<number>;

        findLastIndex<T>(predicate : any, context : any) : _._ChainSingle<number>;

        find<T>(iterator : _.ObjectIterator<T, boolean>) : _._ChainSingle<T>;

        detect<T>(iterator : _.ObjectIterator<T, boolean>) : _._ChainSingle<T>;

        findIndex<T>(predicate : any) : _._ChainSingle<number>;

        findLastIndex<T>(predicate : any) : _._ChainSingle<number>;

        concat(...arr : T[][]) : _Chain<T>;

        push(...item : T[]) : _Chain<T>;

        splice(index : number, quantity : number, ...items : T[]) : _Chain<T>;

        unshift(...items : T[]) : _Chain<T>;
    }
}
declare namespace _ {
    export interface ObjectIterator<T, TResult> {
        (element : T, key : string, list : _.Dictionary<T>) : TResult;
    }
}
declare namespace _ {
    export interface Collection<T> {    }
}
declare namespace _ {
    export interface UnderscoreStatic {
        <T>(value : _.Dictionary<T>) : _.Underscore<T>;

        <T>(value : Array<T>) : _.Underscore<T>;

        <T>(value : T) : _.Underscore<T>;

        each<T>(list : _.List<T>, iterator : _.ListIterator<T, void>, context : any) : _.List<T>;

        each<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, void>, context : any) : _.Dictionary<T>;

        forEach<T>(list : _.List<T>, iterator : _.ListIterator<T, void>, context : any) : _.List<T>;

        forEach<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, void>, context : any) : _.Dictionary<T>;

        map<T, TResult>(list : _.List<T>, iterator : any, context : any) : TResult[];

        map<T, TResult>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, TResult>, context : any) : TResult[];

        collect<T, TResult>(list : _.List<T>, iterator : any, context : any) : TResult[];

        collect<T, TResult>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, TResult>, context : any) : TResult[];

        reduce<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : TResult;

        reduce<T, TResult>(list : _.Dictionary<T>, iterator : _.MemoObjectIterator<T, TResult>, memo : TResult, context : any) : TResult;

        inject<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : TResult;

        foldl<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : TResult;

        reduceRight<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : TResult;

        foldr<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : TResult;

        find<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>, context : any) : T;

        find<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>, context : any) : T;

        find<T, U extends any>(object : _.List<T>, iterator : U) : T;

        find<T>(object : _.List<T>, iterator : string) : T;

        detect<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>, context : any) : T;

        detect<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>, context : any) : T;

        detect<T, U extends any>(object : _.List<T>, iterator : U) : T;

        detect<T>(object : _.List<T>, iterator : string) : T;

        filter<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>, context : any) : T[];

        filter<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>, context : any) : T[];

        select<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>, context : any) : T[];

        select<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>, context : any) : T[];

        where<T, U extends any>(list : _.List<T>, properties : U) : T[];

        findWhere<T, U extends any>(list : _.List<T>, properties : U) : T;

        reject<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>, context : any) : T[];

        reject<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>, context : any) : T[];

        every<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>, context : any) : boolean;

        every<T>(list : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>, context : any) : boolean;

        all<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>, context : any) : boolean;

        all<T>(list : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>, context : any) : boolean;

        some<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>, context : any) : boolean;

        some<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>, context : any) : boolean;

        any<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>, context : any) : boolean;

        any<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>, context : any) : boolean;

        any<T>(list : _.List<T>, value : T) : boolean;

        contains<T>(list : _.List<T>, value : T, fromIndex : number) : boolean;

        contains<T>(object : _.Dictionary<T>, value : T) : boolean;

        include<T>(list : _.Collection<T>, value : T, fromIndex : number) : boolean;

        include<T>(object : _.Dictionary<T>, value : T) : boolean;

        includes<T>(list : _.Collection<T>, value : T, fromIndex : number) : boolean;

        includes<T>(object : _.Dictionary<T>, value : T) : boolean;

        invoke<T extends any>(list : _.List<T>, methodName : string, ...args : any[]) : any;

        pluck<T extends any>(list : _.List<T>, propertyName : string) : any[];

        max(list : _.List<number>) : number;

        /**
         * @see _.max
         * @param {*} object
         * @return {number}
         */
        max(object : _.Dictionary<number>) : number;

        max<T>(list : _.List<T>, iterator : _.ListIterator<T, any>, context : any) : T;

        /**
         * @see _.max
         * @param {*} list
         * @param {*} iterator
         * @param {*} context
         * @return {*}
         */
        max<T>(list : _.Dictionary<T>, iterator : _.ObjectIterator<T, any>, context : any) : T;

        min(list : _.List<number>) : number;

        /**
         * @see _.min
         * @param {*} o
         * @return {number}
         */
        min(o : _.Dictionary<number>) : number;

        min<T>(list : _.List<T>, iterator : _.ListIterator<T, any>, context : any) : T;

        /**
         * @see _.min
         * @param {*} list
         * @param {*} iterator
         * @param {*} context
         * @return {*}
         */
        min<T>(list : _.Dictionary<T>, iterator : _.ObjectIterator<T, any>, context : any) : T;

        sortBy<T, TSort>(list : _.List<T>, iterator : _.ListIterator<T, TSort>, context : any) : T[];

        sortBy<T>(list : _.List<T>, iterator : string, context : any) : T[];

        groupBy<T>(list : _.List<T>, iterator : _.ListIterator<T, any>, context : any) : _.Dictionary<T[]>;

        groupBy<T>(list : _.List<T>, iterator : string, context : any) : _.Dictionary<T[]>;

        indexBy<T>(list : _.List<T>, iterator : _.ListIterator<T, any>, context : any) : _.Dictionary<T>;

        indexBy<T>(list : _.List<T>, iterator : string, context : any) : _.Dictionary<T>;

        countBy<T>(list : _.List<T>, iterator : _.ListIterator<T, any>, context : any) : _.Dictionary<number>;

        countBy<T>(list : _.List<T>, iterator : string, context : any) : _.Dictionary<number>;

        shuffle<T>(list : _.Collection<T>) : T[];

        sample<T>(list : _.Collection<T>, n : number) : T[];

        sample<T>(list : _.Collection<T>) : T;

        toArray<T>(list : _.Collection<T>) : T[];

        size<T>(list : _.Collection<T>) : number;

        partition<T>(array : Array<T>, iterator : _.ListIterator<T, boolean>, context : any) : T[][];

        first<T>(array : _.List<T>) : T;

        first<T>(array : _.List<T>, n : number) : T[];

        head<T>(array : _.List<T>) : T;

        head<T>(array : _.List<T>, n : number) : T[];

        take<T>(array : _.List<T>) : T;

        take<T>(array : _.List<T>, n : number) : T[];

        initial<T>(array : _.List<T>, n : number) : T[];

        last<T>(array : _.List<T>) : T;

        last<T>(array : _.List<T>, n : number) : T[];

        rest<T>(array : _.List<T>, n : number) : T[];

        tail<T>(array : _.List<T>, n : number) : T[];

        drop<T>(array : _.List<T>, n : number) : T[];

        compact<T>(array : _.List<T>) : T[];

        flatten(array : _.List<any>, shallow : boolean) : any[];

        without<T>(array : _.List<T>, ...values : T[]) : T[];

        union<T>(...arrays : _.List<T>[]) : T[];

        intersection<T>(...arrays : _.List<T>[]) : T[];

        difference<T>(array : _.List<T>, ...others : _.List<T>[]) : T[];

        uniq<T, TSort>(array : _.List<T>, isSorted : boolean, iterator : _.ListIterator<T, TSort>, context : any) : T[];

        uniq<T, TSort>(array : _.List<T>, iterator : _.ListIterator<T, TSort>, context : any) : T[];

        unique<T, TSort>(array : _.List<T>, iterator : _.ListIterator<T, TSort>, context : any) : T[];

        unique<T, TSort>(array : _.List<T>, isSorted : boolean, iterator : _.ListIterator<T, TSort>, context : any) : T[];

        zip(...arrays : any[][]) : any[][];

        zip(...arrays : any[]) : any[];

        unzip(...arrays : any[][]) : any[][];

        object<TResult extends any>(keys : _.List<string>, values : _.List<any>) : TResult;

        object<TResult extends any>(...keyValuePairs : any[][]) : TResult;

        object<TResult extends any>(list : _.List<any>, values : any) : TResult;

        indexOf<T>(array : _.List<T>, value : T, isSorted : boolean) : number;

        indexOf<T>(array : _.List<T>, value : T, startFrom : number) : number;

        lastIndexOf<T>(array : _.List<T>, value : T, from : number) : number;

        findIndex<T>(array : _.List<T>, predicate : _.ListIterator<T, boolean>, context : any) : number;

        findLastIndex<T>(array : _.List<T>, predicate : _.ListIterator<T, boolean>, context : any) : number;

        sortedIndex<T, TSort>(list : _.List<T>, value : T, iterator : (p1: T) => TSort, context : any) : number;

        range(start : number, stop : number, step : number) : number[];

        range(stop : number) : number[];

        /**
         * Split an **array** into several arrays containing **count** or less elements
         * of initial array.
         * @param array The array to split
         * @param count The maximum size of the inner arrays.
         * @param {Function} func
         * @param {*} context
         * @param {Array} args
         * @return {*}
         */
        bind(func : Function, context : any, ...args : any[]) : () => any;

        bindAll(object : any, ...methodNames : string[]) : any;

        partial<T1, T2>(fn : any, p1 : T1) : any;

        partial<T1, T2, T3>(fn : any, p1 : T1) : any;

        partial<T1, T2, T3>(fn : any, p1 : T1, p2 : T2) : any;

        partial<T1, T2, T3>(fn : any, stub1 : UnderscoreStatic, p2 : T2) : any;

        partial<T1, T2, T3, T4>(fn : any, p1 : T1) : any;

        partial<T1, T2, T3, T4>(fn : any, p1 : T1, p2 : T2) : any;

        partial<T1, T2, T3, T4>(fn : any, stub1 : UnderscoreStatic, p2 : T2) : any;

        partial<T1, T2, T3, T4>(fn : any, p1 : T1, p2 : T2, p3 : T3) : any;

        partial<T1, T2, T3, T4>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3) : any;

        partial<T1, T2, T3, T4>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3) : any;

        partial<T1, T2, T3, T4>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, p1 : T1) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, p1 : T1, p2 : T2) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, stub1 : UnderscoreStatic, p2 : T2) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, p1 : T1, p2 : T2, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, p2 : T2) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, p2 : T2) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, p2 : T2, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, p6 : T6, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, p5 : T5, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, p4 : T4, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, p4 : T4, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, p3 : T3, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, p2 : T2, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, p1 : T1, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        partial<T1, T2, T3, T4, T5, T6, T7, T8>(fn : any, stub1 : UnderscoreStatic, stub2 : UnderscoreStatic, stub3 : UnderscoreStatic, stub4 : UnderscoreStatic, stub5 : UnderscoreStatic, stub6 : UnderscoreStatic, p7 : T7) : any;

        memoize(fn : Function, hashFn : (p1: any) => string) : Function;

        delay(func : Function, wait : number, ...args : any[]) : any;

        delay(func : Function, ...args : any[]) : any;

        defer(fn : Function, ...args : any[]);

        throttle<T extends Function>(func : T, wait : number, options : _.ThrottleSettings) : ((T)|(_.Cancelable));

        debounce<T extends Function>(fn : T, wait : number, immediate : boolean) : ((T)|(_.Cancelable));

        once<T extends Function>(fn : T) : T;

        restArgs(func : Function, starIndex : number) : Function;

        after(count : number, fn : Function) : Function;

        before(count : number, fn : Function) : Function;

        wrap(fn : Function, wrapper : (p1: Function, p2: any) => any) : Function;

        negate(predicate : (p1: any) => boolean) : (p1: any) => boolean;

        compose(...functions : Function[]) : Function;

        allKeys(object : any) : string[];

        values<T>(object : _.Dictionary<T>) : T[];

        values(object : any) : any[];

        /**
         * Like map, but for objects. Transform the value of each property in turn.
         * @param {*} object The object to transform
         * @param {*} iteratee The function that transforms property values
         * @param {*} context The optional context (value of `this`) to bind to
         * @return {*} a new _.Dictionary of property values
         */
        mapObject<T, U>(object : _.Dictionary<T>, iteratee : (p1: T, p2: string, p3: _.Dictionary<T>) => U, context : any) : _.Dictionary<U>;

        /**
         * Like map, but for objects. Transform the value of each property in turn.
         * @param {*} object The object to transform
         * @param {*} iteratee The function that tranforms property values
         * @param {*} context The optional context (value of `this`) to bind to
         * @return {*}
         */
        mapObject<T>(object : any, iteratee : (p1: any, p2: string, p3: any) => T, context : any) : _.Dictionary<T>;

        /**
         * Like map, but for objects. Retrieves a property from each entry in the object, as if by _.property
         * @param {*} object The object to transform
         * @param {string} iteratee The property name to retrieve
         * @param {*} context The optional context (value of `this`) to bind to
         * @return {*}
         */
        mapObject(object : any, iteratee : string, context : any) : _.Dictionary<any>;

        pairs(object : any) : any[][];

        invert(object : any) : any;

        functions(object : any) : string[];

        methods(object : any) : string[];

        extend(destination : any, ...sources : any[]) : any;

        /**
         * Like extend, but only copies own properties over to the destination object. (alias: assign)
         * @param {*} destination
         * @param {Array} source
         * @return {*}
         */
        extendOwn(destination : any, ...source : any[]) : any;

        /**
         * Like extend, but only copies own properties over to the destination object. (alias: extendOwn)
         * @param {*} destination
         * @param {Array} source
         * @return {*}
         */
        assign(destination : any, ...source : any[]) : any;

        /**
         * Returns the first key on an object that passes a predicate test.
         * @param {*} obj the object to search in
         * @param {*} predicate Predicate function.
         * @param {*} context `this` object in `iterator`, optional.
         * @return {string}
         */
        findKey<T>(obj : _.Dictionary<T>, predicate : _.ObjectIterator<T, boolean>, context : any) : string;

        pick(object : any, ...keys : any[]) : any;

        pick(object : any, fn : (p1: any, p2: any, p3: any) => any) : any;

        omit(object : any, keys : string[]) : any;

        omit(object : any, iteratee : Function) : any;

        defaults(object : any, ...defaults : any[]) : any;

        create(prototype : any, props : any) : any;

        clone<T>(object : T) : T;

        tap<T>(object : T, intercepter : Function) : T;

        has(object : any, key : string) : boolean;

        matches<T, TResult>(attrs : T) : _.ListIterator<T, TResult>;

        matcher<T, TResult>(attrs : T) : _.ListIterator<T, TResult>;

        property(key : string) : (p1: any) => any;

        propertyOf(object : any) : (p1: string) => any;

        isEqual(object : any, other : any) : boolean;

        isEmpty(object : any) : boolean;

        isMatch(object : any, properties : any) : boolean;

        isElement(object : any) : boolean;

        isArray<T>(object : any) : boolean;

        isSymbol(object : any) : boolean;

        isObject(object : any) : boolean;

        isArguments(object : any) : boolean;

        isFunction(object : any) : boolean;

        isError(object : any) : boolean;

        isString(object : any) : boolean;

        isNumber(object : any) : boolean;

        isFinite(object : any) : boolean;

        isBoolean(object : any) : boolean;

        isDate(object : any) : boolean;

        isRegExp(object : any) : boolean;

        isNaN(object : any) : boolean;

        isNull(object : any) : boolean;

        isUndefined(value : any) : boolean;

        noConflict() : any;

        identity<T>(value : T) : T;

        constant<T>(value : T) : () => T;

        noop();

        times<TResult>(n : number, iterator : (p1: number) => TResult, context : any) : TResult[];

        random(max : number) : number;

        random(min : number, max : number) : number;

        mixin(object : any);

        iteratee(value : string) : Function;

        iteratee(value : Function, context : any) : Function;

        iteratee(value : any) : Function;

        uniqueId(prefix : string) : string;

        escape(str : string) : string;

        unescape(str : string) : string;

        result(object : any, property : string, defaultValue : any) : any;

        template(templateString : string, settings : _.TemplateSettings) : (p1: any) => string;

        templateSettings : _.TemplateSettings;

        now() : number;

        chain<T>(obj : T[]) : _._Chain<T>;

        chain<T>(obj : _.Dictionary<T>) : _._Chain<T>;

        chain<T extends any>(obj : T) : _._Chain<T>;

        each<T>(list : _.List<T>, iterator : _.ListIterator<T, void>) : _.List<T>;

        each<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, void>) : _.Dictionary<T>;

        forEach<T>(list : _.List<T>, iterator : _.ListIterator<T, void>) : _.List<T>;

        forEach<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, void>) : _.Dictionary<T>;

        map<T, TResult>(list : _.List<T>, iterator : any) : TResult[];

        map<T, TResult>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, TResult>) : TResult[];

        collect<T, TResult>(list : _.List<T>, iterator : any) : TResult[];

        collect<T, TResult>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, TResult>) : TResult[];

        reduce<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>, memo : TResult) : TResult;

        reduce<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>) : TResult;

        reduce<T, TResult>(list : _.Dictionary<T>, iterator : _.MemoObjectIterator<T, TResult>, memo : TResult) : TResult;

        reduce<T, TResult>(list : _.Dictionary<T>, iterator : _.MemoObjectIterator<T, TResult>) : TResult;

        inject<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>, memo : TResult) : TResult;

        inject<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>) : TResult;

        foldl<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>, memo : TResult) : TResult;

        foldl<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>) : TResult;

        reduceRight<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>, memo : TResult) : TResult;

        reduceRight<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>) : TResult;

        foldr<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>, memo : TResult) : TResult;

        foldr<T, TResult>(list : _.Collection<T>, iterator : _.MemoIterator<T, TResult>) : TResult;

        find<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>) : T;

        find<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>) : T;

        detect<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>) : T;

        detect<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>) : T;

        filter<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>) : T[];

        filter<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>) : T[];

        select<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>) : T[];

        select<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>) : T[];

        reject<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>) : T[];

        reject<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>) : T[];

        every<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>) : boolean;

        every<T>(list : _.List<T>) : boolean;

        every<T>(list : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>) : boolean;

        every<T>(list : _.Dictionary<T>) : boolean;

        all<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>) : boolean;

        all<T>(list : _.List<T>) : boolean;

        all<T>(list : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>) : boolean;

        all<T>(list : _.Dictionary<T>) : boolean;

        some<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>) : boolean;

        some<T>(list : _.List<T>) : boolean;

        some<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>) : boolean;

        some<T>(object : _.Dictionary<T>) : boolean;

        any<T>(list : _.List<T>, iterator : _.ListIterator<T, boolean>) : boolean;

        any<T>(list : _.List<T>) : boolean;

        any<T>(object : _.Dictionary<T>, iterator : _.ObjectIterator<T, boolean>) : boolean;

        any<T>(object : _.Dictionary<T>) : boolean;

        contains<T>(list : _.List<T>, value : T) : boolean;

        include<T>(list : _.Collection<T>, value : T) : boolean;

        includes<T>(list : _.Collection<T>, value : T) : boolean;

        max<T>(list : _.List<T>, iterator : _.ListIterator<T, any>) : T;

        /**
         * @see _.max
         * @param {*} list
         * @param {*} iterator
         * @return {*}
         */
        max<T>(list : _.Dictionary<T>, iterator : _.ObjectIterator<T, any>) : T;

        min<T>(list : _.List<T>, iterator : _.ListIterator<T, any>) : T;

        /**
         * @see _.min
         * @param {*} list
         * @param {*} iterator
         * @return {*}
         */
        min<T>(list : _.Dictionary<T>, iterator : _.ObjectIterator<T, any>) : T;

        sortBy<T, TSort>(list : _.List<T>, iterator : _.ListIterator<T, TSort>) : T[];

        sortBy<T, TSort>(list : _.List<T>) : T[];

        sortBy<T>(list : _.List<T>, iterator : string) : T[];

        groupBy<T>(list : _.List<T>, iterator : _.ListIterator<T, any>) : _.Dictionary<T[]>;

        groupBy<T>(list : _.List<T>) : _.Dictionary<T[]>;

        groupBy<T>(list : _.List<T>, iterator : string) : _.Dictionary<T[]>;

        indexBy<T>(list : _.List<T>, iterator : _.ListIterator<T, any>) : _.Dictionary<T>;

        indexBy<T>(list : _.List<T>, iterator : string) : _.Dictionary<T>;

        countBy<T>(list : _.List<T>, iterator : _.ListIterator<T, any>) : _.Dictionary<number>;

        countBy<T>(list : _.List<T>) : _.Dictionary<number>;

        countBy<T>(list : _.List<T>, iterator : string) : _.Dictionary<number>;

        partition<T>(array : Array<T>, iterator : _.ListIterator<T, boolean>) : T[][];

        initial<T>(array : _.List<T>) : T[];

        rest<T>(array : _.List<T>) : T[];

        tail<T>(array : _.List<T>) : T[];

        drop<T>(array : _.List<T>) : T[];

        flatten(array : _.List<any>) : any[];

        uniq<T, TSort>(array : _.List<T>, isSorted : boolean, iterator : _.ListIterator<T, TSort>) : T[];

        uniq<T, TSort>(array : _.List<T>, isSorted : boolean) : T[];

        uniq<T, TSort>(array : _.List<T>) : T[];

        uniq<T, TSort>(array : _.List<T>, iterator : _.ListIterator<T, TSort>) : T[];

        unique<T, TSort>(array : _.List<T>, iterator : _.ListIterator<T, TSort>) : T[];

        unique<T, TSort>(array : _.List<T>) : T[];

        unique<T, TSort>(array : _.List<T>, isSorted : boolean, iterator : _.ListIterator<T, TSort>) : T[];

        unique<T, TSort>(array : _.List<T>, isSorted : boolean) : T[];

        object<TResult extends any>(list : _.List<any>) : TResult;

        indexOf<T>(array : _.List<T>, value : T) : number;

        lastIndexOf<T>(array : _.List<T>, value : T) : number;

        findIndex<T>(array : _.List<T>, predicate : _.ListIterator<T, boolean>) : number;

        findLastIndex<T>(array : _.List<T>, predicate : _.ListIterator<T, boolean>) : number;

        sortedIndex<T, TSort>(list : _.List<T>, value : T, iterator : (p1: T) => TSort) : number;

        sortedIndex<T, TSort>(list : _.List<T>, value : T) : number;

        range(start : number, stop : number) : number[];

        memoize(fn : Function) : Function;

        throttle<T extends Function>(func : T, wait : number) : ((T)|(_.Cancelable));

        debounce<T extends Function>(fn : T, wait : number) : ((T)|(_.Cancelable));

        restArgs(func : Function) : Function;

        /**
         * Like map, but for objects. Transform the value of each property in turn.
         * @param {*} object The object to transform
         * @param {*} iteratee The function that transforms property values
         * @param context The optional context (value of `this`) to bind to
         * @return {*} a new _.Dictionary of property values
         */
        mapObject<T, U>(object : _.Dictionary<T>, iteratee : (p1: T, p2: string, p3: _.Dictionary<T>) => U) : _.Dictionary<U>;

        /**
         * Like map, but for objects. Transform the value of each property in turn.
         * @param {*} object The object to transform
         * @param {*} iteratee The function that tranforms property values
         * @param context The optional context (value of `this`) to bind to
         * @return {*}
         */
        mapObject<T>(object : any, iteratee : (p1: any, p2: string, p3: any) => T) : _.Dictionary<T>;

        /**
         * Like map, but for objects. Retrieves a property from each entry in the object, as if by _.property
         * @param {*} object The object to transform
         * @param {string} iteratee The property name to retrieve
         * @param context The optional context (value of `this`) to bind to
         * @return {*}
         */
        mapObject(object : any, iteratee : string) : _.Dictionary<any>;

        /**
         * Returns the first key on an object that passes a predicate test.
         * @param {*} obj the object to search in
         * @param {*} predicate Predicate function.
         * @param context `this` object in `iterator`, optional.
         * @return {string}
         */
        findKey<T>(obj : _.Dictionary<T>, predicate : _.ObjectIterator<T, boolean>) : string;

        times<TResult>(n : number, iterator : (p1: number) => TResult) : TResult[];

        iteratee(value : Function) : Function;

        uniqueId() : string;

        result(object : any, property : string) : any;

        template(templateString : string) : (p1: any) => string;

        find<T, U extends any>(object : _.Dictionary<T>, iterator : U) : T;

        find<T>(object : _.Dictionary<T>, iterator : string) : T;

        detect<T, U extends any>(object : _.Dictionary<T>, iterator : U) : T;

        detect<T>(object : _.Dictionary<T>, iterator : string) : T;

        uniq<T, TSort>(array : _.List<T>, isSorted : boolean, iterator : string, context : any) : T[];

        uniq<T, TSort>(array : _.List<T>, isSorted : boolean, iterator : number, context : any) : T[];

        unique<T, TSort>(array : _.List<T>, iterator : number, context : any) : T[];

        unique<T, TSort>(array : _.List<T>, iterator : string, context : any) : T[];

        findIndex<T>(array : _.List<T>, predicate : any, context : any) : number;

        findLastIndex<T>(array : _.List<T>, predicate : any, context : any) : number;

        uniq<T, TSort>(array : _.List<T>, isSorted : boolean, iterator : number) : T[];

        uniq<T, TSort>(array : _.List<T>, isSorted : boolean, iterator : string) : T[];

        unique<T, TSort>(array : _.List<T>, iterator : string) : T[];

        unique<T, TSort>(array : _.List<T>, iterator : number) : T[];

        findIndex<T>(array : _.List<T>, predicate : any) : number;

        findLastIndex<T>(array : _.List<T>, predicate : any) : number;

        apply<T>(value : T[]) : _.Underscore<T>;

        each<T>(list : T[], iterator : _.ListIterator<T, void>, context : any) : _.List<T>;

        forEach<T>(list : T[], iterator : _.ListIterator<T, void>, context : any) : _.List<T>;

        map<T, TResult>(list : T[], iterator : any, context : any) : TResult[];

        collect<T, TResult>(list : T[], iterator : any, context : any) : TResult[];

        find<T>(list : T[], iterator : _.ListIterator<T, boolean>, context : any) : T;

        find<T, U extends any>(object : T[], iterator : U) : T;

        find<T>(object : T[], iterator : string) : T;

        detect<T>(list : T[], iterator : _.ListIterator<T, boolean>, context : any) : T;

        detect<T, U extends any>(object : T[], iterator : U) : T;

        detect<T>(object : T[], iterator : string) : T;

        filter<T>(list : T[], iterator : _.ListIterator<T, boolean>, context : any) : T[];

        select<T>(list : T[], iterator : _.ListIterator<T, boolean>, context : any) : T[];

        where<T, U extends any>(list : T[], properties : U) : T[];

        findWhere<T, U extends any>(list : T[], properties : U) : T;

        reject<T>(list : T[], iterator : _.ListIterator<T, boolean>, context : any) : T[];

        every<T>(list : T[], iterator : _.ListIterator<T, boolean>, context : any) : boolean;

        all<T>(list : T[], iterator : _.ListIterator<T, boolean>, context : any) : boolean;

        some<T>(list : T[], iterator : _.ListIterator<T, boolean>, context : any) : boolean;

        any<T>(list : T[], iterator : _.ListIterator<T, boolean>, context : any) : boolean;

        any<T>(list : T[], value : T) : boolean;

        contains<T>(list : T[], value : T, fromIndex : number) : boolean;

        invoke<T extends any>(list : T[], methodName : string, ...args : any[]) : any;

        pluck<T extends any>(list : T[], propertyName : string) : any[];

        max(list : number[]) : number;

        max<T>(list : T[], iterator : _.ListIterator<T, any>, context : any) : T;

        min(list : number[]) : number;

        min<T>(list : T[], iterator : _.ListIterator<T, any>, context : any) : T;

        sortBy<T, TSort>(list : T[], iterator : _.ListIterator<T, TSort>, context : any) : T[];

        sortBy<T>(list : T[], iterator : string, context : any) : T[];

        groupBy<T>(list : T[], iterator : _.ListIterator<T, any>, context : any) : _.Dictionary<T[]>;

        groupBy<T>(list : T[], iterator : string, context : any) : _.Dictionary<T[]>;

        indexBy<T>(list : T[], iterator : _.ListIterator<T, any>, context : any) : _.Dictionary<T>;

        indexBy<T>(list : T[], iterator : string, context : any) : _.Dictionary<T>;

        countBy<T>(list : T[], iterator : _.ListIterator<T, any>, context : any) : _.Dictionary<number>;

        countBy<T>(list : T[], iterator : string, context : any) : _.Dictionary<number>;

        partition<T>(array : T[], iterator : _.ListIterator<T, boolean>, context : any) : T[][];

        first<T>(array : T[]) : T;

        first<T>(array : T[], n : number) : T[];

        head<T>(array : T[]) : T;

        head<T>(array : T[], n : number) : T[];

        take<T>(array : T[]) : T;

        take<T>(array : T[], n : number) : T[];

        initial<T>(array : T[], n : number) : T[];

        last<T>(array : T[]) : T;

        last<T>(array : T[], n : number) : T[];

        rest<T>(array : T[], n : number) : T[];

        tail<T>(array : T[], n : number) : T[];

        drop<T>(array : T[], n : number) : T[];

        compact<T>(array : T[]) : T[];

        flatten(array : any[], shallow : boolean) : any[];

        without<T>(array : T[], ...values : T[]) : T[];

        difference<T>(array : T[], ...others : _.List<T>[]) : T[];

        uniq<T, TSort>(array : T[], isSorted : boolean, iterator : _.ListIterator<T, TSort>, context : any) : T[];

        uniq<T, TSort>(array : T[], iterator : _.ListIterator<T, TSort>, context : any) : T[];

        unique<T, TSort>(array : T[], iterator : _.ListIterator<T, TSort>, context : any) : T[];

        unique<T, TSort>(array : T[], isSorted : boolean, iterator : _.ListIterator<T, TSort>, context : any) : T[];

        object<TResult extends any>(keys : _.List<string>, values : any[]) : TResult;

        object<TResult extends any>(keys : string[], values : _.List<any>) : TResult;

        object<TResult extends any>(list : any[], values : any) : TResult;

        indexOf<T>(array : T[], value : T, isSorted : boolean) : number;

        indexOf<T>(array : T[], value : T, startFrom : number) : number;

        lastIndexOf<T>(array : T[], value : T, from : number) : number;

        findIndex<T>(array : T[], predicate : _.ListIterator<T, boolean>, context : any) : number;

        findLastIndex<T>(array : T[], predicate : _.ListIterator<T, boolean>, context : any) : number;

        sortedIndex<T, TSort>(list : T[], value : T, iterator : (p1: T) => TSort, context : any) : number;

        each<T>(list : T[], iterator : _.ListIterator<T, void>) : _.List<T>;

        forEach<T>(list : T[], iterator : _.ListIterator<T, void>) : _.List<T>;

        map<T, TResult>(list : T[], iterator : any) : TResult[];

        collect<T, TResult>(list : T[], iterator : any) : TResult[];

        find<T>(list : T[], iterator : _.ListIterator<T, boolean>) : T;

        detect<T>(list : T[], iterator : _.ListIterator<T, boolean>) : T;

        filter<T>(list : T[], iterator : _.ListIterator<T, boolean>) : T[];

        select<T>(list : T[], iterator : _.ListIterator<T, boolean>) : T[];

        reject<T>(list : T[], iterator : _.ListIterator<T, boolean>) : T[];

        every<T>(list : T[], iterator : _.ListIterator<T, boolean>) : boolean;

        every<T>(list : T[]) : boolean;

        all<T>(list : T[], iterator : _.ListIterator<T, boolean>) : boolean;

        all<T>(list : T[]) : boolean;

        some<T>(list : T[], iterator : _.ListIterator<T, boolean>) : boolean;

        some<T>(list : T[]) : boolean;

        any<T>(list : T[], iterator : _.ListIterator<T, boolean>) : boolean;

        any<T>(list : T[]) : boolean;

        contains<T>(list : T[], value : T) : boolean;

        max<T>(list : T[], iterator : _.ListIterator<T, any>) : T;

        min<T>(list : T[], iterator : _.ListIterator<T, any>) : T;

        sortBy<T, TSort>(list : T[], iterator : _.ListIterator<T, TSort>) : T[];

        sortBy<T, TSort>(list : T[]) : T[];

        sortBy<T>(list : T[], iterator : string) : T[];

        groupBy<T>(list : T[], iterator : _.ListIterator<T, any>) : _.Dictionary<T[]>;

        groupBy<T>(list : T[]) : _.Dictionary<T[]>;

        groupBy<T>(list : T[], iterator : string) : _.Dictionary<T[]>;

        indexBy<T>(list : T[], iterator : _.ListIterator<T, any>) : _.Dictionary<T>;

        indexBy<T>(list : T[], iterator : string) : _.Dictionary<T>;

        countBy<T>(list : T[], iterator : _.ListIterator<T, any>) : _.Dictionary<number>;

        countBy<T>(list : T[]) : _.Dictionary<number>;

        countBy<T>(list : T[], iterator : string) : _.Dictionary<number>;

        partition<T>(array : T[], iterator : _.ListIterator<T, boolean>) : T[][];

        initial<T>(array : T[]) : T[];

        rest<T>(array : T[]) : T[];

        tail<T>(array : T[]) : T[];

        drop<T>(array : T[]) : T[];

        flatten(array : any[]) : any[];

        uniq<T, TSort>(array : T[], isSorted : boolean, iterator : _.ListIterator<T, TSort>) : T[];

        uniq<T, TSort>(array : T[], isSorted : boolean) : T[];

        uniq<T, TSort>(array : T[]) : T[];

        uniq<T, TSort>(array : T[], iterator : _.ListIterator<T, TSort>) : T[];

        unique<T, TSort>(array : T[], iterator : _.ListIterator<T, TSort>) : T[];

        unique<T, TSort>(array : T[]) : T[];

        unique<T, TSort>(array : T[], isSorted : boolean, iterator : _.ListIterator<T, TSort>) : T[];

        unique<T, TSort>(array : T[], isSorted : boolean) : T[];

        object<TResult extends any>(list : any[]) : TResult;

        indexOf<T>(array : T[], value : T) : number;

        lastIndexOf<T>(array : T[], value : T) : number;

        findIndex<T>(array : T[], predicate : _.ListIterator<T, boolean>) : number;

        findLastIndex<T>(array : T[], predicate : _.ListIterator<T, boolean>) : number;

        sortedIndex<T, TSort>(list : T[], value : T, iterator : (p1: T) => TSort) : number;

        sortedIndex<T, TSort>(list : T[], value : T) : number;

        uniq<T, TSort>(array : T[], isSorted : boolean, iterator : string, context : any) : T[];

        uniq<T, TSort>(array : T[], isSorted : boolean, iterator : number, context : any) : T[];

        unique<T, TSort>(array : T[], iterator : number, context : any) : T[];

        unique<T, TSort>(array : T[], iterator : string, context : any) : T[];

        findIndex<T>(array : T[], predicate : any, context : any) : number;

        findLastIndex<T>(array : T[], predicate : any, context : any) : number;

        uniq<T, TSort>(array : T[], isSorted : boolean, iterator : number) : T[];

        uniq<T, TSort>(array : T[], isSorted : boolean, iterator : string) : T[];

        unique<T, TSort>(array : T[], iterator : string) : T[];

        unique<T, TSort>(array : T[], iterator : number) : T[];

        findIndex<T>(array : T[], predicate : any) : number;

        findLastIndex<T>(array : T[], predicate : any) : number;
    }
}
declare namespace _ {
    export interface MemoIterator<T, TResult> {
        (prev : TResult, curr : T, index : number, list : _.List<T>) : TResult;
    }
}
declare namespace _ {
    export interface MemoObjectIterator<T, TResult> {
        (prev : TResult, curr : T, key : string, list : _.Dictionary<T>) : TResult;
    }
}
declare namespace _ {
    export interface List<T> extends _.Collection<T> {
        [index : number]: T;

        length : number;
    }
}
declare namespace _ {
    export interface Underscore<T> {
        each(iterator : _.ListIterator<T, void>, context : any) : T[];

        each(iterator : _.ObjectIterator<T, void>, context : any) : T[];

        forEach(iterator : _.ListIterator<T, void>, context : any) : T[];

        forEach(iterator : _.ObjectIterator<T, void>, context : any) : T[];

        map<TResult>(iterator : _.ListIterator<T, TResult>, context : any) : TResult[];

        map<TResult>(iterator : _.ObjectIterator<T, TResult>, context : any) : TResult[];

        collect<TResult>(iterator : _.ListIterator<T, TResult>, context : any) : TResult[];

        collect<TResult>(iterator : _.ObjectIterator<T, TResult>, context : any) : TResult[];

        reduce<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : TResult;

        inject<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : TResult;

        foldl<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : TResult;

        reduceRight<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : TResult;

        foldr<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult, context : any) : TResult;

        find<T>(iterator : _.ListIterator<T, boolean>, context : any) : T;

        find<T, U extends any>(interator : U) : T;

        find<T>(interator : string) : T;

        detect<T>(iterator : _.ListIterator<T, boolean>, context : any) : T;

        detect<T, U extends any>(interator : U) : T;

        detect<T>(interator : string) : T;

        filter(iterator : _.ListIterator<T, boolean>, context : any) : T[];

        select(iterator : _.ListIterator<T, boolean>, context : any) : T[];

        where<U extends any>(properties : U) : T[];

        findWhere<U extends any>(properties : U) : T;

        reject(iterator : _.ListIterator<T, boolean>, context : any) : T[];

        all(iterator : _.ListIterator<T, boolean>, context : any) : boolean;

        every(iterator : _.ListIterator<T, boolean>, context : any) : boolean;

        any(iterator : _.ListIterator<T, boolean>, context : any) : boolean;

        some(iterator : _.ListIterator<T, boolean>, context : any) : boolean;

        contains(value : T, fromIndex : number) : boolean;

        include(value : T, fromIndex : number) : boolean;

        includes(value : T, fromIndex : number) : boolean;

        invoke(methodName : string, ...args : any[]) : any;

        pluck(propertyName : string) : any[];

        max() : number;

        max(iterator : any, context : any) : T;

        max(iterator : any, context : any) : T;

        min() : number;

        min(iterator : any, context : any) : T;

        min(iterator : any, context : any) : T;

        sortBy(iterator : _.ListIterator<T, any>, context : any) : T[];

        sortBy(iterator : string, context : any) : T[];

        groupBy(iterator : _.ListIterator<T, any>, context : any) : _.Dictionary<_.List<T>>;

        groupBy(iterator : string, context : any) : _.Dictionary<T[]>;

        indexBy(iterator : _.ListIterator<T, any>, context : any) : _.Dictionary<T>;

        indexBy(iterator : string, context : any) : _.Dictionary<T>;

        countBy(iterator : _.ListIterator<T, any>, context : any) : _.Dictionary<number>;

        countBy(iterator : string, context : any) : _.Dictionary<number>;

        shuffle() : T[];

        sample<T>(n : number) : T[];

        sample<T>() : T;

        toArray() : T[];

        size() : number;

        first() : T;

        first(n : number) : T[];

        head() : T;

        head(n : number) : T[];

        take() : T;

        take(n : number) : T[];

        initial(n : number) : T[];

        last() : T;

        last(n : number) : T[];

        rest(n : number) : T[];

        tail(n : number) : T[];

        drop(n : number) : T[];

        compact() : T[];

        flatten(shallow : boolean) : any[];

        without(...values : T[]) : T[];

        partition(iterator : _.ListIterator<T, boolean>, context : any) : T[][];

        union(...arrays : _.List<T>[]) : T[];

        intersection(...arrays : _.List<T>[]) : T[];

        difference(...others : _.List<T>[]) : T[];

        uniq(isSorted : boolean, iterator : _.ListIterator<T, any>) : T[];

        uniq<TSort>(iterator : _.ListIterator<T, TSort>, context : any) : T[];

        unique<TSort>(isSorted : boolean, iterator : _.ListIterator<T, TSort>) : T[];

        unique<TSort>(iterator : _.ListIterator<T, TSort>, context : any) : T[];

        zip(...arrays : any[][]) : any[][];

        unzip(...arrays : any[][]) : any[][];

        object(...keyValuePairs : any[][]) : any;

        object(values : any) : any;

        indexOf(value : T, isSorted : boolean) : number;

        indexOf(value : T, startFrom : number) : number;

        lastIndexOf(value : T, from : number) : number;

        findIndex<T>(array : _.List<T>, predicate : _.ListIterator<T, boolean>, context : any) : number;

        findLastIndex<T>(array : _.List<T>, predicate : _.ListIterator<T, boolean>, context : any) : number;

        sortedIndex(value : T, iterator : (p1: T) => any, context : any) : number;

        range(stop : number, step : number) : number[];

        range() : number[];

        chunk() : any[][];

        bind(object : any, ...args : any[]) : Function;

        bindAll(...methodNames : string[]) : any;

        partial(...args : any[]) : Function;

        memoize(hashFn : (p1: any) => string) : Function;

        defer(...args : any[]);

        delay(wait : number, ...args : any[]) : any;

        delay(...args : any[]) : any;

        throttle(wait : number, options : _.ThrottleSettings) : ((Function)|(_.Cancelable));

        debounce(wait : number, immediate : boolean) : ((Function)|(_.Cancelable));

        once() : Function;

        restArgs(starIndex : number) : Function;

        after(fn : Function) : Function;

        before(fn : Function) : Function;

        wrap(wrapper : Function) : () => Function;

        negate() : boolean;

        compose(...functions : Function[]) : Function;

        keys() : string[];

        allKeys() : string[];

        values() : T[];

        pairs() : any[][];

        invert() : any;

        functions() : string[];

        methods() : string[];

        extend(...sources : any[]) : any;

        findKey(predicate : _.ObjectIterator<any, boolean>, context : any) : any;

        pick(keys : any[]) : any;

        pick(fn : (p1: any, p2: any, p3: any) => any) : any;

        omit(keys : string[]) : any;

        omit(fn : Function) : any;

        defaults(...defaults : any[]) : any;

        clone() : T;

        tap(interceptor : (p1: any) => any) : any;

        has(key : string) : boolean;

        matches<TResult>() : _.ListIterator<T, TResult>;

        matcher<TResult>() : _.ListIterator<T, TResult>;

        property() : (p1: any) => any;

        propertyOf() : (p1: string) => any;

        isEqual(other : any) : boolean;

        isEmpty() : boolean;

        isMatch() : boolean;

        isElement() : boolean;

        isArray() : boolean;

        isSymbol() : boolean;

        isObject() : boolean;

        isArguments() : boolean;

        isFunction() : boolean;

        isError() : boolean;

        isString() : boolean;

        isNumber() : boolean;

        isFinite() : boolean;

        isBoolean() : boolean;

        isDate() : boolean;

        isRegExp() : boolean;

        isNaN() : boolean;

        isNull() : boolean;

        isUndefined() : boolean;

        identity() : any;

        constant() : () => T;

        noop();

        times<TResult>(iterator : (p1: number) => TResult, context : any) : TResult[];

        random() : number;

        random(max : number) : number;

        mixin();

        iteratee(context : any) : Function;

        uniqueId() : string;

        escape() : string;

        unescape() : string;

        result(property : string, defaultValue : any) : any;

        template(settings : _.TemplateSettings) : (p1: any) => string;

        chain() : _._Chain<T>;

        value<TResult>() : TResult;

        each(iterator : _.ListIterator<T, void>) : T[];

        each(iterator : _.ObjectIterator<T, void>) : T[];

        forEach(iterator : _.ListIterator<T, void>) : T[];

        forEach(iterator : _.ObjectIterator<T, void>) : T[];

        map<TResult>(iterator : _.ListIterator<T, TResult>) : TResult[];

        map<TResult>(iterator : _.ObjectIterator<T, TResult>) : TResult[];

        collect<TResult>(iterator : _.ListIterator<T, TResult>) : TResult[];

        collect<TResult>(iterator : _.ObjectIterator<T, TResult>) : TResult[];

        reduce<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult) : TResult;

        reduce<TResult>(iterator : _.MemoIterator<T, TResult>) : TResult;

        inject<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult) : TResult;

        inject<TResult>(iterator : _.MemoIterator<T, TResult>) : TResult;

        foldl<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult) : TResult;

        foldl<TResult>(iterator : _.MemoIterator<T, TResult>) : TResult;

        reduceRight<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult) : TResult;

        reduceRight<TResult>(iterator : _.MemoIterator<T, TResult>) : TResult;

        foldr<TResult>(iterator : _.MemoIterator<T, TResult>, memo : TResult) : TResult;

        foldr<TResult>(iterator : _.MemoIterator<T, TResult>) : TResult;

        find<T>(iterator : _.ListIterator<T, boolean>) : T;

        detect<T>(iterator : _.ListIterator<T, boolean>) : T;

        detect<T, U extends any>() : T;

        filter(iterator : _.ListIterator<T, boolean>) : T[];

        select(iterator : _.ListIterator<T, boolean>) : T[];

        reject(iterator : _.ListIterator<T, boolean>) : T[];

        all(iterator : _.ListIterator<T, boolean>) : boolean;

        all() : boolean;

        every(iterator : _.ListIterator<T, boolean>) : boolean;

        every() : boolean;

        any(iterator : _.ListIterator<T, boolean>) : boolean;

        any() : boolean;

        some(iterator : _.ListIterator<T, boolean>) : boolean;

        some() : boolean;

        contains(value : T) : boolean;

        include(value : T) : boolean;

        includes(value : T) : boolean;

        max(iterator : _.ListIterator<T, number>) : T;

        min(iterator : _.ListIterator<T, number>) : T;

        sortBy(iterator : _.ListIterator<T, any>) : T[];

        sortBy() : T[];

        sortBy(iterator : string) : T[];

        groupBy(iterator : _.ListIterator<T, any>) : _.Dictionary<_.List<T>>;

        groupBy() : _.Dictionary<_.List<T>>;

        groupBy(iterator : string) : _.Dictionary<T[]>;

        indexBy(iterator : _.ListIterator<T, any>) : _.Dictionary<T>;

        indexBy(iterator : string) : _.Dictionary<T>;

        countBy(iterator : _.ListIterator<T, any>) : _.Dictionary<number>;

        countBy() : _.Dictionary<number>;

        countBy(iterator : string) : _.Dictionary<number>;

        initial() : T[];

        rest() : T[];

        tail() : T[];

        drop() : T[];

        flatten() : any[];

        partition(iterator : _.ListIterator<T, boolean>) : T[][];

        uniq(isSorted : boolean) : T[];

        uniq() : T[];

        uniq<TSort>(iterator : _.ListIterator<T, TSort>) : T[];

        unique<TSort>(isSorted : boolean) : T[];

        unique<TSort>() : T[];

        unique<TSort>(iterator : _.ListIterator<T, TSort>) : T[];

        object() : any;

        indexOf(value : T) : number;

        lastIndexOf(value : T) : number;

        findIndex<T>(array : _.List<T>, predicate : _.ListIterator<T, boolean>) : number;

        findLastIndex<T>(array : _.List<T>, predicate : _.ListIterator<T, boolean>) : number;

        sortedIndex(value : T, iterator : (p1: T) => any) : number;

        sortedIndex(value : T) : number;

        range(stop : number) : number[];

        memoize() : Function;

        throttle(wait : number) : ((Function)|(_.Cancelable));

        debounce(wait : number) : ((Function)|(_.Cancelable));

        restArgs() : Function;

        findKey(predicate : _.ObjectIterator<any, boolean>) : any;

        create() : any;

        times<TResult>(iterator : (p1: number) => TResult) : TResult[];

        iteratee() : Function;

        result(property : string) : any;

        template() : (p1: any) => string;

        find<T>(iterator : _.ObjectIterator<T, boolean>, context : any) : T;

        detect<T>(iterator : _.ObjectIterator<T, boolean>, context : any) : T;

        findIndex<T>(array : _.List<T>, predicate : any, context : any) : number;

        findLastIndex<T>(array : _.List<T>, predicate : any, context : any) : number;

        find<T>(iterator : _.ObjectIterator<T, boolean>) : T;

        detect<T>(iterator : _.ObjectIterator<T, boolean>) : T;

        findIndex<T>(array : _.List<T>, predicate : any) : number;

        findLastIndex<T>(array : _.List<T>, predicate : any) : number;

        findIndex<T>(array : T[], predicate : _.ListIterator<T, boolean>, context : any) : number;

        findLastIndex<T>(array : T[], predicate : _.ListIterator<T, boolean>, context : any) : number;

        findIndex<T>(array : T[], predicate : _.ListIterator<T, boolean>) : number;

        findLastIndex<T>(array : T[], predicate : _.ListIterator<T, boolean>) : number;

        findIndex<T>(array : T[], predicate : any, context : any) : number;

        findLastIndex<T>(array : T[], predicate : any, context : any) : number;

        findIndex<T>(array : T[], predicate : any) : number;

        findLastIndex<T>(array : T[], predicate : any) : number;
    }
}
declare namespace _ {
    export interface ListIterator<T, TResult> {
        (value : T, index : number, list : _.List<T>) : TResult;
    }
}
declare var _ : any;



