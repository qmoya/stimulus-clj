(ns stimulus.example.example
  (:require [stimulus.controller :refer [->controller]]
            ["@hotwired/stimulus" :refer [Controller Application]]
            [goog.dom :as gdom]))

(defn greet [controller event]
  (let [name-target (get-name-target controller)
        output-target (get-output-target controller)]
    (gdom/setTextContent output-target (str "Hello, " (.-value name-target)))))
  
(def greet-controller (-> {:extends Controller
                           :targets ["output" "name"]
                           :actions {:greet greet}}
                          ->controller))

(let [application (.start Application)]
  (.register application "hello" greet-controller))
