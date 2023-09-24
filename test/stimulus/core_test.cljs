(ns stimulus.core-test
  (:require [cljs.test :refer [deftest is]]
            [stimulus.controller :refer [defcontroller]]))

(deftest test-greet-controller
  (let [expansion (macroexpand '(defcontroller GreetController
                                  {:extends Controller
                                   :targets ["output" "name"]}
                                  [(initialize [this]
                                               (js/console.log (.get-output-target this)))
                                   (connect [this]
                                            (js/console.log "called"))
                                   (greet [this event]
                                          (js/console.log event))]))]
    (is (= expansion '(do 
                        (shadow.cljs.modern/defclass GreetController 
                          (extends nil) 
                          (constructor [this context] 
                                       (super context)) 
                          Object 
                          (get-output-target [this] 
                                             (clojure.core/let [targets (.-targets this)] 
                                               (.find targets "output")))
                          (get-name-target [this] 
                                           (clojure.core/let [targets (.-targets this)] 
                                             (.find targets "name"))) 
                          (initialize [this] (js/console.log (.get-output-target this)))
                          (connect [this] (js/console.log "called")) 
                          (greet [this event] (js/console.log event))) 
                        (set! (.-targets GreetController) (clj->js ["output" "name"])))))))

