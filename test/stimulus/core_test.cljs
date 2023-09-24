(ns stimulus.core-test
  (:require [cljs.test :refer [deftest]]
            [stimulus.controller :refer [defcontroller]]))

(deftest greeting-controller
  (let [expansion (macroexpand '(defcontroller GreetController
                                  {:extends Controller
                                   :targets ["output" "name"]}
                                  [(initialize [this]
                                               (js/console.log (.get-output-target this)))
                                   (connect [this]
                                            (js/console.log "called"))
                                   (greet [this event]
                                          (js/console.log event))]))]
    (assert (= expansion "TODO"))))

(comment
  (macroexpand '(defcontroller ^{:extends Controller} GreetController
                  {:targets ["output" "name"]}
                  [(initialize [this]
                               (js/console.log "greet: initialize"))
                   (connect [this]
                            (js/console.log "greet: called")
                            (.get-output-target this)
                            (js/console.log "greet: shanvitz")
                            (let [targets (.-targets this)]
                              (js/console.log targets)
                              (js/console.log (.get-output-target this))))
                   (greet [this event]
                          (let [output-target (.get-output-target this)
                                name-target (.get-name-target this)
                                name (.-value name-target)
                                greeting (str "Hello, " name "!")]
                            (gdom/setTextContent output-target greeting)))])))