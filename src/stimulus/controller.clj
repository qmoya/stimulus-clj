(ns stimulus.controller
  (:require [shadow.cljs.modern :refer [defclass] :as modern]
            [camel-snake-kebab.core :as csk]
            [clojure.string :as str]))

(defn target-fn [target-name]
  (let [target-sym (symbol (str "get-" target-name "-target"))]
    `(~target-sym ~(vector 'this)
                  (let ~(vector 'targets (list '.-targets 'this))
                    ~(list '.find 'targets (name target-name))))))

(defn external-target-fn [target-name]
  (let [target-sym (symbol (str "get-" target-name "-target"))]
    (list 'defn target-sym (vector 'controller)
          (list (symbol (str "." target-sym)) 'controller))))

(defn controller-name [filename]
  (let [name (-> filename
                 (str/split #"/")
                 (last)
                 (str/split #"\.")
                 (first))]
    (symbol (csk/->PascalCase (str name "-controller")))))

(defn make-method [[k v]]
  `(~(symbol k) ~(vector 'this 'event) ~(list v 'this 'event)))

(defn controller [name options]
  (let [target-fns (map target-fn (:targets options))
        external-target-fns (map external-target-fn (:targets options))
        methods (map make-method (:actions options))]
    `(do
       (defclass ~name
         (~'extends ~(:extends options))
         (~'constructor ~(vector 'this 'context)
                        ~(list 'super 'context))

         ~'Object
         ~@target-fns
         ~@methods)
       (set! (.-targets ~name) ~(list 'clj->js (:targets options)))
       ~@external-target-fns
       ~name)))

(defmacro ->controller 
  ([name options] (controller name options))
  ([options] (controller (gensym "Controller") options)))