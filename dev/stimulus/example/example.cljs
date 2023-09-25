(ns stimulus.example.example
  (:require [stimulus.controller :refer [->controller]]
            ["@hotwired/stimulus" :refer [Controller Application]]
            [goog.dom :as gdom]))

(defn greet [controller event]
  (js/console.log (.-value (get-name-target controller))))

(def greet-controller (-> {:extends Controller
                           :targets ["output" "name"]
                           :actions {:greet greet}}
                          ->controller))

(let [application (.start Application)]
  (.register application "hello" greet-controller))
