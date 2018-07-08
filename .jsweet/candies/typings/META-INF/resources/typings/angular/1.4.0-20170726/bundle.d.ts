/* Generated from Java with JSweet 2.0.0-rc1 - http://www.jsweet.org */
declare var angular : ng.IAngularStatic;


declare namespace ng.auto {
    export interface IInjectorService {
        annotate(fn : Function) : string[];

        annotate(inlineAnnotatedFunction : any[]) : string[];

        get<T>(name : string, caller : string) : T;

        has(name : string) : boolean;

        instantiate<T>(typeConstructor : Function, locals : any) : T;

        invoke(inlineAnnotatedFunction : any[]) : any;

        invoke(func : Function, context : any, locals : any) : any;

        get<T>(name : string) : T;

        instantiate<T>(typeConstructor : Function) : T;

        invoke(func : Function, context : any) : any;

        invoke(func : Function) : any;
    }
}
declare namespace ng.auto {
    export interface IProvideService {
        /**
         * Register a constant service, such as a string, a number, an array, an object or a function, with the $injector. Unlike value it can be injected into a module configuration function (see config) and it cannot be overridden by an Angular decorator.
         * 
         * @param {string} name The name of the constant.
         * @param {*} value The constant value.
         */
        constant(name : string, value : any);

        /**
         * Register a service decorator with the $injector. A service decorator intercepts the creation of a service, allowing it to override or modify the behaviour of the service. The object returned by the decorator may be the original service, or a new service object which replaces or wraps and delegates to the original service.
         * 
         * @param {string} name The name of the service to decorate.
         * @param {Function} decorator This function will be invoked when the service needs to be instantiated and should return the decorated service instance. The function is called using the injector.invoke method and is therefore fully injectable. Local injection arguments:
         * 
         * $delegate - The original service instance, which can be monkey patched, configured, decorated or delegated to.
         */
        decorator(name : string, decorator : Function);

        /**
         * Register a service decorator with the $injector. A service decorator intercepts the creation of a service, allowing it to override or modify the behaviour of the service. The object returned by the decorator may be the original service, or a new service object which replaces or wraps and delegates to the original service.
         * 
         * @param {string} name The name of the service to decorate.
         * @param {Array} inlineAnnotatedFunction This function will be invoked when the service needs to be instantiated and should return the decorated service instance. The function is called using the injector.invoke method and is therefore fully injectable. Local injection arguments:
         * 
         * $delegate - The original service instance, which can be monkey patched, configured, decorated or delegated to.
         */
        decorator(name : string, inlineAnnotatedFunction : any[]);

        factory(name : string, serviceFactoryFunction : Function) : ng.IServiceProvider;

        factory(name : string, inlineAnnotatedFunction : any[]) : ng.IServiceProvider;

        provider(name : string, provider : ng.IServiceProvider) : ng.IServiceProvider;

        provider(name : string, serviceProviderConstructor : Function) : ng.IServiceProvider;

        service(name : string, constructor : Function) : ng.IServiceProvider;

        service(name : string, inlineAnnotatedFunction : any[]) : ng.IServiceProvider;

        value(name : string, value : any) : ng.IServiceProvider;
    }
}
declare namespace ng.auto {}
declare namespace ng {
    /**
     * Represents either a component type (`type` is `component`) or a loader function
     * (`type` is `loader`).
     * 
     * See also {@link RouteDefinition}.
     * @class
     * @extends Object
     */
    export interface ComponentDefinition {
        type : string;

        loader? : any;

        component? : ng.Type;
    }
}
declare namespace ng {
    export interface Function {
        $inject? : string[];
    }
}
declare namespace ng {
    export interface IAnchorScrollProvider extends ng.IServiceProvider {
        disableAutoScrolling();
    }
}
declare namespace ng {
    export interface IAnchorScrollService {
        ();

        (hash : string);

        yOffset : any;
    }
}
declare namespace ng {
    export interface IAngularBootstrapConfig {
        strictDi? : boolean;

        debugInfoEnabled? : boolean;
    }
}
declare namespace ng {
    export interface IAngularEvent {
        /**
         * the scope on which the event was $emit-ed or $broadcast-ed.
         */
        targetScope : ng.IScope;

        /**
         * the scope that is currently handling the event. Once the event propagates through the scope hierarchy, this property is set to null.
         */
        currentScope : ng.IScope;

        /**
         * name of the event.
         */
        name : string;

        /**
         * calling stopPropagation function will cancel further event propagation (available only for events that were $emit-ed).
         */
        stopPropagation? : any;

        /**
         * calling preventDefault sets defaultPrevented flag to true.
         */
        preventDefault : any;

        /**
         * true if preventDefault was called.
         */
        defaultPrevented : boolean;
    }
}
declare namespace ng {
    export interface IAngularStatic {
        bind(context : any, fn : Function, ...args : any[]) : Function;

        /**
         * Use this function to manually start up angular application.
         * 
         * @param {*} element DOM element which is the root of angular application.
         * @param {Array} modules An array of modules to load into the application.
         * Each item in the array should be the name of a predefined module or a (DI annotated)
         * function that will be invoked by the injector as a config block.
         * @param {*} config an object for defining configuration options for the application. The following keys are supported:
         * - `strictDi`: disable automatic function annotation for the application. This is meant to assist in finding bugs which break minified code.
         * @return {*}
         */
        bootstrap(element : any, modules : string[], config : ng.IAngularBootstrapConfig) : ng.auto.IInjectorService;

        /**
         * Creates a deep copy of source, which should be an object or an array.
         * 
         * - If no destination is supplied, a copy of the object or array is created.
         * - If a destination is provided, all of its elements (for array) or properties (for objects) are deleted and then all elements/properties from the source are copied to it.
         * - If source is not an object or array (inc. null and undefined), source is returned.
         * - If source is identical to 'destination' an exception will be thrown.
         * 
         * @param {*} source The source that will be used to make a copy. Can be any type, including primitives, null, and undefined.
         * @param {*} destination Destination into which the source is copied. If provided, must be of the same type as source.
         * @return {*}
         */
        copy<T>(source : T, destination : T) : T;

        /**
         * Wraps a raw DOM element or HTML string as a jQuery element.
         * 
         * If jQuery is available, angular.element is an alias for the jQuery function. If jQuery is not available, angular.element delegates to Angular's built-in subset of jQuery, called "jQuery lite" or "jqLite."
         */
        element : any;

        equals(value1 : any, value2 : any) : boolean;

        extend(destination : any, ...sources : any[]) : any;

        /**
         * Invokes the iterator function once for each item in obj collection, which can be either an object or an array. The iterator function is invoked with iterator(value, key), where value is the value of an object property or an array element and key is the object property key or array element index. Specifying a context for the function is optional.
         * 
         * It is worth noting that .forEach does not iterate over inherited properties because it filters using the hasOwnProperty method.
         * 
         * @param {Array} obj Object to iterate over.
         * @param {*} iterator Iterator function.
         * @param {*} context Object to become context (this) for the iterator function.
         * @return {*}
         */
        forEach<T>(obj : T[], iterator : (p1: T, p2: number) => any, context : any) : any;

        /**
         * Invokes the iterator function once for each item in obj collection, which can be either an object or an array. The iterator function is invoked with iterator(value, key), where value is the value of an object property or an array element and key is the object property key or array element index. Specifying a context for the function is optional.
         * 
         * It is worth noting that .forEach does not iterate over inherited properties because it filters using the hasOwnProperty method.
         * 
         * @param {ng.IAngularStatic.Obj} obj Object to iterate over.
         * @param {*} iterator Iterator function.
         * @param {*} context Object to become context (this) for the iterator function.
         * @return {*}
         */
        forEach<T>(obj : any, iterator : (p1: T, p2: string) => any, context : any) : any;

        /**
         * Invokes the iterator function once for each item in obj collection, which can be either an object or an array. The iterator function is invoked with iterator(value, key), where value is the value of an object property or an array element and key is the object property key or array element index. Specifying a context for the function is optional.
         * 
         * It is worth noting that .forEach does not iterate over inherited properties because it filters using the hasOwnProperty method.
         * 
         * @param {*} obj Object to iterate over.
         * @param {*} iterator Iterator function.
         * @param {*} context Object to become context (this) for the iterator function.
         * @return {*}
         */
        forEach(obj : any, iterator : (p1: any, p2: any) => any, context : any) : any;

        fromJson(json : string) : any;

        identity<T>(arg : T) : T;

        injector(modules : any[], strictDi : boolean) : ng.auto.IInjectorService;

        isArray(value : any) : boolean;

        isDate(value : any) : boolean;

        isDefined(value : any) : boolean;

        isElement(value : any) : boolean;

        isFunction(value : any) : boolean;

        isNumber(value : any) : boolean;

        isObject(value : any) : boolean;

        isString(value : any) : boolean;

        isUndefined(value : any) : boolean;

        lowercase(str : string) : string;

        /**
         * Deeply extends the destination object dst by copying own enumerable properties from the src object(s) to dst. You can specify multiple src objects. If you want to preserve original objects, you can do so by passing an empty object as the target: var object = angular.merge({}, object1, object2).
         * 
         * Unlike extend(), merge() recursively descends into object properties of source objects, performing a deep copy.
         * 
         * @param {*} dst Destination object.
         * @param {Array} src Source object(s).
         * @return {*}
         */
        merge(dst : any, ...src : any[]) : any;

        /**
         * The angular.module is a global place for creating, registering and retrieving Angular modules. All modules (angular core or 3rd party) that should be available to an application must be registered using this mechanism.
         * 
         * When passed two or more arguments, a new module is created. If passed only one argument, an existing module (the name passed as the first argument to module) is retrieved.
         * 
         * @param {string} name The name of the module to create or retrieve.
         * @param {Array} requires The names of modules this module depends on. If specified then new module is being created. If unspecified then the module is being retrieved for further configuration.
         * @param {Function} configFn Optional configuration function for the module.
         * @return {*}
         */
        module(name : string, requires : string[], configFn : Function) : ng.IModule;

        noop(...args : any[]);

        reloadWithDebugInfo();

        toJson(obj : any, pretty : boolean) : string;

        uppercase(str : string) : string;

        version : any;

        /**
         * If window.name contains prefix NG_DEFER_BOOTSTRAP! when angular.bootstrap is called, the bootstrap process will be paused until angular.resumeBootstrap() is called.
         * @param {Array} extraModules An optional array of modules that should be added to the original list of modules that the app was about to be bootstrapped with.
         * @return {*}
         */
        resumeBootstrap(extraModules : string[]) : ng.auto.IInjectorService;

        /**
         * Use this function to manually start up angular application.
         * 
         * @param {*} element DOM element which is the root of angular application.
         * @param {Array} modules An array of modules to load into the application.
         * Each item in the array should be the name of a predefined module or a (DI annotated)
         * function that will be invoked by the injector as a config block.
         * @param config an object for defining configuration options for the application. The following keys are supported:
         * - `strictDi`: disable automatic function annotation for the application. This is meant to assist in finding bugs which break minified code.
         * @return {*}
         */
        bootstrap(element : any, modules : string[]) : ng.auto.IInjectorService;

        /**
         * Use this function to manually start up angular application.
         * 
         * @param {*} element DOM element which is the root of angular application.
         * @param modules An array of modules to load into the application.
         * Each item in the array should be the name of a predefined module or a (DI annotated)
         * function that will be invoked by the injector as a config block.
         * @param config an object for defining configuration options for the application. The following keys are supported:
         * - `strictDi`: disable automatic function annotation for the application. This is meant to assist in finding bugs which break minified code.
         * @return {*}
         */
        bootstrap(element : any) : ng.auto.IInjectorService;

        /**
         * Creates a deep copy of source, which should be an object or an array.
         * 
         * - If no destination is supplied, a copy of the object or array is created.
         * - If a destination is provided, all of its elements (for array) or properties (for objects) are deleted and then all elements/properties from the source are copied to it.
         * - If source is not an object or array (inc. null and undefined), source is returned.
         * - If source is identical to 'destination' an exception will be thrown.
         * 
         * @param {*} source The source that will be used to make a copy. Can be any type, including primitives, null, and undefined.
         * @param destination Destination into which the source is copied. If provided, must be of the same type as source.
         * @return {*}
         */
        copy<T>(source : T) : T;

        /**
         * Invokes the iterator function once for each item in obj collection, which can be either an object or an array. The iterator function is invoked with iterator(value, key), where value is the value of an object property or an array element and key is the object property key or array element index. Specifying a context for the function is optional.
         * 
         * It is worth noting that .forEach does not iterate over inherited properties because it filters using the hasOwnProperty method.
         * 
         * @param {Array} obj Object to iterate over.
         * @param {*} iterator Iterator function.
         * @param context Object to become context (this) for the iterator function.
         * @return {*}
         */
        forEach<T>(obj : T[], iterator : (p1: T, p2: number) => any) : any;

        /**
         * Invokes the iterator function once for each item in obj collection, which can be either an object or an array. The iterator function is invoked with iterator(value, key), where value is the value of an object property or an array element and key is the object property key or array element index. Specifying a context for the function is optional.
         * 
         * It is worth noting that .forEach does not iterate over inherited properties because it filters using the hasOwnProperty method.
         * 
         * @param {ng.IAngularStatic.Obj} obj Object to iterate over.
         * @param {*} iterator Iterator function.
         * @param context Object to become context (this) for the iterator function.
         * @return {*}
         */
        forEach<T>(obj : any, iterator : (p1: T, p2: string) => any) : any;

        /**
         * Invokes the iterator function once for each item in obj collection, which can be either an object or an array. The iterator function is invoked with iterator(value, key), where value is the value of an object property or an array element and key is the object property key or array element index. Specifying a context for the function is optional.
         * 
         * It is worth noting that .forEach does not iterate over inherited properties because it filters using the hasOwnProperty method.
         * 
         * @param {*} obj Object to iterate over.
         * @param {*} iterator Iterator function.
         * @param context Object to become context (this) for the iterator function.
         * @return {*}
         */
        forEach(obj : any, iterator : (p1: any, p2: any) => any) : any;

        identity<T>() : T;

        injector(modules : any[]) : ng.auto.IInjectorService;

        injector() : ng.auto.IInjectorService;

        /**
         * The angular.module is a global place for creating, registering and retrieving Angular modules. All modules (angular core or 3rd party) that should be available to an application must be registered using this mechanism.
         * 
         * When passed two or more arguments, a new module is created. If passed only one argument, an existing module (the name passed as the first argument to module) is retrieved.
         * 
         * @param {string} name The name of the module to create or retrieve.
         * @param {Array} requires The names of modules this module depends on. If specified then new module is being created. If unspecified then the module is being retrieved for further configuration.
         * @param configFn Optional configuration function for the module.
         * @return {*}
         */
        module(name : string, requires : string[]) : ng.IModule;

        /**
         * The angular.module is a global place for creating, registering and retrieving Angular modules. All modules (angular core or 3rd party) that should be available to an application must be registered using this mechanism.
         * 
         * When passed two or more arguments, a new module is created. If passed only one argument, an existing module (the name passed as the first argument to module) is retrieved.
         * 
         * @param {string} name The name of the module to create or retrieve.
         * @param requires The names of modules this module depends on. If specified then new module is being created. If unspecified then the module is being retrieved for further configuration.
         * @param configFn Optional configuration function for the module.
         * @return {*}
         */
        module(name : string) : ng.IModule;

        toJson(obj : any) : string;

        /**
         * If window.name contains prefix NG_DEFER_BOOTSTRAP! when angular.bootstrap is called, the bootstrap process will be paused until angular.resumeBootstrap() is called.
         * @param extraModules An optional array of modules that should be added to the original list of modules that the app was about to be bootstrapped with.
         * @return {*}
         */
        resumeBootstrap() : ng.auto.IInjectorService;

        /**
         * Use this function to manually start up angular application.
         * 
         * @param {*} element DOM element which is the root of angular application.
         * @param {Array} modules An array of modules to load into the application.
         * Each item in the array should be the name of a predefined module or a (DI annotated)
         * function that will be invoked by the injector as a config block.
         * @param {*} config an object for defining configuration options for the application. The following keys are supported:
         * - `strictDi`: disable automatic function annotation for the application. This is meant to assist in finding bugs which break minified code.
         * @return {*}
         */
        bootstrap(element : any, modules : Function[], config : ng.IAngularBootstrapConfig) : ng.auto.IInjectorService;

        /**
         * Use this function to manually start up angular application.
         * 
         * @param {*} element DOM element which is the root of angular application.
         * @param {Array} modules An array of modules to load into the application.
         * Each item in the array should be the name of a predefined module or a (DI annotated)
         * function that will be invoked by the injector as a config block.
         * @param {*} config an object for defining configuration options for the application. The following keys are supported:
         * - `strictDi`: disable automatic function annotation for the application. This is meant to assist in finding bugs which break minified code.
         * @return {*}
         */
        bootstrap(element : any, modules : any[][], config : ng.IAngularBootstrapConfig) : ng.auto.IInjectorService;

        /**
         * Use this function to manually start up angular application.
         * 
         * @param {*} element DOM element which is the root of angular application.
         * @param {Array} modules An array of modules to load into the application.
         * Each item in the array should be the name of a predefined module or a (DI annotated)
         * function that will be invoked by the injector as a config block.
         * @param config an object for defining configuration options for the application. The following keys are supported:
         * - `strictDi`: disable automatic function annotation for the application. This is meant to assist in finding bugs which break minified code.
         * @return {*}
         */
        bootstrap(element : any, modules : any[][]) : ng.auto.IInjectorService;

        /**
         * Use this function to manually start up angular application.
         * 
         * @param {*} element DOM element which is the root of angular application.
         * @param {Array} modules An array of modules to load into the application.
         * Each item in the array should be the name of a predefined module or a (DI annotated)
         * function that will be invoked by the injector as a config block.
         * @param config an object for defining configuration options for the application. The following keys are supported:
         * - `strictDi`: disable automatic function annotation for the application. This is meant to assist in finding bugs which break minified code.
         * @return {*}
         */
        bootstrap(element : any, modules : Function[]) : ng.auto.IInjectorService;
    }
}
declare namespace ng {
    /**
     * The animation object which contains callback functions for each event that is expected to be animated.
     * @class
     * @extends Object
     */
    export interface IAnimateCallbackObject {
        eventFn(element : Node, doneFn : () => void) : Function;
    }
}
declare namespace ng {
    export interface IAnimateProvider {
        /**
         * Registers a new injectable animation factory function.
         * 
         * @param {string} name The name of the animation.
         * @param {*} factory The factory function that will be executed to return the animation object.
         */
        register(name : string, factory : () => ng.IAnimateCallbackObject);

        /**
         * Gets and/or sets the CSS class expression that is checked when performing an animation.
         * 
         * @param {RegExp} expression The className expression which will be checked against all animations.
         * @returns The current CSS className expression value. If null then there is no expression value.
         * @return {RegExp}
         */
        classNameFilter(expression : RegExp) : RegExp;

        /**
         * Gets and/or sets the CSS class expression that is checked when performing an animation.
         * 
         * @param expression The className expression which will be checked against all animations.
         * @returns The current CSS className expression value. If null then there is no expression value.
         * @return {RegExp}
         */
        classNameFilter() : RegExp;
    }
}
declare namespace ng {
    export interface IAsyncModelValidators {
        [index : string]: (p1: any, p2: any) => ng.IPromise<any>;
    }
}
declare namespace ng {
    export interface IAttributes {
        [name : string]: any;

        /**
         * Converts an attribute name (e.g. dash/colon/underscore-delimited string, optionally prefixed with x- or data-) to its normalized, camelCase form.
         * 
         * Also there is special case for Moz prefix starting with upper case letter.
         * 
         * For further information check out the guide on @see https://docs.angularjs.org/guide/directive#matching-directives
         * @param {string} name
         * @return {string}
         */
        $normalize(name : string) : string;

        /**
         * Adds the CSS class value specified by the classVal parameter to the
         * element. If animations are enabled then an animation will be triggered
         * for the class addition.
         * @param {string} classVal
         */
        $addClass(classVal : string);

        /**
         * Removes the CSS class value specified by the classVal parameter from the
         * element. If animations are enabled then an animation will be triggered for
         * the class removal.
         * @param {string} classVal
         */
        $removeClass(classVal : string);

        /**
         * Set DOM element attribute value.
         * @param {string} key
         * @param {*} value
         */
        $set(key : string, value : any);

        /**
         * Observes an interpolated attribute.
         * The observer function will be invoked once during the next $digest
         * following compilation. The observer is then invoked whenever the
         * interpolated value changes.
         * @param {string} name
         * @param {*} fn
         * @return {Function}
         */
        $observe<T>(name : string, fn : (p1: T) => any) : Function;

        /**
         * A map of DOM element attribute names to the normalized name. This is needed
         * to do reverse lookup from normalized name back to actual name.
         */
        $attr : any;

        /**
         * Observes an interpolated attribute.
         * The observer function will be invoked once during the next $digest
         * following compilation. The observer is then invoked whenever the
         * interpolated value changes.
         * @param {string} name
         * @param {*} fn
         * @return {Function}
         */
        $observe<T>(name : string, fn : () => any) : Function;
    }
}
declare namespace ng {
    export interface IAugmentedJQuery extends JQuery {
        find(selector : string) : IAugmentedJQuery;

        find(element : any) : IAugmentedJQuery;

        find(obj : JQuery) : IAugmentedJQuery;

        controller() : any;

        controller(name : string) : any;

        injector() : any;

        scope() : ng.IScope;

        isolateScope() : ng.IScope;

        inheritedData(key : string, value : any) : JQuery;

        inheritedData(obj : any) : JQuery;

        inheritedData(key : string) : any;

        inheritedData() : any;
    }
}
declare namespace ng {
    /**
     * angular.element
     * when calling angular.element, angular returns a jQuery object,
     * augmented with additional methods like e.g. scope.
     * see: http://docs.angularjs.org/api/angular.element
     * @class
     * @extends *
     */
    export interface IAugmentedJQueryStatic extends JQueryStatic {
        (selector : string, context : any) : ng.IAugmentedJQuery;

        (element : Element) : ng.IAugmentedJQuery;

        (object : any) : ng.IAugmentedJQuery;

        (elementArray : Element[]) : ng.IAugmentedJQuery;

        (object : JQuery) : ng.IAugmentedJQuery;

        (func : Function) : ng.IAugmentedJQuery;

        (array : any[]) : ng.IAugmentedJQuery;

        () : ng.IAugmentedJQuery;

        (selector : string) : ng.IAugmentedJQuery;
    }
}
declare namespace ng {
    export interface IBrowserService {
        defer : any;

        [key : string]: any;
    }
}
declare namespace ng {
    /**
     * $cacheFactory - service in module ng
     * 
     * Factory that constructs Cache objects and gives access to them.
     * 
     * see https://docs.angularjs.org/api/ng/service/$cacheFactory
     * @class
     * @extends Object
     */
    export interface ICacheFactoryService {
        /**
         * Factory that constructs Cache objects and gives access to them.
         * 
         * @param {string} cacheId Name or id of the newly created cache.
         * @param {ng.ICacheFactoryService.OptionsMap} optionsMap Options object that specifies the cache behavior. Properties:
         * 
         * capacity — turns the cache into LRU cache.
         * @return {*}
         */
        (cacheId : string, optionsMap : any) : ng.ICacheObject;

        /**
         * Get information about all the caches that have been created.
         * @returns key-value map of cacheId to the result of calling cache#info
         * @return {*}
         */
        info() : any;

        /**
         * Get access to a cache object by the cacheId used when it was created.
         * 
         * @param {string} cacheId Name or id of a cache to access.
         * @return {*}
         */
        get(cacheId : string) : ng.ICacheObject;

        /**
         * Factory that constructs Cache objects and gives access to them.
         * 
         * @param {string} cacheId Name or id of the newly created cache.
         * @param optionsMap Options object that specifies the cache behavior. Properties:
         * 
         * capacity — turns the cache into LRU cache.
         * @return {*}
         */
        (cacheId : string) : ng.ICacheObject;
    }
}
declare namespace ng {
    /**
     * $cacheFactory.Cache - type in module ng
     * 
     * A cache object used to store and retrieve data, primarily used by $http and the script directive to cache templates and other data.
     * 
     * see https://docs.angularjs.org/api/ng/type/$cacheFactory.Cache
     * @class
     * @extends Object
     */
    export interface ICacheObject {
        /**
         * Retrieve information regarding a particular Cache.
         * @return {ng.ICacheObject.Info}
         */
        info() : any;

        /**
         * Inserts a named entry into the Cache object to be retrieved later, and incrementing the size of the cache if the key was not already present in the cache. If behaving like an LRU cache, it will also remove stale entries from the set.
         * 
         * It will not insert undefined values into the cache.
         * 
         * @param {string} key the key under which the cached data is stored.
         * @param {*} value the value to store alongside the key. If it is undefined, the key will not be stored.
         * @return {*}
         */
        put<T>(key : string, value : T) : T;

        /**
         * Retrieves named data stored in the Cache object.
         * 
         * @param {string} key the key of the data to be retrieved
         * @return {*}
         */
        get<T>(key : string) : T;

        /**
         * Removes an entry from the Cache object.
         * 
         * @param {string} key the key of the entry to be removed
         */
        remove(key : string);

        /**
         * Clears the cache object of any entries.
         */
        removeAll();

        /**
         * Destroys the Cache object entirely, removing it from the $cacheFactory set.
         */
        destroy();

        /**
         * Inserts a named entry into the Cache object to be retrieved later, and incrementing the size of the cache if the key was not already present in the cache. If behaving like an LRU cache, it will also remove stale entries from the set.
         * 
         * It will not insert undefined values into the cache.
         * 
         * @param {string} key the key under which the cached data is stored.
         * @param value the value to store alongside the key. If it is undefined, the key will not be stored.
         * @return {*}
         */
        put<T>(key : string) : T;
    }
}
declare namespace ng {
    export interface ICloneAttachFunction {
        (clonedElement : JQuery, scope : ng.IScope) : any;

        (clonedElement : JQuery) : any;

        () : any;
    }
}
declare namespace ng {
    export interface ICompiledExpression {
        (context : any, locals : any) : any;

        literal : boolean;

        constant : boolean;

        assign(context : any, value : any) : any;

        (context : any) : any;
    }
}
declare namespace ng {
    export interface ICompileProvider extends ng.IServiceProvider {
        directive(name : string, directiveFactory : Function) : ICompileProvider;

        directive(directivesMap : any) : ICompileProvider;

        aHrefSanitizationWhitelist() : RegExp;

        aHrefSanitizationWhitelist(regexp : RegExp) : ICompileProvider;

        imgSrcSanitizationWhitelist() : RegExp;

        imgSrcSanitizationWhitelist(regexp : RegExp) : ICompileProvider;

        debugInfoEnabled(enabled : boolean) : any;

        debugInfoEnabled() : any;
    }
}
declare namespace ng {
    export interface ICompileService {
        (element : string, transclude : ng.ITranscludeFunction, maxPriority : number) : ng.ITemplateLinkingFunction;

        (element : Element, transclude : ng.ITranscludeFunction, maxPriority : number) : ng.ITemplateLinkingFunction;

        (element : JQuery, transclude : ng.ITranscludeFunction, maxPriority : number) : ng.ITemplateLinkingFunction;

        (element : string, transclude : ng.ITranscludeFunction) : ng.ITemplateLinkingFunction;

        (element : string) : ng.ITemplateLinkingFunction;

        (element : Element, transclude : ng.ITranscludeFunction) : ng.ITemplateLinkingFunction;

        (element : Element) : ng.ITemplateLinkingFunction;

        (element : JQuery, transclude : ng.ITranscludeFunction) : ng.ITemplateLinkingFunction;

        (element : JQuery) : ng.ITemplateLinkingFunction;
    }
}
declare namespace ng {
    /**
     * Component definition object (a simplified directive definition object)
     * @class
     * @extends Object
     */
    export interface IComponentOptions {
        /**
         * Controller constructor function that should be associated with newly created scope or the name of a registered
         * controller if passed as a string. Empty function by default.
         */
        controller? : any;

        /**
         * An identifier name for a reference to the controller. If present, the controller will be published to scope under
         * the controllerAs name. If not present, this will default to be the same as the component name.
         */
        controllerAs? : string;

        /**
         * html template as a string or a function that returns an html template as a string which should be used as the
         * contents of this component. Empty string by default.
         * If template is a function, then it is injected with the following locals:
         * $element - Current element
         * $attrs - Current attributes object for the element
         */
        template? : ((string)|(Function));

        /**
         * path or function that returns a path to an html template that should be used as the contents of this component.
         * If templateUrl is a function, then it is injected with the following locals:
         * $element - Current element
         * $attrs - Current attributes object for the element
         */
        templateUrl? : ((string)|(Function));

        /**
         * Define DOM attribute binding to component properties. Component properties are always bound to the component
         * controller and not to the scope.
         */
        bindings? : any;

        /**
         * Whether transclusion is enabled. Enabled by default.
         */
        transclude? : boolean;

        require? : any;

        $canActivate? : () => boolean;

        $routeConfig? : ng.RouteDefinition[];
    }
}
declare namespace ng {
    export interface IComponentTemplateFn {
        ($element : ng.IAugmentedJQuery, $attrs : ng.IAttributes) : string;

        ($element : ng.IAugmentedJQuery) : string;

        () : string;
    }
}
declare namespace ng {
    export interface IControllerProvider extends ng.IServiceProvider {
        register(name : string, controllerConstructor : Function);

        register(name : string, dependencyAnnotatedConstructor : any[]);

        allowGlobals();
    }
}
declare namespace ng {
    export interface IControllerService {
        <T>(controllerConstructor : (p1: any) => T, locals : any, bindToController : any) : T;

        <T>(controllerConstructor : Function, locals : any, bindToController : any) : T;

        <T>(controllerName : string, locals : any, bindToController : any) : T;

        <T>(controllerConstructor : (p1: any) => T, locals : any) : T;

        <T>(controllerConstructor : (p1: any) => T) : T;

        <T>(controllerConstructor : Function, locals : any) : T;

        <T>(controllerConstructor : Function) : T;

        <T>(controllerName : string, locals : any) : T;

        <T>(controllerName : string) : T;
    }
}
declare namespace ng {
    export interface IDeferred<T> {
        resolve(value : T);

        reject(reason : any);

        notify(state : any);

        promise : ng.IPromise<T>;

        resolve();

        reject();

        notify();

        resolve(value : ng.IPromise<T>);
    }
}
declare namespace ng {
    export interface IDirective {
        controller? : any;

        controllerAs? : string;

        bindToController? : ((boolean)|(any));

        link? : ((ng.IDirectiveLinkFn)|(ng.IDirectivePrePost));

        name? : string;

        priority? : number;

        replace? : boolean;

        require? : any;

        restrict? : string;

        scope? : any;

        template? : ((string)|(Function));

        templateNamespace? : string;

        templateUrl? : ((string)|(Function));

        terminal? : boolean;

        transclude? : any;

        compile(templateElement : ng.IAugmentedJQuery, templateAttributes : ng.IAttributes, transclude : ng.ITranscludeFunction) : ng.IDirectivePrePost;
    }
}
declare namespace ng {
    export interface IDirectiveCompileFn {
        (templateElement : ng.IAugmentedJQuery, templateAttributes : ng.IAttributes, transclude : ng.ITranscludeFunction) : ng.IDirectivePrePost;
    }
}
declare namespace ng {
    export interface IDirectiveFactory {
        (...args : any[]) : ng.IDirective;
    }
}
declare namespace ng {
    export interface IDirectiveLinkFn {
        (scope : ng.IScope, instanceElement : ng.IAugmentedJQuery, instanceAttributes : ng.IAttributes, controller : any, transclude : ng.ITranscludeFunction);
    }
}
declare namespace ng {
    export interface IDirectivePrePost {
        pre? : any;

        post? : any;
    }
}
declare namespace ng {
    export interface IDocumentService extends ng.IAugmentedJQuery {    }
}
declare namespace ng {
    export interface IExceptionHandlerService {
        (exception : Error, cause : string);

        (exception : Error);
    }
}
declare namespace ng {
    export interface IFilterCurrency {
        /**
         * Formats a number as a currency (ie $1,234.56). When no currency symbol is provided, default symbol for current locale is used.
         * @param {number} amount Input to filter.
         * @param {string} symbol Currency symbol or identifier to be displayed.
         * @param {number} fractionSize Number of decimal places to round the amount to, defaults to default max fraction size for current locale
         * @return {string} Formatted number
         */
        (amount : number, symbol : string, fractionSize : number) : string;

        /**
         * Formats a number as a currency (ie $1,234.56). When no currency symbol is provided, default symbol for current locale is used.
         * @param {number} amount Input to filter.
         * @param {string} symbol Currency symbol or identifier to be displayed.
         * @param fractionSize Number of decimal places to round the amount to, defaults to default max fraction size for current locale
         * @return {string} Formatted number
         */
        (amount : number, symbol : string) : string;

        /**
         * Formats a number as a currency (ie $1,234.56). When no currency symbol is provided, default symbol for current locale is used.
         * @param {number} amount Input to filter.
         * @param symbol Currency symbol or identifier to be displayed.
         * @param fractionSize Number of decimal places to round the amount to, defaults to default max fraction size for current locale
         * @return {string} Formatted number
         */
        (amount : number) : string;
    }
}
declare namespace ng {
    export interface IFilterDate {
        /**
         * Formats date to a string based on the requested format.
         * 
         * @param {Date} date Date to format either as Date object, milliseconds (string or number) or various ISO 8601 datetime string formats (e.g. yyyy-MM-ddTHH:mm:ss.sssZ and its shorter versions like yyyy-MM-ddTHH:mmZ, yyyy-MM-dd or yyyyMMddTHHmmssZ). If no timezone is specified in the string input, the time is considered to be in the local timezone.
         * @param {string} format Formatting rules (see Description). If not specified, mediumDate is used.
         * @param {string} timezone Timezone to be used for formatting. It understands UTC/GMT and the continental US time zone abbreviations, but for general use, use a time zone offset, for example, '+0430' (4 hours, 30 minutes east of the Greenwich meridian) If not specified, the timezone of the browser will be used.
         * @return {string} Formatted string or the input if input is not recognized as date/millis.
         */
        (date : Date, format : string, timezone : string) : string;

        /**
         * Formats date to a string based on the requested format.
         * 
         * @param {Date} date Date to format either as Date object, milliseconds (string or number) or various ISO 8601 datetime string formats (e.g. yyyy-MM-ddTHH:mm:ss.sssZ and its shorter versions like yyyy-MM-ddTHH:mmZ, yyyy-MM-dd or yyyyMMddTHHmmssZ). If no timezone is specified in the string input, the time is considered to be in the local timezone.
         * @param {string} format Formatting rules (see Description). If not specified, mediumDate is used.
         * @param timezone Timezone to be used for formatting. It understands UTC/GMT and the continental US time zone abbreviations, but for general use, use a time zone offset, for example, '+0430' (4 hours, 30 minutes east of the Greenwich meridian) If not specified, the timezone of the browser will be used.
         * @return {string} Formatted string or the input if input is not recognized as date/millis.
         */
        (date : Date, format : string) : string;

        /**
         * Formats date to a string based on the requested format.
         * 
         * @param {Date} date Date to format either as Date object, milliseconds (string or number) or various ISO 8601 datetime string formats (e.g. yyyy-MM-ddTHH:mm:ss.sssZ and its shorter versions like yyyy-MM-ddTHH:mmZ, yyyy-MM-dd or yyyyMMddTHHmmssZ). If no timezone is specified in the string input, the time is considered to be in the local timezone.
         * @param format Formatting rules (see Description). If not specified, mediumDate is used.
         * @param timezone Timezone to be used for formatting. It understands UTC/GMT and the continental US time zone abbreviations, but for general use, use a time zone offset, for example, '+0430' (4 hours, 30 minutes east of the Greenwich meridian) If not specified, the timezone of the browser will be used.
         * @return {string} Formatted string or the input if input is not recognized as date/millis.
         */
        (date : Date) : string;

        /**
         * Formats date to a string based on the requested format.
         * 
         * @param {number} date Date to format either as Date object, milliseconds (string or number) or various ISO 8601 datetime string formats (e.g. yyyy-MM-ddTHH:mm:ss.sssZ and its shorter versions like yyyy-MM-ddTHH:mmZ, yyyy-MM-dd or yyyyMMddTHHmmssZ). If no timezone is specified in the string input, the time is considered to be in the local timezone.
         * @param {string} format Formatting rules (see Description). If not specified, mediumDate is used.
         * @param {string} timezone Timezone to be used for formatting. It understands UTC/GMT and the continental US time zone abbreviations, but for general use, use a time zone offset, for example, '+0430' (4 hours, 30 minutes east of the Greenwich meridian) If not specified, the timezone of the browser will be used.
         * @return {string} Formatted string or the input if input is not recognized as date/millis.
         */
        (date : number, format : string, timezone : string) : string;

        /**
         * Formats date to a string based on the requested format.
         * 
         * @param {string} date Date to format either as Date object, milliseconds (string or number) or various ISO 8601 datetime string formats (e.g. yyyy-MM-ddTHH:mm:ss.sssZ and its shorter versions like yyyy-MM-ddTHH:mmZ, yyyy-MM-dd or yyyyMMddTHHmmssZ). If no timezone is specified in the string input, the time is considered to be in the local timezone.
         * @param {string} format Formatting rules (see Description). If not specified, mediumDate is used.
         * @param {string} timezone Timezone to be used for formatting. It understands UTC/GMT and the continental US time zone abbreviations, but for general use, use a time zone offset, for example, '+0430' (4 hours, 30 minutes east of the Greenwich meridian) If not specified, the timezone of the browser will be used.
         * @return {string} Formatted string or the input if input is not recognized as date/millis.
         */
        (date : string, format : string, timezone : string) : string;

        /**
         * Formats date to a string based on the requested format.
         * 
         * @param {number} date Date to format either as Date object, milliseconds (string or number) or various ISO 8601 datetime string formats (e.g. yyyy-MM-ddTHH:mm:ss.sssZ and its shorter versions like yyyy-MM-ddTHH:mmZ, yyyy-MM-dd or yyyyMMddTHHmmssZ). If no timezone is specified in the string input, the time is considered to be in the local timezone.
         * @param {string} format Formatting rules (see Description). If not specified, mediumDate is used.
         * @param timezone Timezone to be used for formatting. It understands UTC/GMT and the continental US time zone abbreviations, but for general use, use a time zone offset, for example, '+0430' (4 hours, 30 minutes east of the Greenwich meridian) If not specified, the timezone of the browser will be used.
         * @return {string} Formatted string or the input if input is not recognized as date/millis.
         */
        (date : number, format : string) : string;

        /**
         * Formats date to a string based on the requested format.
         * 
         * @param {string} date Date to format either as Date object, milliseconds (string or number) or various ISO 8601 datetime string formats (e.g. yyyy-MM-ddTHH:mm:ss.sssZ and its shorter versions like yyyy-MM-ddTHH:mmZ, yyyy-MM-dd or yyyyMMddTHHmmssZ). If no timezone is specified in the string input, the time is considered to be in the local timezone.
         * @param {string} format Formatting rules (see Description). If not specified, mediumDate is used.
         * @param timezone Timezone to be used for formatting. It understands UTC/GMT and the continental US time zone abbreviations, but for general use, use a time zone offset, for example, '+0430' (4 hours, 30 minutes east of the Greenwich meridian) If not specified, the timezone of the browser will be used.
         * @return {string} Formatted string or the input if input is not recognized as date/millis.
         */
        (date : string, format : string) : string;

        /**
         * Formats date to a string based on the requested format.
         * 
         * @param {string} date Date to format either as Date object, milliseconds (string or number) or various ISO 8601 datetime string formats (e.g. yyyy-MM-ddTHH:mm:ss.sssZ and its shorter versions like yyyy-MM-ddTHH:mmZ, yyyy-MM-dd or yyyyMMddTHHmmssZ). If no timezone is specified in the string input, the time is considered to be in the local timezone.
         * @param format Formatting rules (see Description). If not specified, mediumDate is used.
         * @param timezone Timezone to be used for formatting. It understands UTC/GMT and the continental US time zone abbreviations, but for general use, use a time zone offset, for example, '+0430' (4 hours, 30 minutes east of the Greenwich meridian) If not specified, the timezone of the browser will be used.
         * @return {string} Formatted string or the input if input is not recognized as date/millis.
         */
        (date : string) : string;

        /**
         * Formats date to a string based on the requested format.
         * 
         * @param {number} date Date to format either as Date object, milliseconds (string or number) or various ISO 8601 datetime string formats (e.g. yyyy-MM-ddTHH:mm:ss.sssZ and its shorter versions like yyyy-MM-ddTHH:mmZ, yyyy-MM-dd or yyyyMMddTHHmmssZ). If no timezone is specified in the string input, the time is considered to be in the local timezone.
         * @param format Formatting rules (see Description). If not specified, mediumDate is used.
         * @param timezone Timezone to be used for formatting. It understands UTC/GMT and the continental US time zone abbreviations, but for general use, use a time zone offset, for example, '+0430' (4 hours, 30 minutes east of the Greenwich meridian) If not specified, the timezone of the browser will be used.
         * @return {string} Formatted string or the input if input is not recognized as date/millis.
         */
        (date : number) : string;
    }
}
declare namespace ng {
    export interface IFilterFilter {
        <T>(array : T[], expression : string, comparator : ng.IFilterFilterComparatorFunc<T>) : T[];

        <T>(array : T[], expression : string) : T[];

        <T>(array : T[], expression : string, comparator : boolean) : T[];

        <T>(array : T[], expression : ng.IFilterFilterPatternObject, comparator : boolean) : T[];

        <T>(array : T[], expression : ng.IFilterFilterPatternObject, comparator : ng.IFilterFilterComparatorFunc<T>) : T[];

        <T>(array : T[], expression : ng.IFilterFilterPredicateFunc<T>, comparator : ng.IFilterFilterComparatorFunc<T>) : T[];

        <T>(array : T[], expression : ng.IFilterFilterPredicateFunc<T>, comparator : boolean) : T[];

        <T>(array : T[], expression : ng.IFilterFilterPatternObject) : T[];

        <T>(array : T[], expression : ng.IFilterFilterPredicateFunc<T>) : T[];
    }
}
declare namespace ng {
    export interface IFilterFilterComparatorFunc<T> {
        (actual : T, expected : T) : boolean;
    }
}
declare namespace ng {
    export interface IFilterFilterPatternObject {
        [name : string]: any;
    }
}
declare namespace ng {
    export interface IFilterFilterPredicateFunc<T> {
        (value : T, index : number, array : T[]) : boolean;
    }
}
declare namespace ng {
    export interface IFilterJson {
        /**
         * Allows you to convert a JavaScript object into JSON string.
         * @param {*} object Any JavaScript object (including arrays and primitive types) to filter.
         * @param {number} spacing The number of spaces to use per indentation, defaults to 2.
         * @return {string} JSON string.
         */
        (object : any, spacing : number) : string;

        /**
         * Allows you to convert a JavaScript object into JSON string.
         * @param {*} object Any JavaScript object (including arrays and primitive types) to filter.
         * @param spacing The number of spaces to use per indentation, defaults to 2.
         * @return {string} JSON string.
         */
        (object : any) : string;
    }
}
declare namespace ng {
    export interface IFilterLimitTo {
        /**
         * Creates a new array containing only a specified number of elements. The elements are taken from either the beginning or the end of the source array, string or number, as specified by the value and sign (positive or negative) of limit.
         * @param {Array} input Source array to be limited.
         * @param {string} limit The length of the returned array. If the limit number is positive, limit number of items from the beginning of the source array/string are copied. If the number is negative, limit number of items from the end of the source array are copied. The limit will be trimmed if it exceeds array.length. If limit is undefined, the input will be returned unchanged.
         * @param {string} begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {Array} A new sub-array of length limit or less if input array had less than limit elements.
         */
        <T>(input : T[], limit : string, begin : string) : T[];

        /**
         * Creates a new string containing only a specified number of elements. The elements are taken from either the beginning or the end of the source string or number, as specified by the value and sign (positive or negative) of limit. If a number is used as input, it is converted to a string.
         * @param {string} input Source string or number to be limited.
         * @param {string} limit The length of the returned string. If the limit number is positive, limit number of items from the beginning of the source string are copied. If the number is negative, limit number of items from the end of the source string are copied. The limit will be trimmed if it exceeds input.length. If limit is undefined, the input will be returned unchanged.
         * @param {string} begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {string} A new substring of length limit or less if input had less than limit elements.
         */
        (input : string, limit : string, begin : string) : string;

        /**
         * Creates a new array containing only a specified number of elements. The elements are taken from either the beginning or the end of the source array, string or number, as specified by the value and sign (positive or negative) of limit.
         * @param {Array} input Source array to be limited.
         * @param {string} limit The length of the returned array. If the limit number is positive, limit number of items from the beginning of the source array/string are copied. If the number is negative, limit number of items from the end of the source array are copied. The limit will be trimmed if it exceeds array.length. If limit is undefined, the input will be returned unchanged.
         * @param begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {Array} A new sub-array of length limit or less if input array had less than limit elements.
         */
        <T>(input : T[], limit : string) : T[];

        /**
         * Creates a new string containing only a specified number of elements. The elements are taken from either the beginning or the end of the source string or number, as specified by the value and sign (positive or negative) of limit. If a number is used as input, it is converted to a string.
         * @param {string} input Source string or number to be limited.
         * @param {string} limit The length of the returned string. If the limit number is positive, limit number of items from the beginning of the source string are copied. If the number is negative, limit number of items from the end of the source string are copied. The limit will be trimmed if it exceeds input.length. If limit is undefined, the input will be returned unchanged.
         * @param begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {string} A new substring of length limit or less if input had less than limit elements.
         */
        (input : string, limit : string) : string;

        /**
         * Creates a new array containing only a specified number of elements. The elements are taken from either the beginning or the end of the source array, string or number, as specified by the value and sign (positive or negative) of limit.
         * @param {Array} input Source array to be limited.
         * @param {string} limit The length of the returned array. If the limit number is positive, limit number of items from the beginning of the source array/string are copied. If the number is negative, limit number of items from the end of the source array are copied. The limit will be trimmed if it exceeds array.length. If limit is undefined, the input will be returned unchanged.
         * @param {number} begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {Array} A new sub-array of length limit or less if input array had less than limit elements.
         */
        <T>(input : T[], limit : string, begin : number) : T[];

        /**
         * Creates a new array containing only a specified number of elements. The elements are taken from either the beginning or the end of the source array, string or number, as specified by the value and sign (positive or negative) of limit.
         * @param {Array} input Source array to be limited.
         * @param {number} limit The length of the returned array. If the limit number is positive, limit number of items from the beginning of the source array/string are copied. If the number is negative, limit number of items from the end of the source array are copied. The limit will be trimmed if it exceeds array.length. If limit is undefined, the input will be returned unchanged.
         * @param {number} begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {Array} A new sub-array of length limit or less if input array had less than limit elements.
         */
        <T>(input : T[], limit : number, begin : number) : T[];

        /**
         * Creates a new array containing only a specified number of elements. The elements are taken from either the beginning or the end of the source array, string or number, as specified by the value and sign (positive or negative) of limit.
         * @param {Array} input Source array to be limited.
         * @param {number} limit The length of the returned array. If the limit number is positive, limit number of items from the beginning of the source array/string are copied. If the number is negative, limit number of items from the end of the source array are copied. The limit will be trimmed if it exceeds array.length. If limit is undefined, the input will be returned unchanged.
         * @param {string} begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {Array} A new sub-array of length limit or less if input array had less than limit elements.
         */
        <T>(input : T[], limit : number, begin : string) : T[];

        /**
         * Creates a new string containing only a specified number of elements. The elements are taken from either the beginning or the end of the source string or number, as specified by the value and sign (positive or negative) of limit. If a number is used as input, it is converted to a string.
         * @param {number} input Source string or number to be limited.
         * @param {number} limit The length of the returned string. If the limit number is positive, limit number of items from the beginning of the source string are copied. If the number is negative, limit number of items from the end of the source string are copied. The limit will be trimmed if it exceeds input.length. If limit is undefined, the input will be returned unchanged.
         * @param {number} begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {string} A new substring of length limit or less if input had less than limit elements.
         */
        (input : number, limit : number, begin : number) : string;

        /**
         * Creates a new string containing only a specified number of elements. The elements are taken from either the beginning or the end of the source string or number, as specified by the value and sign (positive or negative) of limit. If a number is used as input, it is converted to a string.
         * @param {number} input Source string or number to be limited.
         * @param {string} limit The length of the returned string. If the limit number is positive, limit number of items from the beginning of the source string are copied. If the number is negative, limit number of items from the end of the source string are copied. The limit will be trimmed if it exceeds input.length. If limit is undefined, the input will be returned unchanged.
         * @param {string} begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {string} A new substring of length limit or less if input had less than limit elements.
         */
        (input : number, limit : string, begin : string) : string;

        /**
         * Creates a new string containing only a specified number of elements. The elements are taken from either the beginning or the end of the source string or number, as specified by the value and sign (positive or negative) of limit. If a number is used as input, it is converted to a string.
         * @param {number} input Source string or number to be limited.
         * @param {number} limit The length of the returned string. If the limit number is positive, limit number of items from the beginning of the source string are copied. If the number is negative, limit number of items from the end of the source string are copied. The limit will be trimmed if it exceeds input.length. If limit is undefined, the input will be returned unchanged.
         * @param {string} begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {string} A new substring of length limit or less if input had less than limit elements.
         */
        (input : number, limit : number, begin : string) : string;

        /**
         * Creates a new string containing only a specified number of elements. The elements are taken from either the beginning or the end of the source string or number, as specified by the value and sign (positive or negative) of limit. If a number is used as input, it is converted to a string.
         * @param {string} input Source string or number to be limited.
         * @param {string} limit The length of the returned string. If the limit number is positive, limit number of items from the beginning of the source string are copied. If the number is negative, limit number of items from the end of the source string are copied. The limit will be trimmed if it exceeds input.length. If limit is undefined, the input will be returned unchanged.
         * @param {number} begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {string} A new substring of length limit or less if input had less than limit elements.
         */
        (input : string, limit : string, begin : number) : string;

        /**
         * Creates a new string containing only a specified number of elements. The elements are taken from either the beginning or the end of the source string or number, as specified by the value and sign (positive or negative) of limit. If a number is used as input, it is converted to a string.
         * @param {string} input Source string or number to be limited.
         * @param {number} limit The length of the returned string. If the limit number is positive, limit number of items from the beginning of the source string are copied. If the number is negative, limit number of items from the end of the source string are copied. The limit will be trimmed if it exceeds input.length. If limit is undefined, the input will be returned unchanged.
         * @param {string} begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {string} A new substring of length limit or less if input had less than limit elements.
         */
        (input : string, limit : number, begin : string) : string;

        /**
         * Creates a new string containing only a specified number of elements. The elements are taken from either the beginning or the end of the source string or number, as specified by the value and sign (positive or negative) of limit. If a number is used as input, it is converted to a string.
         * @param {number} input Source string or number to be limited.
         * @param {string} limit The length of the returned string. If the limit number is positive, limit number of items from the beginning of the source string are copied. If the number is negative, limit number of items from the end of the source string are copied. The limit will be trimmed if it exceeds input.length. If limit is undefined, the input will be returned unchanged.
         * @param {number} begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {string} A new substring of length limit or less if input had less than limit elements.
         */
        (input : number, limit : string, begin : number) : string;

        /**
         * Creates a new string containing only a specified number of elements. The elements are taken from either the beginning or the end of the source string or number, as specified by the value and sign (positive or negative) of limit. If a number is used as input, it is converted to a string.
         * @param {string} input Source string or number to be limited.
         * @param {number} limit The length of the returned string. If the limit number is positive, limit number of items from the beginning of the source string are copied. If the number is negative, limit number of items from the end of the source string are copied. The limit will be trimmed if it exceeds input.length. If limit is undefined, the input will be returned unchanged.
         * @param {number} begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {string} A new substring of length limit or less if input had less than limit elements.
         */
        (input : string, limit : number, begin : number) : string;

        /**
         * Creates a new array containing only a specified number of elements. The elements are taken from either the beginning or the end of the source array, string or number, as specified by the value and sign (positive or negative) of limit.
         * @param {Array} input Source array to be limited.
         * @param {number} limit The length of the returned array. If the limit number is positive, limit number of items from the beginning of the source array/string are copied. If the number is negative, limit number of items from the end of the source array are copied. The limit will be trimmed if it exceeds array.length. If limit is undefined, the input will be returned unchanged.
         * @param begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {Array} A new sub-array of length limit or less if input array had less than limit elements.
         */
        <T>(input : T[], limit : number) : T[];

        /**
         * Creates a new string containing only a specified number of elements. The elements are taken from either the beginning or the end of the source string or number, as specified by the value and sign (positive or negative) of limit. If a number is used as input, it is converted to a string.
         * @param {string} input Source string or number to be limited.
         * @param {number} limit The length of the returned string. If the limit number is positive, limit number of items from the beginning of the source string are copied. If the number is negative, limit number of items from the end of the source string are copied. The limit will be trimmed if it exceeds input.length. If limit is undefined, the input will be returned unchanged.
         * @param begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {string} A new substring of length limit or less if input had less than limit elements.
         */
        (input : string, limit : number) : string;

        /**
         * Creates a new string containing only a specified number of elements. The elements are taken from either the beginning or the end of the source string or number, as specified by the value and sign (positive or negative) of limit. If a number is used as input, it is converted to a string.
         * @param {number} input Source string or number to be limited.
         * @param {number} limit The length of the returned string. If the limit number is positive, limit number of items from the beginning of the source string are copied. If the number is negative, limit number of items from the end of the source string are copied. The limit will be trimmed if it exceeds input.length. If limit is undefined, the input will be returned unchanged.
         * @param begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {string} A new substring of length limit or less if input had less than limit elements.
         */
        (input : number, limit : number) : string;

        /**
         * Creates a new string containing only a specified number of elements. The elements are taken from either the beginning or the end of the source string or number, as specified by the value and sign (positive or negative) of limit. If a number is used as input, it is converted to a string.
         * @param {number} input Source string or number to be limited.
         * @param {string} limit The length of the returned string. If the limit number is positive, limit number of items from the beginning of the source string are copied. If the number is negative, limit number of items from the end of the source string are copied. The limit will be trimmed if it exceeds input.length. If limit is undefined, the input will be returned unchanged.
         * @param begin Index at which to begin limitation. As a negative index, begin indicates an offset from the end of input. Defaults to 0.
         * @return {string} A new substring of length limit or less if input had less than limit elements.
         */
        (input : number, limit : string) : string;
    }
}
declare namespace ng {
    export interface IFilterLowercase {
        /**
         * Converts string to lowercase.
         * @param {string} value
         * @return {string}
         */
        (value : string) : string;
    }
}
declare namespace ng {
    export interface IFilterNumber {
        /**
         * Formats a number as text.
         * @param number Number to format.
         * @param {number} fractionSize Number of decimal places to round the number to. If this is not provided then the fraction size is computed from the current locale's number formatting pattern. In the case of the default locale, it will be 3.
         * @return {string} Number rounded to decimalPlaces and places a "," after each third digit.
         * @param {number} value
         */
        (value : number, fractionSize : number) : string;

        /**
         * Formats a number as text.
         * @param number Number to format.
         * @param fractionSize Number of decimal places to round the number to. If this is not provided then the fraction size is computed from the current locale's number formatting pattern. In the case of the default locale, it will be 3.
         * @return {string} Number rounded to decimalPlaces and places a "," after each third digit.
         * @param {number} value
         */
        (value : number) : string;

        /**
         * Formats a number as text.
         * @param number Number to format.
         * @param {number} fractionSize Number of decimal places to round the number to. If this is not provided then the fraction size is computed from the current locale's number formatting pattern. In the case of the default locale, it will be 3.
         * @return {string} Number rounded to decimalPlaces and places a "," after each third digit.
         * @param {string} value
         */
        (value : string, fractionSize : number) : string;

        /**
         * Formats a number as text.
         * @param number Number to format.
         * @param {string} fractionSize Number of decimal places to round the number to. If this is not provided then the fraction size is computed from the current locale's number formatting pattern. In the case of the default locale, it will be 3.
         * @return {string} Number rounded to decimalPlaces and places a "," after each third digit.
         * @param {number} value
         */
        (value : number, fractionSize : string) : string;

        /**
         * Formats a number as text.
         * @param number Number to format.
         * @param {string} fractionSize Number of decimal places to round the number to. If this is not provided then the fraction size is computed from the current locale's number formatting pattern. In the case of the default locale, it will be 3.
         * @return {string} Number rounded to decimalPlaces and places a "," after each third digit.
         * @param {string} value
         */
        (value : string, fractionSize : string) : string;

        /**
         * Formats a number as text.
         * @param number Number to format.
         * @param fractionSize Number of decimal places to round the number to. If this is not provided then the fraction size is computed from the current locale's number formatting pattern. In the case of the default locale, it will be 3.
         * @return {string} Number rounded to decimalPlaces and places a "," after each third digit.
         * @param {string} value
         */
        (value : string) : string;
    }
}
declare namespace ng {
    export interface IFilterOrderBy {
        /**
         * Orders a specified array by the expression predicate. It is ordered alphabetically for strings and numerically for numbers. Note: if you notice numbers are not being sorted as expected, make sure they are actually being saved as numbers and not strings.
         * @param {Array} array The array to sort.
         * @param {string} expression A predicate to be used by the comparator to determine the order of elements.
         * @param {boolean} reverse Reverse the order of the array.
         * @return {Array} Reverse the order of the array.
         */
        <T>(array : T[], expression : string, reverse : boolean) : T[];

        /**
         * Orders a specified array by the expression predicate. It is ordered alphabetically for strings and numerically for numbers. Note: if you notice numbers are not being sorted as expected, make sure they are actually being saved as numbers and not strings.
         * @param {Array} array The array to sort.
         * @param {string} expression A predicate to be used by the comparator to determine the order of elements.
         * @param reverse Reverse the order of the array.
         * @return {Array} Reverse the order of the array.
         */
        <T>(array : T[], expression : string) : T[];

        /**
         * Orders a specified array by the expression predicate. It is ordered alphabetically for strings and numerically for numbers. Note: if you notice numbers are not being sorted as expected, make sure they are actually being saved as numbers and not strings.
         * @param {Array} array The array to sort.
         * @param {Array} expression A predicate to be used by the comparator to determine the order of elements.
         * @param {boolean} reverse Reverse the order of the array.
         * @return {Array} Reverse the order of the array.
         */
        <T>(array : T[], expression : string[], reverse : boolean) : T[];

        /**
         * Orders a specified array by the expression predicate. It is ordered alphabetically for strings and numerically for numbers. Note: if you notice numbers are not being sorted as expected, make sure they are actually being saved as numbers and not strings.
         * @param {Array} array The array to sort.
         * @param {Array} expression A predicate to be used by the comparator to determine the order of elements.
         * @param {boolean} reverse Reverse the order of the array.
         * @return {Array} Reverse the order of the array.
         */
        <T>(array : T[], expression : ((p1: T) => any)[], reverse : boolean) : T[];

        /**
         * Orders a specified array by the expression predicate. It is ordered alphabetically for strings and numerically for numbers. Note: if you notice numbers are not being sorted as expected, make sure they are actually being saved as numbers and not strings.
         * @param {Array} array The array to sort.
         * @param {*} expression A predicate to be used by the comparator to determine the order of elements.
         * @param {boolean} reverse Reverse the order of the array.
         * @return {Array} Reverse the order of the array.
         */
        <T>(array : T[], expression : (p1: T) => any, reverse : boolean) : T[];

        /**
         * Orders a specified array by the expression predicate. It is ordered alphabetically for strings and numerically for numbers. Note: if you notice numbers are not being sorted as expected, make sure they are actually being saved as numbers and not strings.
         * @param {Array} array The array to sort.
         * @param {Array} expression A predicate to be used by the comparator to determine the order of elements.
         * @param reverse Reverse the order of the array.
         * @return {Array} Reverse the order of the array.
         */
        <T>(array : T[], expression : string[]) : T[];

        /**
         * Orders a specified array by the expression predicate. It is ordered alphabetically for strings and numerically for numbers. Note: if you notice numbers are not being sorted as expected, make sure they are actually being saved as numbers and not strings.
         * @param {Array} array The array to sort.
         * @param {*} expression A predicate to be used by the comparator to determine the order of elements.
         * @param reverse Reverse the order of the array.
         * @return {Array} Reverse the order of the array.
         */
        <T>(array : T[], expression : (p1: T) => any) : T[];

        /**
         * Orders a specified array by the expression predicate. It is ordered alphabetically for strings and numerically for numbers. Note: if you notice numbers are not being sorted as expected, make sure they are actually being saved as numbers and not strings.
         * @param {Array} array The array to sort.
         * @param {Array} expression A predicate to be used by the comparator to determine the order of elements.
         * @param reverse Reverse the order of the array.
         * @return {Array} Reverse the order of the array.
         */
        <T>(array : T[], expression : ((p1: T) => any)[]) : T[];
    }
}
declare namespace ng {
    /**
     * $filterProvider - $filter - provider in module ng
     * 
     * Filters are just functions which transform input to an output. However filters need to be Dependency Injected. To achieve this a filter definition consists of a factory function which is annotated with dependencies and is responsible for creating a filter function.
     * 
     * see https://docs.angularjs.org/api/ng/provider/$filterProvider
     * @class
     * @extends *
     */
    export interface IFilterProvider extends ng.IServiceProvider {
        /**
         * register(name);
         * 
         * @param {string} name Name of the filter function, or an object map of filters where the keys are the filter names and the values are the filter factories. Note: Filter names must be valid angular Expressions identifiers, such as uppercase or orderBy. Names with special characters, such as hyphens and dots, are not allowed. If you wish to namespace your filters, then you can use capitalization (myappSubsectionFilterx) or underscores (myapp_subsection_filterx).
         * @return {*}
         */
        register(name : string) : ng.IServiceProvider;

        /**
         * register(name);
         * 
         * @param {*} name Name of the filter function, or an object map of filters where the keys are the filter names and the values are the filter factories. Note: Filter names must be valid angular Expressions identifiers, such as uppercase or orderBy. Names with special characters, such as hyphens and dots, are not allowed. If you wish to namespace your filters, then you can use capitalization (myappSubsectionFilterx) or underscores (myapp_subsection_filterx).
         * @return {*}
         */
        register(name : any) : ng.IServiceProvider;
    }
}
declare namespace ng {
    /**
     * $filter - $filterProvider - service in module ng
     * 
     * Filters are used for formatting data displayed to the user.
     * 
     * see https://docs.angularjs.org/api/ng/service/$filter
     * @class
     * @extends Object
     */
    export interface IFilterService {
        (name : "filter") : ng.IFilterFilter;

        (name : "currency") : ng.IFilterCurrency;

        (name : "_number") : ng.IFilterNumber;

        (name : "date") : ng.IFilterDate;

        (name : "json") : ng.IFilterJson;

        (name : "lowercase") : ng.IFilterLowercase;

        (name : "uppercase") : ng.IFilterUppercase;

        (name : "limitTo") : ng.IFilterLimitTo;

        (name : "orderBy") : ng.IFilterOrderBy;

        /**
         * Usage:
         * $filter(name);
         * 
         * @param {string} name Name of the filter function to retrieve
         * @return {*}
         */
        <T>(name : string) : T;
    }
}
declare namespace ng {
    export interface IFilterUppercase {
        /**
         * Converts string to uppercase.
         * @param {string} value
         * @return {string}
         */
        (value : string) : string;
    }
}
declare namespace ng {
    /**
     * form.FormController - type in module ng
     * see https://docs.angularjs.org/api/ng/type/form.FormController
     * @class
     * @extends Object
     */
    export interface IFormController {
        [name : string]: any;

        $pristine : boolean;

        $dirty : boolean;

        $valid : boolean;

        $invalid : boolean;

        $submitted : boolean;

        $error : any;

        $addControl(control : ng.INgModelController);

        $removeControl(control : ng.INgModelController);

        $setValidity(validationErrorKey : string, isValid : boolean, control : ng.INgModelController);

        $setDirty();

        $setPristine();

        $commitViewValue();

        $rollbackViewValue();

        $setSubmitted();

        $setUntouched();
    }
}
declare namespace ng {
    export interface IHttpBackendService {
        (method : string, url : string, post : any, callback : Function, headers : any, timeout : number, withCredentials : boolean);

        (method : string, url : string, post : any, callback : Function, headers : any, timeout : number);

        (method : string, url : string, post : any, callback : Function, headers : any);

        (method : string, url : string, post : any, callback : Function);

        (method : string, url : string, post : any);

        (method : string, url : string);
    }
}
declare namespace ng {
    export interface IHttpHeadersGetter {
        () : any;

        (headerName : string) : string;
    }
}
declare namespace ng {
    export interface IHttpInterceptor {
        request? : (p1: ng.IRequestConfig) => ((ng.IRequestConfig)|(ng.IPromise<ng.IRequestConfig>));

        requestError? : (p1: any) => any;

        response? : (p1: ng.IHttpPromiseCallbackArg<any>) => ((ng.IPromise<any>)|(any));

        responseError? : (p1: any) => any;
    }
}
declare namespace ng {
    export interface IHttpInterceptorFactory {
        (...args : any[]) : ng.IHttpInterceptor;
    }
}
declare namespace ng {
    export interface IHttpPromise<T> extends ng.IPromise<ng.IHttpPromiseCallbackArg<T>> {
        success(callback : ng.IHttpPromiseCallback<T>) : IHttpPromise<T>;

        error(callback : ng.IHttpPromiseCallback<any>) : IHttpPromise<T>;
    }
}
declare namespace ng {
    export interface IHttpPromiseCallback<T> {
        (data : T, status : number, headers : ng.IHttpHeadersGetter, config : ng.IRequestConfig);
    }
}
declare namespace ng {
    export interface IHttpPromiseCallbackArg<T> {
        data? : T;

        status? : number;

        headers? : any;

        config? : ng.IRequestConfig;

        statusText? : string;
    }
}
declare namespace ng {
    export interface IHttpProvider extends ng.IServiceProvider {
        defaults : ng.IHttpProviderDefaults;

        /**
         * Register service factories (names or implementations) for interceptors which are called before and after
         * each request.
         */
        interceptors : ((string)|(ng.IHttpInterceptorFactory)|(((string)|(ng.IHttpInterceptorFactory))[]))[];

        useApplyAsync() : boolean;

        useApplyAsync(value : boolean) : IHttpProvider;

        /**
         * 
         * @param {boolean=} value If true, `$http` will return a normal promise without the `success` and `error` methods.
         * @returns {boolean|Object} If a value is specified, returns the $httpProvider for chaining.
         * otherwise, returns the current configured value.
         * @param {boolean} value
         * @return {*}
         */
        useLegacyPromiseExtensions(value : boolean) : ((boolean)|(IHttpProvider));
    }
}
declare namespace ng {
    /**
     * Object that controls the defaults for $http provider. Not all fields of IRequestShortcutConfig can be configured
     * via defaults and the docs do not say which. The following is based on the inspection of the source code.
     * https://docs.angularjs.org/api/ng/service/$http#defaults
     * https://docs.angularjs.org/api/ng/service/$http#usage
     * https://docs.angularjs.org/api/ng/provider/$httpProvider The properties section
     * @class
     * @extends Object
     */
    export interface IHttpProviderDefaults {
        /**
         * {boolean|Cache}
         * If true, a default $http cache will be used to cache the GET request, otherwise if a cache instance built with $cacheFactory, this cache will be used for caching.
         */
        cache? : any;

        /**
         * Transform function or an array of such functions. The transform function takes the http request body and
         * headers and returns its transformed (typically serialized) version.
         * @see {@link https://docs.angularjs.org/api/ng/service/$http#transforming-requests-and-responses}
         */
        transformRequest? : ((ng.IHttpRequestTransformer)|(ng.IHttpRequestTransformer[]));

        /**
         * Transform function or an array of such functions. The transform function takes the http response body and
         * headers and returns its transformed (typically deserialized) version.
         */
        transformResponse? : ((ng.IHttpResponseTransformer)|(ng.IHttpResponseTransformer[]));

        /**
         * Map of strings or functions which return strings representing HTTP headers to send to the server. If the
         * return value of a function is null, the header will not be sent.
         * The key of the map is the request verb in lower case. The "common" key applies to all requests.
         * @see {@link https://docs.angularjs.org/api/ng/service/$http#setting-http-headers}
         */
        headers? : ng.IHttpRequestConfigHeaders;

        /**
         * Name of HTTP header to populate with the XSRF token.
         */
        xsrfHeaderName? : string;

        /**
         * Name of cookie containing the XSRF token.
         */
        xsrfCookieName? : string;

        /**
         * whether to to set the withCredentials flag on the XHR object. See [requests with credentials]https://developer.mozilla.org/en/http_access_control#section_5 for more information.
         */
        withCredentials? : boolean;

        /**
         * A function used to the prepare string representation of request parameters (specified as an object). If
         * specified as string, it is interpreted as a function registered with the $injector. Defaults to
         * $httpParamSerializer.
         */
        paramSerializer? : ((string)|((p1: any) => string));
    }
}
declare namespace ng {
    export interface IHttpRequestConfigHeaders {
        [requestType : string]: ((string)|(() => string));

        common? : ((string)|(() => string));

        get? : ((string)|(() => string));

        post? : ((string)|(() => string));

        put? : ((string)|(() => string));

        patch? : ((string)|(() => string));
    }
}
declare namespace ng {
    export interface IHttpRequestTransformer {
        (data : any, headersGetter : ng.IHttpHeadersGetter) : any;
    }
}
declare namespace ng {
    export interface IHttpResponseTransformer {
        (data : any, headersGetter : ng.IHttpHeadersGetter, status : number) : any;
    }
}
declare namespace ng {
    /**
     * HttpService
     * see http://docs.angularjs.org/api/ng/service/$http
     * @class
     * @extends Object
     */
    export interface IHttpService {
        /**
         * Object describing the request to be made and how it should be processed.
         * @param {*} config
         * @return {*}
         */
        <T>(config : ng.IRequestConfig) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform GET request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param {*} config Optional configuration object
         * @return {*}
         */
        get<T>(url : string, config : ng.IRequestShortcutConfig) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform DELETE request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param {*} config Optional configuration object
         * @return {*}
         */
        delete<T>(url : string, config : ng.IRequestShortcutConfig) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform HEAD request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param {*} config Optional configuration object
         * @return {*}
         */
        head<T>(url : string, config : ng.IRequestShortcutConfig) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform JSONP request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param {*} config Optional configuration object
         * @return {*}
         */
        jsonp<T>(url : string, config : ng.IRequestShortcutConfig) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform POST request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param {*} data Request content
         * @param {*} config Optional configuration object
         * @return {*}
         */
        post<T>(url : string, data : any, config : ng.IRequestShortcutConfig) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform PUT request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param {*} data Request content
         * @param {*} config Optional configuration object
         * @return {*}
         */
        put<T>(url : string, data : any, config : ng.IRequestShortcutConfig) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform PATCH request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param {*} data Request content
         * @param {*} config Optional configuration object
         * @return {*}
         */
        patch<T>(url : string, data : any, config : ng.IRequestShortcutConfig) : ng.IHttpPromise<T>;

        /**
         * Runtime equivalent of the $httpProvider.defaults property. Allows configuration of default headers, withCredentials as well as request and response transformations.
         */
        defaults : ng.IHttpProviderDefaults;

        /**
         * Array of config objects for currently pending requests. This is primarily meant to be used for debugging purposes.
         */
        pendingRequests : ng.IRequestConfig[];

        /**
         * Shortcut method to perform GET request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param config Optional configuration object
         * @return {*}
         */
        get<T>(url : string) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform DELETE request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param config Optional configuration object
         * @return {*}
         */
        delete<T>(url : string) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform HEAD request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param config Optional configuration object
         * @return {*}
         */
        head<T>(url : string) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform JSONP request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param config Optional configuration object
         * @return {*}
         */
        jsonp<T>(url : string) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform POST request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param {*} data Request content
         * @param config Optional configuration object
         * @return {*}
         */
        post<T>(url : string, data : any) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform PUT request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param {*} data Request content
         * @param config Optional configuration object
         * @return {*}
         */
        put<T>(url : string, data : any) : ng.IHttpPromise<T>;

        /**
         * Shortcut method to perform PATCH request.
         * 
         * @param {string} url Relative or absolute URL specifying the destination of the request
         * @param {*} data Request content
         * @param config Optional configuration object
         * @return {*}
         */
        patch<T>(url : string, data : any) : ng.IHttpPromise<T>;
    }
}
declare namespace ng {
    export interface IInterpolateProvider extends ng.IServiceProvider {
        startSymbol() : string;

        startSymbol(value : string) : IInterpolateProvider;

        endSymbol() : string;

        endSymbol(value : string) : IInterpolateProvider;
    }
}
declare namespace ng {
    export interface IInterpolateService {
        (text : string, mustHaveExpression : boolean, trustedContext : string, allOrNothing : boolean) : ng.IInterpolationFunction;

        endSymbol() : string;

        startSymbol() : string;

        (text : string, mustHaveExpression : boolean, trustedContext : string) : ng.IInterpolationFunction;

        (text : string, mustHaveExpression : boolean) : ng.IInterpolationFunction;

        (text : string) : ng.IInterpolationFunction;
    }
}
declare namespace ng {
    export interface IInterpolationFunction {
        (context : any) : string;
    }
}
declare namespace ng {
    export interface IIntervalService {
        (func : Function, delay : number, count : number, invokeApply : boolean, ...args : any[]) : ng.IPromise<any>;

        cancel(promise : ng.IPromise<any>) : boolean;

        (func : Function, delay : number, count : number) : ng.IPromise<any>;

        (func : Function, delay : number) : ng.IPromise<any>;
    }
}
declare namespace ng {
    export interface ILocaleDateTimeFormatDescriptor {
        MONTH : string[];

        SHORTMONTH : string[];

        DAY : string[];

        SHORTDAY : string[];

        AMPMS : string[];

        medium : string;

        short : string;

        fullDate : string;

        longDate : string;

        mediumDate : string;

        shortDate : string;

        mediumTime : string;

        shortTime : string;
    }
}
declare namespace ng {
    export interface ILocaleNumberFormatDescriptor {
        DECIMAL_SEP : string;

        GROUP_SEP : string;

        PATTERNS : ng.ILocaleNumberPatternDescriptor[];

        CURRENCY_SYM : string;
    }
}
declare namespace ng {
    export interface ILocaleNumberPatternDescriptor {
        minInt : number;

        minFrac : number;

        maxFrac : number;

        posPre : string;

        posSuf : string;

        negPre : string;

        negSuf : string;

        gSize : number;

        lgSize : number;
    }
}
declare namespace ng {
    export interface ILocaleService {
        id : string;

        NUMBER_FORMATS : ng.ILocaleNumberFormatDescriptor;

        DATETIME_FORMATS : ng.ILocaleDateTimeFormatDescriptor;

        pluralCat : (p1: any) => string;
    }
}
declare namespace ng {
    export interface ILocationProvider extends ng.IServiceProvider {
        hashPrefix() : string;

        hashPrefix(prefix : string) : ILocationProvider;

        html5Mode() : boolean;

        html5Mode(active : boolean) : ILocationProvider;

        html5Mode(mode : any) : ILocationProvider;
    }
}
declare namespace ng {
    /**
     * $location - $locationProvider - service in module ng
     * see https://docs.angularjs.org/api/ng/service/$location
     * @class
     * @extends Object
     */
    export interface ILocationService {
        absUrl() : string;

        hash() : string;

        hash(newHash : string) : ILocationService;

        host() : string;

        /**
         * Return path of current url
         * @return {string}
         */
        path() : string;

        /**
         * Change path when called with parameter and return $location.
         * Note: Path should always begin with forward slash (/), this method will add the forward slash if it is missing.
         * 
         * @param {string} path New path
         * @return {*}
         */
        path(path : string) : ILocationService;

        port() : number;

        protocol() : string;

        replace() : ILocationService;

        /**
         * Return search part (as object) of current url
         * @return {*}
         */
        search() : any;

        /**
         * Change search part when called with parameter and return $location.
         * 
         * @param {*} search When called with a single argument the method acts as a setter, setting the search component of $location to the specified value.
         * 
         * If the argument is a hash object containing an array of values, these values will be encoded as duplicate search parameters in the url.
         * @return {*}
         */
        search(search : any) : ILocationService;

        /**
         * Change search part when called with parameter and return $location.
         * 
         * @param {string} search New search params
         * @param {*} paramValue If search is a string or a Number, then paramValue will override only a single search property. If paramValue is null, the property specified via the first argument will be deleted. If paramValue is an array, it will override the property of the search component of $location specified via the first argument. If paramValue is true, the property specified via the first argument will be added with no value nor trailing equal sign.
         * @return {*}
         */
        search(search : string, paramValue : any) : ILocationService;

        state() : any;

        state(state : any) : ILocationService;

        url() : string;

        url(url : string) : ILocationService;
    }
}
declare namespace ng {
    export interface ILogCall {
        (...args : any[]);
    }
}
declare namespace ng {
    export interface ILogProvider extends ng.IServiceProvider {
        debugEnabled() : boolean;

        debugEnabled(enabled : boolean) : ILogProvider;
    }
}
declare namespace ng {
    export interface ILogService {
        debug : any;

        error : any;

        info : any;

        log : any;

        warn : any;
    }
}
declare namespace ng {
    export interface IModelFormatter {
        (value : any) : any;
    }
}
declare namespace ng {
    export interface IModelParser {
        (value : any) : any;
    }
}
declare namespace ng {
    export interface IModelValidators {
        [index : string]: (p1: any, p2: any) => boolean;
    }
}
declare namespace ng {
    export interface IModelViewChangeListener {
        ();
    }
}
declare namespace ng {
    export interface IModule {
        animation(name : string, animationFactory : Function) : IModule;

        animation(name : string, inlineAnnotatedFunction : any[]) : IModule;

        animation(object : any) : IModule;

        /**
         * Use this method to register a component.
         * 
         * @param {string} name The name of the component.
         * @param {*} options A definition object passed into the component.
         * @return {*}
         */
        component(name : string, options : ng.IComponentOptions) : IModule;

        /**
         * Use this method to register work which needs to be performed on module loading.
         * 
         * @param {Function} configFn Execute this function on module load. Useful for service configuration.
         * @return {*}
         */
        config(configFn : Function) : IModule;

        /**
         * Use this method to register work which needs to be performed on module loading.
         * 
         * @param {Array} inlineAnnotatedFunction Execute this function on module load. Useful for service configuration.
         * @return {*}
         */
        config(inlineAnnotatedFunction : any[]) : IModule;

        config(object : any) : IModule;

        /**
         * Register a constant service, such as a string, a number, an array, an object or a function, with the $injector. Unlike value it can be injected into a module configuration function (see config) and it cannot be overridden by an Angular decorator.
         * 
         * @param {string} name The name of the constant.
         * @param {*} value The constant value.
         * @return {*}
         */
        constant(name : string, value : any) : IModule;

        constant(object : any) : IModule;

        /**
         * The $controller service is used by Angular to create new controllers.
         * 
         * This provider allows controller registration via the register method.
         * 
         * @param {string} name Controller name, or an object map of controllers where the keys are the names and the values are the constructors.
         * @param {Function} controllerConstructor Controller constructor fn (optionally decorated with DI annotations in the array notation).
         * @return {*}
         */
        controller(name : string, controllerConstructor : Function) : IModule;

        /**
         * The $controller service is used by Angular to create new controllers.
         * 
         * This provider allows controller registration via the register method.
         * 
         * @param {string} name Controller name, or an object map of controllers where the keys are the names and the values are the constructors.
         * @param controllerConstructor Controller constructor fn (optionally decorated with DI annotations in the array notation).
         * @param {Array} inlineAnnotatedConstructor
         * @return {*}
         */
        controller(name : string, inlineAnnotatedConstructor : any[]) : IModule;

        controller(object : any) : IModule;

        /**
         * Register a new directive with the compiler.
         * 
         * @param {string} name Name of the directive in camel-case (i.e. ngBind which will match as ng-bind)
         * @param {*} directiveFactory An injectable directive factory function.
         * @return {*}
         */
        directive(name : string, directiveFactory : ng.IDirectiveFactory) : IModule;

        /**
         * Register a new directive with the compiler.
         * 
         * @param {string} name Name of the directive in camel-case (i.e. ngBind which will match as ng-bind)
         * @param directiveFactory An injectable directive factory function.
         * @param {Array} inlineAnnotatedFunction
         * @return {*}
         */
        directive(name : string, inlineAnnotatedFunction : any[]) : IModule;

        directive(object : any) : IModule;

        /**
         * Register a service factory, which will be called to return the service instance. This is short for registering a service where its provider consists of only a $get property, which is the given service factory function. You should use $provide.factory(getFn) if you do not need to configure your service in a provider.
         * 
         * @param {string} name The name of the instance.
         * @param $getFn The $getFn for the instance creation. Internally this is a short hand for $provide.provider(name, {$get: $getFn}).
         * @param {Function} $getFn
         * @return {*}
         */
        factory(name : string, $getFn : Function) : IModule;

        /**
         * Register a service factory, which will be called to return the service instance. This is short for registering a service where its provider consists of only a $get property, which is the given service factory function. You should use $provide.factory(getFn) if you do not need to configure your service in a provider.
         * 
         * @param {string} name The name of the instance.
         * @param {Array} inlineAnnotatedFunction The $getFn for the instance creation. Internally this is a short hand for $provide.provider(name, {$get: $getFn}).
         * @return {*}
         */
        factory(name : string, inlineAnnotatedFunction : any[]) : IModule;

        factory(object : any) : IModule;

        filter(name : string, filterFactoryFunction : Function) : IModule;

        filter(name : string, inlineAnnotatedFunction : any[]) : IModule;

        filter(object : any) : IModule;

        provider(name : string, serviceProviderFactory : ng.IServiceProviderFactory) : IModule;

        provider(name : string, serviceProviderConstructor : ng.IServiceProviderClass) : IModule;

        provider(name : string, inlineAnnotatedConstructor : any[]) : IModule;

        provider(name : string, providerObject : ng.IServiceProvider) : IModule;

        provider(object : any) : IModule;

        /**
         * Run blocks are the closest thing in Angular to the main method. A run block is the code which needs to run to kickstart the application. It is executed after all of the service have been configured and the injector has been created. Run blocks typically contain code which is hard to unit-test, and for this reason should be declared in isolated modules, so that they can be ignored in the unit-tests.
         * @param {Function} initializationFunction
         * @return {*}
         */
        run(initializationFunction : Function) : IModule;

        /**
         * Run blocks are the closest thing in Angular to the main method. A run block is the code which needs to run to kickstart the application. It is executed after all of the service have been configured and the injector has been created. Run blocks typically contain code which is hard to unit-test, and for this reason should be declared in isolated modules, so that they can be ignored in the unit-tests.
         * @param {Array} inlineAnnotatedFunction
         * @return {*}
         */
        run(inlineAnnotatedFunction : any[]) : IModule;

        service(name : string, serviceConstructor : Function) : IModule;

        service(name : string, inlineAnnotatedConstructor : any[]) : IModule;

        service(object : any) : IModule;

        /**
         * Register a value service with the $injector, such as a string, a number, an array, an object or a function. This is short for registering a service where its provider's $get property is a factory function that takes no arguments and returns the value service.
         * 
         * Value services are similar to constant services, except that they cannot be injected into a module configuration function (see config) but they can be overridden by an Angular decorator.
         * 
         * @param {string} name The name of the instance.
         * @param {*} value The value.
         * @return {*}
         */
        value(name : string, value : any) : IModule;

        value(object : any) : IModule;

        /**
         * Register a service decorator with the $injector. A service decorator intercepts the creation of a service, allowing it to override or modify the behaviour of the service. The object returned by the decorator may be the original service, or a new service object which replaces or wraps and delegates to the original service.
         * @param {string} name The name of the service to decorate
         * @param decorator This function will be invoked when the service needs to be instantiated and should return the decorated service instance. The function is called using the injector.invoke method and is therefore fully injectable. Local injection arguments: $delegate - The original service instance, which can be monkey patched, configured, decorated or delegated to.
         * @param {Function} decoratorConstructor
         * @return {*}
         */
        decorator(name : string, decoratorConstructor : Function) : IModule;

        decorator(name : string, inlineAnnotatedConstructor : any[]) : IModule;

        name : string;

        requires : string[];
    }
}
declare namespace ng {
    export interface INgModelController {
        $render();

        $setValidity(validationErrorKey : string, isValid : boolean);

        $setViewValue(value : any, trigger : string);

        $setPristine();

        $setDirty();

        $validate();

        $setTouched();

        $setUntouched();

        $rollbackViewValue();

        $commitViewValue();

        $isEmpty(value : any) : boolean;

        $viewValue : any;

        $modelValue : any;

        $parsers : ng.IModelParser[];

        $formatters : ng.IModelFormatter[];

        $viewChangeListeners : ng.IModelViewChangeListener[];

        $error : any;

        $name : string;

        $touched : boolean;

        $untouched : boolean;

        $validators : ng.IModelValidators;

        $asyncValidators : ng.IAsyncModelValidators;

        $pending : any;

        $pristine : boolean;

        $dirty : boolean;

        $valid : boolean;

        $invalid : boolean;

        $setViewValue(value : any);
    }
}
declare namespace ng {
    export interface IParseProvider {
        logPromiseWarnings() : boolean;

        logPromiseWarnings(value : boolean) : IParseProvider;

        unwrapPromises() : boolean;

        unwrapPromises(value : boolean) : IParseProvider;
    }
}
declare namespace ng {
    export interface IParseService {
        (expression : string, interceptorFn : (p1: any, p2: ng.IScope, p3: any) => any, expensiveChecks : boolean) : ng.ICompiledExpression;

        (expression : string, interceptorFn : (p1: any, p2: ng.IScope, p3: any) => any) : ng.ICompiledExpression;

        (expression : string) : ng.ICompiledExpression;
    }
}
declare namespace ng {
    export interface IPromise<T> {
        /**
         * Regardless of when the promise was or will be resolved or rejected, then calls one of the success or error callbacks asynchronously as soon as the result is available. The callbacks are called with a single argument: the result or rejection reason. Additionally, the notify callback may be called zero or more times to provide a progress indication, before the promise is resolved or rejected.
         * The successCallBack may return IPromise<void> for when a $q.reject() needs to be returned
         * This method returns a new promise which is resolved or rejected via the return value of the successCallback, errorCallback. It also notifies via the return value of the notifyCallback method. The promise can not be resolved or rejected from the notifyCallback method.
         * @param {*} successCallback
         * @param {*} errorCallback
         * @param {*} notifyCallback
         * @return {*}
         */
        then<TResult>(successCallback : (p1: T) => IPromise<TResult>, errorCallback : (p1: any) => any, notifyCallback : (p1: any) => any) : IPromise<TResult>;

        /**
         * Shorthand for promise.then(null, errorCallback)
         * @param {*} onRejected
         * @return {*}
         */
        catch<TResult>(onRejected : (p1: any) => IPromise<TResult>) : IPromise<TResult>;

        /**
         * Allows you to observe either the fulfillment or rejection of a promise, but to do so without modifying the final value. This is useful to release resources or do some clean-up that needs to be done whether the promise was rejected or resolved. See the full specification for more information.
         * 
         * Because finally is a reserved word in JavaScript and reserved keywords are not supported as property names by ES3, you'll need to invoke the method like promise['finally'](callback) to make your code IE8 and Android 2.x compatible.
         * @param {*} finallyCallback
         * @return {*}
         */
        finally(finallyCallback : () => any) : IPromise<T>;

        /**
         * Regardless of when the promise was or will be resolved or rejected, then calls one of the success or error callbacks asynchronously as soon as the result is available. The callbacks are called with a single argument: the result or rejection reason. Additionally, the notify callback may be called zero or more times to provide a progress indication, before the promise is resolved or rejected.
         * The successCallBack may return IPromise<void> for when a $q.reject() needs to be returned
         * This method returns a new promise which is resolved or rejected via the return value of the successCallback, errorCallback. It also notifies via the return value of the notifyCallback method. The promise can not be resolved or rejected from the notifyCallback method.
         * @param {*} successCallback
         * @param {*} errorCallback
         * @return {*}
         */
        then<TResult>(successCallback : (p1: T) => IPromise<TResult>, errorCallback : (p1: any) => any) : IPromise<TResult>;

        /**
         * Regardless of when the promise was or will be resolved or rejected, then calls one of the success or error callbacks asynchronously as soon as the result is available. The callbacks are called with a single argument: the result or rejection reason. Additionally, the notify callback may be called zero or more times to provide a progress indication, before the promise is resolved or rejected.
         * The successCallBack may return IPromise<void> for when a $q.reject() needs to be returned
         * This method returns a new promise which is resolved or rejected via the return value of the successCallback, errorCallback. It also notifies via the return value of the notifyCallback method. The promise can not be resolved or rejected from the notifyCallback method.
         * @param {*} successCallback
         * @return {*}
         */
        then<TResult>(successCallback : (p1: T) => IPromise<TResult>) : IPromise<TResult>;

        /**
         * Regardless of when the promise was or will be resolved or rejected, then calls one of the success or error callbacks asynchronously as soon as the result is available. The callbacks are called with a single argument: the result or rejection reason. Additionally, the notify callback may be called zero or more times to provide a progress indication, before the promise is resolved or rejected.
         * The successCallBack may return IPromise<void> for when a $q.reject() needs to be returned
         * This method returns a new promise which is resolved or rejected via the return value of the successCallback, errorCallback. It also notifies via the return value of the notifyCallback method. The promise can not be resolved or rejected from the notifyCallback method.
         * @param {*} successCallback
         * @param {*} errorCallback
         * @param {*} notifyCallback
         * @return {*}
         */
        then<TResult>(successCallback : (p1: T) => TResult, errorCallback : (p1: any) => any, notifyCallback : (p1: any) => any) : IPromise<TResult>;

        /**
         * Shorthand for promise.then(null, errorCallback)
         * @param {*} onRejected
         * @return {*}
         */
        catch<TResult>(onRejected : (p1: any) => TResult) : IPromise<TResult>;

        /**
         * Regardless of when the promise was or will be resolved or rejected, then calls one of the success or error callbacks asynchronously as soon as the result is available. The callbacks are called with a single argument: the result or rejection reason. Additionally, the notify callback may be called zero or more times to provide a progress indication, before the promise is resolved or rejected.
         * The successCallBack may return IPromise<void> for when a $q.reject() needs to be returned
         * This method returns a new promise which is resolved or rejected via the return value of the successCallback, errorCallback. It also notifies via the return value of the notifyCallback method. The promise can not be resolved or rejected from the notifyCallback method.
         * @param {*} successCallback
         * @param {*} errorCallback
         * @return {*}
         */
        then<TResult>(successCallback : (p1: T) => TResult, errorCallback : (p1: any) => any) : IPromise<TResult>;

        /**
         * Regardless of when the promise was or will be resolved or rejected, then calls one of the success or error callbacks asynchronously as soon as the result is available. The callbacks are called with a single argument: the result or rejection reason. Additionally, the notify callback may be called zero or more times to provide a progress indication, before the promise is resolved or rejected.
         * The successCallBack may return IPromise<void> for when a $q.reject() needs to be returned
         * This method returns a new promise which is resolved or rejected via the return value of the successCallback, errorCallback. It also notifies via the return value of the notifyCallback method. The promise can not be resolved or rejected from the notifyCallback method.
         * @param {*} successCallback
         * @return {*}
         */
        then<TResult>(successCallback : (p1: T) => TResult) : IPromise<TResult>;
    }
}
declare namespace ng {
    export interface IQResolveReject<T> {
        ();

        (value : T);
    }
}
declare namespace ng {
    /**
     * $q - service in module ng
     * A promise/deferred implementation inspired by Kris Kowal's Q.
     * See http://docs.angularjs.org/api/ng/service/$q
     * @param {*} resolver
     * @class
     * @extends Object
     */
    export interface IQService {
        constructor<T>(resolver : (p1: ng.IQResolveReject<T>) => any);

        constructor<T>(resolver : (p1: ng.IQResolveReject<T>, p2: ng.IQResolveReject<any>) => any);

        <T>(resolver : (p1: ng.IQResolveReject<T>) => any) : ng.IPromise<T>;

        <T>(resolver : (p1: ng.IQResolveReject<T>, p2: ng.IQResolveReject<any>) => any) : ng.IPromise<T>;

        /**
         * Combines multiple promises into a single promise that is resolved when all of the input promises are resolved.
         * 
         * Returns a single promise that will be resolved with an array of values, each value corresponding to the promise at the same index in the promises array. If any of the promises is resolved with a rejection, this resulting promise will be rejected with the same rejection value.
         * 
         * @param {Array} promises An array of promises.
         * @return {*}
         */
        all<T>(promises : ng.IPromise<any>[]) : ng.IPromise<T[]>;

        all<T extends any>(promises : any) : ng.IPromise<T>;

        /**
         * Creates a Deferred object which represents a task which will finish in the future.
         * @return {*}
         */
        defer<T>() : ng.IDeferred<T>;

        /**
         * Creates a promise that is resolved as rejected with the specified reason. This api should be used to forward rejection in a chain of promises. If you are dealing with the last promise in a promise chain, you don't need to worry about it.
         * 
         * When comparing deferreds/promises to the familiar behavior of try/catch/throw, think of reject as the throw keyword in JavaScript. This also means that if you "catch" an error via a promise error callback and you want to forward the error to the promise derived from the current promise, you have to "rethrow" the error by returning a rejection constructed via reject.
         * 
         * @param {*} reason Constant, message, exception or an object representing the rejection reason.
         * @return {*}
         */
        reject(reason : any) : ng.IPromise<any>;

        /**
         * Wraps an object that might be a value or a (3rd party) then-able promise into a $q promise. This is useful when you are dealing with an object that might or might not be a promise, or if the promise comes from a source that can't be trusted.
         * 
         * @param {*} value Value or a promise
         * @return {*}
         */
        resolve<T>(value : ng.IPromise<T>) : ng.IPromise<T>;

        /**
         * Wraps an object that might be a value or a (3rd party) then-able promise into a $q promise. This is useful when you are dealing with an object that might or might not be a promise, or if the promise comes from a source that can't be trusted.
         * @return {*}
         */
        resolve() : ng.IPromise<void>;

        /**
         * Wraps an object that might be a value or a (3rd party) then-able promise into a $q promise. This is useful when you are dealing with an object that might or might not be a promise, or if the promise comes from a source that can't be trusted.
         * 
         * @param {*} value Value or a promise
         * @return {*}
         */
        when<T>(value : ng.IPromise<T>) : ng.IPromise<T>;

        /**
         * Wraps an object that might be a value or a (3rd party) then-able promise into a $q promise. This is useful when you are dealing with an object that might or might not be a promise, or if the promise comes from a source that can't be trusted.
         * @return {*}
         */
        when() : ng.IPromise<void>;

        /**
         * Creates a promise that is resolved as rejected with the specified reason. This api should be used to forward rejection in a chain of promises. If you are dealing with the last promise in a promise chain, you don't need to worry about it.
         * 
         * When comparing deferreds/promises to the familiar behavior of try/catch/throw, think of reject as the throw keyword in JavaScript. This also means that if you "catch" an error via a promise error callback and you want to forward the error to the promise derived from the current promise, you have to "rethrow" the error by returning a rejection constructed via reject.
         * 
         * @param reason Constant, message, exception or an object representing the rejection reason.
         * @return {*}
         */
        reject() : ng.IPromise<any>;

        /**
         * Wraps an object that might be a value or a (3rd party) then-able promise into a $q promise. This is useful when you are dealing with an object that might or might not be a promise, or if the promise comes from a source that can't be trusted.
         * 
         * @param {*} value Value or a promise
         * @return {*}
         */
        resolve<T>(value : T) : ng.IPromise<T>;

        /**
         * Wraps an object that might be a value or a (3rd party) then-able promise into a $q promise. This is useful when you are dealing with an object that might or might not be a promise, or if the promise comes from a source that can't be trusted.
         * 
         * @param {*} value Value or a promise
         * @return {*}
         */
        when<T>(value : T) : ng.IPromise<T>;

        constructor();
    }
}
declare namespace ng {
    /**
     * $scope for ngRepeat directive.
     * see https://docs.angularjs.org/api/ng/directive/ngRepeat
     * @class
     * @extends *
     */
    export interface IRepeatScope extends ng.IScope {
        /**
         * iterator offset of the repeated element (0..length-1).
         */
        $index : number;

        /**
         * true if the repeated element is first in the iterator.
         */
        $first : boolean;

        /**
         * true if the repeated element is between the first and last in the iterator.
         */
        $middle : boolean;

        /**
         * true if the repeated element is last in the iterator.
         */
        $last : boolean;

        /**
         * true if the iterator position $index is even (otherwise false).
         */
        $even : boolean;

        /**
         * true if the iterator position $index is odd (otherwise false).
         */
        $odd : boolean;
    }
}
declare namespace ng {
    /**
     * Object describing the request to be made and how it should be processed.
     * see http://docs.angularjs.org/api/ng/service/$http#usage
     * @class
     * @extends *
     */
    export interface IRequestConfig extends ng.IRequestShortcutConfig {
        /**
         * HTTP method (e.g. 'GET', 'POST', etc)
         */
        method : string;

        /**
         * Absolute or relative URL of the resource that is being requested.
         */
        url : string;
    }
}
declare namespace ng {
    /**
     * Object describing the request to be made and how it should be processed.
     * see http://docs.angularjs.org/api/ng/service/$http#usage
     * @class
     * @extends *
     */
    export interface IRequestShortcutConfig extends ng.IHttpProviderDefaults {
        /**
         * {Object.<string|Object>}
         * Map of strings or objects which will be turned to ?key1=value1&key2=value2 after the url. If the value is not a string, it will be JSONified.
         */
        params? : any;

        /**
         * {string|Object}
         * Data to be sent as the request message data.
         */
        data? : any;

        /**
         * Timeout in milliseconds, or promise that should abort the request when resolved.
         */
        timeout? : ((number)|(ng.IPromise<any>));

        /**
         * See [XMLHttpRequest.responseType]https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest#xmlhttprequest-responsetype
         */
        responseType? : string;
    }
}
declare namespace ng {
    export interface IRootElementService extends JQuery {    }
}
declare namespace ng {
    /**
     * $rootScope - $rootScopeProvider - service in module ng
     * see https://docs.angularjs.org/api/ng/type/$rootScope.Scope and https://docs.angularjs.org/api/ng/service/$rootScope
     * @param {boolean} isolate
     * @param {*} parent
     * @class
     * @extends Object
     */
    export interface IRootScopeService {
        [index : string]: any;

        () : any;

        (exp : string) : any;

        (exp : (p1: ng.IScope) => any) : any;

        $applyAsync() : any;

        $applyAsync(exp : string) : any;

        $applyAsync(exp : (p1: ng.IScope) => any) : any;

        /**
         * Dispatches an event name downwards to all child scopes (and their children) notifying the registered $rootScope.Scope listeners.
         * 
         * The event life cycle starts at the scope on which $broadcast was called. All listeners listening for name event on this scope get notified. Afterwards, the event propagates to all direct and indirect scopes of the current scope and calls all registered listeners along the way. The event cannot be canceled.
         * 
         * Any exception emitted from the listeners will be passed onto the $exceptionHandler service.
         * 
         * @param {string} name Event name to broadcast.
         * @param {Array} args Optional one or more arguments which will be passed onto the event listeners.
         * @return {*}
         */
        $broadcast(name : string, ...args : any[]) : ng.IAngularEvent;

        $destroy();

        $digest();

        /**
         * Dispatches an event name upwards through the scope hierarchy notifying the registered $rootScope.Scope listeners.
         * 
         * The event life cycle starts at the scope on which $emit was called. All listeners listening for name event on this scope get notified. Afterwards, the event traverses upwards toward the root scope and calls all registered listeners along the way. The event will stop propagating if one of the listeners cancels it.
         * 
         * Any exception emitted from the listeners will be passed onto the $exceptionHandler service.
         * 
         * @param {string} name Event name to emit.
         * @param {Array} args Optional one or more arguments which will be passed onto the event listeners.
         * @return {*}
         */
        $emit(name : string, ...args : any[]) : ng.IAngularEvent;

        $eval() : any;

        $eval(expression : string, locals : any) : any;

        $eval(expression : (p1: ng.IScope) => any, locals : any) : any;

        $evalAsync();

        $evalAsync(expression : string);

        $evalAsync(expression : (p1: ng.IScope) => any);

        constructor(isolate : boolean, parent : ng.IScope);

        /**
         * Listens on events of a given type. See $emit for discussion of event life cycle.
         * 
         * The event listener function format is: function(event, args...).
         * 
         * @param {string} name Event name to listen on.
         * @param {*} listener Function to call when the event is emitted.
         * @return {Function}
         */
        $on(name : string, listener : (p1: ng.IAngularEvent, p2: any) => any) : Function;

        $watch(watchExpression : string, listener : string, objectEquality : boolean) : Function;

        $watch<T>(watchExpression : string, listener : (p1: T, p2: T, p3: ng.IScope) => any, objectEquality : boolean) : Function;

        $watch(watchExpression : (p1: ng.IScope) => any, listener : string, objectEquality : boolean) : Function;

        $watch<T>(watchExpression : (p1: ng.IScope) => T, listener : (p1: T, p2: T, p3: ng.IScope) => any, objectEquality : boolean) : Function;

        $watchCollection<T>(watchExpression : string, listener : (p1: T, p2: T, p3: ng.IScope) => any) : Function;

        $watchCollection<T>(watchExpression : (p1: ng.IScope) => T, listener : (p1: T, p2: T, p3: ng.IScope) => any) : Function;

        $watchGroup(watchExpressions : any[], listener : (p1: any, p2: any, p3: ng.IScope) => any) : Function;

        $watchGroup(watchExpressions : any[], listener : (p1: any, p2: any, p3: ng.IScope) => any) : Function;

        $parent : ng.IScope;

        $root : IRootScopeService;

        $id : number;

        $$isolateBindings : any;

        $$phase : any;

        $eval(expression : string) : any;

        $eval(expression : (p1: ng.IScope) => any) : any;

        constructor(isolate : boolean);

        constructor();

        $watch(watchExpression : string, listener : string) : Function;

        $watch(watchExpression : string) : Function;

        $watch<T>(watchExpression : string, listener : (p1: T, p2: T, p3: ng.IScope) => any) : Function;

        $watch(watchExpression : (p1: ng.IScope) => any, listener : string) : Function;

        $watch(watchExpression : (p1: ng.IScope) => any) : Function;

        $watch<T>(watchExpression : (p1: ng.IScope) => T, listener : (p1: T, p2: T, p3: ng.IScope) => any) : Function;

        $watch<T>(watchExpression : (p1: ng.IScope) => T) : Function;
    }
}
declare namespace ng {
    export interface ISCEDelegateProvider extends ng.IServiceProvider {
        resourceUrlBlacklist(blacklist : any[]);

        resourceUrlWhitelist(whitelist : any[]);

        resourceUrlBlacklist() : any[];

        resourceUrlWhitelist() : any[];
    }
}
declare namespace ng {
    export interface ISCEDelegateService {
        getTrusted(type : string, mayBeTrusted : any) : any;

        trustAs(type : string, value : any) : any;

        valueOf(value : any) : any;
    }
}
declare namespace ng {
    export interface ISCEProvider extends ng.IServiceProvider {
        enabled(value : boolean);
    }
}
declare namespace ng {
    export interface ISCEService {
        getTrusted(type : string, mayBeTrusted : any) : any;

        getTrustedCss(value : any) : any;

        getTrustedHtml(value : any) : any;

        getTrustedJs(value : any) : any;

        getTrustedResourceUrl(value : any) : any;

        getTrustedUrl(value : any) : any;

        parse(type : string, expression : string) : (p1: any, p2: any) => any;

        parseAsCss(expression : string) : (p1: any, p2: any) => any;

        parseAsHtml(expression : string) : (p1: any, p2: any) => any;

        parseAsJs(expression : string) : (p1: any, p2: any) => any;

        parseAsResourceUrl(expression : string) : (p1: any, p2: any) => any;

        parseAsUrl(expression : string) : (p1: any, p2: any) => any;

        trustAs(type : string, value : any) : any;

        trustAsHtml(value : any) : any;

        trustAsJs(value : any) : any;

        trustAsResourceUrl(value : any) : any;

        trustAsUrl(value : any) : any;

        isEnabled() : boolean;
    }
}
declare namespace ng {
    export interface IScope extends ng.IRootScopeService {    }
}
declare namespace ng {
    export interface IServiceProvider {
        $get : any;
    }
}
declare namespace ng {
    export interface IServiceProviderClass {
        constructor(...args : any[]);

        constructor();
    }
}
declare namespace ng {
    export interface IServiceProviderFactory {
        (...args : any[]) : ng.IServiceProvider;
    }
}
declare namespace ng {
    export interface ITemplateCacheService extends ng.ICacheObject {    }
}
declare namespace ng {
    export interface ITemplateLinkingFunction {
        (scope : ng.IScope, cloneAttachFn : ng.ICloneAttachFunction) : ng.IAugmentedJQuery;

        (scope : ng.IScope) : ng.IAugmentedJQuery;
    }
}
declare namespace ng {
    /**
     * $templateRequest service
     * see http://docs.angularjs.org/api/ng/service/$templateRequest
     * @class
     * @extends Object
     */
    export interface ITemplateRequestService {
        /**
         * Downloads a template using $http and, upon success, stores the
         * contents inside of $templateCache.
         * 
         * If the HTTP request fails or the response data of the HTTP request is
         * empty then a $compile error will be thrown (unless
         * {ignoreRequestError} is set to true).
         * 
         * @param {string} tpl                  The template URL.
         * @param {boolean} ignoreRequestError   Whether or not to ignore the exception
         * when the request fails or the template is
         * empty.
         * 
         * @return   {*} A promise whose value is the template content.
         */
        (tpl : string, ignoreRequestError : boolean) : ng.IPromise<string>;

        /**
         * total amount of pending template requests being downloaded.
         * @type {number}
         */
        totalPendingRequests : number;

        /**
         * Downloads a template using $http and, upon success, stores the
         * contents inside of $templateCache.
         * 
         * If the HTTP request fails or the response data of the HTTP request is
         * empty then a $compile error will be thrown (unless
         * {ignoreRequestError} is set to true).
         * 
         * @param {string} tpl                  The template URL.
         * @param ignoreRequestError   Whether or not to ignore the exception
         * when the request fails or the template is
         * empty.
         * 
         * @return   {*} A promise whose value is the template content.
         */
        (tpl : string) : ng.IPromise<string>;
    }
}
declare namespace ng {
    export interface ITimeoutService {
        (delay : number, invokeApply : boolean) : ng.IPromise<void>;

        <T>(fn : (p1: any) => T, delay : number, invokeApply : boolean, ...args : any[]) : ng.IPromise<T>;

        cancel(promise : ng.IPromise<any>) : boolean;

        (delay : number) : ng.IPromise<void>;

        () : ng.IPromise<void>;

        <T>(fn : (p1: any) => T, delay : number) : ng.IPromise<T>;

        <T>(fn : (p1: any) => T) : ng.IPromise<T>;

        cancel() : boolean;
    }
}
declare namespace ng {
    export interface ITranscludeFunction {
        (scope : ng.IScope, cloneAttachFn : ng.ICloneAttachFunction) : ng.IAugmentedJQuery;

        (cloneAttachFn : ng.ICloneAttachFunction) : ng.IAugmentedJQuery;

        () : ng.IAugmentedJQuery;
    }
}
declare namespace ng {
    export interface IWindowService extends Window {
        [key : string]: any;
    }
}
declare namespace ng {}
declare namespace ng {
    /**
     * `RouteDefinition` defines a route within a {@link RouteConfig} decorator.
     * 
     * Supported keys:
     * - `path` or `aux` (requires exactly one of these)
     * - `component`, `loader`,  `redirectTo` (requires exactly one of these)
     * - `name` or `as` (optional) (requires exactly one of these)
     * - `data` (optional)
     * 
     * See also {@link Route}, {@link AsyncRoute}, {@link AuxRoute}, and {@link Redirect}.
     * @class
     * @extends Object
     */
    export interface RouteDefinition {
        path? : string;

        aux? : string;

        component? : ((ng.Type)|(ng.ComponentDefinition)|(string));

        loader? : any;

        redirectTo? : any[];

        as? : string;

        name? : string;

        data? : any;

        useAsDefault? : boolean;
    }
}
declare namespace ng {
    export interface StringTypes {    }

    export namespace StringTypes {

        /**
         * Generated to type the string "filter".
         * @exclude
         * @class
         */
        export interface filter {        }

        /**
         * Generated to type the string "currency".
         * @exclude
         * @class
         */
        export interface currency {        }

        /**
         * Generated to type the string "number".
         * @exclude
         * @class
         */
        export interface _number {        }

        /**
         * Generated to type the string "date".
         * @exclude
         * @class
         */
        export interface date {        }

        /**
         * Generated to type the string "json".
         * @exclude
         * @class
         */
        export interface json {        }

        /**
         * Generated to type the string "lowercase".
         * @exclude
         * @class
         */
        export interface lowercase {        }

        /**
         * Generated to type the string "uppercase".
         * @exclude
         * @class
         */
        export interface uppercase {        }

        /**
         * Generated to type the string "limitTo".
         * @exclude
         * @class
         */
        export interface limitTo {        }

        /**
         * Generated to type the string "orderBy".
         * @exclude
         * @class
         */
        export interface orderBy {        }
    }

}
declare namespace ng {
    /**
     * Runtime representation a type that a Component or other object is instances of.
     * 
     * An example of a `Type` is `MyCustomComponent` class, which in JavaScript is be represented by
     * the `MyCustomComponent` constructor function.
     * @class
     * @extends Function
     */
    export interface Type extends Function {    }
}


declare module "angular" {
    export = ng;
}
