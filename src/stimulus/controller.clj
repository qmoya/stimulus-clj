(ns stimulus.controller
   (:require [shadow.cljs.modern :refer [defclass] :as modern]
             [camel-snake-kebab.core :as csk]
             [clojure.string :as str]))

 (defn target-fn [target-name]
   (let [target-sym (symbol (str "get-" target-name "-target"))]
     `(~target-sym ~(vector 'this)
                   (let ~(vector 'targets (list '.-targets 'this))
                     ~(list '.find 'targets (name target-name))))))

 (defn controller-name [filename]
   (let [name (-> filename
                  (str/split #"/")
                  (last)
                  (str/split #"\.")
                  (first))]
     (symbol (csk/->PascalCase (str name "-controller")))))

 (defmacro defcontroller [name options methods]
   (let [target-fns (map target-fn (:targets options))]
     `(do
        (defclass ~name
          (~'extends ~(:extends (meta name)))
          (~'constructor ~(vector 'this 'context)
                         ~(list 'super 'context))

          ~'Object
          ~@target-fns
          ~@methods)
        (set! (.-targets ~name) ~(list 'clj->js (:targets options))))))