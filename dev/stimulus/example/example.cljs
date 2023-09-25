(ns stimulus.example.example
  (:require [stimulus.controller :refer [defcontroller]]
            ["@hotwired/stimulus" :refer [Controller Application]]
            [goog.dom :as gdom]))

(defn greet [controller event]
  (js/console.log (.-value (.get-name-target controller))))

(defcontroller GreetController {:extends Controller
                                :targets ["output" "name"]
                                :actions {:greet greet}})

(let [application (.start Application)]
  (.register application "hello" GreetController))
