(ns stimulus.core-test
  (:require [cljs.test :refer [deftest is]]
            [stimulus.controller :refer [->controller]]))

(deftest test-greet-controller
  (let [expansion (macroexpand '(->controller GreetController {:extends Controller
                                                               :targets ["output" "name"]
                                                               :actions {:greet (fn [this event]
                                                                                  (js/console.log event))}}))]
    (is (= expansion '(do
                        (shadow.cljs.modern/defclass GreetController
                          (extends Controller)
                          (constructor [this context]
                                       (super context))
                          Object
                          (get-output-target [this]
                                             (clojure.core/let [targets (.-targets this)]
                                               (.find targets "output")))
                          (get-name-target [this]
                                           (clojure.core/let [targets (.-targets this)]
                                             (.find targets "name")))
                          (greet [this event] ((fn [this event] (js/console.log event)) this event)))
                        (set! (.-targets GreetController) (clj->js ["output" "name"]))
                        (defn get-output-target [controller] (.get-output-target controller))
                        (defn get-name-target [controller] (.get-name-target controller))
                        GreetController)))))

